package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

import java.util.List;

@Mapper
public interface ProductMapper {

    /**
     * 根据可选条件查询商品。
     *
     * 所有参数都为空时查询全部商品。
     */
    List<Product> search(
            @Param("keyword") String keyword,
            @Param("categoryId") Integer categoryId,
            @Param(value = "minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice

    );

    /**
     * 根据商品编号查询商品。
     */
    Product findById(@Param("id") Integer id);

    /**
     * 新增商品。
     */
    void insert(Product product);

    /**
     * 修改商品基本信息。
     */
    int update(Product product);

    /**
     * 墺加商品库存。
     */
    int increaseStock(
            @Param("productId") Integer productId,
            @Param("quantity") Integer quantity
    );

    /**
     * 原子扣减库存。
     *
     * 只有库存充足时才执行扣减。
     *
     * @return 1 表示扣减成功，0 表示商品不存在或库存不足
     */
    int decreaseStock(
            @Param("productId") Integer productId,
            @Param("quantity") Integer quantity
    );

    /**
     * 根据商品编号删除商品。
     */
    int delete(@Param("id") Integer id);
}