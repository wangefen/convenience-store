package com.store.conveniencestore.controller;

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
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PostMapping
    public Product insert(@RequestBody Product product) {
        productService.insert(product);

        return product;
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product product){
        product.setId(id);
        productService.update(product);
        return productService.findById(id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
}
