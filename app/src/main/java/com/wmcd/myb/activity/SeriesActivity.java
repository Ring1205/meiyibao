package com.wmcd.myb.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.ClassifyAdapter;
import com.wmcd.myb.adapter.SeriesAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.model.ClassifyModel;
import com.wmcd.myb.model.SeriesModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.wigdet.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
public class SeriesActivity extends BaseActivity {
    /**
     * The Tv name.
     */
    @Bind(R.id.tv_name)
    TextView tv_name;
    /**
     * The Recycler view.
     */
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;


    /**
     * The Stid.
     */
    private  String stid;
    /**
     * The Series adapter.
     */
    private SeriesAdapter seriesAdapter;
    /**
     * The Data.
     */
    private List<SeriesModel.ListBean> data = new ArrayList<>();

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view.
     */
    private void initView() {
        UiUtils.startnet(this);
        Intent intent = getIntent();
        stid = intent.getStringExtra("stid");
        Log.e("SeriesActivity","stid----"+stid);
        initting();
        initData();
    }

    /**
     * Initting.
     */
    public void initting() {
        tv_name.setText("系列推荐");
        seriesAdapter = new SeriesAdapter(SeriesActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(2, this)));
        recyclerView.setAdapter(seriesAdapter);
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick(R.id.iv_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * Init data.
     */
    private void initData() {
        ServeManager.getSeries(this, stid).enqueue(new Callback<SeriesModel>() {
            @Override
            public void onResponse(Call<SeriesModel> call, Response<SeriesModel> response) {
                   if (response.body() != null && "01".equals(response.body().getResult())) {
                       data = response.body().getList();
                        seriesAdapter.setDate(data);
                       seriesAdapter.notifyDataSetChanged();


                    }
                 else {
                    Toast.makeText(SeriesActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                }

                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<SeriesModel> call, Throwable t) {
                Toast.makeText(SeriesActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "onFailure:" + t.getLocalizedMessage());
                UiUtils.endnet();
            }
        });
    }

}
