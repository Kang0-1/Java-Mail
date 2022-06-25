package com.kang.javamail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.javamail.entity.BigType;
import org.springframework.stereotype.Repository;

@Repository
public interface BigTypeMapper extends BaseMapper<BigType> {
    public BigType findById(Integer id);
}
