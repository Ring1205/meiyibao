package com.wmcd.myb.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.Pingpp;
import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.LoginModel;
import com.wmcd.myb.model.PaymentRequest;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.DateUtil;
import com.wmcd.myb.util.ImageType;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.wigdet.PaymentTask;
import com.zxing.activity.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 支付
 * Created by 邓志宏 on 2017/2/27.
 */
public class PayActivity extends BaseActivity implements View.OnClickListener {
    /**
     * The Tv zfj.
     */
    @Bind(R.id.tv_zfj)
    TextView tv_zfj;//支付金额   ¥100.00
    /**
     * The Cb zhifubao.
     */
    @Bind(R.id.cb_zhifubao)
    CheckBox cb_zhifubao;
    /**
     * The Cb weixing.
     */
    @Bind(R.id.cb_weixing)
    CheckBox cb_weixing;
    /**
     * The Et code.
     */
    @Bind(R.id.et_code)
    EditText et_code;//邀请码
    /**
     * The Rl bottom.
     */
    @Bind(R.id.rl_bottom)
    RelativeLayout rl_bottom;
    /**
     * The Tv vip.
     */
    @Bind(R.id.tv_vip)
    TextView tv_vip;

    /**
     * The Pay style.
     */
    private String payStyle;
    /**
     * The Payment task.
     */
    private PaymentTask paymentTask;
    /**
     * The Period.
     */
    private int period, /**
     * The Mid.
     */
    mid, /**
     * The Price.
     */
    price;
    /**
     * The Name.
     */
    private String name;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_vip);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view.
     */
    private void initView() {
        Intent intent = getIntent();
        price = intent.getIntExtra("price", 0);
        Log.e("PayActivity","price:"+price);
        tv_zfj.setText(price / 100F + "");
        period = intent.getIntExtra("period", 0);
        mid = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        tv_vip.setText(name);
        payStyle = PaymentTask.CHANNEL_ALIPAY;
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String code = editable.toString().trim();
                if (!"".equals(code) && (code.length() == 6 || code.length() == 7 || code.length() == 11)) {
                    request(code);
                }
            }
        });
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.cb_zhifubao, R.id.cb_weixing, R.id.rl_bottom, R.id.iv_back, R.id.rl_pay, R.id.scan_ib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_zhifubao:
                cb_weixing.setChecked(false);
                cb_zhifubao.setChecked(true);
                payStyle = PaymentTask.CHANNEL_ALIPAY;
                break;
            case R.id.cb_weixing:
                cb_zhifubao.setChecked(false);
                cb_weixing.setChecked(true);
                payStyle = PaymentTask.CHANNEL_WECHAT;
                break;
            case R.id.rl_pay:
                paymentTask = new PaymentTask(this, rl_bottom);
                long now = new Date().getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, period);
                final Date y = c.getTime();
                final String code = et_code.getText().toString().trim();
                if ("".equals(code)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("未填写邀请码，是否支付");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            paymentTask.execute(new PaymentRequest(price + "", payStyle, name, "body", MyApplication.mUser.getUid() + "", mid + "", DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"), DateUtil.formatDate(y, "yyyyMMddHHmmss"), code));
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.create().show();
                } else {
                    paymentTask.execute(new PaymentRequest(price + "", payStyle, name, "body", MyApplication.mUser.getUid() + "", mid + "", DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"), DateUtil.formatDate(y, "yyyyMMddHHmmss"), code));
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.scan_ib:
                Intent intent = new Intent(this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, CaptureActivity.SCAN_CODE_RESULT);
                break;
        }
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        rl_bottom.setOnClickListener(this);
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
            }
        } else if (requestCode == CaptureActivity.SCAN_CODE_RESULT) {
            if (data == null) {
                return;
            }
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                et_code.setText(result.substring(result.lastIndexOf("=") + 1, result.length()));
//                request(et_code.getText().toString());
            }
        }
    }

    /**
     * Request.
     *
     * @param code the code
     */
    private void request(String code) {
        UiUtils.startnet(this);
        ServeManager.queryUserByInvitationCode(this, code).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    Log.e("PayActivity",response.body().getResult()+"    "+response.body().getMsg());
                    if (response.body().getUser() == null) {
                        findViewById(R.id.rl_05).setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "没有找到邀请码对应的好友信息", Toast.LENGTH_LONG).show();
                    } else {
                        findViewById(R.id.rl_05).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.friendName_tv)).setText(response.body().getUser().getName());
                        ImageView friendIV = (ImageView) findViewById(R.id.friend_iv);
                        UiUtils.loadImage(PayActivity.this, UrlConfig.IMG + response.body().getUser().getIconR(), friendIV, ImageType.CIRCLE);
                    }
                } else {
                    findViewById(R.id.rl_05).setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                findViewById(R.id.rl_05).setVisibility(View.GONE);
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Show msg.
     *
     * @param title the title
     * @param msg1  the msg 1
     * @param msg2  the msg 2
     */
    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if ("success".equals(str)) {
            builder.setMessage("是否支付成功");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(PayActivity.this, PayFeedbackActivity.class);
                    intent.putExtra("result", "success");
                    intent.putExtra("code", paymentTask.getChargeJsonModel().getOrder_no());
                    startActivity(intent);
                    finish();
                }
            });
            builder.create().show();
        } else if ("fail".equals(str)) {
            builder.setMessage("是否已支付");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(PayActivity.this, PayFeedbackActivity.class);
                    intent.putExtra("result", "fail");
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("否", null);
            builder.create().show();
        } else if ("cancel".equals(str)) {
            Toast.makeText(this,"支付已取消",Toast.LENGTH_SHORT).show();
        } else if ("invalid".equals(str)) {
            builder.setMessage("请安装相关软件");
            builder.create().show();
        }
    }
}
