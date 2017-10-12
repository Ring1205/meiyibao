package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class MemberModel {
    /**
     * result : 01
     * msg : 成功！
     * list : [{"rebatePermil":0,"period":365,"img":"9d0f62889f1b56e29d097fcd471a8fad.png","periodDesc":"一年","price":9900,"name":"黄金会员","icon":"65b01885041550009983d1c7a75e2a87.png","id":1,"priceDesc":"99元包年","desc":"享受部分模板使用权","status":0},{"rebatePermil":0,"period":365,"img":"7d94a2e5db765c50a66554d7750d8a44.png","periodDesc":"一年","price":19900,"name":"铂金会员","icon":"3f8ace3ee7fc5f9b9b2eaef32fecaf52.png","id":2,"priceDesc":"199元包年","desc":"享受所有模板使用权","status":0},{"rebatePermil":300,"period":365,"img":"5f6c1a45b9585569b7da02214029cb71.png","periodDesc":"一年","price":36500,"name":"合伙人会员","icon":"a72485dce2df5a87af4d1ec9edeab110.png","id":3,"priceDesc":"365元包年","desc":"享受所有模板使用权，分享邀请码即可获得奖励","status":0}]
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
         * rebatePermil : 0
         * period : 365
         * img : 9d0f62889f1b56e29d097fcd471a8fad.png
         * periodDesc : 一年
         * price : 9900
         * labelprice : 9900
         * name : 黄金会员
         * icon : 65b01885041550009983d1c7a75e2a87.png
         * mid : 1
         * priceDesc : 99元包年
         * desc : 享受部分模板使用权
         * status : 0
         */

        private int rebatePermil;
        private int period;
        private String img;
        private String periodDesc;
        private int price;
        private int labelprice;
        private String name;
        private String icon;
        private int mid;
        private String priceDesc;
        private String desc;
        private int status;

        public int getLabelprice() {
            return labelprice;
        }

        public void setLabelprice(int labelprice) {
            this.labelprice = labelprice;
        }

        public int getRebatePermil() {
            return rebatePermil;
        }

        public void setRebatePermil(int rebatePermil) {
            this.rebatePermil = rebatePermil;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPeriodDesc() {
            return periodDesc;
        }

        public void setPeriodDesc(String periodDesc) {
            this.periodDesc = periodDesc;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getPriceDesc() {
            return priceDesc;
        }

        public void setPriceDesc(String priceDesc) {
            this.priceDesc = priceDesc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
