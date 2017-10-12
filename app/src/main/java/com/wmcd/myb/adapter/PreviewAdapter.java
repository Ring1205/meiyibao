package com.wmcd.myb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * Created by Administrator on 2017/3/21.
 */
public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.PreviewHolder> {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Data.
     */
    private List<PreviewModel.TemplateBean.PagesBean> data;
    /**
     * The View time one.
     */
    private int VIEW_TIME_ONE = 1;
    /**
     * The View time two.
     */
    private int VIEW_TIME_TWO = 2;
    /**
     * The View time three.
     */
    private int VIEW_TIME_THREE = 3;
    /**
     * The View time.
     */
    private int VIEW_TIME = 0;
    /**
     * The On clickable.
     */
    private OnClickable onClickable;
    /**
     * The Images.
     */
    public static List<ImageView> images, /**
     * The Seles.
     */
    seles;//图片、勾选

    /**
     * Sets on clickable.
     *
     * @param onClickable the on clickable
     */
    public void setOnClickable(OnClickable onClickable) {
        this.onClickable = onClickable;
    }

    /**
     * Instantiates a new Preview adapter.
     *
     * @param context the context
     */
    public PreviewAdapter(Context context) {
        this.context = context;
        images = new ArrayList<>();
        seles = new ArrayList<>();
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(List<PreviewModel.TemplateBean.PagesBean> data) {
        this.data = data;
    }

    /**
     * Gets item view type.
     *
     * @param position the position
     * @return the item view type
     */
    @Override
    public int getItemViewType(int position) {
        return VIEW_TIME;
    }

    /**
     * On create view holder preview holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the preview holder
     */
    @Override
    public PreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PreviewHolder holder = new PreviewHolder(LayoutInflater.from(context).inflate(R.layout.preview_item, null));
        if (viewType == VIEW_TIME_ONE) {
            holder.ll_01.setVisibility(View.GONE);
            holder.ll_02.setVisibility(View.GONE);
            holder.ll_03.setVisibility(View.VISIBLE);
//            holder.iv_031.setVisibility(View.GONE);
        } else if (viewType == VIEW_TIME_TWO) {
            holder.ll_03.setVisibility(View.GONE);
            holder.ll_01.setVisibility(View.VISIBLE);
            holder.ll_02.setVisibility(View.VISIBLE);
            if (data.get(0).getType() != 5) {
                holder.iv_bian_l.setVisibility(View.GONE);
                holder.iv_bian_r.setVisibility(View.GONE);
            }
        } else if (viewType == VIEW_TIME_THREE) {
            holder.ll_03.setVisibility(View.VISIBLE);
            holder.ll_01.setVisibility(View.VISIBLE);
            holder.ll_02.setVisibility(View.VISIBLE);
            holder.iv_bian_l.setVisibility(View.GONE);
            holder.iv_bian_r.setVisibility(View.GONE);
        }
        return holder;
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(final PreviewHolder holder, final int position) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = (int) (windowManager.getDefaultDisplay().getWidth() - UiUtils.dpToPixels(12, context));
        switch (VIEW_TIME) {
            //头像和名片的逻辑
            case 1:
                if (images.size() >= position+1 && seles.size() >= position+1 && images.size() != 0) {
                    holder.iv_031 = seles.get(position-1);
                    holder.iv_03 = images.get(position-1);
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.WRAP_CONTENT);
                    holder.ll_03.setLayoutParams(params);
                    UiUtils.loadImage(context, UrlConfig.IMG + data.get(position).getIcon(), holder.iv_03, w);
                    if (position == 0)
                        holder.iv_031.setSelected(true);
                    holder.tv_033.setText(position + 1 + "");
                    holder.tv_034.setText("/" + data.size());
                    onClickable.getPages(data.get(position), position);
                    holder.ll_03.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickable.getPages(data.get(position), position);
                            holder.iv_031.setSelected(true);
                        }
                    });
                    images.add(holder.iv_03);
                    seles.add((holder.iv_031));
                }
                break;
            case 2:
                final int l = position * 2;
                final int r = l + 1;
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(w / 2, (UiUtils.getZoomXY(data.get(l).getHeight(), data.get(l).getWidth(), w / 2)));
                holder.rl_l.setLayoutParams(params1);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(w / 2, (UiUtils.getZoomXY(data.get(l).getHeight(), data.get(l).getWidth(), w / 2)));
                holder.rl_r.setLayoutParams(params2);
                if (images.size() >= r && seles.size() >= r) {
                    holder.iv_01 = images.get(l);
                    holder.iv_02 = images.get(r);
                    holder.iv_011 = seles.get(l);
                    holder.iv_021 = seles.get(r);
                } else {
                    UiUtils.loadImage(context, UrlConfig.IMG + data.get(l).getIcon(), holder.iv_01, w / 2);
                    UiUtils.loadImage(context, UrlConfig.IMG + data.get(r).getIcon(), holder.iv_02, w / 2);
                    holder.iv_011.setSelected(false);
                    holder.iv_021.setSelected(false);

                    holder.tv_013.setText(l + 1 + "");
                    holder.tv_014.setText("/" + data.size());
                    holder.tv_023.setText(r + 1 + "");
                    holder.tv_024.setText("/" + data.size());
                    holder.ll_02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickable.getPages(data.get(r), r);
                            holder.iv_021.setSelected(true);
                        }
                    });
                    holder.ll_01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickable.getPages(data.get(l), l);
                            holder.iv_011.setSelected(true);
                        }
                    });

                    images.add(holder.iv_01);
                    seles.add(holder.iv_011);
                    images.add(holder.iv_02);
                    seles.add(holder.iv_021);
                }
                break;
            case 3:
                final int o = position * 3;
                final int t = o + 1;
                final int h = o + 2;
                UiUtils.loadImage(context, UrlConfig.IMG + data.get(o).getIcon(), holder.iv_01, w / 3);
                UiUtils.loadImage(context, UrlConfig.IMG + data.get(t).getIcon(), holder.iv_02, w / 3);
                UiUtils.loadImage(context, UrlConfig.IMG + data.get(h).getIcon(), holder.iv_02, w / 3);
                holder.iv_031.setSelected(false);
                holder.iv_011.setSelected(false);
                holder.iv_021.setSelected(false);
                holder.tv_013.setText(o + 1 + "");
                holder.tv_014.setText("/" + data.size());
                holder.tv_023.setText(t + 1 + "");
                holder.tv_024.setText("/" + data.size());
                holder.tv_033.setText(h + 1 + "");
                holder.tv_034.setText("/" + data.size());
                holder.ll_03.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickable.getPages(data.get(h), h);
                        holder.iv_031.setSelected(true);
                    }
                });
                holder.ll_02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickable.getPages(data.get(t), t);
                        holder.iv_021.setSelected(true);
                    }
                });
                holder.ll_01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickable.getPages(data.get(o), o);
                        holder.iv_011.setSelected(true);
                    }
                });
                images.add(holder.iv_01);
                seles.add(holder.iv_011);
                images.add(holder.iv_02);
                seles.add(holder.iv_021);
                images.add(holder.iv_03);
                seles.add(holder.iv_031);
                break;
        }
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        if (VIEW_TIME == 1) {
            int size = data.size();
            Log.e("mingpian","data--"+size);
            return data == null ? 0 : data.size();

        } else if (VIEW_TIME == 2) {
            return data == null ? 0 : data.size() / 2;
        } else if (VIEW_TIME == 3) {
            return data == null ? 0 : data.size() / 3;
        } else {
            return 0;
        }
    }

    /**
     * Sets .
     *
     * @param listtype the listtype
     */
    public void setlistview(int listtype) {
        VIEW_TIME = listtype;
    }

    /**
     * The type Preview holder.
     */
    static class PreviewHolder extends RecyclerView.ViewHolder {
        /**
         * The Ll 01.
         */
        @Bind(R.id.ll_01)
        LinearLayout ll_01;
        /**
         * The Ll 02.
         */
        @Bind(R.id.ll_02)
        LinearLayout ll_02;
        /**
         * The Ll 03.
         */
        @Bind(R.id.ll_03)
        LinearLayout ll_03;
        /**
         * The Iv 01.
         */
        @Bind(R.id.iv_01)
        ImageView iv_01;
        /**
         * The Iv 02.
         */
        @Bind(R.id.iv_02)
        ImageView iv_02;
        /**
         * The Iv 03.
         */
        @Bind(R.id.iv_03)
        ImageView iv_03;
        /**
         * The Iv bian l.
         */
        @Bind(R.id.iv_bian_l)
        ImageView iv_bian_l;
        /**
         * The Iv bian r.
         */
        @Bind(R.id.iv_bian_r)
        ImageView iv_bian_r;
        /**
         * The Iv 031.
         */
        @Bind(R.id.iv_031)
        ImageView iv_031;
        /**
         * The Iv 021.
         */
        @Bind(R.id.iv_021)
        ImageView iv_021;
        /**
         * The Iv 011.
         */
        @Bind(R.id.iv_011)
        ImageView iv_011;
        /**
         * The Tv 033.
         */
        @Bind(R.id.tv_033)
        TextView tv_033;
        /**
         * The Tv 034.
         */
        @Bind(R.id.tv_034)
        TextView tv_034;
        /**
         * The Tv 023.
         */
        @Bind(R.id.tv_023)
        TextView tv_023;
        /**
         * The Tv 024.
         */
        @Bind(R.id.tv_024)
        TextView tv_024;
        /**
         * The Tv 013.
         */
        @Bind(R.id.tv_013)
        TextView tv_013;
        /**
         * The Tv 014.
         */
        @Bind(R.id.tv_014)
        TextView tv_014;
        /**
         * The Rl l.
         */
        @Bind(R.id.rl_l)
        RelativeLayout rl_l;
        /**
         * The Rl r.
         */
        @Bind(R.id.rl_r)
        RelativeLayout rl_r;

        /**
         * Instantiates a new Preview holder.
         *
         * @param itemView the item view
         */
        public PreviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * The interface On clickable.
     */
    public interface OnClickable {
        /**
         * Gets pages.
         *
         * @param pagesBeanList the pages bean list
         * @param pager         the pager
         */
        void getPages(PreviewModel.TemplateBean.PagesBean pagesBeanList, int pager);
    }
}
