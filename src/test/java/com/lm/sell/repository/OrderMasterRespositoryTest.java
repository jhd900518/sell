package com.lm.sell.repository;

import com.lm.sell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRespositoryTest {

    @Autowired
    private OrderMasterRespository respository;

    private final String OPENID = "10";

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("大师兄");
        orderMaster.setBuyerPhone("18280181542");
        orderMaster.setBuyerAddress("四川省成都市");
        orderMaster.setBuyerOpenid("10");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        respository.save(orderMaster);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request = PageRequest.of(0, 1);
        Page<OrderMaster> data = respository.findByBuyerOpenid(OPENID, request);
        System.out.println(data.getTotalElements());
    }
}