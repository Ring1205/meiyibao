package com.wmcd.myb.fragment;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.DesignerTemplateActivity;
import com.wmcd.myb.activity.InformationInputActivity;
import com.wmcd.myb.activity.LoginActivity;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 个人中心
 * Created by 邓志宏 on 2017/2/16.
 */
public class CenterFragment extends BaseFragment {
    /**
     * The Iv head.
     */
    @Bind(R.id.iv_head)
    ImageView iv_head;//头像
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
     * The Iv vip.
     */
    @Bind(R.id.iv_vip)
    ImageView iv_vip;//vip用户
    /**
     * The Recycler view.
     */
    @Bind(R.id.rl_tem)
    RecyclerView recyclerView;//模板
    @Bind(R.id.tv_button)
    TextView tv_button;//入驻设计师
    /**
     * The Tv mname.
     */
    @Bind(R.id.tv_mname)
    TextView tv_mname;

    @Bind(R.id.rl_back)
    RelativeLayout rl_back;

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
        return R.layout.fragment_center;
    }

    /**
     * Init data.
     */
    @Override
    public void initData() {
        super.initData();
        if (MyApplication.mUser == null){
            startActivity(new Intent(MyApplication.getContext(), LoginActivity.class));
            return;
        }
        ServeManager.getUser(MyApplication.getContext(), MyApplication.mUser.getUid() + "").enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null) {
                    Log.e("CenterFragment", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                }
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    userbean = response.body().getUser();
                    if (userbean.getIconR() != null)
                        UiUtils.loadImage(MyApplication.getContext(), UrlConfig.IMG + userbean.getIconR(), iv_head, ImageType.CIRCLE);
                    tv_name.setText(userbean.getName()+"");
                    if (userbean.getMicon() != null)
                        UiUtils.loadImage(MyApplication.getContext(), UrlConfig.IMG + userbean.getMicon(), iv_no_vip, 0);
                    tv_mname.setText(userbean.getMname()+"");
                    if (userbean.getDesigner() != null){
                        tv_button.setText("设计师");
                    }
                } else {
                    Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });

        ServeManager.getTemplateByUser(context, MyApplication.mUser.getUid() + "").enqueue(new Callback<TemplateByUserModel>() {
            @Override
            public void onResponse(Call<TemplateByUserModel> call, Response<TemplateByUserModel> response) {
                if (response.body() != null) {
                    Log.e("CenterFragment", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                }
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (UiUtils.isList(response.body().getList())) {
                        list.clear();
                        recyclerView.setVisibility(View.VISIBLE);
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
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpaceItemDecoration(UiUtils.dip2px(context, 2)));
        adapter = new CenterTemplateAdapter(context);
        recyclerView.setAdapter(adapter);
        rl_back.setVisibility(View.GONE);
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_set, R.id.iv_no_vip, R.id.iv_vip, R.id.iv_head,R.id.tv_button,R.id.rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_set:   //设置
                startActivity(new Intent(context, SetActivity.class));
                break;
            case R.id.iv_no_vip:
//                startActivity(new Intent(getActivity(), VIPsystemActivity.class));
                break;
            case R.id.iv_vip:
//                startActivity(new Intent(getActivity(), VIPsystemActivity.class));
                break;
            case R.id.iv_head:
                context.startActivity(new Intent(context, InformationInputActivity.class));
                break;
            case R.id.tv_button:
                if (MyApplication.mUser !=null && "设计师".equals(tv_button.getText())){
                    startActivity(new Intent(context, DesignerTemplateActivity.class));
                }else {
                    rl_back.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_back:
                rl_back.setVisibility(View.GONE);
                break;
        }
    }

}


