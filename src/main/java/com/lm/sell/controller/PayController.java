package com.lm.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.lm.sell.dto.OrderDto;
import com.lm.sell.enums.ResultEnum;
import com.lm.sell.exception.SellException;
import com.lm.sell.service.OrderService;
import com.lm.sell.service.PayService;
import com.lm.sell.service.impl.PayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("openId") String orderId,
                               @RequestParam("returnUrl") String returnUrl) {
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        PayResponse payResponse = payService.create(orderDto);
        Map map = new HashMap<String, Object>();
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        payService.notify(notifyData);
        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
