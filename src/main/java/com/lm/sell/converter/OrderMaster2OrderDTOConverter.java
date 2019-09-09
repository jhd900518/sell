package com.lm.sell.converter;

import com.lm.sell.dataobject.OrderMaster;
import com.lm.sell.dto.OrderDto;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {

    public static OrderDto convert(OrderMaster orderMaster) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        return orderDto;
    }

    public static List<OrderDto> convert(List<OrderMaster> orderMasterList) {
        List<OrderDto> data = orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
        return data;
    }
}
