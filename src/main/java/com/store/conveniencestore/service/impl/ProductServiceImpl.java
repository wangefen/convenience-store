package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.exception.ResourceNotFoundException;
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
        Product product = productMapper.findById(id);
        if(product == null){
            throw new ResourceNotFoundException("商品不存在：" + id);
        }
        return product;
    }

    @Override
    public void insert(Product product){
        /*
         * 新建商品不能直接指定库存。
         * 初始库存统一为0，后续通过采购增加。
         */
        product.setStock(0);
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
