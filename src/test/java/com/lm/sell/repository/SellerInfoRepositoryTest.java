package com.lm.sell.repository;

import com.lm.sell.dataobject.SellerInfo;
import com.lm.sell.util.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUnique());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("123");
        repository.save(sellerInfo);
    }

    @Test
    public void findByOpenId() {
        SellerInfo sellerInfo = repository.findByOpenid("123");
        System.out.println("the seller info is" + sellerInfo);
    }

    @Test
    public void findByUsername() {
        SellerInfo sellerInfo = repository.findByUsername("admin");
        System.out.println("the seller info is" + sellerInfo);
    }
}