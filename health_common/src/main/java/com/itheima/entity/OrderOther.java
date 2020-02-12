package com.itheima.entity;

import java.io.Serializable;

/**
 * @Author: 汪诚
 * @Date: 2020/2/11 0:16
 */
public class OrderOther implements Serializable {

    private Integer date;
    private Integer number;
    private Integer reservations;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getReservations() {
        return reservations;
    }

    public void setReservations(Integer reservations) {
        this.reservations = reservations;
    }
}
