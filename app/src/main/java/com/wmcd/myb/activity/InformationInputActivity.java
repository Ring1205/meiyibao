package com.wmcd.myb.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.http.UrlConfig;
import com.wmcd.myb.model.InformationModel;
import com.wmcd.myb.net.ServeManager;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Information input activity.信息录入
 */
public class InformationInputActivity extends BaseActivity {
    /**
     * The Et 01.
     */
    @Bind(R.id.et_01)
    EditText et_01;
    /**
     * The Tv phone.
     */
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    /**
     * The Et phone.手机号码
     */
    @Bind(R.id.et_phone)
    EditText et_phone;
    /**
     * The Et 03.店铺详情地址
     */
    @Bind(R.id.et_03)
    EditText et_03;
    /**
     * The Et 04.店铺名
     */
    @Bind(R.id.et_04)
    EditText et_04;
    /**
     * The Et 05.店铺行业
     */
    @Bind(R.id.et_05)
    EditText et_05;
    @Bind(R.id.city_picker)
    LinearLayout cityPicker;
    @Bind(R.id.tv_province)
    TextView tvProvince;
    @Bind(R.id.tv_city)
    TextView tvCity;
    /**
     * The Uid.
     */
    private String uid,
    /**
     * The Name.
     */
    name,
    /**
     * The Phone.
     */
    phone,
    /**
     * The Address.
     */
    address,
    /**
     * The City.
     */
    city,
    /**
     * The Shopname.
     */
    shopname,
    /**
     * The Industry.
     */
    industry;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_input);
        ButterKnife.bind(this);
        String str = "<font color='#FF0000'>*</font> +86";
        tv_phone.setText(Html.fromHtml(str));


        //初始化值
        et_01.setText(MyApplication.mUser.getName());
        et_phone.setText(MyApplication.mUser.getPhone());
        et_03.setText(MyApplication.mUser.getAddress());
        et_04.setText(MyApplication.mUser.getShopname());
        et_05.setText(MyApplication.mUser.getIndustry());
    }

    private void initCityPicker() {
        CityPicker cityPicker = new CityPicker.Builder(InformationInputActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("湖南省")
                .city("长沙市")
                .district("雨花区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份

                String province = citySelected[0];
                tvProvince.setText(province);
                //城市
                String city = citySelected[1];
                tvCity.setText(city);
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * submit data. 提交用户录入的数据到服务器
     */
    // TODO: 2017/6/24 0024 提交之后没有更新userbean
    private void submitData() {
        uid = MyApplication.mUser.getUid() + "";
        name = et_01.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        city = tvProvince.getText()+tvCity.getText().toString();
        address = et_03.getText().toString().trim();
        shopname = et_04.getText().toString().trim();
        industry = et_05.getText().toString().trim();
        Log.e("InformationActivity", uid + "=" + name + "=" + phone + "=" + address + "=" + city + "=" + shopname + "=" + industry);
        //对手机号码进行校验
        if (!Pattern.matches(UrlConfig.PHONE_MATCH, phone.toString().trim())) {
            Toast.makeText(this, "请填正确电话号码", Toast.LENGTH_SHORT).show();
        } else {
            ServeManager.getUserInformation(this, uid, name, phone, address, city, shopname, industry).enqueue(new Callback<InformationModel>() {
                @Override
                public void onResponse(Call<InformationModel> call, Response<InformationModel> response) {
                    if (response.body() != null) {
                        Log.e("PayFeedbackActivity", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                        if ("03".equals(response.body().getResult())) {
                            Toast.makeText(InformationInputActivity.this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (response.body() != null && "01".equals(response.body().getResult())) {
                        Toast.makeText(InformationInputActivity.this, "录入完毕", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(InformationInputActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<InformationModel> call, Throwable t) {
                    Toast.makeText(InformationInputActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /**
     * On click.点击事件
     *
     * @param view the view 被点击的控件
     */
    @OnClick({R.id.button_luru})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_luru:
                submitData();
                break;
        }
    }


    @OnClick(R.id.city_picker)
    public void onClick() {
        initCityPicker();

    }

}
