package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class QueryAdModel {
    /**
     * result : 01
     * msg : 成功！
     * adList : [{"img":"8d3f36413bcc5a04a0901121c63eddb5.png","adid":1,"objId":null,"type":"homePage","title":"okokko","url":"www.baidu.com","target":1,"status":0},{"img":"431ca6d99e4457ccac6a819293dfde1e.jpg","adid":2,"objId":1,"type":"homePage","title":"mb","url":"","target":2,"status":0},{"img":"ad836feea96c5f04920d9da16e421c3a.jpg","adid":3,"objId":3,"type":"homePage","title":"hy","url":null,"target":2,"status":0}]
     */

    private String result;
    private String msg;
    private List<AdListBean> adList;

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

    public List<AdListBean> getAdList() {
        return adList;
    }

    public void setAdList(List<AdListBean> adList) {
        this.adList = adList;
    }

    public static class AdListBean {
        /**
         * img : 8d3f36413bcc5a04a0901121c63eddb5.png
         * adid : 1
         * objId : null
         * type : homePage
         * title : okokko
         * url : www.baidu.com
         * target : 1
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
