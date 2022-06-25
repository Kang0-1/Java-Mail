package com.kang.javamail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.javamail.entity.BigType;
import com.kang.javamail.entity.OrderDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
