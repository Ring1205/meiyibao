package com.wmcd.myb.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class ResultBean implements Serializable {
    private String result;

    private String msg;

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

}
