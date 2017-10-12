package com.wmcd.myb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.wmcd.myb.activity.HelloActivity;
import com.wmcd.myb.activity.MainTopActivity;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.LoginModel;
import com.wmcd.myb.net.ServeManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Main activity.
 */
public class MainActivity extends BaseActivity {
    /**
     * The Shared preferences.
     */
    SharedPreferences sharedPreferences;
    private Intent i = new Intent();

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Log.e("APP_IP", UrlConfig.IP);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("MakeupNet",
                        Activity.MODE_PRIVATE);
                if (sharedPreferences.contains("User") && MyApplication.mUser == null) {
                    try {
                        gemtUser(sharedPreferences.getString("User", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (sharedPreferences.getBoolean("hasenter", false)) {
                    i.setClass(MainActivity.this, MainTopActivity.class);
                } else {
                    i.setClass(MainActivity.this, HelloActivity.class);

                }
                startActivity(i);
                MainActivity.this.finish();
            }
        }, 2000);
    }

    private void gemtUser(String uid) {
        ServeManager.getUser(MainActivity.this, uid).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null){
                    Log.e("CenterFragment","Result:"+response.body().getResult()+"  Msg:"+response.body().getMsg());
                }
                if(response.body() != null && "01".equals(response.body().getResult())){
                    MyApplication.mUser = response.body().getUser();
                } else {
                    Toast.makeText(MainActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
