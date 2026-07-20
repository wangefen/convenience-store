package com.store.conveniencestore.service;

import com.store.conveniencestore.entity.SaleOrder;

import java.util.List;

/**
 * 销售订单业务层接口。
 */
public interface SaleOrderService {

    List<SaleOrder> findAll();

    SaleOrder findById(Integer id);

    void insert(SaleOrder saleOrder);

    void update(SaleOrder saleOrder);

    void delete(Integer id);
}