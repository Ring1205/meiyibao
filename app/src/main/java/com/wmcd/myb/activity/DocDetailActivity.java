package com.wmcd.myb.activity;

import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.DocModel;
import com.wmcd.myb.model.ResultModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.FileHandler;
import com.wmcd.myb.util.UiUtils;

import java.net.URLEncoder;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/25 0025. 文档下载
 */
public class DocDetailActivity extends BaseActivity implements View.OnClickListener {
    /**
     * The Doc bean. 文档bean
     */
    private DocModel.ListDocBean docBean;
    /**
     * The Local path.保存的路径
     */
    private String localPath;
    /**
     * The Popup window.
     */
    private PopupWindow popupWindow;
    /**
     * The Share type.
     */
    private int shareType = 0;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_detail);
        initView();
    }

    /**
     * Init view.初始化View
     */
    private void initView() {
        findViewById(R.id.right_tv).setOnClickListener(this);
        findViewById(R.id.back_iv).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText("模板详情");
        findViewById(R.id.right_tv).setVisibility(View.VISIBLE);
        docBean = (DocModel.ListDocBean) getIntent().getSerializableExtra("docBean");
        if (docBean.getImg().equals("")) {
            Toast.makeText(getApplicationContext(), "模板没有内容", Toast.LENGTH_LONG).show();
            return;
        }
        LinearLayout docLV = (LinearLayout) findViewById(R.id.doc_lv);
        String[] docs = docBean.getImg().split(",");
        ImageView iv;
        for (int i = 0; i < docs.length; i++) {
            iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.MATRIX);
            UiUtils.loadImage(this, UrlConfig.IMG + docs[i], iv, 0);
            docLV.addView(iv);
        }


    }

    /**
     * On click. 点击事件
     *
     * @param view the view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.right_tv:
                showPop(view);
                break;
        }
    }
//
//    private void sendWxFriend() {
//        try {
//            final String urlStr = URLEncoder.encode(docBean.getKey(), "UTF-8").replaceAll("\\+", "%20");
//            ShareSDK.initSDK(DocDetailActivity.this);
//            Platform plat = ShareSDK.getPlatform(WechatMoments.NAME);
//            Wechat.ShareParams wcsp = new Wechat.ShareParams();
//            wcsp.setShareType(Platform.SHARE_WEBPAGE);
//            wcsp.setTitle("分享");
//            wcsp.setText(docBean.getKey());
//            wcsp.setUrl(UrlConfig.DOC_HTTP + urlStr);
//            plat.share(wcsp);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void sendWx() {
//        try {
//            final String urlStr = URLEncoder.encode(docBean.getKey(), "UTF-8").replaceAll("\\+", "%20");
//            ShareSDK.initSDK(DocDetailActivity.this);
//            Platform plat = ShareSDK.getPlatform(Wechat.NAME);
//            Wechat.ShareParams wcsp = new Wechat.ShareParams();
//            wcsp.setShareType(Platform.SHARE_WEBPAGE);
//            wcsp.setTitle("分享");
//            wcsp.setText(docBean.getKey());
//            wcsp.setUrl(UrlConfig.DOC_HTTP + urlStr);
//            plat.share(wcsp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Send wx. 分享到微信
     *
     * @param shareType    the share type
     * @param platformName the platform name
     */
    private void sendWx(int shareType, String platformName) {
        try {
            final String urlStr = URLEncoder.encode(docBean.getKey(), "UTF-8").replaceAll("\\+", "%20");
            ShareSDK.initSDK(DocDetailActivity.this);
            Platform plat = ShareSDK.getPlatform(platformName);
            Wechat.ShareParams wcsp = new Wechat.ShareParams();
            wcsp.setShareType(shareType);
            wcsp.setTitle("分享");
            wcsp.setText(docBean.getKey());
            wcsp.setUrl(UrlConfig.DOC_HTTP + urlStr);
            plat.share(wcsp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("提示");
//        builder.setMessage("是否下载所需资源");
//        builder.setCancelable(false);//设置点击对话框外部不关闭对话框
//        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (!FileHandler.getInstance().checkSDCardState()) {
//                    Toast.makeText(getApplicationContext(), "没有空间", Toast.LENGTH_LONG);
//                    return;
//                }
//                Toast.makeText(getApplicationContext(), "资源文件下载中。。。", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(), LoadDocService.class);
//                intent.putExtra("docUrl", docBean.getKey());
//                startService(intent);
//            }
//        });
//        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//                builder.show();
//}

    /**
     * Load doc. 下载文档
     */
    private void loadDoc() {
        if (docBean.getKey().equals("")) {
            Toast.makeText(getApplicationContext(), "资源文件不存在...", Toast.LENGTH_LONG).show();
            return;
        }
        localPath = FileHandler.getInstance().getDCIMPath() + "/myb_doc/";
        FileHandler.getInstance().createDirectory(localPath);
        try {
            final String urlStr = URLEncoder.encode(docBean.getKey(), "UTF-8").replaceAll("\\+", "%20");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UiUtils.downDoc(mHandler, UrlConfig.DOC_HTTP + urlStr, localPath + docBean.getKey());
                }
            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "资源文件下载失败...", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Send email.发送到邮箱
     */
    private void sendEmail() {
        final Dialog queryDialog = new Dialog(this, R.style.dialog);
        queryDialog.setContentView(R.layout.dialog_send_mail);

        final EditText etEmail = (EditText) queryDialog.findViewById(R.id.et);
        queryDialog.findViewById(R.id.send_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDialog.dismiss();
                popupWindow.dismiss();
                UiUtils.startnet(DocDetailActivity.this);
                ServeManager.sendEmial(DocDetailActivity.this, docBean.getDid(), etEmail.getText().toString().trim()).enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                        if (response.body() != null) {
//                            Log.e("LoginActivity", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
//                            if ("01".equals(response.body().getResult())) {
//                            }
                            Toast.makeText(DocDetailActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DocDetailActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {
                        Toast.makeText(DocDetailActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    }

                });
                UiUtils.endnet();
            }
        });

        queryDialog.show();
    }

    /**
     * Show pop.
     *
     * @param view the view
     */
    private void showPop(View view) {
        View popView = LayoutInflater.from(this).inflate(R.layout.pop_share, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popView.findViewById(R.id.sendmail_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                sendEmail();
            }
        });
        popView.findViewById(R.id.sendwx_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                shareType = 2;
                loadDoc();
            }
        });
        popView.findViewById(R.id.cancle_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popView.findViewById(R.id.sendwxfriend_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                shareType = 1;
                loadDoc();
            }
        });

    }

    /**
     * The M handler. 下载完的handler
     */
//0:下载成功  1：下载失败
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        if (shareType == 1) {//微信圈
                            sendWx(Platform.SHARE_WEBPAGE, Wechat.NAME);
                        } else if (shareType == 2) {//朋友
                            // sendWxFriend();
                            sendWx(Platform.SHARE_WEBPAGE, WechatMoments.NAME);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "资源文件下载失败...", Toast.LENGTH_LONG).show();
                    break;
            }
        }

        ;
    };

    /**
     * On destroy.释放相关资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler = null;
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }
}
