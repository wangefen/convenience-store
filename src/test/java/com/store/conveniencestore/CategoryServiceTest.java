package com.store.conveniencestore;


import com.store.conveniencestore.entity.Category;
import com.store.conveniencestore.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testFindAll(){
        List<Category> list = categoryService.findAll();
        for (Category category : list){
            System.out.println(category);
        }
    }
}
