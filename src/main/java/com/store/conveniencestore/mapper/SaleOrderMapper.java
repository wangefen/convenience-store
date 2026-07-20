package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.SaleOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 销售订单数据访问层。
 *
 * 负责直接操作 sale_order 表。
 */
@Mapper
public interface SaleOrderMapper {

    /**
     * 查询全部销售订单。
     */
    @Select("""
            SELECT id, sale_time
            FROM sale_order
            """)
    List<SaleOrder> findAll();

    /**
     * 根据 id 查询销售订单。
     */
    @Select("""
            SELECT id, sale_time
            FROM sale_order
            WHERE id = #{id}
            """)
    SaleOrder findById(Integer id);

    /**
     * 新增销售订单。
     */
    @Insert("""
            INSERT INTO sale_order(sale_time)
            VALUES(#{saleTime})
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    void insert(SaleOrder saleOrder);

    /**
     * 修改销售订单时间。
     */
    @Update("""
            UPDATE sale_order
            SET sale_time = #{saleTime}
            WHERE id = #{id}
            """)
    void update(SaleOrder saleOrder);

    /**
     * 根据 id 删除销售订单。
     */
    @Delete("""
            DELETE FROM sale_order
            WHERE id = #{id}
            """)
    void delete(Integer id);
}