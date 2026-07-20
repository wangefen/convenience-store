package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.PurchaseItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 采购明细数据访问层。
 *
 * 负责直接操作数据库中的 purchase_item 表。
 */
@Mapper
public interface PurchaseItemMapper {

    /**
     * 查询全部采购明细。
     */
    @Select("""
            SELECT id,
                   purchase_order_id,
                   product_id,
                   quantity,
                   purchase_price
            FROM purchase_item
            """)
    List<PurchaseItem> findAll();


    /**
     * 根据采购明细 id 查询。
     */
    @Select("""
            SELECT id,
                   purchase_order_id,
                   product_id,
                   quantity,
                   purchase_price
            FROM purchase_item
            WHERE id = #{id}
            """)
    PurchaseItem findById(Integer id);


    /**
     * 根据采购订单 id 查询该订单中的全部商品明细。
     *
     * 一张采购订单可能对应多条采购明细，
     * 所以这里返回 List<PurchaseItem>。
     */
    @Select("""
            SELECT id,
                   purchase_order_id,
                   product_id,
                   quantity,
                   purchase_price
            FROM purchase_item
            WHERE purchase_order_id = #{purchaseOrderId}
            """)
    List<PurchaseItem> findByPurchaseOrderId(Integer purchaseOrderId);


    /**
     * 新增采购明细。
     */
    @Insert("""
            INSERT INTO purchase_item(
                purchase_order_id,
                product_id,
                quantity,
                purchase_price
            )
            VALUES(
                #{purchaseOrderId},
                #{productId},
                #{quantity},
                #{purchasePrice}
            )
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    void insert(PurchaseItem purchaseItem);


    /**
     * 修改采购明细。
     */
    @Update("""
            UPDATE purchase_item
            SET purchase_order_id = #{purchaseOrderId},
                product_id = #{productId},
                quantity = #{quantity},
                purchase_price = #{purchasePrice}
            WHERE id = #{id}
            """)
    void update(PurchaseItem purchaseItem);


    /**
     * 根据采购明细 id 删除。
     */
    @Delete("""
            DELETE FROM purchase_item
            WHERE id = #{id}
            """)
    void delete(Integer id);
}