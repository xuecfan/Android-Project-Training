package com.example.chaofanteaching.InfoList;

public class Info {
    private String name;
    private String college;
    private String subject;
    private String price;

    public Info(String _name,String _college,String _subject,String _price){
        this.name=_name;
        this.college=_college;
        this.subject=_subject;
        this.price=_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return college;
    }

    public void setFrom(String college) {
        this.college = college;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
