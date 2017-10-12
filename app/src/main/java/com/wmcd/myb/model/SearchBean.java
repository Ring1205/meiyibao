package com.wmcd.myb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public class SearchBean implements Serializable{

    /**
     * result : 01
     * msg : 成功！
     * hotWordList : [{"count":12,"hwid":3,"word":"五一","status":0},{"count":2,"hwid":1,"word":"头像","status":0},{"count":1,"hwid":2,"word":"海报","status":0}]
     */

    private String result;
    private String msg;
    private List<HotWordListBean> hotWordList;

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

    public List<HotWordListBean> getHotWordList() {
        return hotWordList;
    }

    public void setHotWordList(List<HotWordListBean> hotWordList) {
        this.hotWordList = hotWordList;
    }

    public static class HotWordListBean {
        /**
         * count : 12
         * hwid : 3
         * word : 五一
         * status : 0
         */

        private int count;
        private int hwid;
        private String word;
        private int status;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getHwid() {
            return hwid;
        }

        public void setHwid(int hwid) {
            this.hwid = hwid;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
