package com.wmcd.myb.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.wmcd.myb.activity.LoginActivity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by 邓志宏 on 2017/2/15.
 */

public class LoginModel {
    /**
     * result : 01
     * msg : 成功！
     * user : {"loginId":null,"loginType":"1","icon":null,"createdate":"2017-02-12 17:04:45","mid":1,"mname":"黄金会员","iconR":"e67e0ee373334cb3a08f37ee41ab8462","mdesc":"享受部分模板使用权","periodDesc":"一年","balance":0,"phone":"15399909116","micon":"65b01885041550009983d1c7a75e2a87.png","name":"赵四","id":1,"beginTime":"2609-03-04 22:48:40","endTime":"2609-03-06 01:28:40","invitationCode":null,"status":0}
     */

    private String result;
    private String msg;
    private UserBean user;

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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * loginId : null
         * loginType : 1
         * icon : null
         * createdate : 2017-02-12 17:04:45
         * mid : 1
         * mname : 黄金会员
         * iconR : e67e0ee373334cb3a08f37ee41ab8462
         * mdesc : 享受部分模板使用权
         * periodDesc : 一年
         * balance : 0
         * phone : 15399909116
         * micon : 65b01885041550009983d1c7a75e2a87.png
         * name : 赵四
         * id : 1
         * invitationImg : 85041550009983d1c7a75e2a87.png
         * beginTime : 2609-03-04 22:48:40
         * endTime : 2609-03-06 01:28:40
         * invitationCode : null
         * status : 0
         * designer : null 非空为设计师
         */

        private Object loginId;
        private String loginType;
        private Object icon;
        private String createdate;
        private Integer mid;
        private String mname;
        private String iconR;
        private String mdesc;
        private String periodDesc;
        private Integer balance;
        private String phone;
        private String micon;
        private String name;
        private int uid;
        private String invitationImg;
        private String beginTime;
        private String endTime;
        private String invitationCode;
        private int status;
        private String address;
        private String shopname;
        private String industry;
        private String designer;

        public String getDesigner() {
            return designer;
        }

        public void setDesigner(String designer) {
            this.designer = designer;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getInvitationImg() {
            return invitationImg;
        }
        public void setInvitationImg(String invitationImg) {
            this.invitationImg = invitationImg;
        }
        public Object getLoginId() {
            return loginId;
        }

        public void setLoginId(Object loginId) {
            this.loginId = loginId;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public Integer getMid() {
            return mid;
        }

        public void setMid(Integer mid) {
            this.mid = mid;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getIconR() {
            return iconR;
        }

        public void setIconR(String iconR) {
            this.iconR = iconR;
        }

        public String getMdesc() {
            return mdesc;
        }

        public void setMdesc(String mdesc) {
            this.mdesc = mdesc;
        }

        public String getPeriodDesc() {
            return periodDesc;
        }

        public void setPeriodDesc(String periodDesc) {
            this.periodDesc = periodDesc;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMicon() {
            return micon;
        }

        public void setMicon(String micon) {
            this.micon = micon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public void logout(Context context) {
            SharedPreferences mySharedPreferences = context.getSharedPreferences("MakeupNet",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            try {
                ShareSDK.getPlatform(new LoginActivity(), Wechat.NAME).removeAccount(true);
            }catch (Exception e){
                e.printStackTrace();
            }
            editor.remove("User");
            editor.commit();
        }
    }
}
