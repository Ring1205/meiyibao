package com.wmcd.myb.model;

/**
 * Created by Administrator on 2017/3/9.
 */

public class FeedbackModel {
    /**
     * result : 01
     * msg : 成功！
     * orderMember : {"uid":10,"amount":2,"code":"1489045778881924935","subject":"[美易宝]购买铂金会员","channel":"wx","mid":2,"endtime":"2017-04-09 12:03:03","createdate":"2017-03-09 15:51:20","remark":"body","begintime":"2017-03-09 12:03:03","id":8}
     */

    private String result;
    private String msg;
    private OrderMemberBean orderMember;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OrderMemberBean getOrderMember() {
        return orderMember;
    }

    public void setOrderMember(OrderMemberBean orderMember) {
        this.orderMember = orderMember;
    }

    public static class OrderMemberBean {
        /**
         * uid : 10
         * amount : 2
         * code : 1489045778881924935
         * subject : [美易宝]购买铂金会员
         * channel : wx
         * mid : 2
         * endtime : 2017-04-09 12:03:03
         * createdate : 2017-03-09 15:51:20
         * remark : body
         * begintime : 2017-03-09 12:03:03
         * omid : 8
         */

        private int uid;
        private int amount;
        private String code;
        private String subject;
        private String channel;
        private int mid;
        private String endtime;
        private String createdate;
        private String remark;
        private String begintime;
        private int omid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public int getOmid() {
            return omid;
        }

        public void setOmid(int omid) {
            this.omid = omid;
        }
    }
}
