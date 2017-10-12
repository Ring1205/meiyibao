package com.wmcd.myb.fragment.catrgoryitem;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.NextPreviewActivity;
import com.wmcd.myb.activity.SearchActivity;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.RecommendModel;
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
 * Created by Administrator on 2017/6/14 0014.
 */

public class ChoiceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rv_choice)
    RecyclerView rvChoice;
    @Bind(R.id.iv_publicity_icon)
    ImageView ivPublicityIcon;
    @Bind(R.id.search_publicity)
    ImageView searchPublicity;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private List<RecommendModel.RecommendTemplatesBean> data;
    private List<RecommendModel.RecommendTemplatesBean.RecommendTemplateListBean> datainfo;
    private RecommendModel.RecommendTemplatesBean.RecommendTemplateListBean flag;
    private MyAdapter adapter;


    @Override
    protected void initView() {
        flag = new RecommendModel.RecommendTemplatesBean.RecommendTemplateListBean();
        flag.setName("flag");
        data = new ArrayList<>();
        datainfo = new ArrayList<>();
        swiperefresh.setOnRefreshListener(this);
        searchPublicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        data = null;
        datainfo.clear();
        datainfo.add(flag);
        getData();
    }

    private void getData() {
        UiUtils.startnet(context);
        ServeManager.getRecommend(context).enqueue(new Callback<RecommendModel>() {
            @Override
            public void onResponse(Call<RecommendModel> call, Response<RecommendModel> response) {
                if (response.body() != null)
                    Log.e("CategoryFragment", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    data = response.body().getRecommendTemplates();
                    for (int i = 0; i < data.size(); i++) {
                        datainfo.addAll(data.get(i).getRecommendTemplateList());

                        if (i < data.size() - 1)
                            datainfo.add(flag);
                    }
                    showView();
                } else {
                    Toast.makeText(context.getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<RecommendModel> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                UiUtils.endnet();
            }
        });
    }


    private void showView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        Log.i("datainfo", "---" + data.size());
        rvChoice.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            //返回position对应的条目所占的size
            @Override
            public int getSpanSize(int position) {
                //istitle
                if (datainfo.get(position).getName().equals("flag"))

                    return 2;
                else
                    //iscontent
                    return 1;

            }
        });
        rvChoice.addItemDecoration(new SpaceItemDecoration(5));
        adapter = new MyAdapter(context, datainfo);
        rvChoice.setAdapter(adapter);
        UiUtils.endnet();
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_choice;
    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        swiperefresh.setRefreshing(false);
    }

    class MyAdapter extends RecyclerView.Adapter {

        private Context context;
        private List<RecommendModel.RecommendTemplatesBean.RecommendTemplateListBean> data = new ArrayList<>();

        public MyAdapter(Context context, List<RecommendModel.RecommendTemplatesBean.RecommendTemplateListBean> data) {
            this.context = context;
            this.data = data;

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            //根据viewType生成viewHolder
            switch (viewType) {
                case 0:
                    viewHolder = new MyHolderTitle(View.inflate(context, R.layout.category_title_item1, null));
                    break;
                case 1:
                    viewHolder = new MyHolder(View.inflate(context, R.layout.category_img_item, null));
                    break;
            }
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            //根据条目的类型给holder中的控件填充数据
            int itemViewType = getItemViewType(position);

            switch (itemViewType) {
                //标题
                case 0:
                    MyHolderTitle holder0 = (MyHolderTitle) holder;
                    if (datainfo.get(position + 1).getType() == 3) {
                        holder0.tv_title.setText("热门头像");
                    }
                    if (datainfo.get(position + 1).getType() == 4) {
                        holder0.tv_title.setText("热门海报");
                    }
                    if (datainfo.get(position + 1).getType() == 5) {
                        holder0.tv_title.setText("热门画册");
                    }

                    break;
                case 1:
                    MyHolder holder1 = (MyHolder) holder;
                    holder1.iv_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, NextPreviewActivity.class);
                            intent.putExtra("tid", datainfo.get(position).getTid());
                            context.startActivity(intent);
                        }
                    });

                    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    int w = (int) (windowManager.getDefaultDisplay().getWidth() - UiUtils.dpToPixels(21, context)) / 2;

                    UiUtils.loadImage(context, UrlConfig.IMG + data.get(position).getIcon(), holder1.iv_item, w);
                    ViewGroup.LayoutParams layoutParams = holder1.iv_item.getLayoutParams();
                    layoutParams.width = w;
                    layoutParams.height = (int) (w * (float) data.get(position).getHeight() / (float) data.get(position).getWidth());
                    UiUtils.loadImage(context, UrlConfig.IMG + data.get(position).getMicon(), holder1.iv_login, (int) UiUtils.dpToPixels(20, context));
                    break;

            }
        }

        @Override
        public int getItemViewType(int position) {
            //跟据position对应的条目返回去对应的样式（Type）
            if (datainfo.get(position).getName().equals("flag")) {
                //istitle
                return 0;
            } else
                //iscontent
                return 1;
        }

        @Override
        public int getItemCount() {
            return datainfo.size();//data ==null?0:data.size()+datainfo.size();

        }


        class MyHolderTitle extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_title)
            TextView tv_title;

            public MyHolderTitle(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        class MyHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_item)
            ImageView iv_item;

            @Bind(R.id.iv_logo)
            ImageView iv_login;


            public MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    @OnClick(R.id.iv_publicity_icon)
    public void onClick() {
        listener.open();
    }

}

