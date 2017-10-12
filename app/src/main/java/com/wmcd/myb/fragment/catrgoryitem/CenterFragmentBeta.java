package com.wmcd.myb.fragment.catrgoryitem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.AccountDetailActivity;
import com.wmcd.myb.activity.InformationInputActivity;
import com.wmcd.myb.activity.SetActivity;
import com.wmcd.myb.adapter.CenterTemplateAdapter;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.LoginModel;
import com.wmcd.myb.model.TemplateByUserModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.ImageType;
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
 * Created by Administrator on 2017/6/24 0024.
 */

public class CenterFragmentBeta extends BaseFragment {
    /**
     * The Iv head.
     */
    @Bind(R.id.iv_head)
    ImageView iv_head;//头像
    @Bind(R.id.ll_not_member)
    LinearLayout ll_not_member;//not member


    @Bind(R.id.iv_vip_icon)
    ImageView iv_vip_icon;//member icon
    @Bind(R.id.tv_invite_code)
    TextView tv_invite_code;//member icon
    /**
     * The Tv name.
     */
    @Bind(R.id.tv_name)
    TextView tv_name;//名字
    /**
     * The Iv no vip.
     */
    @Bind(R.id.iv_no_vip)
    ImageView iv_no_vip;//没有vip
    /**
     * The Recycler view.
     */
    @Bind(R.id.rl_tem)
    RecyclerView recyclerView;//模板
    /**
     * The Tv mname.
     */
    @Bind(R.id.tv_mname)
    TextView tv_mname;
    @Bind(R.id.iv_selector_line)
    ImageView ivSelectorLine;
    @Bind(R.id.iv_selector_line2)
    ImageView ivSelectorLine2;
    @Bind(R.id.rl_wallet)
    RelativeLayout rlWallet;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_power_time_dec)
    TextView tvPowerTimeDec;
    @Bind(R.id.tv_right_dec)
    TextView tvRightDec;
    @Bind(R.id.iv_look_list)
    ImageView ivLookList;
    @Bind(R.id.tv_look_list)
    TextView tvLookList;

    /**
     * The Userbean.
     */
    private LoginModel.UserBean userbean;
    /**
     * The Adapter.
     */
    private CenterTemplateAdapter adapter;
    /**
     * The List.
     */
    private List<TemplateByUserModel.ListBean> list = new ArrayList<>();

    /**
     * Sets layout resouce id.
     *
     * @return the layout resouce id
     */
    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_center_beta;
    }

    /**
     * Init data.
     */
    @Override
    public void initData() {
        ServeManager.getTemplateByUser(context, MyApplication.mUser.getUid() + "").enqueue(new Callback<TemplateByUserModel>() {
            @Override
            public void onResponse(Call<TemplateByUserModel> call, Response<TemplateByUserModel> response) {
                if (response.body() != null) {
                    Log.e("CenterFragment", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                }
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (UiUtils.isList(response.body().getList())) {
                        list.clear();
                        //recyclerView.setVisibility(View.VISIBLE);
                        TemplateByUserModel.ListBean model;
                        for (int i = 0; i < response.body().getList().size(); i++) {
                            model = response.body().getList().get(i);
                            list.add(model);
                        }
                        adapter.setData(list);
                        adapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TemplateByUserModel> call, Throwable t) {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Init view.
     */
    @Override
    protected void initView() {
        userbean = MyApplication.mUser;
        //is member

        if (userbean.getMid() != null) {

            //user name
            tv_name.setText(userbean.getName());
            //user head icon
            UiUtils.loadImage(context, UrlConfig.IMG + userbean.getIconR(), iv_head, ImageType.CIRCLE);

            //money
            tvMoney.setText((double) userbean.getBalance() / 100 + "");//钱
            //time
            tvPowerTimeDec.setText(userbean.getBeginTime().substring(0, userbean.getBeginTime().indexOf(" ")) + "-" + userbean.getEndTime().substring(0, userbean.getEndTime().indexOf(" ")));
            //right intro
            tvRightDec.setText(userbean.getMdesc());
            iv_vip_icon.setVisibility(View.VISIBLE);
            //icon
            UiUtils.loadImage(context, UrlConfig.IMG + userbean.getMicon(), iv_vip_icon, 0);
            ll_not_member.setVisibility(View.GONE);

            tv_invite_code.setVisibility(View.VISIBLE);
            // invitecode
            tv_invite_code.setText("邀请码：" + userbean.getInvitationCode());


        } else {
            iv_vip_icon.setVisibility(View.GONE);
            ll_not_member.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpaceItemDecoration(UiUtils.dip2px(context, 2)));
        adapter = new CenterTemplateAdapter(context);
        recyclerView.setAdapter(adapter);

    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_set, R.id.iv_no_vip, R.id.iv_head, R.id.template, R.id.wallet,R.id.iv_look_list, R.id.tv_look_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_set:   //设置
                startActivity(new Intent(context, SetActivity.class));
                break;
            case R.id.iv_no_vip:
                break;
            case R.id.template:
                recyclerView.setFocusable(false);
                ivSelectorLine2.setVisibility(View.VISIBLE);
                ivSelectorLine.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                rlWallet.setVisibility(View.GONE);
                break;
            case R.id.wallet:
                ivSelectorLine.setVisibility(View.VISIBLE);
                ivSelectorLine2.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                rlWallet.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_look_list:
                startActivityForResult(new Intent(context, AccountDetailActivity.class),2011);
                break;
            case R.id.tv_look_list:
                startActivityForResult(new Intent(context, AccountDetailActivity.class),2011);
                break;
            case R.id.iv_head:
                context.startActivity(new Intent(context, InformationInputActivity.class));
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2011) {
            UiUtils.startnet(context);
            ServeManager.getUser(context, MyApplication.mUser.getUid() + "").enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.body() != null) {
                        Log.e("CenterFragment", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                    }
                    if (response.body() != null && "01".equals(response.body().getResult())) {
                        MyApplication.mUser = response.body().getUser();
                        initView();
                    } else {
                        Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                    UiUtils.endnet();
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                    UiUtils.endnet();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
