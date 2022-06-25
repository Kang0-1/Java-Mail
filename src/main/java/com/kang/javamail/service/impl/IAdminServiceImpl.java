package com.kang.javamail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.javamail.entity.Admin;
import com.kang.javamail.entity.Order;
import com.kang.javamail.mapper.AdminMapper;
import com.kang.javamail.mapper.OrderDetailMapper;
import com.kang.javamail.mapper.OrderMapper;
import com.kang.javamail.service.IAdminService;
import com.kang.javamail.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
}
