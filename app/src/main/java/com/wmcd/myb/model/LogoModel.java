package com.wmcd.myb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class LogoModel {
    /**
     * result : 01
     * msg : 成功！
     * list : [{"fid":null,"uid":1,"img":"9cb88b3dc087423eb0f95c48e9d70c3e","luid":1,"createdate":"2017-04-24 22:52:01","text":"美易宝","logoid":1,"status":0}]
     */

    private String result;
    private String msg;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * fid : null
         * uid : 1
         * img : 9cb88b3dc087423eb0f95c48e9d70c3e
         * luid : 1
         * createdate : 2017-04-24 22:52:01
         * text : 美易宝
         * logoid : 1
         * status : 0
         * width : 500
         * height : 500
         */

        private Object fid;
        private int uid;
        private String img;
        private int luid;
        private String createdate;
        private String text;
        private int logoid;
        private int status;
        private int width;
        private int height;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Object getFid() {
            return fid;
        }

        public void setFid(Object fid) {
            this.fid = fid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getLuid() {
            return luid;
        }

        public void setLuid(int luid) {
            this.luid = luid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getLogoid() {
            return logoid;
        }

        public void setLogoid(int logoid) {
            this.logoid = logoid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
