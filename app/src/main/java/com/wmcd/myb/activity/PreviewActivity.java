package com.wmcd.myb.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.google.gson.Gson;
import com.wmcd.myb.R;
import com.wmcd.myb.adapter.PreviewAdapter;
import com.wmcd.myb.aliyun.PutObjectSamples;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.GenerateImageModel;
import com.wmcd.myb.model.PreviewModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2017/3/21.
 */
public class PreviewActivity extends BaseActivity {
    /**
     * The Pages list.
     */
    public PreviewModel.TemplateBean.PagesBean pagesList;
    /**
     * The Template bean.
     */
    public PreviewModel.TemplateBean templateBean;
    /**
     * The Page.
     */
    public int page;
    /**
     * The Tv name.
     */
    @Bind(R.id.tv_name)
    TextView tv_name;//菜单栏
    /**
     * The Recyclerview.
     */
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    /**
     * The Tv keep.
     */
    @Bind(R.id.tv_keep)
    TextView tv_keep;
    /**
     * The Rl tishi.
     */
    @Bind(R.id.rl_tishi)
    RelativeLayout rl_tishi;
    /**
     * The Iv bottom vip.
     */
    @Bind(R.id.iv_bottom_vip)
    ImageView iv_bottom_vip;
    /**
     * The Tv vipname.
     */
    @Bind(R.id.tv_vipname)
    TextView tv_vipname;
    /**
     * The Preview adapter.
     */
    private PreviewAdapter previewAdapter;
    /**
     * The Tid.
     */
    private int tid;
    /**
     * The Bitmap.
     */
    private Bitmap bitmap;
    /**
     * The A boolean.
     */
    private boolean aBoolean, /**
     * The T boolean.
     */
    tBoolean;
    /**
     * The Photo bean.
     */
    private GenerateImageModel.PhotoBean photoBean;
    /**
     * The Bitmaps.
     */
    private Map<Integer, Bitmap> bitmaps = new HashMap<>();

