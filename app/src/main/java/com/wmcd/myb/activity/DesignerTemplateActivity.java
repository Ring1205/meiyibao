package com.wmcd.myb.activity;

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
import com.wmcd.myb.adapter.DesignerAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.DesignerModel;
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
 * 设计师模板界面
 * Created by Administrator on 2017/7/20.
 */

public class DesignerTemplateActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private List<DesignerModel.ListBean> designers;
    private DesignerAdapter classifyadapter;
    private int page = 1;
    private Handler handler = new Handler();
    //用来标记是否正在向最后一个滑动
    boolean isSlidingToLast = false;
    boolean isRefreshList = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        ButterKnife.bind(this);
        tv_name.setText("设计师模板");
        designers = new ArrayList();
        swiperefresh.setOnRefreshListener(this);
        initView();
        initData();
    }

    private void initView() {
        classifyadapter = new DesignerAdapter(this);
        recyclerview.setHasFixedSize(true);
        final StaggeredGridLayoutManager staggeredGrid = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGrid.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerview.setLayoutManager(staggeredGrid);
        recyclerview.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(3, this)));
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerview, int newState) {
                super.onScrollStateChanged(recyclerview, newState);
                staggeredGrid.invalidateSpanAssignments();
            }
        });
        recyclerview.setAdapter(classifyadapter);
    }

    private void initData() {
        UiUtils.startnet(this);
        ServeManager.getDesigner(this, MyApplication.mUser.getUid()+"",page+"").enqueue(new Callback<DesignerModel>() {
            @Override
            public void onResponse(Call<DesignerModel> call, Response<DesignerModel> response) {
                if (response.body() != null)
                    Log.e("DesignerActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (UiUtils.isList(response.body().getList())) {
                        //没有下一页
                        if (response.body().getList().size() < 10 && isSlidingToLast) {
                            recyclerview.setOnScrollListener(null);
                        } else if (isRefreshList) {
                            onScroll();
                            isRefreshList = false;
                        }
                        for (int i = 0; i < response.body().getList().size(); i++) {
                            designers.add(response.body().getList().get(i));
                        }
                        classifyadapter.setDate(designers);
                        classifyadapter.notifyDataSetChanged();
                        page++;
                    }else {
                        Toast.makeText(getApplicationContext(), "数据为空", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<DesignerModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("DesignerActivity", "onFailure:" + t.getLocalizedMessage());
                UiUtils.endnet();
            }
        });
    }
    @OnClick(R.id.iv_back)
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
        swiperefresh.setRefreshing(true);
        UiUtils.startnet(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefreshList = true;
                designers.clear();
                page = 1;
                initData();
                swiperefresh.setRefreshing(false);
            }
        }, 1000);
    }

    /**
     * On scroll.
     */
    private void onScroll() {
        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isSlidingToLast) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        Log.e("ClassifyActivity", "滑动到底了" + "==" + isSlidingToLast);
                        initData();
                    }

                }
            }

            //找到数组中的最大值
            private int findMax(int[] lastPositions) {
                int max = lastPositions[0];
                for (int value : lastPositions) {
                    if (value > max) {
                        max = value;
                    }
                }
                return max;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });
    }
}
