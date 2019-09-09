package com.lm.sell.controller;

import com.lm.sell.constant.CookieConstants;
import com.lm.sell.constant.RedisConstants;
import com.lm.sell.config.ProjectUrlConfig;
import com.lm.sell.dataobject.SellerInfo;
import com.lm.sell.enums.ResultEnum;
import com.lm.sell.exception.SellException;
import com.lm.sell.form.OrderForm;
import com.lm.sell.form.UserForm;
import com.lm.sell.service.impl.SellerServiceImpl;
import com.lm.sell.util.CookieUtil;
import com.lm.sell.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProjectUrlConfig urlConfig;

    @GetMapping("/index")
    public ModelAndView index(
                              HttpServletResponse response,
                              Map<String, Object> map) {
        return new ModelAndView( "user/login");
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid UserForm userForm,
                              BindingResult bindingResult,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        if (bindingResult.hasErrors()){
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        SellerInfo sellerInfo = sellerService.findSellerInfoByUser(userForm.getUsername(),
                userForm.getPassword());
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "sell/seller/index");
            return new ModelAndView("common/error");
        }
        String openid = "xjkjjkjdsjkjdkjsjd";
        //2 设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstants.EXPIRE;
        redisUtil.set(String.format(RedisConstants.TOKEN_PREFIX, token), openid, expire);
        //3 设置token到cookie
        CookieUtil.set(response, "token", token, expire);
        return new ModelAndView("redirect:"+urlConfig.getSell()+"/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstants.TOKEN);
        if (cookie != null) {
            redisUtil.del(String.format(RedisConstants.TOKEN_PREFIX, cookie.getValue()));
            CookieUtil.set(response, CookieConstants.TOKEN, null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/index");
        //2 清除redis
        return new ModelAndView("common/success", map);
    }
}
