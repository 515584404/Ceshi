package com.ishengxi.android.model;

import java.util.List;

/**
 * Created by ztr on 2016/04/25.
 */
public class MainProduce {

    /**
     * id : 1
     * title : 新手团
     * money : 2000000
     * restMoney : 1999000
     * rate : 15
     * startTime : 2016-04-25 10:00:00
     * endTime : 2016-05-02 10:00:00
     * valueDate : 2016-05-01
     * duration : 7
     * durationType : DAY
     * type : NEWER
     * status : SELLING
     * repayType : ONCE_TIME
     * countDown : -32331
     * createTime : 2016-04-25 16:18:05
     * updateTime : 2016-04-25 18:09:03
     */

    private List<ProductsEntity> products;

    public List<ProductsEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsEntity> products) {
        this.products = products;
    }

    public static class ProductsEntity {
        private int id;
        private String title;
        private String money;
        private String restMoney;
        private String rate;
        private String startTime;
        private String endTime;
        private String valueDate;
        private int duration;
        private String durationType;
        private String type;
        private String status;
        private String repayType;
        private int countDown;
        private String createTime;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRestMoney() {
            return restMoney;
        }

        public void setRestMoney(String restMoney) {
            this.restMoney = restMoney;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getValueDate() {
            return valueDate;
        }

        public void setValueDate(String valueDate) {
            this.valueDate = valueDate;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getDurationType() {
            return durationType;
        }

        public void setDurationType(String durationType) {
            this.durationType = durationType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public int getCountDown() {
            return countDown;
        }

        public void setCountDown(int countDown) {
            this.countDown = countDown;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
