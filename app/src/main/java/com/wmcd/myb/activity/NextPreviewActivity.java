package com.wmcd.myb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wmcd.myb.R;
import com.wmcd.myb.adapter.NextPreviewAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.GenerateImageModel;
import com.wmcd.myb.model.PreviewModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/6/15.
 */

public class NextPreviewActivity extends BaseActivity {
    @Bind(R.id.iv_bottom_vip)
    ImageView iv_bottom_vip;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.rl_keep)
    RelativeLayout rl_keep;
    @Bind(R.id.iv_example)
    ImageView iv_example;
    @Bind(R.id.rl_tishi)
    RelativeLayout rl_tishi;
    private PreviewModel.TemplateBean templateBean;
    private NextPreviewAdapter nextPreviewAdapter;
    private boolean mJudge;//判断能否进入编辑页面
    private int pointer = 0;
    private int tid;

    //保存图片后得到的路径
    private String path;
    //高保真回传的关联数据
    private GenerateImageModel.PhotoBean photoBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_next);
        ButterKnife.bind(this);
        tid = getIntent().getIntExtra("tid", 0);
        nextPreviewAdapter = new NextPreviewAdapter(this);
        rl_keep.setVisibility(View.GONE);
        initData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        //页面左下角小图标
        UiUtils.loadImage(NextPreviewActivity.this, UrlConfig.IMG + templateBean.getMicon(), iv_bottom_vip, (int) UiUtils.dpToPixels(18, NextPreviewActivity.this));
        if (templateBean.getType() == 5) {//画册
            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
            //调用以下方法让RecyclerView的第一个条目仅为1列
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //如果位置是大于2，那么这个条目将占用SpanCount()这么多的列数，再此也就是viewtype的个数 而如果不是，则占用两列即可
                    int i = position < 2 ? 2 : 1;
                    if (i == 1) {//画册中前两个item
                        nextPreviewAdapter.setItemType(4);
                    } else {
                        nextPreviewAdapter.setItemType(2);
                    }
                    return i;
                }
            });
            //把LayoutManager设置给RecyclerView
            recyclerview.setLayoutManager(layoutManager);
        } else if (templateBean.getType() == 8) {//折页
            GridLayoutManager layoutManager = new GridLayoutManager(this, templateBean.getViewtype());
            nextPreviewAdapter.setItemType(templateBean.getViewtype());
            //把LayoutManager设置给RecyclerView
            recyclerview.setLayoutManager(layoutManager);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            nextPreviewAdapter.setItemType(1);
            //把LayoutManager设置给RecyclerView
            recyclerview.setLayoutManager(layoutManager);
        }
        recyclerview.setAdapter(nextPreviewAdapter);
        nextPreviewAdapter.getOnclick(new NextPreviewAdapter.OnClickListener() {
            @Override
            public void OnClick(int position) {
                if (!mJudge) {
                    iv_example.setVisibility(View.VISIBLE);
                    UiUtils.loadImage(NextPreviewActivity.this, UrlConfig.IMG + templateBean.getPages().get(position).getIcon(), iv_example, UiUtils.dip2px(NextPreviewActivity.this, 360));
                    iv_example.invalidate();
                    iv_example.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iv_example.setVisibility(View.GONE);
                            mJudge = false;
                        }
                    });
                    pointer = position;
                    mJudge = true;
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        UiUtils.startnet(this);
        String uid = "";
        if (MyApplication.mUser != null)
            uid = MyApplication.mUser.getUid() + "";
        ServeManager.getPictureEditor(this, tid + "", uid).enqueue(new Callback<PreviewModel>() {
            @Override
            public void onResponse(Call<PreviewModel> call, Response<PreviewModel> response) {
                if (response.body() != null)
                    Log.e("NextPreviewActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    templateBean = response.body().getTemplate();
                    //判断是否是单页
                    if (templateBean.getPages().size() == 1) {
                        mJudge = true;
                        pointer = 0;
                    }
                    initView();
                    nextPreviewAdapter.setData(templateBean.getPages());
                    nextPreviewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(NextPreviewActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<PreviewModel> call, Throwable t) {
                Toast.makeText(NextPreviewActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("NextPreviewActivity", "onFailure:" + t.getLocalizedMessage());
                UiUtils.endnet();
            }
        });
    }

    @OnClick({R.id.rl_back, R.id.rl_keep, R.id.rl_bottom_02, R.id.iv_x, R.id.iv_tishi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.rl_keep://保存
                UiUtils.startnet(this);
                //高保真
                HighFidelity();
                break;
            case R.id.rl_bottom_02://跳转到编辑页面
                if (templateBean.getIsup() != 1) {
                    Toast.makeText(this, "暂不开放，敬请期待！！！", Toast.LENGTH_SHORT).show();
                } else {
                    if (templateBean.getType() == 6) {
                        Toast.makeText(this, "不好意思，横幅模板暂不开放^o^!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (MyApplication.mUser != null) {
                            String phone = MyApplication.mUser.getPhone() + "";
                            Log.e("NextPreviewActivity", "phone" + phone);
                            if (mJudge && templateBean.getPages().get(pointer) != null) {
                                //在这里判断有没有绑定手机号
                                if (Pattern.matches(UrlConfig.PHONE_MATCH, phone)) {
                                    //判断是不是会员
                                    if (templateBean.getIsbuy() != 0) {
                                        //隐藏展示图
                                        iv_example.setVisibility(View.GONE);
                                        if (templateBean.getPages().size() != 1) {
                                            mJudge = false;
                                        }
                                        //是会员 进入编辑
                                        Intent intent1 = new Intent(this, NextPictureEditorActivity.class);
//                                        Intent intent1 = new Intent(this, PictureEditorActivity.class);
                                        intent1.putExtra("pageslist", templateBean.getPages().get(pointer));
                                        startActivityForResult(intent1, 1101);
                                    } else {
                                        //不是会员
                                        rl_tishi.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    //没有手机号跳转绑定手机号
                                    Intent intent2 = new Intent(this, VerificationActivity.class);
                                    startActivity(intent2);
                                }
                            } else {
                                Toast.makeText(this, "请选择编辑图片", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //强制登录
                            startActivity(new Intent(this, LoginActivity.class));
                        }
                    }
                }
                break;
            case R.id.iv_x:
                rl_tishi.setVisibility(View.GONE);
                break;
            case R.id.iv_tishi:
                Intent intent1 = new Intent(this, MainTopActivity.class);
                intent1.putExtra("member", 1414);
                startActivity(intent1);
                break;
        }
    }

    /**
     * 高保真上传
     */
    private void HighFidelity() {
        Gson bh = new Gson();
        if (MyApplication.mUser != null) {
            templateBean.setUid(MyApplication.mUser.getUid());
            String s = bh.toJson(templateBean);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), s);
            ServeManager.getGenerateImage(this, body).enqueue(new Callback<GenerateImageModel>() {
                @Override
                public void onResponse(Call<GenerateImageModel> call, Response<GenerateImageModel> response) {
                    if (response.body() != null)
                        Log.e("NextPreviewActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                    if (response.body() != null && "01".equals(response.body().getResult())) {
                        photoBean = response.body().getPhoto();
                        keepBitmap();
                        Toast.makeText(NextPreviewActivity.this, "成功上传服务器", Toast.LENGTH_SHORT).show();
                    }else if ("10".equals(response.body().getResult())){
                        Toast.makeText(NextPreviewActivity.this,"稍等片刻再试...",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(NextPreviewActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                    }
                    UiUtils.endnet();
                }

                @Override
                public void onFailure(Call<GenerateImageModel> call, Throwable t) {
                    Toast.makeText(NextPreviewActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    Log.e("NextPreviewActivity", "onFailure:" + t.getLocalizedMessage());
                    UiUtils.endnet();
                    keepBitmap();
                }
            });
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void keepBitmap() {
        Intent intent = new Intent(this, SharedActivity.class);
        intent.putExtra("shared", "editor");
        if (photoBean != null)
            intent.putExtra("photoid", photoBean.getPhotoid());
        intent.putExtra("path", path);
        intent.putExtra("tid", tid);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1101 && resultCode == 1102) {
            Bundle bundle = data.getExtras();
            templateBean.getPages().set(pointer, (PreviewModel.TemplateBean.PagesBean) bundle.getSerializable("Pdata"));
            rl_keep.setVisibility(View.VISIBLE);
            path = bundle.getString("path");

            //获取编辑后的图片
            Glide.with(this).load(path).into(nextPreviewAdapter.images.get(pointer));
        } else if (requestCode == 0) {
            finish();
        }
    }
}
