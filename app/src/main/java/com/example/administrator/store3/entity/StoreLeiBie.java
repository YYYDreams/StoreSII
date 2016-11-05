package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/27.
 * 店铺类别实体类
 */
public class StoreLeiBie implements Serializable {
    int id;
    String name;

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

    @Override
    public String toString() {
        return "StoreLeiBie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
