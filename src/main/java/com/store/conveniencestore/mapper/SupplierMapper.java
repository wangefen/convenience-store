package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SupplierMapper {
    @Select("SELECT id, name, contact, phone, address FROM supplier")
    List<Supplier> findAll();

    @Select("SELECT * from supplier where id = #{id}")
    Supplier findById(Integer id);

    @Insert("INSERT INTO supplier(name, contact, phone, address) VALUES (#{name}, #{contact}, #{phone}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(Supplier supplier);

    @Update("UPDATE supplier SET name = #{name}, contact = #{contact}, phone = #{phone}, address = #{address} WHERE id = #{id}")
    int update(Supplier supplier);

    @Delete("DELETE FROM supplier WHERE id = #{id}")
    int delete(Integer id);

}
