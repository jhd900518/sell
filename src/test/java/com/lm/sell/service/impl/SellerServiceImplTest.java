package com.lm.sell.service.impl;

import com.lm.sell.dataobject.SellerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl sellerService;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid("123");
        System.out.println(sellerInfo.getOpenid());
    }

    @Test
    public void findSellerInfoByUser() {
        SellerInfo sellerInfo = sellerService.findSellerInfoByUser("admin", "admin");
        System.out.println("the result is" + sellerInfo);
    }
}