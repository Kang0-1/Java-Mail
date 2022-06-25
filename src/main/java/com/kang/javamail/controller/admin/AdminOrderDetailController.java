package com.kang.javamail.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.javamail.entity.OrderDetail;
import com.kang.javamail.entity.R;
import com.kang.javamail.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/orderDetail")
public class AdminOrderDetailController {

    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 根据订单号 查询订单商盘详情
     * @param id
     * @return
     */
    @GetMapping("list/{id}")
    public R listByOrderId(@PathVariable(value = "id")Integer id){
        List<OrderDetail> orderDetailList = orderDetailService.list(new QueryWrapper<OrderDetail>().eq("mId", id));
        Map<String,Object> map=new HashMap<>();
        map.put("orderDetailList",orderDetailList);
        return R.success(map);
    }
}
