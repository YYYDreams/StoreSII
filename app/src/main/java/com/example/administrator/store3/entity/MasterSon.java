package com.example.administrator.store3.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 * 大师子页面实体类
 */
public class MasterSon implements Serializable {
    private String image;
    private String name;
    private String yinzhang;
    private String jianjie;
    private String mingyan;

    public MasterSon() {
        super();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYinzhang() {
        return yinzhang;
    }

    public void setYinzhang(String yinzhang) {
        this.yinzhang = yinzhang;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public String getMingyan() {
        return mingyan;
    }

    public void setMingyan(String mingyan) {
        this.mingyan = mingyan;
    }

    @Override
    public String toString() {
        return "MasterSon{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", yinzhang='" + yinzhang + '\'' +
                ", jianjie='" + jianjie + '\'' +
                ", mingyan='" + mingyan + '\'' +
                '}';
    }
}
