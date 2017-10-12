package com.wmcd.myb.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.activity.ClassifyActivity;
import com.wmcd.myb.activity.DocActivity;
import com.wmcd.myb.activity.SearchResultActivity;
import com.wmcd.myb.adapter.CategoryAdapter;
import com.wmcd.myb.adapter.PropagandaAdapter;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.ClassifyModel;
import com.wmcd.myb.model.PropagandaModel;
import com.wmcd.myb.model.SeriesModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.AnimatorUtil;
import com.wmcd.myb.util.InputMethodUtils;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.wigdet.SpaceItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 模板分类
 * 2017/4/6
 */
public class CategoryFragment extends BaseFragment {
    /**
     * The Text view 2.
     */
    @Bind(R.id.textView2)
    TextView textView2;//套系宣传
    /**
     * The Tv 01 01.
     */
    @Bind(R.id.tv_01_01)
    TextView tv_01_01;//头像
    /**
     * The Tv 01 02.
     */
    @Bind(R.id.tv_01_02)
    TextView tv_01_02;//头像
    /**
     * The Tv 01 03.
     */
    @Bind(R.id.tv_01_03)
    TextView tv_01_03;//头像
    /**
     * The Tv 01 04.
     */
    @Bind(R.id.tv_01_04)
    TextView tv_01_04;//头像
    /**
     * The Tv 01 05.
     */
    @Bind(R.id.tv_01_05)
    TextView tv_01_05;//头像
    /**
     * The Tv 01 06.
     */
    @Bind(R.id.tv_01_06)
    TextView tv_01_06;//头像
    /**
     * The Tv 02 01.
     */
    @Bind(R.id.tv_02_01)
    TextView tv_02_01;//头像
    /**
     * The Tv 02 02.
     */
    @Bind(R.id.tv_02_02)
    TextView tv_02_02;//头像
    /**
     * The Tv 02 03.
     */
    @Bind(R.id.tv_02_03)
    TextView tv_02_03;//头像
    /**
     * The Tv 02 04.
     */
    @Bind(R.id.tv_02_04)
    TextView tv_02_04;//头像
    /**
     * The Tv 02 05.
     */
    @Bind(R.id.tv_02_05)
    TextView tv_02_05;//头像
    /**
     * The Recyclerview.
     */
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    /**
     * The Recyclerview top.
     */
    @Bind(R.id.recyclerview_top)
    RecyclerView recyclerview_top;
    /**
     * The Ll 01.
     */
    @Bind(R.id.ll_01)
    LinearLayout ll_01;
    /**
     * The Title ll 01.
     */
    @Bind(R.id.title_ll_01)
    LinearLayout title_ll_01;
    /**
     * The Ll 11.
     */
    @Bind(R.id.ll_11)
    LinearLayout ll_11;
    /**
     * The Ll cetegory show.
     */
    @Bind(R.id.ll_cetegory_show)
    LinearLayout ll_cetegory_show;
    /**
     * The Ll 12.
     */
    @Bind(R.id.ll_12)
    LinearLayout ll_12;
    /**
     * The Ll 13.
     */
    @Bind(R.id.ll_13)
    LinearLayout ll_13;
    /**
     * The Text 01.
     */
    @Bind(R.id.text_01)
    TextView text_01;
    /**
     * The Text 02.
     */
    @Bind(R.id.text_02)
    TextView text_02;
    /**
     * The Text 03.
     */
    @Bind(R.id.text_03)
    TextView text_03;
    /**
     * The Text 04.
     */
    @Bind(R.id.text_04)
    TextView text_04;
    /**
     * The Text 05.
     */
    @Bind(R.id.text_05)
    TextView text_05;
    /**
     * The Text 06.
     */
    @Bind(R.id.text_06)
    TextView text_06;
    /**
     * The Text 07.
     */
    @Bind(R.id.text_07)
    TextView text_07;
    /**
     * The Text 08.
     */
    @Bind(R.id.text_08)
    TextView text_08;
    /**
     * The Text 09.
     */
    @Bind(R.id.text_09)
    TextView text_09;
    /**
     * The Text 10.
     */
    @Bind(R.id.text_10)
    TextView text_10;
    /**
     * The Search et.
     */
    @Bind(R.id.search_et)
    EditText search_et;
    /**
     * The Classify adapter.
     */
    private CategoryAdapter categoryAdapter;
    /**
     * The Propaganda adapter.
     */
    private PropagandaAdapter propagandaAdapter;
    /**
     * The Data.
     */
    private List<ClassifyModel.ListBean> data;
    /**
     * The Pro data.
     */
    private List<PropagandaModel.ListBean> proData;
    /**
     * The Item id.
     */
    private int itemId;
    /**
     * The Page.
     */
    private int page = 1;

