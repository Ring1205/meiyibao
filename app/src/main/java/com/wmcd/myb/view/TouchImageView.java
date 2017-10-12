package com.wmcd.myb.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.wmcd.myb.R;
import com.wmcd.myb.util.UiUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 邓志宏 on 2017/3/2.
 */
public class TouchImageView extends ImageView {
    float x_down = 0;
    float y_down = 0;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    float oldRotation = 0;
    Matrix matrix = new Matrix();
    Matrix matrix1 = new Matrix();
    Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    int mode = NONE;

    boolean matrixCheck = false;
    boolean myBoolean;

    int widthScreen;
    int heightScreen;

    Bitmap gintama;

    /**
     * Instantiates a new Touch image view.
     *
     * @param activity the activity
     */
    public TouchImageView(Activity activity) {
        super(activity);
        gintama = BitmapFactory.decodeResource(getResources(), R.drawable.err_img);

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;

        matrix = new Matrix();

        mMaxX = gintama.getWidth();
        mMaxY = gintama.getHeight();
        mCentreX = gintama.getWidth() / 2;
        mCentreY = gintama.getHeight() / 2;
        width = gintama.getWidth();
        height = gintama.getHeight();
    }

    public void setMyBoolean(boolean b) {
        myBoolean = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawBitmap(gintama, matrix, null);
        canvas.restore();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        gintama = bm;
            if (widthScreen != gintama.getWidth() && heightScreen != gintama.getHeight()) {
                widthScreen = gintama.getWidth();
                heightScreen = gintama.getHeight();
            }
        matrixCheck(0, 0, 1);
    }

    public void setMatrix(float[] floats) {
        matrix1.setValues(floats);
        matrix.set(matrix1);
    }

    /**
     * On touch event boolean.
     *
     * @param event the event
     * @return the boolean
     */
    public boolean onTouchEvent(MotionEvent event) {
        //当点击事件不处于bitmap矩阵中，将触摸事件向下传递
        if (matrixCheck(event.getX(), event.getY(), 1)) {
            return false;
        }
        if (myBoolean) {
            //图层穿透
            if (colorValueJudgment(event))
                return false;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = DRAG;
                x_down = event.getX();
                y_down = event.getY();
                savedMatrix.set(matrix);
                ontouch.startDown();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                oldDist = spacing(event);
                oldRotation = rotation(event);
                savedMatrix.set(matrix);
                midPoint(mid, event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == ZOOM) {
                    matrix1.set(savedMatrix);
                    float rotation = rotation(event) - oldRotation;
                    float newDist = spacing(event);
                    float scale = newDist / oldDist;
                    matrix1.postScale(scale, scale, mid.x, mid.y);// 縮放
                    matrix1.postRotate(rotation, mid.x, mid.y);// 旋轉
                    matrixCheck = matrixCheck(event.getX(), event.getY(), scale);
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        invalidate();
                    }
                } else if (mode == DRAG) {
                    matrix1.set(savedMatrix);
                    matrix1.postTranslate(event.getX() - x_down, event.getY()
                            - y_down);// 平移
                    matrixCheck = matrixCheck(event.getX(), event.getY(), 1);
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mode = NONE;
                ontouch.actionUp(gintama, matrix);
                System.gc();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                ontouch.actionUp(gintama, matrix);
                System.gc();
                break;
        }
        return true;
    }

