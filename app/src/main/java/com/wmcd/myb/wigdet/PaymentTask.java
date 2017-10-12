package com.wmcd.myb.wigdet;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.wmcd.myb.model.ChargeJsonModel;
import com.wmcd.myb.model.ChargeModel;
import com.wmcd.myb.model.PaymentRequest;
import com.wmcd.myb.net.ServeManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/8.
 */
public class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {
    /**
     * 银联支付渠道
     */
    public static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    public static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付宝支付渠道
     */
    public static final String CHANNEL_ALIPAY = "alipay";
    /**
     * The View.
     */
    private View view;
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Charge json model.
     */
    private ChargeJsonModel chargeJsonModel;

    /**
     * Instantiates a new Payment task.
     *
     * @param context the context
     * @param view    the view
     */
    public PaymentTask(Context context ,View view){
        this.view = view;
        this.context = context;
    }

    /**
     * On pre execute.
     */
    @Override
    protected void onPreExecute() {
        view.setOnClickListener(null);
    }

    /**
     * Get charge json model charge json model.
     *
     * @return the charge json model
     */
    public ChargeJsonModel getChargeJsonModel(){
            return chargeJsonModel;
    }

    /**
     * Do in background string.
     *
     * @param pr the pr
     * @return the string
     */
    @Override
    protected String doInBackground(PaymentRequest... pr) {
        Log.e("PaymentTask", pr[0].getAmount()+pr[0].getChannel()+pr[0].getSubject()+ pr[0].getBody()+ pr[0].getUid()+ pr[0].getMid()+ pr[0].getBeginTime()+pr[0].getEndTime());
        ServeManager.getCharge(context, pr[0].getAmount(), pr[0].getChannel(),"[美易宝]购买"+pr[0].getSubject(), pr[0].getBody(), pr[0].getUid(), pr[0].getMid(), pr[0].getBeginTime(), pr[0].getEndTime(),pr[0].getInvitationCode()).enqueue(new Callback<ChargeModel>() {
            @Override
            public void onResponse(Call<ChargeModel> call, Response<ChargeModel> response) {
                if (response.body() != null)
                    Log.e("PaymenTask","Result:"+response.body().getResult()+"  Msg:"+response.body().getMsg());
                if (response.body()!= null&&"01".equals(response.body().getResult())) {
                    String data = response.body().getCharge();
                    Log.e("asdsadsad",data);
                    if (null == data) {
                        return;
                    }
                     Gson gson = new Gson();
                     chargeJsonModel = gson.fromJson(data, ChargeJsonModel.class);
                    Pingpp.createPayment((Activity) context, data);
                }else if (response.body()!= null&&"09".equals(response.body().getResult())){
                    Toast.makeText(context,"邀请码错误",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context,"网络加载失败,错误码："+response.body().getResult(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChargeModel> call, Throwable t) {
                Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                Log.e("PaymenTask","onFailure:"+t.getLocalizedMessage());
            }
        });

        return null;
    }

    /**
     * 获得服务端的charge，调用ping++ sdk。
     *
     * @param data the data
     */
    @Override
    protected void onPostExecute(String data) {
    }
}
