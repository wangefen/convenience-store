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
            SELECT id, supplier_id, purchase_time
            FROM purchase_order
            """)
    List<PurchaseOrder> findAll();

    /**
     * 根据 id 查询采购订单。
     */
    @Select("""
            SELECT id, supplier_id, purchase_time
            FROM purchase_order
            WHERE id = #{id}
            """)
    PurchaseOrder findById(Integer id);

    /**
     * 新增采购订单。
     */
    @Insert("""
            INSERT INTO purchase_order(supplier_id, purchase_time)
            VALUES(#{supplierId}, #{purchaseTime})
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    void insert(PurchaseOrder purchaseOrder);

    /**
     * 修改采购订单。
     */
    @Update("""
            UPDATE purchase_order
            SET supplier_id = #{supplierId},
                purchase_time = #{purchaseTime}
            WHERE id = #{id}
            """)
    void update(PurchaseOrder purchaseOrder);

    /**
     * 删除采购订单。
     */
    @Delete("""
            DELETE FROM purchase_order
            WHERE id = #{id}
            """)
    void delete(Integer id);
}