    private boolean colorValueJudgment(MotionEvent event) {
        Bitmap bi = UiUtils.createViewBitmap(this);
        try {
            int color = bi.getPixel((int) event.getX(), (int) event.getY());
//            int r = Color.red(color);
//            int g = Color.green(color);
//            int b = Color.blue(color);
            int a = Color.alpha(color);
            if (a != 0) {
                //选中
                ontouch.onTouch(true);
            } else {
                mode = NONE;
                ontouch.onTouch(false);
                return true;
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return false;
    }

    /**
     * bitmap的旋转
     *
     * @param rotation
     */
    public void matrix1Rotate(float rotation) {
        float[] f = new float[9];
        matrix1.getValues(f);
        matrix1.postRotate(rotation, mCentreX, mCentreY);
        matrixCheck = matrixCheck(0, 0, 1);
        if (matrixCheck == false) {
            matrix.set(matrix1);
            invalidate();
        }
        matrix1.getValues(f);
    }

    //后台所需数据
    public float getScaleWidth() {
        return mMaxX - mMinX;
    }

    public float getDownX() {
        return mMinX;
    }

    public float getDownY() {
        return mMinY;
    }

    public float getAngle() {
        return mAngle;
    }

    public Bitmap getBitmap() {
        return gintama;
    }

    public float[] getfMatrix() {
        float[] floats = new float[9];
        matrix.getValues(floats);
        return floats;
    }

    /**
     * bitmap的缩放
     *
     * @param scale
     */
    public void matrix1Scale(float scale) {
        float[] f = new float[9];
        matrix1.getValues(f);
        matrix1.postScale(scale, scale, mCentreX, mCentreY);
        matrixCheck = matrixCheck(0, 0, 1);
        if (matrixCheck == false) {
            matrix.set(matrix1);
            invalidate();
        } else {
            matrix1.set(matrix);
        }
        matrix1.getValues(f);
    }

    float mMinX, mMinY;
    float mMaxX, mMaxY;
    float mCentreX, mCentreY;
    double width, height;
    float mAngle;

    /**
     * Matrix check boolean.
     * scale 默认比例为1
     *
     * @return the boolean
     */
    private boolean matrixCheck(float x, float y, float scale) {
        float[] f = new float[9];
        matrix1.getValues(f);
        // 图片4个顶点的坐标
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
        float y2 = f[3] * gintama.getWidth() + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] * gintama.getHeight() + f[2];
        float y3 = f[3] * 0 + f[4] * gintama.getHeight() + f[5];
        float x4 = f[0] * gintama.getWidth() + f[1] * gintama.getHeight() + f[2];
        float y4 = f[3] * gintama.getWidth() + f[4] * gintama.getHeight() + f[5];

        float[] a = {x1, x2, x3, x4};
        Arrays.sort(a);  //进行排序，从小到大
        float[] b = {y1, y2, y3, y4};
        Arrays.sort(b);
        mMinX = a[0];
        mMinY = b[0];
        mMaxX = a[3];
        mMaxY = b[3];
        mCentreX = (mMaxX + mMinX) / 2;
        mCentreY = (mMaxY + mMinY) / 2;

        Map<Float, Float> mapx = new HashMap<>();
        mapx.put(x1, y1);
        mapx.put(x2, y2);
        mapx.put(x3, y3);
        mapx.put(x4, y4);

        Map<Float, Float> mapy = new HashMap<>();
        mapy.put(y1, x1);
        mapy.put(y2, x2);
        mapy.put(y3, x3);
        mapy.put(y4, x4);


        // 图片现宽度
        width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        height = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));

        // 缩放比率判断
        if (width < widthScreen / 6) {
            if (scale < 1) {
                return true;
            }
        }
        if (width > widthScreen * 3) {
            if (scale > 1) {
                return true;
            }
        }

        //手指触摸位置
        if (x != 0 && y != 0) {
            if (x > mMinX && x < mMaxX && y > mMinY && y < mMaxY) {
                //选中
                ontouch.onTouch(true);
            } else {
                mode = NONE;
                ontouch.onTouch(false);
                return true;
            }
        }

        //旋转方向
        if (f[0] * f[1] > 0) {
            if (f[3] > 0) {//右下
                mAngle = (float) (Math.atan((mapx.get(mMaxX) - mMinY) / (mMaxX - mapy.get(mMinY))) * 180 / Math.PI) + 90;
            } else if (f[3] < 0) {//左上
                mAngle = (float) (Math.atan((mMinY - mapx.get(mMinX)) / (mapy.get(mMinY) - mMinX)) * 180 / Math.PI);
            }
        } else if (f[0] * f[1] < 0) {
            if (f[3] > 0) {//右上
                mAngle = (float) (Math.atan((mapx.get(mMaxX) - mMinY) / (mMaxX - mapy.get(mMinY))) * 180 / Math.PI);
            } else if (f[3] < 0) {//左下
                mAngle = (float) (Math.atan((mMinY - mapx.get(mMinX)) / (mapy.get(mMinY) - mMinX)) * 180 / Math.PI) - 90;
            }
        }

        // 出界判断
        if ((x1 < widthScreen / 3 && x2 < widthScreen / 3
                && x3 < widthScreen / 3 && x4 < widthScreen / 3)
                || (x1 > widthScreen * 2 / 3 && x2 > widthScreen * 2 / 3
                && x3 > widthScreen * 2 / 3 && x4 > widthScreen * 2 / 3)
                || (y1 < heightScreen / 3 && y2 < heightScreen / 3
                && y3 < heightScreen / 3 && y4 < heightScreen / 3)
                || (y1 > heightScreen * 2 / 3 && y2 > heightScreen * 2 / 3
                && y3 > heightScreen * 2 / 3 && y4 > heightScreen * 2 / 3)) {
            return false;
        }
        return false;
    }

    // 触碰两点间距离
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 取手势中心点
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    // 取旋转角度
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    // 将移动，缩放以及旋转后的图层保存为新图片
    // 本例中沒有用到該方法，需要保存圖片的可以參考
    public Bitmap CreatNewPhoto() {
        Bitmap bitmap = Bitmap.createBitmap(widthScreen, heightScreen,
                Bitmap.Config.ARGB_8888); // 背景图片
        Canvas canvas = new Canvas(bitmap); // 新建画布
        canvas.drawBitmap(gintama, matrix, null); // 画图片
        canvas.save(Canvas.ALL_SAVE_FLAG); // 保存画布
        canvas.restore();
        return bitmap;
    }

    public onTouchImage ontouch;

    public void setOntouch(onTouchImage ontouch) {
        this.ontouch = ontouch;
    }

    public interface onTouchImage {
        void onTouch(boolean b);

        void actionUp(Bitmap bitmap, Matrix matrix);
        void startDown();
    }
}

