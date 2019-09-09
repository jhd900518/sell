package com.lm.sell.repository;

import com.lm.sell.dataobject.OrderDetail;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRespositoryTest {

    @Autowired
    private OrderDetailRespository respository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567");
        orderDetail.setOrderId("1234");
        orderDetail.setProductId("1234567");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(1.2));
        orderDetail.setProductQuantity(2);
        respository.save(orderDetail);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> datas=respository.findByOrderId("1234");
        System.out.println(datas.size());
    }
}