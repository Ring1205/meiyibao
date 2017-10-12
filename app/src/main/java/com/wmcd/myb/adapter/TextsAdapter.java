package com.wmcd.myb.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.FontsModel;
import com.wmcd.myb.util.UiUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/6.
 */
public class TextsAdapter extends RecyclerView.Adapter<TextsAdapter.TextsHolder> {
    /**
     * The Font list.
     */
    private List<FontsModel.ListBean> fontList;
    /**
     * The Context.
     */
    public Context context;
    /**
     * The Image view.
     */
    private ImageView imageView;

    /**
     * Instantiates a new Texts adapter.
     *
     * @param context the context
     */
    public TextsAdapter(Context context) {
        this.context = context;
        imageView = new ImageView(context);
    }

    /**
     * Sets fonts.
     *
     * @param fontList the font list
     */
    public void setFonts(List<FontsModel.ListBean> fontList) {
        this.fontList = fontList;
    }

    /**
     * The Member item onclick.
     */
    private MemberItemOnclick memberItemOnclick;

    /**
     * Gets member item onclick.
     *
     * @param memberItemOnclick the member item onclick
     */
    public void getMemberItemOnclick(MemberItemOnclick memberItemOnclick) {
        this.memberItemOnclick = memberItemOnclick;
    }

    /**
     * On create view holder texts holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the texts holder
     */
    @Override
    public TextsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextsHolder(View.inflate(context, R.layout.edittext_item, null));
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(final TextsHolder holder, final int position) {
        holder.iv_dian.setVisibility(View.GONE);
        UiUtils.loadImage(context, UrlConfig.IMG + fontList.get(position).getIcon(), holder.iv_set_text_item, UiUtils.dip2px(context, 150));
        double kb = fontList.get(position).getZipsize() / 1024;
        double mb = kb / 1024;
        if (mb < 1)
            holder.tv_set_down_text.setText(kb + "K");
        else
            holder.tv_set_down_text.setText(mb + "M");
        final File file = new File(Environment.getExternalStorageDirectory() + "/fonts/" + fontList.get(position).getName() + ".ttf");

        if (file.exists()){
            holder.tv_set_down_text.setText("已存在");
            holder.iv_down_text.setVisibility(View.GONE);
        }else
            holder.iv_down_text.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!file.exists()) {
                    holder.tv_set_down_text.setText("下载中...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String urlStr = URLEncoder.encode(fontList.get(position).getName() + ".zip", "UTF-8");
                                urlStr = urlStr.replaceAll("\\+", "%20");
                                Log.e("PictureEditorActivity", urlStr);
                                UiUtils.downLoadFromUrl(UrlConfig.FONT + urlStr, Environment.getExternalStorageDirectory() + "/fonts/" + fontList.get(position).getName() + ".ttf");
                            } catch (IOException e) {
                                Toast.makeText(context, fontList.get(position).getName() + "字体下载失败", Toast.LENGTH_SHORT).show();
                                Log.e("PictureEditorActivity", e.getMessage());
                            }
                            Activity activity = (Activity)context;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.iv_down_text.setVisibility(View.GONE);
                                    holder.tv_set_down_text.setText("已存在");
                                }
                            });
                        }
                    }).start();
                } else {
                    holder.iv_dian.setVisibility(View.VISIBLE);
                    holder.iv_down_text.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    imageView = holder.iv_dian;
                    memberItemOnclick.setItemOnclik(fontList.get(position).getName());
                }
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
        return fontList == null ? 0 : fontList.size();
    }

    /**
     * The type Texts holder.
     */
    public static class TextsHolder extends RecyclerView.ViewHolder {
        /**
         * The Iv dian.
         */
        @Bind(R.id.iv_dian)
        public ImageView iv_dian;
        /**
         * The Iv set text item.
         */
        @Bind(R.id.iv_set_text_item)
        public ImageView iv_set_text_item;
        /**
         * The Iv down text.
         */
        @Bind(R.id.iv_down_text)
        public ImageView iv_down_text;
        /**
         * The Tv set down text.
         */
        @Bind(R.id.tv_set_down_text)
        public TextView tv_set_down_text;

        /**
         * Instantiates a new Texts holder.
         *
         * @param itemView the item view
         */
        public TextsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * The interface Member item onclick.
     */
    public interface MemberItemOnclick {
        /**
         * Sets item onclik.
         *
         * @param string the string
         */
        void setItemOnclik(String string);
    }
}
