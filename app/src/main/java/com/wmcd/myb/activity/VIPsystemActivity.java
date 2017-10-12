package com.wmcd.myb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.GalleryAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.util.RecyclerScaleUtils;
import com.wmcd.myb.util.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 邓志宏 on 2017/2/20.
 */
public class VIPsystemActivity extends BaseActivity {
    /**
     * The Rl pay.
     */
    @Bind(R.id.rl_pay)
    RelativeLayout rl_pay;
    /**
     * The M recycler view.
     */
    @Bind(R.id.list)
    RecyclerView mRecyclerView;
    /**
     * The Strings.
     */
    private String[] strings = new String[]{};
    /**
     * The M datas.
     */
    private List<Integer> mDatas;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view.
     */
    private void initView() {
        initDatas();
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, mDatas);
        mRecyclerView.setAdapter(galleryAdapter);
        RecyclerScaleUtils utils = new RecyclerScaleUtils();
        utils.attachToRecyclerView(mRecyclerView, ScreenUtils.dip2px(this, 30f));
    }

    /**
     * Init datas.
     */
    private void initDatas() {
        mDatas = new ArrayList<Integer>(Arrays.asList(R.drawable.vip_top,
                R.drawable.vip_top,
                R.drawable.vip_top));
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_back,R.id.rl_pay})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_pay:
                break;
        }
    }
}