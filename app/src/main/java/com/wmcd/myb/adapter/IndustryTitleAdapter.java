package com.wmcd.myb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.model.IndustryModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class IndustryTitleAdapter extends RecyclerView.Adapter {
    private Context context;

    public IndustryTitleAdapter(Context context, List<IndustryModel.ListBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    private List<IndustryModel.ListBean> datas;
    private IndustryTitleAdapter.OnMyItemClickListener listener;
    private WindowManager windowManager;

    public void setOnMyItemClickListener(IndustryTitleAdapter.OnMyItemClickListener listener) {
        this.listener = listener;

    }

    public interface OnMyItemClickListener {
        void myClick(int industryid);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IndustryTitleAdapter.MyHolder(View.inflate(context, R.layout.industry_item, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final IndustryTitleAdapter.MyHolder mholder = (MyHolder) holder;
        if (datas.get(position).isselect()) {
            mholder.tv_industry.setSelected(true);
        } else {
            mholder.tv_industry.setSelected(false);
        }
        ViewGroup.LayoutParams layoutParams = mholder.tv_industry.getLayoutParams();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = (windowManager.getDefaultDisplay().getWidth()) / 4;
        layoutParams.width = w;
        layoutParams.height = w / 3;

        mholder.tv_industry.setText(datas.get(position).getIndustryname());

        mholder.tv_industry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();

                datas.get(position).setIsselect(true);
                notifyDataSetChanged();
                if (listener != null)
                    listener.myClick(datas.get(position).getIndustryid());
            }
        });

    }

    private void clearAll() {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setIsselect(false);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_industry)
        TextView tv_industry;


        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
