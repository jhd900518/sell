package com.lm.sell.aspect;

import com.lm.sell.constant.CookieConstants;
import com.lm.sell.constant.RedisConstants;
import com.lm.sell.exception.SellerAuthorizeException;
import com.lm.sell.util.CookieUtil;
import com.lm.sell.util.RedisUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("execution(public * com.lm.sell.controller.Seller*.*(..))" +
            "&& !execution(public * com.lm.sell.controller.SellerUserController.*(..))")
    public void verify() {

    }

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstants.TOKEN);
        if (cookie == null) {
            System.out.println("Cookie 中没有token");
            throw new SellerAuthorizeException();
        }
        String tokenValue = (String) redisUtil.get(String.format(RedisConstants.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            System.out.println("Redis查询不到token");
            throw new SellerAuthorizeException();
        }
    }
}
