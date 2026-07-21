package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.Category;
import com.store.conveniencestore.mapper.CategoryMapper;
import com.store.conveniencestore.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


//干活的地方 —— 真正的业务逻辑
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    // Spring 创建 CategoryServiceImpl 时，自动把 CategoryMapper 注入进来
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public void add(Category category){
        categoryMapper.insert(category);
    }

    @Override
    public void update(Category category){
        categoryMapper.update(category);
    }

    @Override
    public  void delete(Integer id){
        categoryMapper.delete(id);
    }
}
