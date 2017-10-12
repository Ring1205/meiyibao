package com.wmcd.myb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.NoticeAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.model.ResultModel;
import com.wmcd.myb.model.SystemMsgBean;
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
 * Created by Administrator on 2017/4/11 0011. 消息中心
 */
public class NoticeListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    /**
     * The Sys vw.系统通知
     */
    @Bind(R.id.sys_vw)
    View sysVW;
    /**
     * The Zc vw.资产消息
     */
    @Bind(R.id.zc_vw)
    View zcVW;
    /**
     * The Sys lv.系统通知的XlistView
     */
    @Bind(R.id.sys_lv)
    XListView sysLV;
    /**
     * The Assets lv.资产消息的XlistView
     */
    @Bind(R.id.assets_lv)
    XListView assetsLv;
    /**
     * The Sys count tv. 系统通知的数量
     */
    @Bind(R.id.sys_count_tv)
    TextView sysCountTV;
    /**
     * The Zc count tv.资产消息的数量
     */
    @Bind(R.id.zc_count_tv)
    TextView zcCountTV;
    /**
     * The Sys adapter.
     */
    private NoticeAdapter sysAdapter, /**
     * The Asserts adapter.
     */
    assertsAdapter;
    /**
     * The Sys list.系统消息的bean
     */
    private List<SystemMsgBean.ListBean> sysList, /**
     * The Asserts list.
     */
    assertsList;
    /**
     * The Sys current page.
     */
    private int sysCurrentPage = 1, /**
     * The Asserts current page.
     */
    assertsCurrentPage = 1;
    /**
     * The Sys count.
     */
    private int sysCount, /**
     * The Asserts count.
     */
    assertsCount;
    /**
     * The Type.
     */
    private String type = "1";
    /**
     * The Sys num.
     */
    private int sysNum, /**
     * The Assert num.
     */
    assertNum;
    /**
     * The Is assignment.
     */
    private boolean isAssignment = false;
    /**
     * The Cur system msg.
     */
    private SystemMsgBean.ListBean curSystemMsg;
    /**
     * The constant NOTICE_LIST_ACTIVITY_CODE.
     */
    public static final int NOTICE_LIST_ACTIVITY_CODE = 140;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * On resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (type=="1")
            request();
        else
            requestAssert();
    }

    /**
     * Init view.
     */
    private void initView() {
        ((TextView) findViewById(R.id.title_tv)).setText("消息中心");
        sysLV.setPullLoadEnable(true);
        sysLV.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                sysCurrentPage = 1;
                request();
                sysLV.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (sysCount < 10) {
                    Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_LONG).show();
                    return;
                }
                sysCurrentPage++;
                request();
                sysLV.stopLoadMore();
            }
        });
        sysList = new ArrayList<SystemMsgBean.ListBean>();
        sysAdapter = new NoticeAdapter(sysList, this);
        sysLV.setAdapter(sysAdapter);
        sysLV.findViewById(R.id.xlistview_footer_content).setVisibility(View.GONE);
        sysLV.setOnItemClickListener(this);
        assetsLv.setPullLoadEnable(true);
        assetsLv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                assertsCurrentPage = 1;
                requestAssert();
                assetsLv.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (assertsCount < 10) {
                    Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_LONG).show();
                    return;
                }
                assertsCurrentPage++;
                requestAssert();
                assetsLv.stopLoadMore();
            }
        });
        assertsList = new ArrayList<SystemMsgBean.ListBean>();
        assertsAdapter = new NoticeAdapter(assertsList, this);
        assetsLv.setAdapter(assertsAdapter);
        assetsLv.findViewById(R.id.xlistview_footer_content).setVisibility(View.GONE);
        assetsLv.setOnItemClickListener(this);
        request();
    }

    /**
     * On item click.
     *
     * @param adapterView the adapter view
     * @param view        the view
     * @param position    the position
     * @param l           the l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this, NoticeDetailActivity.class);
        switch (adapterView.getId()) {
            case R.id.sys_lv:
                curSystemMsg = sysList.get(position - 1);
                intent.putExtra("system", curSystemMsg);
                break;
            case R.id.assets_lv:
                curSystemMsg = assertsList.get(position - 1);
                intent.putExtra("system", curSystemMsg);
                break;
        }
        startActivityForResult(intent, NOTICE_LIST_ACTIVITY_CODE);
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTICE_LIST_ACTIVITY_CODE && resultCode == RESULT_OK) {
            updateMsg(curSystemMsg.getMsgid() + "");
        }

    }

    /**
     * Update msg. 刷新消息
     *
     * @param id the id 消息的类型
     */
    private void updateMsg(String id) {
        UiUtils.startnet(this);
        ServeManager.updateMessage(this, id).enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (type.equals("1")) {
                        curSystemMsg.setRead(1);
                        sysAdapter.refresh(sysList,type=="1");
                    } else if (type.equals("2")) {
                        curSystemMsg.setRead(1);
                        assertsAdapter.refresh(assertsList,type=="1");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Request.请求系统消息的数据
     */
    private void request() {
        UiUtils.startnet(this);
        ServeManager.queryMessage(this, "1", type, sysCurrentPage + "").enqueue(new Callback<SystemMsgBean>() {
            @Override
            public void onResponse(Call<SystemMsgBean> call, Response<SystemMsgBean> response) {
                UiUtils.endnet();
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (response.body().getList() == null || response.body().getList().size() == 0) {
                        Toast.makeText(getApplicationContext(), "没有系统通知数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!isAssignment) {

                    }
                    refreshNoticesCount(response);
                    isAssignment = true;
                    if (sysCurrentPage == 1) {
                        sysList.clear();
                    }
                    sysCount = response.body().getList().size();
                    sysList.addAll(response.body().getList());
                    sysAdapter.refresh(sysList,type=="1");
                } else {
                    Toast.makeText(getApplicationContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SystemMsgBean> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Refresh notices count. 刷新消息数量
     *
     * @param response the response
     */
    private void refreshNoticesCount(Response<SystemMsgBean> response) {
//        response.body().setAssetsMessageUnreadCount(2);
//        response.body().setSystemMessageUnreadCount(3);
        if (response.body().getSystemMessageUnreadCount() > 0) {
            sysCountTV.setVisibility(View.VISIBLE);
            sysCountTV.setText(response.body().getSystemMessageUnreadCount() + "");
        } else {
            sysCountTV.setVisibility(View.GONE);
        }
        if (response.body().getAssetsMessageUnreadCount() > 0) {
            zcCountTV.setVisibility(View.VISIBLE);
            zcCountTV.setText(response.body().getAssetsMessageUnreadCount() + "");
        } else {
            zcCountTV.setVisibility(View.GONE);
        }
    }

    /**
     * Request assert. 请求资产消息的数据
     */
    private void requestAssert() {
        UiUtils.startnet(this);
        ServeManager.queryMessage(this, "1", type, sysCurrentPage + "").enqueue(new Callback<SystemMsgBean>() {
            @Override
            public void onResponse(Call<SystemMsgBean> call, Response<SystemMsgBean> response) {
                UiUtils.endnet();
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (response.body().getList() == null || response.body().getList().size() == 0) {
                        Toast.makeText(getApplicationContext(), "没有系统通知数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (assertsCurrentPage == 1) {
                        assertsList.clear();
                    }
                    refreshNoticesCount(response);
                    assertsCount = response.body().getList().size();
                    assertsList.addAll(response.body().getList());
                    assertsAdapter.refresh(assertsList,type=="1");
                } else {
                    Toast.makeText(getApplicationContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SystemMsgBean> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.back_iv, R.id.sys_rl, R.id.assets_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.sys_rl:
                type = "1";
                sysLV.setVisibility(View.VISIBLE);
                sysVW.setVisibility(View.VISIBLE);
                assetsLv.setVisibility(View.GONE);
                zcVW.setVisibility(View.GONE);
                if (sysList.size() == 0) {
                    request();
                }
                break;
            case R.id.assets_rl:
                type = "2";
                sysLV.setVisibility(View.GONE);
                sysVW.setVisibility(View.GONE);
                assetsLv.setVisibility(View.VISIBLE);
                zcVW.setVisibility(View.VISIBLE);
                if (assertsList.size() == 0) {
                    requestAssert();
                }
                break;
        }
    }
}
