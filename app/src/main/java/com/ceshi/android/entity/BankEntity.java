package com.ceshi.android.entity;

/**
 * Created by ztr on 2016/05/12.
 */
public class BankEntity {

    public BankEntity(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * onceLimitMoney : 50000
     * id : 19
     * monthLimitMoney : 2
     * status : VALID
     * description : 限额(元): 单笔5万/单日5万/单月2
     * thirdParty : LIANLIAN
     * name : 交通银行
     * code : 03010000
     * standardCode : BOCM
     * dayLimitMoney : 50001
     */

    private String onceLimitMoney;
    private int id;
    private String monthLimitMoney;
    private String status;
    private String description;
    private String thirdParty;
    private String name;
    private String code;
    private String standardCode;
    private String dayLimitMoney;

    public String getOnceLimitMoney() {
        return onceLimitMoney;
    }

    public void setOnceLimitMoney(String onceLimitMoney) {
        this.onceLimitMoney = onceLimitMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthLimitMoney() {
        return monthLimitMoney;
    }

    public void setMonthLimitMoney(String monthLimitMoney) {
        this.monthLimitMoney = monthLimitMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    public String getDayLimitMoney() {
        return dayLimitMoney;
    }

    public void setDayLimitMoney(String dayLimitMoney) {
        this.dayLimitMoney = dayLimitMoney;
    }


}
