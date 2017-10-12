package com.wmcd.myb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wmcd.myb.R;
import com.wmcd.myb.util.RecyclerScaleUtils;
import com.wmcd.myb.util.ScreenUtils;

import java.util.List;

/**
 * Created by zhlf on 2017/3/26 0026.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    /**
     * The M inflater.
     */
    private LayoutInflater mInflater;
    /**
     * The M datas.
     */
    private List<Integer> mDatas;

    /**
     * Instantiates a new Gallery adapter.
     *
     * @param context the context
     * @param datats  the datats
     */
    public GalleryAdapter(Context context, List<Integer> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }


    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     *
     * @param viewGroup the view group
     * @param i         the
     * @return the view holder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_vip_ad, viewGroup, false);
        RecyclerScaleUtils.onCreateViewHolder(viewGroup, view, ScreenUtils.dip2px(viewGroup.getContext(), 30f));
        return new ViewHolder(view);
    }

    /**
     * 设置值
     *
     * @param viewHolder the view holder
     * @param position   the position
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        RecyclerScaleUtils.onBindViewHolder(viewHolder.itemView,position, getItemCount(), ScreenUtils.dip2px(viewHolder.itemView.getContext(),30f));
        viewHolder.mImg.setImageResource(mDatas.get(position));
    }

    /**
     * The type View holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The M img.
         */
        ImageView mImg;

        /**
         * Instantiates a new View holder.
         *
         * @param v the v
         */
        public ViewHolder(View v) {
            super(v);
            mImg = (ImageView) v.findViewById(R.id.imageView);
        }


    }
}