package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/1.
 * 商品实体类
 */
public class Goods implements Serializable {
    String image;
    String neirong;
    String guige;
    float jiage;
    int jianshu=0;
    boolean selected=false;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJianshu() {
        return jianshu;
    }

    public void setJianshu(int jianshu) {
        this.jianshu = jianshu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public float getJiage() {
        return jiage;
    }

    public void setJiage(float jiage) {
        this.jiage = jiage;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "image='" + image + '\'' +
                ", neirong='" + neirong + '\'' +
                ", guige='" + guige + '\'' +
                ", jiage=" + jiage +
                ", jianshu=" + jianshu +
                ", selected=" + selected +
                ", id=" + id +
                '}';
    }
}
