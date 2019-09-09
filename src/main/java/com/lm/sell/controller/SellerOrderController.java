package com.lm.sell.controller;

import com.lm.sell.dto.OrderDto;
import com.lm.sell.enums.ResultEnum;
import com.lm.sell.service.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDto> orderDtoPage = orderService.findList(request);
        map.put("orderDtoPage", orderDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDto orderDto;
        try {
            orderDto = orderService.findOne(orderId);
            orderService.cancel(orderDto);
        } catch (Exception e) {
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDto orderDto;
        try {
            orderDto = orderService.findOne(orderId);
            orderService.finish(orderDto);
        } catch (Exception e) {
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url", "/sell/seller/order/detail");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        //推送微信消息
        //TODO
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDto orderDto;
        try {
            orderDto = orderService.findOne(orderId);
        } catch (Exception e) {
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDto", orderDto);
        return new ModelAndView("order/detail", map);
    }
}
