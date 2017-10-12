package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.SeriesActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.PropagandaModel;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */
public class PropagandaAdapter extends RecyclerView.Adapter {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Data.
     */
    private List<PropagandaModel.ListBean> data;

    /**
     * Instantiates a new Propaganda adapter.
     *
     * @param context the context
     */
    public PropagandaAdapter(Context context) {
        this.context = context;
    }

    /**
     * Gets data.
     *
     * @param data the data
     */
    public void getData(List<PropagandaModel.ListBean> data) {
        this.data = data;
    }

    /**
     * On create view holder recycler view . view holder.
     *
     * @param viewGroup the view group
     * @param i         the
     * @return the recycler view . view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PropagandaHolder(View.inflate(context, R.layout.imageview_item1, null));
    }

    /**
     * On bind view holder.
     *
     * @param viewHolder the view holder
     * @param i          the
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        PropagandaHolder holder = (PropagandaHolder) viewHolder;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = windowManager.getDefaultDisplay().getWidth() / 4;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = w;
        params.height = w / 2;
        holder.itemView.setLayoutParams(params);
        UiUtils.loadImage(context, UrlConfig.IMG + data.get(i).getIcon(), (ImageView) holder.itemView, w);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeriesActivity.class);
                intent.putExtra("stid",data.get(i).getStid()+"");
                context.startActivity(intent);
            }
        });

    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * The type Propaganda holder.
     */
    static class PropagandaHolder extends RecyclerView.ViewHolder {

        /**
         * Instantiates a new Propaganda holder.
         *
         * @param itemView the item view
         */
        public PropagandaHolder(View itemView) {
            super(itemView);
        }
    }
}
