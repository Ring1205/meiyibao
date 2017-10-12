package com.wmcd.myb.util;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.wmcd.myb.R;
import com.wmcd.myb.activity.MainTopActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.functions.Action1;


/**
 * The type Upgrade manager.
 */
public class UpgradeManager {

    /**
     * The M context.
     */
    private Context mContext;
    /**
     * The Update msg.
     */
    private String updateMsg = "有最新的软件包哦，亲快下载吧~";
    /**
     * The Path.
     */
    private String path = "";
    /**
     * The constant defaultName.
     */
    private static final String defaultName = "update.apk";
    /**
     * The Apk name.
     */
    private String apkName = defaultName;
    /**
     * The constant DOWN_UPDATE.
     */
    private static final int DOWN_UPDATE = 1;
    /**
     * The constant DOWN_OVER.
     */
    private static final int DOWN_OVER = 2;
    /**
     * The constant DOWN_ERROR.
     */
    private static final int DOWN_ERROR = 3;
    /**
     * The constant DOWNLOAD_NOT_ENOUGH_SPACE.
     */
    private static final int DOWNLOAD_NOT_ENOUGH_SPACE = 4;
    /**
     * The constant APK_REMOVED.
     */
    private static final int APK_REMOVED = 5;
    /**
     * The Progress.
     */
    private int progress;
    /**
     * The Down load thread.
     */
    private Thread downLoadThread;
    /**
     * The Download dialog.
     */
    private Dialog downloadDialog;
    /**
     * The Intercept flag.
     */
    private boolean interceptFlag = false;
    /**
     * The M progress bar.
     */
    private ProgressBar mProgressBar;
    /**
     * The Apk url.
     */
    private String apkUrl;
    /**
     * The Progress value tv.
     */
    private TextView progressValueTV, /**
     * The Progress pre tv.
     */
    progressPreTV;

    /**
     * The M handler.
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgressBar.setProgress(msg.arg1);
                    progressValueTV.setText(msg.arg1 + "%");
                    progressPreTV.setText(msg.arg1 + "/100");
                    break;
                case DOWN_OVER:
                    downloadDialog.dismiss();
                    installApk();
                    break;
                case DOWN_ERROR:
                    downloadDialog.dismiss();
                    Toast.makeText(mContext, "下载出错", Toast.LENGTH_LONG).show();
                    break;
                case DOWNLOAD_NOT_ENOUGH_SPACE:
                    downloadDialog.dismiss();
                    Toast.makeText(mContext, "空间不足", Toast.LENGTH_LONG).show();
                    break;
                case APK_REMOVED:
                    Toast.makeText(mContext, "安装包被移除", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    /**
     * Instantiates a new Upgrade manager.
     *
     * @param context the context
     * @param url     the url
     */
    public UpgradeManager(Context context, String url) {
        this.mContext = context;
        if (null != url)
            apkUrl = url;
    }

    /**
     * Instantiates a new Upgrade manager.
     *
     * @param context the context
     * @param url     the url
     * @param info    the info
     */
    public UpgradeManager(Activity context, String url, String info) {
        this.mContext = context;
        updateMsg = info;
        if (null != url)
            apkUrl = url;
    }

    /**
     * Check update info.
     */
// 外部接口让主Activity调用
    public void checkUpdateInfo() {
        showDownloadDialog();
    }

    /**
     * Show download dialog.
     */
    private void showDownloadDialog() {
        downloadDialog = new Dialog(mContext, R.style.dialog);
        downloadDialog.setContentView(R.layout.upgrade_dialog);
        ((TextView) downloadDialog.findViewById(R.id.textView1)).setText(TextUtils.isEmpty(updateMsg) ? "软件升级" : Html.fromHtml(updateMsg));
        downloadDialog.setCancelable(false);
        mProgressBar = (ProgressBar) downloadDialog.findViewById(R.id.progressBar_step);
        progressValueTV = (TextView) downloadDialog.findViewById(R.id.progress_value_tv);
        progressPreTV = (TextView) downloadDialog.findViewById(R.id.progress_pre_tv);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        downloadDialog.show();
        RxPermissions.getInstance(mContext)
                // 申请权限
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            downloadApk();
                        } else {


                        }
                    }
                });


    }

    /**
     * The Mdown apk runnable.
     */
    private Runnable mdownApkRunnable = new Runnable() {
        InputStream is = null;
        FileOutputStream fos = null;

        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                if (!UiUtils.isAvaiableSpace(length)) {
                    interceptFlag = true;
                    mHandler.sendEmptyMessage(DOWNLOAD_NOT_ENOUGH_SPACE);
                    return;
                }
                is = conn.getInputStream();
                path = UiUtils.setMkDir(mContext);

                try {
                    apkName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);
                } catch (IndexOutOfBoundsException e1) {
                    Log.d("DOWNLOAD", "IndexOutOfBounds");
                    apkName = defaultName;
                } catch (NullPointerException e2) {
                    Log.d("DOWNLOAD", "NullPointer");
                    apkName = defaultName;
                }

                File file = new File(path, apkName);
               /* boolean file1 = file.isDirectory();
                if (file1)*/
                Log.i("路径", file.getAbsolutePath());
                /**
                 * 动态权限
                 */
                ActivityCompat.requestPermissions((MainTopActivity)mContext, new String[]{android
                        .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                fos = new FileOutputStream(file);
                float count = 0;
                byte buf[] = new byte[1024];
                int numread = 0;
                while (!interceptFlag && (numread = is.read(buf)) > 0) {
                    count += numread;
                    fos.write(buf, 0, numread);
                    progress = (int) ((count * 100) / length);
                    Message msg = Message.obtain();
                    msg.what = DOWN_UPDATE;
                    msg.arg1 = progress;
                    mHandler.sendMessage(msg);
                }
                if (interceptFlag) {
                    return;
                }
                fos.flush();
                progress = 100;
                mHandler.sendEmptyMessage(DOWN_OVER);
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(DOWN_ERROR);
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    mHandler.sendEmptyMessage(DOWN_ERROR);
                }
            }
        }
    };

    /**
     * 下载apk
     *
     * @param
     */
    private void downloadApk() {
        interceptFlag = false;
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     *
     * @param
     */
    private void installApk() {
//        File apkfile = new File(path, apkName);
//        if (!apkfile.exists()) {
//            mHandler.sendEmptyMessage(APK_REMOVED);
//            return;
//        }
//        // 通过Intent安装APK文件
//        String cmd = "chmod 777 " + path;
//        try {
//            Runtime.getRuntime().exec(cmd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.parse("file://" + apkfile.toString());
//        i.setDataAndType(uri, "application/vnd.android.package-archive");
//        mContext.startActivity(i);
        File apkfile = new File(path, apkName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(mContext, "com.wmcd.myb.fileprovider", apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }
}
