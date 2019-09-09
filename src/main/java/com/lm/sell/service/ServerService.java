package com.lm.sell.service;

import com.lm.sell.dataobject.SellerInfo;

public interface ServerService {

    SellerInfo findSellerInfoByOpenid(String openid);

    SellerInfo findSellerInfoByUser(String username, String password);


}
