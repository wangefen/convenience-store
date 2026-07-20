package com.store.conveniencestore.entity;

import java.time.LocalDateTime;

/**
 * 采购订单实体类。
 *
 * 一个 PurchaseOrder 对象对应 purchase_order 表中的一条记录。
 */
public class PurchaseOrder {

    /**
     * 采购订单编号。
     * 对应数据库字段：id
     */
    private Integer id;

    /**
     * 供应商编号。
     * 对应数据库字段：supplier_id
     */
    private Integer supplierId;

    /**
     * 采购时间。
     * 对应数据库字段：purchase_time
     */
    private LocalDateTime purchaseTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", supplierId=" + supplierId +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}