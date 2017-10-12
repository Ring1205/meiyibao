package com.wmcd.myb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.model.SystemMsgBean;
import com.wmcd.myb.util.DateUtil;
import com.wmcd.myb.util.UiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zlf on 2017/4/11 0023.
 */
public class NoticeAdapter extends BaseAdapter {
    /**
     * The System msg list.
     */
    private List<SystemMsgBean.ListBean> systemMsgList;
    /**
     * The Inflater.
     */
    private LayoutInflater inflater;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Type.
     */
    private boolean type = true;


    /**
     * Instantiates a new Notice adapter.
     *
     * @param msystemMsgList the msystem msg list
     * @param mcontext       the mcontext
     */
    public NoticeAdapter(List<SystemMsgBean.ListBean> msystemMsgList, Context mcontext) {
        this.systemMsgList = msystemMsgList;
        inflater = LayoutInflater.from(mcontext);
        context = mcontext;

    }

    /**
     * Refresh.
     *
     * @param msystemMsgList the msystem msg list
     * @param type           the type
     */
    public void refresh(List<SystemMsgBean.ListBean> msystemMsgList,boolean type ) {
        this.systemMsgList = msystemMsgList;
        this.type = type;
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
        return systemMsgList != null ? systemMsgList.size() : 0;
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
            if (type){
                // 系统消息
            view = inflater.inflate(R.layout.item_notice, null);}
            else{

                //资产消息
                view = inflater.inflate(R.layout.item_notices_assets, null);

            }
            viewHolder.timeTV = (TextView) view.findViewById(R.id.time_tv);
            viewHolder.titleTV = (TextView) view.findViewById(R.id.title_tv);
            viewHolder.contentTV = (TextView) view.findViewById(R.id.content_tv);
            viewHolder.docIV = (ImageView) view.findViewById(R.id.isRead_iv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (HolderView) view.getTag();
        }
        final SystemMsgBean.ListBean sys = systemMsgList.get(position);
        Date date = StringToDate(sys.getCreatedate());
        String s = DateUtil.timeCha(date);
        viewHolder.timeTV.setText(s);
        viewHolder.titleTV.setText(sys.getSubject());
        viewHolder.contentTV.setText(sys.getBody());
        if (sys.getRead() == 0) {
            viewHolder.docIV.setVisibility(View.VISIBLE);
        } else {
            viewHolder.docIV.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    /**
     * String to date date.
     *
     * @param string the string
     * @return the date
     */
    private Date StringToDate(String string) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(string);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    /**
     * The type Holder view.
     */
    final class HolderView {
        /**
         * The Time tv.
         */
        public TextView timeTV, /**
         * The Title tv.
         */
        titleTV, /**
         * The Content tv.
         */
        contentTV;
        /**
         * The Doc iv.
         */
        public ImageView docIV;
    }


}
