package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by 邓志宏 on 2017/2/21.
 */

public class TemplateByUserModel {
    /**
     * result : 01
     * msg : 成功！
     * list : [{"hid":null,"buyCount":0,"icon":"8a17968f62e85c1c81d8a9564aeff0e6","createdate":"2017-02-12 14:36:35","type":3,"price":0,"name":"头像","width":235,"id":1,"useCount":0,"desc":"头像","height":235,"order":0,"status":0}]
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
         * hid : null
         * buyCount : 0
         * icon : 8a17968f62e85c1c81d8a9564aeff0e6
         * createdate : 2017-02-12 14:36:35
         * type : 3
         * price : 0
         * name : 头像
         * width : 235
         * id : 1
         * useCount : 0
         * desc : 头像
         * height : 235
         * order : 0
         * status : 0
         */

        private Object hid;
        private int buyCount;
        private String icon;
        private String createdate;
        private int type;
        private int price;
        private String name;
        private int width;
        private int tid;
        private int useCount;
        private String desc;
        private int height;
        private int order;
        private int status;

        public Object getHid() {
            return hid;
        }

        public void setHid(Object hid) {
            this.hid = hid;
        }

        public int getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(int buyCount) {
            this.buyCount = buyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getUseCount() {
            return useCount;
        }

        public void setUseCount(int useCount) {
            this.useCount = useCount;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "hid=" + hid +
                    ", buyCount=" + buyCount +
                    ", icon='" + icon + '\'' +
                    ", createdate='" + createdate + '\'' +
                    ", type=" + type +
                    ", price=" + price +
                    ", name='" + name + '\'' +
                    ", width=" + width +
                    ", tid=" + tid +
                    ", useCount=" + useCount +
                    ", desc='" + desc + '\'' +
                    ", height=" + height +
                    ", order=" + order +
                    ", status=" + status +
                    '}';
        }
    }
}
