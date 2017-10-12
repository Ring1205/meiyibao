package com.wmcd.myb.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wmcd.myb.R;
import com.wmcd.myb.adapter.CoverageAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.PreviewModel;
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
import java.net.URL;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wmcd.myb.util.UiUtils.getZoomXY;

/**
 * Created by 邓志宏 on 2017/2/27.图片编辑页面
 */
public class PictureEditorActivity extends BaseActivity {
    @Bind(R.id.fl_editor)
    FrameLayout fl_editor;
    @Bind(R.id.tv_keep)
    TextView tv_keep;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.iv_picture_botton)
    RelativeLayout iv_picture_botton;
    @Bind(R.id.ll_02)
    LinearLayout ll_02;
    @Bind(R.id.iv_aid)
    ImageView iv_aid;
    @Bind(R.id.rl_bottom_card)
    CardView rl_bottom_card;
    @Bind(R.id.recyclerview_bottom)
    SwipeMenuRecyclerView recyclerview_bottom;
    @Bind(R.id.rl_bottom_tucheng)
    RelativeLayout rl_bottom_tucheng;
    @Bind(R.id.rl_top_02)
    RelativeLayout rl_top_02;

    private List<PreviewModel.TemplateBean.PagesBean.ObjectsBean> data = new ArrayList<>();
    private PreviewModel.TemplateBean.PagesBean Pdata;
    private int fl_height,fl_width;
    private TouchImageView touchImg;
    private int pointer;
    private float fsize;
    private Bitmap bitmap;
    private int cursor;
    private Map<Integer, Bitmap> bitmaps;
    private String strColor = "#000000";
    private boolean aBoolean,bBoolean;
    private String strings,strFont;
    private CoverageAdapter coverageAdapter;
    private ImageView twoGoods;
    private MyTextView textviews;
    private HashSet<String> fonts;
    private int Height_Ture, Width_Ture;//画板宽高
    private String filepath = Environment.getExternalStorageDirectory() + "/fonts/";
    private AlertDialog.Builder builder;
    private Map<Integer, View> coverageList;
    File file = new File(MyApplication.imgPath, "bg.jpg");

