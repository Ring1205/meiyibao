package com.wmcd.myb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.model.SearchBean;

import java.util.List;

/**
 * Created by zlf on 2017/4/8 0023.
 */
public class HotAdapter extends BaseAdapter {
    /**
     * The M hot.
     */
    private List<SearchBean.HotWordListBean> mHot;
    /**
     * The Inflater.
     */
    private LayoutInflater inflater;
    /**
     * The Context.
     */
    private Context context;


    /**
     * Instantiates a new Hot adapter.
     *
     * @param mmHot    the mm hot
     * @param mcontext the mcontext
     */
    public HotAdapter(List<SearchBean.HotWordListBean> mmHot, Context mcontext) {
        this.mHot = mmHot;
        inflater = LayoutInflater.from(mcontext);
        context = mcontext;

    }

    /**
     * Refresh.
     *
     * @param mmHot the mm hot
     */
    public void refresh(List<SearchBean.HotWordListBean> mmHot) {
        this.mHot = mmHot;
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
        return mHot != null ? mHot.size() : 0;
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
        view = inflater.inflate(R.layout.item_hot_search, null);
        TextView keyTV = (TextView) view.findViewById(R.id.key_tv);
        SearchBean.HotWordListBean hot = mHot.get(position);
        keyTV.setText(hot.getWord());
        return view;
    }

}
