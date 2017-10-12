package com.wmcd.myb.fragment.catrgoryitem;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.SearchActivity;
import com.wmcd.myb.adapter.ClassifyAdapter;
import com.wmcd.myb.adapter.IndustryTitleAdapter;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.ClassifyModel;
import com.wmcd.myb.model.IndustryModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.wigdet.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class CouponFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.iv_publicity_icon)
    ImageView ivPublicityIcon;
    @Bind(R.id.search_publicity)
    ImageView searchPublicity;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * The Page.
     */
    private int page = 1;

    private String ttid = "12";
    /**
     * The Is sliding to last.
     */
    boolean isSlidingToLast = false;
    /**
     * The Is refresh list.
     */
    boolean isRefreshList = false;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.select_title)
    RecyclerView select_title;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private List<IndustryModel.ListBean> datas;
    IndustryModel.ListBean firsttitle;

    @Override
    protected void initView() {
        data.clear();
        currentindustry = "";
        datas = new ArrayList<>();
        firsttitle = new IndustryModel.ListBean();
        firsttitle.setIndustryname("全部");
        firsttitle.setIsselect(true);
        //-1 means all
        firsttitle.setIndustryid(-1);
        datas.add(0, firsttitle);


        classifyadapter = new ClassifyAdapter(context);
        recyclerview.setHasFixedSize(true);
        onScroll();

        getIndustry();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(3, context)));
        recyclerview.setAdapter(classifyadapter);
        swiperefresh.setOnRefreshListener(this);
        searchPublicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchActivity.class));
            }
        });

    }

    private void getData(String currentindustry1) {

        UiUtils.startnet(context);
        ServeManager.getClassify(context, MyApplication.mUser == null ? "" : MyApplication.mUser.getUid() + "", ttid, page + "", "", "", "", currentindustry1).enqueue(new Callback<ClassifyModel>() {
            @Override
            public void onResponse(Call<ClassifyModel> call, Response<ClassifyModel> response) {
                if (response.body() != null)
                    Log.e("ClassifyActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg() + "===" + response.body().getList().size() + "==" + page + "===" + isRefreshList);
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    //没有下一页
                    if (response.body().getList().size() < 10 && isSlidingToLast) {
                        recyclerview.setOnScrollListener(null);
                    } else if (isRefreshList) {
                        onScroll();
                        isRefreshList = false;
                    }
                    if (UiUtils.isList(response.body().getList())) {
                        data.addAll(response.body().getList());
                        classifyadapter.setDate(data);
                        classifyadapter.notifyDataSetChanged();
                        page++;
                    }
                } else {
                    Toast.makeText(context, "网络加载失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<ClassifyModel> call, Throwable t) {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "onFailure:" + t.getLocalizedMessage());
                UiUtils.endnet();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        currentindustry = "";


    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
    }

    @Override
    protected void initData() {

        getData(currentindustry);

    }

    /*
    * get industry
    * */
    private IndustryTitleAdapter mIndustryTitleAdapter;

    private void getIndustry() {
        select_title.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        ServeManager.getIndustry(context, ttid).enqueue(new Callback<IndustryModel>() {
            @Override
            public void onResponse(Call<IndustryModel> call, Response<IndustryModel> response) {
                if (response.body() != null) {
                    datas.addAll(response.body().getList());
                    mIndustryTitleAdapter = new IndustryTitleAdapter(context, datas);
                    select_title.setAdapter(mIndustryTitleAdapter);
                    mIndustryTitleAdapter.setOnMyItemClickListener(new IndustryTitleAdapter.OnMyItemClickListener() {
                        @Override
                        public void myClick(int industryid) {
                            data.clear();
                            page = 1;
                            if (industryid == -1) {
                                currentindustry = "";
                                getData(currentindustry);
                            } else {
                                currentindustry = industryid + "";
                                getData(currentindustry);
                            }
                        }
                    });
                }else
                {
                    Toast.makeText(context,"没有数据",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<IndustryModel> call, Throwable t) {
                Toast.makeText(context,"网络异常，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void onScroll() {
        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private String currentindustry = "";

    @Override
    public void onRefresh() {
        swiperefresh.setRefreshing(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefreshList = true;
                data.clear();
                page = 1;
                getData(currentindustry);
                swiperefresh.setRefreshing(false);
            }
        }, 0);
    }


    @OnClick(R.id.iv_publicity_icon)
    public void onClick() {
        listener.open();
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_coupon;
    }


}
