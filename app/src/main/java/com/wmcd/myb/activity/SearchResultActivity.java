package com.wmcd.myb.activity;

import android.app.Activity;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.ClassifyAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.model.ClassifyModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.InputMethodUtils;
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
 * Created by zlf on 2017/4/10.
 */
public class SearchResultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * The Recycler view.
     */
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    /**
     * The Swiperefresh.
     */
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    /**
     * The Search et.
     */
    @Bind(R.id.search_et)
    EditText searchET;


    /**
     * The Classifyadapter.
     */
    private ClassifyAdapter classifyadapter;
    /**
     * The Data.
     */
    private List<ClassifyModel.ListBean> data = new ArrayList<>();
    /**
     * The Handler.
     */
    private Handler handler = new Handler();
    /**
     * The Page.
     */
    private int page = 1;
    /**
     * The Is sliding to last.
     */
//用来标记是否正在向最后一个滑动
    boolean isSlidingToLast = false;
    /**
     * The Is refresh list.
     */
    boolean isRefreshList = false;
    /**
     * The Key.
     */
    private String key;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view.
     */
    private void initView() {
        UiUtils.startnet(this);
        key = getIntent().getStringExtra("key");
        if (getIntent().getStringExtra("banner")!=null)
            searchET.setText(key);
        classifyadapter = new ClassifyAdapter(this);
        recyclerView.setHasFixedSize(true);
        final StaggeredGridLayoutManager staggeredGrid = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGrid);
        recyclerView.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(3, this)));
        recyclerView.setAdapter(classifyadapter);
        onScroll();
        swiperefresh.setOnRefreshListener(this);
        initData();
        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if ("".equals(searchET.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "请输入关键字", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        if (getCurrentFocus() != null) {
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        UiUtils.startnet(SearchResultActivity.this);
                        key = searchET.getText().toString().trim();
                        isRefreshList = true;
                        data.clear();
                        page = 1;
                        initData();
                        swiperefresh.setRefreshing(false);
                    }
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * On scroll.
     */
    private void onScroll() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当前RecyclerView显示出来的最后一个的item的position
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
                        UiUtils.startnet(SearchResultActivity.this);
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


    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick(R.id.cancle_tv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancle_tv:
                if (searchET.getText().length()==0){
                    finish();
                    InputMethodUtils.hideSoftInput(this);
                }else {
                    searchET.setText("");
                }
                break;
        }
    }

    /**
     * Init data.
     */
    private void initData() {
        ServeManager.search(this, key, page + "").enqueue(new Callback<ClassifyModel>() {
            @Override
            public void onResponse(Call<ClassifyModel> call, Response<ClassifyModel> response) {
                UiUtils.endnet();
                Log.e("searchactivity","key"+key);
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (response.body().getList() == null || response.body().getList().size() == 0) {
                        Toast.makeText(getApplicationContext(), "没有数据", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (response.body().getList().size() < 10 && isSlidingToLast) {
                        recyclerView.setOnScrollListener(null);
                    } else if (isRefreshList) {
                        onScroll();
                        isRefreshList = false;
                    }
                    if (UiUtils.isList(response.body().getList())) {
                        for (int i = 0; i < response.body().getList().size(); i++) {
                            data.add(response.body().getList().get(i));
                        }
                        classifyadapter.setDate(data);
                        classifyadapter.notifyDataSetChanged();
                        page++;
                    }
                } else {
                    Toast.makeText(SearchResultActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClassifyModel> call, Throwable t) {
                Toast.makeText(SearchResultActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                UiUtils.endnet();
            }
        });
    }

    /**
     * Start classify activity.
     *
     * @param context the context
     * @param type    the type
     */
    public void startClassifyActivity(Activity context, String type) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("key", type);
        context.startActivity(intent);
    }

    /**
     * On refresh.
     */
    @Override
    public void onRefresh() {
        swiperefresh.setRefreshing(true);
        UiUtils.startnet(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefreshList = true;
                data.clear();
                page = 1;
                initData();
                swiperefresh.setRefreshing(false);
            }
        }, 2000);
    }
}
