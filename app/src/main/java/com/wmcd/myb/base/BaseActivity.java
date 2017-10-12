package com.wmcd.myb.base;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.wmcd.myb.util.OnBooleanListener;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 邓志宏 on 2017/2/16.
 */
public class BaseActivity extends FragmentActivity {
    /**
     * The On permission listener.请求权限后回调的监听
     */
    private OnBooleanListener onPermissionListener;

    /**
     * Apply schedulers observable . transformer.
     *
     * @param <T> the type parameter
     * @return the observable . transformer
     */
    public <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     * @param persistentState    the persistent state
     */
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁用横屏
    }

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁用横屏
    }

    /**
     * On start.
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * On resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        //清空Glide的缓存
        //Glide.get(this).clearMemory();
    }

    //这个方法必须在子线程运行
    public boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(MyApplication.getContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(MyApplication.getContext()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Init state.初始化activity的状态 导航栏和状态栏透明
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * On permission requests.请求动态权限
     *
     * @param permission        the permission 要请求的权限
     * @param onBooleanListener the on boolean listener 请求结果的监听器
     */
    public void onPermissionRequests(String [] permission, OnBooleanListener onBooleanListener) {
        onPermissionListener = onBooleanListener;

        ActivityCompat.requestPermissions(this, permission,1);
    }


    /**
     * On request permissions result. 请求结果的会调方法
     *
     * @param requestCode  the request code 请求码
     * @param permissions  the permissions 权限数组
     * @param grantResults the grant results 请求结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限通过
                if (onPermissionListener != null) {
                    onPermissionListener.onClick(true);
                }
            } else {
                //权限拒绝
                if (onPermissionListener != null) {
                    onPermissionListener.onClick(false);
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
