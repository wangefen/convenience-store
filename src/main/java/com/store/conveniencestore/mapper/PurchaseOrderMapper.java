package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.PurchaseOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PurchaseOrderMapper {

    /**
     * 查询全部采购订单。
     */
    @Select("""
            SELECT id, supplier_id, purchase_time, status
            FROM purchase_order
            """)
    List<PurchaseOrder> findAll();

    /**
     * 根据 id 查询采购订单。
     */
    @Select("""
            SELECT id, supplier_id, purchase_time, status
            FROM purchase_order
            WHERE id = #{id}
            """)
    PurchaseOrder findById(Integer id);

    /**
     * 新增采购订单。
     */
    @Insert("""
            INSERT INTO purchase_order(supplier_id, purchase_time, status)
            VALUES(#{supplierId}, #{purchaseTime}, #{status})
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    void insert(PurchaseOrder purchaseOrder);

    /**
     * 只有处于 COMPLETED 状态的订单才能取消。
     *
     * 返回值：
     * 1：取消成功
     * 0：订单不存在、已经取消或状态发生变化
     */
    @Update("""
        UPDATE purchase_order
        SET status = 'CANCELLED'
        WHERE id = #{id}
          AND status = 'COMPLETED'
        """)
    int cancelIfCompleted(@Param("id") Integer id);



}