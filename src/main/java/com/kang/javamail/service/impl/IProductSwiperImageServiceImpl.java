package com.kang.javamail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.javamail.entity.Product;
import com.kang.javamail.entity.ProductSwiperImage;
import com.kang.javamail.mapper.ProductMapper;
import com.kang.javamail.mapper.ProductSwiperImageMapper;
import com.kang.javamail.service.IProductService;
import com.kang.javamail.service.IProductSwiperImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IProductSwiperImageServiceImpl extends ServiceImpl<ProductSwiperImageMapper, ProductSwiperImage> implements IProductSwiperImageService {

    @Autowired
    private ProductSwiperImageMapper productSwiperImageMapper;
}
