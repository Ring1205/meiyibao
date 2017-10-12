package com.wmcd.myb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.MemberModel;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/9.
 */
public class MemberAdapter extends RecyclerView.Adapter{
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Data.
     */
    private List<MemberModel.ListBean> data;
    /**
     * The Member item onclick.
     */
    private MemberItemOnclick memberItemOnclick;

    /**
     * Set member item onclick.
     *
     * @param memberItemOnclick the member item onclick
     */
    public void setMemberItemOnclick(MemberItemOnclick memberItemOnclick){
        this.memberItemOnclick = memberItemOnclick;
    }

    /**
     * Instantiates a new Member adapter.
     *
     * @param context the context
     */
    public MemberAdapter(Context context){
        this.context = context;
    }

    /**
     * Set data.
     *
     * @param data the data
     */
    public void setData(List<MemberModel.ListBean> data){
        this.data = data;
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = View.inflate(context,R.layout.member_item,null);
        view.setLayoutParams(params);
        return new MemberHolder(view);
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MemberHolder memberHolder = (MemberHolder) holder;
        final MemberModel.ListBean bean = data.get(data.size() - position-1);
        UiUtils.loadImage(context, UrlConfig.IMG+bean.getImg(),memberHolder.iv_vip,new DisplayMetrics().widthPixels);
        memberHolder.tv_time.setText(bean.getPeriodDesc());
        memberHolder.tv_introduce.setText(bean.getDesc());
        memberHolder.tv_price.setText((float)bean.getLabelprice()/100+"");
        memberHolder.rl_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberItemOnclick.setItemOnclik(bean);
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
        return data == null?0:data.size();
    }

    /**
     * The type Member holder.
     */
    static class MemberHolder extends RecyclerView.ViewHolder{
        /**
         * The Iv vip.
         */
        @Bind(R.id.iv_vip)
        ImageView iv_vip;
        /**
         * The Tv time.
         */
        @Bind(R.id.tv_time)
        TextView tv_time;
        /**
         * The Tv introduce.
         */
        @Bind(R.id.tv_introduce)
        TextView tv_introduce;
        /**
         * The Tv price.
         */
        @Bind(R.id.tv_price)
        TextView tv_price;
        /**
         * The Rl pay.
         */
        @Bind(R.id.rl_pay)
        RelativeLayout rl_pay;

        /**
         * Instantiates a new Member holder.
         *
         * @param itemView the item view
         */
        public MemberHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * The interface Member item onclick.
     */
    public interface MemberItemOnclick{
        /**
         * Sets item onclik.
         *
         * @param type the type
         */
        void setItemOnclik(MemberModel.ListBean type);
    }
}
