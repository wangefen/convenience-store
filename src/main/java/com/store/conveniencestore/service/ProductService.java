package com.store.conveniencestore.service;

import com.store.conveniencestore.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> search(String keyword, Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice);

    Product findById(Integer id);

    void insert(Product product);

    void update(Product product);

    void delete(Integer id);
}
