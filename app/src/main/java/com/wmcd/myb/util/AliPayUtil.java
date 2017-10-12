package com.wmcd.myb.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by 邓志宏 on 2016/4/6.
 */
public class AliPayUtil {
    /**
     * The constant SDK_PAY_FLAG.
     */
    private static final int SDK_PAY_FLAG = 1;
    /**
     * The M handler.
     */
    private Handler mHandler;
    /**
     * The Context.
     */
    private Activity context;

    /**
     * Instantiates a new Ali pay util.
     *
     * @param mHandler the m handler
     * @param context  the context
     */
    public AliPayUtil(Handler mHandler, Activity context) {
        this.mHandler = mHandler;
        this.context = context;
    }

    /**
     * 你的服务器接受微信支付回调的网页，获取支付结果
     */
    private String url_order_up;
    /**
     * 支付金额
     */
    private double money;
    /**
     * 商品信息，标题
     */
    private String title;
    /**
     * 用户的ip
     */
    private String user_ip;
    /**
     * The constant PARTNER.
     */
// 商户PID 2088221608518573
    public static final String PARTNER = "2088421908758907";
    /**
     * The constant SELLER.
     */
// 商户收款账号  157238907@qq.com
    public static final String SELLER = "xufeng@maizheqiye.com";
    /**
     * The constant RSA_PRIVATE.
     */
// 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE
            ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK8eC+4gaUrmPI/G131H4EA02SH+LFsjOLt53wYZ/1Qczzm8gbwg8AI3vAgIxYPw9QXn6iI+YbA7cR5W1wtUD8WCA4myxN/vG1Jc7y7vJ4rKSZ6lds/Oqnfr163TE5cdXFuiHp6WkcDjGunQwwLQaDoshFy/O1yB7uJAz6XVsiZPAgMBAAECgYBL+2mJxR5qlUOpzskpXsp6kXO1QCfW042GKvcOGrGiuW1gy94u1svM6on3onaxLRfjnCEHArI4WVJXq65bB/pfLKYnuRg2l3rL2vTeQfFQ8OY1ErABAk/foshTrGVNIqPyUNdDeYuYzOhUB2xsBfZ0LvAASbMTrS2fw2qUVJZOgQJBANhbF+5UJ0QpVKOe11nQRokGjDk6aqWO0uIBnSn0o/UwoIN4lTHd89J9mLeiVfqGZWWRPnFwZqlq4jEVV2yHzL8CQQDPNIXLyX2TtLT3bP7mVTAkGdukeZR+aMxNW0hTn2wU0sai1GEuHlXmpuMfrXInEF+oaTRTvYQV8Cr6EHI2grpxAkEAwxAGGHM438RfshFQ2YHlRj9oB2S6qD+HtAp+d0hA1pwb68y4vrf8z0c7fDzZRcEMBDGaWc5cUNyzyEsUgh5BowJAVdn7Pc6EW3jaKlJc4u6U/cBBA0rkveOKEtspgoxhaddTOhApG8Sx7tli7bMdwxXDrLG6Xp/9ZGGYTPgXeoJ74QJAYzY+7/lhfAkaj7wOLcYlUt1hkKOXza8u/dZl4Zk8aDLiPvbUbcSZe/UZe+etMLXuVqj3UWCxJlCpKiFiWei0PA==";
//    // 支付宝公钥
//    public static final String RSA_PUBLIC = "1";
    /**
     * 自己的订单号
     */
    private String payNum;

    /**
     * Instantiates a new Ali pay util.
     *
     * @param payNum       the pay num
     * @param context      the context
     * @param url_order_up the url order up
     * @param money        the money
     * @param title        the title
     * @param user_ip      the user ip
     * @param handler      the handler
     */
    public AliPayUtil(String payNum, Activity context, String url_order_up, double money, String title, String user_ip, Handler handler) {
        this.payNum = payNum;
        this.context = context;
        this.url_order_up = url_order_up;
        this.money = money;
        this.title = title;
        this.user_ip = user_ip;
        mHandler = handler;
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void aliPay() {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            context.finish();
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo(title, title, money + "");
        //Logger.e(orderInfo);
        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        //Logger.e(sign + "");
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        //Logger.e(payInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     *
     * @param subject the subject
     * @param body    the body
     * @param price   the price
     * @return the order info
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + payNum + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + url_order_up + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     * @return the out trade no
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     * @return the string
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     * @return the sign type
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
