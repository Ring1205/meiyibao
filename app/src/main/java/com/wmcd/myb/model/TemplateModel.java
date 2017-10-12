package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by 邓志宏 on 2017/2/22.
 */

public class TemplateModel {
    /**
     * template : {"isbuy":0,"hid":null,"buyCount":0,"objects":[{"img":"01a6542492c8414a997ccc901b2d692a.png","color":null,"weight":null,"createdate":"2017-02-12 14:37:42","type":1,"tid":1,"size":null,"transparency":0,"width":466,"x":0,"y":0,"z":0,"id":1,"text":null,"height":622,"font":null},{"img":null,"color":null,"weight":null,"createdate":"2017-02-12 14:38:21","type":2,"tid":1,"size":null,"transparency":0,"width":null,"x":100,"y":50,"z":1,"id":2,"text":"哈哈哈","height":null,"font":null}],"icon":"8a17968f62e85c1c81d8a9564aeff0e6","createdate":"2017-02-12 14:36:35","type":3,"price":0,"name":"头像","width":235,"id":1,"useCount":0,"desc":"头像","height":235,"order":0,"status":0}
     * result : 01
     * msg : 成功！
     */

    private TemplateBean template;
    private String result;
    private String msg;

    public TemplateBean getTemplate() {
        return template;
    }

    public void setTemplate(TemplateBean template) {
        this.template = template;
    }

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

    public static class TemplateBean {
        /**
         * isbuy : 0
         * hid : null
         * buyCount : 0
         * objects : [{"img":"01a6542492c8414a997ccc901b2d692a.png","color":null,"weight":null,"createdate":"2017-02-12 14:37:42","type":1,"tid":1,"size":null,"transparency":0,"width":466,"x":0,"y":0,"z":0,"id":1,"text":null,"height":622,"font":null},{"img":null,"color":null,"weight":null,"createdate":"2017-02-12 14:38:21","type":2,"tid":1,"size":null,"transparency":0,"width":null,"x":100,"y":50,"z":1,"id":2,"text":"哈哈哈","height":null,"font":null}]
         * icon : 8a17968f62e85c1c81d8a9564aeff0e6
         * createdate : 2017-02-12 14:36:35
         * type : 3
         * price : 0
         * name : 头像
         * width : 235
         * tid : 1
         * useCount : 0
         * desc : 头像
         * height : 235
         * order : 0.0
         * status : 0
         */

        private int isbuy;
        private Object hid;
        private int buyCount;
        private String icon;
        private String createdate;
        private int type;
        private int price;
        private String name;
        private int width;
        private int tid;
        private int useCount;
        private String desc;
        private int height;
        private double order;
        private int status;
        private List<ObjectsBean> objects;

        public int getIsbuy() {
            return isbuy;
        }

        public void setIsbuy(int isbuy) {
            this.isbuy = isbuy;
        }

        public Object getHid() {
            return hid;
        }

        public void setHid(Object hid) {
            this.hid = hid;
        }

        public int getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(int buyCount) {
            this.buyCount = buyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getUseCount() {
            return useCount;
        }

        public void setUseCount(int useCount) {
            this.useCount = useCount;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public double getOrder() {
            return order;
        }

        public void setOrder(double order) {
            this.order = order;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ObjectsBean> getObjects() {
            return objects;
        }

        public void setObjects(List<ObjectsBean> objects) {
            this.objects = objects;
        }

        public static class ObjectsBean {
            /**
             * img : 01a6542492c8414a997ccc901b2d692a.png
             * color : null
             * weight : null
             * createdate : 2017-02-12 14:37:42
             * type : 1
             * tid : 1
             * size : null
             * transparency : 0
             * width : 466
             * x : 0
             * y : 0
             * z : 0.0
             * oid : 1
             * text : null
             * height : 622
             * font : null
             */

            private String img;
            private Object color;
            private Object weight;
            private String createdate;
            private int type;
            private int tid;
            private Object size;
            private int transparency;
            private int width;
            private int x;
            private int y;
            private double z;
            private int oid;
            private Object text;
            private int height;
            private Object font;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public Object getColor() {
                return color;
            }

            public void setColor(Object color) {
                this.color = color;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }

            public Object getSize() {
                return size;
            }

            public void setSize(Object size) {
                this.size = size;
            }

            public int getTransparency() {
                return transparency;
            }

            public void setTransparency(int transparency) {
                this.transparency = transparency;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public double getZ() {
                return z;
            }

            public void setZ(double z) {
                this.z = z;
            }

            public int getOid() {
                return oid;
            }

            public void setOid(int oid) {
                this.oid = oid;
            }

            public Object getText() {
                return text;
            }

            public void setText(Object text) {
                this.text = text;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public Object getFont() {
                return font;
            }

            public void setFont(Object font) {
                this.font = font;
            }
        }
    }
}
