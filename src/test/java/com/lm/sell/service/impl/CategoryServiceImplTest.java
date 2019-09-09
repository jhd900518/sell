package com.lm.sell.service.impl;

import com.lm.sell.dataobject.ProductCategory;
import com.lm.sell.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory category = categoryService.findOne(1);
        System.out.println("the is is " + category.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> result = categoryService.findAll();
        System.out.println("the size is" + result.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2));
        System.out.println("the result size is" + result.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(4);
        productCategory.setCategoryName("精品男装");
        categoryService.save(productCategory);
    }
}