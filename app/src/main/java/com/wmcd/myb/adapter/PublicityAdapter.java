package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.NextPreviewActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.PropagandaModel;
import com.wmcd.myb.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class PublicityAdapter extends RecyclerView.Adapter {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Data.
     */
    private List<PropagandaModel.TemplateListBean> data;
    /**
     * Set data.
     *
     * @param data the data
     */
    public void setData(List data){
        this.data = data;
    }

    /**
     * Instantiates a new Center template adapter.
     *
     * @param context the context
     */
    public PublicityAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
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
        return new PublicityAdapter.TemplateHolder(View.inflate(context, R.layout.imageview_item1,null));
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        PublicityAdapter.TemplateHolder templateHolde = (PublicityAdapter.TemplateHolder) holder;
        templateHolde.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NextPreviewActivity.class);
                int tid = data.get(position).getTid();
                i.putExtra("tid",tid);
                context.startActivity(i);
            }
        });
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = (windowManager.getDefaultDisplay().getWidth()- UiUtils.dip2px(context,12))/2;
        UiUtils.loadImage(context, UrlConfig.IMG + data.get(position).getIcon(), templateHolde.imageView,w);
        float ratio = (float)data.get(position).getHeight()/(float)data.get(position).getWidth();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = w;
        layoutParams.height = (int) (w*ratio);
        templateHolde.imageView.setLayoutParams(layoutParams);

        Log.i("showSize","w---"+w+"ratio---"+ratio);
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return data != null ? data.size():0;
    }

    /**
     * The type Template holder.
     */
    static class TemplateHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.image_item)
        ImageView imageView;
        /**
         * Instantiates a new Template holder.
         *
         * @param itemView the item view
         */
        public TemplateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
