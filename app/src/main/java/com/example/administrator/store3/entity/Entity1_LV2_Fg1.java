package com.example.administrator.store3.entity;

import android.graphics.Bitmap;

/**
 * 实体类
 * Created by Administrator on 2016/4/23.
 */
public class Entity1_LV2_Fg1 {
    int id;
    int Logo_picture;
    int picture;
    String distance;

    public Entity1_LV2_Fg1(int _id, int logo_picture, int _picture, String _distance) {
        id=_id;
        Logo_picture = logo_picture;
        picture=_picture;
        distance=_distance;
    }

    public Entity1_LV2_Fg1() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogo_picture() {
        return Logo_picture;
    }

    public void setLogo_picture(int logo_picture) {
        Logo_picture = logo_picture;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Entity1_LV2_Fg1{" +
                "id=" + id +
                ", Logo_picture=" + Logo_picture +
                ", picture=" + picture +
                ", distance='" + distance + '\'' +
                '}';
    }
}
