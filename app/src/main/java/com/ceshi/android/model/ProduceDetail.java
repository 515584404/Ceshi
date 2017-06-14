package com.ceshi.android.model;

/**
 * Created by ztr on 2016/04/28.
 */
public class ProduceDetail {

    /**
     * createTime : 2016-04-25 16:18:05
     * valueDate : 2016-05-01
     * updateTime : 2016-04-25 18:09:03
     * countDown : -282757
     * status : SELLING
     * repayType : ONCE_TIME
     * type : NEWER
     * endTime : 2016-05-02 10:00:00
     * restMoney : 1999000
     * startTime : 2016-04-25 10:00:00
     * id : 1
     * duration : 7
     * title : 新手团
     * rate : 15
     * money : 2000000
     * durationType : DAY
     */

    private ProductEntity product;
    /**
     * product : {"createTime":"2016-04-25 16:18:05","valueDate":"2016-05-01 ","updateTime":"2016-04-25 18:09:03","countDown":-282757,"status":"SELLING","repayType":"ONCE_TIME","type":"NEWER","endTime":"2016-05-02 10:00:00","restMoney":"1999000","startTime":"2016-04-25 10:00:00","id":1,"duration":7,"title":"新手团","rate":"15","money":"2000000","durationType":"DAY"}
     * availableMoney : 4000.00
     */

    private String availableMoney;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public String getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    public static class ProductEntity {
        private String createTime;
        private String valueDate;
        private String updateTime;
        private int countDown;
        private String status;
        private String repayType;
        private String type;
        private String endTime;
        private String restMoney;
        private String startTime;
        private int id;
        private int duration;
        private String title;
        private String rate;
        private String money;
        private String durationType;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getValueDate() {
            return valueDate;
        }

        public void setValueDate(String valueDate) {
            this.valueDate = valueDate;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getCountDown() {
            return countDown;
        }

        public void setCountDown(int countDown) {
            this.countDown = countDown;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRepayType() {
            return repayType;
        }

        public void setRepayType(String repayType) {
            this.repayType = repayType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getRestMoney() {
            return restMoney;
        }

        public void setRestMoney(String restMoney) {
            this.restMoney = restMoney;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDurationType() {
            return durationType;
        }

        public void setDurationType(String durationType) {
            this.durationType = durationType;
        }
    }
}
