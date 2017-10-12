package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class GenerateLogoModel {
    /**
     * result : 01
     * msg : 成功！
     * list : [{"fid":542,"img":"986e0026698f4ee6a7c6128012b664f4","text":"美易宝","logoid":92},{"fid":562,"img":"c09d3c19723845b3b1c28bbec40c526b","text":"美易宝","logoid":39},{"fid":577,"img":"6545d3225fea489b8644474db91ebfaa","text":"美易宝","logoid":98},{"fid":549,"img":"f791783dca7141d38cdce0b963094e0b","text":"美易宝","logoid":47},{"fid":586,"img":"8d5c446fe853433ba4ede292809b51b1","text":"美易宝","logoid":137},{"fid":631,"img":"647a7cf309c04622b0d5de4d15fab792","text":"美易宝","logoid":135},{"fid":555,"img":"5067ef69d5854c96b3cd6cc3beef9d2d","text":"美易宝","logoid":115},{"fid":571,"img":"94c830a815b9437aa61f19f1595c954d","text":"美易宝","logoid":119},{"fid":594,"img":"f0ad76e76a2a479faec581f3a0fbc161","text":"美易宝","logoid":105}]
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
         * fid : 542
         * img : 986e0026698f4ee6a7c6128012b664f4
         * text : 美易宝
         * logoid : 92
         */

        private int fid;
        private String img;
        private String text;
        private int logoid;

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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
    }
}
