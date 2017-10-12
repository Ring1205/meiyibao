package com.wmcd.myb.fragment.catrgoryitem;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.DocActivity;
import com.wmcd.myb.activity.DocDetailActivity;
import com.wmcd.myb.activity.LoginActivity;
import com.wmcd.myb.activity.MainTopActivity;
import com.wmcd.myb.activity.SearchActivity;
import com.wmcd.myb.adapter.DocAdapter;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.DocModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.wigdet.XListView;

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

public class TableFragment extends BaseFragment implements XListView.IXListViewListener, View.OnClickListener {
    /**
     * The Account lv.集成了下拉刷新和上拉加载更多的listView
     */
    @Bind(R.id.accout_lv)
    XListView accountLV;
    @Bind(R.id.iv_publicity_icon)
    ImageView ivPublicityIcon;
    @Bind(R.id.search_publicity)
    ImageView searchPublicity;
    /**
     * The Doc adapter.XListView适配器
     */
    private DocAdapter docAdapter;
    /**
     * The Current page.当前的页面
     */
    private int currentPage = 1;
    /**
     * The Count.文档的数量
     */
    private int count;
    /**
     * The Doc beann list.实体类
     */
    private List<DocModel.ListDocBean> docBeannList;

    @Override
    protected void initView() {
        currentPage = 1;
        accountLV.setPullLoadEnable(true);
        accountLV.setXListViewListener(this);
        docBeannList = new ArrayList<>();
        docAdapter = new DocAdapter(docBeannList, context);
        accountLV.setAdapter(docAdapter);
        accountLV.findViewById(R.id.xlistview_footer_content).setVisibility(View.GONE);
        request();

        accountLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //判断是否登录 是否会员
                if (MyApplication.mUser == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else if (MyApplication.mUser.getMid() == null) {
                    Intent intent = new Intent(context, MainTopActivity.class);
                    intent.putExtra("member", true);
                    startActivity(intent);
                } else if (MyApplication.mUser.getMid() == 3) {
                    Intent intent = new Intent(context, DocDetailActivity.class);
                    intent.putExtra("docBean", docBeannList.get(position - 1));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(context, MainTopActivity.class);
                    intent.putExtra("member", true);
                    startActivity(intent);
                }
            }
        });
        searchPublicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchActivity.class));
            }
        });
    }

    /**
     * Request.请求数据
     */
    private void request() {
        UiUtils.startnet(context);
        ServeManager.getDocList(context, currentPage + "").enqueue(new Callback<DocModel>() {
            @Override
            public void onResponse(Call<DocModel> call, Response<DocModel> response) {
                if (response.body() != null)
                    Log.e(DocActivity.class.getName(), "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                // Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (response.body().getDocList() == null || response.body().getDocList().size() == 0) {
                        Toast.makeText(context, "没有数据了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (currentPage == 1 && docBeannList != null && docBeannList.size() > 0) {
                        docBeannList.clear();
                    }
                    count = response.body().getDocList().size();
                    docBeannList.addAll(response.body().getDocList());
                    docAdapter.refresh(docBeannList);
                } else {
                    Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<DocModel> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * On load more.上拉加载更多
     */
    @Override
    public void onLoadMore() {
        //count<10说明没有下一页了
        if (count < 10) {
            Toast.makeText(context, "没有更多数据", Toast.LENGTH_LONG).show();
            return;
        }
        currentPage++;
        request();
        accountLV.stopLoadMore();
    }

    /**
     * On refresh. 下拉刷新
     */
    @Override
    public void onRefresh() {
        currentPage = 1;
        request();
        accountLV.stopRefresh();
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_table;
    }

    @Override
    public void onClick(View v) {

    }


    @OnClick(R.id.iv_publicity_icon)
    public void onClick() {
        listener.open();
    }
}
