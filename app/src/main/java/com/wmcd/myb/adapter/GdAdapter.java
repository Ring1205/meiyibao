package com.wmcd.myb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.HomeModel;
import com.wmcd.myb.util.UiUtils;

import java.util.List;

/**
 * Created by zlf on 2017/3/24 0024.
 */
public class GdAdapter extends BaseAdapter {
    /**
     * The Ad list.
     */
    private List<HomeModel.OnlineListBean> adList;
    /**
     * The M inflater.
     */
    private LayoutInflater mInflater;
    /**
     * The Card position.
     */
    private int cardPosition;
    /**
     * The W.
     */
    private int w;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Window manager.
     */
    private WindowManager windowManager;

    /**
     * Instantiates a new Gd adapter.
     *
     * @param mContext the m context
     * @param mAdList  the m ad list
     * @param disable  the disable
     */
    public GdAdapter(Context mContext, List<HomeModel.OnlineListBean> mAdList, int disable) {
        this.context = mContext;
        this.adList = mAdList;
        mInflater = LayoutInflater.from(mContext);
        cardPosition = disable;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    @Override
    public int getCount() {
        return adList.size();
    }

    /**
     * Gets item.
     *
     * @param position the position
     * @return the item
     */
    @Override
    public Object getItem(int position) {

        return adList.get(position);
    }

    /**
     * Gets item id.
     *
     * @param position the position
     * @return the item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Gets view.
     *
     * @param position    the position
     * @param convertView the convert view
     * @param parent      the parent
     * @return the view
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if (convertView == null) {
            holderView = new HolderView();
            if (cardPosition == 2) {
                convertView = mInflater.inflate(R.layout.item_pic_four, null);
            }else {
                convertView = mInflater.inflate(R.layout.item_pic_three, null);
            }
            holderView.iv = (ImageView) convertView.findViewById(R.id.imageView);
            holderView.titleTV = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        HomeModel.OnlineListBean adBean = adList.get(position);

        if (cardPosition == 2) {
            UiUtils.loadImage(context, UrlConfig.IMG + adBean.getIcon(), holderView.iv, windowManager.getDefaultDisplay().getWidth() / 4);
        } else {
            UiUtils.loadImage(context, UrlConfig.IMG + adBean.getIcon(), holderView.iv, windowManager.getDefaultDisplay().getWidth() / 2);
        }


//        if (cardPosition==1) {
//            holderView.titleTV.setText(adBean.getTitle());
//            holderView.titleTV.setVisibility(View.VISIBLE);
//        } else {
//            holderView.titleTV.setVisibility(View.GONE);
//        }

        return convertView;
    }

    /**
     * The type Holder view.
     */
    final class HolderView {
        /**
         * The Iv.
         */
        ImageView iv;
        /**
         * The Title tv.
         */
        TextView titleTV;
    }

}
