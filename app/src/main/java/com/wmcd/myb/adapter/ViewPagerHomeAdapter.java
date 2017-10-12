package com.wmcd.myb.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wmcd.myb.view.AutoViewPager;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
class ViewPagerHomeAdapterextends extends PagerAdapter {
    /**
     * The Imgs.
     */
    private List<ImageView> imgs;
    /**
     * The Image loader.
     */
    private ImageLoader imageLoader;

    /**
     * Sets image loader.
     *
     * @param imageLoader the image loader
     */
    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    /**
     * Instantiates a new View pager home adapterextends.
     *
     * @param imgs the imgs
     */
    public ViewPagerHomeAdapterextends(List<ImageView> imgs){
        this.imgs = imgs;
    }

    /**
     * Destroy item.
     *
     * @param container the container
     * @param position  the position
     * @param object    the object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * Instantiate item object.
     *
     * @param container the container
     * @param position  the position
     * @return the object
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imgs.get(position));
        if (imageLoader != null){
            imageLoader.loadImg(imgs.get(position),position);
        }
        return imgs.get(position);
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    @Override
    public int getCount() {
        return imgs == null ? 0 : imgs.size();
    }

    /**
     * Is view from object boolean.
     *
     * @param view   the view
     * @param object the object
     * @return the boolean
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * The interface On item click listener.
     */
    public interface OnItemClickListener {
        /**
         * On item click.
         *
         * @param view     the view
         * @param position the position
         */
        void onItemClick(View view, int position);
    }

    /**
     * The interface Image loader.
     */
    public interface ImageLoader {
        /**
         * Load img.
         *
         * @param tager    the tager
         * @param position the position
         */
        void loadImg(ImageView tager,int position);
    }
}