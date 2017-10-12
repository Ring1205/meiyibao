package com.wmcd.myb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class CategoryModel implements Serializable {
    /**
     * result : 01
     * msg : 成功！
     * list : [{"img":null,"types":[{"img":"fd13fb4f42be4be2a5768245547c22e4.png","name":"头像","createdate":"2017-02-12 10:17:57","ttid":3,"img2":"83de80beb49d51ee9f7dfb3de5857eca.png","parentid":1,"desc":"头像模板","order":0,"status":0},{"img":"82566145bbd94f619fa2fc7902bc7192.png","name":"海报","createdate":"2017-02-12 10:18:10","ttid":4,"img2":"a50ebbafc9645b2b89ac0ebb42767f6a.png","parentid":1,"desc":"海报模板","order":1,"status":0},{"img":"66da583756534b74b4708c4180112f42.png","name":"线上名片","createdate":"2017-04-26 11:06:58","ttid":11,"img2":"48ec18d058475032b7900eeb9dccf685.png","parentid":1,"desc":"线上名片","order":2,"status":0},{"img":"578406021da34d40b9a53305aed53471.png","name":"优惠券","createdate":"2017-04-29 14:24:31","ttid":12,"img2":"c666ba7b52355feabb260e04b78b9676.png","parentid":1,"desc":"优惠券","order":4,"status":0},{"img":"11fdcf04ccf64664886b0957ea3f42ac.png","name":"会员卡","createdate":"2017-06-20 09:54:05","ttid":16,"img2":"02601cba2ef2586fb1e663216e63dafc.png","parentid":1,"desc":"线上会员卡","order":3,"status":0}],"name":"线上专栏","createdate":"2017-02-12 10:17:09","ttid":1,"img2":null,"parentid":0,"desc":"线上专栏","order":0,"status":0},{"img":null,"types":[{"img":"8a5ba4c4bb5a440c9eb297f8570901c9.png","name":"画册","createdate":"2017-02-12 10:18:27","ttid":5,"img2":"5e47ba1c9d4b589ab28fd9b96d321fe8.png","parentid":2,"desc":"画册模板","order":7,"status":0},{"img":"66da583756534b74b4708c4180112f42.png","name":"名片","createdate":"2017-02-12 10:18:52","ttid":7,"img2":"48ec18d058475032b7900eeb9dccf685.png","parentid":2,"desc":"名片模板","order":6,"status":0},{"img":"e527ef7124cf4a66ac69946f3b56103b.png","name":"折页","createdate":"2017-02-12 10:19:03","ttid":8,"img2":"f33676ce759b51b8b6a43f026f895671.png","parentid":2,"desc":"折页模板","order":8,"status":0},{"img":"b1cffd9ad5d94f0fb6021d12c29d6b61.png","name":"易拉宝","createdate":"2017-02-12 10:28:03","ttid":9,"img2":"a526cf126fa05b79a41941c0fcd6e3bf.png","parentid":2,"desc":"易拉宝模板","order":10,"status":0},{"img":"0439dfc7b3c749049cb7001e1a4f05e8.png","name":"超级表格","createdate":"2017-03-22 10:20:18","ttid":10,"img2":"f63ab026b52256fe964a06b8f1dc33cb.png","parentid":2,"desc":"超级表格","order":11,"status":0},{"img":"11fdcf04ccf64664886b0957ea3f42ac.png","name":"会员卡","createdate":"2017-04-29 14:24:34","ttid":13,"img2":"02601cba2ef2586fb1e663216e63dafc.png","parentid":2,"desc":"线下会员卡","order":9,"status":0}],"name":"线下专栏","createdate":"2017-02-12 10:17:32","ttid":2,"img2":null,"parentid":0,"desc":"线下专栏","order":0,"status":0}]
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
         * img : null
         * types : [{"img":"fd13fb4f42be4be2a5768245547c22e4.png","name":"头像","createdate":"2017-02-12 10:17:57","ttid":3,"img2":"83de80beb49d51ee9f7dfb3de5857eca.png","parentid":1,"desc":"头像模板","order":0,"status":0},{"img":"82566145bbd94f619fa2fc7902bc7192.png","name":"海报","createdate":"2017-02-12 10:18:10","ttid":4,"img2":"a50ebbafc9645b2b89ac0ebb42767f6a.png","parentid":1,"desc":"海报模板","order":1,"status":0},{"img":"66da583756534b74b4708c4180112f42.png","name":"线上名片","createdate":"2017-04-26 11:06:58","ttid":11,"img2":"48ec18d058475032b7900eeb9dccf685.png","parentid":1,"desc":"线上名片","order":2,"status":0},{"img":"578406021da34d40b9a53305aed53471.png","name":"优惠券","createdate":"2017-04-29 14:24:31","ttid":12,"img2":"c666ba7b52355feabb260e04b78b9676.png","parentid":1,"desc":"优惠券","order":4,"status":0},{"img":"11fdcf04ccf64664886b0957ea3f42ac.png","name":"会员卡","createdate":"2017-06-20 09:54:05","ttid":16,"img2":"02601cba2ef2586fb1e663216e63dafc.png","parentid":1,"desc":"线上会员卡","order":3,"status":0}]
         * name : 线上专栏
         * createdate : 2017-02-12 10:17:09
         * ttid : 1
         * img2 : null
         * parentid : 0
         * desc : 线上专栏
         * order : 0
         * status : 0
         */

        private Object img;
        private String name;
        private String createdate;
        private int ttid;
        private Object img2;
        private int parentid;
        private String desc;
        private int order;
        private int status;
        private List<TypesBean> types;

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public int getTtid() {
            return ttid;
        }

        public void setTtid(int ttid) {
            this.ttid = ttid;
        }

        public Object getImg2() {
            return img2;
        }

        public void setImg2(Object img2) {
            this.img2 = img2;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

        public List<TypesBean> getTypes() {
            return types;
        }

        public void setTypes(List<TypesBean> types) {
            this.types = types;
        }

        public static class TypesBean {
            /**
             * img : fd13fb4f42be4be2a5768245547c22e4.png
             * name : 头像
             * createdate : 2017-02-12 10:17:57
             * ttid : 3
             * img2 : 83de80beb49d51ee9f7dfb3de5857eca.png
             * parentid : 1
             * desc : 头像模板
             * order : 0
             * status : 0
             */

            private String img;
            private String name;
            private String createdate;
            private int ttid;
            private String img2;
            private int parentid;
            private String desc;
            private int order;
            private int status;
private boolean isselect;

            public boolean isselect() {
                return isselect;
            }

            public void setIsselect(boolean isselect) {
                this.isselect = isselect;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public int getTtid() {
                return ttid;
            }

            public void setTtid(int ttid) {
                this.ttid = ttid;
            }

            public String getImg2() {
                return img2;
            }

            public void setImg2(String img2) {
                this.img2 = img2;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
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
    }
}
