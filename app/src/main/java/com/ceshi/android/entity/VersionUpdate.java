package com.ceshi.android.entity;

/**
 * Created by Administrator on 2016/12/7.
 */
public class VersionUpdate {
    private boolean reusult;
    private String message;
    private String downloadUrl;

    public boolean isReusult() {
        return reusult;
    }

    public void setReusult(boolean reusult) {
        this.reusult = reusult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "VersionUpdate{" +
                "reusult=" + reusult +
                ", message='" + message + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
