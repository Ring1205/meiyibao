package com.wmcd.myb.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.bumptech.glide.Glide;
import com.wmcd.myb.R;
import com.wmcd.myb.adapter.NextCoverageAdapter;
import com.wmcd.myb.adapter.PictureBottomAdapter;
import com.wmcd.myb.aliyun.PutObjectSamples;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.db.UrlToUuidDao;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.LogoModel;
import com.wmcd.myb.model.PreviewModel;
import com.wmcd.myb.model.SaveCoverage;
import com.wmcd.myb.util.BitmapUtil;
import com.wmcd.myb.util.OnBooleanListener;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.view.MyPopupWindow;
import com.wmcd.myb.view.MyTextView;
import com.wmcd.myb.view.MybPopupWindow;
import com.wmcd.myb.view.TouchImageView;
import com.wmcd.myb.wigdet.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMovementListener;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/25.
 */

public class NextPictureEditorActivity extends BaseActivity {
    @Bind(R.id.recyclerview_bottom)
    RecyclerView recyclerview_bottom;
    @Bind(R.id.fl_editor)
    FrameLayout fl_editor;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.iv_preview)
    ImageView iv_preview;
    @Bind(R.id.tv_card)
    TextView tv_card;//底部card文字
    @Bind(R.id.iv_card)
    ImageView iv_card;//底部card图片
    @Bind(R.id.rl_bottom_tucheng)
    RelativeLayout rl_bottom_tucheng;//图层列表布局
    @Bind(R.id.sm_recyclerview)
    SwipeMenuRecyclerView sm_recyclerview;//图层列表
    @Bind(R.id.iv_item)
    ImageView iv_item;

    private int pointer = 1102;//选中的图层
    private int mQRCursor = 1102;//选中的二维码
    private boolean mPboolean, mTboolean;//为“放在最”做判断：P为图片，T为文字

    private TouchImageView touchImg;//编辑中的图片
    private ImageView twoGoodsView;//二维码图片

    //编辑中的文字
    private String strColor = "#000000";
    private String strText = "文本丢失";
    private String strFont = "微软雅黑";
    private float strSize;
    private MyTextView mTextView;//编辑中的文字

    private PreviewModel.TemplateBean.PagesBean Pdata;
    private List<PreviewModel.TemplateBean.PagesBean.ObjectsBean> data;
    private PictureBottomAdapter pictureAdapter;
    private NextCoverageAdapter coverageAdapter;
    private float REAL_WIDTH, REAL_HEIGHT;//模板实际宽高
    private float CANVAS_WIDTH, CANCAS_HEIGHT;//画板宽高
    private float RTC_SCALE;
    private String drawUrl;//画板全图保存位置
    private List<String> fonts;//需要下载的字体
    private String filepath = Environment.getExternalStorageDirectory() + "/fonts/";//sd卡中的fonts文件夹

    private File photographFile = new File(MyApplication.imgPath, "bg.jpg");//拍照得到的图片文件

    private List<SaveCoverage> coverageList;//图层列表

    private UrlToUuidDao urlToUuidDao;//数据库工具类

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {//初次加载页面
                for (int i = 0; i < data.size(); i++) {
                    PreviewModel.TemplateBean.PagesBean.ObjectsBean model = data.get(i);
                    if (model.getType() == 1) {
                        if (model.getZoom() == 1) {
                            pointer = i;
                            touchImg = addImg(model.getWidth(), model.getHeight(), model.getX(), model.getY(), model.getImg() + "", model.getReplaceable(), model.getOid(), model.getMatrixBeans());
                        } else {
                            addImg(model.getWidth(), model.getHeight(), model.getX(), model.getY(), model.getImg() + "", model.getReplaceable(), model.getOid(), model.getMatrixBeans());
                        }
                    } else if (model.getType() == 2) {
                        addText(model.getText(), model.getColor(), model.getFontsize(), model.getX(), model.getY(), model.getFont(), model.getOid());
                    }
                }
                UiUtils.endnet();
            } else if (msg.what == 2) {//离开编辑页面
                final Intent intent = new Intent();
                Bundle bundle = new Bundle();
                for (int i1 = 0; i1 < Pdata.getObjects().size(); i1++) {
                    Pdata.getObjects().get(i1).setZ(i1);
                }
                bundle.putString("path", drawUrl);
                bundle.putSerializable("Pdata", Pdata);

                intent.putExtras(bundle);
                intent.setClass(NextPictureEditorActivity.this, NextPreviewActivity.class);
                setResult(1102, intent);
                NextPictureEditorActivity.this.finish();
            } else if (msg.what == 3) {
                Bundle bundle = msg.getData();
                Bitmap myBitmap = bundle.getParcelable("bitmap");
                final int ic = bundle.getInt("pointer");
                coverageList.get(ic).setBitmap(myBitmap);
                final TouchImageView touchImageView = (TouchImageView) coverageList.get(ic).getView();
                touchImageView.setImageBitmap(myBitmap);
                touchImageView.invalidate();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_editor_next);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        Pdata = (PreviewModel.TemplateBean.PagesBean) intent.getSerializableExtra("pageslist");
        data = Pdata.getObjects();

        REAL_HEIGHT = Pdata.getHeight();
        REAL_WIDTH = Pdata.getWidth();

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int w = windowManager.getDefaultDisplay().getWidth();
        CANVAS_WIDTH = w - (int) UiUtils.dpToPixels(20, this);
        RTC_SCALE = CANVAS_WIDTH / REAL_WIDTH;
        CANCAS_HEIGHT = RTC_SCALE * REAL_HEIGHT;

        strSize = CANVAS_WIDTH / RTC_SCALE / 18;

        //遍历所需下载的字体
        fonts = new ArrayList<>();
        for (int v = 0; v <= data.size(); v++) {
            if (v == data.size()) {
                String font = "微软雅黑";
                File file1 = new File(filepath + font + ".ttf");
                if (!file1.exists() && font != null) {
                    fonts.add(font);
                }
            } else {
                if (data.get(v) != null && data.get(v).getFont() != null) {
                    String font = data.get(v).getFont();
                    File file1 = new File(filepath + font + ".ttf");
                    if (!file1.exists() && font != null) {
                        fonts.add(font);
                    }
                }
            }
        }

        //判断未下载的字体，进行下载
        if (fonts.size() != 0) {
            openDownload();
        } else {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        urlToUuidDao = new UrlToUuidDao(this);

        coverageList = new ArrayList<>();

        pictureAdapter = new PictureBottomAdapter(this);
        pictureAdapter.setData(1, 0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview_bottom.setLayoutManager(linearLayoutManager);
        recyclerview_bottom.setAdapter(pictureAdapter);
        pictureAdapter.setPictureClickListener(new PictureBottomAdapter.PictureClickListener() {
            @Override
            public boolean onClick(int b, int i) {
                //图文编辑条目的功能
                OnBottomTools(b, i);
                if (pointer != 1102) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        fl_editor.measure(0, 0);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = (int) CANVAS_WIDTH;
        params.height = (int) CANCAS_HEIGHT;
        fl_editor.setLayoutParams(params);

        rl_bottom_tucheng.setVisibility(View.GONE);
        sm_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sm_recyclerview.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(3, this)));

        //在示例画板添加全图
        processCardPicture(this, UrlConfig.IMG + Pdata.getIcon(), UiUtils.dip2px(this, 55), 1);
    }

    /**
     * 示例画板展示图片或文字
     *
     * @param context
     * @param url
     * @param width
     */
    private void processCardPicture(Context context, String url, int width, int type) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = UiUtils.dip2px(this, 55);
        params.height = UiUtils.dip2px(this, 55);
        if (type == 1) {//展示图片
            tv_card.setVisibility(View.GONE);
            iv_card.setVisibility(View.VISIBLE);

            if (pointer != 1102 && coverageList.get(pointer).getBitmap() != null) {
                iv_card.setImageBitmap(coverageList.get(pointer).getBitmap());
            } else {
                UiUtils.loadImage(context, url, iv_card, width / 2);
            }

            iv_card.setLayoutParams(params);
        } else if (type == 2) {//展示文字
            tv_card.setVisibility(View.VISIBLE);
            iv_card.setVisibility(View.GONE);
            tv_card.setText(strText);
            tv_card.setLayoutParams(params);
        }
    }

    /**
     * 添加图片
     *
     * @param width
     * @param height
     * @param left
     * @param top
     * @param url
     * @param replaceable
     * @return
     */
    private TouchImageView addImg(final int width, final int height, int left, int top, final String url, final int replaceable, final int oid, float[] floats) {
        SaveCoverage saveCoverage = new SaveCoverage();
        int ic = 0;
        for (int i = 0; i < data.size(); i++) {
            if (oid == data.get(i).getOid()) {
                ic = i;
            }
        }
        if (data.get(ic).getZoom() == 1) {
            final TouchImageView tTouchImg = new TouchImageView(this);
            tTouchImg.setOntouch(new TouchImageView.onTouchImage() {
                @Override
                public void onTouch(boolean b) {
                    //判断scrollView的滑动事件是否屏蔽掉
                    scrollView.requestDisallowInterceptTouchEvent(b);
                }

                @Override
                public void actionUp(Bitmap bitmap, Matrix matrix) {
                    //保存移动后的bitmap
                    if (touchImg != null && pointer != 1102) {
                        //拷贝一个bitmap
//                        Bitmap bitmapCopy = copy(bitmap);
                        coverageList.get(pointer).setBitmap(bitmap);
                        coverageList.get(pointer).setMatrix(matrix);

                        //修改添加data中的图片参数
                        modifData();
                    }
                }

                @Override
                public void startDown() {
                    //替换底部菜单栏
                    selection(oid,null,tTouchImg);
                }
            });

            if (floats == null) {
                float[] f = new float[9];
                f[0] = 1;
                f[1] = 0;
                f[2] = (data.get(ic).getX() * RTC_SCALE);
                f[3] = 0;
                f[4] = 1;
                f[5] = (data.get(ic).getY() * RTC_SCALE);
                f[6] = 0;
                f[7] = 0;
                f[8] = 1;
                tTouchImg.setMatrix(f);
            } else if (floats != null) {
                tTouchImg.setMatrix(floats);
            }
            tTouchImg.setMyBoolean(true);

            fl_editor.addView(tTouchImg);

            saveCoverage.setView(tTouchImg);
            coverageList.add(saveCoverage);

            //获取网络数据转成bitmap
            final int finalIc = ic;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap myBitmap = Glide.with(NextPictureEditorActivity.this)
                                .load(UrlConfig.IMG + url + "?x-oss-process=image/format,webp/resize,w_" + width)
                                .asBitmap() //必须
                                .centerCrop()
                                .into((int) (width * RTC_SCALE), (int) (height * RTC_SCALE))
                                .get();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("pointer", finalIc);
                        bundle.putParcelable("bitmap", myBitmap);
                        message.what = 3;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            return tTouchImg;
        } else {
            final ImageView img = new ImageView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = (int) (width * RTC_SCALE);
            params.height = (int) (height * RTC_SCALE);
            params.leftMargin = (int) (left * RTC_SCALE);
            params.topMargin = (int) (top * RTC_SCALE);
            fl_editor.addView(img);
            img.setLayoutParams(params);
            if (replaceable == 1) {
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < data.size(); i++) {
                            if (oid == data.get(i).getOid()) {
                                mQRCursor = i;
                            }
                        }
                        //添加二维码
                        twoGoodsView = img;
                        UiUtils.getPictureFromPhoto(NextPictureEditorActivity.this, 3013);
                    }
                });
            }
            UiUtils.loadImage(this, UrlConfig.IMG + url, img, (int) (width * RTC_SCALE) / 2);
            saveCoverage.setView(img);
            coverageList.add(saveCoverage);
            return null;
        }
    }

    /**
     * 修改可变图片数据
     */
    private void modifData() {
        data.get(pointer).setAngle(touchImg.getAngle());
        data.get(pointer).setX((int) (touchImg.getDownX() / RTC_SCALE));
        data.get(pointer).setY((int) (touchImg.getDownY() / RTC_SCALE));
        data.get(pointer).setScale(touchImg.getScaleWidth() / RTC_SCALE / data.get(pointer).getWidth());

        float[] f = touchImg.getfMatrix();
        data.get(pointer).setMatrixBeans(f);
    }

    /**
     * 添加文字
     *
     * @param str
     * @param color
     * @param size
     * @param left
     * @param top
     * @param fonts
     * @return
     */

    private MyTextView addText(final Object str, final Object color, final String size, float left, float top, final String fonts, final int oid) {
        Log.e("PictureEditorActivity", str + "==" + color + "==" + size + "==" + left + "==" + top + "==" + fonts);
        SaveCoverage saveCoverage = new SaveCoverage();
        final MyTextView text = new MyTextView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) (left * RTC_SCALE);
        params.topMargin = (int) (top * RTC_SCALE);
        text.setLayoutParams(params);
        String s = (String) str;
        s = s.replaceAll("\\\\n", "\\\n");
        for (int i1 = 0; i1 < data.size(); i1++) {
            if (data.get(i1).getOid() == oid) {
                data.get(i1).setText(s);
            }
        }
        try {
            text.setText(s);
        } catch (Exception e) {
            Toast.makeText(this, "文字为空", Toast.LENGTH_LONG).show();
        }
        try {
            text.setTextColor(Color.parseColor(String.valueOf(color)));
        } catch (Exception e) {
            Toast.makeText(this, "文字颜色为空", Toast.LENGTH_LONG).show();
        }
        try {
            text.setTextSize(UiUtils.px2dip(this, (RTC_SCALE * Float.parseFloat(size))));
        } catch (Exception e) {
            Toast.makeText(this, "文字大小为空", Toast.LENGTH_LONG).show();
        }
        if (fonts != null) {
            File filef = new File(filepath + fonts + ".ttf");
            try {
                Typeface tf = Typeface.createFromFile(filef);
                text.setTypeface(tf);//设置字体
            } catch (Exception e) {
                Toast.makeText(this, "字体下载失败", Toast.LENGTH_LONG).show();
            }
        }
        fl_editor.addView(text);

        text.setMyBoolean(true);

        text.setonClick(new MyTextView.MyTextViewInterface() {
            @Override
            public void onClickTextView() {
                //点击文字事件
//                selectionText(oid, text);
            }

            @Override
            public void onTouchTextView() {
                int[] location = new int[2];
                text.getLocationOnScreen(location);
                data.get(pointer).setX((int) (text.getLeft() / RTC_SCALE));
                data.get(pointer).setY((int) (text.getTop() / RTC_SCALE));
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = text.getLeft();
                params.topMargin = text.getTop();
                text.setLayoutParams(params);
            }

            //手指首次触摸到文字
            @Override
            public void startTouchTextView() {
                selection(oid, text, null);
            }
        });
        saveCoverage.setView(text);
        coverageList.add(saveCoverage);
        return text;
    }

    /**
     * 选中文字控件
     */
    private void selection(int oid, MyTextView text, TouchImageView view) {
        scrollView.requestDisallowInterceptTouchEvent(true);
        for (int i1 = 0; i1 < data.size(); i1++) {
            if (data.get(i1).getOid() == oid) {
                pointer = i1;
            }
        }
        if (text != null) {
            if (pointer == 0) {
                pictureAdapter.setData(2, 2);
                mTboolean = true;
            } else {
                pictureAdapter.setData(2, 1);
                mTboolean = false;
            }
        } else if (view != null) {
            if (pointer == 0) {
                pictureAdapter.setData(1, 2);
                mPboolean = false;
            } else {
                pictureAdapter.setData(1, 1);
                mPboolean = true;
            }
        }
        pictureAdapter.notifyDataSetChanged();

        //将之前的文字控件制空
        if (mTextView != null) {
            mTextView.setBackground(null);
            mTextView = null;
        }
        //取消图片的触摸事件和制空图片控件
        if (touchImg != null) {
            touchImg.setBackground(null);
            touchImg = null;
        }

        if (text != null) {
            if (data.get(pointer).getColor() != null) {
                strColor = data.get(pointer).getColor();
            }
            if (data.get(pointer).getText() != null) {
                strText = data.get(pointer).getText();
            }
            if (data.get(pointer).getFont() != null) {
                strFont = data.get(pointer).getFont();
            }
            if (data.get(pointer).getFontsize() != null) {
                strSize = Float.parseFloat(data.get(pointer).getFontsize());
            } else {
                strSize = UiUtils.dip2px(NextPictureEditorActivity.this, text.getTextSize()) / RTC_SCALE;
            }
            mTextView = text;
            mTextView.setBackgroundResource(R.drawable.canv_21);
            //card文字展示
            processCardPicture(NextPictureEditorActivity.this, null, 0, 2);
        } else if (view != null) {
            touchImg = view;
            processCardPicture(this, UrlConfig.IMG + data.get(pointer).getImg(), UiUtils.dip2px(this, 55), 1);
        }
    }

    /**
     * 根据uri获取图片路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private OSS oss;

    /**
     * 上传图片到阿里云服务器
     *
     * @param cursor
     * @param url
     */
    public void getUUID(final int cursor, final String url) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(UrlConfig.accessKeyId, UrlConfig.accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), UrlConfig.endpoint, credentialProvider, conf);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uuid;
                File file = new File(url);
                String uu = urlToUuidDao.find(url);
                if (uu != null){
                    uuid = uu;
                }else {
                    uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
                    if (file.exists()) {
                        urlToUuidDao.add(url,uuid);
                        new PutObjectSamples(oss, UrlConfig.testBucket, uuid, url).asyncPutObjectFromLocalFile();
                    } else {
                        Toast.makeText(getApplicationContext(), "图片上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
                if (cursor != 1102) {
                    data.get(cursor).setImg(uuid);
                } else {
                    Pdata.setIcon(uuid);
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    /**
     * 获取原图宽度
     *
     * @param path
     */
    public int getWidth(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outWidth;
    }

    /**
     * 获取原图高度
     *
     * @param path
     */
    public int getHeight(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outHeight;
    }

    /**
     * 添加替换图片的返回
     *
     * @param requestCode
     * @param resultCode
     * @param iData
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent iData) {
        super.onActivityResult(requestCode, resultCode, iData);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //相册返回
                case 3011:
                    Uri originalUri = iData.getData();
                    Bitmap photo = BitmapUtil.compressBitmap(null, null, this, originalUri);
                    if (photo == null){
                        Toast.makeText(this,"图片已损坏",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    touchImg.setImageResource(R.drawable.err_img);
                    touchImg.setImageBitmap(photo);
                    touchImg.requestLayout();
                    coverageList.get(pointer).setBitmap(photo);
                    processCardPicture(NextPictureEditorActivity.this, null, 0, 1);

                    //获取图片路径
                    String mUri = getRealFilePath(this, originalUri);
                    getUUID(pointer, mUri);
                    //原图片宽度
                    int mWidth = getWidth(mUri);
                    data.get(pointer).setScale(touchImg.getScaleWidth() / RTC_SCALE / mWidth);
                    data.get(pointer).
                            setWidth(mWidth);
                    data.get(pointer).setHeight(getHeight(mUri));
                    break;
                //拍照返回
                case 3012:
                    BitmapUtil.compressImage(photographFile.getPath(), photographFile.getPath(), 50);
                    Bitmap outPhoto = BitmapUtil.convertToBitmap(photographFile.getPath(), 0, 0);
                    touchImg.setImageResource(R.drawable.err_img);
                    touchImg.setImageBitmap(outPhoto);
                    touchImg.requestLayout();
                    coverageList.get(pointer).setBitmap(outPhoto);
                    processCardPicture(NextPictureEditorActivity.this, null, 0, 1);

                    getUUID(pointer, photographFile.getPath());
                    int oWidth = getWidth(photographFile.getPath());
                    data.get(pointer).setScale(touchImg.getScaleWidth() / RTC_SCALE / oWidth);
                    data.get(pointer).setWeight(oWidth + "");
                    data.get(pointer).setHeight(getHeight(photographFile.getPath()));
                    break;
                //二维码图片替换
                case 3013:
                    Uri twOriginalUri = iData.getData();
                    Bitmap photoTwo = BitmapUtil.compressBitmap(null, null, this, twOriginalUri);
                    if (photoTwo == null){
                        Toast.makeText(this,"图片已损坏",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    twoGoodsView.setImageBitmap(photoTwo);
                    processCardPicture(NextPictureEditorActivity.this, null, 0, 1);

                    String path = getRealFilePath(this, twOriginalUri);
                    getUUID(mQRCursor, path);
                    float f = (float) data.get(mQRCursor).getWidth() / (float) getWidth(path);
                    data.get(mQRCursor).setScale(f);
                    break;
                default:
                    Toast.makeText(MyApplication.getContext(), "未添加图片", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (resultCode == 1 && requestCode == 1) {//一键logo
            LogoModel.ListBean bean = (LogoModel.ListBean) iData.getSerializableExtra("img");
            addNewCoverage(null, null, data.size(), 1, bean.getImg(), (int) (bean.getWidth() / RTC_SCALE / 2), (int) (bean.getHeight() / RTC_SCALE / 2));
            //更改指针
            pointer = data.size() - 1;

            data.get(pointer).setWidth(500);
        }
    }

    /**
     * 下载字体
     */
    private void openDownload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否下载所需资源");
        builder.setCancelable(false);//设置点击对话框外部不关闭对话框
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UiUtils.startnet(NextPictureEditorActivity.this);
                onPermissionRequests(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new OnBooleanListener() {
                    @Override
                    public void onClick(boolean bln) {
                        if (bln) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    for (String s : fonts) {
                                        try {
                                            String urlStr = URLEncoder.encode(s + ".zip", "UTF-8");
                                            urlStr = urlStr.replaceAll("\\+", "%20");
                                            Log.e("PictureEditorActivity", urlStr);
                                            UiUtils.downLoadFromUrl(UrlConfig.FONT + urlStr, filepath + s + ".ttf");
                                        } catch (IOException e) {
                                            Log.e("PictureEditorActivity", e.getMessage());
                                            Toast.makeText(NextPictureEditorActivity.this, s + "字体下载失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    Message message = new Message();
                                    message.what = 1;
                                    handler.sendMessage(message);
                                }
                            }).start();
                        } else {
                            Toast.makeText(NextPictureEditorActivity.this, "请在应用管理中打开存储权限！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }

    /**
     * 图文编辑条目的点击事件
     *
     * @param b
     * @param i
     */
    private void OnBottomTools(int b, int i) {
        if (b == 1) {   //图片编辑条目
            switch (i) {
                case 0: //放在最上或下面
                    if (touchImg != null && pointer != 1102) {
                        if (!mPboolean) {//跳转到最上层
                            mPboolean = !mPboolean;
                            processChange(pointer, true);
                        } else {//跳转到最下层
                            processChange(pointer, false);
                            mPboolean = !mPboolean;
                        }
                    } else {
                        Toast.makeText(this, "请选择可编辑图片", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1: //换图
                    if (touchImg != null) {
                        showPopupWindow();
                    } else {
                        Toast.makeText(this, "请选择可编辑图片", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2: //抠图
                    Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                    break;
                case 3: //旋转
                    if (touchImg != null) {
                        touchImg.matrix1Rotate(45f);
                        modifData();
                    }
                    break;
                case 4: //放大
                    if (touchImg != null) {
                        touchImg.matrix1Scale(1.1f);
                        modifData();
                    }
                    break;
                case 5: //缩小
                    if (touchImg != null) {
                        touchImg.matrix1Scale(0.9f);
                        modifData();
                    }
                    break;
                case 6: //删除
                    if (pointer != 1102)
                        removeView(pointer);
                    else
                        Toast.makeText(this, "请选择删除图文", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (b == 2) { //文字编辑条目
            switch (i) {
                case 0: //放在最上或下面
                    if (mTextView == null && pointer != 1102) {
                        Toast.makeText(this, "请选择文本", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!mTboolean) {//跳转到最下层
                            processChange(pointer, false);
                            mTboolean = !mTboolean;
                        } else {//跳转到最上层
                            processChange(pointer, true);
                            mTboolean = !mTboolean;
                        }
                    }
                    break;
                case 1: //编辑
                    if (mTextView == null || pointer == 1102) {
                        Toast.makeText(this, "请选择文本", Toast.LENGTH_SHORT).show();
                    } else {
                        MybPopupWindow mybPopupWindow = new MybPopupWindow(this, strText, strColor, strFont);
                        mybPopupWindow.showPopupwindow();
                        mybPopupWindow.setOnEditText(new MybPopupWindow.OnEditText() {
                            @Override
                            public void getEditText(String string) {
                                mTextView.setText(string);
                                strText = string;
                                data.get(pointer).setText(string);
                                //card文字展示
                                processCardPicture(NextPictureEditorActivity.this, null, 0, 2);
                            }

                            @Override
                            public void setTextStyle(String font) {

                                final File fileo = new File(filepath + font + ".ttf");
                                strFont = font;
                                try {
                                    Typeface tf = Typeface.createFromFile(fileo);
                                    mTextView.setTypeface(tf);//设置字体
                                } catch (RuntimeException e) {
                                    Log.e("PictureEditorActivity", e.getMessage());
                                }
                                data.get(pointer).setFont(font);
                            }

                            @Override
                            public void setTextColor(String color) {
                                strColor = color;
                                mTextView.setTextColor(Color.parseColor(color));
                                data.get(pointer).setColor(color);
                            }
                        });
                    }
                    break;
                case 2: //放大
                    if (mTextView == null || pointer == 1102) {
                        Toast.makeText(this, "请选择文本", Toast.LENGTH_SHORT).show();
                    } else {
                        strSize = strSize + 3;
                        mTextView.setTextSize(UiUtils.px2dip(this, strSize * RTC_SCALE));
                        data.get(pointer).setFontsize(strSize + "");
                    }
                    break;
                case 3: //缩小
                    if (mTextView == null || pointer == 1102) {
                        Toast.makeText(this, "请选择文本", Toast.LENGTH_SHORT).show();
                    } else {
                        if (strSize > 0) {
                            strSize = strSize - 3;
                            mTextView.setTextSize(UiUtils.px2dip(this, strSize * RTC_SCALE));
                            data.get(pointer).setFontsize(strSize + "");
                        }
                    }
                    break;
                case 4: //删除
                    if (mTextView == null || pointer == 1102) {
                        Toast.makeText(this, "请选择可编辑文字", Toast.LENGTH_SHORT).show();
                    } else {
                        removeView(pointer);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @OnClick({R.id.tv_back, R.id.tv_keep, R.id.rl_top_01, R.id.rl_top_02, R.id.iv_aid, R.id.iv_YES, R.id.iv_delet, R.id.rl_bottom})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_keep:
                if (mTextView != null)
                    mTextView.setBackground(null);

                pointer = 1102;
                onPermissionRequests(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new OnBooleanListener() {
                    @Override
                    public void onClick(boolean bln) {
                        if (bln) {
                            drawUrl = UiUtils.saveBitmap(NextPictureEditorActivity.this, UiUtils.createViewBitmap(fl_editor), "DCIM");
                            getUUID(pointer, drawUrl);
                        } else {
                            Toast.makeText(NextPictureEditorActivity.this, "请在应用管理中打开存储权限！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.rl_top_01://预览
                iv_preview.setVisibility(View.VISIBLE);
                if (mTextView != null) {
                    mTextView.setBackground(null);
                }
                iv_preview.setImageBitmap(UiUtils.createViewBitmap(fl_editor));
                iv_preview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iv_preview.setVisibility(View.GONE);
                        if (mTextView != null) {
                            mTextView.setBackgroundResource(R.drawable.canv_21);
                        }
                    }
                });
                break;
            case R.id.rl_top_02://一键logo
                if (mTextView != null) {
                    mTextView.setBackground(null);
                }
                startActivityForResult(new Intent(this, LogoActivity.class), 1);
                break;
            case R.id.iv_aid:   //添加图文
                //用来判断添加图片还是文字，data.get(pointer).getType()，添加图片=1，添加文字=2
                //添加文字
                addNewCoverage("编辑文本", "#ffff01", data.size(), 2, null, 0, 0);
                if (pointer == 0) {
                    pictureAdapter.setData(2, 2);
                    mTboolean = true;
                } else {
                    pictureAdapter.setData(2, 1);
                    mTboolean = false;
                }
                pictureAdapter.notifyDataSetChanged();
                MybPopupWindow mybPopupWindow = new MybPopupWindow(this, strText, "#ffff01", strFont);
                data.get(pointer).setColor("#ffff01");
                mybPopupWindow.showPopupwindow();
                mybPopupWindow.setOnEditText(new MybPopupWindow.OnEditText() {
                    @Override
                    public void getEditText(String string) {
                        mTextView.setText(string);
                        strText = string;
                        data.get(pointer).setText(string);
                    }

                    @Override
                    public void setTextStyle(String font) {
                        final File fileo = new File(filepath + font + ".ttf");
                        strFont = font;
                        try {
                            Typeface tf = Typeface.createFromFile(fileo);
                            mTextView.setTypeface(tf);//设置字体
                        } catch (RuntimeException e) {
                            Log.e("PictureEditorActivity", e.getMessage());
                        }
                        data.get(pointer).setFont(font);
                    }

                    @Override
                    public void setTextColor(String color) {
                        strColor = color;
                        mTextView.setTextColor(Color.parseColor(color));
                        data.get(pointer).setColor(color);
                    }
                });
                break;
            case R.id.iv_YES://图层列表“完成”事件
                if (pointer != 1102) {
                    int type = data.get(pointer).getType();
                    rl_bottom_tucheng.setVisibility(View.GONE);
                    if (type == 1) {
                        processCardPicture(this, UrlConfig.IMG + data.get(pointer).getImg(), UiUtils.dip2px(this, 55), 1);
                        if (data.get(pointer).getZoom() == 1) {
                            touchImg = (TouchImageView) coverageList.get(pointer).getView();
                        }
                        //图片功能列表
                        if (pointer == 0) {
                            pictureAdapter.setData(1, 2);
                            mPboolean = false;
                        } else {
                            pictureAdapter.setData(1, 1);
                            mPboolean = true;
                        }
                        pictureAdapter.notifyDataSetChanged();
                    } else if (type == 2) {
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        mTextView = (MyTextView) coverageList.get(pointer).getView();
                        mTextView.setMyBoolean(true);
                        mTextView.setBackgroundResource(R.drawable.canv_21);
                        strFont = data.get(pointer).getFont();
                        strColor = data.get(pointer).getColor();
                        strText = data.get(pointer).getText();
                        strSize = Float.parseFloat(data.get(pointer).getFontsize());
                        //card展示图
                        processCardPicture(this, null, 0, 2);
                        //文字功能列表
                        if (pointer != 0) {
                            pictureAdapter.setData(2, 1);
                            mTboolean = false;
                        } else {
                            pictureAdapter.setData(2, 2);
                            mTboolean = true;
                        }
                        pictureAdapter.notifyDataSetChanged();
                    }

                    for (int i = 0; i < coverageList.size(); i++) {
                        if (data.get(i).getType() == 1 && data.get(i).getZoom() == 1) {
//                            TouchImageView touchImageView1 = (TouchImageView) coverageList.get(i).getView();
                        } else if (data.get(i).getType() == 2) {
                            MyTextView mTextView = (MyTextView) coverageList.get(i).getView();
                            mTextView.setMyBoolean(true);
                        }
                    }

                } else {
                    Toast.makeText(this, "请选择图层进行编辑", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_delet://图层列表删除图层事件
                if (pointer != 1102) {
                    removeView(pointer);
                    coverageAdapter.getData(data, coverageList, pointer);
                    coverageAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "请选择删除图层", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_bottom://展现图层列表布局
                rl_bottom_tucheng.setVisibility(View.VISIBLE);
                coverageAdapter = new NextCoverageAdapter(this);
                sm_recyclerview.setAdapter(coverageAdapter);
                sm_recyclerview.setLongPressDragEnabled(true);// 开启长按拖拽。
                sm_recyclerview.setItemViewSwipeEnabled(true);// 开启滑动删除。
                sm_recyclerview.setOnItemMovementListener(onItemMovementListener);
                sm_recyclerview.setOnItemMoveListener(onItemMoveListener);

                if (mTextView != null) {
                    mTextView.setBackground(null);
                    mTextView.setMyBoolean(false);
                }
                mTextView = null;
                touchImg = null;
                pointer = 1102;

                coverageAdapter.getData(data, coverageList, pointer);
                coverageAdapter.notifyDataSetChanged();

                coverageAdapter.setCoverageOnclick(new NextCoverageAdapter.CoverageOnclick() {
                    @Override
                    public void setCoverageItem(int position) {
                        pointer = position;
                    }
                });

                for (int i = 0; i < coverageList.size(); i++) {
                    if (data.get(i).getType() == 1 && data.get(i).getZoom() == 1) {
                        TouchImageView touchImageView1 = (TouchImageView) coverageList.get(i).getView();
                    } else if (data.get(i).getType() == 2) {
                        MyTextView mTextView = (MyTextView) coverageList.get(i).getView();
                        mTextView.setMyBoolean(false);
                    }
                }
                break;
        }
    }

    /**
     * 根据原位图生成一个新的位图，并将原位图所占空间释放
     *
     * @param srcBmp 原位图
     * @return 新位图 bitmap
     */
    public static Bitmap copy(Bitmap srcBmp) {
        Bitmap destBmp;
        try {
            //创建一个临时文件
            File file = new File("/mnt/sdcard/temp/tmp.txt");
            file.getParentFile().mkdirs();

            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            int width = srcBmp.getWidth();
            int height = srcBmp.getHeight();

            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, width * height * 4);
            //将位图信息写进buffer
            srcBmp.copyPixelsToBuffer(map);

            //创建一个新的位图
            destBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            map.position(0);
            //从临时缓冲中拷贝位图信息
            destBmp.copyPixelsFromBuffer(map);

            channel.close();
            randomAccessFile.close();
        } catch (Exception ex) {
            destBmp = null;
        }
        return destBmp;
    }

    /**
     * Show popup window.
     */
    private void showPopupWindow() {
        final MyPopupWindow popupWindow = new MyPopupWindow(this, "拍照", "从手机相册选择");
        popupWindow.setonclick(new MyPopupWindow.SetOnClickLinener() {
            @Override
            public void OnClick01() {   //拍照
                onPermissionRequests(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new OnBooleanListener() {
                    @Override
                    public void onClick(boolean bln) {
                        if (bln) {
                            UiUtils.getPictureFormCamera(NextPictureEditorActivity.this, 3012, photographFile);
                        } else {
                            Toast.makeText(NextPictureEditorActivity.this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                popupWindow.dismiss();
            }

            @Override
            public void OnClick02() {   //从手机相册选择
                UiUtils.getPictureFromPhoto(NextPictureEditorActivity.this, 3011);
                popupWindow.dismiss();
            }

            @Override
            public void OnClick03() {
            }
        });
    }

    private int oid = 1;

    /**
     * 添加图层
     *
     * @param str
     * @param textColor
     * @param coverage
     * @param type
     * @param url
     */
    private void addNewCoverage(String str, String textColor, int coverage, int type, String url, int width, int height) {
        PreviewModel.TemplateBean.PagesBean.ObjectsBean textModel = new PreviewModel.TemplateBean.PagesBean.ObjectsBean();
        textModel.setImg(null);
        textModel.setColor(null);
        textModel.setFontsize(null);
        textModel.setWeight(null);
        textModel.setCreatedate(null);
        textModel.setPid(0);
        textModel.setOid(oid);
        textModel.setType(type);
        textModel.setTransparency(0);
        textModel.setWidth(width);
        textModel.setX(0);
        textModel.setY(0);
        textModel.setZ(coverage);
        textModel.setText(null);
        textModel.setHeight(height);
        textModel.setFont(null);
        pointer = coverage;
        if (type == 2) {
            if (mTextView != null) {
                mTextView.setBackground(null);
            }
            textModel.setFontsize(CANVAS_WIDTH / RTC_SCALE / 18 + "");
            textModel.setFont("微软雅黑");
            textModel.setText(str);
            textModel.setColor(textColor);
            data.add(coverage, textModel);
            MyTextView text = addText(str, textColor, CANVAS_WIDTH / RTC_SCALE / 18 + "", 0, 0, "微软雅黑", oid);
            mTextView = text;
            strText = str;
            strColor = textColor;
            strSize = CANVAS_WIDTH / RTC_SCALE / 18;
            mTextView.setMyBoolean(true);
            mTextView.setBackgroundResource(R.drawable.canv_21);
        } else if (type == 1) {
            textModel.setZoom(1);
            textModel.setImg(url);
            textModel.setReplaceable(1);
            textModel.setScale(1);
            data.add(coverage, textModel);
            touchImg = addImg(width, height, 0, 0, url, 0, oid, textModel.getMatrixBeans());
            //针对添加的logo的功能板块
            touchImg.setMyBoolean(false);
        }
        oid++;
    }

    /**
     * 当Item被移动之前。
     */
    public OnItemMovementListener onItemMovementListener = new OnItemMovementListener() {
        /**
         * 当Item在移动之前，获取拖拽的方向。
         * @param recyclerView     {@link RecyclerView}.
         * @param targetViewHolder target ViewHolder.
         * @return
         */
        @Override
        public int onDragFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder) {
            // 我们让上锁图层不能拖拽。
            int i = targetViewHolder.getAdapterPosition();
            if (data.get(i).getZoom() != 1 && data.get(i).getType() == 1) {
                return OnItemMovementListener.INVALID;// 返回无效的方向。
            }

//            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//            if (layoutManager instanceof LinearLayoutManager) {// 如果是LinearLayoutManager。
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
//                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {// 横向的List。
//                    return (OnItemMovementListener.LEFT | OnItemMovementListener.RIGHT); // 只能左右拖拽。
//                } else {// 竖向的List。
//                    return OnItemMovementListener.UP | OnItemMovementListener.DOWN; // 只能上下拖拽。
//                }
//            }
//            else if (layoutManager instanceof GridLayoutManager) {// 如果是Grid。
//                return OnItemMovementListener.LEFT | OnItemMovementListener.RIGHT | OnItemMovementListener.UP |
//                        OnItemMovementListener.DOWN; // 可以上下左右拖拽。
//            }


//            return ItemTouchHelper.START;// 返回无效的方向。
            return OnItemMovementListener.LEFT | OnItemMovementListener.RIGHT | OnItemMovementListener.UP | OnItemMovementListener.DOWN;
        }

        @Override
        public int onSwipeFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder) {
            // 我们让上锁图层不能滑动删除。
            int i = targetViewHolder.getAdapterPosition();
            if (data.get(i).getZoom() != 1 && data.get(i).getType() == 1) {
                return OnItemMovementListener.INVALID;// 返回无效的方向。
            }

//            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//            if (layoutManager instanceof LinearLayoutManager) {// 如果是LinearLayoutManager
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
//                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {// 横向的List。
//                    return OnItemMovementListener.UP | OnItemMovementListener.DOWN; // 只能上下滑动删除。
//                } else {// 竖向的List。
//                    return OnItemMovementListener.LEFT | OnItemMovementListener.RIGHT; // 只能左右滑动删除。
//                }
//            }
            return OnItemMovementListener.INVALID;// 其它均返回无效的方向。
//            return OnItemMovementListener.LEFT | OnItemMovementListener.RIGHT | OnItemMovementListener.UP | OnItemMovementListener.DOWN;
        }
    };
    /**
     * 当Item移动的时候监听。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            // 当Item被拖拽的时候，交换Item的位置。
            processChange(fromPosition, toPosition);

            coverageAdapter.getData(data, coverageList, pointer);
            coverageAdapter.notifyDataSetChanged();

            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            removeView(position);

            coverageAdapter.getData(data, coverageList, pointer);
            coverageAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 图层列表中的图层位置交换
     *
     * @param fromPosition
     * @param toPosition
     */
    private void processChange(int fromPosition, int toPosition) {
        //替换data数据
        Collections.swap(data, fromPosition, toPosition);
        //替换bitmap集合
        Collections.swap(coverageList, fromPosition, toPosition);
        //刷新界面
        initRefreshDisplay();
        //更改指针
        if (pointer == fromPosition) {
            pointer = toPosition;
        } else if (pointer == toPosition) {
            pointer = fromPosition;
        }
    }

    /**
     * 刷新界面
     */
    private void initRefreshDisplay() {
        fl_editor.removeAllViews();
        for (SaveCoverage saveCoverage : coverageList) {
            fl_editor.addView(saveCoverage.getView());
        }
    }

    /**
     * 图层最上与最下的调动
     *
     * @param cursor      指定的图层光标
     * @param toporbottom true是跳转到最上层，false是跳转到最下层
     */
    private void processChange(int cursor, boolean toporbottom) {
        if (cursor != 1102) {
            if (toporbottom) {
                processCoverage(cursor, data.size() - 1);
            } else {
                processCoverage(cursor, 0);
            }
        }
    }

    /**
     * 交换图层
     *
     * @param getData
     * @param addData
     */
    private void processCoverage(int getData, int addData) {
        //替换data数据
        PreviewModel.TemplateBean.PagesBean.ObjectsBean objectsBean = data.get(getData);
        data.remove(getData);
        data.add(addData, objectsBean);

        //替换图层集合
        SaveCoverage saveCoverage = coverageList.get(getData);
        coverageList.remove(getData);
        coverageList.add(addData, saveCoverage);

        //刷新界面
        initRefreshDisplay();
        //更改指针
        pointer = addData;
    }

    /**
     * 删除并刷新图层
     *
     * @param position
     */
    private void removeView(int position) {
        if (pointer != 1102) {
            if (data.get(position).getType() == 2) {
                if (mTextView != null) {
                    mTextView.setMyBoolean(false);
                    mTextView.setBackground(null);
                    mTextView.setText("");
                    mTextView = null;
                }
            } else if (data.get(position).getType() == 1) {
                if (touchImg != null) {
                    touchImg.setBackground(null);
                    touchImg = null;
                }
            }

            data.remove(position);
            coverageList.remove(position);
            pointer = 1102;

            processCardPicture(this, UrlConfig.IMG + Pdata.getIcon(), UiUtils.dip2px(this, 55), 1);

            initRefreshDisplay();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && iv_preview.getVisibility() != View.GONE) {
            iv_preview.setVisibility(View.GONE);
            if (mTextView != null) {
                mTextView.setBackgroundResource(R.drawable.canv_21);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
