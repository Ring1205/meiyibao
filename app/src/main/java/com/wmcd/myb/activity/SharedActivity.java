package com.wmcd.myb.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.ResultModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.ImageType;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.view.ClearEditView;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/14.
 */
public class SharedActivity extends BaseActivity {
    /**
     * The Rl 01.
     */
    @Bind(R.id.rl_01)
    RelativeLayout rl_01;
    /**
     * The Ll 02.
     */
    @Bind(R.id.ll_02)
    LinearLayout ll_02;
    /**
     * The Tv tab name.
     */
    @Bind(R.id.tv_tab_name)
    TextView tv_tab_name;
    /**
     * The Tv main.
     */
    @Bind(R.id.tv_main)
    TextView tv_main;
    /**
     * The Iv head.
     */
    @Bind(R.id.iv_head)
    ImageView iv_head;
    /**
     * The Iv head 02.
     */
    @Bind(R.id.iv_head_02)
    ImageView iv_head_02;
    /**
     * The Vip.
     */
    @Bind(R.id.vip)
    ImageView vip;
    /**
     * The Tv yaoqingma.
     */
    @Bind(R.id.tv_yaoqingma)
    TextView tv_yaoqingma;
    /**
     * The Tv name.
     */
    @Bind(R.id.tv_name)
    TextView tv_name;
    /**
     * The Button yangzhengma.
     */
    @Bind(R.id.button_yangzhengma)
    Button button_yangzhengma;
    /**
     * The Iv demo.
     */
    @Bind(R.id.iv_demo)
    ImageView iv_demo;
    /**
     * The Email.
     */
    @Bind(R.id.email_et)
    ClearEditView email;
    /**
     * The Path.
     */
    private String path;
    /**
     * The Bitmap.
     */
    private Bitmap bitmap;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        switch (intent.getStringExtra("shared")){
            case "member":
                rl_01.setVisibility(View.VISIBLE);
                ll_02.setVisibility(View.GONE);
                tv_tab_name.setText("美易宝会员");
                tv_main.setVisibility(View.GONE);
                UiUtils.loadImage(this, UrlConfig.IMG+ MyApplication.mUser.getIconR(),iv_head, ImageType.CIRCLE);
                UiUtils.loadImage(this,UrlConfig.IMG+MyApplication.mUser.getMicon(),vip,0);
                tv_name.setText(MyApplication.mUser.getName());
                tv_yaoqingma.setText("恭喜您成为"+MyApplication.mUser.getMname());
                button_yangzhengma.setText(MyApplication.mUser.getInvitationCode());
                ActivityCompat.requestPermissions(this, new String[]{android
                        .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bitmap = Glide.with(SharedActivity.this)
                            .load(UrlConfig.IMG + MyApplication.mUser.getInvitationImg())
                            .asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                            path = UiUtils.saveBitmap(SharedActivity.this,bitmap,"DCIM");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case "editor":
                rl_01.setVisibility(View.GONE);
                ll_02.setVisibility(View.VISIBLE);
                tv_tab_name.setText("保存/分享");
                tv_main.setVisibility(View.VISIBLE);
                path = intent.getStringExtra("path");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(path,options);
//                iv_demo.setImageBitmap(bitmap);
                UiUtils.loadImage(SharedActivity.this,path,iv_head_02,ImageType.CIRCLE);
                break;
        }
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.rl_back,R.id.button_shared,R.id.tv_main,R.id.button_member,R.id.shared_weixing,R.id.shared_penyouquan})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                if (getIntent().getIntExtra("tid",0) != 0){
                    Intent intent = new Intent(this,NextPreviewActivity.class);
                    intent.putExtra("tid",getIntent().getIntExtra("tid",0));
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.button_shared:
                showShare();
                break;
            case R.id.tv_main:
                startActivity(new Intent(this,MainTopActivity.class));
                finish();
                break;
            case R.id.button_member:
                String emailstr =email.getText().toString().trim();
                if (!emailstr.isEmpty()&& emailstr!=null){
                    int i = getIntent().getIntExtra("photoid",0);
                    String uuid = getIntent().getStringExtra("UUID");
                    Log.e("SharedActivity","photoid:"+i+"        uuid:"+uuid);
                    if (i == 0){
                        Toast.makeText(SharedActivity.this, "上传高保真失败，无法发送邮箱", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    UiUtils.startnet(this);
                    ServeManager.setEmail(this,i,emailstr,uuid).enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            if (response.body() != null)
                                Log.e("SharedActivity", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                            if (response.body() != null && "01".equals(response.body().getResult())) {
                                Toast.makeText(SharedActivity.this,"成功提交E-mail到服务器",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SharedActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                            }
                            UiUtils.endnet();
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {
                            Toast.makeText(SharedActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
                            UiUtils.endnet();
                        }
                    });
                }else{
                    Toast.makeText(SharedActivity.this ,"请输入邮箱地址",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shared_weixing:
                Platform plat = ShareSDK.getPlatform(Wechat.NAME);
                showShare(plat.getName());
                break;
            case R.id.shared_penyouquan:
                Platform plat1 = ShareSDK.getPlatform(WechatMoments.NAME);
                showShare(plat1.getName());
                break;
        }
    }

    /**
     * Show share.
     */
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("美易宝");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://gx303.com/search");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("亲，在浏览器中打开链接");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        if (path != null){
            oks.setImagePath(path);//确保SDcard下面存在此张图片
            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                    if (platform.getName().equalsIgnoreCase(Wechat.NAME)) {
                        paramsToShare.setText(null);
                        paramsToShare.setTitle(null);
                        paramsToShare.setTitleUrl(null);
                        paramsToShare.setImagePath(path);
                    } else if (platform.getName().equalsIgnoreCase(WechatMoments.NAME)) {
                        paramsToShare.setText(null);
                        paramsToShare.setTitle(null);
                        paramsToShare.setTitleUrl(null);
                        paramsToShare.setImagePath(path);
                    }
                }
            });
        } else {
            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
            oks.setImageUrl("http://www.520gxqm.com/uploads/allimg/160727/211213H94-2.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.wangqianfang.com/baidu/?q=%E7%BE%8E%E6%98%93%E5%AE%9DAPP%E4%B8%8B%E8%BD%BD");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
        }
        // 启动分享GUI
        oks.show(this);
    }

    /**
     * Show share.
     *
     * @param platform the platform
     */
    private void showShare(String platform) {
        OnekeyShare oks = new OnekeyShare();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("美易宝");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://gx303.com/search");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("亲，在浏览器中打开链接");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        if (path != null){
            oks.setImagePath(path);//确保SDcard下面存在此张图片
            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                    if (platform.getName().equalsIgnoreCase(Wechat.NAME)) {
                        paramsToShare.setText(null);
                        paramsToShare.setTitle(null);
                        paramsToShare.setTitleUrl(null);
                        paramsToShare.setImagePath(path);
                    } else if (platform.getName().equalsIgnoreCase(WechatMoments.NAME)) {
                        paramsToShare.setText(null);
                        paramsToShare.setTitle(null);
                        paramsToShare.setTitleUrl(null);
                        paramsToShare.setImagePath(path);
                    }
                }
            });
        } else {
            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
            oks.setImageUrl("http://www.520gxqm.com/uploads/allimg/160727/211213H94-2.jpg");
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("http://www.wangqianfang.com/baidu/?q=%E7%BE%8E%E6%98%93%E5%AE%9DAPP%E4%B8%8B%E8%BD%BD");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
        }
        // 启动分享GUI
        oks.show(this);
    }

    /**
     * On request permissions result.
     *
     * @param requestCode  the request code
     * @param permissions  the permissions
     * @param grantResults the grant results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //创建文件夹
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/");
                        if (!file.exists()) {
                            Log.e("jim", "path1 create:" + file.mkdirs());
                        }
                    }
                    break;
                }
        }
    }
}
