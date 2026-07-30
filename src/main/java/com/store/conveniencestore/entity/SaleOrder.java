package com.store.conveniencestore.entity;

import java.time.LocalDateTime;

/**
 * 销售订单实体类。
 *
 * 一个 SaleOrder 对象对应 sale_order 表中的一条记录。
 */
public class SaleOrder {

    /**
     * 销售订单编号。
     * 对应数据库字段：id
     */
    private Integer id;

    /**
     * 销售时间。
     * 对应数据库字段：sale_time
     */
    private LocalDateTime saleTime;

    private String status;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(LocalDateTime saleTime) {
        this.saleTime = saleTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SaleOrder{" +
                "id=" + id +
                ", saleTime=" + saleTime +
                '}';
    }
}