package com.ceshi.android.entity;

import java.util.List;

/**
 * Created by ztr on 2016/05/04.
 */
public class TradeEntity {

    /**
     * records : [{"createTime":"2016-05-02 11:38:54","investRecordId":0,"updateTime":null,"status":"VALID","type":"BUY","productId":2,"fee":"0","id":12,"availableMoney":"3990.00","productTitle":"月月团(10%/1月)","userId":1,"money":"10","userBankCardId":0,"clientType":"PC"},{"createTime":"2016-04-26 10:05:07","investRecordId":0,"updateTime":null,"status":"VALID","type":"BUY","productId":3,"fee":"0","id":11,"availableMoney":"4000.00","productTitle":null,"userId":1,"money":"3000","userBankCardId":0,"clientType":"PC"},{"createTime":"2016-04-26 10:04:58","investRecordId":0,"updateTime":null,"status":"VALID","type":"BUY","productId":3,"fee":"0","id":10,"availableMoney":"7000.00","productTitle":null,"userId":1,"money":"2000","userBankCardId":0,"clientType":"PC"},{"createTime":"2016-04-26 10:04:41","investRecordId":0,"updateTime":null,"status":"VALID","type":"BUY","productId":2,"fee":"0","id":9,"availableMoney":"9000.00","productTitle":null,"userId":1,"money":"1000","userBankCardId":0,"clientType":"PC"},{"createTime":"2016-04-26 10:04:27","investRecordId":0,"updateTime":null,"status":"VALID","type":"DEPOSIT","productId":0,"fee":"0","id":8,"availableMoney":"10000.00","productTitle":null,"userId":1,"money":"10000.00","userBankCardId":0,"clientType":"PC"}]
     * totalPage : 1
     * currentPage : 1
     */

    private int totalPage;
    private int currentPage;
    /**
     * createTime : 2016-05-02 11:38:54
     * investRecordId : 0
     * updateTime : null
     * status : VALID
     * type : BUY
     * productId : 2
     * fee : 0
     * id : 12
     * availableMoney : 3990.00
     * productTitle : 月月团(10%/1月)
     * userId : 1
     * money : 10
     * userBankCardId : 0
     * clientType : PC
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
        private int investRecordId;
        private Object updateTime;
        private String status;
        private String type;
        private int productId;
        private String fee;
        private int id;
        private String availableMoney;
        private String productTitle;
        private int userId;
        private String money;
        private int userBankCardId;
        private String clientType;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getInvestRecordId() {
            return investRecordId;
        }

        public void setInvestRecordId(int investRecordId) {
            this.investRecordId = investRecordId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getAvailableMoney() {
            return availableMoney;
        }

        public void setAvailableMoney(String availableMoney) {
            this.availableMoney = availableMoney;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getUserBankCardId() {
            return userBankCardId;
        }

        public void setUserBankCardId(int userBankCardId) {
            this.userBankCardId = userBankCardId;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }
    }
}
