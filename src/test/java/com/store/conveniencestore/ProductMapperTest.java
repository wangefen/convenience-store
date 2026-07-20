package com.store.conveniencestore;

import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ProductMapper 测试类。
 *
 * @Transactional 用在测试类上时，
 * 每个测试执行完成后，数据库操作默认会回滚。
 *
 * 也就是说测试中新增、修改、删除的数据，
 * 测试结束后不会真正影响数据库原有数据。
 */
@SpringBootTest
@Transactional
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;


    /**
     * 测试查询全部商品。
     */
    @Test
    public void testFindAll() {

        List<Product> productList = productMapper.findAll();

        for (Product product : productList) {
            System.out.println(product);
        }

        assertNotNull(productList);
    }


    /**
     * 测试根据商品 id 查询商品。
     */
    @Test
    public void testFindById() {

        Product product = productMapper.findById(1);

        System.out.println(product);

        // 断言查询结果不为 null
        assertNotNull(product);

        // 你的数据库中 id=1 的商品是可口可乐
        assertEquals("可口可乐", product.getName());

        // 验证 category_id 是否成功映射到 categoryId
        assertNotNull(product.getCategoryId());

        // 验证 sale_price 是否成功映射到 salePrice
        assertNotNull(product.getSalePrice());
    }


    /**
     * 测试新增商品。
     */
    @Test
    public void testInsert() {

        // 创建一个新的 Product 对象
        Product product = new Product();

        product.setName("测试矿泉水");
        product.setCategoryId(1);
        product.setSalePrice(new BigDecimal("2.50"));

        System.out.println("新增前的商品对象：" + product);

        // 执行新增
        productMapper.insert(product);

        /*
         * 因为 Mapper 中使用了：
         *
         * @Options(
         *     useGeneratedKeys = true,
         *     keyProperty = "id"
         * )
         *
         * 数据库生成的自增 id 会自动放回 product 对象。
         */
        System.out.println("新增后的商品对象：" + product);
        System.out.println("数据库生成的商品 id：" + product.getId());

        // 验证数据库是否成功生成 id
        assertNotNull(product.getId());

        // 使用生成的 id 再查一次数据库
        Product insertedProduct =
                productMapper.findById(product.getId());

        System.out.println("从数据库重新查询的商品：" + insertedProduct);

        assertNotNull(insertedProduct);
        assertEquals("测试矿泉水", insertedProduct.getName());
        assertEquals(1, insertedProduct.getCategoryId());

        /*
         * BigDecimal 不建议直接使用普通 equals 比较，
         * 使用 compareTo 更稳妥。
         *
         * compareTo 返回 0，表示两个金额相等。
         */
        assertEquals(
                0,
                insertedProduct.getSalePrice()
                        .compareTo(new BigDecimal("2.50"))
        );
    }


    /**
     * 测试修改商品。
     */
    @Test
    public void testUpdate() {

        /*
         * 不直接修改数据库中原来的商品，
         * 先创建一条临时测试数据。
         */
        Product product = new Product();

        product.setName("修改前的测试商品");
        product.setCategoryId(1);
        product.setSalePrice(new BigDecimal("5.00"));

        productMapper.insert(product);

        System.out.println("修改前：" + product);

        /*
         * 修改这个 Product 对象的属性。
         *
         * id 不需要重新设置，
         * 因为 insert 后数据库生成的 id
         * 已经放回了 product 对象。
         */
        product.setName("修改后的测试商品");
        product.setCategoryId(2);
        product.setSalePrice(new BigDecimal("6.80"));

        // 执行数据库修改
        productMapper.update(product);

        // 从数据库重新查询
        Product updatedProduct =
                productMapper.findById(product.getId());

        System.out.println("修改后：" + updatedProduct);

        assertNotNull(updatedProduct);
        assertEquals(
                "修改后的测试商品",
                updatedProduct.getName()
        );

        assertEquals(2, updatedProduct.getCategoryId());

        assertEquals(
                0,
                updatedProduct.getSalePrice()
                        .compareTo(new BigDecimal("6.80"))
        );
    }


    /**
     * 测试删除商品。
     */
    @Test
    public void testDelete() {

        /*
         * 先新增一条专门用于删除测试的数据，
         * 避免删除数据库中原来的商品。
         */
        Product product = new Product();

        product.setName("准备删除的测试商品");
        product.setCategoryId(1);
        product.setSalePrice(new BigDecimal("1.00"));

        productMapper.insert(product);

        Integer productId = product.getId();

        System.out.println("即将删除的商品：" + product);
        System.out.println("即将删除的商品 id：" + productId);

        // 根据 id 删除商品
        productMapper.delete(productId);

        // 删除后重新查询
        Product deletedProduct =
                productMapper.findById(productId);

        System.out.println("删除后的查询结果：" + deletedProduct);

        // 删除成功后，查询结果应该为 null
        assertNull(deletedProduct);
    }
}