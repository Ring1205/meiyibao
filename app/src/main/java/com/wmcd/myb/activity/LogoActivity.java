package com.wmcd.myb.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.GenerateLogoAdapter;
import com.wmcd.myb.adapter.LogoAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.AddLogo;
import com.wmcd.myb.model.GenerateLogoModel;
import com.wmcd.myb.model.LogoModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.view.DividerGridItemDecoration;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/4/24. 一键Logo Activity
 */
public class LogoActivity extends BaseActivity implements View.OnClickListener {
    /**
     * The Rl bottom.
     */
    @Bind(R.id.rl_bottom)   //logo入库
            RelativeLayout rl_bottom;
    /**
     * The Ll 01.
     */
    @Bind(R.id.ll_01)   //查找logo
            LinearLayout ll_01;
    /**
     * The Rl view.
     */
    @Bind(R.id.rl_view)    //制作的logo
            RelativeLayout rl_view;
    /**
     * The Rv generate logo.
     */
    @Bind(R.id.rv_generate_logo)    //制作的logo
            RecyclerView rv_generate_logo;
    /**
     * The Et name.
     */
    @Bind(R.id.et_name) //单位名
            EditText et_name;
    /**
     * The Iv no logo.
     */
    @Bind(R.id.iv_no_logo)  //没有我的logo时
            ImageView iv_no_logo;
    /**
     * The Recyclerview.
     */
    @Bind(R.id.recyclerview)    //我的logo
            RecyclerView recyclerview;
    /**
     * The Iv 05 on.
     */
    @Bind(R.id.iv_05_on)    //中点
            ImageView iv_05_on;
    /**
     * The Iv 04 on.
     */
    @Bind(R.id.iv_04_on)    //前点
            ImageView iv_04_on;
    /**
     * The Iv 06 on.
     */
    @Bind(R.id.iv_06_on)    //后点
            ImageView iv_06_on;
    /**
     * The Iv refresh.
     */
    @Bind(R.id.iv_refresh)   //刷新
            ImageView iv_refresh;
    /**
     * The Gongsi.
     */
    @Bind(R.id.gongsi)
    TextView gongsi;
    /**
     * The Logo adapter.
     */
    private LogoAdapter logoAdapter;
    /**
     * The Generate logo model.
     */
    private GenerateLogoModel generateLogoModel;
    /**
     * The Name.
     */
    private String name;
    private GenerateLogoAdapter generateLogo;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * Init data.
     */
//请求数据logo
    private void initData() {
        query();
    }

    /**
     * Query.查找用户的logo库
     */
    public void query() {
        UiUtils.startnet(this);
        ServeManager.myLogo(this, MyApplication.mUser.getUid()+"").enqueue(new Callback<LogoModel>() {
            @Override
            public void onResponse(Call<LogoModel> call, Response<LogoModel> response) {
                if (response.body() != null)
                    Log.e(LogoActivity.class.getName(), "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (response.body().getList().size() > 0) {
                        //用户有logo
                        iv_no_logo.setVisibility(View.GONE);
                        List<LogoModel.ListBean> list = response.body().getList();
                        if (list != null){
                            Collections.reverse(list);
                            logoAdapter.setData(list);
                        }
                        //logoAdapter.notifyDataSetChanged();
                        /**
                         只刷新刚添加的
                         * */
                        logoAdapter.notifyItemInserted(0);
                        logoAdapter.notifyItemRangeChanged(0, 100);
                        recyclerview.setVisibility(View.VISIBLE);
                    } else {
                        //用户没有logo
                        iv_no_logo.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<LogoModel> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Init view.
     */
    private void initView() {
        rl_bottom.setVisibility(View.GONE);
        iv_no_logo.setVisibility(View.GONE);
        ll_01.setVisibility(View.GONE);
        recyclerview.setVisibility(View.GONE);
        rl_view.setVisibility(View.GONE);
        et_name.setOnClickListener(this);
        generateLogo = new GenerateLogoAdapter(this);
        logoAdapter = new LogoAdapter(this);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerview.addItemDecoration(new DividerGridItemDecoration(this));
        recyclerview.setAdapter(logoAdapter);

        rv_generate_logo.setLayoutManager(new GridLayoutManager(this, 3));
        rv_generate_logo.addItemDecoration(new DividerGridItemDecoration(this));
        rv_generate_logo.setAdapter(generateLogo);
    }

    /**
     * The Select static.
     */
    private int SelectStatic = -1;

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_back, R.id.iv_state_onclick, R.id.iv_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.iv_state_onclick://开始制作
                name = et_name.getText().toString().trim();
                if ("".equals(name)) {
                    Toast.makeText(getApplicationContext(), "请输入相关名称", Toast.LENGTH_SHORT).show();
                } else {
                    createLogo(name);
                }
                break;
            case R.id.iv_refresh:
                Toast.makeText(LogoActivity.this, "刷新", Toast.LENGTH_SHORT).show();
                createLogo(name);
                break;
        }
    }

    /**
     * Add logo.将生成的logo提交到服务器
     *
     * @param position the position
     */
    public void addLogo(int position) {
        Log.e("LogoActivity", "SelectStatic=" + SelectStatic + "    position=" + position + "     id=" + generateLogoModel.getList().get(position).getLogoid());
        ServeManager.addLogo(LogoActivity.this,MyApplication.mUser.getUid()+"", generateLogoModel.getList().get(position).getLogoid() + "", name, generateLogoModel.getList().get(position).getImg()).enqueue(new Callback<AddLogo>() {
            @Override
            public void onResponse(Call<AddLogo> call, Response<AddLogo> response) {
                Toast.makeText(LogoActivity.this, "添加到logo库成功", Toast.LENGTH_SHORT).show();
                query();
            }

            @Override
            public void onFailure(Call<AddLogo> call, Throwable t) {
                Toast.makeText(LogoActivity.this, "添加到logo库失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * Create logo.根据用户输入的name生成logo供用户选择
     *
     * @param name the name
     */
    private void createLogo(String name) {
        if (name.length() > 6) {
            Toast.makeText(this,"Too long!Too hard! Please input again!",Toast.LENGTH_LONG).show();
        }else {
        String s = name.trim().toString();
        UiUtils.startnet(this);
        gongsi.setText(s);
        ServeManager.generateLogo(this, s).enqueue(new Callback<GenerateLogoModel>() {
            @Override
            public void onResponse(Call<GenerateLogoModel> call, Response<GenerateLogoModel> response) {
                if (response.body() != null)
                    Log.e(DocActivity.class.getName(), "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    ll_01.setVisibility(View.VISIBLE);
                    // TODO: 2017/6/10 0010  
                    //rl_view.setVisibility(View.VISIBLE);
                    generateLogoModel = response.body();
                    generateLogo.setData(generateLogoModel);
                    generateLogo.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<GenerateLogoModel> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });}
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LogoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void deleteLogo(int position) {
        logoAdapter.notifyItemRemoved(position);
        //logoAdapter.notifyItemRangeChanged(position-1,100);
        //logoAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logoAdapter = null;
        generateLogo = null;
    }
}
