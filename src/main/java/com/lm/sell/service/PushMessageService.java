package com.lm.sell.service;

import com.lm.sell.dto.OrderDto;

public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDto
     */
    void orderStatus(OrderDto orderDto);
}
