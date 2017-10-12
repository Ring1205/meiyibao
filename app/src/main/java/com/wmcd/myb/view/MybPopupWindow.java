package com.wmcd.myb.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.PictureEditorActivity;
import com.wmcd.myb.adapter.TextsAdapter;
import com.wmcd.myb.model.FontsModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.InputMethodUtils;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 邓志宏 on 2017/3/3.
 */
public class MybPopupWindow extends PopupWindow implements View.OnClickListener, TextView.OnEditorActionListener {
    /**
     * The Ll set color.
     */
    LinearLayout ll_set_color;

    /**
     * The Context.
     */
    private Context context;
    /**
     * The View.
     */
    private View view;
    /**
     * The Lp.
     */
    private WindowManager.LayoutParams lp;
    /**
     * The Iv editor 01.
     */
    private ImageView iv_editor_01, /**
     * The Iv editor 02.
     */
    iv_editor_02, /**
     * The Iv editor 03.
     */
    iv_editor_03, /**
     * The Iv editor 04.
     */
    iv_editor_04;
    /**
     * The Tv tab name.
     */
    private EditText tv_tab_name;
    /**
     * The Handler.
     */
    private Handler handler = new Handler();
    /**
     * The On edit text.
     */
    private OnEditText onEditText;
    /**
     * The String.
     */
    private String string = "文本丢失";
    /**
     * The Str font.
     */
    private String strFont = "微软雅黑";
    /**
     * The Color.
     */
    private String color = "#000000";
    /**
     * The B boolean.
     */
    private boolean bBoolean = true;
    /**
     * The Font list.
     */
    private List<FontsModel.ListBean> fontList;
    /**
     * The Texts adapter.
     */
    private TextsAdapter textsAdapter;
    /**
     * The Recyclerview texts.
     */
    private RecyclerView recyclerview_texts;
    /**
     * The Color 01.
     */
    private ImageView color_01,color_02,color_03,color_04,color_05,color_06,color_07,color_08,color_09,color_10,color_11,color_12,color_13,color_14,color_15,color_16,color_17,color_18,color_19, color_20,color_21,color_22,color_23,color_24,color_25,color_26,color_27,color_28,color_29,color_30,color_31, color_32, color_33;
    private String[] strings = {"#000000", "#ffffff", "#cccccc", "#999999", "#666666", "#333333", "#010101"
            , "#9a0000", "#9A3400", "#9A6604", "#356400", "#006699", "#003366", "#FE0000", "#FF6634", "#FFFF01"
            , "#65FE03", "#0099FF", "#3565FE", "#6600CC", "#E30145", "#FFCB98", "#33CB98", "#66CCFF", "#6665FF"
            , "#D666FE", "#FFCBCB", "#FFFECC", "#99FFCD", "#CCFFFE", "#CCCCFF", "#CB99FE", "#FE65CD"};

    /**
     * Sets on edit text.
     *
     * @param onEditText the on edit text
     */
    public void setOnEditText(OnEditText onEditText) {
        this.onEditText = onEditText;
    }

