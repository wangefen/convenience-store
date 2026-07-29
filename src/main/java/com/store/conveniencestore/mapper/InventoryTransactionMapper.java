package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.InventoryTransaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InventoryTransactionMapper {

    /**
     * 新增一条库存流水。
     */
    @Insert("""
            INSERT INTO inventory_transaction(
                product_id,
                change_quantity,
                business_type,
                business_id,
                create_time
            )
            VALUES(
                #{productId},
                #{changeQuantity},
                #{businessType},
                #{businessId},
                #{createTime}
            )
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    void insert(InventoryTransaction transaction);

    /**
     * 查询全部库存流水，最新记录排在前面。
     */
    @Select("""
            SELECT id,
                   product_id,
                   change_quantity,
                   business_type,
                   business_id,
                   create_time
            FROM inventory_transaction
            ORDER BY create_time DESC, id DESC
            """)
    List<InventoryTransaction> findAll();

    /**
     * 查询指定商品的全部库存流水。
     */
    @Select("""
            SELECT id,
                   product_id,
                   change_quantity,
                   business_type,
                   business_id,
                   create_time
            FROM inventory_transaction
            WHERE product_id = #{productId}
            ORDER BY create_time DESC, id DESC
            """)
    List<InventoryTransaction> findByProductId(
            @Param("productId") Integer productId
    );
}