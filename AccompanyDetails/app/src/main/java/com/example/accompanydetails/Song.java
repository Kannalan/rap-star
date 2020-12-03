package com.example.accompanydetails;

import androidx.annotation.VisibleForTesting;

class Song {
    private int Id;//id值主键
    private String author;//作者id
    private String name;
    private int accompaniment;//伴奏id
    private String lyricspath;//歌词路径
    private int style;
    private String path;
    private double money;//付费
    private String imgpath;//封面路径
    private int listenernum;//收听人数

    public Song(int id, String author, String name, int accompaniment, String lyricspath, int style, String path, double money, String imgpath, int listenernum) {
        Id = id;
        this.author = author;
        this.name = name;
        this.accompaniment = accompaniment;
        this.lyricspath = lyricspath;
        this.style = style;
        this.path = path;
        this.money = money;
        this.imgpath = imgpath;
        this.listenernum = listenernum;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccompaniment() {
        return accompaniment;
    }

    public void setAccompaniment(int accompaniment) {
        this.accompaniment = accompaniment;
    }

    public String getLyricspath() {
        return lyricspath;
    }

    public void setLyricspath(String lyricspath) {
        this.lyricspath = lyricspath;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public int getListenernum() {
        return listenernum;
    }

    public void setListenernum(int listenernum) {
        this.listenernum = listenernum;
    }
}
