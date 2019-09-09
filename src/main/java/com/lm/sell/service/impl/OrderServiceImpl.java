package com.lm.sell.service.impl;

import com.lm.sell.converter.OrderMaster2OrderDTOConverter;
import com.lm.sell.dataobject.OrderDetail;
import com.lm.sell.dataobject.OrderMaster;
import com.lm.sell.dataobject.ProductInfo;
import com.lm.sell.dto.CartDto;
import com.lm.sell.dto.OrderDto;
import com.lm.sell.enums.OrderStatusEnum;
import com.lm.sell.enums.PayStatusEnum;
import com.lm.sell.enums.ResultEnum;
import com.lm.sell.exception.SellException;
import com.lm.sell.repository.OrderDetailRespository;
import com.lm.sell.repository.OrderMasterRespository;
import com.lm.sell.service.OrderService;
import com.lm.sell.service.ProductService;
import com.lm.sell.service.PushMessageService;
import com.lm.sell.service.WebSocket;
import com.lm.sell.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRespository orderDetailRespository;

    @Autowired
    private OrderMasterRespository orderMasterRespository;

    @Autowired
    private PushMessageService pushMessage;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        String orderId = KeyUtil.genUnique();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1 查询商品(价格价格，数量)
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            orderAmount = orderDetail.getProductPrice()
                    .multiply(new BigDecimal((double)orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUnique());
            orderDetail.setOrderId(orderId);
            orderDetailRespository.save(orderDetail);
        }
        orderDto.setOrderId(orderId);
        orderDto.setOrderAmount(orderAmount);
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMasterRespository.save(orderMaster);
        //扣库存
        List<CartDto> data = orderDto.getOrderDetailList()
                .stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(data);
        //发送websocket消息
        webSocket.sendMessage(orderDto.getOrderId());
        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRespository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRespository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRespository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDto> orderDtoList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDto>(orderDtoList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRespository.findAll(pageable);
        List<OrderDto> orderDtoList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDto>(orderDtoList,pageable,orderMasterPage.getTotalElements());
    }


    @Override
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            System.out.println("取消订单 订单状态不正确,orderId=" + orderDto.getOrderId() + "orderStatus=" + orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateResult = orderMasterRespository.save(orderMaster);
        if (updateResult == null) {
            System.out.println("取消订单 更新失败,orderMaster=" + orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            System.out.println("取消订单 订单中无商品详情，orderDto=" + orderDto);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDtoList);
        //如果已支付,需要退款
        if (orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateResult = orderMasterRespository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_EROOR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateResult = orderMasterRespository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }
}
