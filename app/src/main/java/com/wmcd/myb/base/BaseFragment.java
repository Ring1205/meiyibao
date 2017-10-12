package com.wmcd.myb.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wmcd.myb.fragment.catrgoryitem.AlbumBelowFragment;
import com.wmcd.myb.net.ServeManager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 贴附的activity
     */
    protected Context context;

    /**
     * 根view
     */
    protected View mRootView;

    /**
     * On attach.
     *
     * @param context the context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    /**
     * On create view view.
     *
     * @param inflater           the inflater
     * @param container          the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutResouceId(), container, false);
        ButterKnife.bind(this, mRootView);
        setListener();
        initView();
        initData();
        Log.i("basefragment","oncreateview");
        return mRootView;
    }
    protected  changeDrawer listener;
    public void setOnchangeDrawerListener(changeDrawer listener) {
        this.listener = listener;

    }
    public  interface  changeDrawer{
        void close();
        void open();
    }

    /**
     * Init view.
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 设置监听事件
     */
    protected void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 设置根布局资源id
     *
     * @return layout resouce id
     */
    protected abstract int setLayoutResouceId();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
