package com.ceshi.android.entity;

/**注册完成的返回信息解析类
 * Created by Mark on 2016/10/31.
 */
public class RegistFinishEntity {

    public String message;

    public boolean result;

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
