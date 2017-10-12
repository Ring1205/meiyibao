package com.wmcd.myb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.FeedbackModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 支付反馈界面
 * Created by Administrator on 2017/3/8.
 */
public class PayFeedbackActivity extends BaseActivity {
    /**
     * The Iv s.
     */
    @Bind(R.id.iv_s)
    ImageView iv_s;
    /**
     * The Iv e.
     */
    @Bind(R.id.iv_e)
    ImageView iv_e;
    /**
     * The Text.
     */
    @Bind(R.id.text)
    TextView text;
    /**
     * The Button huiyan.
     */
    @Bind(R.id.button_huiyan)
    Button button_huiyan;

    /**
     * The String.
     */
    private String string;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        string = intent.getStringExtra("result");
        String code = intent.getStringExtra("code");
        if ("success".equals(string)){
            UiUtils.startnet(this);
            ServeManager.getFeedback(this,code).enqueue(new Callback<FeedbackModel>() {
                @Override
                public void onResponse(Call<FeedbackModel> call, Response<FeedbackModel> response) {
                    if (response.body() != null)
                        Log.e("PayFeedbackActivity","Result:"+response.body().getResult()+"  Msg:"+response.body().getMsg());
                    if (response.body() != null&&"01".equals(response.body().getResult()) && response.body().getOrderMember() != null){
                        iv_e.setVisibility(View.GONE);
                        iv_s.setVisibility(View.VISIBLE);
                        text.setText("支付成功");
                        button_huiyan.setVisibility(View.VISIBLE);
                    } else {
                        iv_e.setVisibility(View.VISIBLE);
                        iv_s.setVisibility(View.GONE);
                        text.setText("支付失败请联系客服");
                    }
                    UiUtils.endnet();
                }

                @Override
                public void onFailure(Call<FeedbackModel> call, Throwable t) {
                    Toast.makeText(PayFeedbackActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
                    Log.e("LoginActivity","onFailure:"+t.getLocalizedMessage());
                    UiUtils.endnet();
                }
            });
        } else if ("fail".equals(string)){
            iv_e.setVisibility(View.VISIBLE);
            iv_s.setVisibility(View.GONE);
            text.setText("支付失败请重试");
        }
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_back,R.id.button_huiyan})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.button_huiyan:
                startActivity(new Intent(this,InformationInputActivity.class));
                finish();
                break;
        }
    }
}
