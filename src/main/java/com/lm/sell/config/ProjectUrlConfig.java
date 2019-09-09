package com.lm.sell.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static org.apache.naming.SelectorContext.prefix;

@Component
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrlConfig {

    public String wechatMpAuthorize;

    public String wechatOpenAuthorize;

    public String sell;

    public String getWechatMpAuthorize() {
        return wechatMpAuthorize;
    }

    public void setWechatMpAuthorize(String wechatMpAuthorize) {
        this.wechatMpAuthorize = wechatMpAuthorize;
    }

    public String getWechatOpenAuthorize() {
        return wechatOpenAuthorize;
    }

    public void setWechatOpenAuthorize(String wechatOpenAuthorize) {
        this.wechatOpenAuthorize = wechatOpenAuthorize;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
