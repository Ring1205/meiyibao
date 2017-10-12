package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.NextPreviewActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.DesignerModel;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 邓志宏 on 2017/2/22.
 */
public class DesignerAdapter extends RecyclerView.Adapter<DesignerAdapter.ClassifyHolder> {
    private Context context;
    private int type;
    private List<DesignerModel.ListBean> date;

    public DesignerAdapter(Context context) {
        this.context = context;
    }

    public DesignerAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    public void setDate(List date) {
        this.date = date;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ClassifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassifyHolder(View.inflate(context, R.layout.item_designer, null));
    }

    @Override
    public void onBindViewHolder(ClassifyHolder holder, int position) {
        final DesignerModel.ListBean bean = date.get(position);
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
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
        ViewGroup.LayoutParams lp = holder.iv_img.getLayoutParams();
        lp.width = w;
        lp.height = h;
        holder.iv_img.setLayoutParams(lp);
        UiUtils.loadImage(context, UrlConfig.IMG + bean.getIcon(), holder.iv_img, w);
        switch (bean.getStatus()){
            case 1:
                holder.iv_state.setImageResource(R.drawable.examine_03);
                holder.tv_state.setText("审核通过");
                break;
            case 2:
                holder.iv_state.setImageResource(R.drawable.examine_01);
                holder.tv_state.setText("审核中");
                break;
            case 3:
                holder.iv_state.setImageResource(R.drawable.examine_02);
                holder.tv_state.setText("审核失败");
                break;
        }

    }

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
        @Bind(R.id.iv_img)
        ImageView iv_img;
        @Bind(R.id.iv_state)
        ImageView iv_state;
        @Bind(R.id.tv_state)
        TextView tv_state;

        public ClassifyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
