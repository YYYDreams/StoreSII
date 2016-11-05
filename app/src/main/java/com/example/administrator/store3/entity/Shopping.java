package com.example.administrator.store3.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/1.
 * 购物车实体类
 */
public class Shopping implements Serializable {
    String dianming;
    ArrayList<Goods>goodses;
    boolean selected=false;

    public String getDianming() {
        return dianming;
    }

    public void setDianming(String dianming) {
        this.dianming = dianming;
    }

    public ArrayList<Goods> getGoodses() {
        return goodses;
    }

    public void setGoodses(ArrayList<Goods> goodses) {
        this.goodses = goodses;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Shopping{" +
                "dianming='" + dianming + '\'' +
                ", goodses=" + goodses +
                ", selected=" + selected +
                '}';
    }
}
