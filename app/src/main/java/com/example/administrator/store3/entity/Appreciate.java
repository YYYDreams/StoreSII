package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/23.
 * 鉴赏实体类
 */
public class Appreciate implements Serializable {
    int id;
    String createDate;
    String modifyDate;
    int orders;
    String propaganda;
    String title;
    String introduce;
    int totle;
    String pageNumber;
    String pageSize;
    String pageCountStart;

    public Appreciate(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getPropaganda() {
        return propaganda;
    }

    public void setPropaganda(String propaganda) {
        this.propaganda = propaganda;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getTotle() {
        return totle;
    }

    public void setTotle(int totle) {
        this.totle = totle;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageCountStart() {
        return pageCountStart;
    }

    public void setPageCountStart(String pageCountStart) {
        this.pageCountStart = pageCountStart;
    }

    @Override
    public String toString() {
        return "Appreciate{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", orders=" + orders +
                ", propaganda='" + propaganda + '\'' +
                ", title='" + title + '\'' +
                ", introduce='" + introduce + '\'' +
                ", totle=" + totle +
                ", pageNumber='" + pageNumber + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", pageCountStart='" + pageCountStart + '\'' +
                '}';
    }
}
