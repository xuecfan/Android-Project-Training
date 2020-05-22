package com.example.chaofanteaching.comments.fragments;

/**
 * 评论实体类
 */
public class Comment {
    private String user;
    private String objUser;
//    private String date;//评论日期
    private String content;//评论内容
    private String isOnTime;//是否准时
    private String teachingQuality;//教学质量

    public Comment() {
    }

    public Comment(String user, String objUser, String content, String isOnTime, String teachingQuality) {
        this.user = user;
        this.objUser = objUser;
        this.content = content;
        this.isOnTime = isOnTime;
        this.teachingQuality = teachingQuality;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getObjUser() {
        return objUser;
    }

    public void setObjUser(String objUser) {
        this.objUser = objUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsOnTime() {
        return isOnTime;
    }

    public void setIsOnTime(String isOnTime) {
        this.isOnTime = isOnTime;
    }

    public String getTeachingQuality() {
        return teachingQuality;
    }

    public void setTeachingQuality(String teachingQuality) {
        this.teachingQuality = teachingQuality;
    }
}
