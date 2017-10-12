package com.wmcd.myb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class SystemMsgBean implements Serializable{

    /**
     * result : 01
     * msg : 成功！
     * systemMessageUnreadCount : 1
     * list : [{"uid":1,"read":1,"subject":"11","msgid":3,"createdate":"2017-04-11 10:36:55","type":1,"body":"22","status":0},{"uid":1,"read":0,"subject":"标题","msgid":1,"createdate":"2017-04-11 10:17:34","type":1,"body":"内容","status":0}]
     * assetsMessageUnreadCount : 1
     */

    private String result;
    private String msg;
    private int systemMessageUnreadCount;
    private int assetsMessageUnreadCount;
    private List<ListBean> list;

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

    public int getSystemMessageUnreadCount() {
        return systemMessageUnreadCount;
    }

    public void setSystemMessageUnreadCount(int systemMessageUnreadCount) {
        this.systemMessageUnreadCount = systemMessageUnreadCount;
    }

    public int getAssetsMessageUnreadCount() {
        return assetsMessageUnreadCount;
    }

    public void setAssetsMessageUnreadCount(int assetsMessageUnreadCount) {
        this.assetsMessageUnreadCount = assetsMessageUnreadCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements  Serializable{
        /**
         * uid : 1
         * read : 1
         * subject : 11
         * msgid : 3
         * createdate : 2017-04-11 10:36:55
         * type : 1
         * body : 22
         * status : 0
         */

        private int uid;
        private int read;
        private String subject;
        private int msgid;
        private String createdate;
        private int type;
        private String body;
        private int status;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getMsgid() {
            return msgid;
        }

        public void setMsgid(int msgid) {
            this.msgid = msgid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
