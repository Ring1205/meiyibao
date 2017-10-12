package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class SeriesModel {


    /**
     * result : 01
     * msg : 成功！
     * list : [{"name":"立夏黄琳","icon":"f2e0ba1ffde2447ca072ede25074574a.png","width":7814,"description":"立夏","createdate":"2017-04-26 16:59:08","type":3,"sid":3,"height":23595,"status":0,"order":0},{"name":"立夏杨洋","icon":"b452a7160fd84cdf8a0573314729c936.jpg","width":3750,"description":"立夏","createdate":"2017-04-30 15:38:15","type":3,"sid":8,"height":22000,"status":0,"order":0}]
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

    public static class ListBean {
        /**
         * name : 立夏黄琳
         * icon : f2e0ba1ffde2447ca072ede25074574a.png
         * width : 7814
         * description : 立夏
         * createdate : 2017-04-26 16:59:08
         * type : 3
         * sid : 3
         * height : 23595
         * status : 0
         * order : 0
         */

        private String name;
        private String icon;
        private int width;
        private String description;
        private String createdate;
        private int type;
        private int sid;
        private int height;
        private int status;
        private int order;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
}
