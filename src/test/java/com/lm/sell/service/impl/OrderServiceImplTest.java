package com.lm.sell.service.impl;

import com.lm.sell.dataobject.OrderDetail;
import com.lm.sell.dto.OrderDto;
import com.lm.sell.enums.OrderStatusEnum;
import com.lm.sell.enums.PayStatusEnum;
import com.lm.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderDto.setBuyerOpenid("abc");
        orderDto.setBuyerAddress("四川省成都市");
        orderDto.setBuyerPhone("18280181542");
        orderDto.setBuyerName("金宏东");
        orderDto.setOrderStatus(OrderStatusEnum.NEW.getCode());
        List<OrderDetail> details = new ArrayList<>();
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductName("奶茶");
        orderDetail1.setProductId("1568014341353578185");
        orderDetail1.setProductQuantity(2);
        orderDetail1.setProductPrice(new BigDecimal(6.50));
        orderDetail1.setProductIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568024784207&di=a71914bb288609d031ee4585d08a152a&imgtype=0&src=http%3A%2F%2Fsc01.alicdn.com%2Fkf%2FHTB1BmM6zMKTBuNkSne1q6yJoXXaw%2FPremix-no-sugar-milk-powder-bubble-tea.jpg_300x300.jpg");
        details.add(orderDetail1);
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductName("奶茶");
        orderDetail2.setProductId("1568014341353578185");
        orderDetail2.setProductQuantity(2);
        orderDetail2.setProductPrice(new BigDecimal(6.50));
        details.add(orderDetail2);
        orderDto.setOrderDetailList(details);
        System.out.println(orderDto);
        orderService.create(orderDto);
    }

    @Test
    public void findOne() {
        OrderDto orderDto = orderService.findOne("1568026264623812043");
        System.out.println(orderDto);
    }
}