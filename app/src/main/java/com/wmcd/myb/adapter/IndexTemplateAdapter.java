package com.wmcd.myb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wmcd.myb.R;
import com.wmcd.myb.model.HomeModel;
import com.wmcd.myb.util.RecyclerScaleUtils;
import com.wmcd.myb.util.ScreenUtils;

/**
 * Created by Administrator on 2017/3/25 0025.
 */
public class IndexTemplateAdapter extends RecyclerView.Adapter<IndexTemplateAdapter.ViewHolder> {
    /**
     * The M context.
     */
    private Context mContext;
    /**
     * The M home model.
     */
    private HomeModel mHomeModel;
    /**
     * The Window manager.
     */
    private WindowManager windowManager;

    /**
     * Instantiates a new Index template adapter.
     *
     * @param context   the context
     * @param homeModel the home model
     */
    public IndexTemplateAdapter(Context context, HomeModel homeModel) {
        this.mContext = context;
        this.mHomeModel = homeModel;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return 3;
    }

    /**
     * On create view holder view holder.
     *
     * @param viewGroup the view group
     * @param viewType  the view type
     * @return the view holder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_cardgv, viewGroup, false);
        //RecyclerScaleUtils.onCreateViewHolder(viewGroup, v, ScreenUtils.dip2px(viewGroup.getContext(), 30f));
        RecyclerScaleUtils.onCreateViewHolder(windowManager.getDefaultDisplay().getWidth(), v, ScreenUtils.dip2px(viewGroup.getContext(), 30f));
        return new ViewHolder(v);
    }

    /**
     * On bind view holder.
     *
     * @param viewHolder the view holder
     * @param position   the position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // 给ViewHolder设置元素
        // ImageLoader.getInstance().displayImage(mList.get(position).get("A"), viewHolder.mImageView);
        //设置第一个与最后item对称显示
        RecyclerScaleUtils.onBindViewHolder(viewHolder.itemView, position, getItemCount(), ScreenUtils.dip2px(viewHolder.itemView.getContext(), 30f));


        viewHolder.adGV.setGravity(Gravity.CENTER);
        viewHolder.adGV.setClickable(true);
        viewHolder.adGV.setFocusable(true);
        if (position == 0 || position == 1) {
            viewHolder.adGV.setNumColumns(2);
        } else {
            viewHolder.adGV.setNumColumns(4);
        }
        switch (position) {
            case 0:
                viewHolder.adGV.setAdapter(new GdAdapter(mContext, mHomeModel.getHeadList(), 0));
                break;
            case 1:
                viewHolder.adGV.setAdapter(new GdAdapter(mContext, mHomeModel.getPosterList(), 1));
                break;
            case 2:
                viewHolder.adGV.setAdapter(new GdAdapter(mContext, mHomeModel.getMoreList(), 2));
                break;
        }

        viewHolder.adGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int gvPosition, long l) {
                //  Toast.makeText(mContext, "GV==" + gvPosition + "===POSITION" + position, Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * The type View holder.
     */
// 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Ad gv.
         */
        public GridView adGV;
        // public TextView titleTV;

        /**
         * Instantiates a new View holder.
         *
         * @param v the v
         */
        public ViewHolder(View v) {
            super(v);
            adGV = (GridView) v.findViewById(R.id.ad_gv);
            //   titleTV = (TextView) v.findViewById(R.id.title_tv);
        }
    }
}
