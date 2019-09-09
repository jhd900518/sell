package com.lm.sell.enums;

public enum ResultEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数错误"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    ORDER_DETAIL_EMPTY(16, "订单详情为空"),
    ORDER_PAY_STATUS_EROOR(17, "订单支付状态不正确"),
    CART_EMPTY(18, "购物车不能为空"),
    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),
    WECHAT_MP_ERROR(20, "微信公众方面错误"),
    WECHAT_PAY_AMOUNT_ERROR(21, "微信支付金额错误"),
    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),
    ORDER_FINISH_SUCCESS(23, "订单已经完结"),
    PRODUCT_STATUS_ERROR(24, "商品状态错误"),
    LOGIN_FAIL(25, "登录失败"),
    LOGIN_SUCCESS(26, "登录成功"),
    LOGOUT_SUCCESS(27, "登出成功");



    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
