package com.wmcd.myb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 可挪动,可点击的textview
 * Created by 邓志宏 on 2017/2/28.
 */
public class MyTextView extends TextView {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The My boolean.
     */
    private boolean myBoolean;
    /**
     * The My text view interface.
     */
    private MyTextViewInterface myTextViewInterface;
    /**
     * The Startx.down事件发生时，手指相对于view左上角x轴的距离
     */
    private float startx;
    /**
     * The Starty.down事件发生时，手指相对于view左上角y轴的距离
     */
    private float starty;//
    /**
     * The Endx.move事件发生时，手指相对于view左上角x轴的距离
     */
    private float endx; //
    /**
     * The Endy. move事件发生时，手指相对于view左上角y轴的距离
     */
    private float endy; //
    /**
     * The Left.DragTV左边缘相对于父控件的距离
     */
    private int left; //
    /**
     * The Top.
     */
    private int top; //
    /**
     * The Right.DragTV上边缘相对于父控件的距离
     */
    private int right; //
    /**
     * The Bottom.DragTV底边缘相对于父控件的距离
     */
    private int bottom; //
    /**
     * The Hor.触摸情况下，手指在x轴方向移动的距离
     */
    private int hor; //
    /**
     * The Ver.触摸情况下，手指在y轴方向移动的距离
     */
    private int ver; //

    /**
     * Set my boolean.
     *
     * @param b the b
     */
    public void setMyBoolean(boolean b){
        myBoolean = b;
    }

    /**
     * Sets click.
     *
     * @param myTextViewInterface the my text view interface
     */
    public void setonClick(MyTextViewInterface myTextViewInterface) {
        this.myTextViewInterface = myTextViewInterface;
    }

    /**
     * Instantiates a new My text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Instantiates a new My text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new My text view.
     *
     * @param context the context
     */
    public MyTextView(Context context) {
        this(context, null);
        this.context = context;
    }

    /**
     * The Start time.
     */
    long startTime = 0;

    /**
     * On touch event boolean.
     *
     * @param event the event
     * @return the boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (myBoolean) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startTime = System.currentTimeMillis();
                    // 手指刚触摸到屏幕的那一刻，手指相对于View左上角水平和竖直方向的距离:startX startY
                    startx = event.getX();
                    starty = event.getY();
                    myTextViewInterface.startTouchTextView();
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 手指停留在屏幕或移动时，手指相对与View左上角水平和竖直方向的距离:endX endY
                    endx = event.getX();
                    endy = event.getY();
                    // 获取此时刻 View的位置。
                    left = getLeft();
                    top = getTop();
                    right = getRight();
                    bottom = getBottom();
                    // 手指移动的水平距离
                    hor = (int) (endx - startx);
                    // 手指移动的竖直距离
                    ver = (int) (endy - starty);
                    // 当手指在水平或竖直方向上发生移动时，重新设置View的位置（layout方法）
                    if (hor != 0 || ver != 0)
                        layout(left + hor, top + ver, right + hor, bottom + ver);
                    break;
                case MotionEvent.ACTION_UP:
                    long stopTime = System.currentTimeMillis();
//                    if (startx - endx == 0 || starty - endy == 0 || Math.abs(startTime - stopTime) <= 50)
//                        myTextViewInterface.onClickTextView();
//                    else
                        myTextViewInterface.onTouchTextView();
                    break;
                default:
                    break;
            }
            return true;
        } else
            return false;
    }

    /**
     * The interface My text view interface.
     */
    public interface MyTextViewInterface {
        /**
         * On click text view.
         */
        void onClickTextView();

        /**
         * On touch text view.
         */
        void onTouchTextView();

        /**
         * Start touch text view.
         */
        void startTouchTextView();
    }
}
