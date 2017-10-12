package com.wmcd.myb.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The type Wx pay util.
 */
public class WxPayUtil {
    /**
     * 你的服务器接受微信支付回调的网页，获取支付结果
     */
    private String url_order_up;
    /**
     * 支付金额
     */
    private int money;
    /**
     * 商品信息，标题
     */
    private String title;

    /**
     * Gets url order up.
     *
     * @return the url order up
     */
    public String getUrl_order_up() {
        return url_order_up;
    }

    /**
     * Sets url order up.
     *
     * @param url_order_up the url order up
     */
    public void setUrl_order_up(String url_order_up) {
        this.url_order_up = url_order_up;
    }

    /**
     * Gets money.
     *
     * @return the money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets money.
     *
     * @param money the money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets user ip.
     *
     * @return the user ip
     */
    public String getUser_ip() {
        return user_ip;
    }

    /**
     * Sets user ip.
     *
     * @param user_ip the user ip
     */
    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets pay num.
     *
     * @return the pay num
     */
    public String getPayNum() {
        return payNum;
    }

    /**
     * Sets pay num.
     *
     * @param payNum the pay num
     */
    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    /**
     * 用户的ip
     */
    private String user_ip;
    /**
     * 支付type 官网上去看有什么 安卓传入 APP
     */
    private String type;
    /**
     * 微信开放平台对应APP的id
     */
    public String APP_ID;
    /**
     * 微信开放平台商户平台给你的密钥
     */
    public String API_KEY;
    /**
     * 微信商户号
     */
    public String MCH_ID;
    /**
     * 保存取得预订单号的信息
     */
    private Map<String, String> resultunifiedorder;
    /**
     * 微信支付類
     */
    private PayReq req;
    /**
     * 商户订单号
     */
    private String payNum;
    /**
     * 微信
     */
    private IWXAPI msgApi;
    /**
     * The Context.
     */
    private Context context;

    /**
     * 传入所需要的参数
     *
     * @param aPP_ID  APP_ID
     * @param aPI_KEY aPI_KEY
     * @param mCH_ID  MCH_ID
     * @param context the context
     */
    public WxPayUtil(String aPP_ID, String aPI_KEY, String mCH_ID,
                     Context context) {
        super();
        APP_ID = aPP_ID;
        API_KEY = aPI_KEY;
        MCH_ID = mCH_ID;
        this.context = context;
        registApi();
    }

    /**
     * Instantiates a new Wx pay util.
     */
    public WxPayUtil() {
        super();
    }

    /**
     * 向微信注册你的APP，如果不註冊微信將拒絕
     */
    public void registApi() {
        if (context != null) {
            msgApi = WXAPIFactory.createWXAPI(context, APP_ID);
        }
    }

    /**
     * The type Get prepay id task.
     */
    private class GetPrepayIdTask extends
            AsyncTask<String, String, Map<String, String>> {

        /**
         * The Dialog.
         */
        private ProgressDialog dialog;
        /**
         * The Tag.
         */
        public boolean tag;

        /**
         * On pre execute.
         */
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "提示", "正在提交" +
                    "订单...");
        }

        /**
         * On post execute.
         *
         * @param result the result
         */
        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            resultunifiedorder = result;
            // 提交订单
            if (resultunifiedorder != null && tag) {
                sendPayReq();
            }
        }

        /**
         * On cancelled.
         */
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        /**
         * Do in background map.
         *
         * @param params the params
         * @return the map
         */
        @Override
        protected Map<String, String> doInBackground(String... params) {
            String requestXML = getRequestXml(getParams());
            Log.e("requestXML", requestXML);
            String result = httpsRequest(
                    "https://api.mch.weixin.qq.com/pay/unifiedorder", "POST",
                    requestXML);
            Map<String, String> xml = decodeXml(result);
            return xml;
        }
    }

    /**
     * Decode xml map.
     *
     * @param content the content
     * @return the map
     */
    public Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;
    }

    /**
     * Gets params.
     *
     * @return the params
     */
//统一下单参数
    private SortedMap getParams() {
        SortedMap<String, Object> parameterMap = new TreeMap<>();
        parameterMap.put("appid", APP_ID);
        parameterMap.put("mch_id", MCH_ID);
        parameterMap.put("nonce_str", genNonceStr());
        parameterMap.put("body", title);
        parameterMap.put("out_trade_no", payNum);
        parameterMap.put("total_fee", money + "");
        parameterMap.put("spbill_create_ip", "127.0.0.1");
        parameterMap.put("notify_url", url_order_up);
        parameterMap.put("trade_type", "APP");
        String sign = createSign(parameterMap);
        parameterMap.put("sign", sign);
        return parameterMap;
    }

    /**
     * Gen nonce str string.
     *
     * @return the string
     */
    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }

    /**
     * Gen time stamp long.
     *
     * @return the long
     */
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Gets request xml.
     *
     * @param parameters the parameters
     * @return the request xml
     */
//请求参数 转xml
    public static String getRequestXml(SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "" + value + "</" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * Create sign string.
     *
     * @param parameters the parameters
     * @return the string
     */
//生成签名
    public static String createSign(SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
//        sb.append("key=" + UrlConfig.API_KEY);
        String sign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return sign;
    }

    /**
     * Https request string.
     *
     * @param requestUrl    the request url
     * @param requestMethod the request method
     * @param outputStr     the output str
     * @return the string
     */
//请求方法
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            Log.e("err","连接超时：{}" + ce);
        } catch (Exception e) {
            Log.e("err","https请求异常：{}" + e);
        }
        return null;
    }

    /**
     * Gen pay req.
     */
    private void genPayReq() {
        if (resultunifiedorder == null)
            return;
        req = new PayReq();
        req.appId = APP_ID;
        req.partnerId = MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        SortedMap signParams = new TreeMap();
        signParams.put("appid", req.appId);
        signParams.put("noncestr", req.nonceStr);
        signParams.put("package", req.packageValue);
        signParams.put("partnerid", req.partnerId);
        signParams.put("prepayid", req.prepayId);
        signParams.put("timestamp", req.timeStamp);
        req.sign = createSign(signParams);

        Log.e("orionsignParams", signParams.toString());

    }

    /**
     * 开启订单
     *
     * @param isSend 是否直接发送订单，传false只获得预订单号
     */
    public void start(boolean isSend) {
        if (msgApi.isWXAppInstalled()) {
            GetPrepayIdTask gpt = new GetPrepayIdTask();
            gpt.execute();
            gpt.tag = isSend;
        } else {
            UiUtils.shortToast(context, "未安装微信");
        }
    }

    /**
     * 如果你之前start方法传入的flase调用该方法，提交之前未完成订单
     */
    public void sendPayReq() {
        if (resultunifiedorder == null) {
            return;
        }
        genPayReq();
        msgApi.sendReq(req);

    }


}
