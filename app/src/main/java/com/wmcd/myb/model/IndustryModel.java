package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class IndustryModel {

    /**
     * result : 01
     * msg : 成功！
     * list : [{"industryid":1,"industryname":"美业","status":0},{"industryid":2,"industryname":"餐饮","status":0}]
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
         * industryid : 1
         * industryname : 美业
         * status : 0
         */
private  boolean isselect;

        public boolean isselect() {
            return isselect;
        }

        public void setIsselect(boolean isselect) {
            this.isselect = isselect;
        }

        private int industryid;
        private String industryname;
        private int status;

        public int getIndustryid() {
            return industryid;
        }

        public void setIndustryid(int industryid) {
            this.industryid = industryid;
        }

        public String getIndustryname() {
            return industryname;
        }

        public void setIndustryname(String industryname) {
            this.industryname = industryname;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
