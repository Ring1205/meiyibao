package com.wmcd.myb.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.mob.tools.utils.UIHandler;
import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.LoginCodeModel;
import com.wmcd.myb.model.LoginModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.SetStatusBarLightMode;

import java.util.HashMap;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 邓志宏 on 2017/2/12. 登录的activity
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
    /**
     * The Tv code.获取验证码
     */
    @Bind(R.id.tv_code)
    TextView tv_code;
    /**
     * The Et code. 输入验证码
     */
    EditText et_code;//验证码
    /**
     * The Et phone.手机号码
     */
    @Bind(R.id.et_phone)
    EditText et_phone;//电话号码
    /**
     * The Iv logining.
     */
    ImageView iv_logining;//手机登录
    /**
     * The Weixing.
     */
    @Bind(R.id.ll_weixing)
    RelativeLayout weixing;
    /**
     * The Bottom.手机登录
     */
    @Bind(R.id.bottom)
    View bottom;
    /**
     * The Rl phone.
     */
    @Bind(R.id.rl_phone)
    RelativeLayout rl_phone;
    /**
     * The Wx icon iv.微信登录
     */
    @Bind(R.id.wx_icon_iv)
    ImageView wx_icon_iv;
    /**
     * The Tv hint.
     */
    @Bind(R.id.tv_hint)
    TextView tv_hint;

    /**
     * The constant MSG_USERID_FOUND.找到用户userid
     */
    private static final int MSG_USERID_FOUND = 1;
    /**
     * The constant MSG_LOGIN.
     */
    private static final int MSG_LOGIN = 2;
    /**
     * The constant MSG_AUTH_CANCEL.AUTH验证取消
     */
    private static final int MSG_AUTH_CANCEL = 3;
    /**
     * The constant MSG_AUTH_ERROR.AUTH验证失败
     */
    private static final int MSG_AUTH_ERROR = 4;
    /**
     * The constant MSG_AUTH_COMPLETE.AUTH验证完成
     */
    private static final int MSG_AUTH_COMPLETE = 5;


    /**
     * The Wechat. 微信
     */
    private Platform wechat;
    /**
     * The Time.验证码倒计时控件
     */
    private TimeCount time;
    /**
     * The Code.验证码
     */
    private String Code;
    /**
     * The Name.
     */
    private String name, /**
     * The Phone.手机号码
     */
    phone, /**
     * The Login type.登录方式
     */
    loginType, /**
     * The Login id.
     */
    loginId, UserIcon;

    /**
     * Login for back.登录并返回原来的Activity
     *
     * @param activity the activity 原来的Activity
     */
    public static void loginForBack(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("needBack", true);
        activity.startActivity(intent);
    }

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏字体为黑色
        SetStatusBarLightMode.StatusBarLightMode(this);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(this, 150);
        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        ShareSDK.initSDK(this);
        rl_phone.setVisibility(View.GONE);
        weixing.setOnClickListener(this);

        bottom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_code = (EditText) findViewById(R.id.et_code);
                iv_logining = (ImageView) findViewById(R.id.iv_logining);
                weixing.setVisibility(View.GONE);
                rl_phone.setVisibility(View.VISIBLE);
                iv_logining.setVisibility(View.VISIBLE);
                tv_code.setOnClickListener(LoginActivity.this);
                iv_logining.setOnClickListener(LoginActivity.this);
                return true;
            }
        });
    }

    /**
     * On destroy. 释放sharesdk相关资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    /**
     * On click.点击事件
     *
     * @param view the view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_weixing:   //微信登录
                weixing.setOnClickListener(null);
                wx_icon_iv.setImageResource(R.drawable.wx_icon_gray);
                tv_hint.setText("登录中...");
                Toast.makeText(this, "正在跳转中...", Toast.LENGTH_SHORT).show();
                wechat = ShareSDK.getPlatform(Wechat.NAME);
                authorize(wechat);
                break;
            case R.id.iv_logining:  //手机登录
                if ("15575110552".equals(et_phone.getText().toString().trim())) {
                    name = et_phone.getText().toString().trim();
                    phone = et_phone.getText().toString().trim();
                    loginType = "1";
                    loginId = "";
                    UserIcon = "";
                    loginWechat();
                }
                if (!"".equals(et_phone.getText().toString().trim())) {
                    if (!"".equals(et_code.getText().toString().trim())) {
                        if (Code != null) {
                            if (Code.equals(et_code.getText().toString().trim())) {
                                if ("2".equals(loginType)){
                                    phone = et_phone.getText().toString().trim();
                                    if (!"".equals(phone))
                                        loginWechat();
                                    break;
                                }else if (name == null || "".equals(name)) {
                                    name = et_phone.getText().toString().trim();
                                    loginType = "1";
                                    loginId = "";
                                    UserIcon = "";
                                    phone = et_phone.getText().toString().trim();
                                    loginWechat();
                                }
                            } else {
                                Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "请获取验证码", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_code:
                if (!"".equals(et_phone.getText().toString().trim())) {
                    if (Pattern.matches(UrlConfig.PHONE_MATCH, et_phone.getText().toString().trim())) {
                        ServeManager.getPhoneCode(this, et_phone.getText().toString().trim()).enqueue(new Callback<LoginCodeModel>() {
                            @Override
                            public void onResponse(Call<LoginCodeModel> call, Response<LoginCodeModel> response) {
                                if (response.body() != null) {
                                    Log.e("LoginActivity", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                                }
                                if (response.body() != null && response.body().getResult().equals("01")) {
                                    time.start();
                                    Code = response.body().getCode().toString();
                                } else {
                                    Toast.makeText(LoginActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginCodeModel> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * Handle message boolean. 主线程处理消息
     *
     * @param msg the msg
     * @return the boolean
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND:
                Log.e("LoginActivity", "用户信息已存在，正在跳转登录操作......");
                break;
            case MSG_LOGIN:
                Log.e("LoginActivity", "使用微信帐号登录中...");
                name = new Wechat(this).getDb().getUserName();
                phone = "";
                loginType = "2";
                loginId = new Wechat(this).getDb().get("unionid");
                UserIcon =new Wechat(this).getDb().getUserIcon();
                    ServeManager.getIsPhone(this, loginId).enqueue(new Callback<LoginCodeModel>() {
                        @Override
                        public void onResponse(Call<LoginCodeModel> call, Response<LoginCodeModel> response) {
                            if (response.body() != null) {
                                Log.e("LoginActivity", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                            }
                            if ("01".equals(response.body().getResult())) {
                                loginWechat();
                                finish();
                            } else if ("03".equals(response.body().getResult())) {
                                et_code = (EditText) findViewById(R.id.et_code);
                                iv_logining = (ImageView) findViewById(R.id.iv_logining);
                                weixing.setVisibility(View.GONE);
                                rl_phone.setVisibility(View.VISIBLE);
                                iv_logining.setVisibility(View.VISIBLE);
                                tv_code.setOnClickListener(LoginActivity.this);
                                iv_logining.setOnClickListener(LoginActivity.this);
                                Toast.makeText(LoginActivity.this, "请填写手机号码", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginCodeModel> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    });
                break;
            case MSG_AUTH_CANCEL:
                Log.e("LoginActivity", "授权操作已取消");
                break;
            case MSG_AUTH_ERROR:
                Log.e("LoginActivity", "授权操作遇到错误，请阅读Logcat输出");
                Toast.makeText(this, "请检查是否安装微信", Toast.LENGTH_LONG).show();
                break;
            case MSG_AUTH_COMPLETE:
                Log.e("LoginActivity", "授权成功，正在跳转登录操作…");
                // 执行相关业务逻辑操作，比如登录操作
                String userName = new Wechat(this).getDb().getUserName(); // 用户昵称
                String userId = new Wechat(this).getDb().getUserId();   // 用户Id
                String platName = new Wechat(this).getName();      // 平台名称
                Log.e("LoginActivity", "userName=" + userName + "    userId=" + userId + "   platName=" + platName);
                break;
        }
        return false;
    }

    private void loginWechat() {
        ServeManager.getLogining(this, phone, name, loginType, loginId, UserIcon).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null) {
                    Log.e("LoginActivity", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                }
                if ("01".equals(response.body().getResult())) {
                    MyApplication.mUser = response.body().getUser();
                    SharedPreferences.Editor editor = getSharedPreferences("MakeupNet",Activity.MODE_PRIVATE).edit();
                    editor.putString("User",MyApplication.mUser.getUid()+"");
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,MainTopActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "微信登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Authorize.
     *
     * @param plat the plat
     */
    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }

        Log.e("LoginActivity", plat.isAuthValid() + "");
        //判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                return;
            }
        }
        //如果机器安裝了微信，则跳转到微信作登录
        if (plat.isClientValid()) {
            plat.authorize();
        }

        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);

        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (i == Platform.ACTION_USER_INFOR) {
                    UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, LoginActivity.this);
                    login(platform.getName(), platform.getDb().getUserId(), hashMap);
                    Log.e("LoginActivity", platform.getDb().getUserName() + "======A");
                    Log.e("LoginActivity", platform.getDb().getUserId() + "======B");
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e("LoginActivity", "onError:" + i);
                if (i == Platform.ACTION_USER_INFOR) {
                    UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, LoginActivity.this);
                }
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.e("LoginActivity", "onCancel:" + i);
                if (i == Platform.ACTION_USER_INFOR) {
                    UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, LoginActivity.this);
                }
            }
        });
        //获取用户资料
        plat.showUser(null);
        //移除授权
//        plat.removeAccount(true);
    }

    /**
     * Login.
     *
     * @param plat     the plat
     * @param userId   the user id
     * @param userInfo the user info
     */
    private void login(String plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * The type Time count.倒计时
     */
    class TimeCount extends CountDownTimer {
        /**
         * Instantiates a new Time count.
         *
         * @param millisInFuture    the millis in future
         * @param countDownInterval the count down interval
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        /**
         * On finish.倒计时完毕 可以重新获取验证码
         */
        @Override
        public void onFinish() {//计时完毕时触发
            tv_code.setText("重新获取");
            tv_code.setClickable(true);
        }

        /**
         * On tick.正在倒计时的回调
         *
         * @param millisUntilFinished the millis until finished 离倒计时完毕还有的毫秒数
         */
        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tv_code.setClickable(false);
            tv_code.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
