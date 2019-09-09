package com.lm.sell.controller;

import com.lm.sell.VO.ResultVO;
import com.lm.sell.converter.OrderForm2OrderDTOConverter;
import com.lm.sell.dataobject.OrderDetail;
import com.lm.sell.dto.OrderDto;
import com.lm.sell.enums.ResultEnum;
import com.lm.sell.exception.SellException;
import com.lm.sell.form.OrderForm;
import com.lm.sell.service.BuyerService;
import com.lm.sell.service.OrderService;
import com.lm.sell.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("参数不正确，orderForm=" + orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDto orderDto = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDto createResult = orderService.create(orderDto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderDto.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDetail>> list(@RequestParam("openid") String openid,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = PageRequest.of(page, size);
        Page<OrderDto> orderDtoPage = orderService.findList(openid, request);
        return ResultVOUtil.success(orderDtoPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDto> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        //TODO
        OrderDto orderDto = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDto);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        //TODO
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
