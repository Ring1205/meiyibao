package com.wmcd.myb.model;

/**
 * Created by Administrator on 2017/3/8.
 */
public class PaymentRequest {
    String amount;
    String channel;
    String subject;
    String body;
    String uid;
    String mid;
    String beginTime;
    String endTime;
    String invitationCode;

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public PaymentRequest(String amount, String channel, String subject, String body, String uid, String mid, String beginTime, String endTime,String invitationCode) {
        this.amount = amount;
        this.channel = channel;
        this.subject = subject;
        this.body = body;
        this.uid = uid;
        this.mid = mid;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.invitationCode = invitationCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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
}
