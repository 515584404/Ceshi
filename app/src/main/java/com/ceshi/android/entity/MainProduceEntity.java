package com.ceshi.android.entity;

import java.util.List;

/**
 * 首页产品JSON返回数据类
 * Created by ztr on 2016/04/25.
 */
public class MainProduceEntity {

    /**
     * newUserProduct : {"id":86,"title":"新手专享【15（T）X1611010】","money":"100000","restMoney":"100000","rate":"15","startTime":"2016-12-02 10:00:00","endTime":"2016-12-05 10:00:00","duration":15,"durationType":"DAY","lockDuration":15,"category":"GROUP","type":"NEWER","status":"SELLING","interestValueType":"T_1","interestPayType":"DAY_BY_DAY","principlePayType":"ONCE_TIME","minMoney":"100","maxMoney":"20000","redeemFeeRate":"","countDown":-337,"createTime":"2016-12-02 09:41:58","updateTime":null,"securityDescription":"","repayDescription":"按日付息到期还本"}
     * currentProduct : {"id":87,"title":"零乾袋","money":"100000","restMoney":"100000","rate":"12","startTime":"2016-12-02 10:00:00","endTime":"2016-12-05 10:00:00","duration":0,"durationType":"DAY","lockDuration":0,"category":"GROUP","type":"CURRENT","status":"SELLING","interestValueType":"T_0","interestPayType":"DAY_BY_DAY","principlePayType":"ONCE_TIME","minMoney":"1","maxMoney":"20000","redeemFeeRate":"","countDown":-337,"createTime":"2016-12-02 09:43:23","updateTime":null,"securityDescription":"","repayDescription":"按日付息到期还本"}
     * periodicalProducts : [{"id":83,"title":"安乾季【03J1611005】","money":"50000","restMoney":"50000","rate":"9.8","startTime":"2016-11-30 10:00:00","endTime":"2016-12-05 10:00:00","duration":3,"durationType":"MONTH","lockDuration":3,"category":"GROUP","type":"QUATER","status":"SELLING","interestValueType":"T_1","interestPayType":"MONTH_BY_MONTH","principlePayType":"ONCE_TIME","minMoney":"100","maxMoney":"50000","redeemFeeRate":"","countDown":-173137,"createTime":"2016-11-30 09:56:33","updateTime":"2016-11-30 18:00:32","securityDescription":"","repayDescription":"按月付息到期还本"},{"id":85,"title":"安乾月【01Y1611009】","money":"50000","restMoney":"50000","rate":"9.2","startTime":"2016-12-02 10:00:00","endTime":"2016-12-05 10:00:00","duration":1,"durationType":"MONTH","lockDuration":1,"category":"GROUP","type":"MONTH","status":"SELLING","interestValueType":"T_1","interestPayType":"ONCE_TIME","principlePayType":"ONCE_TIME","minMoney":"100","maxMoney":"50000","redeemFeeRate":"","countDown":-337,"createTime":"2016-12-02 09:39:07","updateTime":null,"securityDescription":"","repayDescription":"到期还本付息"},{"id":76,"title":"安乾季【03J1611004】","money":"50000","restMoney":"0","rate":"9.8","startTime":"2016-11-25 10:00:00","endTime":"2016-11-30 10:00:00","duration":3,"durationType":"MONTH","lockDuration":3,"category":"GROUP","type":"QUATER","status":"SELLOUT","interestValueType":"T_1","interestPayType":"MONTH_BY_MONTH","principlePayType":"ONCE_TIME","minMoney":"100","maxMoney":"50000","redeemFeeRate":"","countDown":-605137,"createTime":"2016-11-25 09:50:46","updateTime":"2016-11-30 13:29:10","securityDescription":"","repayDescription":"按月付息到期还本"},{"id":77,"title":"安乾月【01Y1611006】","money":"50000","restMoney":"0","rate":"9.2","startTime":"2016-11-25 10:00:00","endTime":"2016-11-28 10:00:00","duration":1,"durationType":"MONTH","lockDuration":1,"category":"GROUP","type":"MONTH","status":"SELLOUT","interestValueType":"T_1","interestPayType":"ONCE_TIME","principlePayType":"ONCE_TIME","minMoney":"100","maxMoney":"50000","redeemFeeRate":"","countDown":-605137,"createTime":"2016-11-25 09:52:13","updateTime":"2016-11-28 13:16:07","securityDescription":"","repayDescription":"到期还本付息"}]
     */

