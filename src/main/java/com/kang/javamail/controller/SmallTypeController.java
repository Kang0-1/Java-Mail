package com.kang.javamail.controller;


import com.kang.javamail.entity.BigType;
import com.kang.javamail.entity.R;
import com.kang.javamail.entity.SmallType;
import com.kang.javamail.service.IBigTypeService;
import com.kang.javamail.service.ISmallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/smallType")
public class SmallTypeController {

    @Autowired
    private ISmallTypeService smallTypeService;

    /**
     * 查询所有商品小类
     */
    @GetMapping("/findAll")
    public R findAll(){
        List<SmallType> smallTypeList = smallTypeService.list();
        Map<String,Object> map=new HashMap<>();
        map.put("message",smallTypeList);
        return R.success(map);
    }

}
