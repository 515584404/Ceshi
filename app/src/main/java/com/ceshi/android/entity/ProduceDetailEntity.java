package com.ceshi.android.entity;

/**
 * Created by ztr on 2016/04/28.
 */
public class ProduceDetailEntity {


    /**
     * product : {"id":100,"title":"新手专享【15（T）X1612004】","money":"100000","restMoney":"100000","rate":"15","startTime":"2016-12-12 10:00:00","endTime":"2016-12-14 10:00:00","duration":15,"durationType":"DAY","lockDuration":15,"category":"GROUP","type":"NEWER","status":"SELLING","interestValueType":"T_1","interestPayType":"ONCE_TIME","principlePayType":"ONCE_TIME","minMoney":"100","maxMoney":"20000","redeemFeeRate":"","countDown":-110005,"createTime":"2016-12-12 09:36:47","updateTime":"2016-12-12 09:41:39","securityDescription":"","seqNumber":0,"repayDescription":"到期还本付息"}
     * availableMoney : 0.00
     */

    private ProductBean product;
    private String availableMoney;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public String getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    public static class ProductBean {
        /**
         * id : 100
         * title : 新手专享【15（T）X1612004】
         * money : 100000
         * restMoney : 100000
         * rate : 15
         * startTime : 2016-12-12 10:00:00
         * endTime : 2016-12-14 10:00:00
         * duration : 15
         * durationType : DAY
         * lockDuration : 15
         * category : GROUP
         * type : NEWER
         * status : SELLING
         * interestValueType : T_1
         * interestPayType : ONCE_TIME
         * principlePayType : ONCE_TIME
         * minMoney : 100
         * maxMoney : 20000
         * redeemFeeRate :
         * countDown : -110005
         * createTime : 2016-12-12 09:36:47
         * updateTime : 2016-12-12 09:41:39
         * securityDescription :
         * seqNumber : 0
         * repayDescription : 到期还本付息
         */

        private int id;
        private String title;
        private String money;
        private String restMoney;
        private String rate;
        private String startTime;
        private String endTime;
        private int duration;
        private String durationType;
        private int lockDuration;
        private String category;
        private String type;
        private String status;
        private String interestValueType;
        private String interestPayType;
        private String principlePayType;
        private String minMoney;
        private String maxMoney;
        private String redeemFeeRate;
        private int countDown;
        private String createTime;
        private String updateTime;
        private String securityDescription;
        private int seqNumber;
        private String repayDescription;

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

        public int getLockDuration() {
            return lockDuration;
        }

        public void setLockDuration(int lockDuration) {
            this.lockDuration = lockDuration;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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

        public String getInterestValueType() {
            return interestValueType;
        }

        public void setInterestValueType(String interestValueType) {
            this.interestValueType = interestValueType;
        }

        public String getInterestPayType() {
            return interestPayType;
        }

        public void setInterestPayType(String interestPayType) {
            this.interestPayType = interestPayType;
        }

        public String getPrinciplePayType() {
            return principlePayType;
        }

        public void setPrinciplePayType(String principlePayType) {
            this.principlePayType = principlePayType;
        }

        public String getMinMoney() {
            return minMoney;
        }

        public void setMinMoney(String minMoney) {
            this.minMoney = minMoney;
        }

        public String getMaxMoney() {
            return maxMoney;
        }

        public void setMaxMoney(String maxMoney) {
            this.maxMoney = maxMoney;
        }

        public String getRedeemFeeRate() {
            return redeemFeeRate;
        }

        public void setRedeemFeeRate(String redeemFeeRate) {
            this.redeemFeeRate = redeemFeeRate;
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

        public String getSecurityDescription() {
            return securityDescription;
        }

        public void setSecurityDescription(String securityDescription) {
            this.securityDescription = securityDescription;
        }

        public int getSeqNumber() {
            return seqNumber;
        }

        public void setSeqNumber(int seqNumber) {
            this.seqNumber = seqNumber;
        }

        public String getRepayDescription() {
            return repayDescription;
        }

        public void setRepayDescription(String repayDescription) {
            this.repayDescription = repayDescription;
        }
    }
}
