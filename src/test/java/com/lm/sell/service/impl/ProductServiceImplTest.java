package com.lm.sell.service.impl;

import com.lm.sell.dataobject.ProductInfo;
import com.lm.sell.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("12346");
        System.out.println(productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfos = productService.findUpAll();
        System.out.println("the size is" + productInfos.size());
    }

    @Test
    public void findAll() {
        PageRequest request = PageRequest.of(0, 5);
        Page<ProductInfo> productInfos = productService.findAll(request);
        System.out.println(productInfos.getTotalElements());
    }

    @Test
    public void onSale() {
        productService.onSale("12346");
    }

    @Test
    public void offSale() {
        productService.offSale("12346");
    }
}