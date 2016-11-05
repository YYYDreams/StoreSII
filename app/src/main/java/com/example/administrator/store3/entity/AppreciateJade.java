package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/24.
 * 鉴赏子页面实体类
 */
public class AppreciateJade implements Serializable {
    int id;
    String appraisal;
    String picture;

    public AppreciateJade(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
