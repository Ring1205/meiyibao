package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.NextPreviewActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.ClassifyModel;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 邓志宏 on 2017/2/22.
 */
public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ClassifyHolder> {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Type.
     */
    private int type;
    /**
     * The Date.
     */
    private List<ClassifyModel.ListBean> date;

    /**
     * Instantiates a new Classify adapter.
     *
     * @param context the context
     */
    public ClassifyAdapter(Context context) {
        this.context = context;
    }


    /**
     * Instantiates a new Classify adapter.
     *
     * @param context the context
     * @param type    the type
     */
    public ClassifyAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    /**
     * Set date.
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
     * On create view holder classify holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the classify holder
     */
    @Override
    public ClassifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassifyHolder(View.inflate(context, R.layout.class_item, null));
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(ClassifyHolder holder, int position) {
        final ClassifyModel.ListBean bean = date.get(position);
        holder.iv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NextPreviewActivity.class);
                intent.putExtra("tid", bean.getTid());
                context.startActivity(intent);
            }
        });
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = (int) (windowManager.getDefaultDisplay().getWidth() - UiUtils.dpToPixels(21, context)) / (type==0?2:1);
        int h = (int) (((float)w/(float) bean.getWidth())*(float) bean.getHeight());
        ViewGroup.LayoutParams lp = holder.iv_item.getLayoutParams();
        lp.width = w;
        lp.height = h;
        holder.iv_item.setLayoutParams(lp);
        UiUtils.loadImage(context, UrlConfig.IMG + bean.getIcon(), holder.iv_item, w);
        UiUtils.loadImage(context, UrlConfig.IMG + bean.getMicon(), holder.iv_login, (int) UiUtils.dpToPixels(20, context));
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
     * The type Classify holder.
     */
    class ClassifyHolder extends RecyclerView.ViewHolder {
        /**
         * The Iv item.
         */
        @Bind(R.id.iv_item)
        ImageView iv_item;
        @Bind(R.id.iv_login)
        ImageView iv_login;

        /**
         * Instantiates a new Classify holder.
         *
         * @param itemView the item view
         */
        public ClassifyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
