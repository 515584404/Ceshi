package com.ceshi.android.entity;

/**
 * Created by Administrator on 2016/5/10.
 */
public class WithdrawEntity {

    /**
     * isSetTranPassword : true
     * isBindBankCard : true
     * bankCardNum : 6226****6152
     * bankCardName : 光大银行
     * availableMoney : 3990.00
     */

    private boolean isSetTranPassword;
    private boolean isBindBankCard;
    private String bankCardNum;
    private String bankCardName;
    private String availableMoney;

    public boolean isIsSetTranPassword() {
        return isSetTranPassword;
    }

    public void setIsSetTranPassword(boolean isSetTranPassword) {
        this.isSetTranPassword = isSetTranPassword;
    }

    public boolean isIsBindBankCard() {
        return isBindBankCard;
    }

    public void setIsBindBankCard(boolean isBindBankCard) {
        this.isBindBankCard = isBindBankCard;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    @Override
    public String toString() {
        return "WithdrawEntity{" +
                "isSetTranPassword=" + isSetTranPassword +
                ", isBindBankCard=" + isBindBankCard +
                ", bankCardNum='" + bankCardNum + '\'' +
                ", bankCardName='" + bankCardName + '\'' +
                ", availableMoney='" + availableMoney + '\'' +
                '}';
    }
}
