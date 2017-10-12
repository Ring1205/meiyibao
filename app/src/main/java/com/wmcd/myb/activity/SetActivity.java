package com.wmcd.myb.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 邓志宏 on 2017/2/20.
 */
public class SetActivity extends BaseActivity {
    /**
     * The Tv banben.
     */
    @Bind(R.id.tv_banben)
    TextView tv_banben;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        tv_banben.setText("版本:" +getVersion());
    }
    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_back, R.id.tv_log_out, R.id.iv_xieyi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_log_out:
                if ("1".equals(MyApplication.mUser.getLoginType())){
                    MyApplication.mUser = null;
                }else if ("2".equals(MyApplication.mUser.getLoginType())){
                    MyApplication.mUser.logout(this);
                    MyApplication.mUser = null;
                }
                Intent intent = new Intent(this, MainTopActivity.class);
                intent.putExtra("123",123);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_xieyi:
                startActivity(new Intent(this, UserAgreementActivity.class));
                break;
        }
    }


    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     *
     * @return the version
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "解析失败";
        }
    }
}
