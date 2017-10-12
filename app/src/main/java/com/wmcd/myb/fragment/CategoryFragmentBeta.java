package com.wmcd.myb.fragment;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.CategoryMenuAdapter;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.factory.FragmentFactory;
import com.wmcd.myb.fragment.catrgoryitem.CouponFragment;
import com.wmcd.myb.fragment.catrgoryitem.PostFragment;
import com.wmcd.myb.fragment.catrgoryitem.RollFragment;
import com.wmcd.myb.model.CategoryModel;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class CategoryFragmentBeta extends BaseFragment {
    @Bind(R.id.left_drawer)
    RecyclerView leftDrawer;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.vp_categorybeta)
    NoScrollViewPager vpCategorybeta;
    private MyFragmentStatePagerAdapter adapter;
    private CategoryModel.ListBean.TypesBean data;
    private List<CategoryModel.ListBean.TypesBean> datas_all;
    private List<CategoryModel.ListBean.TypesBean> datas_original;
    private List<CategoryModel.ListBean.TypesBean> datas_menu;

    private String[] titles = {"精选推荐", "套系宣传"};
    private CategoryMenuAdapter adapterMenu;

    List<Integer> ttid_data;
    @Override
    protected void initView() {
        datas_all = new ArrayList<>();
        datas_original = new ArrayList<>();
        datas_menu = new ArrayList<>();
        ttid_data = new ArrayList<>();
        point = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            data = new CategoryModel.ListBean.TypesBean();
            data.setName(titles[i]);
            data.setDesc("0");
            data.setTtid(-i - 1);
            //data_all and data_menu contain this data
            datas_all.add(data);
            datas_menu.add(data);
        }
        leftDrawer.setLayoutManager(new LinearLayoutManager(context));
        drawerLayout.setScrimColor(Color.TRANSPARENT);

        if (adapter == null)
            adapter = new MyFragmentStatePagerAdapter(getChildFragmentManager());
        //cache page
        vpCategorybeta.setOffscreenPageLimit(0);

    }
private List<Integer> point ;
    @Override
    protected void initData() {
        ServeManager.queryTemplateType(context).enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {

                if (response.body() != null && "01".equals(response.body().getResult())) {
                    // only data_all contain e
                    for (int i = 0; i < response.body().getList().size(); i++) {
                        CategoryModel.ListBean.TypesBean e = new CategoryModel.ListBean.TypesBean();
                        e.setName(response.body().getList().get(i).getName().substring(0, 2));
                        e.setDesc("1");
                        datas_all.add(e);
                        Log.i("data_all",datas_all.size()+"");
                        point.add(i,datas_all.size());
                        datas_all.addAll(response.body().getList().get(i).getTypes());

                        datas_original.addAll(response.body().getList().get(i).getTypes());
                        datas_menu.addAll(response.body().getList().get(i).getTypes());
                    }
                    for (int i = 0; i < datas_menu.size(); i++) {
                        int ttid = datas_menu.get(i).getTtid();
                        ttid_data.add(i,ttid);
                    }
                    adapter.setDatas_menu(datas_menu);
                    vpCategorybeta.setAdapter(adapter);


                    adapterMenu = new CategoryMenuAdapter(datas_all, context);
                    leftDrawer.setAdapter(adapterMenu);
                    adapterMenu.setOnMyItemClickListener(new CategoryMenuAdapter.OnMyItemClickListener() {
                        @Override
                        public void myClick(View v, int position) {
                            int ttid;
                            if (position>=point.get(1)){
                                ttid = datas_menu.get(position-2).getTtid();
                            }else if (position<point.get(1)&&position>=point.get(0)){
                                    ttid = datas_menu.get(position-1).getTtid();
                                }
                                else {
                                ttid = datas_menu.get(position).getTtid();
                            }
                            Log.i("tttttttttttttttttid",ttid+"");
                            vpCategorybeta.setCurrentItem(ttid_data.indexOf(ttid));
                            Log.i("123", "position---" + position);
                            setfragment();
                            drawerLayout.closeDrawer(leftDrawer);
                        }
                    });
                } else {
                    Toast.makeText(context, "网络加载失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getTtidByPosition(int position) {
        int ttid = datas_all.get(position).getTtid();
        if (ttid == 0) {
           return getTtidByPosition(position + 1);
        } else {
            return ttid;
        }

    }

    private void setfragment() {
        PostFragment fragment = (PostFragment) FragmentFactory.createFragment(4);
        fragment.setPage(1);
        CouponFragment fragment1 = (CouponFragment) FragmentFactory.createFragment(12);
        fragment1.setPage(1);
        RollFragment fragment2 = (RollFragment) FragmentFactory.createFragment(9);
        fragment2.setPage(1);

    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_categorybeta;
    }

    class MyFragmentStatePagerAdapter extends FragmentPagerAdapter {


        private List<CategoryModel.ListBean.TypesBean> datas_menu;

        public void setDatas_menu(List<CategoryModel.ListBean.TypesBean> datas_menu) {
            this.datas_menu = datas_menu;
        }


        public MyFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseFragment getItem(int position) {//指定Position所对应的页面的Fragment内容
            //这里先根据position查找数据集所对应的ttid 然后根据ttid创建对应的fragment
            int ttid = datas_menu.get(position).getTtid();
            Log.i("ttttid", "ttid" + ttid);
            BaseFragment fragment = FragmentFactory.createFragment(ttid);
            fragment.setOnchangeDrawerListener(new changeDrawer() {
                @Override
                public void close() {
                    drawerLayout.closeDrawer(leftDrawer);
                }

                @Override
                public void open() {
                    drawerLayout.openDrawer(leftDrawer);
                }
            });
            return fragment;
        }
        //这个方法根据传入的position返回ttid


        @Override
        public int getCount() {//决定ViewPager页数的总和
            return datas_menu.size();
        }

    }

}
