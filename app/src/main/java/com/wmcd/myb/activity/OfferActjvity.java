package com.wmcd.myb.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class OfferActjvity extends BaseActivity {
    @Bind(R.id.sort_byzonghe)
    TextView sortByzonghe;
    @Bind(R.id.sort_byprice)
    TextView sortByprice;
    @Bind(R.id.sort_byarea)
    TextView sortByarea;

    TextView tvSmalltobig;
    TextView tvBigtosmall;
    @Bind(R.id.fengge)
    ImageView fengge;
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.item_offer_popupwindow, null);
        tvSmalltobig = (TextView) view.findViewById(R.id.tv_smalltobig);
        tvBigtosmall = (TextView) view.findViewById(R.id.tv_bigtosmall);
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        }
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        initListener();

    }

    private void initListener() {
        tvSmalltobig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tvSmalltobig.getText().toString()){

                    case "从小到大":
                        Toast.makeText(OfferActjvity.this, "从小到大排序", Toast.LENGTH_SHORT).show();
                        break;
                    case "从近到远":
                        Toast.makeText(OfferActjvity.this, "从近到远排序", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        tvBigtosmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tvBigtosmall.getText().toString()){

                    case "从大到小":
                        Toast.makeText(OfferActjvity.this, "从大到小排序", Toast.LENGTH_SHORT).show();
                        break;
                    case "从远到近":
                        Toast.makeText(OfferActjvity.this, "从远到近排序", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });


    }

    @OnClick({R.id.sort_byzonghe, R.id.sort_byprice, R.id.sort_byarea})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_byzonghe:
                Toast.makeText(this, "综合排序", Toast.LENGTH_SHORT).show();
                // popupWindow.showAsDropDown(sortByzonghe);
                break;
            case R.id.sort_byprice:
                Toast.makeText(this, "价格排序", Toast.LENGTH_SHORT).show();
                tvSmalltobig.setText("从小到大");
                tvBigtosmall.setText("从大到小");
                popupWindow.showAsDropDown(fengge);
                break;
            case R.id.sort_byarea:
                tvSmalltobig.setText("从近到远");
                tvBigtosmall.setText("从远到近");
                Toast.makeText(this, "地区排序", Toast.LENGTH_SHORT).show();
                popupWindow.showAsDropDown(fengge);
                break;
        }
    }
}
