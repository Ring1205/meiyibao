package com.wmcd.myb.model;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;

/**
 * 存储图层的类
 * Created by Administrator on 2017/6/21.
 */

public class SaveCoverage {
    private View view;
    private Bitmap bitmap;
    private Matrix matrix;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}
