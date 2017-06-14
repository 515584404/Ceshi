package com.ceshi.android.entity;

import java.util.List;

/**
 * Created by ztr on 2016/05/04.
 */
public class TzZqEntity {

    /**
     * id : 67
     * createTime : 2016-04-26 10:05:07
     * title : 贸易企业资金周转3
     * rate : 12
     * investRecordId : 7
     * updateTime : null
     * userId : 1
     * creditMoney : 2500000
     * distributeMoney : 1500.000000
     * creditId : 7
     * href : #
     */

    private List<RecordsEntity> records;

    public List<RecordsEntity> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsEntity> records) {
        this.records = records;
    }

    public static class RecordsEntity {
        private int id;
        private String createTime;
        private String title;
        private String rate;
        private int investRecordId;
        private Object updateTime;
        private int userId;
        private String creditMoney;
        private String distributeMoney;
        private int creditId;
        private String href;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCreditMoney() {
            return creditMoney;
        }

        public void setCreditMoney(String creditMoney) {
            this.creditMoney = creditMoney;
        }

        public String getDistributeMoney() {
            return distributeMoney;
        }

        public void setDistributeMoney(String distributeMoney) {
            this.distributeMoney = distributeMoney;
        }

        public int getCreditId() {
            return creditId;
        }

        public void setCreditId(int creditId) {
            this.creditId = creditId;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}
