package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ApiResponse<List<Product>> findAll(){
        List<Product> products = productService.findAll();
        return ApiResponse.success(products);
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> findById(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return ApiResponse.success(product);
    }

    @PostMapping
    public ApiResponse<Product> insert(@RequestBody Product product) {
        productService.insert(product);

        return ApiResponse.success(product);
    }

    @PutMapping("/{id}")
    public ApiResponse<Product> update(@PathVariable Integer id, @RequestBody Product product){
        product.setId(id);
        productService.update(product);
        Product updatedProduct = productService.findById(id);

        return ApiResponse.success(updatedProduct);
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ApiResponse.success(null);
    }
}
