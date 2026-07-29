package com.store.conveniencestore.entity;

import java.time.LocalDateTime;

/**
 * 库存流水实体类。
 *
 * 每当库存增加或减少时，记录一条流水。
 */
public class InventoryTransaction {

    private Integer id;

    private Integer productId;

    /**
     * 库存变化数量：
     * 正数表示增加，负数表示减少。
     */
    private Integer changeQuantity;

    /**
     * 业务类型：
     * PURCHASE：采购
     * SALE：销售
     */
    private String businessType;

    /**
     * 对应的采购订单或销售订单编号。
     */
    private Integer businessId;

    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getChangeQuantity() {
        return changeQuantity;
    }

    public void setChangeQuantity(Integer changeQuantity) {
        this.changeQuantity = changeQuantity;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "InventoryTransaction{" +
                "id=" + id +
                ", productId=" + productId +
                ", changeQuantity=" + changeQuantity +
                ", businessType='" + businessType + '\'' +
                ", businessId=" + businessId +
                ", createTime=" + createTime +
                '}';
    }
}