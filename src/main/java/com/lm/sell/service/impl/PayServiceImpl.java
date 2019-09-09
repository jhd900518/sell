package com.lm.sell.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lm.sell.dto.OrderDto;
import com.lm.sell.enums.OrderStatusEnum;
import com.lm.sell.enums.ResultEnum;
import com.lm.sell.exception.SellException;
import com.lm.sell.service.OrderService;
import com.lm.sell.service.PayService;
import com.lm.sell.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private ThirdPayImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDto orderDto) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        PayResponse payResponse = bestPayService.pay(payRequest);
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1. 验证签名
        //2. 支付状态
        //3. 支付的金额
        //4. 支付的人(下单人==支付人)
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        //查询订单
        OrderDto orderDto = orderService.findOne(payResponse.getOrderId());
        if (orderDto == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致
        if (!MathUtil.equals(orderDto.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            throw new SellException(ResultEnum.WECHAT_PAY_AMOUNT_ERROR);
        }
        orderDto.setPayStatus(OrderStatusEnum.FINISHED.getCode());
        orderService.paid(orderDto);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        return refundResponse;
    }
}
