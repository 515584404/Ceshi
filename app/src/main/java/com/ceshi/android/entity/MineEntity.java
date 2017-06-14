package com.ceshi.android.entity;

/**
 * Created by ztr on 2016/05/01.
 */
public class MineEntity {

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
    private String currentInterest;
    private String currentMoney;
    private String currentYestodayInterest;
    private String periodicalExpactInterest;
    private String periodicalInterest;
    private String periodicalMoney;


    public String getYestodayInterest() {
        return yestodayInterest;
    }

    public void setYestodayInterest(String yestodayInterest) {
        this.yestodayInterest = yestodayInterest;
    }

    public String getAvailableMoney() {
        return availableMoney;
    }

    public String getCurrentInterest() {
        return currentInterest;
    }

    public String getCurrentMoney() {
        return currentMoney;
    }

    public String getCurrentYestodayInterest() {
        return currentYestodayInterest;
    }

    public String getPeriodicalExpactInterest() {
        return periodicalExpactInterest;
    }

    public String getPeriodicalInterest() {
        return periodicalInterest;
    }

    public String getPeriodicalMoney() {
        return periodicalMoney;
    }

    public void setCurrentInterest(String currentInterest) {
        this.currentInterest = currentInterest;
    }

    public void setCurrentMoney(String currentMoney) {
        this.currentMoney = currentMoney;
    }

    public void setCurrentYestodayInterest(String currentYestodayInterest) {
        this.currentYestodayInterest = currentYestodayInterest;
    }

    public void setPeriodicalExpactInterest(String periodicalExpactInterest) {
        this.periodicalExpactInterest = periodicalExpactInterest;
    }

    public void setPeriodicalInterest(String periodicalInterest) {
        this.periodicalInterest = periodicalInterest;
    }

    public void setPeriodicalMoney(String periodicalMoney) {
        this.periodicalMoney = periodicalMoney;
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

    @Override
    public String toString() {
        return "MineEntity{" +
                "availableMoney='" + availableMoney + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", totalInterest='" + totalInterest + '\'' +
                ", mobile='" + mobile + '\'' +
                ", yestodayInterest='" + yestodayInterest + '\'' +
                ", currentInterest='" + currentInterest + '\'' +
                ", currentMoney='" + currentMoney + '\'' +
                ", currentYestodayInterest='" + currentYestodayInterest + '\'' +
                ", periodicalExpactInterest='" + periodicalExpactInterest + '\'' +
                ", periodicalInterest='" + periodicalInterest + '\'' +
                ", periodicalMoney='" + periodicalMoney + '\'' +
                '}';
    }
}
