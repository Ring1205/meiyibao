package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by 邓志宏 on 2017/2/17.
 */

public class HomeModel {
    /**
     * result : 01
     * msg : 成功！
     * onlineList : [{"owner":0,"buyCount":0,"browseCount":0,"icon":"f2ec87de1fa14dc98474169bae1cdcc0","description":"个人头像","createdate":"2017-03-20 13:44:18","viewtype":1,"type":3,"tid":14,"name":"头像","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"ba1062ee125145e18734ab8e3b2b7575","description":"个人头像","createdate":"2017-03-20 13:44:19","viewtype":1,"type":3,"tid":15,"name":"头像2","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"7421e4d35bee41b5a45b69a3faec1dc9","description":"个人头像","createdate":"2017-03-20 13:44:20","viewtype":1,"type":3,"tid":16,"name":"头像3","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"ce67eb085152417ba3788d2b9170f273","description":"活动海报","createdate":"2017-03-20 11:40:09","viewtype":1,"type":4,"tid":4,"name":"立夏","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"ede9d97bb32a45d5b909ea31e58f9106","description":"活动海报","createdate":"2017-03-20 11:40:09","viewtype":1,"type":4,"tid":5,"name":"六一","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"964a11ef9148484a9f80cdf34affbcbd","description":"活动海报","createdate":"2017-03-20 11:40:10","viewtype":1,"type":4,"tid":6,"name":"母亲节","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":null,"description":"画册","createdate":"2017-03-20 13:57:02","viewtype":2,"type":5,"tid":32,"name":"画册1","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":null,"description":"画册","createdate":"2017-03-20 13:57:03","viewtype":2,"type":5,"tid":33,"name":"画册2","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":null,"description":"画册","createdate":"2017-03-20 13:57:05","viewtype":2,"type":5,"tid":34,"name":"画册3","listtype":2,"useCount":0,"order":0,"status":0}]
     * offlineList : [{"owner":0,"buyCount":0,"browseCount":0,"icon":"073bd2c280eb456ab2c2f91af8754011","description":"易拉宝活动","createdate":"2017-03-20 12:02:53","viewtype":1,"type":9,"tid":10,"name":"易拉宝活动1","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"eecc94562c7a43f1891c1d6ecb3f6958","description":"易拉宝活动","createdate":"2017-03-20 12:02:55","viewtype":1,"type":9,"tid":11,"name":"易拉宝活动2","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"61fa2aff8b5a4c1caf952747a1bab17b","description":"易拉宝活动","createdate":"2017-03-20 12:02:56","viewtype":1,"type":9,"tid":12,"name":"易拉宝活动3","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"87a74b6433e442e789f744e12e33df03","description":"活动单页反面","createdate":"2017-03-20 11:40:16","viewtype":1,"type":8,"tid":9,"name":"开业活动","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"032d462fdc6b4bba90923921188deb6a","description":"价格单","createdate":"2017-03-20 13:49:01","viewtype":1,"type":8,"tid":23,"name":"价格单1","listtype":2,"useCount":0,"order":0,"status":0},{"owner":0,"buyCount":0,"browseCount":0,"icon":"e4a9be2093e94a94a29302bb0517e4c0","description":"价格单","createdate":"2017-03-20 13:49:03","viewtype":1,"type":8,"tid":24,"name":"价格单2","listtype":2,"useCount":0,"order":0,"status":0}]
     * adList : [{"img":"8d3f36413bcc5a04a0901121c63eddb5.png","adid":1,"objId":null,"type":"homePage","title":"okokko","url":"www.baidu.com","target":1,"status":0},{"img":"431ca6d99e4457ccac6a819293dfde1e.jpg","adid":2,"objId":1,"type":"homePage","title":"mb","url":"","target":2,"status":0},{"img":"ad836feea96c5f04920d9da16e421c3a.jpg","adid":3,"objId":3,"type":"homePage","title":"hy","url":null,"target":2,"status":0}]
     */

    private String result;
    private String msg;
    private List<OnlineListBean> onlineList;//
    private List<OfflineListBean> offlineList;
    private List<AdListBean> adList;
    private List<OnlineListBean> headList;
    private List<OnlineListBean> posterList;
    private List<OnlineListBean> moreList;
    public List<OnlineListBean> getHeadList() {
        return headList;
    }

    public void setHeadList(List<OnlineListBean> headList) {
        this.headList = headList;
    }

    public List<OnlineListBean> getPosterList() {
        return posterList;
    }

    public void setPosterList(List<OnlineListBean> posterList) {
        this.posterList = posterList;
    }

    public List<OnlineListBean> getMoreList() {
        return moreList;
    }

    public void setMoreList(List<OnlineListBean> moreList) {
        this.moreList = moreList;
    }


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

    public List<OnlineListBean> getOnlineList() {
        return onlineList;
    }

    public void setOnlineList(List<OnlineListBean> onlineList) {
        this.onlineList = onlineList;
    }

    public List<OfflineListBean> getOfflineList() {
        return offlineList;
    }

    public void setOfflineList(List<OfflineListBean> offlineList) {
        this.offlineList = offlineList;
    }

    public List<AdListBean> getAdList() {
        return adList;
    }

    public void setAdList(List<AdListBean> adList) {
        this.adList = adList;
    }

    public static class OnlineListBean {
        /**
         * owner : 0
         * buyCount : 0
         * browseCount : 0
         * icon : f2ec87de1fa14dc98474169bae1cdcc0
         * description : 个人头像
         * createdate : 2017-03-20 13:44:18
         * viewtype : 1
         * type : 3
         * tid : 14
         * name : 头像
         * listtype : 2
         * useCount : 0
         * order : 0
         * status : 0
         */

        private int owner;
        private int buyCount;
        private int browseCount;
        private String icon;
        private String description;
        private String createdate;
        private int viewtype;
        private int type;
        private int tid;
        private String name;
        private int listtype;
        private int useCount;
        private int order;
        private int status;

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
            this.owner = owner;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    public static class OfflineListBean {
        /**
         * owner : 0
         * buyCount : 0
         * browseCount : 0
         * icon : 073bd2c280eb456ab2c2f91af8754011
         * description : 易拉宝活动
         * createdate : 2017-03-20 12:02:53
         * viewtype : 1
         * type : 9
         * tid : 10
         * name : 易拉宝活动1
         * listtype : 2
         * useCount : 0
         * order : 0
         * status : 0
         */

        private int owner;
        private int buyCount;
        private int browseCount;
        private String icon;
        private String description;
        private String createdate;
        private int viewtype;
        private int type;
        private int tid;
        private String name;
        private int listtype;
        private int useCount;
        private int order;
        private int status;

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
            this.owner = owner;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    public static class AdListBean {
        /**
         * img : 8d3f36413bcc5a04a0901121c63eddb5.png
         * adid : 1
         * objId : null
         * type : homePage
         * title : okokko
         * url : www.baidu.com
         * target : 1 ;网页
         * 2 ;预览
         * 3 ;购买会员
         * 4 ;提现
         * status : 0
         */

        private String img;
        private int adid;
        private Object objId;
        private String type;
        private String title;
        private String url;
        private int target;
        private int status;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getAdid() {
            return adid;
        }

        public void setAdid(int adid) {
            this.adid = adid;
        }

        public Object getObjId() {
            return objId;
        }

        public void setObjId(Object objId) {
            this.objId = objId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
