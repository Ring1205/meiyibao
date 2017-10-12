package com.wmcd.myb.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.wigdet.GlideCircleTransform;
import com.wmcd.myb.wigdet.GlideRoundTransform;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * UI提示的一些
 *
 * @author Rebecca
 */
@SuppressWarnings("deprecation")
public class UiUtils {
    /**
     * The constant pd.
     */
    private static ProgressDialog pd;
    /**
     * 判断11位电话号码格式的正则
     */
    public static final String PHONE_MATCH = "^1[34578]\\d{9}$";
    /**
     * The constant sToast.
     */
    private static Toastor sToast;

    public static void setRatio(List<Float> ratio) {
        UiUtils.ratio = ratio;
    }

    public static List<Float> getRatio() {
        return ratio;
    }

    private static List<Float> ratio = new ArrayList<>();

    /**
     * Dip 2 px int.
     *
     * @param context the context
     * @param dpValue the dp value
     * @return the int
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Px 2 dip float.
     *
     * @param context the context
     * @param pxValue the px value
     * @return the float
     */
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    /**
     * Px 2 sp float.
     *
     * @param context the context
     * @param pxValue the px value
     * @return the float
     */
    public static float px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / scale + 0.5f);
    }

    /**
     * pid转像素点
     *
     * @param dp      the dp
     * @param context the context
     * @return float
     */
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    /**
     * Startnet.
     *
     * @param context the context
     * @param str     the str
     */
// 请求网络提示
    public static void startnet(Context context, String str) {
        if (pd == null)
            pd = new ProgressDialog(context);
        else {
            pd.dismiss();
            pd = new ProgressDialog(context);
        }
        pd.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(str);
        pd.show();
    }

    /**
     * Startnet.
     *
     * @param context the context
     */
// 请求网络提示
    public static void startnet(Context context) {
        if (pd == null)
            pd = new ProgressDialog(context);
        if (pd.isShowing())
            return;
        pd.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在加载中......");
        try {
            pd.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Startnetpic.
     *
     * @param context the context
     */
// 请求网络提示
    public static void startnetpic(Context context) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在下载中......");
        pd.show();
    }

    /**
     * 判断是否还有下一页
     *
     * @param j the j
     * @return true 's have next page
     */
    public static boolean haveNextPage(JSONObject j) {
        if (j.optInt("currentPage") >= j.optInt("totalPage"))
            return false;
        else
            return true;
    }

    /**
     * Endnet.
     */
// 结束
    public static void endnet() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pd != null)
                    pd.dismiss();
                pd = null;
            }
        }, 1000);
    }

    /**
     * Endnet.
     * @param l the l
     */
// 结束
    public static void endnet(long l) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pd != null)
                    pd.dismiss();
                pd = null;
            }
        }, l);
    }

    /**
     * Long toast.
     *
     * @param context the context
     * @param text    the text
     */
// 长吐司
    public static void longToast(Context context, String text) {
        if (sToast == null)
            sToast = new Toastor(context);
        sToast.showLongToast(text);
    }

    /**
     * Short toast.
     *
     * @param context the context
     * @param text    the text
     */
// 短吐司
    public static void shortToast(Context context, String text) {
        if (sToast == null)
            sToast = new Toastor(context);
        sToast.showSingletonToast(text);
    }

    /**
     * Hide keyboard.
     *
     * @param context the context
     * @param view    the view
     */
// 键盘隐藏
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0); // 隐藏
    }

    /**
     * Gets picture from photo.
     *
     * @param activity    the activity
     * @param requestCode the request code
     */
// 从相册中获取照片
    public static void getPictureFromPhoto(Activity activity, int requestCode) {
        Intent openphotoIntent = new Intent(Intent.ACTION_PICK);
        openphotoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(openphotoIntent, requestCode);

    }

    /**
     * Gets picture form camera.
     *
     * @param activity    the activity
     * @param requestCode the request code
     * @param file        the file
     */
