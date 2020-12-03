package com.example.demoviewpage1122;

public class Accompany {
    private int id;
    private String name;
    private String introduction;
    private double money;
    private String coverpath;
    private String author;
    private int usednum;

    public Accompany(int id, String name, String introduction, double money, String coverpath, String author, int usednum) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.money = money;
        this.coverpath = coverpath;
        this.author = author;
        this.usednum = usednum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCoverpath() {
        return coverpath;
    }

    public void setCoverpath(String coverpath) {
        this.coverpath = coverpath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getUsednum() {
        return usednum;
    }

    public void setUsednum(int usednum) {
        this.usednum = usednum;
    }
}
