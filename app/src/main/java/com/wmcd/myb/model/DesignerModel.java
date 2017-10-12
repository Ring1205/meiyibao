package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class DesignerModel {
    /**
     * result : 01
     * msg : 成功！
     * list : [{"icon":"ded91a18a60e49738a1bc97b86bd020b","description":"创意招聘","createdate":"2017-07-20 14:36:00","viewtype":1,"mname":"合伙人会员","type":4,"tid":822,"sid":null,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","listtype":2,"useCount":0,"height":210,"emailCount":0,"order":0,"owner":"17674349877","isbuy":1,"buyCount":0,"browseCount":0,"makeCount":0,"isup":1,"name":"招聘海报","width":285,"open":0,"status":2}]
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
         * icon : ded91a18a60e49738a1bc97b86bd020b
         * description : 创意招聘
         * createdate : 2017-07-20 14:36:00
         * viewtype : 1
         * mname : 合伙人会员
         * type : 4
         * tid : 822
         * sid : null
         * price : null
         * swiperOrder : 0
         * micon : a72485dce2df5a87af4d1ec9edeab110.png
         * listtype : 2
         * useCount : 0
         * height : 210
         * emailCount : 0
         * order : 0
         * owner : 17674349877
         * isbuy : 1
         * buyCount : 0
         * browseCount : 0
         * makeCount : 0
         * isup : 1
         * name : 招聘海报
         * width : 285
         * open : 0
         * status : 2
         */

        private String icon;
        private String description;
        private String createdate;
        private int viewtype;
        private String mname;
        private int type;
        private int tid;
        private Object sid;
        private Object price;
        private int swiperOrder;
        private String micon;
        private int listtype;
        private int useCount;
        private int height;
        private int emailCount;
        private int order;
        private String owner;
        private int isbuy;
        private int buyCount;
        private int browseCount;
        private int makeCount;
        private int isup;
        private String name;
        private int width;
        private int open;
        private int status;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

        public int getViewtype() {
            return viewtype;
        }

        public void setViewtype(int viewtype) {
            this.viewtype = viewtype;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public Object getSid() {
            return sid;
        }

        public void setSid(Object sid) {
            this.sid = sid;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public int getSwiperOrder() {
            return swiperOrder;
        }

        public void setSwiperOrder(int swiperOrder) {
            this.swiperOrder = swiperOrder;
        }

        public String getMicon() {
            return micon;
        }

        public void setMicon(String micon) {
            this.micon = micon;
        }

        public int getListtype() {
            return listtype;
        }

        public void setListtype(int listtype) {
            this.listtype = listtype;
        }

        public int getUseCount() {
            return useCount;
        }

        public void setUseCount(int useCount) {
            this.useCount = useCount;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getEmailCount() {
            return emailCount;
        }

        public void setEmailCount(int emailCount) {
            this.emailCount = emailCount;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public int getIsbuy() {
            return isbuy;
        }

        public void setIsbuy(int isbuy) {
            this.isbuy = isbuy;
        }

        public int getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(int buyCount) {
            this.buyCount = buyCount;
        }

        public int getBrowseCount() {
            return browseCount;
        }

        public void setBrowseCount(int browseCount) {
            this.browseCount = browseCount;
        }

        public int getMakeCount() {
            return makeCount;
        }

        public void setMakeCount(int makeCount) {
            this.makeCount = makeCount;
        }

        public int getIsup() {
            return isup;
        }

        public void setIsup(int isup) {
            this.isup = isup;
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

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
