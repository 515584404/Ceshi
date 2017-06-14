package com.ceshi.android.entity;

/**
 * Created by ztr on 2016/05/14.
 */
public class ZqDetail {

    /**
     * id : 2
     * companyId : 1
     * companyName : 爱德华资产管理
     * productId : 1
     * productTitle : 新手团
     * title : 钢铁企业资金周转
     * href : #
     * totalMoney : 500000
     * ownMoney : 500000
     * rate : 16
     * valueTime : 2016-05-01
     * repayTime : 2016-05-08
     * duration : 7
     * durationType : DAY
     * status : BOUGHT
     * repayFrom : 企业经营所得
     * loanPurpose : 企业经营
     * loanerInfo : 格式:
     姓名:李四,性别:男,身份证号:12010678908888,年龄:39,征信情况:良好
     * otherInfo : 格式和借款人信息格式一样
     * createTime : 2016-04-25 16:19:49
     * updateTime : null
     */

    private int id;
    private int companyId;
    private String companyName;
    private int productId;
    private String productTitle;
    private String title;
    private String href;
    private String totalMoney;
    private String ownMoney;
    private String rate;
    private String valueTime;
    private String repayTime;
    private int duration;
    private String durationType;
    private String status;
    private String repayFrom;
    private String loanPurpose;
    private String loanerInfo;
    private String otherInfo;
    private String createTime;
    private Object updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getOwnMoney() {
        return ownMoney;
    }

    public void setOwnMoney(String ownMoney) {
        this.ownMoney = ownMoney;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getValueTime() {
        return valueTime;
    }

    public void setValueTime(String valueTime) {
        this.valueTime = valueTime;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRepayFrom() {
        return repayFrom;
    }

    public void setRepayFrom(String repayFrom) {
        this.repayFrom = repayFrom;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getLoanerInfo() {
        return loanerInfo;
    }

    public void setLoanerInfo(String loanerInfo) {
        this.loanerInfo = loanerInfo;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
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
}
