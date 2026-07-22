package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.SaleItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 销售明细数据访问层。
 */
@Mapper
public interface SaleItemMapper {

    @Select("""
            SELECT id,
                   sale_order_id,
                   product_id,
                   quantity,
                   sale_price
            FROM sale_item
            """)
    List<SaleItem> findAll();

    @Select("""
            SELECT id,
                   sale_order_id,
                   product_id,
                   quantity,
                   sale_price
            FROM sale_item
            WHERE id = #{id}
            """)
    SaleItem findById(Integer id);

    @Select("""
            SELECT id,
                   sale_order_id,
                   product_id,
                   quantity,
                   sale_price
            FROM sale_item
            WHERE sale_order_id = #{saleOrderId}
            """)
    List<SaleItem> findBySaleOrderId(Integer saleOrderId);

    @Insert("""
            INSERT INTO sale_item(
                sale_order_id,
                product_id,
                quantity,
                sale_price
            )
            VALUES(
                #{saleOrderId},
                #{productId},
                #{quantity},
                #{salePrice}
            )
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    void insert(SaleItem saleItem);

    @Update("""
            UPDATE sale_item
            SET sale_order_id = #{saleOrderId},
                product_id = #{productId},
                quantity = #{quantity},
                sale_price = #{salePrice}
            WHERE id = #{id}
            """)
    void update(SaleItem saleItem);

    @Delete("""
            DELETE FROM sale_item
            WHERE id = #{id}
            """)
    void delete(Integer id);
}