package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.ClassifyActivity;
import com.wmcd.myb.activity.CommentActivity;
import com.wmcd.myb.activity.LoginActivity;
import com.wmcd.myb.activity.MainTopActivity;
import com.wmcd.myb.activity.NextPreviewActivity;
import com.wmcd.myb.activity.NoticeListActivity;
import com.wmcd.myb.activity.OfferActjvity;
import com.wmcd.myb.activity.SearchActivity;
import com.wmcd.myb.activity.SearchResultActivity;
import com.wmcd.myb.activity.SeriesActivity;
import com.wmcd.myb.activity.WebViewActivity;
import com.wmcd.myb.activity.WithdrawActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.HomeModel01;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.view.AutoViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/18.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> implements View.OnClickListener {
    private List<View> viewList;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Home model.
     */
    private HomeModel01 homeModel;
    private WindowManager windowManager;

    /**
     * Sets home model.
     *
     * @param homeModel the home model
     */
    public void setHomeModel(HomeModel01 homeModel) {
        if (this.homeModel != null) {
            this.homeModel = null;
        }
        this.homeModel = homeModel;
    }

    /**
     * Instantiates a new Home adapter.
     *
     * @param context the context
     */
    public HomeAdapter(Context context) {
        this.context = context;

    }

    /**
     * On create view holder home holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the home holder
     */
    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home02, null));
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(final HomeHolder holder, int position) {
        //轮播图
        holder.ivNotices.setOnClickListener(this);
        holder.ivSearch.setOnClickListener(this);
        final List<HomeModel01.AdListBean> adList = homeModel.getAdList();
        final String[] strings = new String[adList.size()];
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final int w = windowManager.getDefaultDisplay().getWidth();
        for (int i = 0; i < adList.size(); i++) {
            strings[i] = UrlConfig.IMG + adList.get(i).getImg();
        }
        holder.autoViewpagerOne.setImageUrls(strings);
        holder.autoViewpagerOne.setImageLoader(new AutoViewPager.ImageLoader() {
            @Override
            public void loadImg(Context context, String url, ImageView tager, String isradiuValue) {
                UiUtils.loadImage(context, url, tager, w);
            }
        });
        holder.autoViewpagerOne.setOnItemClickListener(new AutoViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HomeModel01.AdListBean ad = adList.get(position - 1);
                switch (ad.getTarget()) {
                    case 1:
                        Intent intent1 = new Intent(context, WebViewActivity.class);
                        intent1.putExtra("WebUrl", ad.getUrl());
                        context.startActivity(intent1);
                        break;
                    case 2:
                        if (MyApplication.mUser != null && ad.getObjId() != null) {
                            Intent intent = new Intent(context, NextPreviewActivity.class);
                            intent.putExtra("tid", (int) ad.getObjId());
                            context.startActivity(intent);
                        } else {
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                        break;
                    case 3:
                        if (MyApplication.mUser != null) {
                            Intent intent2 = new Intent(context, MainTopActivity.class);
                            intent2.putExtra("member", true);
                            context.startActivity(intent2);
                        } else
                            context.startActivity(new Intent(context, LoginActivity.class));
                        break;
                    case 4:
                        if (MyApplication.mUser != null)
                            context.startActivity(new Intent(context, WithdrawActivity.class));
                        else
                            context.startActivity(new Intent(context, LoginActivity.class));
                        break;
                    case 5:
                        if (MyApplication.mUser != null) {
                            Intent intent = new Intent(context, SeriesActivity.class);
                            intent.putExtra("stid", ad.getObjId() + "");
                            context.startActivity(intent);
                        } else {
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                        break;
                    case 6:
                        if (MyApplication.mUser != null) {
                            Intent intent = new Intent(context, ClassifyActivity.class);
                            intent.putExtra("tagid", ad.getObjId() + "");
                            context.startActivity(intent);
                        } else {
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                        break;
                    case 7:

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("key",ad.getKey());
                        intent.putExtra("banner","123");

                        context.startActivity(intent);
                }
            }
        });

        /**
         * 头像模板
         */
        showHead(holder, windowManager);

        /**
         * 海报模板
         */
        showPost(holder,windowManager);
        /**
         * 线上名片
         */
       showCard(holder,windowManager);
        /**
         * 易拉宝
         */
        showExhibition(holder,windowManager);
        /**
         * 最新折页
         */
        showFold(holder,windowManager);
        /**
         * 系列推荐
         */
        TemplateAdapter01 templateAdapter01 = new TemplateAdapter01(context);
        holder.vpSeries.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        templateAdapter01.setData(homeModel.getSeriesList());
        holder.vpSeries.setAdapter(templateAdapter01);
        templateAdapter01.notifyDataSetChanged();

        List<HomeModel01.HotTagListBean> strs3 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            strs3.add(homeModel.getHotTagList().get(i));
        }
        HotTagAdapter hotTagAdapter = new HotTagAdapter(context);
        holder.rvBqHot01.setLayoutManager(new GridLayoutManager(context,2,LinearLayoutManager.HORIZONTAL,false));
        holder.rvBqHot01.setAdapter(hotTagAdapter);


        hotTagAdapter.setData(homeModel.getHotTagList());
        hotTagAdapter.notifyDataSetChanged();
    }

    private void showFold(HomeHolder holder, WindowManager windowManager) {
        List<String> url = new ArrayList<>();
        List<Integer> tid = new ArrayList<>();
        int w = (windowManager.getDefaultDisplay().getWidth()) / 3;
        int height = homeModel.getFoldersList().get(0).getHeight();
        int width = homeModel.getFoldersList().get(0).getWidth();
        float ratio = (float) w / (float) width;
        for (int i = 0; i < homeModel.getFoldersList().size(); i++) {
            url.add(homeModel.getFoldersList().get(i).getIcon());
            tid.add(homeModel.getFoldersList().get(i).getTid());
        }
        show(holder.vpFold, ratio, height, url,tid);
    }

    private void showExhibition(HomeHolder holder, WindowManager windowManager) {
        List<String> url = new ArrayList<>();
        List<Integer> tid = new ArrayList<>();
        int w = (windowManager.getDefaultDisplay().getWidth()) / 3;
        int height = homeModel.getExhibitionFrameList().get(0).getHeight();
        int width = homeModel.getExhibitionFrameList().get(0).getWidth();
        float ratio = (float) w / (float) width;
        for (int i = 0; i < homeModel.getExhibitionFrameList().size(); i++) {
            url.add(homeModel.getExhibitionFrameList().get(i).getIcon());
            tid.add(homeModel.getExhibitionFrameList().get(i).getTid());
        }
        show(holder.vpExhibition, ratio, height, url,tid);
    }

    private void showCard(HomeHolder holder, WindowManager windowManager) {
        List<String> url = new ArrayList<>();
        List<Integer> tid = new ArrayList<>();

        int w = (windowManager.getDefaultDisplay().getWidth()) / 3;
        int height = homeModel.getBusinessCardList().get(0).getHeight();
        int width = homeModel.getBusinessCardList().get(0).getWidth();
        float ratio = (float) w / (float) width;
        for (int i = 0; i < homeModel.getBusinessCardList().size(); i++) {
            url.add(homeModel.getBusinessCardList().get(i).getIcon());
            tid.add(homeModel.getBusinessCardList().get(i).getTid());
        }
        show(holder.vpCard, ratio, height, url,tid);

    }

    private void showPost(HomeHolder holder, WindowManager windowManager) {
        List<String> url = new ArrayList<>();
        List<Integer> tid = new ArrayList<>();
        int w = (windowManager.getDefaultDisplay().getWidth()) / 3;
        int height = homeModel.getPosterList().get(0).getHeight();
        int width = homeModel.getPosterList().get(0).getWidth();
        float ratio = (float) w / (float) width;
        for (int i = 0; i < homeModel.getPosterList().size(); i++) {
            url.add(homeModel.getPosterList().get(i).getIcon());
            tid.add(homeModel.getPosterList().get(i).getTid());
        }
        show(holder.vpPost, ratio, height, url,tid);
    }

    private void showHead(HomeHolder holder, WindowManager windowManager) {
        List<String> url = new ArrayList<>();
        List<Integer> tid = new ArrayList<>();
        int w = (windowManager.getDefaultDisplay().getWidth()) / 3;
        int height = homeModel.getHeadList().get(0).getHeight();
        int width = homeModel.getHeadList().get(0).getWidth();
        float ratio = (float) w / (float) width;
        for (int i = 0; i < homeModel.getHeadList().size(); i++) {
            url.add(homeModel.getHeadList().get(i).getIcon());
            tid.add(homeModel.getHeadList().get(i).getTid());
        }
        show(holder.vpHead, ratio, height, url,tid);
    }

    private void show(ViewPager vp, float ratio, int height, List<String> url, final List<Integer> tid) {
        int w = (windowManager.getDefaultDisplay().getWidth() - (int) UiUtils.dpToPixels(18, context)) / 3;
        int padding = (int) UiUtils.dpToPixels(3, context);
        viewList = new ArrayList<>();
        for (int i = 0; i < url.size(); i++) {
            ImageView imageView = new ImageView(context);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, (int) (ratio * height));
            layoutParams.setMargins((int) UiUtils.dpToPixels(2, context), 0, (int) UiUtils.dpToPixels(2, context), 0);
            imageView.setBackgroundResource(R.drawable.pic_bg);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setLayoutParams(layoutParams);

            UiUtils.loadImage(context, UrlConfig.IMG + url.get(i), imageView, w);
            viewList.add(imageView);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NextPreviewActivity.class);
                    intent.putExtra("tid",tid.get(finalI));
                    context.startActivity(intent);
                }
            });
        }
        int height1 = (int) (ratio * height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height1);
        layoutParams.setMargins((int) UiUtils.dpToPixels(3, context), 0, (int) UiUtils.dpToPixels(3, context), 0);
        vp.setLayoutParams(layoutParams);
        vp.setPageMargin((int) UiUtils.dpToPixels(3, context));
        vp.setAdapter(new MypagerAdapter(viewList));
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return homeModel == null ? 0 : 1;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_notices://头像
                intent.setClass(context,NoticeListActivity.class);
                break;
            case R.id.iv_search:
                intent.setClass(context, SearchActivity.class);
                break;
        }
        context.startActivity(intent);
    }

    /**
     * The type Home holder.
     */
    class HomeHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_notices)
        ImageView ivNotices;
        @Bind(R.id.iv_search)
        ImageView ivSearch;
        @Bind(R.id.ll_shousuo)
        RelativeLayout llShousuo;
        @Bind(R.id.auto_viewpager_one)
        AutoViewPager autoViewpagerOne;
        @Bind(R.id.shutiao_01)
        ImageView shutiao01;
        @Bind(R.id.iv_remen_01)
        ImageView ivRemen01;
        @Bind(R.id.rv_bq_hot_01)
        RecyclerView rvBqHot01;
        @Bind(R.id.iv_muzhi_01)
        ImageView ivMuzhi01;
        @Bind(R.id.iv_tj_01)
        ImageView ivTj01;
        @Bind(R.id.vp_series)
        RecyclerView vpSeries;
        @Bind(R.id.shutiao_03)
        ImageView shutiao03;
        @Bind(R.id.tv_xs_01)
        TextView tvXs01;
        @Bind(R.id.vp_head)
        ViewPager vpHead;
        @Bind(R.id.shutiao_09)
        ImageView shutiao09;
        @Bind(R.id.tv_xs_02)
        TextView tvXs02;
        @Bind(R.id.vp_post)
        ViewPager vpPost;
        @Bind(R.id.shutiao_06)
        ImageView shutiao06;
        @Bind(R.id.tv_xx_04)
        TextView tvXx04;
        @Bind(R.id.vp_card)
        ViewPager vpCard;
        @Bind(R.id.shutiao_05)
        ImageView shutiao05;
        @Bind(R.id.tv_xx_01)
        TextView tvXx01;
        @Bind(R.id.vp_exhibition)
        ViewPager vpExhibition;
        @Bind(R.id.shutiao_07)
        ImageView shutiao07;
        @Bind(R.id.tv_xx_03)
        TextView tvXx03;
        @Bind(R.id.vp_fold)
        ViewPager vpFold;
        @Bind(R.id.ll_web)
        LinearLayout llWeb;
        @Bind(R.id.sv_view)
        ScrollView svView;

        /**
         * Instantiates a new Home holder.
         *
         * @param itemView the item view
         */
        public HomeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * On click.查看全部的点击事件
         *
         * @param view the view
         */
        @OnClick({R.id.tv_xs_01, R.id.tv_xs_02, R.id.tv_xx_01, R.id.tv_xx_03, R.id.tv_xx_04})
        public void onClick(View view) {
            Intent intent = new Intent(context, ClassifyActivity.class);
            switch (view.getId()) {
                case R.id.tv_xs_01://头像
                    intent.putExtra("Type", "3");
                    break;
                case R.id.tv_xs_02://海报
                    intent.putExtra("Type", "4");
                    break;
                case R.id.tv_xx_01://易拉宝
                    intent.putExtra("Type", "9");
                    break;
                case R.id.tv_xx_03://折页的全部
                    intent.putExtra("Type", "8");
                    break;
                case R.id.tv_xx_04://名片
                    intent.putExtra("Type", "7");
                    break;
            }
            context.startActivity(intent);
        }
    }
}
