package com.wmcd.myb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.model.DocModel;

import java.util.List;

/**
 * Created by zlf on 2017/3/25 0023.
 */
public class DocAdapter extends BaseAdapter {
    /**
     * The Doc been list.
     */
    private List<DocModel.ListDocBean> docBeenList;
    /**
     * The Inflater.
     */
    private LayoutInflater inflater;
    /**
     * The Context.
     */
    private Context context;

    /**
     * Instantiates a new Doc adapter.
     *
     * @param mdocBeenList the mdoc been list
     * @param mcontext     the mcontext
     */
    public DocAdapter(List<DocModel.ListDocBean> mdocBeenList, Context mcontext) {
        this.docBeenList = mdocBeenList;
        inflater = LayoutInflater.from(mcontext);
        context = mcontext;

    }

    /**
     * Refresh.
     *
     * @param mdocBeenList the mdoc been list
     */
    public void refresh(List<DocModel.ListDocBean> mdocBeenList) {
        this.docBeenList = mdocBeenList;
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
        return docBeenList != null ? docBeenList.size() : 0;
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
            view = inflater.inflate(R.layout.item_doc, null);
            viewHolder.infoTV = (TextView) view.findViewById(R.id.info_tv);
            viewHolder.docTV = (TextView) view.findViewById(R.id.doc_tv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (HolderView) view.getTag();
        }
        final DocModel.ListDocBean docBean = docBeenList.get(position);

        viewHolder.docTV.setText(docBean.getName());

        return view;
    }

    /**
     * The type Holder view.
     */
    final class HolderView {
        /**
         * The Info tv.
         */
        public TextView infoTV;
        /**
         * The Doc tv.
         */
        public TextView docTV;
    }


}
