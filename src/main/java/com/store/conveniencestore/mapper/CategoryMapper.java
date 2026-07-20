package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select  * from category")
    List<Category> findAll();

    @Insert("INSERT INTO category(name) VALUES(#{name})")
    void insert(Category category);

    @Update("UPDATE category SET name=#{name} WHERE id=#{id}")
    void update(Category category);

    @Delete("DELETE FROM category WHERE id=#{id}")
    void delete(Integer id);
}
