package com.wmcd.myb.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.util.FileHandler;
import com.wmcd.myb.util.UiUtils;
import java.net.URLEncoder;

/**
 * 下载doc
 * Created by zlf on 2017/3/26 0026.
 */
public class LoadDocService extends Service {
    /**
     * The Local path.
     */
    private String localPath;
    /**
     * The Doc name.
     */
    private String docName;

    /**
     * On create.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        localPath = FileHandler.getInstance().getDCIMPath() + "/myb_doc/";
        FileHandler.getInstance().createDirectory(localPath);
    }

    /**
     * On start.
     *
     * @param intent  the intent
     * @param startId the start id
     */
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        try {
            docName = intent.getStringExtra("docUrl");
            final String urlStr = URLEncoder.encode(docName, "UTF-8").replaceAll("\\+", "%20");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UiUtils.downDoc(mHandler, UrlConfig.DOC_HTTP + urlStr, localPath + docName);
                }
            }).start();

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(LoadDocService.this, "资源文件下载失败...", Toast.LENGTH_LONG).show();
            stopSelf();
        }
    }

    /**
     * The M handler.
     */
//0:下载成功  1：下载失败
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(LoadDocService.this, "资源文件下载成功...存放目录:"+localPath+docName, Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(LoadDocService.this, "资源文件下载失败...", Toast.LENGTH_LONG).show();
                    break;
            }
            mHandler = null;
            stopSelf();
        }

        ;
    };

    /**
     * On bind binder.
     *
     * @param intent the intent
     * @return the binder
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * On destroy.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
