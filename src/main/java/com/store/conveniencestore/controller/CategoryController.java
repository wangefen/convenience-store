package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.entity.Category;
import com.store.conveniencestore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<Category>> findAll() {

        List<Category> categories =
                categoryService.findAll();

        return ApiResponse.success(categories);
    }

    //@RequestBody把前端请求体中的 JSON 数据读取出来，按照方法参数指定的 Category 类型，转换成一个 Category 对象。
    @PostMapping
    public ApiResponse<Category> add(
            @RequestBody Category category) {

        categoryService.insert(category);

        return ApiResponse.success(category);
    }

    @PutMapping("/{id}")
    public ApiResponse<Category> update(
            @PathVariable Integer id,
            @RequestBody Category category) {

        category.setId(id);
        categoryService.update(category);

        return ApiResponse.success(category);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Integer id) {

        categoryService.delete(id);

        return ApiResponse.success(null);
    }
}