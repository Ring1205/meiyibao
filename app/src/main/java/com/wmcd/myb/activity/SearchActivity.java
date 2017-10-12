package com.wmcd.myb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.HotAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.model.SearchBean;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.InputMethodUtils;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/4/7 0007.
 */
public class SearchActivity extends BaseActivity {
    /**
     * The Search et.
     */
    @Bind(R.id.search_et)
    EditText searchET;
    /**
     * The Fl hotsearch.
     */
/* @Bind(R.id.hot_gv)
     GridView hotGV;*/
    @Bind(R.id.fl_hotsearch)
    FlowLayout fl_hotsearch;
    /**
     * The Line 3.
     */
    @Bind(R.id.line3)
    ImageView line3;
    /**
     * The Iv 01.
     */
    @Bind(R.id.iv_01)
    ImageView iv_01;
    /**
     * The Iv 02.
     */
    @Bind(R.id.iv_02)
    ImageView iv_02;
    /**
     * The Iv 11.
     */
    @Bind(R.id.iv_11)
    ImageView iv_11;
    /**
     * The Iv 12.
     */
    @Bind(R.id.iv_12)
    ImageView iv_12;
    /**
     * The Iv 21.
     */
    @Bind(R.id.iv_21)
    ImageView iv_21;
    /**
     * The Hot list.
     */
/*@Bind(R.id.category_gv)
    GridView categoryGV;*/
    private List<SearchBean.HotWordListBean> hotList;
    /**
     * The constant SEARCH_ACTIVITY_CODE.
     */
    public static final int SEARCH_ACTIVITY_CODE = 102;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        request();
        init();
//        searchET.setFocusable(true);
//        searchET.requestFocus();
//        InputMethodManager imm = (InputMethodManager) searchET.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    /**
     * On resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Request.
     */
    private void request() {
        ServeManager.beforeSearch(this).enqueue(new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    hotList = response.body().getHotWordList();
                    showHotsearch();
                    line3.setPadding(110, 200, 110, 110);
                }
            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {
            }
        });
    }

    /**
     * The Tvlist.
     */
    private ArrayList<TextView> tvlist = new ArrayList<>();

    /**
     * Init.
     */
    private void init() {
    /*    hotGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                jumpAc(hotList.get(position).getWord());
            }
        });
        categoryGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/
        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if ("".equals(searchET.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "请输入关键字", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        jumpAc(searchET.getText().toString().trim());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Show hotsearch.
     */
    private void showHotsearch() {
        // TODO Auto-generated method stub

        for (int i = 0; i < hotList.size(); i++) {
            LinearLayout.MarginLayoutParams lp = new LinearLayout.MarginLayoutParams(-2, -2);

            lp.leftMargin = (int) UiUtils.dpToPixels(7, this);
            lp.rightMargin = (int) UiUtils.dpToPixels(7, this);
            lp.topMargin = (int) UiUtils.dpToPixels(10, this);
            lp.bottomMargin = (int) UiUtils.dpToPixels(10, this);
            LinearLayout ll = (LinearLayout) View.inflate(this, R.layout.hotsearchtv_item, null);
            final TextView tv = (TextView) ll.findViewById(R.id.search_tv);

            tv.setText(hotList.get(i).getWord());
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_shape));
            fl_hotsearch.addView(ll, lp);
            tvlist.add(tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpAc(tv.getText().toString());
                    Toast.makeText(SearchActivity.this, tv.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Jump ac.
     *
     * @param key the key
     */
    private void jumpAc(String key) {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra("key", key);
        startActivityForResult(intent, SEARCH_ACTIVITY_CODE);
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.cancle_tv, R.id.iv_01, R.id.iv_02, R.id.iv_11, R.id.iv_12, R.id.iv_21})
    public void onClick(View view) {
        Intent intent = new Intent(SearchActivity.this, SeriesActivity.class);
        switch (view.getId()) {
            case R.id.cancle_tv:
                String s = searchET.getText().toString();
                if ("" .equals(s)){
                    finish();

                }else{
                    searchET.setText("");
                }
                InputMethodUtils.hideSoftInput(SearchActivity.this);
                break;
            case R.id.iv_01:
                intent.putExtra("stid", "1");
                startActivity(intent);
                finish();
                break;
            case R.id.iv_02:
                intent.putExtra("stid", "2");
                startActivity(intent);
                finish();
                break;
            case R.id.iv_11:
                intent.putExtra("stid", "3");
                startActivity(intent);
                finish();
                break;
            case R.id.iv_12:
                intent.putExtra("stid", "4");
                startActivity(intent);
                finish();
                break;
            case R.id.iv_21:
                intent.putExtra("stid", "5");
                startActivity(intent);
                finish();
                break;

        }

    }

}