    /**
     * Init data.
     */
//获取字体列表数据
    private void initData() {
        ServeManager.getFonts(context).enqueue(new Callback<FontsModel>() {
            @Override
            public void onResponse(Call<FontsModel> call, Response<FontsModel> response) {
                if (response.body() != null)
                    Log.e(PictureEditorActivity.class.getName(), "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    fontList = response.body().getList();
                    textsAdapter.setFonts(fontList);
                    textsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FontsModel> call, Throwable t) {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Instantiates a new Myb popup window.
     * @param context the context
     * @param string  the string
     * @param color   the color
     * @param strFont the str font
     */
    public MybPopupWindow(Context context, final String string, String color, String strFont) {
        this.strFont = strFont;
        this.context = context;
        this.string = string;
        this.color = color;
        initView();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_tab_name.requestFocus();
                tv_tab_name.setText(string);
                tv_tab_name.setSelection(string.length());
//                tv_tab_name.setOnEditorActionListener(MybPopupWindow.this);
            }
        }, 500);
        recyclerview_texts.setVisibility(View.GONE);
        ll_set_color.setVisibility(View.GONE);

        textsAdapter = new TextsAdapter(context);
        recyclerview_texts.setLayoutManager(new LinearLayoutManager(context));
        recyclerview_texts.setAdapter(textsAdapter);
        if (bBoolean) {
            initData();
            bBoolean = false;
        }
    }

    /**
     * Init view.
     */
    private void initView() {
        this.view = LayoutInflater.from(context).inflate(R.layout.popupwindow_edit, null);
        ll_set_color = (LinearLayout) view.findViewById(R.id.ll_set_color);
        recyclerview_texts = (RecyclerView) view.findViewById(R.id.recyclerview_texts);
        iv_editor_01 = (ImageView) view.findViewById(R.id.iv_editor_01);
        iv_editor_02 = (ImageView) view.findViewById(R.id.iv_editor_02);
        iv_editor_03 = (ImageView) view.findViewById(R.id.iv_editor_03);
        iv_editor_04 = (ImageView) view.findViewById(R.id.iv_editor_04);
        color_01 = (ImageView) view.findViewById(R.id.color_01);
        color_02 = (ImageView) view.findViewById(R.id.color_02);
        color_03 = (ImageView) view.findViewById(R.id.color_03);
        color_04 = (ImageView) view.findViewById(R.id.color_04);
        color_05 = (ImageView) view.findViewById(R.id.color_05);
        color_06 = (ImageView) view.findViewById(R.id.color_06);
        color_07 = (ImageView) view.findViewById(R.id.color_07);
        color_08 = (ImageView) view.findViewById(R.id.color_08);
        color_09 = (ImageView) view.findViewById(R.id.color_09);
        color_10 = (ImageView) view.findViewById(R.id.color_10);
        color_11 = (ImageView) view.findViewById(R.id.color_11);
        color_12 = (ImageView) view.findViewById(R.id.color_12);
        color_13 = (ImageView) view.findViewById(R.id.color_13);
        color_14 = (ImageView) view.findViewById(R.id.color_14);
        color_15 = (ImageView) view.findViewById(R.id.color_15);
        color_16 = (ImageView) view.findViewById(R.id.color_16);
        color_17 = (ImageView) view.findViewById(R.id.color_17);
        color_18 = (ImageView) view.findViewById(R.id.color_18);
        color_19 = (ImageView) view.findViewById(R.id.color_19);
        color_20 = (ImageView) view.findViewById(R.id.color_20);
        color_21 = (ImageView) view.findViewById(R.id.color_21);
        color_22 = (ImageView) view.findViewById(R.id.color_22);
        color_23 = (ImageView) view.findViewById(R.id.color_23);
        color_24 = (ImageView) view.findViewById(R.id.color_24);
        color_25 = (ImageView) view.findViewById(R.id.color_25);
        color_26 = (ImageView) view.findViewById(R.id.color_26);
        color_27 = (ImageView) view.findViewById(R.id.color_27);
        color_28 = (ImageView) view.findViewById(R.id.color_28);
        color_29 = (ImageView) view.findViewById(R.id.color_29);
        color_30 = (ImageView) view.findViewById(R.id.color_30);
        color_31 = (ImageView) view.findViewById(R.id.color_31);
        color_32 = (ImageView) view.findViewById(R.id.color_32);
        color_33 = (ImageView) view.findViewById(R.id.color_33);
        tv_tab_name = (EditText) view.findViewById(R.id.tv_tab_name);
        iv_editor_01.setOnClickListener(this);
        iv_editor_02.setOnClickListener(this);
        iv_editor_03.setOnClickListener(this);
        iv_editor_04.setOnClickListener(this);
        color_01.setOnClickListener(this);
        color_02.setOnClickListener(this);
        color_03.setOnClickListener(this);
        color_04.setOnClickListener(this);
        color_05.setOnClickListener(this);
        color_06.setOnClickListener(this);
        color_07.setOnClickListener(this);
        color_08.setOnClickListener(this);
        color_09.setOnClickListener(this);
        color_10.setOnClickListener(this);
        color_11.setOnClickListener(this);
        color_12.setOnClickListener(this);
        color_13.setOnClickListener(this);
        color_14.setOnClickListener(this);
        color_15.setOnClickListener(this);
        color_16.setOnClickListener(this);
        color_17.setOnClickListener(this);
        color_18.setOnClickListener(this);
        color_19.setOnClickListener(this);
        color_20.setOnClickListener(this);
        color_21.setOnClickListener(this);
        color_22.setOnClickListener(this);
        color_23.setOnClickListener(this);
        color_24.setOnClickListener(this);
        color_25.setOnClickListener(this);
        color_26.setOnClickListener(this);
        color_27.setOnClickListener(this);
        color_28.setOnClickListener(this);
        color_29.setOnClickListener(this);
        color_30.setOnClickListener(this);
        color_31.setOnClickListener(this);
        color_32.setOnClickListener(this);
        color_33.setOnClickListener(this);
    }

    /**
     * Show popupwindow.
     */
    public void showPopupwindow() {
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

        this.showAtLocation(view, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        final Activity activity = (Activity) context;

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.ll_bottom).getTop();

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
        ValueAnimator animator = ValueAnimator.ofFloat(1f).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float n = (float) animation.getAnimatedValue();
                lp.alpha = 1 - n / 3;
                activity.getWindow().setAttributes(lp);
            }
        });
        animator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodUtils.showSoftInput(tv_tab_name);
            }
        }, 500);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ValueAnimator animator = ValueAnimator.ofFloat(1f).setDuration(500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float n = (float) animation.getAnimatedValue();
                        if (n != 0) {
                            if (n > 1) {
                                lp.alpha = n / 10;
                                activity.getWindow().setAttributes(lp);
                            } else if (n == 1) {
                                lp.alpha = 1;
                                activity.getWindow().setAttributes(lp);
                            } else {
                                lp.alpha = (1 - lp.alpha) / 30 + lp.alpha;
                                if (lp.alpha >= 0.9) {
                                    lp.alpha = (1 - lp.alpha) / 10 + lp.alpha;
                                }
                                activity.getWindow().setAttributes(lp);
                            }
                        }
                    }
                });
                animator.start();
            }
        });
        tv_tab_name.setText(string);
        try {
            tv_tab_name.setTextColor(Color.parseColor(color));
        }catch (Exception e){
            Toast.makeText(context,"文字颜色有误",Toast.LENGTH_SHORT).show();
        }
        if(strFont != null){
        File file = new File(Environment.getExternalStorageDirectory() + "/fonts/" + strFont + ".ttf");
        if (file.exists()) {
            try {
                Typeface tf = Typeface.createFromFile(file);
                tv_tab_name.setTypeface(tf);//设置字体
            } catch (RuntimeException e) {
                Log.e("PictureEditorActivity", e.getMessage());
            }
        }
        tv_tab_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerview_texts.setVisibility(View.GONE);
                ll_set_color.setVisibility(View.GONE);
            }
        });}
    }

    /**
     * On click.
     *
     * @param view the view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_editor_01:
                int i = ll_set_color.getVisibility();
                if (i == 0) {
                    InputMethodUtils.showSoftInput(tv_tab_name);
                    iv_editor_02.setSelected(false);
                    iv_editor_03.setSelected(false);
                    iv_editor_04.setSelected(false);
                    recyclerview_texts.setVisibility(View.GONE);
                    ll_set_color.setVisibility(View.GONE);
                } else {
                    ll_set_color.setVisibility(View.VISIBLE);
                    InputMethodUtils.hideSoftInput(tv_tab_name);
                }
                break;
            case R.id.iv_editor_02:
                InputMethodUtils.hideSoftInput(tv_tab_name);
                ll_set_color.setVisibility(View.GONE);
                iv_editor_02.setSelected(true);
                iv_editor_03.setSelected(false);
                iv_editor_04.setSelected(false);

                recyclerview_texts.setVisibility(View.VISIBLE);
                textsAdapter.setFonts(fontList);
                textsAdapter.notifyDataSetChanged();
                textsAdapter.getMemberItemOnclick(new TextsAdapter.MemberItemOnclick() {
                    @Override
                    public void setItemOnclik(String string) {
                        strFont = string;
                        File file = new File(Environment.getExternalStorageDirectory() + "/fonts/" + strFont + ".ttf");
                        if (file.exists()) {
                            try {
                                Typeface tf = Typeface.createFromFile(file);
                                tv_tab_name.setTypeface(tf);//设置字体
                            } catch (RuntimeException e) {
                                Log.e("PictureEditorActivity", e.getMessage());
                            }
                        }
                        onEditText.setTextStyle(strFont);
                    }
                });
                break;
            case R.id.iv_editor_03:
                InputMethodUtils.hideSoftInput(tv_tab_name);
                iv_editor_02.setSelected(false);
                iv_editor_03.setSelected(true);
                iv_editor_04.setSelected(false);
                recyclerview_texts.setVisibility(View.GONE);
                ll_set_color.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_editor_04:
                string = tv_tab_name.getText().toString();
                tv_tab_name.setText(string);
                onEditText.getEditText(string);
                dismiss();
                break;
            case R.id.color_01:
                setBack();
                color_01.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(color));
                onEditText.setTextColor(color);
                break;
            case R.id.color_02:
                setBack();
                color_02.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[1]));
                onEditText.setTextColor(strings[1]);
                break;
            case R.id.color_03:
                setBack();
                color_03.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[2]));
                onEditText.setTextColor(strings[2]);
                break;
            case R.id.color_04:
                setBack();
                color_04.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[3]));
                onEditText.setTextColor(strings[3]);
                break;
            case R.id.color_05:
                setBack();
                color_05.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[4]));
                onEditText.setTextColor(strings[4]);
                break;
            case R.id.color_06:
                setBack();
                color_06.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[5]));
                onEditText.setTextColor(strings[5]);
                break;
            case R.id.color_07:
                setBack();
                color_07.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[6]));
                onEditText.setTextColor(strings[6]);
                break;
            case R.id.color_08:
                setBack();
                color_08.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[7]));
                onEditText.setTextColor(strings[7]);
                break;
            case R.id.color_09:
                setBack();
                color_09.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[8]));
                onEditText.setTextColor(strings[8]);
                break;
            case R.id.color_10:
                setBack();
                color_10.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[9]));
                onEditText.setTextColor(strings[9]);
                break;
            case R.id.color_11:
                setBack();
                color_11.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[10]));
                onEditText.setTextColor(strings[10]);
                break;
            case R.id.color_12:
                setBack();
                color_12.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[11]));
                onEditText.setTextColor(strings[11]);
                break;
            case R.id.color_13:
                setBack();
                color_13.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[12]));
                onEditText.setTextColor(strings[12]);
                break;
            case R.id.color_14:
                setBack();
                color_14.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[13]));
                onEditText.setTextColor(strings[13]);
                break;
            case R.id.color_15:
                setBack();
                color_15.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[14]));
                onEditText.setTextColor(strings[14]);
                break;
            case R.id.color_16:
                setBack();
                color_16.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[15]));
                onEditText.setTextColor(strings[15]);
                break;
            case R.id.color_17:
                setBack();
                color_17.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[16]));
                onEditText.setTextColor(strings[16]);
                break;
            case R.id.color_18:
                setBack();
                color_18.setSelected(true);
                tv_tab_name.setTextColor(Color.parseColor(strings[17]));
                onEditText.setTextColor(strings[17]);
                break;
            case R.id.color_19:
                setBack();
                color_19.setSelected(true);
                onEditText.setTextColor(strings[18]);
                tv_tab_name.setTextColor(Color.parseColor(strings[18]));
                break;
            case R.id.color_20:
                setBack();
                color_20.setSelected(true);
                onEditText.setTextColor(strings[19]);
                tv_tab_name.setTextColor(Color.parseColor(strings[19]));
                break;
            case R.id.color_21:
                setBack();
                color_21.setSelected(true);
                onEditText.setTextColor(strings[20]);
                tv_tab_name.setTextColor(Color.parseColor(strings[20]));
                break;
            case R.id.color_22:
                setBack();
                color_22.setSelected(true);
                onEditText.setTextColor(strings[21]);
                tv_tab_name.setTextColor(Color.parseColor(strings[21]));
                break;
            case R.id.color_23:
                setBack();
                color_23.setSelected(true);
                onEditText.setTextColor(strings[22]);
                tv_tab_name.setTextColor(Color.parseColor(strings[22]));
                break;
            case R.id.color_24:
                setBack();
                color_24.setSelected(true);
                onEditText.setTextColor(strings[23]);
                tv_tab_name.setTextColor(Color.parseColor(strings[23]));
                break;
            case R.id.color_25:
                setBack();
                color_25.setSelected(true);
                onEditText.setTextColor(strings[24]);
                tv_tab_name.setTextColor(Color.parseColor(strings[24]));
                break;
            case R.id.color_26:
                setBack();
                color_26.setSelected(true);
                onEditText.setTextColor(strings[25]);
                tv_tab_name.setTextColor(Color.parseColor(strings[25]));
                break;
            case R.id.color_27:
                setBack();
                color_27.setSelected(true);
                onEditText.setTextColor(strings[26]);
                tv_tab_name.setTextColor(Color.parseColor(strings[27]));
                break;
            case R.id.color_28:
                setBack();
                color_28.setSelected(true);
                onEditText.setTextColor(strings[27]);
                tv_tab_name.setTextColor(Color.parseColor(strings[17]));
                break;
            case R.id.color_29:
                setBack();
                color_29.setSelected(true);
                onEditText.setTextColor(strings[28]);
                tv_tab_name.setTextColor(Color.parseColor(strings[28]));
                break;
            case R.id.color_30:
                setBack();
                color_30.setSelected(true);
                onEditText.setTextColor(strings[29]);
                tv_tab_name.setTextColor(Color.parseColor(strings[29]));
                break;
            case R.id.color_31:
                setBack();
                color_31.setSelected(true);
                onEditText.setTextColor(strings[30]);
                tv_tab_name.setTextColor(Color.parseColor(strings[30]));
                break;
            case R.id.color_32:
                setBack();
                color_32.setSelected(true);
                onEditText.setTextColor(strings[31]);
                tv_tab_name.setTextColor(Color.parseColor(strings[31]));
                break;
            case R.id.color_33:
                setBack();
                color_33.setSelected(true);
                onEditText.setTextColor(strings[32]);
                tv_tab_name.setTextColor(Color.parseColor(strings[32]));
                break;
        }
    }

    /**
     * Sets back.
     */
    private void setBack() {
        color_01.setSelected(false);
        color_02.setSelected(false);
        color_03.setSelected(false);
        color_04.setSelected(false);
        color_05.setSelected(false);
        color_06.setSelected(false);
        color_07.setSelected(false);
        color_08.setSelected(false);
        color_09.setSelected(false);
        color_10.setSelected(false);
        color_11.setSelected(false);
        color_12.setSelected(false);
        color_13.setSelected(false);
        color_14.setSelected(false);
        color_15.setSelected(false);
        color_16.setSelected(false);
        color_17.setSelected(false);
        color_18.setSelected(false);
        color_19.setSelected(false);
        color_20.setSelected(false);
        color_21.setSelected(false);
        color_22.setSelected(false);
        color_23.setSelected(false);
        color_24.setSelected(false);
        color_25.setSelected(false);
        color_26.setSelected(false);
        color_27.setSelected(false);
        color_28.setSelected(false);
        color_29.setSelected(false);
        color_30.setSelected(false);
        color_31.setSelected(false);
        color_32.setSelected(false);
        color_33.setSelected(false);
    }

    /**
     * On editor action boolean.
     * @param v        the v
     * @param actionId the action id
     * @param event    the event
     * @return the boolean
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_SEND
                || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            InputMethodUtils.hideSoftInput(tv_tab_name);
            string = tv_tab_name.getText().toString();
            tv_tab_name.setText(string);
            onEditText.getEditText(string);
            return true;
        }
        return false;
    }

    /**
     * The interface On edit text.
     */
    public interface OnEditText {
        /**
         * Gets edit text.
         *
         * @param string the string
         */
        void getEditText(String string);

        /**
         * Sets text style.
         *
         * @param font the font
         */
        void setTextStyle(String font);

        /**
         * Sets text color.
         *
         * @param color the color
         */
        void setTextColor(String color);
    }
}
