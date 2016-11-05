package com.example.administrator.store3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/27.
 * 店铺实体类
 */
public class Store implements Serializable {
    int id;
    String storeName;
    String province;
    String city;
    String address;
    String logo;
    String storeImg;
    String latStr;
    String lngStr;
    String distace;
    int totalPages;
    int pageSize;
    int pageNumber;

    public Store() {

    }
    public String getLatStr() {
        return latStr;
    }

    public void setLatStr(String latStr) {
        this.latStr = latStr;
    }

    public String getLngStr() {
        return lngStr;
    }

    public void setLngStr(String lngStr) {
        this.lngStr = lngStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public String getDistace() {
        return distace;
    }

    public void setDistace(String distace) {
        this.distace = distace;
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
        return "Store{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", logo='" + logo + '\'' +
                ", storeImg='" + storeImg + '\'' +
                ", latStr='" + latStr + '\'' +
                ", lngStr='" + lngStr + '\'' +
                ", distace='" + distace + '\'' +
                ", totalPages=" + totalPages +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
