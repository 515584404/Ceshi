package com.ceshi.android.entity;

/**发送注册验证码解析类
 * Created by Mark on 2016/10/29.
 */
public class PostVerificationEntity {

    public String jsessionId;
    public String message;
    public boolean result;

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

    @Override
    public String toString() {
        return "PostVerificationEntity{" +
                "jsessionId='" + jsessionId + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
