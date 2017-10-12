package com.wmcd.myb.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.HelloActivity;
import com.wmcd.myb.activity.MainTopActivity;
import com.wmcd.myb.adapter.HomeAdapter;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.HomeModel01;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页
 * Created by 邓志宏 on 2017/2/16.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    /**
     * The Recycler view.
     */
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    /**
     * The Swipe refresh layout.
     */
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    /**
     * The Fenge.
     */
    @Bind(R.id.fenge)
    TextView fenge;
    /**
     * The Tv tuijian.
     */
    @Bind(R.id.tv_tuijian)
    TextView tv_tuijian;
    /**
     * The Tv touxiang.
     */
    @Bind(R.id.tv_touxiang)
    TextView tv_touxiang;
    /**
     * The Tv haibao.
     */
    @Bind(R.id.tv_haibao)
    TextView tv_haibao;
    /**
     * The Tv minpian.
     */
    @Bind(R.id.tv_minpian)
    TextView tv_minpian;
    /**
     * The Tv vip card.
     */
    @Bind(R.id.tv_vip_card)
    TextView tv_vip_card;
    /**
     * The Home adapter.
     */
    private HomeAdapter homeAdapter;

    /**
     * Sets layout resouce id.
     *
     * @return the layout resouce id
     */
    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_main;
    }

    /**
     * Init view.
     */
    @Override
    protected void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        homeAdapter = new HomeAdapter(context);
        recyclerView.setAdapter(homeAdapter);

    }

    /**
     * 网络请求
     */
    @Override
    public void initData() {
        UiUtils.startnet(context);
        ServeManager.getHome01Service(context, MyApplication.mUser==null?"":MyApplication.mUser.getUid()+"").enqueue(new Callback<HomeModel01>() {
            @Override
            public void onResponse(Call<HomeModel01> call, Response<HomeModel01> response) {
                if (response.body() != null)
                    Log.e("HomeActivity", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    homeAdapter.setHomeModel(response.body());
                    homeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UiUtils.endnet();
                    }
                }, 1000);

            }
            @Override
            public void onFailure(Call<HomeModel01> call, Throwable t) {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                UiUtils.endnet();
            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }


    /**
     * Sets listener.
     */
    @Override
    protected void setListener() {
        super.setListener();
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        initData();
    }
}
