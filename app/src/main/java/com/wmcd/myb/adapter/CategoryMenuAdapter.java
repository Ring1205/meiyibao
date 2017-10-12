package com.wmcd.myb.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.fragment.catrgoryitem.ChoiceFragment;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.CategoryModel;
import com.wmcd.myb.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class CategoryMenuAdapter extends RecyclerView.Adapter {
    private List<CategoryModel.ListBean.TypesBean> datas_all;


    public CategoryMenuAdapter(List<CategoryModel.ListBean.TypesBean> datas_all, Context context) {
        this.datas_all = datas_all;

        this.context = context;
    }

    private Context context;
    private OnMyItemClickListener listener;


    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;

    }

    public interface OnMyItemClickListener {
        void myClick(View v, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                viewHolder = new CategoryMenuAdapter.MyHolder0(View.inflate(context, R.layout.category_menu_0, null));
                break;
            case 1:
                viewHolder = new CategoryMenuAdapter.MyHolder1(View.inflate(context, R.layout.category_menu_1, null));
                break;
            case 2:
                viewHolder = new CategoryMenuAdapter.MyHolder2(View.inflate(context, R.layout.category_menu_2, null));
                break;
        }
        return viewHolder;
    }


    private void clearAllState() {
        for (int i = 0; i < datas_all.size(); i++) {
            datas_all.get(i).setIsselect(false);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case 0:
                MyHolder0 holder0 = (MyHolder0) holder;
                if (datas_all.get(position).isselect()) {
                    holder0.jinxuan.setSelected(true);
                } else {
                    holder0.jinxuan.setSelected(false);
                }
                holder0.tv_title.setText(datas_all.get(position).getName());
                holder0.rl_click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.myClick(v, position);
                        clearAllState();
                        datas_all.get(position).setIsselect(true);
                        notifyDataSetChanged();
                    }
                });

                break;
            case 1:
                MyHolder1 holder1 = (MyHolder1) holder;
                holder1.tv_title.setText(datas_all.get(position).getName());
                break;
            case 2:
                MyHolder2 holder2 = (MyHolder2) holder;
                if (datas_all.get(position).isselect()) {
                    holder2.tv_title.setSelected(true);
                } else {
                    holder2.tv_title.setSelected(false);
                }
                holder2.tv_title.setText(datas_all.get(position).getName());
                UiUtils.loadImage(context, UrlConfig.IMG + datas_all.get(position).getImg(), holder2.iv_menu, 0);
                holder2.rl_click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("123", "position==========" + position);
                        // TODO: 2017/6/27 0027 这里的逻辑还有问题 暂时能用
                        listener.myClick(v, position);
                        clearAllState();
                        datas_all.get(position).setIsselect(true);
                        notifyDataSetChanged();
                    }
                });
                break;

        }

    }

    @Override
    public int getItemCount() {
        return datas_all == null ? 0 : datas_all.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (datas_all.get(position).getDesc().equals("0")) {
            return 0;
        } else if (datas_all.get(position).getDesc().equals("1")) {
            return 1;
        } else {
            return 2;
        }
    }

    class MyHolder0 extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.rl_click)
        RelativeLayout rl_click;
        @Bind(R.id.jinxuan)
        LinearLayout jinxuan;

        public MyHolder0(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyHolder1 extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;


        public MyHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyHolder2 extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_menu)
        ImageView iv_menu;

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.rl_click)
        RelativeLayout rl_click;

        public MyHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
