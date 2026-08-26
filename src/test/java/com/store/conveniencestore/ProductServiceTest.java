package com.store.conveniencestore;

import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ProductService 业务层测试类。
 *
 * 主要测试：
 * ProductService 能否正常调用 ProductMapper，
 * 完成商品的查询、新增、修改和删除。
 */
@SpringBootTest
@Transactional
public class ProductServiceTest {

    /**
     * 这里声明的是 ProductService 接口类型。
     *
     * Spring 会找到带有 @Service 的 ProductServiceImpl，
     * 然后把 ProductServiceImpl 对象注入进来。
     */
    @Autowired
    private ProductService productService;

    /**
     * 测试根据 id 查询商品。
     */
    @Test
    public void testFindById() {

        Product product = productService.findById(1);

        System.out.println(product);

        assertNotNull(product);
        assertEquals("可口可乐", product.getName());
        assertEquals(1, product.getCategoryId());

        assertEquals(
                0,
                product.getSalePrice()
                        .compareTo(new BigDecimal("3.00"))
        );
    }


    /**
     * 测试新增商品。
     */
    @Test
    public void testInsert() {

        Product product = new Product();

        product.setName("Service测试商品");
        product.setCategoryId(1);
        product.setSalePrice(new BigDecimal("8.50"));

        productService.insert(product);

        System.out.println("新增后的商品：" + product);

        /*
         * Mapper 中设置了自动获取数据库生成的主键，
         * 因此新增后 product.id 应该不再是 null。
         */
        assertNotNull(product.getId());

        Product insertedProduct =
                productService.findById(product.getId());

        System.out.println("重新查询的商品：" + insertedProduct);

        assertNotNull(insertedProduct);
        assertEquals(
                "Service测试商品",
                insertedProduct.getName()
        );
    }


    /**
     * 测试修改商品。
     */
    @Test
    public void testUpdate() {

        /*
         * 先创建一条临时商品，
         * 避免直接修改数据库里原来的数据。
         */
        Product product = new Product();

        product.setName("修改前的Service商品");
        product.setCategoryId(1);
        product.setSalePrice(new BigDecimal("10.00"));

        productService.insert(product);

        /*
         * 修改 Java 对象中的属性。
         *
         * product.id 不需要重新设置，
         * 因为新增时数据库生成的 id 已经放回对象。
         */
        product.setName("修改后的Service商品");
        product.setCategoryId(2);
        product.setSalePrice(new BigDecimal("15.50"));

        productService.update(product);

        Product updatedProduct =
                productService.findById(product.getId());

        System.out.println("修改后的商品：" + updatedProduct);

        assertNotNull(updatedProduct);

        assertEquals(
                "修改后的Service商品",
                updatedProduct.getName()
        );

        assertEquals(2, updatedProduct.getCategoryId());

        assertEquals(
                0,
                updatedProduct.getSalePrice()
                        .compareTo(new BigDecimal("15.50"))
        );
    }


    /**
     * 测试删除商品。
     */
    @Test
    public void testDelete() {

        /*
         * 先新增一条专门用于删除测试的数据。
         */
        Product product = new Product();

        product.setName("准备删除的Service商品");
        product.setCategoryId(1);
        product.setSalePrice(new BigDecimal("1.50"));

        productService.insert(product);

        Integer productId = product.getId();

        System.out.println("准备删除的商品：" + product);

        // 调用 Service 删除商品
        productService.delete(productId);

        // 删除后再次查询
        Product deletedProduct =
                productService.findById(productId);

        System.out.println("删除后的查询结果：" + deletedProduct);

        // 删除成功后应该查不到，因此结果为 null
        assertNull(deletedProduct);
    }
}