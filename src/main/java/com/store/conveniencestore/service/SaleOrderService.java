package com.store.conveniencestore.service;

import com.store.conveniencestore.dto.SaleOrderCreateRequest;
import com.store.conveniencestore.dto.SaleOrderResponse;
import com.store.conveniencestore.entity.SaleOrder;

import java.util.List;

/**
 * 销售订单业务层接口。
 */
public interface SaleOrderService {

    List<SaleOrder> findAll();

    SaleOrder findById(Integer id);

    /**
     * 创建一张销售订单和多条销售明细。
     */
    SaleOrderResponse createOrder(
            SaleOrderCreateRequest request
    );


    void update(SaleOrder saleOrder);

    void delete(Integer id);
}