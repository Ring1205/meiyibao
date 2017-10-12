package com.wmcd.myb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by LiPeigen on 2017/7/24 0024.
 */

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
    }
}
