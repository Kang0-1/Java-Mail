package com.kang.javamail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.javamail.entity.BigType;
import com.kang.javamail.entity.Product;
import com.kang.javamail.mapper.BigTypeMapper;
import com.kang.javamail.mapper.ProductMapper;
import com.kang.javamail.service.IBigTypeService;
import com.kang.javamail.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IBigTypeServiceImpl extends ServiceImpl<BigTypeMapper, BigType> implements IBigTypeService {

    @Autowired
    private BigTypeMapper bigTypeMapper;
}
