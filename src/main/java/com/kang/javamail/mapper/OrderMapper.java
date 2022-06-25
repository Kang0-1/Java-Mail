package com.kang.javamail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.javamail.entity.Order;
import com.kang.javamail.entity.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> list(Map<String, Object> map);

    Long getTotal(Map<String, Object> map);
}
