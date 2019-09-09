package com.lm.sell.service;

import com.lm.sell.dataobject.ProductCategory;
import com.lm.sell.dataobject.ProductInfo;
import com.lm.sell.dto.CartDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDto> cartDtoList);

    void decreaseStock(List<CartDto> cartDtoList);

    //商品的上架
    ProductInfo onSale(String productId);

    //商品的下架
    ProductInfo offSale(String productId);
}
