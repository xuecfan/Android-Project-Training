package com.example.chaofanteaching.comments.fragments;

/**
 * 评论实体类
 */
public class Comment {
    private String teacherId;
    private String parentId;
    private String teacherName;
    private String parentName;
    private String date;//评论日期
    private String content;//评论内容
    private int isOnTime;//是否准时
    private int teachingQuality;//教学质量

    public Comment() {
    }

    public Comment(String teacherId, String parentId, String teacherName, String parentName, String date, String content, int isOnTime, int teachingQuality) {
        this.teacherId = teacherId;
        this.parentId = parentId;
        this.teacherName = teacherName;
        this.parentName = parentName;
        this.date = date;
        this.content = content;
        this.isOnTime = isOnTime;
        this.teachingQuality = teachingQuality;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsOnTime() {
        return isOnTime;
    }

    public void setIsOnTime(int isOnTime) {
        this.isOnTime = isOnTime;
    }

    public int getTeachingQuality() {
        return teachingQuality;
    }

    public void setTeachingQuality(int teachingQuality) {
        this.teachingQuality = teachingQuality;
    }
}
