package com.wmcd.myb.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.lzy.okhttputils.OkHttpUtils;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.LoginModel;
import com.wmcd.myb.util.CrashHandler;
import com.wmcd.myb.util.FileHandler;

import java.io.File;

/**
 * Created by 邓志宏 on 2017/2/16.
 */
public class MyApplication extends Application {
    /**
     * The constant mContext.
     */
    private static Context mContext;
    /**
     * The constant basePath.
     */
    public static final String basePath = Environment.getExternalStorageDirectory() + "/makeup.net/";
    /**
     * The constant imgPath.
     */
    public static final String imgPath = basePath + "img/";
    /**
     * The constant tempImgPath.
     */
    public static final String tempImgPath = imgPath + "cache/";
    /**
     * The constant videoPath.
     */
    public static final String videoPath = basePath + "video/";
    /**
     * The constant errPath.
     */
    public static final String errPath = basePath + "err_log/";
    /**
     * The constant DEFAULT_SAVE_IMAGE_PATH.
     */
// 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = basePath + "/discover" + File.separator + "images"
            + File.separator;

    /**
     * The constant mUser.
     */
    public static LoginModel.UserBean mUser;

    /**
     * Gets context.
     *
     * @return the context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * On create.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel("美易宝"));
        mContext = this;
        FileHandler.getInstance().createDirectory(basePath);
        FileHandler.getInstance().createDirectory(imgPath);
        FileHandler.getInstance().createDirectory(tempImgPath);
        FileHandler.getInstance().createDirectory(videoPath);
        FileHandler.getInstance().createDirectory(errPath);
        CrashHandler.getInstance().init(this);
        OkHttpUtils.init(this);

        if (Build.VERSION.SDK_INT < 21) {
            UrlConfig.IP = "http://www.meiyibaoapp.com/";
        }

        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        String msg = new StringBuilder("").append("Mode:").append(mode)
                                .append(" Code:").append(code)
                                .append(" Info:").append(info)
                                .append(" HandlePatchVersion:").append(handlePatchVersion).toString();
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.e("HotFix", "succeed:" + msg);
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            Toast.makeText(mContext, "已替您更新版本...", Toast.LENGTH_LONG).show();
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                            Log.e("HotFix", "强制退出:" + msg);
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            SophixManager.getInstance().cleanPatches();
                            Log.e("HotFix", "清空本地补丁:" + msg);
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.e("HotFix", "error:" + msg);
                        }
                    }
                }).initialize();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
