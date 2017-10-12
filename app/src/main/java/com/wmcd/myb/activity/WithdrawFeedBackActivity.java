package com.wmcd.myb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/23.
 */
public class WithdrawFeedBackActivity extends BaseActivity {
    /**
     * The Money tv.
     */
    @Bind(R.id.money_tv)
    TextView money_tv;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashinfo);
        ButterKnife.bind(this);
        float fstr = Float.parseFloat(getIntent().getStringExtra("amount"))/100;
        money_tv.setText("￥"+fstr);
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick(R.id.back_iv)
    public void onClick(View view){
        finish();
    }
}
