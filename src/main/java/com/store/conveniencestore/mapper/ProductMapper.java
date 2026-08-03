package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT id, name, category_id, sale_price, stock FROM  product")
    List<Product> findAll();

    @Select("SELECT id, name, category_id, sale_price, stock FROM product WHERE id = #{id}")
    Product findById(Integer id);

    @Insert("INSERT INTO product(name, category_id, sale_price) VALUES (#{name}, #{categoryId}, #{salePrice})")//#{name}相当于mybatis去执行product.getname
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn = "id") //useGeneratedKeys = true,使用数据库自动生成的主键，并获取这个主键值。keyProperty = "id",把数据库生成的主键值，写入传入对象的 id 属性。
    void insert(Product product);

    @Update("UPDATE  product SET name = #{name}, category_id = #{categoryId}, sale_price = #{salePrice} WHERE id = #{id}")
    int update(Product product);

    @Update("UPDATE product SET stock = stock + #{quantity} WHERE id = #{productId}")
    int increaseStock(
            //Mapper 中增、删、改方法返回的 int，一般表示 SQL 影响的数据行数；
            // return 由 MyBatis 自动处理。
            //@Param 会告诉 MyBatis：把第一个值 10 标记为 productI把第二个值 5 标记为 quantity
            @Param("productId") Integer productId,
            @Param("quantity") Integer quantity
    );

    /**
     * 原子扣减库存。
     *
     * 只有商品存在并且库存充足时才会执行扣减。
     *
     * 返回值：
     * 1：扣减成功
     * 0：商品不存在或库存不足
     */
    @Update("""
        UPDATE product
        SET stock = stock - #{quantity}
        WHERE id = #{productId}
          AND stock >= #{quantity}
        """)
    int decreaseStock(
            @Param("productId") Integer productId,
            @Param("quantity") Integer quantity
    );

    @Delete("DELETE FROM product WHERE id = #{id}")
    int delete(Integer id);}
