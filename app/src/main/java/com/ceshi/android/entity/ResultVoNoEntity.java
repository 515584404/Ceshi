package com.ceshi.android.entity;

/**
 * Created by killer on 15/8/17.
 * 单一数据类型
 */
public class ResultVoNoEntity {
    /**
     * message : 登录成功
     * result : true
     * JSESSIONID : B16450F1AA4395CE79B7F6C81E6CD122.tomcatcqdweb
     */

    private String message;
    private boolean result;
    private String JSESSIONID;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

}
