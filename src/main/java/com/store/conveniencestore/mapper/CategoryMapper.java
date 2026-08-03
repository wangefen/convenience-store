package com.store.conveniencestore.mapper;

import com.store.conveniencestore.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select id, name from category")
    List<Category> findAll();

    @Select("""
        SELECT id, name
        FROM category
        WHERE id = #{id}
        """)
    Category findById(Integer id);

    @Insert("INSERT INTO category(name) VALUES(#{name})")
    void insert(Category category);

    //Mybatis执行SQL时，会返回成功影响的行数，这里用int接收
    @Update("UPDATE category SET name=#{name} WHERE id=#{id}")
    int update(Category category);

    @Delete("DELETE FROM category WHERE id=#{id}")
    int delete(Integer id);
}
