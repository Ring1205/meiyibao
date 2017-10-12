package com.wmcd.myb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邓志宏 on 2017/2/21.
 */

public class AccountModel {

    /**
     * result : 01
     * msg : 成功！
     * list : [{"hid":null,"buyCount":0,"icon":"8a17968f62e85c1c81d8a9564aeff0e6","createdate":"2017-02-12 14:36:35","type":3,"price":0,"name":"头像","width":235,"id":1,"useCount":0,"desc":"头像","height":235,"order":0,"status":0}]
     */

    private String result;
    private String msg;
    private List<ListAccountBean> list;

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

    public List<ListAccountBean> getList() {
        return list == null ? new ArrayList<ListAccountBean>() : list;
    }

    public void setList(List<ListAccountBean> list) {
        this.list = list;
    }

    public static class ListAccountBean {
        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getInvitationid() {
            return invitationid;
        }

        public void setInvitationid(String invitationid) {
            this.invitationid = invitationid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getSubuid() {
            return subuid;
        }

        public void setSubuid(String subuid) {
            this.subuid = subuid;
        }

        public String getIconR() {
            return iconR == null ? "" : iconR;
        }

        public void setIconR(String iconR) {
            this.iconR = iconR;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        private String uid;

        private String amount;
        private String invitationid;
        private String name;
        private String createdate;
        private String commission;
        private String subuid;
        private String iconR;

        private String mname;//会员名称

    }
}
