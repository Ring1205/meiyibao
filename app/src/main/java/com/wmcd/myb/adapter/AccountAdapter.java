package com.wmcd.myb.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.AccountModel;
import com.wmcd.myb.util.DateUtil;
import com.wmcd.myb.util.ImageType;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class AccountAdapter extends BaseAdapter {
    /**
     * The Account model list.
     */
    private List<AccountModel.ListAccountBean> accountModelList;
    /**
     * The Inflater.
     */
    private LayoutInflater inflater;
    /**
     * The Context.
     */
    private Context context;

    /**
     * Instantiates a new Account adapter.
     *
     * @param mAccountModelList the m account model list
     * @param mcontext          the mcontext
     */
    public AccountAdapter(List<AccountModel.ListAccountBean> mAccountModelList, Context mcontext) {
        this.accountModelList = mAccountModelList;
        inflater = LayoutInflater.from(mcontext);
        context = mcontext;

    }

    /**
     * Refresh.
     *
     * @param mAccountModelList the m account model list
     */
    public void refresh(List<AccountModel.ListAccountBean> mAccountModelList) {
        this.accountModelList = mAccountModelList;
        notifyDataSetChanged();
    }

    /**
     * Gets item.
     *
     * @param i the
     * @return the item
     */
    @Override
    public Object getItem(int i) {
        return i;
    }

    /**
     * Gets item id.
     *
     * @param i the
     * @return the item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    @Override
    public int getCount() {
        return accountModelList != null ? accountModelList.size() : 0;
    }

    /**
     * Gets view.
     *
     * @param position  the position
     * @param view      the view
     * @param viewGroup the view group
     * @return the view
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        HolderView viewHolder = null;
        if (null == view) {
            viewHolder = new HolderView();
            view = inflater.inflate(R.layout.activity_account_item, null);
            viewHolder.monthTV = (TextView) view.findViewById(R.id.month_tv);
            viewHolder.timeTV = (TextView) view.findViewById(R.id.time_tv);
            viewHolder.weekTV = (TextView) view.findViewById(R.id.week_tv);
            viewHolder.moneyTV = (TextView) view.findViewById(R.id.money_tv);
            viewHolder.infoTV = (TextView) view.findViewById(R.id.info_tv);
            viewHolder.iconIV = (ImageView) view.findViewById(R.id.man_icon_iv);
            //  ButterKnife.bind(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (HolderView) view.getTag();
        }
        final AccountModel.ListAccountBean accountModel = accountModelList.get(position);
        String currentMonth = DateUtil.getMonth(accountModel.getCreatedate(), DateUtil.M_FORMAT);
        if (position == 0) {
            viewHolder.monthTV.setVisibility(View.VISIBLE);
            viewHolder.monthTV.setText(currentMonth);
        } else {
            String nextMonth = DateUtil.getMonth(accountModelList.get(position - 1).getCreatedate(), DateUtil.M_FORMAT);
            if (currentMonth.equals(nextMonth)) {
                viewHolder.monthTV.setVisibility(View.GONE);
            } else {
                viewHolder.monthTV.setVisibility(View.VISIBLE);
                viewHolder.monthTV.setText(currentMonth);
            }
        }

        viewHolder.timeTV.setText(Html.fromHtml("<font>" + DateUtil.getStrByDate(accountModel.getCreatedate(), DateUtil.M_FORMAT_TWO) + "<br/>" + DateUtil.getStrByDate(accountModel.getCreatedate(), DateUtil.M_FORMAT_THREE) + "</font>"));

        viewHolder.weekTV.setText(DateUtil.getWeekOfDate(accountModel.getCreatedate(), DateUtil.DATETIME_FORMAT));//星期
        float fstr = 0;
        if (accountModel.getCommission() != null)
            fstr = (float)Integer.parseInt(accountModel.getCommission())/100;
        else
            Toast.makeText(context,"数据错误",Toast.LENGTH_SHORT).show();
        viewHolder.moneyTV.setText("+"+fstr);
        viewHolder.infoTV.setText("好友" + accountModel.getName() + "充值" + accountModel.getMname());
        UiUtils.loadImage(context, UrlConfig.IMG + accountModel.getIconR(), viewHolder.iconIV, ImageType.CIRCLE);
        return view;
    }

    /**
     * The type Holder view.
     */
    final class HolderView {
        /**
         * The Month tv.
         */
// @Bind(R.id.month_tv)
        public TextView monthTV;
        /**
         * The Week tv.
         */
//  @Bind(R.id.week_tv)
        public TextView weekTV;
        /**
         * The Time tv.
         */
// @Bind(R.id.time_tv)
        public TextView timeTV;
        /**
         * The Money tv.
         */
// @Bind(R.id.money_tv)
        public TextView moneyTV;
        /**
         * The Info tv.
         */
// @Bind(R.id.info_tv)
        public TextView infoTV;
        /**
         * The Icon iv.
         */
// @Bind(R.id.man_icon_iv)
        public ImageView iconIV;
    }


}
