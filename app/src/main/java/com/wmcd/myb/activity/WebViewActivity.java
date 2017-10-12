package com.wmcd.myb.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.view.MyPopupWindow;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/18.
 */
public class WebViewActivity extends BaseActivity {
    /**
     * The Web view.
     */
    @Bind(R.id.webview)
    WebView webView;
    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
//        webView.requestFocusFromTouch();
        String Url = getIntent().getStringExtra("WebUrl");
        Log.e("WebViewActivity", "-----" + Url);
        if (Url != null)
            webView.loadUrl(Url);
        else
            webView.loadUrl(UrlConfig.IP + "wapHomeController/homeSwiper");
//        webView.loadUrl(getIntent().getStringExtra("WebUrl"));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        webSettings.setSupportZoom(true);  //支持缩放
        //允许混合内容 解决部分手机 加载不出https请求里面的http下的图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("WebViewActivity", "-----" + url);
                view.loadUrl(url);
                return true;
            }
        });
        webView.addJavascriptInterface(this, "myb");
        UiUtils.startnet(this);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress >= 100) {
                    UiUtils.endnet();
                }
            }
        });
    }

    /**
     * On pause.
     */
    @Override
    protected void onPause (){
        try {
            webView.getClass().getMethod("onPause").invoke(webView,  (Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        super.onPause ();
    }

    /**
     * On resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            webView.getClass().getMethod("onResume").invoke(webView,(Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * On destroy.
     */
    @Override
    protected void onDestroy() {
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        super.onDestroy();
    }

    /**
     * Pass value.
     *
     * @param str the str
     */
    @android.webkit.JavascriptInterface
    public void passValue(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("WebViewActivity", str);
            }
        });
    }

    /**
     * Share h 5.
     *
     * @param url     the url
     * @param title   the title
     * @param content the content
     * @param img     the img
     */
    @android.webkit.JavascriptInterface
    public void shareH5(final String url,String title,Context content,String img) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("WebViewActivity", url);

                if (a) {
                    if (MyApplication.mUser != null) {
                        Intent intent = new Intent(WebViewActivity.this, SharedActivity.class);
                        intent.putExtra("shared", "editor");
                        intent.putExtra("path", url);
                        startActivity(intent);
                        WebViewActivity.this.finish();

                        a = !a;
                    } else {
                        startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
                    }
                }
            }
        });
    }

    /**
     * Select photo.
     */
    @android.webkit.JavascriptInterface
    public void selectPhoto() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("WebViewActivity", "选择照片");
                showPopupWindow();

            }
        });
    }

    /**
     * The B.
     */
    public boolean b = true;
    /**
     * The A.
     */
    public boolean a = true;
    /**
     * The C.
     */
    public boolean c = true;

    /**
     * Buy member.
     */
    @android.webkit.JavascriptInterface
    public void buyMember() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b) {
                    if (MyApplication.mUser != null) {
                        Intent intent = new Intent(WebViewActivity.this, MainTopActivity.class);
                        intent.putExtra("member", true);
                        startActivity(intent);
                        WebViewActivity.this.finish();
                        b = !b;
                    } else {
                        startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
                    }
                }


            }
        });
    }

    /**
     * Show popup window.
     */
    private void showPopupWindow() {
        final MyPopupWindow popupWindow = new MyPopupWindow(this, "拍照", "从手机相册选择");
        popupWindow.setonclick(new MyPopupWindow.SetOnClickLinener() {
            @Override
            public void OnClick01() {   //拍照

                ActivityCompat.requestPermissions(WebViewActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                popupWindow.dismiss();
            }

            @Override
            public void OnClick02() {   //从手机相册选择
                UiUtils.getPictureFromPhoto(WebViewActivity.this, 3011);
                popupWindow.dismiss();
            }

            @Override
            public void OnClick03() {
            }
        });
    }

    /**
     * On request permissions result.
     *
     * @param requestCode  the request code
     * @param permissions  the permissions
     * @param grantResults the grant results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    /**
     * The File.
     */
    File file = new File(MyApplication.imgPath, "bg.jpg");

    /**
     * Do next.
     *
     * @param requestCode  the request code
     * @param grantResults the grant results
     */
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                UiUtils.getPictureFormCamera(WebViewActivity.this, 3012, file);
            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                finish();
            }
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
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3011: //从手机相册选择
                    Uri originalUri = data.getData();

                    webView.loadUrl("javascript:sendPhoto('"+originalUri+"')");
                    Log.i("web0",originalUri+"");

                    break;
                case 3012:

                    webView.loadUrl("javascript:sendPhoto('"+file.getPath()+"')");
                    Log.i("web0",file.getPath()+"");
                    break;
                case 3013:


                    break;

            }


        }
    }
}