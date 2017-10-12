package com.wmcd.myb.model;

/**
 * Created by Administrator on 2017/3/22.
 */

public class GenerateImageModel {
    /**
     * msg : sdsd
     * photo : {"img":"772f1dae48bb43feab744b003f745458,","photoid":19,"tid":"14"}
     * result : 01
     */

    private String msg;
    private PhotoBean photo;
    private String result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PhotoBean getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoBean photo) {
        this.photo = photo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class PhotoBean {
        /**
         * img : 772f1dae48bb43feab744b003f745458,
         * photoid : 19
         * tid : 14
         */

        private String img;
        private int photoid;
        private String tid;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getPhotoid() {
            return photoid;
        }

        public void setPhotoid(int photoid) {
            this.photoid = photoid;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
