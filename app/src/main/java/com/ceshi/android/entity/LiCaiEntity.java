package com.ceshi.android.entity;

import java.util.List;

/**
 * Created by ztr on 2016/05/03.
 */
public class LiCaiEntity {

    /**
     * records : [{"createTime":"2016-05-02 11:38:54","valueDate":"2016-04-26 ","updateTime":"2016-05-03 00:10:01","status":"INTERESTING","repayType":"ONCE_TIME","repaidInterest":"0.00","benxiSum":"10.08","productId":2,"fee":"0","id":8,"duration":1,"rate":"10","interest":"0.08","userId":1,"productTitle":"月月团(10%/1月)","money":"10","durationType":"MONTH","repayDate":"2016-05-26"},{"createTime":"2016-04-26 10:05:07","valueDate":"2016-04-26 ","updateTime":"2016-04-27 00:10:01","status":"INTERESTING","repayType":"ONCE_TIME","repaidInterest":"0.00","benxiSum":"3082.50","productId":3,"fee":"0","id":7,"duration":3,"rate":"11","interest":"82.50","userId":1,"productTitle":"季季团","money":"3000","durationType":"MONTH","repayDate":"2016-07-26"},{"createTime":"2016-04-26 10:04:58","valueDate":"2016-04-26 ","updateTime":"2016-04-27 00:10:01","status":"INTERESTING","repayType":"ONCE_TIME","repaidInterest":"0.00","benxiSum":"2055.00","productId":3,"fee":"0","id":6,"duration":3,"rate":"11","interest":"55.00","userId":1,"productTitle":"季季团","money":"2000","durationType":"MONTH","repayDate":"2016-07-26"},{"createTime":"2016-04-26 10:04:41","valueDate":"2016-04-26 ","updateTime":"2016-04-27 00:10:01","status":"INTERESTING","repayType":"ONCE_TIME","repaidInterest":"0.00","benxiSum":"1008.33","productId":2,"fee":"0","id":5,"duration":1,"rate":"10","interest":"8.33","userId":1,"productTitle":"月月团","money":"1000","durationType":"MONTH","repayDate":"2016-05-26"}]
     * totalPage : 1
     * currentPage : 1
     */

    private int totalPage;
    private int currentPage;
    /**
     * createTime : 2016-05-02 11:38:54
     * valueDate : 2016-04-26
     * updateTime : 2016-05-03 00:10:01
     * status : INTERESTING
     * repayType : ONCE_TIME
     * repaidInterest : 0.00
     * benxiSum : 10.08
     * productId : 2
     * fee : 0
     * id : 8
     * duration : 1
     * rate : 10
     * interest : 0.08
     * userId : 1
     * productTitle : 月月团(10%/1月)
     * money : 10
     * durationType : MONTH
     * repayDate : 2016-05-26
     */

    private List<RecordsEntity> records;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<RecordsEntity> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsEntity> records) {
        this.records = records;
    }

    public static class RecordsEntity {
        private String createTime;
        private String valueDate;
        private String updateTime;
        private String status;
        private String repayType;
        private String repaidInterest;
        private String benxiSum;
        private int productId;
        private String fee;
        private int id;
        private int duration;
        private String rate;
        private String interest;
        private int userId;
        private String productTitle;
        private String money;
        private String durationType;
        private String repayDate;

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

        public String getRepaidInterest() {
            return repaidInterest;
        }

        public void setRepaidInterest(String repaidInterest) {
            this.repaidInterest = repaidInterest;
        }

        public String getBenxiSum() {
            return benxiSum;
        }

        public void setBenxiSum(String benxiSum) {
            this.benxiSum = benxiSum;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
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

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
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

        public String getRepayDate() {
            return repayDate;
        }

        public void setRepayDate(String repayDate) {
            this.repayDate = repayDate;
        }
    }
}
