package com.lm.sell.service.impl;

import com.lm.sell.dto.OrderDto;
import com.lm.sell.enums.ResultEnum;
import com.lm.sell.exception.SellException;
import com.lm.sell.service.BuyerService;
import com.lm.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lm.sell.enums.ResultEnum.ORDER_OWNER_ERROR;

@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto = checkOrderOwner(openid, orderId);
        if (orderDto == null) {
            System.out.println("查不到订单");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDto);
    }

    private OrderDto checkOrderOwner(String openid, String orderId) {
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null) {
            return null;
        }
        if (!orderDto.getBuyerOpenid().equalsIgnoreCase(openid)) {
            System.out.println("订单openid不一致");
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
