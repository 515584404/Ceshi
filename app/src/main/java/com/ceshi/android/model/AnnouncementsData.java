package com.ceshi.android.model;

import java.util.List;

/**
 * Created by ztr on 2016/05/03.
 */
public class AnnouncementsData {

    /**
     * announcements : [{"id":11,"userId":1,"type":"DEPOSIT","status":"UNREAD","title":"充值","content":"您与2016-04-26 10:04:27充值的10000元已到帐, 感谢您的信任与支持!","createTime":"2016-04-26 10:04:27","updateTime":null},{"id":12,"userId":1,"type":"BUY","status":"UNREAD","title":"投资","content":"您与2016-04-26 10:04:41投资的月月团1000元已成功, 感谢您的信任与支持!","createTime":"2016-04-26 10:04:41","updateTime":null},{"id":13,"userId":1,"type":"BUY","status":"UNREAD","title":"投资","content":"您与2016-04-26 10:04:58投资的季季团2000元已成功, 感谢您的信任与支持!","createTime":"2016-04-26 10:04:58","updateTime":null},{"id":14,"userId":1,"type":"BUY","status":"UNREAD","title":"投资","content":"您与2016-04-26 10:05:07投资的季季团3000元已成功, 感谢您的信任与支持!","createTime":"2016-04-26 10:05:07","updateTime":null},{"id":15,"userId":1,"type":"BUY","status":"UNREAD","title":"投资","content":"您与2016-05-02 11:38:54投资的月月团10元已成功, 感谢您的信任与支持!","createTime":"2016-05-02 11:38:54","updateTime":null}]
     * currentPage : 1
     * totalpage : 0
     */

    private int currentPage;
    private int totalpage;
    /**
     * id : 11
     * userId : 1
     * type : DEPOSIT
     * status : UNREAD
     * title : 充值
     * content : 您与2016-04-26 10:04:27充值的10000元已到帐, 感谢您的信任与支持!
     * createTime : 2016-04-26 10:04:27
     * updateTime : null
     */

    private List<AnnouncementsEntity> announcements;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public List<AnnouncementsEntity> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<AnnouncementsEntity> announcements) {
        this.announcements = announcements;
    }

    public static class AnnouncementsEntity {
        private int id;
        private int userId;
        private String type;
        private String status;
        private String title;
        private String content;
        private String createTime;
        private Object updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
}
