package com.wmcd.myb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/25 0025.
 */

public class DocModel implements Serializable {
    private String result;
    private String msg;
    private List<ListDocBean> docList;

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

    public List<ListDocBean> getDocList() {
        return docList;
    }

    public void setDocList(List<ListDocBean> docList) {
        this.docList = docList;
    }

    public static class ListDocBean implements Serializable {
        private String owner;
        private String img;//a6da3bf0961759aeb454677de8d0c82b.jpeg,05da3fac75b05e869f93ace2065d5faa.jpeg,c533312dafbb58a2912ba52938601650.jpeg",
        private String browseCount;//0,

        private String format;//doc",
        private String description;//众筹与投资资源入股协议书.doc",
        private String createdate;//2017-03-25 13:54:29",
        private String suffix;//doc",
        private String size;//28160,
        private String name;//众筹与投资资源入股协议书.doc",
        private String did;//34
        private String key;//众筹与投资资源入股协议书.doc",
        private String downloadCount;//0
        private String order;
        private String status;

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBrowseCount() {
            return browseCount;
        }

        public void setBrowseCount(String browseCount) {
            this.browseCount = browseCount;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
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

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getKey() {
            return key == null ? "" : key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(String downloadCount) {
            this.downloadCount = downloadCount;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
