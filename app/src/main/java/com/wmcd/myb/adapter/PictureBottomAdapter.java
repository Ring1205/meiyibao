package com.wmcd.myb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmcd.myb.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/27.
 */

public class PictureBottomAdapter extends RecyclerView.Adapter {
    private String[] mPNArray = {"放在最", "换图", "抠图", "旋转", "放大", "缩小", "删除"};
    private int[] mPSArray = {R.drawable.canv_01, R.drawable.canv_02, R.drawable.canv_03, R.drawable.canv_17, R.drawable.canv_04, R.drawable.canv_05, R.drawable.canv_06};
    private String[] mTNArray = {"放在最", "编辑", "放大", "缩小", "删除"};
    private int[] mTSArray = {R.drawable.canv_10, R.drawable.canv_11, R.drawable.canv_13, R.drawable.canv_12, R.drawable.canv_06};
    private Context mContext;
    public int mCompileStyle = 0;//图片是1，文字是2
    private int mWidthScreen = 0;//手机宽度像素点
    private boolean mFirstBoolean;//ture是上，false是下
    private int mTopOrBottom = 0;//判断该图层是处于顶部还是底部：0是第一次加载，1是顶部，2是底部
    private PictureClickListener pictureClickListener;

    public void setPictureClickListener(PictureClickListener pictureClickListener) {
        this.pictureClickListener = pictureClickListener;
    }

    /**
     * 加载数据
     * @param style
     * @param topOrBottom 当前图层的位置
     */
    public void setData(int style,int topOrBottom) {
        mCompileStyle = style;
        mFirstBoolean = true;
        mTopOrBottom = topOrBottom;
    }

    public PictureBottomAdapter(Context context) {
        mContext = context;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWidthScreen = windowManager.getDefaultDisplay().getWidth();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PictureBottomHolder(View.inflate(mContext, R.layout.picture_recyc_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        final PictureBottomHolder holder = (PictureBottomHolder) viewHolder;
        if (mCompileStyle == 1) {
            if (i == 0){
                switch (mTopOrBottom){
                    case 0:
                        holder.tv_01.setText(mPNArray[0] + "上面");
                        holder.iv_01.setImageResource(mPSArray[0]);
                        mFirstBoolean = true;
                        break;
                    case 1:
                        holder.tv_01.setText(mPNArray[0] + "下面");
                        holder.iv_01.setImageResource(mTSArray[0]);
                        mFirstBoolean = false;
                        break;
                    case 2:
                        holder.tv_01.setText(mPNArray[0] + "上面");
                        holder.iv_01.setImageResource(mPSArray[0]);
                        mFirstBoolean = true;
                        break;
                }
            }else {
                holder.tv_01.setText(mPNArray[i] );
                holder.iv_01.setImageResource(mPSArray[i]);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.width = mWidthScreen / 5;
            holder.itemView.setLayoutParams(params);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean b = pictureClickListener.onClick(1,i);
                    if (i == 0 && b){
                        setFirst(holder);
                    }
                }
            });

        } else if (mCompileStyle == 2) {
            if (i == 0){
                switch (mTopOrBottom){
                    case 0:
                        holder.tv_01.setText(mPNArray[i] + "下面");
                        holder.iv_01.setImageResource(mTSArray[0]);
                        mFirstBoolean = false;
                        break;
                    case 1:
                        holder.tv_01.setText(mPNArray[i] + "下面");
                        holder.iv_01.setImageResource(mTSArray[0]);
                        mFirstBoolean = false;
                        break;
                    case 2:
                        holder.tv_01.setText(mPNArray[i] + "上面");
                        holder.iv_01.setImageResource(mPSArray[0]);
                        mFirstBoolean = true;
                        break;
                }
            }else {
                holder.tv_01.setText(mTNArray[i] );
                holder.iv_01.setImageResource(mTSArray[i]);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.width = mWidthScreen / 5;
            holder.itemView.setLayoutParams(params);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean b = pictureClickListener.onClick(2,i);
                    if (i == 0 && b){
                        setFirst(holder);
                    }
                }
            });
        }
    }

    private void setFirst(PictureBottomHolder holder) {
        if (mFirstBoolean) {
            holder.tv_01.setText(mPNArray[0] + "下面");
            holder.iv_01.setImageResource(mTSArray[0]);
        } else {
            holder.tv_01.setText(mPNArray[0] + "上面");
            holder.iv_01.setImageResource(mPSArray[0]);
        }
        mFirstBoolean = !mFirstBoolean;
    }

    @Override
    public int getItemCount() {
        if (mCompileStyle == 1) {
            return mPNArray.length;
        } else if (mCompileStyle == 2) {
            return mTNArray.length;
        }
        return mCompileStyle;
    }

    class PictureBottomHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_01)
        ImageView iv_01;
        @Bind(R.id.tv_01)
        TextView tv_01;

        public PictureBottomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PictureClickListener {
        boolean onClick(int b ,int i);
    }
}
