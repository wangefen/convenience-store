package com.store.conveniencestore.controller;


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
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    //@RequestBody:将前端的json请求转换为对应的java类(category)
    @PostMapping
    public void add(@RequestBody Category category){
        categoryService.insert(category);
    }//@RequestBody把前端请求体中的 JSON 数据读取出来，按照方法参数指定的 Category 类型，转换成一个 Category 对象。

    @PutMapping("/{id}")
    public void update(
            @PathVariable Integer id,
            @RequestBody Category category){

        category.setId(id);

        categoryService.update(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){

        categoryService.delete(id);

    }
}
