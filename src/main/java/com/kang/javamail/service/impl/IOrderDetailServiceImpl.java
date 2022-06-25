package com.kang.javamail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.javamail.entity.BigType;
import com.kang.javamail.entity.OrderDetail;
import com.kang.javamail.mapper.BigTypeMapper;
import com.kang.javamail.mapper.OrderDetailMapper;
import com.kang.javamail.service.IBigTypeService;
import com.kang.javamail.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IOrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;
}
