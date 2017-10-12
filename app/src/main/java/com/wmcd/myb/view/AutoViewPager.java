package com.wmcd.myb.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wmcd.myb.R;
import com.wmcd.myb.util.UiUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 无限循环轮播图
 * Created by 邓志宏 on 2016/11/21.
 */
public class AutoViewPager extends RelativeLayout {
    /**
     * The constant LEFT.
     */
    public static final int LEFT = 1;
    /**
     * The constant RIGHT.
     */
    public static final int RIGHT = 2;
    /**
     * The constant CENTER.
     */
    public static final int CENTER = 3;
    /**
     * The Image urls.
     */
    private String[] imageUrls;
    /**
     * 切换延时（ms）
     */
    private int switchTime = 3000;
    /**
     * The Dot mode.
     */
    private int dot_mode;
    /**
     * The Listener.
     */
    private OnItemClickListener listener;

    /**
     * Sets open switch.
     *
     * @param openSwitch the open switch
     */
    public void setOpenSwitch(boolean openSwitch) {
        this.openSwitch = openSwitch;
    }

    /**
     * The Open switch.
     */
    private boolean openSwitch = true;
    /**
     * The Image views list.
     */
//轮播图片
    private ImageView[] imageViewsList;
    /**
     * The Dot views list.
     */
//圆点
    private ImageView[] dotViewsList;
    /**
     * The View pager.
     */
    private ViewPager viewPager;
    /**
     * The M swipe refresh layout.
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * 当前显示
     */
    private int currentItem = 1;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Dot layout.
     */
    private LinearLayout dotLayout;
    /**
     * The Adapter.
     */
    private MyPagerAdapter adapter;
    /**
     * The Timer.
     */
    private Timer timer;
    /**
     * The Timer task.
     */
    private TimerTask timerTask;
    /**
     * The Handler.
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            nextPage();
        }
    };

    /**
     * Sets swipe refresh layout.
     *
     * @param swipeRefreshLayout the swipe refresh layout
     */
    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        mSwipeRefreshLayout = swipeRefreshLayout;
    }

    /**
     * Get view pager view pager.
     *
     * @return the view pager
     */
    public ViewPager getViewPager(){
        return this.viewPager;
    }

    /**
     * The Dot click listener.
     */
    private OnClickListener dotClickListener;

    /**
     * Add on page change listener.
     *
     * @param lis the lis
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener lis) {
        viewPager.addOnPageChangeListener(lis);
    }

    /**
     * Gets current item.
     *
     * @return the current item
     */
    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    /**
     * Sets current item.
     *
     * @param position the position
     */
    public void setCurrentItem(int position) {
        viewPager.setCurrentItem(position);
    }

    /**
     * Sets on item click listener.
     *
     * @param listener the listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * The M image loader.
     */
    private ImageLoader mImageLoader;

    /**
     * Sets image loader.
     *
     * @param imageLoader the image loader
     */
    public void setImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    /**
     * Get image urls string [ ].
     *
     * @return the string [ ]
     */
    public String[] getImageUrls() {
        return imageUrls;
    }

    /**
     * Sets image urls.
     *
     * @param imageUrls the image urls
     */
    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
        changePages();
    }

    /**
     * Gets switch time.
     *
     * @return the switch time
     */
    public long getSwitchTime() {
        return switchTime;
    }

    /**
     * Sets switch time.
     *
     * @param switchTime the switch time
     */
    public void setSwitchTime(int switchTime) {
        this.switchTime = switchTime;
    }

    /**
     * Instantiates a new Auto view pager.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public AutoViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    /**
     * Instantiates a new Auto view pager.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public AutoViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new Auto view pager.
     *
     * @param context the context
     */
    public AutoViewPager(Context context) {
        this(context, null);
    }

    /**
     * Gets top fading edge strength.
     *
     * @return the top fading edge strength
     */
    @Override
    protected float getTopFadingEdgeStrength() {
        return super.getTopFadingEdgeStrength();
    }

    /**
     * 关闭轮播
     */
    public void closeSwitch() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    /**
     * 开启轮播
     */
    public void startSwitch() {
        if (timer != null)
            timer.cancel();
        if (timerTask != null)
            timerTask.cancel();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, switchTime, switchTime);
    }

    /**
     * 初始化
     *
     * @param attrs the attrs
     */
    private void init(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoViewPager);
        switchTime = a.getInteger(R.styleable.AutoViewPager_switch_time, 3000);
        openSwitch = a.getBoolean(R.styleable.AutoViewPager_open_switch, true);
        dot_mode = a.getInteger(R.styleable.AutoViewPager_dot_mode, CENTER);
        initView();
        initListener();
        changePages();
    }


    /**
     * Sets dot mode.
     *
     * @param mode the mode
     */
    public void setDotMode(int mode) {
        dot_mode = mode;
    }

    /**
     * Init view.
     */
    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.auto_viewpager, this, true);
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        switch (dot_mode) {
            case LEFT:
                dotLayout.setGravity(Gravity.LEFT);
                break;
            case RIGHT:
                dotLayout.setGravity(Gravity.RIGHT);
                break;
            case CENTER:
            default:
                dotLayout.setGravity(Gravity.CENTER);
                break;
        }
        dotLayout.removeAllViews();
        viewPager = (ViewPager) findViewById(R.id.auto_viewpager);
        /**
         *  解决跟SwipeRefreshLayout下拉刷新冲突(可能ListView下拉刷新也会有问题，直接换成View应该就可以了)
         *  同时自己手势时取消自动滑动，离开开启自动滑动
         *  如果openSwitch为true的话
         */
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Logger.e("event---" + event.getAction());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        //Logger.e("auto-down");
                        // 关闭翻页
                        if (openSwitch) closeSwitch();
                        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                        //Logger.e("auto-up");
                        // 开始翻页
                        if (openSwitch) startSwitch();
                        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        viewPager.setFocusable(true);
        //设置缓存页面数量
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true, new PagerTransformer());
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        //设置pager间的间距
//        viewPager.setPageMargin(UiUtils.dip2px(context,5));
//        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        params.setMargins(UiUtils.dip2px(context,15),UiUtils.dip2px(context,0),UiUtils.dip2px(context,15),UiUtils.dip2px(context,0));
//        viewPager.setLayoutParams(params);
//        ((RelativeLayout)viewPager.getParent()).setClipChildren(false);
//        viewPager.setClipChildren(false);
//        viewPager.setLayoutParams(params);
        //无限循化
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                if (dotViewsList == null || imageViewsList == null)
                    return;
                for (int i = 0; i < dotViewsList.length; i++) {
                    if ((position == imageViewsList.length - 1 && i == 0) || i == position - 1) {
                        dotViewsList[i].setBackgroundResource(R.drawable.dot_select);
                    } else {
                        dotViewsList[i].setBackgroundResource(R.drawable.dot_no_select);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //0 idle(空闲，挂空挡) 拖动/滑动结束
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (currentItem == viewPager.getAdapter().getCount() - 1) {
                        viewPager.setCurrentItem(1, false);
                    } else if (currentItem == 0) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 2, false);
                    }
                }
            }
        });
    }

    /**
     * 设置pager间的间距和距屏幕的间距
     *
     * @param left   the left
     * @param top    the top
     * @param right  the right
     * @param bottom the bottom
     * @param margin the margin
     */
    public void SetPageSpacing(int left, int top, int right, int bottom, int margin){
        viewPager.setPageMargin(UiUtils.dip2px(context,margin));
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(UiUtils.dip2px(context,left),UiUtils.dip2px(context,top),UiUtils.dip2px(context,right),UiUtils.dip2px(context,bottom));
        viewPager.setLayoutParams(params);
        ((RelativeLayout)viewPager.getParent()).setClipChildren(false);
        viewPager.setClipChildren(false);
        viewPager.setLayoutParams(params);
    }

    /**
     * Init listener.
     */
