package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.InventoryTransaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

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
}