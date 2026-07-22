package com.store.conveniencestore.entity;

import java.math.BigDecimal;

/**
 * 销售明细实体类。
 *
 * 一个 SaleItem 对象对应 sale_item 表中的一条记录。
 */
public class SaleItem {

    private Integer id;

    private Integer saleOrderId;

    private Integer productId;

    private Integer quantity;

    private BigDecimal salePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(Integer saleOrderId) {
        this.saleOrderId = saleOrderId;
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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "id=" + id +
                ", saleOrderId=" + saleOrderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", salePrice=" + salePrice +
                '}';
    }
}