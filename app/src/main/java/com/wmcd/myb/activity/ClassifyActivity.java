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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.ClassifyAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.ClassifyModel;
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
 * Created by 邓志宏 on 2017/2/22.
 */
public class ClassifyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
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
     * The Swiperefresh.
     */
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    /**
     * The Type.
     */
    private String Type;
    /**
     * The Tagid.
     */
    private String Tagid;
    /**
     * The Sid.
     */
    private String sid;
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
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view.
     */
    private void initView() {
        UiUtils.startnet(this);
        Intent intent = getIntent();
        Type = intent.getStringExtra("Type");
        Tagid = intent.getStringExtra("tagid");
        sid = intent.getStringExtra("sid");
        String hotTagName = intent.getStringExtra("hotTagName");
        if (hotTagName!=null){
            tv_name.setText(hotTagName);
            initing(1);
            onScroll();

            swiperefresh.setOnRefreshListener(this);
            initData();
            return;
        }
        if (Type == null) {
            Type = "";
        }
        switch (Type) {
            case "3":
                tv_name.setText("头像模板");
                initing(3);
                break;
            case "4":
                tv_name.setText("海报模板");
                initing(4);
                break;
            case "5":
                tv_name.setText("画册模板");
                initting();
                break;
            case "6":
                tv_name.setText("横幅模板");
                initting();
                break;
            case "7":
                tv_name.setText("名片模板");
                initting();
                break;
            case "8":
                tv_name.setText("单页模板");
                initting();
                break;
            case "9":
                tv_name.setText("易拉宝模板");
                initing(9);
                break;
            case "13":
                tv_name.setText("会员");
                initing(1);
                break;
            case "12":
                tv_name.setText("优惠券");
                initing(1);
                break;
            case "11":
                tv_name.setText("线上名片");
                initing(1);
                break;
            case "14":
                tv_name.setText("对比图");
                initing(1);
                break;
            default:
                tv_name.setText("标签模版");
                initing(1);
                break;
        }
        onScroll();

        swiperefresh.setOnRefreshListener(this);
        initData();
    }

    /**
     * Initting.
     */
    public void initting() {
        classifyadapter = new ClassifyAdapter(this,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(2, this)));
        recyclerView.setAdapter(classifyadapter);
    }

    /**
     * On scroll.
     */
    private void onScroll() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
//                // 当不滚动时
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    //获取最后一个完全显示的ItemPosition
//                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
//                    int totalItemCount = manager.getItemCount();
//                    // 判断是否滚动到底部，并且是向下滚动
//                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
//                        //加载更多功能的代码
//                        page++;
//                    }
//                }
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
                        Log.e("ClassifyActivity", "滑动到底了" + "==" + isSlidingToLast);
                        UiUtils.startnet(ClassifyActivity.this);
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
     * Initing.瀑布流
     *
     * @param i
     */
    private void initing(int i) {
        classifyadapter = new ClassifyAdapter(this);
        recyclerView.setHasFixedSize(true);
        final StaggeredGridLayoutManager staggeredGrid = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGrid.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGrid);
        recyclerView.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(3, this)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGrid.invalidateSpanAssignments();
            }
        });
        Log.e("加载方式","瀑布流");
        recyclerView.setAdapter(classifyadapter);
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
        if (Tagid==null)
            Tagid ="";
        if (Type==null)
            Type="";
        if (sid==null)
            sid="";
        //Log.e("classify","Tagid"+Tagid+"uid-------"+MyApplication.mUser.getUid());
        ServeManager.getClassify(this, MyApplication.mUser==null?"":MyApplication.mUser.getUid()+"", Type, page + "", Tagid,sid,"","").enqueue(new Callback<ClassifyModel>() {
            @Override
            public void onResponse(Call<ClassifyModel> call, Response<ClassifyModel> response) {
                if (response.body() != null)
                    Log.e("ClassifyActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg() + "===" + response.body().getList().size() + "==" + page + "===" + isRefreshList);
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    //没有下一页
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
                    Toast.makeText(ClassifyActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<ClassifyModel> call, Throwable t) {
                Toast.makeText(ClassifyActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "onFailure:" + t.getLocalizedMessage());
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
    public static void startClassifyActivity(Activity context, String type) {
        Intent intent = new Intent(context, ClassifyActivity.class);
        intent.putExtra("Type", type);
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
