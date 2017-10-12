package com.wmcd.myb.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.wmcd.myb.R;
import com.wmcd.myb.activity.LogoActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.GenerateLogoModel;
import com.wmcd.myb.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class GenerateLogoAdapter extends RecyclerView.Adapter {
    /**
     * The Context.
     */
    private LogoActivity context;
    /**
     * The Data.
     */
    private GenerateLogoModel data;
    /**
     * Set data.
     *
     * @param data the data
     */
    public void setData(GenerateLogoModel data){

        this.data = data;
    }
private List<ImageView> ImageViews;
    /**
     * Instantiates a new Center template adapter.
     *
     * @param context the context
     */
    public GenerateLogoAdapter(Context context) {
        data = new GenerateLogoModel();
        ImageViews = new ArrayList<>();
        this.context = (LogoActivity) context;


    }

    /**
     * On create view holder recycler view . view holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the recycler view . view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new GenerateLogoHolder(View.inflate(context, R.layout.imageview_item,null));
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    private int selectState = -1;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final GenerateLogoHolder generateLogoHolder = (GenerateLogoHolder) holder;


        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);


        int w = (windowManager.getDefaultDisplay().getWidth()/*- UiUtils.dip2px(context,12)*/)/3;
       generateLogoHolder.imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(selectState!=position){
                    selectState=position;
                    setImgFront();
                    generateLogoHolder.imageView.setImageResource(R.drawable.imageviewshap);

                }else{
                    setImgFront();
                    generateLogoHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    generateLogoHolder.imageView.setImageResource(R.drawable.logo_14);

                           context.addLogo(position);

                }
           }
       });
        UiUtils.loadImage(context, UrlConfig.IMG + data.getList().get(position).getImg(), generateLogoHolder.imageView,w,true);


        ViewGroup.LayoutParams params =  generateLogoHolder.imageView.getLayoutParams();
        params.width =w;
        params.height = w;
        ImageViews.add(generateLogoHolder.imageView);
        Log.i("showSize","ImageViews---"+ImageViews.size());
    }



    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return data != null ? data.getList().size():0;
    }

    private void setImgFront() {
        for (int i = 0;i<ImageViews.size();i++){
ImageViews.get(i).setImageDrawable(null);
        }
    }/**
     * The type Template holder.
     */
    static class GenerateLogoHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.image_item)
        ImageView imageView;
        /**
         * Instantiates a new Template holder.
         *
         * @param itemView the item view
         */
        public GenerateLogoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
