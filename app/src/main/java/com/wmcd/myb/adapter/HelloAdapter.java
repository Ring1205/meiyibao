package com.wmcd.myb.adapter;

import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class HelloAdapter extends MypagerAdapter {
    public HelloAdapter(List<View> viewList) {
        super(viewList);
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }
}
