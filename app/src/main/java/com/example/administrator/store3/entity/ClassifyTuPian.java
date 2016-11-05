package com.example.administrator.store3.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ClassifyTuPian implements Serializable {
    private int id;
    private String name;
    private String image;

    public ClassifyTuPian() {
        super();
    }

    protected ClassifyTuPian(Integer id,String name,String image) {
       this.id=id;
        this.name=name;
        this.image=image;
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


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ClassifyTuPian{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }



}
