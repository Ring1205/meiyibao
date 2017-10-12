package com.wmcd.myb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.DocAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.DocModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.wigdet.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 文档版本
 */
public class DocActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    /**
     * The Account lv.集成了下拉刷新和上拉加载更多的listView
     */
    @Bind(R.id.accout_lv)
    XListView accountLV;
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

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view.初始化View
     */
    private void initView() {
        ((TextView) findViewById(R.id.title_tv)).setText("文档模板");
        accountLV.setPullLoadEnable(true);
        accountLV.setXListViewListener(this);
        docBeannList = new ArrayList<DocModel.ListDocBean>();
        docAdapter = new DocAdapter(docBeannList, this);
        accountLV.setAdapter(docAdapter);
        accountLV.findViewById(R.id.xlistview_footer_content).setVisibility(View.GONE);
        request();

        accountLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //判断是否登录 是否会员
                if (MyApplication.mUser == null) {
                    Intent intent = new Intent(DocActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (MyApplication.mUser.getMid() == null) {
                    Intent intent = new Intent(DocActivity.this, MainTopActivity.class);
                    intent.putExtra("member", true);
                    startActivity(intent);
                } else if (MyApplication.mUser.getMid() == 3){
                    Intent intent = new Intent(DocActivity.this, DocDetailActivity.class);
                    intent.putExtra("docBean", docBeannList.get(position - 1));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DocActivity.this, MainTopActivity.class);
                    intent.putExtra("member", true);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Request.请求数据
     */
    private void request() {
        UiUtils.startnet(this);
        ServeManager.getDocList(this, currentPage + "").enqueue(new Callback<DocModel>() {
            @Override
            public void onResponse(Call<DocModel> call, Response<DocModel> response) {
                if (response.body() != null)
                    Log.e(DocActivity.class.getName(), "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                // Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (response.body().getDocList() == null || response.body().getDocList().size() == 0) {
                        Toast.makeText(getApplicationContext(), "没有数据了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (currentPage == 1 && docBeannList != null && docBeannList.size() > 0) {
                        docBeannList.clear();
                    }
                    count = response.body().getDocList().size();
                    docBeannList.addAll(response.body().getDocList());
                    docAdapter.refresh(docBeannList);
                } else {
                    Toast.makeText(getApplicationContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<DocModel> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_LONG).show();
            return;
        }
        currentPage++;
        request();
        accountLV.stopLoadMore();
    }

    /**
     * On click.点击事件
     *
     * @param view the view
     */
    @OnClick(R.id.back_iv)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
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
}
