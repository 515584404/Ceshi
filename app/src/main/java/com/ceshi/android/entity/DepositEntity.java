package com.ceshi.android.entity;

/**
 * Created by ztr on 2016/05/05.
 */
public class DepositEntity {

    /**
     * isBindBankCard : true
     * availableMoney : 3990.00
     * bankCard : 光大银行 6226****6152
     * moneyLimitDescription : 限额(元): 单笔5万/单日5万/单月2
     */

    private boolean isBindBankCard;
    private String availableMoney;
    private String bankCard;
    private String moneyLimitDescription;

    public boolean isIsBindBankCard() {
        return isBindBankCard;
    }

    public void setIsBindBankCard(boolean isBindBankCard) {
        this.isBindBankCard = isBindBankCard;
    }

    public String getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getMoneyLimitDescription() {
        return moneyLimitDescription;
    }

    public void setMoneyLimitDescription(String moneyLimitDescription) {
        this.moneyLimitDescription = moneyLimitDescription;
    }
}
