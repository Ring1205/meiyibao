package com.wmcd.myb.fragment.catrgoryitem;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.SearchActivity;
import com.wmcd.myb.adapter.CenterTemplateAdapter;
import com.wmcd.myb.adapter.PropagandaAdapter;
import com.wmcd.myb.adapter.PublicityAdapter;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.model.PropagandaModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.wigdet.SpaceItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/6/14 0014.
 */
public class PublicityFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rv_festival)
    RecyclerView rvFestival;
    @Bind(R.id.iv_publicity_icon)
    ImageView ivPublicityIcon;
    @Bind(R.id.search_publicity)
    ImageView searchPublicity;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private PropagandaAdapter propagandaAdapter;
    private List<PropagandaModel.TemplateListBean> templateList;
    private PublicityAdapter adapter;

    @Override
    protected void initView() {
        propagandaAdapter = new PropagandaAdapter(context);
        rvFestival.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvFestival.setAdapter(propagandaAdapter);
        searchPublicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchActivity.class));
            }
        });

        swiperefresh.setOnRefreshListener(this);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL));
        recyclerview.addItemDecoration(new SpaceItemDecoration(UiUtils.dip2px(context, 2)));
        adapter = new PublicityAdapter(context);
        recyclerview.setAdapter(adapter);

    }

    @Override
    protected void initData() {

        getTitle();
        getContent();


    }

    private void getContent() {

    }

    private void getTitle() {

        ServeManager.getPropaganda(context).enqueue(new Callback<PropagandaModel>() {
            @Override
            public void onResponse(Call<PropagandaModel> call, Response<PropagandaModel> response) {
                if (response.body().getList() != null) {
                    propagandaAdapter.getData(response.body().getList());

                    propagandaAdapter.notifyDataSetChanged();
                    templateList = response.body().getTemplateList();
                    adapter.setData(templateList);
                    adapter.notifyDataSetChanged();

                } else {

                }

            }

            @Override
            public void onFailure(Call<PropagandaModel> call, Throwable t) {

                Toast.makeText(context,"网络异常，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_publicity;
    }


    @OnClick(R.id.iv_publicity_icon)
    public void onClick() {
        listener.open();
    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        swiperefresh.setRefreshing(false);
    }
}
