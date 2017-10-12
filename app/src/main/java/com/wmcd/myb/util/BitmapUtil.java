package com.wmcd.myb.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/8/25.
 */
public class BitmapUtil {
    /**
     * Compress image string.
     *
     * @param filePath   the file path
     * @param targetPath the target path
     * @param quality    the quality
     * @return the string
     */
    public static String compressImage(String filePath, String targetPath, int quality) {
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile.getPath();
    }

    /**
     * 从文件路径中提取图片转成bitmap
     *
     * @param path the path
     * @param w    the w
     * @param h    the h
     * @return bitmap
     */
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
//        if (w != 0 && h != 0){
//            if (width > w || height > h) {
//                // 缩放
//                scaleWidth = ((float) width) / w;
//                scaleHeight = ((float) height) / h;
//            }
//        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), width, height, true);
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     *
     * @param filePath the file path
     * @return the small bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final Options options = new Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        // 得到图片的宽度、高度；
        getInSampleSize(options);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 获取照片角度
     *
     * @param path the path
     * @return int
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     *
     * @param bitmap  the bitmap
     * @param degress the degress
     * @return bitmap
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * Save file.
     *
     * @param bm   the bm
     * @param path the path
     * @throws Exception the exception
     */
//存储进SD卡
    public static void saveFile(Bitmap bm, String path) throws Exception {
        File dirFile = new File(path);
        //检测图片是否存在
        if (dirFile.exists()) {
            dirFile.delete();  //删除原图片
        }
        File myCaptureFile = new File(path);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//100表示不进行压缩，70表示压缩率为30%
        bm.compress(Bitmap.CompressFormat.JPEG, 60, bos);
        bos.flush();
        bos.close();
    }

    /**
     * Gets in sample size.
     *
     * @param opts the opts
     */
    private static void getInSampleSize(Options opts) {
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) 768);
        int heightRatio = (int) Math.ceil(imgHeight / (float) 1280);
        opts.inSampleSize = 1;
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }
    }

    /**
     * 图片压缩处理，size参数为压缩比，比如size为2，则压缩为1/4
     *
     * @param path    the path
     * @param data    the data
     * @param context the context
     * @param uri     the uri
     * @return the bitmap
     */
    public static Bitmap compressBitmap(String path, byte[] data, Context context, Uri uri) {
        Options options = new Options();
        /**
         * 如果设置true的时候，decode时候Bitmap返回的为数据将空
         */
        options.inJustDecodeBounds = true;
        decodeBitmap(path, data, context, uri, options);
        getInSampleSize(options);
        options.inJustDecodeBounds = false;
        Bitmap bm = null;
        try {
            bm = decodeBitmap(path, data, context, uri, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    /**
     * 解析成Bitmap
     *
     * @param path    the path
     * @param data    the data
     * @param context the context
     * @param uri     the uri
     * @param options the options
     * @return the bitmap
     */
    private static Bitmap decodeBitmap(String path, byte[] data, Context context, Uri uri, Options options) {
        Bitmap result = null;
        if (path != null) {
            result = BitmapFactory.decodeFile(path, options);
        } else if (data != null) {
            result = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } else if (uri != null) {
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            try {
                inputStream = cr.openInputStream(uri);
                result = BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
