package com.wmcd.myb.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;
import com.wmcd.myb.model.LoginModel;
import com.wmcd.myb.model.ResultModel;
import com.wmcd.myb.net.ServeManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/23.
 */
public class WithdrawActivity extends BaseActivity {
    /**
     * The Card et.
     */
    @Bind(R.id.card_et)
    EditText card_et;
    /**
     * The Name et.
     */
    @Bind(R.id.name_et)
    EditText name_et;
    /**
     * The Money et.
     */
    @Bind(R.id.money_et)
    EditText money_et;
    /**
     * The Tv ketq.
     */
    @Bind(R.id.tv_ketq)
    TextView tv_ketq;
    /**
     * The Bang et.
     */
    @Bind(R.id.bang_et)
    EditText bang_et;
    /**
     * The A float.
     */
    private float aFloat;
    /**
     * The Name.
     */
    private String name, /**
     * The Bankno.
     */
    bankno, /**
     * The Amount.
     */
    amount, /**
     * The Bankname.
     */
    bankname;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankinfo);
        ButterKnife.bind(this);
        aFloat =(float)(MyApplication.mUser.getBalance()/100);
        tv_ketq.setText("¥ "+aFloat+"元");
        card_et.addTextChangedListener(watcher);
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_back,R.id.button})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button:
                initView();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * The N.
     */
    private int n = 0;

    /**
     * Init view.
     */
    private void initView() {
        bankno = card_et.getText().toString().trim();
        name = name_et.getText().toString().trim();
        bankname = bang_et.getText().toString().trim();
        String string = money_et.getText().toString().trim();

        if(string!=null&&!string.isEmpty()){
            n = (int) (Float.valueOf(string)*100);
        }

        amount = n+"";
        if (bankno == null || "".equals(bankno) || !matchLuhn(bankno)){
            Toast.makeText(this,"请填入正确银行卡号码",Toast.LENGTH_SHORT).show();
        }else if (name == null || "".equals(name)){
            Toast.makeText(this,"请填入持卡人姓名",Toast.LENGTH_SHORT).show();
        }else if (amount == null || "0".equals(amount)){
            Toast.makeText(this,"请输入提款金额",Toast.LENGTH_SHORT).show();
        }else if (bankname == null || "".equals(bankname)){
            Toast.makeText(this,"请输入发卡银行",Toast.LENGTH_SHORT).show();
        } else if (MyApplication.mUser.getBalance() < Float.parseFloat(amount)){
            Toast.makeText(this,"余额不足",Toast.LENGTH_SHORT).show();
        }else {
            showAlertDialog();
        }
    }
    //四个数字空一个格
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString().trim().replace(" ", "");
            String result = "";
            if (str.length() >= 4) {
                card_et.removeTextChangedListener(watcher);
                for (int i = 0; i < str.length(); i++) {
                    result += str.charAt(i);
                    if ((i + 1) % 4 == 0) {
                        result += " ";
                    }
                }
                if (result.endsWith(" ")) {
                    result = result.substring(0, result.length() - 1);
                }
                card_et.setText(result);
                card_et.addTextChangedListener(watcher);
                card_et.setSelection(card_et.getText().toString().length());//焦点到输入框最后位置
            }
        }
    };
    /**
     * 匹配Luhn算法：可用于检测银行卡卡号
     * @param card
     * @return
     */
    public static boolean matchLuhn(String card) {
        String cardNo = card.replaceAll(" ","");
        int[] cardNoArr = new int[cardNo.length()];
        for (int i = 0; i < cardNo.length(); i++) {
            cardNoArr[i] = Integer.valueOf(String.valueOf(cardNo.charAt(i)));
        }
        for (int i = cardNoArr.length - 2; i >= 0; i -= 2) {
            cardNoArr[i] <<= 1;
            cardNoArr[i] = cardNoArr[i] / 10 + cardNoArr[i] % 10;
        }
        int sum = 0;
        for (int i = 0; i < cardNoArr.length; i++) {
            sum += cardNoArr[i];
        }
        return sum % 10 == 0;
    }

    /**
     * Show alert dialog.
     */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确认提现信息");
        builder.setCancelable(false);//设置点击对话框外部不关闭对话框
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initData();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    /**
     * Init data.
     */
    private void initData() {
       // UiUtils.startnet(this);
        ServeManager.getBankWithdraw(this, MyApplication.mUser.getUid(),name,bankno,amount,bankname).enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if (response.body() != null)
                    Log.e("WithdrawActivity","Result:"+response.body().getResult()+"  Msg:"+response.body().getMsg());
                if (response.body() != null&&"01".equals(response.body().getResult())){
                    newMuser();

                    Intent intent = new Intent(WithdrawActivity.this,WithdrawFeedBackActivity.class);
                    intent.putExtra("amount",amount);
                    Toast.makeText(WithdrawActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(WithdrawActivity.this,"网络加载失败",Toast.LENGTH_SHORT).show();
                }
                //UiUtils.endnet();
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(WithdrawActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity","onFailure:"+t.getLocalizedMessage());
               // UiUtils.endnet();
            }
        });
    }

    /**
     * New muser.
     */
    private void newMuser() {
        ServeManager.getUser(this, MyApplication.mUser.getUid() + "").enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null) {
                    Log.e("WithdrawActivity", "Result:" + response.body().getResult() + "  Msg:" + response.body().getMsg());
                }
                if (response.body() != null && "01".equals(response.body().getResult())) {
                    MyApplication.mUser = response.body().getUser();
                } else {
                    Toast.makeText(MyApplication.getContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
