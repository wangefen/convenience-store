package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.dto.ProductCreateRequest;
import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;


import java.util.List;

@Tag(
        name = "商品管理",
        description = "商品的查询、新增、修改和删除接口"
)
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "查询全部商品",
            description = "查询并返回系统中的全部商品"
    )
    @GetMapping
    public ApiResponse<List<Product>> findAll(){
        List<Product> products = productService.findAll();
        return ApiResponse.success(products);
    }

    @Operation(
            summary = "根据编号查询商品",
            description = "根据商品编号查询对应的商品信息"
    )
    @GetMapping("/{id}")
    public ApiResponse<Product> findById(@Parameter(description = "商品编号", example = "1")
      @PathVariable @Positive(message = "商品编号必须大于0")Integer id) {
        Product product = productService.findById(id);
        return ApiResponse.success(product);
    }

    @Operation(
            summary = "新增商品",
            description = "接收商品信息并创建一个新商品"
    )
    @PostMapping
    public ApiResponse<Product> insert(@Valid @RequestBody ProductCreateRequest request) {
       Product product = new Product();
       product.setName(request.name());
       product.setCategoryId(request.categoryId());
       product.setSalePrice(request.salePrice());

       productService.insert(product);

        return ApiResponse.success(product);
    }

    @Operation(
            summary = "修改商品",
            description = "根据商品编号修改已有商品的信息"
    )
    @PutMapping("/{id}")
    public ApiResponse<Product> update(@Parameter(description = "需要修改的商品编号", example = "1")
        @PathVariable @Positive(message = "商品编号必须大于0") Integer id,
        @Valid @RequestBody ProductCreateRequest request){
        Product product = new Product();
        product.setId(id);
        product.setName(request.name());
        product.setCategoryId(request.categoryId());
        product.setSalePrice(request.salePrice());
        productService.update(product);
        Product updatedProduct = productService.findById(id);

        return ApiResponse.success(updatedProduct);
    }


    @Operation(
            summary = "删除商品",
            description = "根据商品编号删除对应的商品"
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "需要删除的商品编号", example = "1")
        @PathVariable @Positive(message = "商品编号必须大于0") Integer id) {
        productService.delete(id);
        return ApiResponse.success(null);
    }
}
