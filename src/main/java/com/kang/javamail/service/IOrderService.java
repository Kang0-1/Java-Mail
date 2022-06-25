package com.kang.javamail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.javamail.entity.Order;

import java.util.List;
import java.util.Map;

public interface IOrderService extends IService<Order> {

    //根据条件分页查询订单

    List<Order> list(Map<String, Object> map);

    /**
     * 根据条件查询订单总记录数
     * @param map
     * @return
     */
    Long getTotal(Map<String, Object> map);
}
