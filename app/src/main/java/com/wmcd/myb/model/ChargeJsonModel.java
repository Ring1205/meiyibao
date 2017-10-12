package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ChargeJsonModel {
    /**
     * id : ch_TmzTW1efX5SCa9qjPSebrbDC
     * object : charge
     * created : 1488971024
     * livemode : true
     * paid : false
     * refunded : false
     * app : app_1SGeLGzHe9yLuzjX
     * channel : alipay
     * order_no : 1488970932414803401
     * client_ip : 192.168.1.153
     * amount : 1
     * amount_settle : 1
     * currency : cny
     * subject : subject
     * body : body
     * time_paid : null
     * time_expire : 1489057424
     * time_settle : null
     * transaction_no : null
     * refunds : {"object":"list","url":"/v1/charges/ch_TmzTW1efX5SCa9qjPSebrbDC/refunds","has_more":false,"data":[]}
     * amount_refunded : 0
     * failure_code : null
     * failure_msg : null
     * metadata : {"uid":"1","mid":"1","beginTime":"20170308190344","endTime":"20180308190344","invitationCode":""}
     * credential : {"object":"credential","alipay":{"orderInfo":"service=\"mobile.securitypay.pay\"&_input_charset=\"utf-8\"&notify_url=\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Fcharges%2Fch_TmzTW1efX5SCa9qjPSebrbDC\"&partner=\"2088421908758907\"&out_trade_no=\"1488970932414803401\"&subject=\"subject\"&body=\"body ch_TmzTW1efX5SCa9qjPSebrbDC\"&total_fee=\"0.01\"&payment_type=\"1\"&seller_id=\"2088421908758907\"&it_b_pay=\"2017-03-09 19:03:44\"&sign=\"1vGzbvKSg0Vpv5O5KmttypH709ROJHZqeYaYJJ%2F5lcGmhD5vVs6KST6rdkle%2FPPwCKubqWAboGPFbR0ZfCSEbsddFp7gJMuVSLw4cz1fgWP2aW9QihLKL01eO25Ij5%2B2C9wWrIq%2Bay4VN9Yy%2B%2BrvevzaH6A07Rqk2A7qMBV3PCE%3D\"&sign_type=\"RSA\""}}
     * extra : {}
     * description : null
     */

    private String id;
    private String object;
    private int created;
    private boolean livemode;
    private boolean paid;
    private boolean refunded;
    private String app;
    private String channel;
    private String order_no;
    private String client_ip;
    private int amount;
    private int amount_settle;
    private String currency;
    private String subject;
    private String body;
    private Object time_paid;
    private int time_expire;
    private Object time_settle;
    private Object transaction_no;
    private RefundsBean refunds;
    private int amount_refunded;
    private Object failure_code;
    private Object failure_msg;
    private MetadataBean metadata;
    private CredentialBean credential;
    private ExtraBean extra;
    private Object description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_settle() {
        return amount_settle;
    }

    public void setAmount_settle(int amount_settle) {
        this.amount_settle = amount_settle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getTime_paid() {
        return time_paid;
    }

    public void setTime_paid(Object time_paid) {
        this.time_paid = time_paid;
    }

    public int getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(int time_expire) {
        this.time_expire = time_expire;
    }

    public Object getTime_settle() {
        return time_settle;
    }

    public void setTime_settle(Object time_settle) {
        this.time_settle = time_settle;
    }

    public Object getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(Object transaction_no) {
        this.transaction_no = transaction_no;
    }

    public RefundsBean getRefunds() {
        return refunds;
    }

    public void setRefunds(RefundsBean refunds) {
        this.refunds = refunds;
    }

    public int getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(int amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public Object getFailure_code() {
        return failure_code;
    }

    public void setFailure_code(Object failure_code) {
        this.failure_code = failure_code;
    }

    public Object getFailure_msg() {
        return failure_msg;
    }

    public void setFailure_msg(Object failure_msg) {
        this.failure_msg = failure_msg;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public CredentialBean getCredential() {
        return credential;
    }

    public void setCredential(CredentialBean credential) {
        this.credential = credential;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public static class RefundsBean {
        /**
         * object : list
         * url : /v1/charges/ch_TmzTW1efX5SCa9qjPSebrbDC/refunds
         * has_more : false
         * data : []
         */

        private String object;
        private String url;
        private boolean has_more;
        private List<?> data;

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }

    public static class MetadataBean {
        /**
         * uid : 1
         * mid : 1
         * beginTime : 20170308190344
         * endTime : 20180308190344
         * invitationCode :
         */

        private String uid;
        private String mid;
        private String beginTime;
        private String endTime;
        private String invitationCode;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
        }
    }

    public static class CredentialBean {
        public static class AlipayBean {
        }
    }

    public static class ExtraBean {
    }
}
