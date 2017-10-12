package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by 邓志宏 on 2017/2/22.
 */

public class ClassifyModel {

    /**
     * result : 01
     * msg : 成功！
     * list : [{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a35649c8711b4998a9ea96a1fa918968","description":"熙雯","createdate":"2017-03-31 17:22:41","viewtype":1,"mname":"合伙人会员","type":3,"tid":158,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像27","width":700,"listtype":2,"useCount":288,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"11b22b7b978b44dc81507b94e2f37c62","description":"曼文","createdate":"2017-04-01 17:15:34","viewtype":1,"mname":"合伙人会员","type":3,"tid":139,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像18","width":700,"listtype":2,"useCount":193,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"3856c8d68da84423bfd5c89c7b25b44b","description":"凌萱","createdate":"2017-03-23 17:10:11","viewtype":1,"mname":"合伙人会员","type":3,"tid":147,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像22","width":700,"listtype":2,"useCount":108,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a6d60af7a60f4a9abe2b18273455a041","description":"HY-TX-04.14.10","createdate":"2017-04-24 13:56:52","viewtype":1,"mname":"合伙人会员","type":3,"tid":337,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.10","width":700,"listtype":2,"useCount":78,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a84667f2319b409881a69de2fed4c1bb","description":"HY-TX-04.14.08","createdate":"2017-04-24 11:38:27","viewtype":1,"mname":"合伙人会员","type":3,"tid":335,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.08","width":700,"listtype":2,"useCount":71,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"f2ec87de1fa14dc98474169bae1cdcc0","description":"个人头像","createdate":"2017-03-20 13:44:18","viewtype":1,"mname":"免费","type":3,"tid":14,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"","name":"头像1","width":700,"listtype":2,"useCount":67,"open":1,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"eddeaefb0d8d4ca6a42668b90eb3b97b","description":"财富论坛2","createdate":"2017-03-10 23:29:48","viewtype":1,"mname":"合伙人会员","type":3,"tid":218,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"财富论坛2","width":1181,"listtype":2,"useCount":62,"open":0,"height":1181,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"81babc4e6b3c40cc9f51650a7cf8d232","description":"笑笑","createdate":"2017-03-30 19:34:07","viewtype":1,"mname":"合伙人会员","type":3,"tid":206,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像35","width":1181,"listtype":2,"useCount":54,"open":0,"height":1181,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"6d49fc5ba31a4000beb5a89a7679cb12","description":"HY-TX-04.14.09","createdate":"2017-04-24 13:46:27","viewtype":1,"mname":"合伙人会员","type":3,"tid":336,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.09","width":700,"listtype":2,"useCount":44,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"fefc13a83e344364babe24b65aa827a9","description":"美梦成真","createdate":"2017-03-23 00:03:46","viewtype":1,"mname":"合伙人会员","type":3,"tid":220,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"美梦成真","width":2953,"listtype":2,"useCount":40,"open":0,"height":2953,"order":0,"status":0}]
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
         * owner : 0
         * isbuy : 0
         * buyCount : 0
         * browseCount : 0
         * icon : a35649c8711b4998a9ea96a1fa918968
         * description : 熙雯
         * createdate : 2017-03-31 17:22:41
         * viewtype : 1
         * mname : 合伙人会员
         * type : 3
         * tid : 158
         * sid : null
         * isup : 1
         * price : 0
         * swiperOrder : 0
         * micon : a72485dce2df5a87af4d1ec9edeab110.png
         * name : 头像27
         * width : 700
         * listtype : 2
         * useCount : 288
         * open : 0
         * height : 700
         * order : 0
         * status : 0
         */

        private int owner;
        private int isbuy;
        private int buyCount;
        private int browseCount;
        private String icon;
        private String description;
        private String createdate;
        private int viewtype;
        private String mname;
        private int type;
        private int tid;
        private Object sid;
        private int isup;
        private int price;
        private int swiperOrder;
        private String micon;
        private String name;
        private int width;
        private int listtype;
        private int useCount;
        private int open;
        private int height;
        private int order;
        private int status;

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
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

        public int getIsup() {
            return isup;
        }

        public void setIsup(int isup) {
            this.isup = isup;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
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
    }
}
