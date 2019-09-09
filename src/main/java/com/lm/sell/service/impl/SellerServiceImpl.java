package com.lm.sell.service.impl;

import com.lm.sell.dataobject.SellerInfo;
import com.lm.sell.exception.SellerAuthorizeException;
import com.lm.sell.repository.SellerInfoRepository;
import com.lm.sell.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements ServerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }

    @Override
    public SellerInfo findSellerInfoByUser(String username, String password) {
        SellerInfo sellerInfo = repository.findByUsername(username);
        if (sellerInfo==null){
            throw new SellerAuthorizeException();
        }
        if (!sellerInfo.getPassword().equals(password)){
            throw new SellerAuthorizeException();
        }
        return sellerInfo;
    }
}
