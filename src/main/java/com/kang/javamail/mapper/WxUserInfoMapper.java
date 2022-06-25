package com.kang.javamail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.kang.javamail.entity.WxUserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface WxUserInfoMapper extends BaseMapper<WxUserInfo> {

    public WxUserInfo findByOpenId(String openId);
}
