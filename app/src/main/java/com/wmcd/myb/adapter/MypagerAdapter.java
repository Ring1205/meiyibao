package com.wmcd.myb.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class MypagerAdapter extends PagerAdapter {


    private List<View> viewList;

    public MypagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));


        return viewList.get(position);
    }

    // TODO: 2017/6/26 0026 这个参数是手动试的
    @Override
    public float getPageWidth(int position) {
        return 0.328f;
    }
}
