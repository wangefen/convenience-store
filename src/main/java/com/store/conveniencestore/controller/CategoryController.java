package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.dto.CategoryRequest;
import com.store.conveniencestore.entity.Category;
import com.store.conveniencestore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@Tag(
        name = "分类管理",
        description = "分类的查询、新增、修改和删除接口"
)
@RestController
@RequestMapping("/Categories")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "查询全部分类",
            description = "查询并返回系统中的全部分类"
    )
    @GetMapping
    public ApiResponse<List<Category>> findAll() {

        List<Category> categories =
                categoryService.findAll();

        return ApiResponse.success(categories);
    }

    @Operation(
            summary = "新增分类",
            description = "接收分类信息并创建一个新分类"
    )
    //@RequestBody把前端请求体中的 JSON 数据读取出来，按照方法参数指定的 Category 类型，转换成一个 Category 对象。
    @PostMapping
    public ApiResponse<Category> add(
            @Valid @RequestBody CategoryRequest request) {

        Category category = new Category();
        category.setName(request.name());
        categoryService.insert(category);

        return ApiResponse.success(category);
    }

    @Operation(
            summary = "修改分类",
            description = "根据分类编号修改已有分类的信息"
    )
    @PutMapping("/{id}")
    public ApiResponse<Category> update(
            @Parameter(description = "需要修改的分类编号", example = "1")
            @PathVariable @Positive(message = "分类编号必须大于0") Integer id,
            @Valid @RequestBody CategoryRequest request) {

        Category category = new Category();

        category.setId(id);
        category.setName(request.name());

        categoryService.update(category);

        return ApiResponse.success(category);
    }

    @Operation(
            summary = "删除分类",
            description = "根据分类编号删除对应的分类"
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @Parameter(description = "需要删除的分类编号", example = "1")
            @PathVariable @Positive(message = "分类编号必须大于0") Integer id) {

        categoryService.delete(id);

        return ApiResponse.success(null);
    }
}