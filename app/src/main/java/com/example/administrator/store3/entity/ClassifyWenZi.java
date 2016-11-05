package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ClassifyWenZi implements Serializable {
   //分类id
    private int id;
  //分类名
    private  String name;

    public ClassifyWenZi() {
        super();

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

    @Override
    public String toString() {
        return "ClassifyUtil{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
