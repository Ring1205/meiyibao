package com.wmcd.myb.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.wmcd.myb.base.MyApplication;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 异常抓取
 *
 * @ClassName: CrashHandler
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wangyikai
 * @date: 2015 -8-18 下午10:42:31
 */
public class CrashHandler implements UncaughtExceptionHandler {


    /**
     * The constant TAG.
     */
    public static final String TAG = "CrashHandler";

    /**
     * The M default handler.
     */
// 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * The constant instance.
     */
// CrashHandler实例
    private static CrashHandler instance;

    /**
     * The M context.
     */
// 程序的Context对象
    private Context mContext;

    /**
     * The Infos.
     */
// 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    /**
     * The Formatter.
     */
// 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * Instantiates a new Crash handler.
     */
    private CrashHandler() {

    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CrashHandler getInstance() {

        if (instance == null) {
            instance = new CrashHandler();
        }

        return instance;
    }

    /**
     * 初始化
     *
     * @param context the context
     */
    public void init(Context context) {

        mContext = context;

        // 获取系统默认的UncaughtException处理器  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该CrashHandler为程序的默认处理器  
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * Uncaught exception.
     *
     * @param thread the thread
     * @param ex     the ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // TODO Auto-generated method stub
        if (handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);
        }else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }


    /**
     * Handle exception boolean.
     *
     * @param ex the ex
     * @return the boolean
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出...", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        // 收集设备参数信息  
        collectDeviceInfo(mContext);
        // 保存日志文件
        return saveCrashInfoFile(ex);
    }


    /**
     * Collect device info.
     *
     * @param ctx the ctx
     */
    public void collectDeviceInfo(Context ctx) {

        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }


    /**
     * Save crash info file boolean.
     *
     * @param ex the ex
     * @return the boolean
     */
    private boolean saveCrashInfoFile(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("=============================================\n");
        sb.append(DateUtil.toDateAndTimeStr(Calendar.getInstance()));
        sb.append("\n");

        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        sb.append("\n*******************************************\n");
        sb.append("Exception:\n");

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        String date = formatter.format(new Date());
        FileHandler fileHandler = FileHandler.getInstance();
        return fileHandler.write(MyApplication.errPath + date + "_err.txt", sb.toString().getBytes(), true);
    }


}
