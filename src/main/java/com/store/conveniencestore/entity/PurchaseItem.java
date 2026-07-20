package com.store.conveniencestore.entity;

import java.math.BigDecimal;

/**
 * 采购明细实体类。
 *
 * 一个 PurchaseItem 对象表示采购订单中的一项商品。
 */
public class PurchaseItem {

    /**
     * 采购明细编号。
     * 对应数据库字段：id
     */
    private Integer id;

    /**
     * 所属采购订单编号。
     * 对应数据库字段：purchase_order_id
     */
    private Integer purchaseOrderId;

    /**
     * 商品编号。
     * 对应数据库字段：product_id
     */
    private Integer productId;

    /**
     * 采购数量。
     * 对应数据库字段：quantity
     */
    private Integer quantity;

    /**
     * 商品采购单价。
     * 对应数据库字段：purchase_price
     */
    private BigDecimal purchasePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public String toString() {
        return "PurchaseItem{" +
                "id=" + id +
                ", purchaseOrderId=" + purchaseOrderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", purchasePrice=" + purchasePrice +
                '}';
    }
}