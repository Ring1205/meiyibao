package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.ClassifyActivity;
import com.wmcd.myb.activity.NextPreviewActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.ClassifyModel;
import com.wmcd.myb.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class CategoryAdapter extends RecyclerView.Adapter {


    private Context context;
    private int itemId;
    private int type;
    private List<ClassifyModel.ListBean> dateH;
    private List<ClassifyModel.ListBean> dateN;
    private List<ClassifyModel.ListBean> dateAll;

    public void setDateH(List<ClassifyModel.ListBean> dateH) {
        this.dateH = dateH;
    }

    public void setDateN(List<ClassifyModel.ListBean> dateN) {
        this.dateN = dateN;

    }

    public CategoryAdapter(Context context, int itemId, int type) {
        this.context = context;
        this.itemId = itemId;
        this.type = type;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        dateAll = new ArrayList<>();
        dateAll.addAll(dateH);
        dateAll.addAll(dateN);
        Log.e("size", "dateAll" + dateAll.size());
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        //根据viewType生成viewHolder
        switch (viewType) {
            case 0:
                viewHolder = new CategoryAdapter.CategoryHolderTitle(View.inflate(context, R.layout.category_title_item, null));
                break;
            case 1:
                viewHolder = new CategoryAdapter.CategoryHolder(View.inflate(context, R.layout.class_item, null));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //根据条目的类型给holder中的控件填充数据


        int itemViewType = getItemViewType(position);

        switch (itemViewType) {
            //标题
            case 0:
                CategoryHolderTitle holder0 = (CategoryHolderTitle) holder;
                if (position == 0)
                    holder0.tv_title.setText("热度榜");
                else
                    holder0.tv_title.setText("新品榜");
                holder0.tv_look_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(context, ClassifyActivity.class);
                        intent1.putExtra("Type", itemId + "");
                        context.startActivity(intent1);
                    }
                });
                break;
            case 1:
                CategoryHolder holder1 = (CategoryHolder) holder;
                final ClassifyModel.ListBean bean;
                if (position < dateH.size() + 1)
                    bean = dateAll.get(position - 1);
                else
                    bean = dateAll.get(position - 2);

                holder1.iv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, NextPreviewActivity.class);
                        intent.putExtra("tid", bean.getTid());
                        context.startActivity(intent);
                    }
                });

                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int w = (int) (windowManager.getDefaultDisplay().getWidth() - UiUtils.dpToPixels(21, context)) / 2;
                UiUtils.loadImage(context, UrlConfig.IMG + bean.getIcon(), holder1.iv_item, w);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (type == 1) {
                    w = w - UiUtils.dip2px(context, 40);
                }
                switch (itemId) {
                    case 3:
                        params.height = w;
                        holder1.iv_item.setLayoutParams(params);
                        break;
                    case 4:
                        params.height = (int) (w * 1.42);
                        holder1.iv_item.setLayoutParams(params);
                        break;
                    case 9:
                        params.height = (int) (w * 2.24);
                        holder1.iv_item.setLayoutParams(params);
                        break;
                    case 1:
                        holder1.iv_item.setLayoutParams(params);
                        break;
                }
                UiUtils.loadImage(context, UrlConfig.IMG + bean.getMicon(), holder1.iv_login, (int) UiUtils.dpToPixels(20, context));
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        //跟据position对应的条目返回去对应的样式（Type）
        if (position == 0 || position == dateH.size() + 1) {
            return 0;
        } else return 1;
    }

    @Override
    public int getItemCount() {
        try {
            return dateH.size() + dateN.size() + 2;
        } catch (Exception e) {
            return 0;
        }
    }


    class CategoryHolderTitle extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;

        @Bind(R.id.tv_look_all)
        TextView tv_look_all;

        public CategoryHolderTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item)
        ImageView iv_item;

        @Bind(R.id.iv_login)
        ImageView iv_login;


        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