// 从相机中获取照片
    public static void getPictureFormCamera(BaseActivity activity, final int requestCode, final File file) {
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            file.getParentFile().mkdirs();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //File photoFile = createImagePathFile(activity);

            Uri uriForFile = FileProvider.getUriForFile(activity,
                    "com.wmcd.myb.fileprovider", file);
            Uri uri = Uri.fromFile(file);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }

            activity.startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(activity, "内存卡不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show general dialog.
     *
     * @param context    the context
     * @param msg        the msg
     * @param canLis     the can lis
     * @param comfirmLis the comfirm lis
     */
//对话框
    public static void ShowGeneralDialog(Context context, String msg, GeneralDialog.OnCancelListener canLis, GeneralDialog.OnConfirmListener comfirmLis) {
        new GeneralDialog.Builder(context).setMsg(msg).setCancelable(false).setCancelButton("取消", canLis).setConfrimButton("确定", comfirmLis).show();
    }

    /**
     * Load image dont animate.
     *
     * @param context the context
     * @param url     the url
     * @param tager   the tager
     * @param width   the width
     */
    public static void loadImageDontAnimate(Context context, String url, ImageView tager, int width) {
        if (width == 0)
            if (tager.getWidth() != 0)
                url = url + "?x-oss-process=image/format,webp/resize,w_" + tager.getWidth();
            else
                url = url;
        else
            url = url + "?x-oss-process=image/format,webp/resize,w_" + width;
        Drawable defImg = context.getResources().getDrawable(R.drawable.err_img);

        try {
            Glide.with(context)
                    .load(url)
                    .error(defImg)
                    .centerCrop()
                    .crossFade(500)
                    .into(tager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载图片
     *
     * @param context the context
     * @param url     /path
     * @param tager   the tager
     * @param width   the width
     */
    public static void loadImage(Context context, String url, final ImageView tager, int width) {

        if (width == 0)
            if (tager.getWidth() != 0)
                url = url + "?x-oss-process=image/format,webp/resize,w_" + tager.getWidth();
            else
                url = url;
        else
            url = url + "?x-oss-process=image/format,webp/resize,w_" + width;
        Drawable defImg = context.getResources().getDrawable(R.drawable.err_img);
        try {
            Glide.with(context)
                    .load(url)
                    .error(defImg)
                    .fitCenter()
                    .crossFade(1)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(tager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Load image.
     *
     * @param context  the context
     * @param url      the url
     * @param tager    the tager
     * @param width    the width
     * @param shuoxiao the shuoxiao
     */
    public static void loadImage(Context context, String url, final ImageView tager, int width, float shuoxiao) {
        if (width == 0)
            if (tager.getWidth() != 0)
                url = url + "?x-oss-process=image/format,webp/resize,w_" + tager.getWidth();
            else
                url = url;
        else
            url = url + "?x-oss-process=image/format,webp/resize,w_" + width;

        Drawable defImg = context.getResources().getDrawable(R.drawable.err_img);
        try {
            Glide
                    .with(context)
                    .load(url)
                    .thumbnail(shuoxiao)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(tager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load image.
     *
     * @param context      the context
     * @param url          the url
     * @param tager        the tager
     * @param width        the width
     * @param isbackground the isbackground
     */
/*
    * 加载背景图片
    * */
    public static void loadImage(Context context, String url, final ImageView tager, int width, boolean isbackground) {
        if (width == 0)
            if (tager.getWidth() != 0)
                url = url + "?x-oss-process=image/format,webp/resize,w_" + tager.getWidth();
            else
                url = url;
        else
            url = url + "?x-oss-process=image/format,webp/resize,w_" + width;
        Drawable defImg = context.getResources().getDrawable(R.drawable.err_img);
        try {

            Glide.with(context).load(url)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    tager.setBackground(resource);
                    resource=null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载圓角和圆形图片
     * ImageType.CIRCLE
     *
     * @param context the context
     * @param url     the url
     * @param tager   CIRCLE园、FILLET圆角
     * @param defImg2 the def img 2
     */
    public static void loadImage(Context context, String url, ImageView tager, String defImg2) {
        if (tager != null && tager.getWidth() != 0)
            url = url + "?x-oss-process=image/format,webp/resize,w_" + tager.getWidth();

        if (ImageType.CIRCLE.equals(defImg2)) {
            Drawable defImg = context.getResources().getDrawable(R.drawable.err_img);
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .bitmapTransform(new GlideCircleTransform(context))
                    .placeholder(defImg)
                    .error(defImg)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(tager);
        } else if (ImageType.FILLET.equals(defImg2)) {
            ColorDrawable defImg = new ColorDrawable(ContextCompat.getColor(context, R.color.no_photo));
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .bitmapTransform(new GlideRoundTransform(context, dip2px(context, 2)))
                    .placeholder(defImg)
                    .error(defImg)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(tager);
        }
    }

    /**
     * 加载图片
     *
     * @param context the context
     * @param file    the file
     * @param tager   the tager
     */
    public static void loadImage(Context context, File file, ImageView tager) {
        ColorDrawable defImg = new ColorDrawable(ContextCompat.getColor(context, R.color.no_photo));
        Glide.with(context)
                .load(file)
                .fitCenter()
                .error(defImg)
                .placeholder(defImg)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(tager);
    }


    /**
     * 获取网络图片第一帧
     *
     * @param url    the url
     * @param width  the width
     * @param height the height
     * @return bitmap
     */
    public static Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * Sets list view height based on children.
     *
     * @param listView the list view
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * Start swipe refresh.
     *
     * @param SwipeRefresh the swipe refresh
     */
//打开刷新滚动条
    public static void StartSwipeRefresh(final SwipeRefreshLayout SwipeRefresh) {
        SwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                //进度条颜色
                SwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
                SwipeRefresh.setRefreshing(true);
            }
        });
    }

    /**
     * Stop swipe refresh.
     *
     * @param SwipeRefresh the swipe refresh
     */
//关闭刷新滚动条
    public static void StopSwipeRefresh(final SwipeRefreshLayout SwipeRefresh) {
        SwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                SwipeRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * Sets price point.
     *
     * @param editText the edit text
     */
//edittext确定到小数点后两位
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    /**
     * 判断bean类中的list是否为空
     *
     * @param list the list
     * @return boolean
     */
    public static boolean isList(List list) {
        return list != null && !list.isEmpty();
    }

    /**
     * Gets zoom xy.
     *
     * @param panel      the panel
     * @param panelw     the panelw
     * @param coordinate the coordinate
     * @return zoom xy
     */
    public static int getZoomXY(int panel, int panelw, int coordinate) {
        return (int) ((float) panel / (float) panelw * (float) coordinate);
    }

    /**
     * Gets zoom xy.
     *
     * @param panel      the panel
     * @param panelw     the panelw
     * @param coordinate the coordinate
     * @return zoom xy
     */
    public static int getZoomXY(float panel, int panelw, int coordinate) {
        return (int) (panel / (float) panelw * (float) coordinate);
    }

    /**
     * 将View视图转成bitamp
     *
     * @param v the v
     * @return bitmap
     */
    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /**
     * 保存图片到指定文件夹中
     *
     * @param context the context
     * @param bm      the bm
     * @param path    the path
     * @return the string
     */
    public static String saveBitmap(Context context, Bitmap bm, String path) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            bm.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/" + path + "/" + fileName)));
        return Environment.getExternalStorageDirectory() + "/" + path + "/" + fileName;
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr   the url str
     * @param fileName the file name
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName) {
        URL url = null;
        //得到输入流
        InputStream inputStream = null;
        BufferedOutputStream bos = null;
        try {
            url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            inputStream = conn.getInputStream();

            ZipInputStream zis = new ZipInputStream(inputStream);

            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null
                    && !entry.isDirectory()) {
                File target = new File(fileName);
                if (!target.getParentFile().exists()) {
                    // 创建文件父目录
                    target.getParentFile().mkdirs();
                }
                // 写入文件
                bos = new BufferedOutputStream(new FileOutputStream(target));
                int read = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, read);
                }
                bos.flush();
            }
            zis.closeEntry();
        } catch (Exception e) {
            Log.e("UiUtils", "---" + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e("UiUtils", e.getMessage());
            }
        }
//        //文件保存位置
//        File file = new File(fileName);
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(getData);
//        if (fos != null) {
//            fos.close();
//        }
//        if (inputStream != null) {
//            inputStream.close();
//        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream the input stream
     * @return byte [ ]
     * @throws IOException the io exception
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 从网络Url中下载文件
     *
     * @param mHandler the m handler
     * @param urlStr   the url str
     * @param fileName the file name
     * @throws IOException
     */
    public static void downDoc(Handler mHandler, String urlStr, String fileName) {
        URL url = null;
        //得到输入流
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            URL mURL = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            inputStream = conn.getInputStream();
            File file = new File(fileName);
            file.createNewFile();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            while ((inputStream.read(buf)) != -1) {
                fos.write(buf);
            }
            inputStream.close();
            mHandler.sendEmptyMessage(0);
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(1);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(1);
            }
        }
    }

    /**
     * Is avaiable space boolean.
     *
     * @param size the size
     * @return the boolean
     */
    public static boolean isAvaiableSpace(long size) {
        if (isSdcardReadable()) {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            // int blockCounts = statFs.getBlockCount();
            int avCounts = statFs.getAvailableBlocks();
            long blockSize = statFs.getBlockSize();
            long spaceLeft = avCounts * blockSize;
            if (spaceLeft > size) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the SD card is readable
     *
     * @return the boolean
     */
    public static boolean isSdcardReadable() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) || Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Sets mk dir.
     *
     * @param context the context
     * @return the mk dir
     */
    public static String setMkDir(Context context) {
        return setMkDir(context, ".project");
    }

    /**
     * Sets mk dir.
     *
     * @param context the context
     * @param dirName the dir name
     * @return the mk dir
     */
    public static String setMkDir(Context context, String dirName) {
        String filePath = "";
        if (isSdcardReadable()) {
            filePath = Environment.getExternalStorageDirectory() + File.separator + dirName;
        } else {
            filePath = context.getCacheDir().getAbsolutePath() + File.separator + dirName;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            boolean b = file.mkdirs();
        }
        Log.i("路径",filePath);
        return filePath;
    }

}
