package com.ceshi.android.entity;

/**
 * Created by ztr on 2016/05/05.
 */
public class SendMsgCodeEntity {

    /**
     * jsessionId : E9586FEF7A36AB199D5CD9274088D802.tomcatdemo
     * message : 图片验证码错误
     * result : false
     */

    private String jsessionId;
    private String message;
    private boolean result;

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }

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
}
