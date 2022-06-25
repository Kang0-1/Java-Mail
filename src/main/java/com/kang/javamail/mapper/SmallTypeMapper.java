package com.kang.javamail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.javamail.entity.BigType;
import com.kang.javamail.entity.SmallType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SmallTypeMapper extends BaseMapper<SmallType> {
    List<SmallType> list(Map<String, Object> map);

    Long getTotal(Map<String, Object> map);
}
