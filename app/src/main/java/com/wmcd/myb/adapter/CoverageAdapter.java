package com.wmcd.myb.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.PreviewModel;
import com.wmcd.myb.util.UiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/28.
 */
public class CoverageAdapter extends RecyclerView.Adapter<CoverageAdapter.CoverageHolder> {
    private Context context;
    private int pointer = 1102;
    private Map<Integer, Bitmap> bitmaps;
    private CoverageOnclick coverageOnclick;
    private List<PreviewModel.TemplateBean.PagesBean.ObjectsBean> data;
    private List<View> views;

    public CoverageAdapter(Context context) {
        this.context = context;
        views = new ArrayList<>();
    }

    public void setCoverageOnclick(CoverageOnclick coverageOnclick) {
        this.coverageOnclick = coverageOnclick;
    }

    public void getData(List<PreviewModel.TemplateBean.PagesBean.ObjectsBean> data, Map<Integer, Bitmap> bitmaps,int pointer) {
        this.data = data;
        this.bitmaps = bitmaps;
        this.pointer = pointer;
        views.clear();
    }

    @Override
    public CoverageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CoverageHolder(View.inflate(context, R.layout.item_coverage, null));
    }

    @Override
    public void onBindViewHolder(final CoverageHolder holder, final int position) {
        final PreviewModel.TemplateBean.PagesBean.ObjectsBean mode = data.get(position);
        holder.iv_lock.setVisibility(View.GONE);

        RelativeLayout.LayoutParams holderParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        holderParams.topMargin = UiUtils.dip2px(context, 42);
        holderParams.width = UiUtils.dip2px(context, 98);
        holderParams.height = UiUtils.dip2px(context, 98);
        holder.itemView.setLayoutParams(holderParams);

        holder.iv_back.setBackground(null);
        if (mode.getType() == 1) {
            Bitmap bitmap = bitmaps.get(position);
            if (bitmap == null) {
                holder.img.setVisibility(View.VISIBLE);
                holder.text.setVisibility(View.GONE);
                UiUtils.loadImageDontAnimate(context, UrlConfig.IMG + mode.getImg(), holder.img, UiUtils.dip2px(context, 100));
                if (data.get(position).getZoom() != 1 && data.get(position).getReplaceable() != 1) {
                    holder.iv_lock.setVisibility(View.VISIBLE);
                    holder.iv_lock.setImageResource(R.drawable.lock);
                }
            } else {
                holder.img.setVisibility(View.VISIBLE);
                holder.text.setVisibility(View.GONE);
                holder.img.setImageBitmap(bitmap);
            }
        } else if (mode.getType() == 2) {
            holder.text.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.GONE);
            String s = mode.getText();
            s = s.replaceAll("\\\\n", "\\\n");
            holder.text.setText(s);
            if (mode.getFont() != null) {
                final File filef = new File(Environment.getExternalStorageDirectory() + "/fonts/" + mode.getFont() + ".ttf");
                try {
                    Typeface tf = Typeface.createFromFile(filef);
                    holder.text.setTypeface(tf);//设置字体
                } catch (Exception e) {
                    Toast.makeText(context, "字体有误", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            holder.text.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.GONE);
            String s = mode.getText();
            s = s.replaceAll("\\\\n", "\\\n");
            holder.text.setText(s);
//            holder.text.setTextColor(Color.parseColor(String.valueOf(mode.getColor())));
//            if (mode.getFont() != null) {
//                final File filef = new File(Environment.getExternalStorageDirectory() + "/fonts/" + mode.getFont() + ".ttf");
//                try {
//                    Typeface tf = Typeface.createFromFile(filef);
//                    holder.text.setTypeface(tf);//设置字体
//                } catch (Exception e) {
//                    Toast.makeText(context, "字体有误", Toast.LENGTH_LONG).show();
//                }
//            }
        }

        holder.rl_back.getBackground().setAlpha(120);

        if (!(mode.getType() == 1 && mode.getZoom() != 1)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Coverage", " ----" + position + "       ");
                    pointer = position;
                    coverageOnclick.setCoverageItem(position);
                    for (int i = 0; i < views.size(); i++) {
                        views.get(i).setBackground(null);
                    }
                    holder.iv_back.setBackgroundResource(R.drawable.canv_20);
                }
            });
        }

        if (pointer != 1102 && pointer == position) {
            holder.iv_back.setBackgroundResource(R.drawable.canv_20);
        }

        views.add(holder.iv_back);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class CoverageHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.rl_back)
        RelativeLayout rl_back;
        @Bind(R.id.iv_lock)
        ImageView iv_lock;
        @Bind(R.id.iv_back)
        ImageView iv_back;

        public CoverageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CoverageOnclick {
        void setCoverageItem(int position);
    }
}
