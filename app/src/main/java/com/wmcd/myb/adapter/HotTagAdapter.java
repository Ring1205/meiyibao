package com.wmcd.myb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.ClassifyActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.HomeModel01;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
class HotTagAdapter extends RecyclerView.Adapter<HotTagAdapter.MyHolder> {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The List been.
     */
    private List<HomeModel01.HotTagListBean> listBeen;

    /**
     * Instantiates a new Hot tag adapter.
     *
     * @param context the context
     */
    public HotTagAdapter(Context context) {
        this.context = context;
    }

    /**
     * Sets data.
     *
     * @param listBeen the list been
     */
    public void setData(List<HomeModel01.HotTagListBean> listBeen) {
        this.listBeen = listBeen;
    }

    /**
     * On create view holder my holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the my holder
     */
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.hot_inclod, null);
        return new MyHolder(view);
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = (int) ((windowManager.getDefaultDisplay().getWidth()-UiUtils.dpToPixels(30,context)) / 3.5);
        if (listBeen != null) {
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassifyActivity.class);
                    String name = listBeen.get(position).getName();
                    intent.putExtra("hotTagName",name+"模版");
                    intent.putExtra("tagid", listBeen.get(position).getTagid()+"");
                    context.startActivity(intent);
                }
            });
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv.getLayoutParams();
            layoutParams.setMargins((int)UiUtils.dpToPixels(5,context),(int)UiUtils.dpToPixels(5,context),0,0);
            layoutParams.width = w;
            layoutParams.height = w/3;
            String img = listBeen.get(position).getImg();
            UiUtils.loadImage(context, UrlConfig.IMG + img, holder.iv, w);
        }
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return listBeen.size() == 0 ? 0 : listBeen.size();
    }

    /**
     * The type My holder.
     */
    public class MyHolder extends RecyclerView.ViewHolder {
        /**
         * The Iv.
         */
        ImageView iv;

        /**
         * Instantiates a new My holder.
         *
         * @param itemView the item view
         */
        public MyHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_hot_item);
        }
    }
}
