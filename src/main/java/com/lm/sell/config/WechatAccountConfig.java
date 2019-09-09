package com.lm.sell.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.naming.SelectorContext.prefix;

@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 公众平台配置
     */
    private String mpAppId;
    private String mpAppSecret;
    /**
     * 开放平台配置
     */
    private String openAppId;
    private String openAppSecret;

    private String mchId;
    private String mchKey;
    private String keyPath;
    private String notifyUrl;

    private Map<String,String> templateId;

    public String getMpAppId() {
        return mpAppId;
    }

    public void setMpAppId(String mpAppId) {
        this.mpAppId = mpAppId;
    }

    public String getMpAppSecret() {
        return mpAppSecret;
    }

    public void setMpAppSecret(String mpAppSecret) {
        this.mpAppSecret = mpAppSecret;
    }

    public String getOpenAppId() {
        return openAppId;
    }

    public void setOpenAppId(String openAppId) {
        this.openAppId = openAppId;
    }

    public String getOpenAppSecret() {
        return openAppSecret;
    }

    public void setOpenAppSecret(String openAppSecret) {
        this.openAppSecret = openAppSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Map<String, String> getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Map<String, String> templateId) {
        this.templateId = templateId;
    }
}
