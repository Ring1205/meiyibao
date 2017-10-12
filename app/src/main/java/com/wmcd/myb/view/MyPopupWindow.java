package com.wmcd.myb.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wmcd.myb.R;

import butterknife.ButterKnife;

/**
 * Created by 邓志宏 on 2017/3/1.
 */
public class MyPopupWindow extends PopupWindow implements View.OnClickListener {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Text view 1.
     */
    private TextView textView1, /**
     * The Text view 2.
     */
    textView2, /**
     * The Text view 3.
     */
    textView3, /**
     * The Text viewdis.
     */
    textViewdis;
    /**
     * The Ll 01.
     */
    private LinearLayout ll_01, /**
     * The Ll 02.
     */
    ll_02;
    /**
     * The View.
     */
    private View view;
    /**
     * The Onclicklinener.
     */
    private SetOnClickLinener onclicklinener;
    /**
     * The Lp.
     */
    private WindowManager.LayoutParams lp;

    /**
     * Setonclick.
     *
     * @param onclicklinener the onclicklinener
     */
    public void setonclick(SetOnClickLinener onclicklinener){
        this.onclicklinener = onclicklinener;
    }

    /**
     * Instantiates a new My popup window.
     *
     * @param context the context
     * @param str1    the str 1
     * @param str2    the str 2
     * @param str3    the str 3
     */
    public MyPopupWindow(Context context, String str1, String str2, String str3){
        this.view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
        this.context = context;
        textViewdis = new TextView(context);
        textView1 = new TextView(context);
        textView2 = new TextView(context);
        textView3 = new TextView(context);
        ll_01 = (LinearLayout) view.findViewById(R.id.ll_01);
        ll_02 = (LinearLayout) view.findViewById(R.id.ll_02);
        if (str3 == null)
            ll_01.setVisibility(View.GONE);
        else if (str2 == null)
            ll_02.setVisibility(View.GONE);
        textViewdis = (TextView) view.findViewById(R.id.tv_dismiss);
        textView1 = (TextView) view.findViewById(R.id.tv_01);
        textView2 = (TextView) view.findViewById(R.id.tv_02);
        textView3 = (TextView) view.findViewById(R.id.tv_03);
        textView1.setText(str1);
        textView2.setText(str2);
        textView3.setText(str3);
        textViewdis.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.PopupWindowStyle);

        this.showAtLocation(view, Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
        final Activity activity = (Activity) context;

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.ll_all).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        //设置背景颜色渐变
        lp = activity.getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(1f).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float n = (float) animation.getAnimatedValue();
                lp.alpha = 1-n/3;
                activity.getWindow().setAttributes(lp);
            }
        });
        animator.start();
        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ValueAnimator animator = ValueAnimator.ofFloat(1f).setDuration(1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float n = (float) animation.getAnimatedValue();
                        if (n != 0){
                            if (n > 1){
                                lp.alpha = n/10;
                                activity.getWindow().setAttributes(lp);
                            } else if (n == 1){
                                lp.alpha = 1;
                                activity.getWindow().setAttributes(lp);
                            } else {
                                lp.alpha = (1-lp.alpha)/30+lp.alpha;
                                if (lp.alpha >= 0.9){
                                    lp.alpha = (1-lp.alpha)/10+lp.alpha;
                                }
                                activity.getWindow().setAttributes(lp);
                            }
                        }
                    }
                });
                animator.start();
            }
        });
    }

    /**
     * Instantiates a new My popup window.
     *
     * @param context the context
     * @param str1    the str 1
     * @param str2    the str 2
     */
    public MyPopupWindow(Context context, String str1, String str2){
        this(context,str1,str2,null);
    }

    /**
     * Instantiates a new My popup window.
     *
     * @param context the context
     * @param str1    the str 1
     */
    public MyPopupWindow(Context context, String str1){
        this(context,str1,null,null);
    }

    /**
     * On click.
     *
     * @param v the v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_dismiss:
                dismiss();
                break;
            case R.id.tv_01:
                onclicklinener.OnClick01();
                break;
            case R.id.tv_02:
                onclicklinener.OnClick02();
                break;
            case R.id.tv_03:
                onclicklinener.OnClick03();
                break;
        }
    }

    /**
     * The interface Set on click linener.
     */
    public interface SetOnClickLinener{
        /**
         * On click 01.
         */
        void OnClick01();

        /**
         * On click 02.
         */
        void OnClick02();

        /**
         * On click 03.
         */
        void OnClick03();
    }
}