    /**
     * Sets layout resouce id.
     *
     * @return the layout resouce id
     */
    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_category01;
    }

    /**
     * Init img.
     */
    private void initImg() {
        UiUtils.startnet(context);
        ServeManager.getPropaganda(context).enqueue(new Callback<PropagandaModel>() {
            @Override
            public void onResponse(Call<PropagandaModel> call, Response<PropagandaModel> response) {
                if (response.body() != null)
                    Log.e("CategoryFragment", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    proData = response.body().getList();
                    propagandaAdapter.getData(proData);
                    propagandaAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context.getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                }
                UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<PropagandaModel> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("CategoryFragment", "onFailure:" + t.getLocalizedMessage());
                UiUtils.endnet();
            }
        });
    }

    /**
     * Init data.
     */
    protected void initData() {
        ServeManager.getClassify(context, MyApplication.mUser == null ? "" : MyApplication.mUser.getUid() + "", itemId + "", page + "", "", "", "1","").enqueue(new Callback<ClassifyModel>() {
            @Override
            public void onResponse(Call<ClassifyModel> call, Response<ClassifyModel> response) {
                if (response.body() != null)
                    Log.e("CategoryFragment", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    if (UiUtils.isList(response.body().getList())) {
                        data = response.body().getList();
                        categoryAdapter.setDateH(data);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
                        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            //返回position对应的条目所占的size
                            @Override
                            public int getSpanSize(int position) {
                                if (position == 0 || position == data.size() + 1)

                                    return 2;
                                else

                                    return 1;

                            }
                        });
                        recyclerview.setLayoutManager(gridLayoutManager);
                        ServeManager.getClassify(context, MyApplication.mUser == null ? "" : MyApplication.mUser.getUid() + "", itemId + "", page + "", "", "", "","").enqueue(new Callback<ClassifyModel>() {
                            @Override
                            public void onResponse(Call<ClassifyModel> call, Response<ClassifyModel> response) {
                                if (response.body() != null)
                                    Log.e("CategoryFragment", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                                if (response.body() != null && "01".equals(response.body().getResult())) {
                                    if (UiUtils.isList(response.body().getList())) {
                                        data = response.body().getList();
                                        categoryAdapter.setDateN(data);
                                        recyclerview.setAdapter(categoryAdapter);
                                        categoryAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    Toast.makeText(context.getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ClassifyModel> call, Throwable t) {
                                Toast.makeText(context.getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        // page++;
                    }
                } else {
                    Toast.makeText(context.getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClassifyModel> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("CategoryFragment", "onFailure:" + t.getLocalizedMessage());
            }
        });
    }


    /**
     * Init view.
     */
    protected void initView() {

        recyclerview_top.setVisibility(View.VISIBLE);
        ll_01.setVisibility(View.GONE);
        setPoster(View.GONE);
        textView2.setBackgroundResource(R.drawable.fragment_text_back);
        textView2.setTextColor(Color.parseColor("#ffffff"));

        StaggeredGridLayoutManager staggeredGridT = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_top.setLayoutManager(staggeredGridT);
        propagandaAdapter = new PropagandaAdapter(context);
        recyclerview_top.addItemDecoration(new SpaceItemDecoration((int) UiUtils.dpToPixels(3, context)));
        recyclerview_top.setAdapter(propagandaAdapter);


        initImg();

    }

    /**
     * Sets listener.
     */
    @Override
    protected void setListener() {
        super.setListener();
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    new SearchResultActivity().startClassifyActivity((Activity) context, search_et.getText().toString());
                }
                return false;
            }
        });
    }

    /**
     * Initing.
     *
     * @param i the
     */
    private void initing(int i) {
        setPoster(View.VISIBLE);
        categoryAdapter = new CategoryAdapter(context, itemId, 1);
        recyclerview.setHasFixedSize(true);


//添加滑动监听 主要是为了动画
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mScrollThreshold;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
                if (isSignificantDelta) {
                    if (dy > 20) {
                        //消失
                        if (title_ll_01.getVisibility() == View.VISIBLE) {
                            if (AnimatorUtil.isOver) {
                                AnimatorUtil.animHeightToView(getActivity(), title_ll_01, false, 1000);
                                AnimatorUtil.animWidthToView(getActivity(), text_10, false, 1000);
                            }
                        }
                    } else if (dy < -20) {
                        //出现
                        if (title_ll_01.getVisibility() == View.GONE) {
                            if (AnimatorUtil.isOver) {
                                AnimatorUtil.animHeightToView(getActivity(), title_ll_01, true, 1000);
                                AnimatorUtil.animWidthToView(getActivity(), text_10, true, 1000);
                            }
                        }
                    }
                }
            }
        });
        initData();
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.textView2, R.id.tv_01_01, R.id.tv_01_02, R.id.tv_01_03, R.id.tv_01_04, R.id.tv_01_05, R.id.tv_01_06, R.id.tv_02_01, R.id.tv_02_02, R.id.tv_02_03, R.id.tv_02_04, R.id.tv_02_05, R.id.text_01, R.id.text_02, R.id.text_03, R.id.text_04, R.id.text_05, R.id.text_06, R.id.text_07, R.id.text_08, R.id.text_09, R.id.text_10, R.id.iv_all, R.id.tv_look_all_second, R.id.cancle_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView2:
                setTextStyls();
                recyclerview_top.setVisibility(View.VISIBLE);
                ll_01.setVisibility(View.GONE);
                textView2.setBackgroundResource(R.drawable.fragment_text_back);
                textView2.setTextColor(Color.parseColor("#ffffff"));
                //显示套系
                initImg();
                break;
            case R.id.tv_01_01:
                setTextStyls();
                tv_01_01.setBackgroundResource(R.drawable.fragment_text_back);
                tv_01_01.setTextColor(Color.parseColor("#ffffff"));
                itemId = 3;
                page = 1;
                //data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.tv_01_02:
                setTextStyls();
                tv_01_02.setBackgroundResource(R.drawable.fragment_text_back);
                tv_01_02.setTextColor(Color.parseColor("#ffffff"));
                itemId = 4;
                page = 1;
                //data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.tv_01_03:
                setTextStyls();
                tv_01_03.setBackgroundResource(R.drawable.fragment_text_back);
                tv_01_03.setTextColor(Color.parseColor("#ffffff"));
                itemId = 5;
                page = 1;
                //data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.tv_01_04:
                setTextStyls();
                tv_01_04.setBackgroundResource(R.drawable.fragment_text_back);
                tv_01_04.setTextColor(Color.parseColor("#ffffff"));
                startActivity(new Intent(context, DocActivity.class));
                page = 1;
                //data.clear();
                break;
            case R.id.tv_01_05:
                setTextStyls();
                tv_01_05.setBackgroundResource(R.drawable.fragment_text_back);
                tv_01_05.setTextColor(Color.parseColor("#ffffff"));
                itemId = 11;
                page = 1;
                //data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.tv_01_06:
                setTextStyls();
                tv_01_06.setBackgroundResource(R.drawable.fragment_text_back);
                tv_01_06.setTextColor(Color.parseColor("#ffffff"));
                itemId = 14;
                page = 1;
                //data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.tv_02_01:
                setTextStyls();
                tv_02_01.setBackgroundResource(R.drawable.fragment_text_back);
                tv_02_01.setTextColor(Color.parseColor("#ffffff"));
                itemId = 7;
                page = 1;
                //data.clear();
                initPoster();
                initing(4);
                initPoster();
                break;
            case R.id.tv_02_02:
                setTextStyls();
                tv_02_02.setBackgroundResource(R.drawable.fragment_text_back);
                tv_02_02.setTextColor(Color.parseColor("#ffffff"));
                itemId = 9;
                page = 1;
                //data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.tv_02_03:
                setTextStyls();
                tv_02_03.setBackgroundResource(R.drawable.fragment_text_back);
                tv_02_03.setTextColor(Color.parseColor("#ffffff"));
                itemId = 12;
                page = 1;
                // data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.tv_02_04:
                setTextStyls();
                tv_02_04.setBackgroundResource(R.drawable.fragment_text_back);
                tv_02_04.setTextColor(Color.parseColor("#ffffff"));
                itemId = 13;
                page = 1;
                //data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.tv_02_05:
                setTextStyls();
                tv_02_05.setBackgroundResource(R.drawable.fragment_text_back);
                tv_02_05.setTextColor(Color.parseColor("#ffffff"));
                itemId = 8;
                page = 1;
                //data.clear();
                initPoster();
                initing(3);
                break;
            case R.id.text_01:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_01.getText().toString());
                break;
            case R.id.text_02:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_02.getText().toString());
                break;
            case R.id.text_03:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_03.getText().toString());
                break;
            case R.id.text_04:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_04.getText().toString());
                break;
            case R.id.text_05:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_05.getText().toString());
                break;
            case R.id.text_06:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_06.getText().toString());
                break;
            case R.id.text_07:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_07.getText().toString());
                break;
            case R.id.text_08:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_08.getText().toString());
                break;
            case R.id.text_09:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_09.getText().toString());
                break;
            case R.id.text_10:
                new SearchResultActivity().startClassifyActivity((Activity) context, text_10.getText().toString());
                break;
            case R.id.iv_all:
                Intent intent = new Intent(context, ClassifyActivity.class);
                intent.putExtra("Type", itemId + "");
                startActivity(intent);
                break;
            case R.id.tv_look_all_second:
                Intent intent1 = new Intent(context, ClassifyActivity.class);
                intent1.putExtra("Type", itemId + "");
                startActivity(intent1);
                break;
            case R.id.cancle_tv:
                search_et.setText("");
                InputMethodUtils.hideSoftInput((BaseActivity) context);
                break;
        }
    }

    /**
     * Init poster.
     */
    private void initPoster() {
        ServeManager.getTag(context, itemId + "").enqueue(new Callback<SeriesModel>() {
            @Override
            public void onResponse(Call<SeriesModel> call, Response<SeriesModel> response) {
                if (response.body() != null)
                    Log.e("CategoryFragment", "Result:" + response.body().getResult() + "     Msg:" + response.body().getMsg());
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    setPoster(View.VISIBLE);
                    List<SeriesModel.ListBean> listBean = response.body().getList();
                    for (int i = 0; i < listBean.size(); i++) {
                        switch (i) {
                            case 0:
                                text_01.setText(listBean.get(i).getName());
                                break;
                            case 1:
                                text_02.setText(listBean.get(i).getName());
                                break;
                            case 2:
                                text_03.setText(listBean.get(i).getName());
                                break;
                            case 3:
                                text_04.setText(listBean.get(i).getName());
                                break;
                            case 4:
                                text_05.setText(listBean.get(i).getName());
                                break;
                            case 5:
                                text_06.setText(listBean.get(i).getName());
                                break;
                            case 6:
                                text_07.setText(listBean.get(i).getName());
                                break;
                            case 7:
                                text_08.setText(listBean.get(i).getName());
                                break;
                            case 8:
                                text_09.setText(listBean.get(i).getName());
                                break;
                            case 9:
                                text_10.setText(listBean.get(i).getName());
                                break;
                        }
                    }
                } else {
                    Toast.makeText(context.getApplicationContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SeriesModel> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                Log.e("CategoryFragment", "onFailure:" + t.getLocalizedMessage());
            }
        });
    }

    /**
     * Sets text styls.
     */
    private void setTextStyls() {
        recyclerview_top.setVisibility(View.GONE);
        ll_01.setVisibility(View.VISIBLE);
        textView2.setBackground(null);
        textView2.setTextColor(Color.BLACK);
        tv_01_01.setBackground(null);
        tv_01_01.setTextColor(Color.BLACK);
        tv_01_02.setBackground(null);
        tv_01_02.setTextColor(Color.BLACK);
        tv_01_03.setBackground(null);
        tv_01_03.setTextColor(Color.BLACK);
        tv_01_04.setBackground(null);
        tv_01_04.setTextColor(Color.BLACK);
        tv_01_05.setBackground(null);
        tv_01_05.setTextColor(Color.BLACK);
        tv_01_06.setBackground(null);
        tv_01_06.setTextColor(Color.BLACK);
        tv_02_01.setBackground(null);
        tv_02_01.setTextColor(Color.BLACK);
        tv_02_02.setBackground(null);
        tv_02_02.setTextColor(Color.BLACK);
        tv_02_03.setBackground(null);
        tv_02_03.setTextColor(Color.BLACK);
        tv_02_04.setBackground(null);
        tv_02_04.setTextColor(Color.BLACK);
        tv_02_05.setBackground(null);
        tv_02_05.setTextColor(Color.BLACK);


    }

    /**
     * Sets poster.
     *
     * @param visibility the visibility
     */
    private void setPoster(int visibility) {
        ll_11.setVisibility(visibility);
        ll_12.setVisibility(visibility);
        ll_13.setVisibility(visibility);
        text_10.setVisibility(visibility);
    }

}
