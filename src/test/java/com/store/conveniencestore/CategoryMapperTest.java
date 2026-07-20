package com.store.conveniencestore;

import com.store.conveniencestore.entity.Category;
import com.store.conveniencestore.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void testFindAll() {
        List<Category> categories = categoryMapper.findAll();

        for (Category category : categories) {
            System.out.println(category);
        }
    }
}
