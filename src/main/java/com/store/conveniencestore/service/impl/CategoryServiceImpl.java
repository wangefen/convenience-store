package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.Category;
import com.store.conveniencestore.exception.ResourceNotFoundException;
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
    public void insert(Category category) {

        if (category == null) {
            throw new IllegalArgumentException(
                    "分类数据不能为空"
            );
        }

        categoryMapper.insert(category);
    }

    @Override
    public void update(Category category) {

        if (category == null) {
            throw new IllegalArgumentException(
                    "分类数据不能为空"
            );
        }

        if (category.getId() == null
                || category.getId() <= 0) {

            throw new IllegalArgumentException(
                    "分类编号必须大于0"
            );
        }

        int affectedRows = categoryMapper.update(category);

        if (affectedRows == 0) {
            throw new ResourceNotFoundException(
                    "分类不存在，分类编号：" + category.getId()
            );
        }
    }

    @Override
    public void delete(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "分类编号必须大于0"
            );
        }

        int affectedRows = categoryMapper.delete(id);

        if (affectedRows == 0) {
            throw new ResourceNotFoundException(
                    "分类不存在，分类编号：" + id
            );
        }
    }
}
