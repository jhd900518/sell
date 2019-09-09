package com.lm.sell.service.impl;

import com.lm.sell.config.WechatAccountConfig;
import com.lm.sell.dto.OrderDto;
import com.lm.sell.service.PushMessageService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDto orderDto) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(orderDto.getBuyerOpenid());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "请鸡蛋收获"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "18289808765"),
                new WxMpTemplateData("keyword3", orderDto.getOrderId()),
                new WxMpTemplateData("keyword4", orderDto.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5", "¥" + orderDto.getOrderAmount()),
                new WxMpTemplateData("remark", "还有再次光临")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {

        }

    }
}
