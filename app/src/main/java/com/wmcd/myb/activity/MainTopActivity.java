package com.wmcd.myb.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.fragment.AlbumFragment;
import com.wmcd.myb.fragment.CategoryFragment;
import com.wmcd.myb.fragment.CategoryFragmentBeta;
import com.wmcd.myb.fragment.CenterFragment;
import com.wmcd.myb.fragment.HomeFragment;
import com.wmcd.myb.fragment.MemberFragment;
import com.wmcd.myb.fragment.catrgoryitem.CenterFragmentBeta;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.VersionBean;
import com.wmcd.myb.net.ServeManager;
import com.wmcd.myb.util.UpgradeManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 邓志宏 on 2017/2/16.
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.__\_<|>_/___.'  >'"".                   //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //     ========`-.____`-.___\_____/___.-`____.-'========          //
 * //                          `=---='                               //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //                   佛祖保佑            NO BUG                    //
 * ////////////////////////////////////////////////////////////////////
 */
public class MainTopActivity extends BaseActivity {
    /**
     * The Main tab 01.首页
     */
    @Bind(R.id.main_tab_01)
    LinearLayout mainTab01;
    @Bind(R.id.main_pic)
    LinearLayout mainpic;
    /**
     * The Main tab 04.个人
     */
    @Bind(R.id.main_tab_04)
    LinearLayout mainTab04;
    /**
     * The Main tab in.会员
     */
    @Bind(R.id.main_tab_in)
    LinearLayout main_tab_in;
    /**
     * The Main tab category.分类
     */
    @Bind(R.id.main_tab_category)
    LinearLayout main_tab_category;
    /**
     * The Tabs.
     */
    private List<View> Tabs;
    /**
     * The Fragments.
     */
    private Fragment[] fragments;

    /**
     * The Home fragment.首页
     */
    private HomeFragment homeFragment;
    /**
     * The Member fragment. 会员
     */
    private MemberFragment memberFragment;
    /**
     * The Center fragment. 个人
     */
    private CenterFragment centerFragment;

    /**
     * The Fragment transaction.
     */
    private FragmentTransaction fragmentTransaction;
    /**
     * The Category fragment. 分类
     */
    private CategoryFragment categoryFragment;
    private CategoryFragmentBeta categoryFragmentBeta;
    private CenterFragmentBeta centerFragmentBeta;
    private AlbumFragment albumFragment;


    /**
     * The Current tab index.当前选中的Tab
     */
    public int currentTabIndex;
    /**
     * The Time. 返回的间隔时间
     */
    private long time;

    /**
     * On create.
     */

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intExtra = intent.getIntExtra("123", 0);
        if (intExtra == 123) {
            Tabs.get(0).performClick();
            Tabs.get(0).setSelected(true);
            currentTabIndex = 0;
        }

        Log.e("123456", "123456" + intent.getIntExtra("member", 0));
        if (intent.getIntExtra("member", 0) == 1414) {
            Tabs.get(3).performClick();
            Tabs.get(3).setSelected(true);
            currentTabIndex = 3;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_top);
        ButterKnife.bind(this);
        // TODO: 2017/6/27 0027  清除磁盘缓存 后台改了图片之后Glide会继续加载已经缓存的图片 目前没有更好的方法
//        clearCacheDiskSelf();
        if (savedInstanceState != null) {
            currentTabIndex = savedInstanceState.getInt("position");
        }
        //初始化fragment和tabs集合
        initTabAndFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //显示首页
        fragmentTransaction.add(R.id.fragment_container, homeFragment).show(homeFragment).commit();
        //触发tab的点击事件
        Tabs.get(currentTabIndex).performClick();

