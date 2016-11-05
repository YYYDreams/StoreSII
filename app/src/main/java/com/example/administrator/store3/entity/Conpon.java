package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/23.
 * 优惠券实体类
 */
public class Conpon implements Serializable {
    int id;
    String picture;
    String storeName;
    String useRule;
    String useTime;
    int conpon;

    public Conpon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUseRule() {
        return useRule;
    }

    public void setUseRule(String useRule) {
        this.useRule = useRule;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public int getConpon() {
        return conpon;
    }

    public void setConpon(int conpon) {
        this.conpon = conpon;
    }

    @Override
    public String toString() {
        return "Conpon{" +
                "id=" + id +
                ", picture='" + picture + '\'' +
                ", storeName='" + storeName + '\'' +
                ", useRule='" + useRule + '\'' +
                ", useTime='" + useTime + '\'' +
                ", conpon=" + conpon +
                '}';
    }
}
