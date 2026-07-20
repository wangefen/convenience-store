package com.store.conveniencestore.service;

import com.store.conveniencestore.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Integer id);

    void insert(Product product);

    void update(Product product);

    void delete(Integer id);
}
