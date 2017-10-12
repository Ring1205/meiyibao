package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wmcd.myb.R;
import com.wmcd.myb.activity.ClassifyActivity;
import com.wmcd.myb.activity.NextPreviewActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.ClassifyModel;
import com.wmcd.myb.model.SeriesModel;
import com.wmcd.myb.util.UiUtils;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder> {

    /**
     * The Context.
     */
    private Context context;
    /**
     * The Date.
     */
    private List<SeriesModel.ListBean> date;

    /**
     * Instantiates a new Series adapter.
     *
     * @param context the context
     */
    public SeriesAdapter(Context context) {
        this.context = context;

    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(List date) {
        this.date = date;
    }

    /**
     * Gets item view type.
     *
     * @param position the position
     * @return the item view type
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * On create view holder series holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the series holder
     */
    @Override
    public SeriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SeriesHolder(View.inflate(context, R.layout.series_item, null));
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(final SeriesHolder holder, int position) {
        final SeriesModel.ListBean bean = date.get(position);
        holder.iv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NextPreviewActivity.class);
                intent.putExtra("tid", bean.getType());
               // context.startActivity(intent);
            }
        });
        int i = bean.getHeight()/bean.getWidth();
        //holder.iv_vip.setVisibility(View.GONE);
        //holder.tv_name.setText(bean.getName());
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = (int) (windowManager.getDefaultDisplay().getWidth() - UiUtils.dpToPixels(21, context));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, (int) (i*w));
        holder.iv_item.setLayoutParams(params);

       /* SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                //图片加载完成
                holder.iv_item.setImageBitmap(bitmap);
                Log.e("seriesactivity","jiazai完成");
            }
        };
        Glide.with( context ).load( UrlConfig.IMG + bean.getIcon() ).asBitmap().into( target ) ;*/
        UiUtils.loadImage(context, UrlConfig.IMG + bean.getIcon(), holder.iv_item, w,0.1f);

        holder.iv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClassifyActivity.class);
                intent.putExtra("sid",bean.getSid()+"");
                context.startActivity(intent);
            }
        });
        //holder.tv_nature.setText(bean.getName());
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return date != null ? date.size() : 0;
    }

    /**
     * The type Series holder.
     */
    class SeriesHolder extends RecyclerView.ViewHolder {
        /**
         * The Iv item.
         */
        @Bind(R.id.iv_item)
        ImageView iv_item;


        /**
         * Instantiates a new Series holder.
         *
         * @param itemView the item view
         */
        public SeriesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}