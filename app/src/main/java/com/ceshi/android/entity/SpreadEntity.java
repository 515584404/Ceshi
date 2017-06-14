package com.ceshi.android.entity;

import java.util.List;

/**
 * Created by ztr on 2016/05/01.
 */

public class SpreadEntity {


    /**
     * recommenderMobile : 13888888888
     * id : 1
     * recommendTime : 2016-05-10 14:50:33
     * commission : 0
     * recommendedMobile : 1377777****
     */

    private List<RecordsBean> records;

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        private String recommenderMobile;
        private int id;
        private String recommendTime;
        private String commission;
        private String recommendedMobile;

        public String getRecommenderMobile() {
            return recommenderMobile;
        }

        public void setRecommenderMobile(String recommenderMobile) {
            this.recommenderMobile = recommenderMobile;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRecommendTime() {
            return recommendTime;
        }

        public void setRecommendTime(String recommendTime) {
            this.recommendTime = recommendTime;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getRecommendedMobile() {
            return recommendedMobile;
        }

        public void setRecommendedMobile(String recommendedMobile) {
            this.recommendedMobile = recommendedMobile;
        }
    }
}
