package com.wmcd.myb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.PreviewModel;
import com.wmcd.myb.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/15.
 */

public class NextPreviewAdapter extends RecyclerView.Adapter<NextPreviewAdapter.PreviewHolder> {
    private Context context;
    public List<ImageView> images;//图片控件集合
    private int itemType;//条目类型
    private List<PreviewModel.TemplateBean.PagesBean> pagesBean;
    private OnClickListener onClickListener;
    private int ScreenWidth;//recycleview的宽度

    public NextPreviewAdapter(Context context) {
        this.context = context;
        images = new ArrayList<>();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ScreenWidth = windowManager.getDefaultDisplay().getWidth() - UiUtils.dip2px(context, 16);
    }

    public void getOnclick(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void setItemType(int itemType){
        this.itemType = itemType;
    }

    public void setData(List<PreviewModel.TemplateBean.PagesBean> pagesBean) {
        this.pagesBean = pagesBean;
        images.clear();
    }

    @Override
    public NextPreviewAdapter.PreviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PreviewHolder(View.inflate(context, R.layout.preview_item_next, null));
    }

    @Override
    public void onBindViewHolder(NextPreviewAdapter.PreviewHolder previewHolder, final int i) {
        PreviewModel.TemplateBean.PagesBean mode = pagesBean.get(i);

        int urlWidth = 0;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) previewHolder.iv_item.getLayoutParams();
        switch (itemType) {
            case 1://一行只有一个item时
                params.width = ScreenWidth;
                params.height = ScreenWidth*mode.getHeight()/mode.getWidth();
                urlWidth = ScreenWidth;
                break;
            case 2://一行有两个item时
                int tScreenWidth = (ScreenWidth-UiUtils.dip2px(context,4))/2;
                params.width = tScreenWidth;
                params.height = tScreenWidth*mode.getHeight()/mode.getWidth();
                urlWidth = tScreenWidth;
                break;
            case 3://一行有三个item时
                int sScreenWidth = (ScreenWidth-UiUtils.dip2px(context,8))/3;
                params.width = sScreenWidth;
                params.height = sScreenWidth*mode.getHeight()/mode.getWidth();
                urlWidth = sScreenWidth;
                break;
            case 4://一行有四个item时
                int fScreenWidth = (ScreenWidth-UiUtils.dip2px(context,12))/4;
                params.width = fScreenWidth;
                params.height = fScreenWidth*mode.getHeight()/mode.getWidth();
                urlWidth = fScreenWidth;
                break;
        }
        previewHolder.iv_item.setLayoutParams(params);
        previewHolder.tv_item.setText(mode.getPageno() + "/" + pagesBean.size());
        UiUtils.loadImage(context, UrlConfig.IMG + mode.getIcon(), previewHolder.iv_item, urlWidth);

        previewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClick(i);
            }
        });

        images.add(previewHolder.iv_item);
    }

    @Override
    public int getItemCount() {
        return pagesBean == null ? 0 : pagesBean.size();
    }

    static class PreviewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item)
        ImageView iv_item;
        @Bind(R.id.tv_item)
        TextView tv_item;

        public PreviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickListener {
        void OnClick(int position);

    }
}
