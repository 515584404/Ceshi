package com.ceshi.android.entity;

/**
 * Created by ztr on 2016/05/01.
 */
public class SafeEntity {

    /**
     * isSetTranPassword : true
     * isSetIdentification : true
     * isBindBankCard : true
     * identification : 阚* 3411****4814
     * mobile : 1388888****
     * bankCard : 光大银行 6226****6152
     */

    private boolean isSetTranPassword;
    private boolean isSetIdentification;
    private boolean isBindBankCard;
    private String identification;
    private String mobile;
    private String bankCard;

    public boolean isIsSetTranPassword() {
        return isSetTranPassword;
    }

    public void setIsSetTranPassword(boolean isSetTranPassword) {
        this.isSetTranPassword = isSetTranPassword;
    }

    public boolean isIsSetIdentification() {
        return isSetIdentification;
    }

    public void setIsSetIdentification(boolean isSetIdentification) {
        this.isSetIdentification = isSetIdentification;
    }

    public boolean isIsBindBankCard() {
        return isBindBankCard;
    }

    public void setIsBindBankCard(boolean isBindBankCard) {
        this.isBindBankCard = isBindBankCard;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }
}
