package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.Category;
import com.store.conveniencestore.mapper.CategoryMapper;
import com.store.conveniencestore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


//干活的地方 —— 真正的业务逻辑
@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

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