    /**
     * The constant oss.
     */
//    private static final String endpoint = "http://oss-cn-shanghai.aliyuncs.com/";
//    private static final String testBucket = "mybpic";
//    private static final String accessKeyId = "LTAIXdRaTAjA7KjB";
//    private static final String accessKeySecret = "tfsrQd2ciMkI1zbyg04CdjQPXnIDuG";
    private OSS oss;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String uid = "";
        if (MyApplication.mUser != null)
            uid = MyApplication.mUser.getUid() + "";
        ServeManager.getPictureEditor(this, tid + "", uid).enqueue(new Callback<PreviewModel>() {
            @Override
            public void onResponse(Call<PreviewModel> call, Response<PreviewModel> response) {
                if (response.body() != null)
                    Log.e("PictureEditorActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    templateBean = response.body().getTemplate();
                } else {
                    Toast.makeText(PreviewActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<PreviewModel> call, Throwable t) {
                Toast.makeText(PreviewActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("PictureEditorActivity", "onFailure:" + t.getLocalizedMessage());
                UiUtils.endnet();
            }
        });
    }

    /**
     * On destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //.cleanActivity(this);
        Log.e("preview","previewdestory");
    }
    /**
     * Init data.
     */
    private void initData() {
        UiUtils.startnet(this);
        String uid = "";
        if (MyApplication.mUser != null)
            uid = MyApplication.mUser.getUid() + "";
        Log.e("PreviewActivity", "ring.uid=" + uid);
        ServeManager.getPictureEditor(this, tid + "", uid).enqueue(new Callback<PreviewModel>() {
            @Override
            public void onResponse(Call<PreviewModel> call, Response<PreviewModel> response) {
                if (response.body() != null)
                    Log.e("previewActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    templateBean = response.body().getTemplate();
                    int type = templateBean.getType();
                    setTabName(type);
                    setView();
                    recyclerview.setItemViewCacheSize(templateBean.getPages().size() / templateBean.getViewtype());
                    previewAdapter.setlistview(response.body().getTemplate().getViewtype());
                    previewAdapter.setData(response.body().getTemplate().getPages());
                    previewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(PreviewActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<PreviewModel> call, Throwable t) {
                Toast.makeText(PreviewActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("previewActivity", "onFailure:" + t.getLocalizedMessage());
                UiUtils.endnet();
            }
        });
    }
    /**
     * Sets view.
     */
    private void setView() {
        tv_vipname.setText(templateBean.getMname());
        UiUtils.loadImage(PreviewActivity.this, UrlConfig.IMG + templateBean.getMicon(), iv_bottom_vip, (int) UiUtils.dpToPixels(18, PreviewActivity.this));
    }

    /**
     * Init view.
     */
    private void initView() {
        tid = getIntent().getIntExtra("tid", 0);
        previewAdapter = new PreviewAdapter(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(previewAdapter);
        tv_keep.setVisibility(View.GONE);
        previewAdapter.setOnClickable(new PreviewAdapter.OnClickable() {
            @Override
            public void getPages(PreviewModel.TemplateBean.PagesBean pagesBeanList, int pager) {
                pagesList = pagesBeanList;
                page = pager;
                for (ImageView sele : previewAdapter.seles) {
                    sele.setSelected(false);
                }
            }
        });
        initData();
    }

    /**
     * Sets tab name.
     *
     * @param type the type
     */
    public void setTabName(int type) {
        switch (type) {
            case 3:
                tv_name.setText("头像预览");
                break;
            case 4:
                tv_name.setText("海报预览");
                break;
            case 5:
                tv_name.setText("画册预览");
                break;
            case 6:
                tv_name.setText("横幅预览");
                break;
            case 7:
                tv_name.setText("名片预览");
                break;
            case 8:
                tv_name.setText("单页预览");
                break;
            case 9:
                tv_name.setText("易拉宝预览");
                break;
            case 13:
                tv_name.setText("会员卡预览");
                break;
            case 12:
                tv_name.setText("优惠卷预览");
                break;
            case 11:
                tv_name.setText("线上名片预览");
                break;
            case 14:
                tv_name.setText("对比图预览");
                break;
        }
    }

    /**
     * On click.
     * @param view the view
     */
    @OnClick({R.id.iv_back, R.id.rl_bottom_02, R.id.tv_keep, R.id.iv_x, R.id.iv_tishi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_bottom_02: //编辑
                if (templateBean.getIsup() != 1) {
                    Toast.makeText(this, "暂不开放，敬请期待！！！", Toast.LENGTH_SHORT).show();
                } else {
                    if (templateBean.getType() == 6) {
                        Toast.makeText(this, "不好意思，横幅模板暂不开放^o^!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (MyApplication.mUser != null) {
                            String phone = MyApplication.mUser.getPhone() + "";
                            Log.e("PreviewActivity", "phone" + phone);
                            if (pagesList != null) {
                                //在这里判断有没有绑定手机号
                                if (Pattern.matches(UrlConfig.PHONE_MATCH, phone)) {
                                    //判断是不是会员
                                    if (templateBean.getIsbuy() != 0) {
                                        //是会员 进入编辑
                                        Intent intent1 = new Intent(this, NextPictureEditorActivity.class);
//                                        Intent intent1 = new Intent(this, PictureEditorActivity.class);
                                        intent1.putExtra("pageslist", pagesList);
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
            case R.id.tv_keep:
                UiUtils.startnet(PreviewActivity.this);
                if (!tBoolean) {
                    tBoolean = true;
                    if (aBoolean) {
                        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(UrlConfig.accessKeyId, UrlConfig.accessKeySecret);
                        ClientConfiguration conf = new ClientConfiguration();
                        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                        OSSLog.enableLog();
                        oss = new OSSClient(getApplicationContext(), UrlConfig.endpoint, credentialProvider, conf);
                    }
                    HiFi();
                }
                break;
            case R.id.iv_x:
                rl_tishi.setVisibility(View.GONE);
                break;
            case R.id.iv_tishi:
                Intent intent = new Intent(this, MainTopActivity.class);
                intent.putExtra("member", true);
                startActivity(intent);
                break;
        }
    }

    /**
     * Hi fi.
     */
//高保真回传
    private void HiFi() {
        Gson bh = new Gson();
        if (MyApplication.mUser != null) {
            templateBean.setUid(MyApplication.mUser.getUid());
            String s = bh.toJson(templateBean);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), s);
            ServeManager.getGenerateImage(this, body).enqueue(new Callback<GenerateImageModel>() {
                @Override
                public void onResponse(Call<GenerateImageModel> call, Response<GenerateImageModel> response) {
                    if (response.body() != null)
                        Log.e("previewActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                    if (response.body() != null && "01".equals(response.body().getResult())) {
                        photoBean = response.body().getPhoto();
                        Toast.makeText(PreviewActivity.this, "成功上传服务器", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PreviewActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                    }
                    UiUtils.endnet();
                    keepBitmap();
                }

                @Override
                public void onFailure(Call<GenerateImageModel> call, Throwable t) {
                    Toast.makeText(PreviewActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    Log.e("previewActivity", "onFailure:" + t.getLocalizedMessage());
                    UiUtils.endnet();
                    keepBitmap();
                }
            });
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    /**
     * Keep bitmap.
     */
    private void keepBitmap() {
        if (bitmaps.size() == 1) {
            path = UiUtils.saveBitmap(this, bitmap, "DCIM");
            if (aBoolean) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
                        new PutObjectSamples(oss, UrlConfig.testBucket, uuid, path).asyncPutObjectFromLocalFile();
                        startActivity(uuid);
                    }
                }).start();
            } else {
                startActivity("");
            }
        }
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * The Path.
     */
    private String path;

    /**
     * Start activity.
     *
     * @param uuid the uuid
     */
    private void startActivity(String uuid) {
        final Intent intent = new Intent(this, SharedActivity.class);
        intent.putExtra("shared", "editor");
        if (photoBean != null)
            intent.putExtra("photoid", photoBean.getPhotoid());
        if (!"".equals(uuid)) {
            intent.putExtra("UUID", uuid);
        }
        intent.putExtra("path", path);
        intent.putExtra("tid", tid);
        startActivityForResult(intent,0);
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
        if (requestCode == 1101 && resultCode == 1102) {
            tBoolean = false;
            Bundle bundle = data.getExtras();
//            byte[] bis = bundle.getByteArray("bitmap");
            aBoolean = bundle.getBoolean("aBoolean");
            // bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
            pagesList = (PreviewModel.TemplateBean.PagesBean) bundle.getSerializable("Pdata");
            bitmaps.put(page, bitmap);
            templateBean.getPages().set(page, pagesList);
            tv_keep.setVisibility(View.VISIBLE);
            for (Integer integer : bitmaps.keySet()) {
                if (bitmaps.get(integer) != null && !bitmaps.get(integer).isRecycled()) {
                    previewAdapter.images.get(integer).setImageBitmap(bitmaps.get(integer));
                }
            }
            System.gc();
        }else if (requestCode == 0){
            finish();
        }
    }


}
