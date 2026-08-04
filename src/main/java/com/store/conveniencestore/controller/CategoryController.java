package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.dto.CategoryRequest;
import com.store.conveniencestore.entity.Category;
import com.store.conveniencestore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Categories")
public class CategoryController {


    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<List<Category>> findAll() {

        List<Category> categories =
                categoryService.findAll();

        return ApiResponse.success(categories);
    }

    //@RequestBody把前端请求体中的 JSON 数据读取出来，按照方法参数指定的 Category 类型，转换成一个 Category 对象。
    @PostMapping
    public ApiResponse<Category> add(
            @Valid @RequestBody CategoryRequest request) {

        Category category = new Category();
        category.setName(request.name());
        categoryService.insert(category);

        return ApiResponse.success(category);
    }

    @PutMapping("/{id}")
    public ApiResponse<Category> update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequest request) {

        Category category = new Category();

        category.setId(id);
        category.setName(request.name());

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