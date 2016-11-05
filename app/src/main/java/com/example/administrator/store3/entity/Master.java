package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/23.
 * 大师实体类
 */
public class Master implements Serializable {
    int id;
    String image;
    int praise=120;
    String username="齐白石";
    boolean isSelected=false;
    public Master() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public boolean isSelected() {
        return isSelected;
    }
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "Master{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", praise=" + praise +
                ", name='" + username + '\'' +
                '}';
    }
}
