package com.lm.sell.repository;

import com.lm.sell.dataobject.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12346");
        productInfo.setCategoryType(3);
        productInfo.setProductDescription("皮蛋粥");
        productInfo.setProductIcon("jskdjk");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStatus(3);
        productInfo.setProductStock(1000);
        repository.save(productInfo);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> result = repository.findByProductStatus(1);
        System.out.println("the size is" + result.size());
    }

    @Test
    public void onSale(){

    }
}