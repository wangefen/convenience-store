package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.SaleOrder;
import com.store.conveniencestore.mapper.SaleOrderMapper;
import com.store.conveniencestore.service.SaleOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 销售订单业务层实现类。
 */
@Service
public class SaleOrderServiceImpl implements SaleOrderService {

    private final SaleOrderMapper saleOrderMapper;

    public SaleOrderServiceImpl(SaleOrderMapper saleOrderMapper) {
        this.saleOrderMapper = saleOrderMapper;
    }

    @Override
    public List<SaleOrder> findAll() {
        return saleOrderMapper.findAll();
    }

    @Override
    public SaleOrder findById(Integer id) {
        return saleOrderMapper.findById(id);
    }

    @Override
    public void insert(SaleOrder saleOrder) {

        // 前端没有传销售时间时，自动使用当前时间
        if (saleOrder.getSaleTime() == null) {
            saleOrder.setSaleTime(LocalDateTime.now());
        }

        saleOrderMapper.insert(saleOrder);
    }

    @Override
    public void update(SaleOrder saleOrder) {
        saleOrderMapper.update(saleOrder);
    }

    @Override
    public void delete(Integer id) {
        saleOrderMapper.delete(id);
    }
}