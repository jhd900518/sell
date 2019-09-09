package com.lm.sell.hanlder;

import com.lm.sell.VO.ResultVO;
import com.lm.sell.config.ProjectUrlConfig;
import com.lm.sell.exception.SellException;
import com.lm.sell.exception.SellerAuthorizeException;
import com.lm.sell.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig urlConfig;

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthrizeException() {
//        return new ModelAndView("redirect:"
//                .concat(urlConfig.getWechatOpenAuthorize())
//                .concat("/sell/wechat/qrAuthorize")
//                .concat("?returnUrl=")
//                .concat(urlConfig.getSell())
//                .concat("/sell/seller/login"));
        return new ModelAndView("redirect:"+urlConfig.getSell()+"/sell/seller/index");
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
