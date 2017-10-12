package com.wmcd.myb.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.LogoActivity;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.DeleteLogo;
import com.wmcd.myb.model.LogoModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/4/24.
 */
public class LogoAdapter extends RecyclerView.Adapter<LogoAdapter.MyViewHolder>{
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Data.
     */
    private List<LogoModel.ListBean> data;
    /**
     * The Window manager.
     */
    private WindowManager windowManager;
    private LayoutInflater mInflater;
    private List<ImageView>deletelogos;


    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(List<LogoModel.ListBean> data) {
        this.data = data;
    }

    /**
     * Instantiates a new Logo adapter.
     *
     * @param context the context
     */
    public LogoAdapter(Context context) {
        this.context =  context;
        data = new ArrayList<>();
        deletelogos = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    /**
     * On create view holder recycler view . view holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the recycler view . view holder
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.imageview_item, null));
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.imageview_item, parent, false));
        return holder;
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    // TODO: 2017/6/13 0013  有内存泄漏
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = (windowManager.getDefaultDisplay().getWidth() / 3);
        UiUtils.loadImage(context, UrlConfig.IMG + data.get(position).getImg(), holder.imageView, w);
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        layoutParams.width = w;
        layoutParams.height = w;
        deletelogos.add(holder.deletelogo);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isVisit = holder.deletelogo.getVisibility();
                if (isVisit==View.VISIBLE){
                    deleteLogo(position);
                }else{
                    /**
                     * 点击事件的逻辑写在这里
                     */
                    Intent intent = new Intent();
                    intent.putExtra("img",data.get(position));
                    ((LogoActivity)context).setResult(1,intent);
                    ((Activity) context).finish();
                }
                for (int i = 0;i<deletelogos.size();i++){
                    deletelogos.get(i).setVisibility(View.GONE);
                }
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                for (int i = 0;i<deletelogos.size();i++){
                    deletelogos.get(i).setVisibility(View.GONE);
                }
                holder.deletelogo.setVisibility(View.VISIBLE);
              /* Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {40,400};
                vibrator.vibrate(pattern,-1);*/
                return true;
            }
        });
    }
    public void removeItem(int position){
        notifyItemRemoved(position);
        data.remove(position);
        if(position != data.size()){

            notifyItemRangeRemoved(position, data.size() - position);

        }

    }
    private void deleteLogo(final int position) {
        ServeManager.deleteLogo(context, data.get(position).getLuid()+"").enqueue(new Callback<DeleteLogo>() {
            @Override
            public void onResponse(Call<DeleteLogo> call, Response<DeleteLogo> response) {
                if (response.body() != null)
                    Log.e(LogoActivity.class.getName(), "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    removeItem(position);
                 } else {
                    Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeleteLogo> call, Throwable t) {
                UiUtils.endnet();
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
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
            return data==null?0:data.size();
    }

    /**
     * The type My view holder.
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_item)
        ImageView imageView;
        @Bind(R.id.delete_logo)
        ImageView deletelogo;
        /**
         * Instantiates a new My view holder.
         *
         * @param view the view
         */
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,itemView);
        }
    }
}
