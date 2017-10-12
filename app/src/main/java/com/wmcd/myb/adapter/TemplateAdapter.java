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
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.HomeModel01;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 邓志宏 on 2017/2/20.
 */
public class TemplateAdapter extends RecyclerView.Adapter {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Offdata.
     */
    private List<HomeModel01.FoldersListBean> offdata;
    /**
     * The Ondata.
     */
    private List<HomeModel01.HeadListBean> ondata;
    /**
     * The Id.
     */
    private int id = 0;


    /**
     * Sets data.
     *
     * @param ondata  the ondata
     * @param offdata the offdata
     * @param id      the id
     */
    public void setData(List<HomeModel01.HeadListBean> ondata, List<HomeModel01.FoldersListBean> offdata,int id) {
        this.ondata = ondata;
        this.offdata = offdata;
        this.id = id;
    }

    /**
     * Instantiates a new Template adapter.
     *
     * @param context the context
     */
    public TemplateAdapter(Context context) {
        this.context = context;
    }

    /**
     * On create view holder recycler view . view holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the recycler view . view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TemplateHolder(View.inflate(context, R.layout.imageview_item, null));
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TemplateHolder templateHolde = (TemplateHolder) holder;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = windowManager.getDefaultDisplay().getWidth() / 2;

        if (ondata != null) {
            templateHolde.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NextPreviewActivity.class);
                    intent.putExtra("tid", ondata.get(position).getTid());
                    context.startActivity(intent);
                }
            });
            UiUtils.loadImage(context, UrlConfig.IMG + ondata.get(position).getIcon(), (ImageView) templateHolde.itemView, w);
        }

         if (offdata!=null){
            templateHolde.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NextPreviewActivity.class);
                    intent.putExtra("tid", offdata.get(position).getTid());
                    context.startActivity(intent);
                }
            });
            UiUtils.loadImage(context, UrlConfig.IMG + offdata.get(position).getIcon(), (ImageView) templateHolde.itemView, w);
        }
        //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.rightMargin = (int) UiUtils.dpToPixels(5, context);
        templateHolde.itemView.setLayoutParams(param);
        if (id!=0){
            ViewGroup.LayoutParams paramss = new ViewGroup.LayoutParams(windowManager.getDefaultDisplay().getWidth(), (int) (windowManager.getDefaultDisplay().getWidth()/3.2));
            templateHolde.itemView.setLayoutParams(paramss);
            templateHolde.itemView.setBackgroundResource(id);
        }
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        if (id!=0)
            return  1;
        if (ondata != null)
            return ondata != null ? ondata.size() : 0;
        else
            return offdata != null ? offdata.size() : 0;

    }

    /**
     * The type Template holder.
     */
    static class TemplateHolder extends RecyclerView.ViewHolder {
        /**
         * Instantiates a new Template holder.
         *
         * @param itemView the item view
         */
        public TemplateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
