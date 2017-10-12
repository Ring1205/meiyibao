package com.wmcd.myb.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.wmcd.myb.R;
import com.wmcd.myb.activity.LoginActivity;
import com.wmcd.myb.activity.MainTopActivity;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.util.ImageFilePath;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
public class AlbumFragment extends BaseFragment {
    /**
     * The constant TAG.
     */
    private static final String TAG = "AlbumFragment";
    /**
     * The File.
     */
    private File file;
    /**
     * 搞这个变量是为了解决拍照上传后无法在重新拍照上传的问题
     */
    private int i = 0;
    /**
     * The Web view.
     */
    @Bind(R.id.webview)
    WebView webView;

    /**
     * Sets layout resouce id.
     *
     * @return the layout resouce id
     */
    @Override
    protected int setLayoutResouceId() {
        return R.layout.activity_webview;
    }

    /**
     * Init view.
     */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        webView.loadUrl(UrlConfig.IP + "wapH5PageController/showEdit");
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);
        //允许混合内容 解决部分手机 加载不出https请求里面的http下的图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.addJavascriptInterface(this, "myb");
        webView.setWebChromeClient(mWebChromeClient);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                Log.i(TAG, "shouldOverrideUrlLoading url=" + url);
                view.loadUrl(url);
                return true;
            }
        });
        Log.i(TAG, "start"+UrlConfig.IP + "wapH5PageController/showEdit");

        //返回上一页
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键
                        webView.goBack();   //后退

                        //webview.goForward();//前进

                        return true;    //已处理
                    }
                }
                return false;
            }
        });

    }

    /**
     * The constant INPUT_FILE_REQUEST_CODE.
     */
    public static final int INPUT_FILE_REQUEST_CODE = 1;
    /**
     * The M upload message.
     */
    private ValueCallback<Uri> mUploadMessage;
    /**
     * The constant FILECHOOSER_RESULTCODE.
     */
    private final static int FILECHOOSER_RESULTCODE = 2;
    /**
     * The M file path callback.
     */
    private ValueCallback<Uri[]> mFilePathCallback;

    /**
     * The M camera photo path.
     */
    private String mCameraPhotoPath;

    /**
     * Create image file file.
     *
     * @return the file
     */

    @SuppressLint("SdCardPath")
    private File createImageFile() {
        //mCameraPhotoPath="/mnt/sdcard/tmp.png";
        file = new File(Environment.getExternalStorageDirectory() + "/", i + "tmp.png");
        mCameraPhotoPath = file.getAbsolutePath();
        //Uri uri = Uri.fromFile(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * The M web chrome client.
     */
    private WebChromeClient mWebChromeClient = new WebChromeClient() {


        public boolean onShowFileChooser(
                WebView webView, ValueCallback<Uri[]> filePathCallback,
                WebChromeClient.FileChooserParams fileChooserParams) {

            Log.d(TAG, "onShowFileChooser");
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }


            mFilePathCallback = filePathCallback;


            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    Log.e("WebViewSetting", "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    //file路径转换成url
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    System.out.println(mCameraPhotoPath);
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
                System.out.println(takePictureIntent);
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;
        }


        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Log.d(TAG, "openFileChooser1");

            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            getActivity().startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILECHOOSER_RESULTCODE);

        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            Log.d(TAG, "openFileChooser2");
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            getActivity().startActivityForResult(
                    Intent.createChooser(i, "Image Chooser"),
                    FILECHOOSER_RESULTCODE);
        }

        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.d(TAG, "openFileChooser3");
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            getActivity().startActivityForResult(Intent.createChooser(i, "Image Chooser"), AlbumFragment.FILECHOOSER_RESULTCODE);

        }
    };

    /**
     * Share h 5.share this webpage
     *
     * @param url     the url
     * @param title   the title
     * @param content the content
     * @param img     the img
     */
    @android.webkit.JavascriptInterface
    public void shareH5(final String url, final String title, final String content, final String img) {
        Log.e("WebViewActivity", url + "----" + img + "---" + title + "" + "" + content);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.mUser != null) {
                    showShare(url, title, content, img);
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
    }

    public void pausemusic(){
        if (webView!=null){
            webView.loadUrl("javascript:musicPause()");
        }
    }
    public  void startmusic(){
        if (webView!=null){
            webView.loadUrl("javascript:musicPlay()");
        }
    }
public void reload (){
    if (webView!=null)
    webView.reload();
}
    private void showShare(String url, String title, String content, String img) {

        ShareSDK.initSDK(context);

        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用

        oks.setTitle(title);
        oks.setText(content);

// url仅在微信（包括好友和朋友圈）中使用
        oks.setImageUrl(UrlConfig.IMG + img);
        oks.setUrl(url);
        oks.show(context);

    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//权限还没有授予，需要在这里写申请权限的代码
            Log.e("权限", "没有权限");
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            Log.e("权限", "有权限");
        }
        Log.d(TAG, "onActivityResult");

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            if (result != null) {
                String imagePath = ImageFilePath.getPath(context, result);
                if (!TextUtils.isEmpty(imagePath)) {
                    result = Uri.parse("file:///" + imagePath);
                }
            }
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == INPUT_FILE_REQUEST_CODE && mFilePathCallback != null) {
            // 5.0的回调
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        Log.d("camera_photo_path", mCameraPhotoPath);
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    Log.d("camera_dataString", dataString);
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            RxPermissions.getInstance(context)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {

                            } else {

                            }
                        }
                    });

            mFilePathCallback.onReceiveValue(results);
            i++;
            mFilePathCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }
}
