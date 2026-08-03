package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.PurchaseItem;
import com.store.conveniencestore.exception.ResourceNotFoundException;
import com.store.conveniencestore.mapper.PurchaseItemMapper;
import com.store.conveniencestore.mapper.PurchaseOrderMapper;
import com.store.conveniencestore.service.PurchaseItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购明细业务层实现类。
 */
@Service
public class PurchaseItemServiceImpl implements PurchaseItemService {

    private final PurchaseItemMapper purchaseItemMapper;
    private final PurchaseOrderMapper purchaseOrderMapper;


    /**
     * 构造器注入 PurchaseItemMapper。
     */
    public PurchaseItemServiceImpl(
            PurchaseItemMapper purchaseItemMapper, PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseItemMapper = purchaseItemMapper;

        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    /**
     * 查询全部采购明细。
     */
    @Override
    public List<PurchaseItem> findAll() {
        return purchaseItemMapper.findAll();
    }

    /**
     * 根据明细 id 查询。
     */
    @Override
    public PurchaseItem findById(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "采购明细编号必须大于0"
            );
        }

        PurchaseItem purchaseItem =
                purchaseItemMapper.findById(id);

        if (purchaseItem == null) {
            throw new ResourceNotFoundException(
                    "采购明细不存在，明细编号：" + id
            );
        }

        return purchaseItem;
    }

    /**
     * 查询某张采购订单的全部明细。
     */
    @Override
    public List<PurchaseItem> findByPurchaseOrderId(
            Integer purchaseOrderId) {

        if (purchaseOrderId == null
                || purchaseOrderId <= 0) {

            throw new IllegalArgumentException(
                    "采购订单编号必须大于0"
            );
        }

        if (purchaseOrderMapper.findById(
                purchaseOrderId) == null) {

            throw new ResourceNotFoundException(
                    "采购订单不存在，订单编号："
                            + purchaseOrderId
            );
        }

        return purchaseItemMapper
                .findByPurchaseOrderId(purchaseOrderId);
    }

}