    private NewUserProductBean newUserProduct;
    private CurrentProductBean currentProduct;
    private List<PeriodicalProductsBean> periodicalProducts;

    public NewUserProductBean getNewUserProduct() {
        return newUserProduct;
    }

    public void setNewUserProduct(NewUserProductBean newUserProduct) {
        this.newUserProduct = newUserProduct;
    }

    public CurrentProductBean getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(CurrentProductBean currentProduct) {
        this.currentProduct = currentProduct;
    }

    public List<PeriodicalProductsBean> getPeriodicalProducts() {
        return periodicalProducts;
    }

    public void setPeriodicalProducts(List<PeriodicalProductsBean> periodicalProducts) {
        this.periodicalProducts = periodicalProducts;
    }

    public static class NewUserProductBean {
        /**
         * id : 86
         * title : 新手专享【15（T）X1611010】
         * money : 100000
         * restMoney : 100000
         * rate : 15
         * startTime : 2016-12-02 10:00:00
         * endTime : 2016-12-05 10:00:00
         * duration : 15
         * durationType : DAY
         * lockDuration : 15
         * category : GROUP
         * type : NEWER
         * status : SELLING
         * interestValueType : T_1
         * interestPayType : DAY_BY_DAY
         * principlePayType : ONCE_TIME
         * minMoney : 100
         * maxMoney : 20000
         * redeemFeeRate :
         * countDown : -337
         * createTime : 2016-12-02 09:41:58
         * updateTime : null
         * securityDescription :
         * repayDescription : 按日付息到期还本
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
        private Object updateTime;
        private String securityDescription;
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

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getSecurityDescription() {
            return securityDescription;
        }

        public void setSecurityDescription(String securityDescription) {
            this.securityDescription = securityDescription;
        }

        public String getRepayDescription() {
            return repayDescription;
        }

        public void setRepayDescription(String repayDescription) {
            this.repayDescription = repayDescription;
        }
    }

    public static class CurrentProductBean {
        /**
         * id : 87
         * title : 零乾袋
         * money : 100000
         * restMoney : 100000
         * rate : 12
         * startTime : 2016-12-02 10:00:00
         * endTime : 2016-12-05 10:00:00
         * duration : 0
         * durationType : DAY
         * lockDuration : 0
         * category : GROUP
         * type : CURRENT
         * status : SELLING
         * interestValueType : T_0
         * interestPayType : DAY_BY_DAY
         * principlePayType : ONCE_TIME
         * minMoney : 1
         * maxMoney : 20000
         * redeemFeeRate :
         * countDown : -337
         * createTime : 2016-12-02 09:43:23
         * updateTime : null
         * securityDescription :
         * repayDescription : 按日付息到期还本
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
        private Object updateTime;
        private String securityDescription;
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

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getSecurityDescription() {
            return securityDescription;
        }

        public void setSecurityDescription(String securityDescription) {
            this.securityDescription = securityDescription;
        }

        public String getRepayDescription() {
            return repayDescription;
        }

        public void setRepayDescription(String repayDescription) {
            this.repayDescription = repayDescription;
        }
    }

    public static class PeriodicalProductsBean {
        /**
         * id : 83
         * title : 安乾季【03J1611005】
         * money : 50000
         * restMoney : 50000
         * rate : 9.8
         * startTime : 2016-11-30 10:00:00
         * endTime : 2016-12-05 10:00:00
         * duration : 3
         * durationType : MONTH
         * lockDuration : 3
         * category : GROUP
         * type : QUATER
         * status : SELLING
         * interestValueType : T_1
         * interestPayType : MONTH_BY_MONTH
         * principlePayType : ONCE_TIME
         * minMoney : 100
         * maxMoney : 50000
         * redeemFeeRate :
         * countDown : -173137
         * createTime : 2016-11-30 09:56:33
         * updateTime : 2016-11-30 18:00:32
         * securityDescription :
         * repayDescription : 按月付息到期还本
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

        public String getRepayDescription() {
            return repayDescription;
        }

        public void setRepayDescription(String repayDescription) {
            this.repayDescription = repayDescription;
        }
    }
}
