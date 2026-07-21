package com.store.conveniencestore.service;

import com.store.conveniencestore.entity.Category;

import java.util.List;

//定义契约 —— 声明有哪些功能函数
public interface CategoryService {
    List<Category> findAll();

    void insert(Category category);

    void update(Category category);

    void delete(Integer id);
}
