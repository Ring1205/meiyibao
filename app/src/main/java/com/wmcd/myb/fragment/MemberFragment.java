package com.wmcd.myb.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.wmcd.myb.activity.PayActivity;
import com.wmcd.myb.activity.SharedActivity;
import com.wmcd.myb.adapter.MemberAdapter;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.LoginModel;
import com.wmcd.myb.model.MemberModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.ImageType;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 会员管理
 * Created by 邓志宏 on 2017/2/16.
 */
public class MemberFragment extends Fragment {
    /**
     * The Recyclerview.
     */
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    /**
     * The Linear layout 01.
     */
    @Bind(R.id.ll_01)
    LinearLayout linearLayout_01;
    /**
     * The Iv share.
     */
    @Bind(R.id.iv_share)
    ImageView iv_share;
    /**
     * The Iv head.
     */
    @Bind(R.id.iv_head)
    ImageView iv_head;
    /**
     * The Vip.
     */
    @Bind(R.id.vip)
    ImageView vip;
    /**
     * The Tv name.
     */
    @Bind(R.id.tv_name)
    TextView tv_name;
    /**
     * The Tv yaoqingma.
     */
    @Bind(R.id.tv_yaoqingma)
    TextView tv_yaoqingma;
    /**
     * The Iv member 01.
     */
    @Bind(R.id.iv_member_01)
    ImageView iv_member_01;
    /**
     * The Iv triangle 01.
     */
    @Bind(R.id.iv_triangle_01)
    ImageView iv_triangle_01;
    /**
     * The Iv member 02.
     */
    @Bind(R.id.iv_member_02)
    ImageView iv_member_02;
    /**
     * The Iv triangle 02.
     */
    @Bind(R.id.iv_triangle_02)
    ImageView iv_triangle_02;
    /**
     * The Iv member 03.
     */
    @Bind(R.id.iv_member_03)
    ImageView iv_member_03;
    /**
     * The Iv triangle 03.
     */
    @Bind(R.id.iv_triangle_03)
    ImageView iv_triangle_03;
    /**
     * The Tv money.
     */
    @Bind(R.id.tv_money)
    TextView tv_money;
    /**
     * The Tv youxiaoqi.
     */
    @Bind(R.id.tv_youxiaoqi)
    TextView tv_youxiaoqi;
    /**
     * The Tv quanyi.
     */
    @Bind(R.id.tv_quanyi)
    TextView tv_quanyi;
    /**
     * The Linear layout 02.
     */
    @Bind(R.id.ll_02)
    LinearLayout linearLayout_02;

    /**
     * The View 1.
     */
    @Bind(R.id.view)
    View view1;
    /**
     * The Tv qianbao.
     */
    @Bind(R.id.tv_qianbao)
    TextView tv_qianbao;
    /**
     * The Rl qianbao.
     */
    @Bind(R.id.rl_qianbao)
    RelativeLayout rl_qianbao;

    /**
     * The View.
     */
    private View view;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Member adapter.
     */
    private MemberAdapter memberAdapter;
    /**
     * The Data.
     */
    private List<MemberModel.ListBean> data;
    /**
     * The Userbean.
     */
    private LoginModel.UserBean userbean;

