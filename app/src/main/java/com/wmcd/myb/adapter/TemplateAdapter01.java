package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.NextPreviewActivity;
import com.wmcd.myb.activity.SeriesActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.HomeModel01;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/29 0029.
 */
class TemplateAdapter01 extends RecyclerView.Adapter<TemplateAdapter01.TemplateHolder01>{
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Series list been.
     */
    private List<HomeModel01.SeriesListBean> seriesListBeen;

    /**
     * Instantiates a new Template adapter 01.
     *
     * @param context the context
     */
    public TemplateAdapter01(Context context) {
        this.context = context;
    }

    /**
     * Sets data.
     *
     * @param seriesListBeen the series list been
     */
    public void setData(List<HomeModel01.SeriesListBean> seriesListBeen) {
      this.seriesListBeen = seriesListBeen;
    }

    /**
     * On create view holder template holder 01.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the template holder 01
     */
    @Override
    public TemplateHolder01 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TemplateHolder01(View.inflate(context, R.layout.imageview_item01, null));
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(TemplateHolder01 holder, final int position) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = (int) (windowManager.getDefaultDisplay().getWidth()-UiUtils.dpToPixels(10,context))/2;
        if (seriesListBeen != null) {
            holder.iv_item_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SeriesActivity.class);
                    intent.putExtra("stid", seriesListBeen.get(position).getStid()+"");
                    context.startActivity(intent);
                }
            });

            UiUtils.loadImage(context, UrlConfig.IMG + seriesListBeen.get(position).getImg(),holder.iv_item_01, w);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(w, (int) (w/1.5));
           // param.rightMargin = (int) UiUtils.dpToPixels(5, context);
            holder.iv_item_01.setLayoutParams(param);
        }
    }


    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return seriesListBeen !=null?seriesListBeen.size():0;
    }

    /**
     * The type Template holder 01.
     */
    class TemplateHolder01 extends RecyclerView.ViewHolder {

        /**
         * The Iv item 01.
         */
        ImageView iv_item_01;

        /**
         * Instantiates a new Template holder 01.
         *
         * @param itemView the item view
         */
        public TemplateHolder01(View itemView) {
            super(itemView);
            iv_item_01 = (ImageView) itemView.findViewById(R.id.iv_item_01);
        }
    }
}
