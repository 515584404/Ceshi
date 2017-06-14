package com.ceshi.android.model;

/**
 * Created by ztr on 2016/05/01.
 */
public class MineData {

    /**
     * availableMoney : 4000.00
     * totalMoney : 10000.00
     * totalInterest : 0.00
     * mobile : 13888888888
     */

    private String availableMoney;
    private String totalMoney;
    private String totalInterest;
    private String mobile;
    private String yestodayInterest;

    public String getYestodayInterest() {
        return yestodayInterest;
    }

    public void setYestodayInterest(String yestodayInterest) {
        this.yestodayInterest = yestodayInterest;
    }

    public String getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