//圆点点击切换pager
    private void initListener() {
        dotClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem((Integer) v.getTag());
            }
        };
    }

    /**
     * 下一页
     */
    private void nextPage() {
        viewPager.setCurrentItem(currentItem + 1, true);
    }

    /**
     * images and dotView
     */
    private void changePages() {
        if (imageUrls == null || imageUrls.length == 0) {
            imageViewsList = null;
            dotViewsList = null;
            viewPager.removeAllViews();
            dotLayout.removeAllViews();
            closeSwitch();
            adapter.notifyDataSetChanged();
            return;
        }
        int length = imageUrls.length;
        if (imageViewsList == null) {
            if (length > 1) {
                imageViewsList = new ImageView[length + 2];
                ImageView first = new ImageView(context);
                first.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageView last = new ImageView(context);
                last.setScaleType(ImageView.ScaleType.FIT_XY);
                imageViewsList[length + 1] = last;
                imageViewsList[0] = first;
            } else
                imageViewsList = new ImageView[length];
        }
        if (dotViewsList == null)
            dotViewsList = new ImageView[length];
        dotLayout.removeAllViews();
        // 热点个数与图片特殊相等
        // 3 1 2 3 1 无限循环轮播
        for (int i = 0; i < length; i++) {
            ImageView view = new ImageView(context);
            view.setMaxWidth(getWidth()-UiUtils.dip2px(context,14));
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            if (imageViewsList.length > 1) {
                imageViewsList[i+1] = view;
            }else {
                imageViewsList[i] = view;
            }
            ImageView dotView = new ImageView(context);
            if (imageViewsList.length > 1)
                dotView.setTag(i + 1);
            else
                dotView.setTag(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotView.setOnClickListener(dotClickListener);
            dotLayout.addView(dotView, params);
            dotViewsList[i] = dotView;
        }
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(currentItem);
        if (dotViewsList.length > 1 && dotViewsList.length <= currentItem)
            dotViewsList[currentItem-1].setBackgroundResource(R.drawable.dot_select);
        else
            dotViewsList[0].setBackgroundResource(R.drawable.dot_select);
        if (openSwitch)
            startSwitch();
    }

    /**
     * 整个Transformer对viewpager的页面进行了缩放和Y轴旋转
     * 代码非常简短，简单的介绍下，可以看到postion主要分为
     * [-Infinity,-1)
     * (1,+Infinity]
     * [-1,1]
     * 这三个区间，对于前两个，拿我们的页面上目前显示的3个Page来说，前两个分别对应左右两个露出一点的Page，那么对于alpha值，只需要设置为最小值即可。
     * 对于[-1,1]，这个就需要详细分析了，我们这里拿：第一页->第二页这个过程来说，主要看position的变化
     * 第1页->第2页
     * 页1的postion变化为：从0到-1
     * 页2的postion变化为：从1到0
     * 第一页到第二页，实际上就是左滑，第一页到左边，第二页成为currentItem到达中间，那么对应alpha的变化应该是：
     * 页1到左边，对应alpha应该是：1到minAlpha
     * 页2到中间，成为currentItem，对应alpha应该是：minAlpha到1
     * Created by yqr on 2016/11/9 0009.
     */
    class PagerTransformer implements ViewPager.PageTransformer {

        /**
         * The constant minScale.
         */
        public static final float minScale = 0.9f;
        /**
         * The constant DEFAULT_MAX_ROTATE.
         */
        private static final float DEFAULT_MAX_ROTATE = 15f;
        /**
         * The M max rotate.
         */
        private float mMaxRotate = DEFAULT_MAX_ROTATE;
        /**
         * The constant DEFAULT_CENTER.
         */
        public static final float DEFAULT_CENTER = 0.4f;

        /**
         * 每个状态的view该显示多大是根据position的值来设置的。
         * 本例中值关心3张图片的大小，就只有3个状态。position小于-1 ， -1到1 大于1
         * 等于-1 就是屏幕上左边的图片 ， 等于1 是屏幕右边的图 等于0是中间的图。
         *
         * @param page     the page
         * @param position -- 第一个view的position初始为0 ，向左滑第一个view的position就慢慢减少成为负数。                 第二个view的position初始为1，向左滑慢慢变成0.变成0时就是这个view在最中间的时候。                 整个过程，每个view的position都是在变的、
         */
        @Override
        public void transformPage(View page, float position) {
            page.setPivotY(page.getHeight() / 2);//旋转轴

            if (position < -1) {
//            view.setAlpha(mMinAlpha);
                page.setScaleY(minScale);
                page.setScaleX(minScale);
                page.setRotationY(-1 * mMaxRotate);
                page.setPivotX(page.getWidth());
            } else if (position <= 1) { // [-1,1]
                page.setRotationY(position * mMaxRotate);
                if (position < 0) //[0，-1]
                {
                    float factor = minScale + (1 - minScale) * (1 + position);
                    page.setScaleY(factor);
                    page.setScaleX(factor);
//                page.setPivotX(page.getWidth() * (DEFAULT_CENTER + DEFAULT_CENTER * (-position)));
                    page.setPivotX(page.getWidth());

                } else//[1，0]
                {
                    float factor = minScale + (1 - minScale) * (1 - position);
                    page.setScaleY(factor);
                    page.setScaleX(factor);
//                page.setPivotX(page.getWidth() * DEFAULT_CENTER * (1 - position));
                    page.setPivotX(0);
                }
            } else { // (1,+Infinity]
                page.setScaleY(minScale);
                page.setScaleX(minScale);
                page.setRotationY(1 * mMaxRotate);
                page.setPivotX(0);
            }
        }

    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {
        /**
         * Destroy item.
         *
         * @param container the container
         * @param position  the position
         * @param object    the object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewsList[position]);
        }

        /**
         * Instantiate item object.
         *
         * @param container the container
         * @param position  the position
         * @return the object
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = imageViewsList[position];
            String url;
            if (position == 0) {
                url = imageUrls[imageUrls.length - 1];
            } else if (position == imageViewsList.length - 1) {
                url = imageUrls[0];
            } else {
                url = imageUrls[position - 1];
            }
            if (mImageLoader != null)
                mImageLoader.loadImg(context, url, imageView,"");
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(v, position);
                }
            });
            container.addView(imageView);
            return imageView;
        }

        /**
         * Gets count.
         *
         * @return the count
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList != null ? imageViewsList.length : 0;
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
         * @param context      the context
         * @param url          the url
         * @param tager        the tager
         * @param isradiuValue the isradiu value
         */
        void loadImg(Context context, String url, ImageView tager, String isradiuValue);
    }

}