/* public static File createImagePathFile(Activity activity) {
        //文件目录可以根据自己的需要自行定义
        Uri imageFilePath;
        File file = new File(activity.getExternalCacheDir(), "11");
        imageFilePath = Uri.fromFile(file);
        return file;
    }*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                fl_editor.removeAllViews();
                coverageList.clear();
                for (int i = 0; i < data.size(); i++) {
                    PreviewModel.TemplateBean.PagesBean.ObjectsBean model = data.get(i);
                    if (model.getType() == 1) {
                        Log.e("PictureEditorActivity", "=-" + ".=" + model.getZ());
                        addImg(model.getWidth(), model.getHeight(), model.getX(), model.getY(), model.getImg() + "", model.getReplaceable(), i);
                    } else if (model.getType() == 2) {
                        addText(model.getText(), model.getColor(), model.getFontsize(), model.getX(), model.getY(), model.getFont(), i);
                    } else if (model.getType() == 3) {
//                        addTouchImg(i);
//                        final TouchImageView touchImgView = new TouchImageView(PictureEditorActivity.this);
//                        touchImgView.setOntouch(new TouchImageView.onTouchImage() {
//                            @Override
//                            public void onTouch(boolean b) {
//                                scrollView.requestDisallowInterceptTouchEvent(b);
//                            }
//
//                            @Override
//                            public void actionUp(Bitmap bitmap) {}
//                        });
//                        coverageList.put(i, touchImgView);
//                        fl_editor.addView(touchImgView);
//                        touchImgView.setImageBitmap(bitmaps.get(i));
                    }
                }
                //二维码
                if (touchImageView != null) {
                    if (bitmapcovage == null) {
                        touchImageView.buildDrawingCache();
                        bitmapcovage = touchImageView.getDrawingCache();
                    } else {
                        touchImageView1.buildDrawingCache();
                        bitmapcovage = touchImageView1.getDrawingCache();
                    }
                    touchImageView1 = new TouchImageView(PictureEditorActivity.this);
                    touchImageView1.setOntouch(new TouchImageView.onTouchImage() {
                        @Override
                        public void onTouch(boolean b) {
                            Log.e("PictureEditorc", b + "");
                            scrollView.requestDisallowInterceptTouchEvent(b);
                        }

                        @Override
                        public void actionUp(Bitmap bitmap, Matrix matrix) {

                        }

                        @Override
                        public void startDown() {

                        }
                    });
                    fl_editor.addView(touchImageView1);
                    touchImageView1.setImageBitmap(bitmapcovage);
                }
                UiUtils.endnet();
            }
            super.handleMessage(msg);
        }
    };

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_editor);
        ButterKnife.bind(this);
        initView();
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
                            UiUtils.getPictureFormCamera(PictureEditorActivity.this, 3012, file);
                        } else {
                            Toast.makeText(PictureEditorActivity.this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
                popupWindow.dismiss();
            }

            @Override
            public void OnClick02() {   //从手机相册选择
                UiUtils.getPictureFromPhoto(PictureEditorActivity.this, 3011);
                popupWindow.dismiss();
            }

            @Override
            public void OnClick03() {
            }
        });
    }

    /**
     * The Touch image view.
     */
    private TouchImageView touchImageView;
    /**
     * The Touch image view 1.
     */
    private TouchImageView touchImageView1;
    /**
     * The Bitmapcovage.
     */
    private Bitmap bitmapcovage;

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
                case 3011:
                    Uri originalUri = data.getData();
                    Bitmap photo = BitmapUtil.compressBitmap(null, null, PictureEditorActivity.this, originalUri);
                    if (cursor == 2580) {
                        bitmap = photo;
                        touchImg.setImageResource(R.drawable.err_img);
                        touchImg.setImageBitmap(photo);
                        touchImg.requestLayout();
                        bitmaps.put(pointer, bitmap);
                        aBoolean = true;
                    } else {
                        bitmaps.put(cursor, photo);
                        TouchImageView touchImageView = (TouchImageView) coverageList.get(cursor);
                        touchImageView.setImageBitmap(photo);
                        fl_editor.addView(touchImageView);
                        aBoolean = true;
                    }
                    touchImg.setMyBoolean(true);
                    break;
                case 3012:
                    BitmapUtil.compressImage(file.getPath(), file.getPath(), 50);
                    Bitmap outPhoto = BitmapUtil.convertToBitmap(file.getPath(), 0, 0);
                    if (cursor == 2580) {
                        bitmap = outPhoto;
                        touchImg.setImageResource(R.drawable.err_img);
                        touchImg.setImageBitmap(outPhoto);
                        touchImg.requestLayout();
                        bitmaps.put(pointer, bitmap);
                        aBoolean = true;
                    } else {
                        bitmaps.put(cursor, outPhoto);
                        TouchImageView touchImageView = (TouchImageView) coverageList.get(cursor);
                        touchImageView.setImageBitmap(outPhoto);
                        fl_editor.addView(touchImageView);
                        aBoolean = true;
                    }
                    touchImg.setMyBoolean(true);
                    break;
                case 3013:
                    Uri twOriginalUri = data.getData();
                    Bitmap photoTwo = BitmapUtil.compressBitmap(null, null, PictureEditorActivity.this, twOriginalUri);
                    bitmap = photoTwo;
                    twoGoods.setImageBitmap(photoTwo);
                    aBoolean = true;
                    touchImg.setMyBoolean(true);
                    break;
                default:
                    Toast.makeText(MyApplication.getContext(), "未添加图片", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (resultCode == 1) {
            //一键logo
            touchImageView = new TouchImageView(this);
            String img = data.getStringExtra("img");
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(UiUtils.dip2px(this, 80), UiUtils.dip2px(this, 80));
            touchImageView.setLayoutParams(params);
            UiUtils.loadImage(this, UrlConfig.IMG + img, touchImageView, (int) UiUtils.dpToPixels(80, this));
            fl_editor.addView(touchImageView);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }, 600);
        }
    }

    /**
     * Init view.
     */
    private void initView() {
        bitmaps = new HashMap<>();
        cursor = 2580;

//        ll_02.setVisibility(View.GONE);
        Intent intent = getIntent();
        Pdata = (PreviewModel.TemplateBean.PagesBean) intent.getSerializableExtra("pageslist");
        tv_keep.setText("预览");
        fl_editor.measure(0, 0);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int w = windowManager.getDefaultDisplay().getWidth();
        Width_Ture = w - (int) UiUtils.dpToPixels(20, this);
        Height_Ture = getZoomXY(Width_Ture, Pdata.getWidth(), Pdata.getHeight());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = Width_Ture;
        params.height = Height_Ture;
        fl_editor.setLayoutParams(params);

        fl_height = Pdata.getHeight();
        fl_width = Pdata.getWidth();
        data = Pdata.getObjects();
        fonts = new HashSet<>();
        for (int v = 0; v <= data.size(); v++) {
            if (v == data.size()){
                String font = "微软雅黑";
                File file1 = new File(filepath+font+".ttf");
                if (!file1.exists() && font != null) {
                    fonts.add(font);
                }
            }else {
                if (data.get(v) != null && data.get(v).getFont() != null) {
                    String font = data.get(v).getFont();
                    File file1 = new File(filepath + font + ".ttf");
                    if (!file1.exists() && font != null) {
                        fonts.add(font);
                    }
                }
            }
        }

        coverageList = new HashMap<>();
        bBoolean = true;

        if (fonts.size() != 0) {
            openDownload();
        } else {
            UiUtils.startnet(PictureEditorActivity.this);
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }

        initializeCard(this, UrlConfig.IMG + Pdata.getIcon(), UiUtils.dip2px(this, 55));

        rl_bottom_tucheng.setVisibility(View.GONE);
        recyclerview_bottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerview_bottom.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(5, this)));
    }

    /**
     * Initialize card.
     *
     * @param context the context
     * @param url     the url
     * @param width   the width
     */
    private void initializeCard(PictureEditorActivity context, String url, int width) {
        rl_bottom_card.setVisibility(View.VISIBLE);
        ImageView img1 = new ImageView(context);
        CardView.LayoutParams params1 = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl_bottom_card.addView(img1);
        img1.setBackgroundResource(R.drawable.canv_16);
        img1.setLayoutParams(params1);
        UiUtils.loadImage(context, url, img1, width);
    }

    /**
     * Open download.
     */
    private void openDownload() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否下载所需资源");
        builder.setCancelable(false);//设置点击对话框外部不关闭对话框
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UiUtils.startnet(PictureEditorActivity.this);
                ActivityCompat.requestPermissions(PictureEditorActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (String s : fonts) {
                            URL url = null;
                            try {
                                String urlStr = URLEncoder.encode(s + ".zip", "UTF-8");
                                urlStr = urlStr.replaceAll("\\+", "%20");
                                Log.e("PictureEditorActivity", urlStr);
                                UiUtils.downLoadFromUrl(UrlConfig.FONT + urlStr, filepath + s + ".ttf");
                            } catch (IOException e) {
                                Toast.makeText(PictureEditorActivity.this, s + "字体下载失败", Toast.LENGTH_SHORT).show();
                                Log.e("PictureEditorActivity", e.getMessage());
                            }
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();
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
     * Add text my text view.
     *
     * @param str   the str
     * @param color the color
     * @param size  the size
     * @param left  the left
     * @param top   the top
     * @param fonts the fonts
     * @param i     the
     * @return the my text view
     */
    private MyTextView addText(final Object str, final Object color, final Object size, int left, int top, final String fonts, final int i) {
        Log.e("PictureEditorActivity", str + "==" + color + "==" + size + "==" + left + "==" + top + "==" + fonts);
        final MyTextView text = new MyTextView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getZoomXY(Width_Ture, fl_width, left);
        params.topMargin = getZoomXY(Height_Ture, fl_height, top);
        text.setLayoutParams(params);
        String s = (String) str;
        s = s.replaceAll("\\\\n", "\\\n");
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
            text.setTextSize(UiUtils.px2dip(this, ((float) (Width_Ture) / (float) fl_width * Float.parseFloat(size.toString()))));
        } catch (Exception e) {
            Toast.makeText(this, "文字大小为空", Toast.LENGTH_LONG).show();
        }
        if (fonts != null) {
            final File filef = new File(filepath + fonts + ".ttf");
            try {
                Typeface tf = Typeface.createFromFile(filef);
                text.setTypeface(tf);//设置字体
            } catch (Exception e) {
                Toast.makeText(this, "字体下载失败", Toast.LENGTH_LONG).show();
            }
        }
        fl_editor.addView(text);
        text.setonClick(new MyTextView.MyTextViewInterface() {
            @Override
            public void onClickTextView() {
                ll_02.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTouchTextView() {
                int[] location = new int[2];
                text.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Log.e("PictureEditorActivity", x + "=======" + y + "     " + text.getLeft() + "=======" + text.getTop());
                data.get(pointer).setX(text.getLeft() * fl_width / Width_Ture);
                data.get(pointer).setY(text.getTop() * fl_height / Height_Ture);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = text.getLeft();
                params.topMargin = text.getTop();
                text.setLayoutParams(params);
            }

            @Override
            public void startTouchTextView() {
                scrollView.requestDisallowInterceptTouchEvent(true);
                ll_02.setVisibility(View.VISIBLE);
            }
        });

        coverageList.put(i, text);
        if ("编辑文本".equals(str) && "#ffff01".equals(color) && "微软雅黑".equals(fonts)) {
            MybPopupWindow mybPopupWindow = new MybPopupWindow(PictureEditorActivity.this, String.valueOf(str), "#ffff02", fonts);
            data.get(i).setColor("#ffff02");
            mybPopupWindow.showPopupwindow();
            mybPopupWindow.setOnEditText(new MybPopupWindow.OnEditText() {
                @Override
                public void getEditText(String string) {
                    text.setText(string);
                    strings = string;
                    data.get(pointer).setText(string);
                }

                @Override
                public void setTextStyle(String font) {
                    final File fileo = new File(filepath + font + ".ttf");
                    strFont = font;
                    try {
                        Typeface tf = Typeface.createFromFile(fileo);
                        text.setTypeface(tf);//设置字体
                    } catch (RuntimeException e) {
                        Log.e("PictureEditorActivity", e.getMessage());
                    }
                    data.get(pointer).setFont(font);
                }

                @Override
                public void setTextColor(String color) {
                    strColor = color;
                    text.setTextColor(Color.parseColor(color));
                    data.get(pointer).setColor(color);
                }
            });
        }
        return text;
    }

    /**
     * Add img.
     *
     * @param width       the width
     * @param height      the height
     * @param left        the left
     * @param top         the top
     * @param url         the url
     * @param replaceable the replaceable
     * @param i           the
     */
    private void addImg(int width, int height, int left, int top, String url, int replaceable, final int i) {
        if (data.get(i).getZoom() == 1) {
            TouchImageView touchImg1 = new TouchImageView(this);
            touchImg1.setOntouch(new TouchImageView.onTouchImage() {
                @Override
                public void onTouch(boolean b) {
                    Log.e("PictureEditorc", b + "----");
                    scrollView.requestDisallowInterceptTouchEvent(b);
                }

                @Override
                public void actionUp(Bitmap bitmap,Matrix matrix) {
                    if (touchImg != null && bitmaps.get(pointer) != null) {
                        Bitmap bitmapCopy = copy(bitmap);
                        bitmaps.put(pointer, bitmapCopy);
                    }
                }

                @Override
                public void startDown() {

                }
            });
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(Width_Ture, Height_Ture);
            params.width = getZoomXY(width, fl_width, Width_Ture);
            params.height = getZoomXY(height, fl_height, Height_Ture);
            params.leftMargin = getZoomXY(Width_Ture, fl_width, left);
            params.topMargin = getZoomXY(Height_Ture, fl_height, top);
            fl_editor.addView(touchImg1);
            touchImg1.setLayoutParams(params);
            if (bitmaps.get(i) == null) {
                loadImage(this, UrlConfig.IMG + url, touchImg1, UiUtils.getZoomXY(width, fl_width, Width_Ture));
            } else {
                touchImg1.setImageResource(R.drawable.err_img);
                touchImg1.setImageBitmap(bitmaps.get(i));
            }
            coverageList.put(i, touchImg1);
        } else {
            final ImageView img = new ImageView(this);
            Log.e("PictureEditorActivity", width + "===" + fl_width + "====" + Width_Ture + "===" + Height_Ture + "==" + fl_height + "===" + height + "======");
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(Width_Ture, Height_Ture);
            params.width = getZoomXY(width, fl_width, Width_Ture);
            params.height = getZoomXY(height, fl_height, Height_Ture);
            params.leftMargin = getZoomXY(Width_Ture, fl_width, left);
            params.topMargin = getZoomXY(Height_Ture, fl_height, top);
            fl_editor.addView(img);
            img.setLayoutParams(params);
            if (replaceable == 1) {
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        twoGoods = img;
                        UiUtils.getPictureFromPhoto(PictureEditorActivity.this, 3013);
                    }
                });
            }
            loadImage(this, UrlConfig.IMG + url, img, UiUtils.getZoomXY(width, fl_width, Width_Ture));
            coverageList.put(i, img);
        }
    }

    /**
     * Add touch img.
     *
     * @param i the
     */
    private void addTouchImg(int i) {
        TouchImageView touchImgView = new TouchImageView(PictureEditorActivity.this);
        touchImgView.setOntouch(new TouchImageView.onTouchImage() {
            @Override
            public void onTouch(boolean b) {
                scrollView.requestDisallowInterceptTouchEvent(b);
            }

            @Override
            public void actionUp(Bitmap bitmap,Matrix matrix) {
            }

            @Override
            public void startDown() {

            }
        });
        coverageList.put(i, touchImgView);
    }

    /**
     * Load image.
     *
     * @param context the context
     * @param url     the url
     * @param tager   the tager
     * @param width   the width
     */
    public static void loadImage(Context context, String url, final ImageView tager, int width) {
        if (width == 0) {
            if (tager.getWidth() != 0)
                url = url + "?x-oss-process=image/format,webp/resize,w_" + tager.getWidth();
        } else {
            url = url + "?x-oss-process=image/format,webp/resize,w_" + width;
        }
        Drawable defImg = context.getResources().getDrawable(R.drawable.err_img);
        try {
            Glide.with(context)
                    .load(url)
                    .error(defImg)
                    .fitCenter()
                    .crossFade(500).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                    tager.requestLayout();
                    target.getRequest().begin();
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            })
                    .into(tager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.tv_back, R.id.tv_keep, R.id.iv_picture_botton, R.id.iv_text_botton, R.id.rl_bottom_07, R.id.rl_bottom_08, R.id.rl_bottom_09, R.id.rl_bottom_03, R.id.iv_aid, R.id.rl_bottom_card, R.id.rl_top_02, R.id.iv_top_02})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:  //返回
                finish();
                break;
            case R.id.tv_keep:  //跳转到预览界面
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getType() == 3) {
                        Pdata.setEditZ(i);
                        data.remove(i);
                        Pdata.setObjects(data);
                    }
                }

                if (textviews != null)
                    textviews.setBackground(null);

                final Intent intent = new Intent();

                Bundle bundle = new Bundle();
                bundle.putSerializable("Pdata", Pdata);
                bundle.putBoolean("aBoolean", aBoolean);

                intent.putExtras(bundle);
                intent.setClass(this, PreviewActivity.class);
                setResult(1102, intent);
                finish();
                break;
            case R.id.iv_picture_botton:    //换图
                if (touchImg != null) {
                    showPopupWindow();
                } else {
                    Toast.makeText(this, "请选择可编辑图片", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_text_botton:   //编辑
                if (textviews == null) {
                    Toast.makeText(this, "请选择文本", Toast.LENGTH_SHORT).show();
                } else {
                    final MybPopupWindow mybPopupWindow = new MybPopupWindow(PictureEditorActivity.this, strings, strColor, strFont);
                    mybPopupWindow.showPopupwindow();
                    mybPopupWindow.setOnEditText(new MybPopupWindow.OnEditText() {
                        @Override
                        public void getEditText(String string) {
                            textviews.setText(string);
                            strings = string;
                            data.get(pointer).setText(string);
                        }

                        @Override
                        public void setTextStyle(String font) {
                            final File fileo = new File(filepath + font + ".ttf");
                            strFont = font;
                            try {
                                Typeface tf = Typeface.createFromFile(fileo);
                                textviews.setTypeface(tf);//设置字体
                            } catch (RuntimeException e) {
                                Log.e("PictureEditorActivity", e.getMessage());
                            }
                            data.get(pointer).setFont(font);
                        }

                        @Override
                        public void setTextColor(String color) {
                            strColor = color;
                            textviews.setTextColor(Color.parseColor(color));
                            data.get(pointer).setColor(color);
                        }
                    });
                }
                break;
            case R.id.rl_bottom_07:     //字体放大
                if (textviews == null) {
                    Toast.makeText(this, "请选择文本", Toast.LENGTH_SHORT).show();
                } else {
                    fsize = fsize + 3;
                    textviews.setTextSize(UiUtils.px2dip(PictureEditorActivity.this, ((float) (Width_Ture) / (float) fl_width * fsize)));
                    data.get(pointer).setFontsize(fsize + "");
                }
                break;
            case R.id.rl_bottom_08:    //字体缩小
                if (textviews == null) {
                    Toast.makeText(this, "请选择文本", Toast.LENGTH_SHORT).show();
                } else {
                    if (fsize > 0) {
                        fsize = fsize - 3;
                        textviews.setTextSize(UiUtils.px2dip(PictureEditorActivity.this, ((float) (Width_Ture) / (float) fl_width * fsize)));
                        data.get(pointer).setFontsize(fsize + "");
                    }
                }
                break;
            case R.id.rl_bottom_09:     //删除
                if (textviews == null && touchImg == null) {
                    Toast.makeText(this, "请选择可编辑图层", Toast.LENGTH_SHORT).show();
                } else {
                    if (textviews != null) {
                        textviews.setMyBoolean(false);
                        textviews.setBackground(null);
                        textviews.setText("");
                    } else {
                        touchImg.setMyBoolean(false);
                    }
                    textviews = null;
                    touchImg = null;

                    data.remove(pointer);

                    removeMap(coverageList, pointer);
                    if (bitmaps.get(pointer) != null) {
                        bitmaps.get(pointer).recycle();
                        removeMap(bitmaps, pointer);
                    }

                    coverageAdapter.getData(data, bitmaps,pointer);
                    coverageAdapter.notifyDataSetChanged();

                    updateCoverage();

                    rl_bottom_card.removeAllViews();
                    initializeCard(this, UrlConfig.IMG + Pdata.getIcon(), UiUtils.dip2px(this, 55));
                }
                break;
            case R.id.rl_bottom_03:
                Toast.makeText(this, "继续期待  v(˘•㉨•˘)v", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_aid: //添加字体
                if (textviews != null) {
                    textviews.setMyBoolean(false);
                    textviews.setBackground(null);
                }
                addNewCoverage("编辑文本", "#ffff01", data.size(), 2);
                break;
            case R.id.rl_bottom_card:
                rl_bottom_tucheng.setVisibility(View.VISIBLE);
                coverageAdapter = new CoverageAdapter(this);
                recyclerview_bottom.setAdapter(coverageAdapter);
                recyclerview_bottom.setLongPressDragEnabled(true);// 开启长按拖拽。
                recyclerview_bottom.setItemViewSwipeEnabled(true);// 开启滑动删除。
                recyclerview_bottom.setOnItemMovementListener(onItemMovementListener);
                recyclerview_bottom.setOnItemMoveListener(onItemMoveListener);

                if (textviews != null) {
                    textviews.setMyBoolean(false);
                    textviews.setBackground(null);
                }
                if (touchImg != null) {
                    touchImg.setMyBoolean(false);
                }
                textviews = null;
                touchImg = null;

                coverageAdapter.setCoverageOnclick(new CoverageAdapter.CoverageOnclick() {
                    @Override
                    public void setCoverageItem(final int position) {
                        int type = data.get(position).getType();
                        rl_bottom_tucheng.setVisibility(View.GONE);

                        pointer = position;
                        if (type == 1) {
                            rl_bottom_card.removeAllViews();
                            if (bitmaps.get(pointer) != null) {
                                rl_bottom_card.setVisibility(View.VISIBLE);
                                ImageView img = new ImageView(PictureEditorActivity.this);
                                CardView.LayoutParams params1 = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                rl_bottom_card.addView(img);
                                img.setBackgroundResource(R.drawable.canv_16);
                                img.setLayoutParams(params1);
                                img.setImageBitmap(bitmaps.get(pointer));
                            } else {
                                initializeCard(PictureEditorActivity.this, UrlConfig.IMG + data.get(position).getImg(), UiUtils.dip2px(PictureEditorActivity.this, 55));
                            }
                            if (data.get(position).getZoom() == 1) {
                                TouchImageView touchImageView = (TouchImageView) coverageList.get(position);
                                touchImg = touchImageView;
                                if (bitmaps.get(position) != null) {
                                    touchImg.setMyBoolean(true);
                                }
                            }
                        } else if (type == 2) {
                            MyTextView text = (MyTextView) coverageList.get(position);
                            scrollView.requestDisallowInterceptTouchEvent(true);
                            textviews = text;
                            textviews.setMyBoolean(true);
                            textviews.setBackgroundResource(R.drawable.canv_21);
                            strFont = data.get(position).getFont();
                            strColor = data.get(position).getColor();
                            strings = data.get(position).getText();
                            fsize = Float.parseFloat(data.get(position).getFontsize());

                            rl_bottom_card.removeAllViews();
                            TextView textView = new TextView(PictureEditorActivity.this);
                            CardView.LayoutParams params2 = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            rl_bottom_card.addView(textView);
                            textView.setBackgroundResource(R.drawable.canv_16);
                            textView.setText(strings);
                            textView.setLayoutParams(params2);
                        } else if (type == 3) {
//                            ll_02.setVisibility(View.GONE);
                            if (touchImg != null) {
                                touchImg.setMyBoolean(true);
                            }
                        } else if (type == 4) {
                            //添加图层
                        }
                    }
                });

                coverageAdapter.getData(data, bitmaps,pointer);
                coverageAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_keep_bottom:
                rl_bottom_tucheng.setVisibility(View.GONE);
//                ll_02.setVisibility(View.GONE);
                break;
            case R.id.rl_top_02:
                startActivityForResult(new Intent(PictureEditorActivity.this, LogoActivity.class), 1);
                break;
        }
    }

    /**
     * The Width.
     */
    private int width;
    /**
     * The Height.
     */
    private int height;

    /**
     * Add new coverage.
     *
     * @param str       the str
     * @param textColor the text color
     * @param coverage  the coverage
     * @param type      the type
     */
    private void addNewCoverage(String str, String textColor, int coverage, int type) {
        width = getWindowManager().getDefaultDisplay().getWidth() * fl_width / Width_Ture / 2 - UiUtils.dip2px(this,30);
        height = getWindowManager().getDefaultDisplay().getHeight() * fl_height / Height_Ture / 2 - UiUtils.dip2px(this, 150);
        Log.e("PictureEditor","width:"+width+"       height:"+height);
        PreviewModel.TemplateBean.PagesBean.ObjectsBean textModel = new PreviewModel.TemplateBean.PagesBean.ObjectsBean();
        textModel.setImg(null);
        textModel.setColor(textColor);
        textModel.setFontsize(width/8+"");
        textModel.setWeight(null);
        textModel.setCreatedate(null);
        textModel.setPid(0);
        textModel.setOid(0);
        textModel.setType(type);
        textModel.setTransparency(0);
        textModel.setWidth(0);
        textModel.setX(width);
        textModel.setY(height);
        textModel.setZ(coverage);
        textModel.setText(str);
        textModel.setHeight(0);
        textModel.setFont("微软雅黑");
        data.add(coverage, textModel);
        if (type == 2) {
            MyTextView text = addText(str, textColor, width/8+"", width, height, "微软雅黑", coverage);
            pointer = coverage;
            textviews = text;
            strColor = textColor;
            fsize = Float.parseFloat(width/8+"");
            textviews.setMyBoolean(true);
            textviews.setBackgroundResource(R.drawable.canv_21);
        } else if (type == 3)
            addTouchImg(coverage);
    }

    /**
     * On key down boolean.
     *
     * @param keyCode the key code
     * @param event   the event
     * @return the boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * On destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("PictureEditorActivity", "PictureEditorActivity已经destory了");
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
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //创建文件夹
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File file = new File(Environment.getExternalStorageDirectory() + "/fonts/");
                        if (!file.exists()) {
                            Log.e("jim", "path1 create:" + file.mkdirs());
                        }
                    }
                    break;
                }
        }
    }

    /**
     * 当Item移动的时候监听。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            // 当Item被拖拽的时候，交换Item的位置。
            Collections.swap(data, fromPosition, toPosition);
            coverageAdapter.notifyItemMoved(fromPosition, toPosition);
            if (data.get(toPosition).getType() == 4) {
                Pdata.setEditZ(toPosition);
            } else if (data.get(fromPosition).getType() == 4) {
                Pdata.setEditZ(fromPosition);
            }

            View view = coverageList.get(toPosition);
            coverageList.put(toPosition, coverageList.get(fromPosition));
            coverageList.put(fromPosition, view);

            if (bitmaps.get(fromPosition) != null || bitmaps.get(toPosition) != null) {
                Bitmap bitmapb = bitmaps.get(toPosition);
                bitmaps.put(toPosition, bitmaps.get(fromPosition));
                bitmaps.put(fromPosition, bitmapb);
            }

            updateCoverage();

            coverageAdapter.getData(data, bitmaps,pointer);
            coverageAdapter.notifyDataSetChanged();
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            if (data.get(position).getType() == 4)
                Pdata.setEditZ(null);
            data.remove(position);
            removeMap(coverageList, position);
            if (bitmaps.get(position) != null || bitmaps.get(position + 1) != null) {
                bitmaps.get(position).recycle();
                removeMap(bitmaps, position);
            }
            coverageAdapter.getData(data, bitmaps,pointer);
            coverageAdapter.notifyDataSetChanged();
            textviews = null;

            updateCoverage();

            rl_bottom_card.removeAllViews();
            initializeCard(PictureEditorActivity.this, UrlConfig.IMG + Pdata.getIcon(), UiUtils.dip2px(PictureEditorActivity.this, 55));
        }
    };

    /**
     * Update coverage.
     */
    private void updateCoverage() {
        fl_editor.removeAllViews();
        for (int i = 0; i < coverageList.size(); i++) {
            if (coverageList.get(i) != null)
                fl_editor.addView(coverageList.get(i));
        }
    }

    /**
     * Remove map.
     *
     * @param maps     the maps
     * @param position the position
     */
    private void removeMap(Map maps, int position) {
        Map<Integer, View> map = new HashMap<>();
        map.putAll(maps);
        Iterator i = map.keySet().iterator();
        maps.clear();

//        for (Integer key : coverageList.keySet()) {
//            if (key > position) {
//                maps.put(key - 1, map.get(key));
//            } else if (key == position) {
//                maps.put(key, null);
//            } else {
//                maps.put(key, map.get(key));
//            }
//        }

        while (i.hasNext()) {
            int key = (int) i.next();
            if (key > position) {
                maps.put(key - 1, map.get(key));
            } else if (key == position) {

            } else {
                maps.put(key, map.get(key));
            }
        }
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

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {// 如果是LinearLayoutManager。
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {// 横向的List。
                    return (OnItemMovementListener.LEFT | OnItemMovementListener.RIGHT); // 只能左右拖拽。
                } else {// 竖向的List。
                    return OnItemMovementListener.UP | OnItemMovementListener.DOWN; // 只能上下拖拽。
                }
            }
//            else if (layoutManager instanceof GridLayoutManager) {// 如果是Grid。
//                return OnItemMovementListener.LEFT | OnItemMovementListener.RIGHT | OnItemMovementListener.UP |
//                        OnItemMovementListener.DOWN; // 可以上下左右拖拽。
//            }
            return OnItemMovementListener.INVALID;// 返回无效的方向。
        }

        @Override
        public int onSwipeFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder) {
            // 我们让上锁图层不能滑动删除。
            int i = targetViewHolder.getAdapterPosition();
            if (data.get(i).getZoom() != 1 && data.get(i).getType() == 1) {
                return OnItemMovementListener.INVALID;// 返回无效的方向。
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {// 如果是LinearLayoutManager
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {// 横向的List。
                    return OnItemMovementListener.UP | OnItemMovementListener.DOWN; // 只能上下滑动删除。
                } else {// 竖向的List。
                    return OnItemMovementListener.LEFT | OnItemMovementListener.RIGHT; // 只能左右滑动删除。
                }
            }
            return OnItemMovementListener.INVALID;// 其它均返回无效的方向。
        }
    };

    /**
     * 根据原位图生成一个新的位图，并将原位图所占空间释放
     *
     * @param srcBmp 原位图
     * @return 新位图 bitmap
     */
    public static Bitmap copy(Bitmap srcBmp) {
        Bitmap destBmp = null;
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

            //释放原位图占用的空间
//            srcBmp.recycle();

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
}
