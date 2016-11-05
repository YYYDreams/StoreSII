package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/7.
 * 品牌实体类
 */
public class Brand implements Serializable{
    int id;
    int orders;
    String logo;
    String name;
    String type;
    String url;
    String introduction;
    int totalPages;
    int pageSize;
    int pageNumber;

    public Brand() {
       super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", orders=" + orders +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
