package com.wmcd.myb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.AccountAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.AccountModel;
import com.wmcd.myb.model.LoginModel;
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
 * 账单详情
 */
public class AccountDetailActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    /**
     * The Account lv.
     */
    @Bind(R.id.accout_lv)
    XListView accountLV;
    /**
     * The Account adapter.
     */
    private AccountAdapter accountAdapter;
    /**
     * The Current page.
     */
    private int currentPage = 1;
    /**
     * The Count.
     */
    private int count;
    /**
     * The Account list.
     */
    private List<AccountModel.ListAccountBean> accountList;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view.
     */
    private void initView() {
        ((TextView) findViewById(R.id.title_tv)).setText("账单详情");
        ((TextView) findViewById(R.id.account_tv)).setText(MyApplication.mUser == null || MyApplication.mUser.getBalance() == null ? "0" : (float) MyApplication.mUser.getBalance() / 100 + "");
        accountLV.setPullLoadEnable(true);
        accountLV.setXListViewListener(this);
        accountList = new ArrayList<AccountModel.ListAccountBean>();
        accountAdapter = new AccountAdapter(accountList, this);
        accountLV.setAdapter(accountAdapter);
        accountLV.findViewById(R.id.xlistview_footer_content).setVisibility(View.GONE);
        request();
    }

    /**
     * Request.
     */
    private void request() {
        UiUtils.startnet(this);
        ServeManager.getAccountInfo(this, MyApplication.mUser.getUid() + "", currentPage + "").enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                UiUtils.endnet();
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (response.body().getList() == null || response.body().getList().size() == 0) {
                        Toast.makeText(getApplicationContext(), "没有账单数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (currentPage == 1) {
                        accountList.clear();
                    }
                    count = response.body().getList().size();
                    accountList.addAll(response.body().getList());
                    accountAdapter.refresh(accountList);
                } else {
                    Toast.makeText(getApplicationContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * On load more.
     */
    @Override
    public void onLoadMore() {
        if (count < 10) {
            Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_LONG).show();
            return;
        }
        currentPage++;
        request();
        accountLV.stopLoadMore();
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.back_iv, R.id.cash_tv})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.cash_tv:
                if (MyApplication.mUser == null && MyApplication.mUser.getBalance() == 0)
                    return;
                startActivityForResult(new Intent(this, WithdrawActivity.class), 2011);
                break;
        }
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
        if (requestCode == 2011) {
            ServeManager.getUser(this, MyApplication.mUser.getUid() + "").enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.body() != null) {
                        Log.e("CenterFragment", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                    }
                    if (response.body() != null && "01".equals(response.body().getResult())) {
                        MyApplication.mUser = response.body().getUser();
                        initView();
                    } else {
                        Toast.makeText(AccountDetailActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                    UiUtils.endnet();
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Toast.makeText(AccountDetailActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    UiUtils.endnet();
                }
            });
        }
    }

    /**
     * On refresh.
     */
    @Override
    public void onRefresh() {
        currentPage = 1;
        request();
        accountLV.stopRefresh();
    }
}
