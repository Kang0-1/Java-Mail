package com.kang.javamail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.javamail.entity.BigType;
import com.kang.javamail.entity.WxUserInfo;
import com.kang.javamail.mapper.BigTypeMapper;
import com.kang.javamail.mapper.WxUserInfoMapper;
import com.kang.javamail.service.IBigTypeService;
import com.kang.javamail.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IWxUserInfoServiceImpl extends ServiceImpl<WxUserInfoMapper, WxUserInfo> implements IWxUserInfoService {

    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;
}
