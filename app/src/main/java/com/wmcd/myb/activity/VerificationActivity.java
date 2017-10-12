package com.wmcd.myb.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.InformationModel;
import com.wmcd.myb.model.LoginCodeModel;
import com.wmcd.myb.net.ServeManager;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class VerificationActivity extends BaseActivity {

    /**
     * The Cancel verifi.
     */
    @Bind(R.id.cancel_verifi)
    ImageView cancelVerifi;
    /**
     * The Tv phone.
     */
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    /**
     * The Et phone.
     */
    @Bind(R.id.et_phone)
    EditText etPhone;
    /**
     * The Et code.
     */
    @Bind(R.id.et_code)
    EditText etCode;
    /**
     * The Tv code.
     */
    @Bind(R.id.tv_code)
    TextView tvCode;
    /**
     * The View.
     */
    @Bind(R.id.view)
    View view;
    /**
     * The Rl phone.
     */
    @Bind(R.id.rl_phone)
    RelativeLayout rlPhone;
    /**
     * The Bt verifi.
     */
    @Bind(R.id.bt_verifi)
    ImageView btVerifi;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_activity);
        ButterKnife.bind(this);
        time = new VerificationActivity.TimeCount(60000, 1000);//构造CountDownTimer对象

    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.cancel_verifi, R.id.tv_phone, R.id.et_phone, R.id.et_code, R.id.tv_code, R.id.bt_verifi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_verifi:
                finish();
                break;
            case R.id.tv_phone:
                break;
            case R.id.et_phone:
                break;
            case R.id.et_code:
                break;
            case R.id.tv_code:
                getCode();
                break;
            case R.id.bt_verifi:
                String code = etCode.getText().toString().trim();
                if (code != null) {
                    verifiCode(code);
                } else {
                    Toast.makeText(VerificationActivity.this, "请输入你收到的验证码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * Verifi code.
     *
     * @param code the code
     */
    private void verifiCode(String code) {
        String uid = MyApplication.mUser.getUid() + "";
        String name = MyApplication.mUser.getName() + "";
        String address = MyApplication.mUser.getAddress() + "";
        String shopname = MyApplication.mUser.getShopname() + "";
        String industry = MyApplication.mUser.getIndustry() + "";
        if (code.equals(Code)) {
            //验证成功 执行上传逻辑
            ServeManager.getUserInformation(this, uid, name, phonenumber, address, "", shopname, industry).enqueue(new Callback<InformationModel>() {
                @Override
                public void onResponse(Call<InformationModel> call, Response<InformationModel> response) {
                    if (response.body() != null)
                        Log.e("VerificationActivity", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                    if (response.body() != null && "01".equals(response.body().getResult())) {
                        Toast.makeText(VerificationActivity.this, "绑定完毕", Toast.LENGTH_SHORT).show();
                        //成功之后在本地更新登录信息
                        MyApplication.mUser.setPhone(phonenumber);
                        finish();

                    } else if (response.body() != null && "03".equals(response.body().getResult())){
                        Toast.makeText(VerificationActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(VerificationActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<InformationModel> call, Throwable t) {
                    Toast.makeText(VerificationActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(VerificationActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * The Code.
     */
    private String Code;
    /**
     * The Time.
     */
    private TimeCount time;
    /**
     * The Phonenumber.
     */
    private String phonenumber;

    /**
     * Gets code.得到验证码
     */
    private void getCode() {
        if (!"".equals(etPhone.getText().toString().trim())) {
            if (Pattern.matches(UrlConfig.PHONE_MATCH, etPhone.getText().toString().trim())) {
                ServeManager.getPhoneCode(this, etPhone.getText().toString().trim()).enqueue(new Callback<LoginCodeModel>() {
                    @Override
                    public void onResponse(Call<LoginCodeModel> call, Response<LoginCodeModel> response) {
                        if (response.body() != null) {
                            Log.e("", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                        }
                        if (response.body() != null && response.body().getResult().equals("01")) {
                            time.start();
                            Code = response.body().getCode().toString();
                            phonenumber = etPhone.getText().toString().trim();
                        } else {
                            Toast.makeText(VerificationActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginCodeModel> call, Throwable t) {
                        Toast.makeText(VerificationActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                etPhone.setText("");
            }
        } else {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * The type Time count.
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
         * On finish.
         */
        @Override
        public void onFinish() {//计时完毕时触发
            tvCode.setText("重新获取");
            tvCode.setClickable(true);
        }

        /**
         * On tick.
         *
         * @param millisUntilFinished the millis until finished
         */
        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tvCode.setClickable(false);
            tvCode.setText(millisUntilFinished / 1000 + "秒");
        }
    }

}
