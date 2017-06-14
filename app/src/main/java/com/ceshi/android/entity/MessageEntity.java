package com.ceshi.android.entity;

import java.util.List;

/**
 * Created by ztr on 2016/05/10.
 */
public class MessageEntity {

    /**
     * totalpage : 1
     * userMessages : [{"content":"您与2016-05-10 14:24:44申请的1元已受理, 感谢您的信任与支持!","id":21,"createTime":"2016-05-10 14:24:44","title":"提现","updateTime":null,"status":"UNREAD","userId":1,"type":"WITHDRAW"},{"content":"您与2016-05-10 11:42:15申请的25元已受理, 感谢您的信任与支持!","id":20,"createTime":"2016-05-10 11:42:15","title":"提现","updateTime":null,"status":"UNREAD","userId":1,"type":"WITHDRAW"},{"content":"您与2016-05-10 11:39:42申请的258元已受理, 感谢您的信任与支持!","id":19,"createTime":"2016-05-10 11:39:42","title":"提现","updateTime":null,"status":"UNREAD","userId":1,"type":"WITHDRAW"},{"content":"您与2016-05-10 11:37:05申请的23元已受理, 感谢您的信任与支持!","id":18,"createTime":"2016-05-10 11:37:05","title":"提现","updateTime":null,"status":"UNREAD","userId":1,"type":"WITHDRAW"},{"content":"您与2016-05-10 11:34:10申请的123元已受理, 感谢您的信任与支持!","id":17,"createTime":"2016-05-10 11:34:10","title":"提现","updateTime":null,"status":"UNREAD","userId":1,"type":"WITHDRAW"},{"content":"您与2016-05-02 11:38:54投资的月月团10元已成功, 感谢您的信任与支持!","id":15,"createTime":"2016-05-02 11:38:54","title":"投资","updateTime":"2016-05-05 09:41:39","status":"READED","userId":1,"type":"BUY"},{"content":"您与2016-04-26 10:05:07投资的季季团3000元已成功, 感谢您的信任与支持!","id":14,"createTime":"2016-04-26 10:05:07","title":"投资","updateTime":"2016-05-05 09:41:42","status":"READED","userId":1,"type":"BUY"},{"content":"您与2016-04-26 10:04:58投资的季季团2000元已成功, 感谢您的信任与支持!","id":13,"createTime":"2016-04-26 10:04:58","title":"投资","updateTime":"2016-05-05 09:41:43","status":"READED","userId":1,"type":"BUY"},{"content":"您与2016-04-26 10:04:41投资的月月团1000元已成功, 感谢您的信任与支持!","id":12,"createTime":"2016-04-26 10:04:41","title":"投资","updateTime":"2016-05-03 16:11:13","status":"READED","userId":1,"type":"BUY"},{"content":"您与2016-04-26 10:04:27充值的10000元已到帐, 感谢您的信任与支持!","id":11,"createTime":"2016-04-26 10:04:27","title":"充值","updateTime":"2016-05-03 16:11:11","status":"READED","userId":1,"type":"DEPOSIT"}]
     * currentPage : 1
     */

    private int totalpage;
    private int currentPage;
    /**
     * content : 您与2016-05-10 14:24:44申请的1元已受理, 感谢您的信任与支持!
     * id : 21
     * createTime : 2016-05-10 14:24:44
     * title : 提现
     * updateTime : null
     * status : UNREAD
     * userId : 1
     * type : WITHDRAW
     */

    private List<UserMessagesEntity> userMessages;

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<UserMessagesEntity> getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(List<UserMessagesEntity> userMessages) {
        this.userMessages = userMessages;
    }

    public static class UserMessagesEntity {
        private String content;
        private int id;
        private String createTime;
        private String title;
        private Object updateTime;
        private String status;
        private int userId;
        private String type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
    }
}
