package com.ceshi.android.model;

/**
 * Created by killer on 15/8/17.
 * 单一数据类型
 */
public class ResultVoNoData{


    /**
     * result : true
     * message : 登录成功
     */

    private boolean result;
    private String message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultVoNoData{" +
                "result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
