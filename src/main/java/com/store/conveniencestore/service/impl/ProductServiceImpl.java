package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.exception.ResourceNotFoundException;
import com.store.conveniencestore.mapper.CategoryMapper;
import com.store.conveniencestore.mapper.ProductMapper;
import com.store.conveniencestore.mapper.SupplierMapper;
import com.store.conveniencestore.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    public ProductServiceImpl(ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public List<Product> search(String keyword, Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice){
        if (categoryId != null && categoryId <= 0){
            throw new IllegalArgumentException(
                    "商品分类编号必须大于0"
            );
        }

        if (minPrice != null && minPrice.signum() < 0){
            throw new IllegalArgumentException(
                    "最低价格不能小于0"
            );
        }

        if (maxPrice != null && maxPrice.signum() < 0) {
            throw new IllegalArgumentException(
                    "最高价格不能小于0"
            );
        }

        if (minPrice != null
                && maxPrice != null
                && minPrice.compareTo(maxPrice) > 0) {

            throw new IllegalArgumentException(
                    "最低价格不能大于最高价格"
            );
        }
        //.trim():会删除字符串首尾的空格：
        String normalizedKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();

        return productMapper.search(normalizedKeyword, categoryId, minPrice, maxPrice);
    }

    @Override
    public Product findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "商品编号必须大于0"
            );
        }

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
        if (product == null) {
            throw new IllegalArgumentException(
                    "商品数据不能为空"
            );
        }

        checkCategory(product.getCategoryId());



        product.setStock(0);
        productMapper.insert(product);
    }

    @Override
    public void delete(Integer id){
        if (id == null || id <= 0){
            throw new IllegalArgumentException(
                    "商品标编号必须大于0"
            );
        }
        int affectedRows = productMapper.delete(id);
        if (affectedRows == 0){
            throw new ResourceNotFoundException(
                    "商品不存在"
            );
        }
    }

    @Override
    public void update(Product product){
        if (product == null) {
            throw new IllegalArgumentException(
                    "商品数据不能为空"
            );
        }

        if (product.getId() == null
                || product.getId() <= 0) {

            throw new IllegalArgumentException(
                    "商品编号必须大于0"
            );
        }

        checkCategory(product.getCategoryId());

        int affectedRows = productMapper.update(product);
        if (affectedRows == 0){
            throw new ResourceNotFoundException(
                    "商品不存在：" + product.getId()
            );
        }
    }

    private void checkCategory(Integer categoryId){
        if (categoryId == null || categoryId <= 0){
            throw new IllegalArgumentException(
                    "商品分类编号必须大于0"
            );
        }

        if (categoryMapper.findById(categoryId) == null){
            throw new ResourceNotFoundException(
                    "分类不存在，请先核对好后在操作"
            );
        }
    }
}
