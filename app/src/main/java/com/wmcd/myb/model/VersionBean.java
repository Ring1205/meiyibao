package com.wmcd.myb.model;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class VersionBean {
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

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    private String result;
    private String msg;
    private Version version;

    public CommonData getCommonData() {
        return commonData;
    }

    public void setCommonData(CommonData commonData) {
        this.commonData = commonData;
    }

    private CommonData commonData;

    public static class Version {
        private int applicationVersion;
        private String applicationType;
        private String url;
        private String applicationStatus;// 是否强制升级(Y/N)
        private String applicationDes;// 测试版本'

        public int getApplicationVersion() {
            return applicationVersion;
        }

        public void setApplicationVersion(int applicationVersion) {
            this.applicationVersion = applicationVersion;
        }

        public String getApplicationType() {
            return applicationType;
        }

        public void setApplicationType(String applicationType) {
            this.applicationType = applicationType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getApplicationStatus() {
            return applicationStatus;
        }

        public void setApplicationStatus(String applicationStatus) {
            this.applicationStatus = applicationStatus;
        }

        public String getApplicationDes() {
            return applicationDes == null ? "" : applicationDes;
        }

        public void setApplicationDes(String applicationDes) {
            this.applicationDes = applicationDes;
        }
    }

    public static class CommonData {
        public String getOSSEndpoint() {
            return OSSEndpoint;
        }

        public void setOSSEndpoint(String OSSEndpoint) {
            this.OSSEndpoint = OSSEndpoint;
        }

        public String getAliyunAccessKeyId() {
            return aliyunAccessKeyId;
        }

        public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
            this.aliyunAccessKeyId = aliyunAccessKeyId;
        }

        public String getAliyunAccessAppCode() {
            return aliyunAccessAppCode;
        }

        public void setAliyunAccessAppCode(String aliyunAccessAppCode) {
            this.aliyunAccessAppCode = aliyunAccessAppCode;
        }

        public String getFontOSSUrl() {
            return fontOSSUrl;
        }

        public void setFontOSSUrl(String fontOSSUrl) {
            this.fontOSSUrl = fontOSSUrl;
        }

        public String getDocOSSUrl() {
            return docOSSUrl;
        }

        public void setDocOSSUrl(String docOSSUrl) {
            this.docOSSUrl = docOSSUrl;
        }

        public String getImgOSSUrl() {
            return imgOSSUrl;
        }

        public void setImgOSSUrl(String imgOSSUrl) {
            this.imgOSSUrl = imgOSSUrl;
        }

        public String getAliyunAccessKeySecret() {
            return aliyunAccessKeySecret;
        }

        public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
            this.aliyunAccessKeySecret = aliyunAccessKeySecret;
        }

        public String getFileOSSUrl() {
            return fileOSSUrl;
        }

        public void setFileOSSUrl(String fileOSSUrl) {
            this.fileOSSUrl = fileOSSUrl;
        }

        public String getImgBucket() {
            return imgBucket;
        }

        public void setImgBucket(String imgBucket) {
            this.imgBucket = imgBucket;
        }

        private String OSSEndpoint;//http://oss-cn-shanghai.aliyuncs.com/
        private String aliyunAccessKeyId;//LTAIXdRaTAjA7KjB
        private String aliyunAccessAppCode;//b3fe443f6b5e4103aef68aeeed89a800
        private String fontOSSUrl;//http://mybfont.dzwapp.com/"
        private String docOSSUrl;//http://mybdoc.dzwapp.com/"
        private String imgOSSUrl;//http://mybimg.dzwapp.com/"
        private String aliyunAccessKeySecret;//tfsrQd2ciMkI1zbyg04CdjQPXnIDuG",
        private String fileOSSUrl;//http://mybfile.dzwapp.com/",
        private String imgBucket;//mybpic
    }
}
