package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.mapper.ProductMapper;
import com.store.conveniencestore.service.ProductService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productMapper.findById(id);
    }

    @Override
    public void insert(Product product){
        productMapper.insert(product);
    }

    @Override
    public void delete(Integer id){
        productMapper.delete(id);
    }

    @Override
    public void update(Product product){
        productMapper.update(product);
    }
}
