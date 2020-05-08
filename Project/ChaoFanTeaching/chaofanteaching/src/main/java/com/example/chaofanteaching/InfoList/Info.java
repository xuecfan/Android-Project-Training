package com.example.chaofanteaching.InfoList;

public class Info {
    private String name;
    private String college;
    private String subject;
    private String school;
    private String price;//time
    private String experience;//dis
    private String user;

    public Info(String _name,String _school,String _college,String _subject,String _price,String _experience,String _user){
        this.name=_name;
        this.college=_college;
        this.subject=_subject;
        this.school=_school;
        this.price=_price;
        this.experience=_experience;
        this.user=_user;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExperience() {
        return experience;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
