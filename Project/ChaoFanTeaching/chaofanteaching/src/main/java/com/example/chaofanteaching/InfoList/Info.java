package com.example.chaofanteaching.InfoList;

public class Info {
    private String name;
    private String college;
    private String subject;
    private String school;

    public Info(String _name,String _school,String _college,String _subject){
        this.name=_name;
        this.college=_college;
        this.subject=_subject;
        this.school=_school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