    /**
     * On create view view.
     *
     * @param inflater           the inflater
     * @param container          the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(context, R.layout.fragment_member, null);
            ButterKnife.bind(this, view);
            initView();
        }
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Init view.
     */
    private void initView() {
        userbean = MyApplication.mUser;
        if (userbean.getMid() != null){
            linearLayout_01.setVisibility(View.GONE);
            linearLayout_02.setVisibility(View.VISIBLE);
            UiUtils.loadImage(context, UrlConfig.IMG+userbean.getIconR(),iv_head, ImageType.CIRCLE);
            UiUtils.loadImage(context, UrlConfig.IMG+userbean.getMicon(),vip,0);
            tv_name.setText(userbean.getName());
            tv_yaoqingma.setText("邀请码："+userbean.getInvitationCode());
            if (userbean.getMid() == 1){
                iv_share.setVisibility(View.GONE);
                tv_yaoqingma.setVisibility(View.GONE  );
                iv_member_01.setImageResource(R.drawable.member_11);
                iv_triangle_01.setVisibility(View.VISIBLE);
                iv_member_02.setImageResource(R.drawable.member_02);
                iv_triangle_02.setVisibility(View.GONE);
                iv_member_03.setImageResource(R.drawable.member_03);
                iv_triangle_03.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                tv_qianbao.setVisibility(View.GONE);
                rl_qianbao.setVisibility(View.GONE);
            } else if (userbean.getMid() == 2){
                iv_share.setVisibility(View.GONE);
                tv_yaoqingma.setVisibility(View.GONE  );
                iv_member_01.setImageResource(R.drawable.member_01);
                iv_triangle_01.setVisibility(View.GONE);
                iv_member_02.setImageResource(R.drawable.member_21);
                iv_triangle_02.setVisibility(View.VISIBLE);
                iv_member_03.setImageResource(R.drawable.member_03);
                iv_triangle_03.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                tv_qianbao.setVisibility(View.GONE);
                rl_qianbao.setVisibility(View.GONE);
            } else if (userbean.getMid() == 3){
                iv_member_01.setImageResource(R.drawable.member_01);
                iv_triangle_01.setVisibility(View.GONE);
                iv_member_02.setImageResource(R.drawable.member_02);
                iv_triangle_02.setVisibility(View.GONE);
                iv_member_03.setImageResource(R.drawable.member_31);
                iv_triangle_03.setVisibility(View.VISIBLE);
            } else if (userbean.getMid() == 4){
                iv_member_01.setImageResource(R.drawable.member_01);
                iv_triangle_01.setVisibility(View.GONE);
                iv_member_02.setImageResource(R.drawable.member_02);
                iv_triangle_02.setVisibility(View.GONE);
                iv_member_03.setImageResource(R.drawable.member_31);
                iv_triangle_03.setVisibility(View.VISIBLE);
            }
            tv_money.setText("￥ "+(double)userbean.getBalance()/100);//钱
            tv_youxiaoqi.setText(userbean.getBeginTime().substring(0,userbean.getBeginTime().indexOf(" "))+"-"+userbean.getEndTime().substring(0,userbean.getEndTime().indexOf(" ")));
            tv_quanyi.setText(userbean.getMdesc());
        } else {
            linearLayout_01.setVisibility(View.VISIBLE);
            linearLayout_02.setVisibility(View.GONE);
            memberAdapter = new MemberAdapter(context);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            recyclerview.setAdapter(memberAdapter);
            memberAdapter.setMemberItemOnclick(new MemberAdapter.MemberItemOnclick() {
                @Override
                public void setItemOnclik(MemberModel.ListBean type) {
                    Intent intent = new Intent(context,PayActivity.class);
                    intent.putExtra("period",type.getPeriod());
                    intent.putExtra("price",type.getPrice());
                    intent.putExtra("id",type.getMid());
                    intent.putExtra("name",type.getName());
                    startActivityForResult(intent,2011);
                }
            });
            initData();
        }
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_share,R.id.tv_accont,R.id.iv_head})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_share:
                //view.setOnClickListener(null);
                Intent intent = new Intent(context, SharedActivity.class);
                intent.putExtra("shared","member");
                startActivity(intent);
                break;
            case R.id.tv_accont:
                startActivityForResult(new Intent(context, AccountDetailActivity.class),2011);
                break;
            case R.id.iv_head:
                context.startActivity(new Intent(context, InformationInputActivity.class));
                break;
        }
    }

    /**
     * Init data.
     */
    private void initData() {
        UiUtils.startnet(context);
        ServeManager.getVIPmessage(context).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if (response.body() != null)
                    Log.e("MemberFragment","Result:"+response.body().getResult()+"  Msg:"+response.body().getMsg());
                if (response.body() != null&&"01".equals(response.body().getResult())){
                    data = response.body().getList();
                } else
                    Toast.makeText(context,"网络加载失败",Toast.LENGTH_SHORT).show();
                memberAdapter.setData(data);
                memberAdapter.notifyDataSetChanged();
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {
                Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                Log.e("MemberFragment","onFailure:"+t.getLocalizedMessage());
                UiUtils.endnet();
            }
        });
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2011){
            UiUtils.startnet(context);
            ServeManager.getUser(context,MyApplication.mUser.getUid()+"").enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.body() != null){
                        Log.e("CenterFragment","Result:"+response.body().getResult()+"  Msg:"+response.body().getMsg());
                    }
                    if(response.body() != null && "01".equals(response.body().getResult())){
                        MyApplication.mUser = response.body().getUser();
                        initView();
                    } else {
                        Toast.makeText(context,"网络请求失败",Toast.LENGTH_SHORT).show();
                    }
                    UiUtils.endnet();
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                    UiUtils.endnet();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Refresh.
     */
    public void refresh(){
        ServeManager.getUser(context,MyApplication.mUser.getUid()+"").enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null){
                    Log.e("CenterFragment","Result:"+response.body().getResult()+"  Msg:"+response.body().getMsg());
                }
                if(response.body() != null && "01".equals(response.body().getResult())){
                    MyApplication.mUser = response.body().getUser();
                    initView();
                } else {
                    Toast.makeText(context,"网络请求失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * On attach.
     *
     * @param context the context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