        //动态更改关联数据库
        updateVersion();
    }

    /**
     * Init tab and fragment.
     */
    private void initTabAndFragment() {
        Tabs = new ArrayList<>();
        Tabs.add(mainTab01);
        Tabs.add(main_tab_category);
        Tabs.add(mainpic);
        Tabs.add(main_tab_in);
        Tabs.add(mainTab04);
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        if (memberFragment == null)
            memberFragment = new MemberFragment();
        if (albumFragment == null)
            albumFragment = new AlbumFragment();
        if (centerFragment == null)
            centerFragment = new CenterFragment();
        if (categoryFragment == null)
            categoryFragment = new CategoryFragment();//加分类功能
        if (categoryFragmentBeta == null)
            categoryFragmentBeta = new CategoryFragmentBeta();//new分类功能
        if (centerFragmentBeta == null)
             centerFragmentBeta = new CenterFragmentBeta();//new分类功能

        fragments = new Fragment[]{homeFragment, categoryFragmentBeta, albumFragment, memberFragment, centerFragment};
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
        if (MyApplication.mUser == null) {
            //fragmentTransaction.add(R.id.fragment_container, homeFragment).show(homeFragment).commit();
        }
        fragments[0].onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.main_tab_01, R.id.main_tab_in, R.id.main_tab_04, R.id.main_tab_category, R.id.main_pic})
    public void onClick(View view) {
        int index = currentTabIndex;
        switch (view.getId()) {
            case R.id.main_tab_01:
                index = 0;
//                main_tab_pic.setSelected(false);

                break;
            case R.id.main_tab_category:
                index = 1;
//                main_tab_pic.setSelected(false);
                break;
            case R.id.main_pic:
                index = 2;
                break;
            case R.id.main_tab_in:
//                main_tab_pic.setSelected(false);
                if (MyApplication.mUser == null) {
                    toLogin();
                    return;
                } else {
                    index = 3;
                    memberFragment.refresh();
                }
                break;
            case R.id.main_tab_04:
                if (MyApplication.mUser == null) {
                    toLogin();
                    return;
                } else {
                    centerFragment.initData();
                    index = 4;
                }
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            Tabs.get(currentTabIndex).setSelected(false);
            trx.show(fragments[index]).commit();
        }
        //设置当前点击tab为选中状态
        Tabs.get(index).setSelected(true);
        //设置当前点击position为currentTabIndex值
        currentTabIndex = index;
        if (albumFragment != null) {

            albumFragment.reload();

        }
        if (albumFragment != null) {
            albumFragment.pausemusic();
            if (currentTabIndex == 2) {
                albumFragment.startmusic();
            }
        }
    }



    /**
     * On resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
      /*  if (MyApplication.mUser == null && currentTabIndex != 1) {
            Tabs.get(0).performClick();
        }
        Tabs.get(currentTabIndex).setSelected(true);*/
    }

    /**
     * To login.登录
     */
    private void toLogin() {
        LoginActivity.loginForBack(this);
    }

    /**
     * On back pressed. 返回的点击事件
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time < 2000) {
            if (albumFragment!=null){
                albumFragment.reload();
            }
            finish();
        } else {
            Toast.makeText(MainTopActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        }
    }

    /**
     * On save instance state.保存状态
     *
     * @param outState the out state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentTabIndex);
    }

    /**
     * Update version.更新版本号
     */
    private void updateVersion() {
        ServeManager.validateVesion(this).enqueue(new Callback<VersionBean>() {
            @Override
            public void onResponse(Call<VersionBean> call, Response<VersionBean> response) {
                VersionBean versionBean = response.body();
                if (versionBean != null && "01".equals(versionBean.getResult())) {
                    VersionBean.CommonData commonData = versionBean.getCommonData();
                    UrlConfig.FONT = commonData.getFontOSSUrl();
                    UrlConfig.DOC_HTTP = commonData.getDocOSSUrl();
                    UrlConfig.IMG = commonData.getImgOSSUrl();
                    UrlConfig.endpoint = commonData.getOSSEndpoint();
                    UrlConfig.testBucket = commonData.getImgBucket();
                    UrlConfig.accessKeyId = commonData.getAliyunAccessKeyId();
                    UrlConfig.accessKeySecret = commonData.getAliyunAccessKeySecret();
                    try {
                        PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
                        int versionCode = pi.versionCode;
                        if (versionCode < versionBean.getVersion().getApplicationVersion()) {
                            upgradePrompt(versionBean.getVersion());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<VersionBean> call, Throwable t) {
                Toast.makeText(MainTopActivity.this, "未更新新版本相关链接", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Upgrade prompt.版本更新
     *
     * @param version the version 版本号
     */
    private void upgradePrompt(final VersionBean.Version version) {
        final Dialog queryDialog = new Dialog(this, R.style.dialog);
        queryDialog.setContentView(R.layout.prompt_edit);
        queryDialog.setCancelable(false);
        queryDialog.setCanceledOnTouchOutside(false);
        String dsc = "".equals(version.getApplicationDes()) ? "版本升级" : Html.fromHtml(version.getApplicationDes()).toString();
        ((TextView) queryDialog.findViewById(R.id.textView1)).setText(dsc);
        Button upgradeBT = (Button) queryDialog.findViewById(R.id.ok_btn);
        upgradeBT.setText(getString(R.string.now_upgrade_title));
        Button cannclBT = (Button) queryDialog.findViewById(R.id.canncl_btn);
        if (version.getApplicationStatus().equals("Y")) {//强制升级
            cannclBT.setText(getString(R.string.exit_title));
        } else
            cannclBT.setText(getString(R.string.next_upgrade_title));

        queryDialog.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDialog.dismiss();
                UpgradeManager upgradeManager = new UpgradeManager(MainTopActivity.this, version.getUrl(), version.getApplicationDes());
                upgradeManager.checkUpdateInfo();
            }
        });
        queryDialog.findViewById(R.id.canncl_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDialog.dismiss();
                if (version.getApplicationStatus().equals("Y")) {//强制升级
                    finish();
                } else {
                    queryDialog.dismiss();
                }
            }
        });
        queryDialog.show();
    }

}
