package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT id, name, category_id, sale_price FROM  product")
    List<Product> findAll();

    @Select("SELECT * FROM product WHERE id = #{id}")
    Product findById(Integer id);

    @Insert("INSERT INTO product(name, category_id, sale_price) VALUES (#{name}, #{categoryId}, #{salePrice})")//#{name}相当于mybatis去执行product.getname
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn = "id") //useGeneratedKeys = true,使用数据库自动生成的主键，并获取这个主键值。keyProperty = "id",把数据库生成的主键值，写入传入对象的 id 属性。
    void insert(Product product);

    @Update("UPDATE  product SET name = #{name}, category_id = #{categoryId}, sale_price = #{salePrice} WHERE id = #{id}")
    void update(Product product);

    @Delete("DELETE FROM product WHERE id = #{id}")
    void delete(Integer id);}
