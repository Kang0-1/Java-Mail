package com.kang.javamail.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.javamail.entity.Order;
import com.kang.javamail.entity.OrderDetail;
import com.kang.javamail.entity.PageBean;
import com.kang.javamail.entity.R;
import com.kang.javamail.service.IOrderDetailService;
import com.kang.javamail.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 根据条件分页查询
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        System.out.println("pageBean:+"+pageBean);
        Map<String,Object> map=new HashMap<>();
        map.put("orderNo",pageBean.getQuery().trim());
        map.put("currentPageNum",pageBean.getCurrentPageNum());
        map.put("pageSize",pageBean.getPageSize());

        List<Order> orderList=orderService.list(map);
        Long total=orderService.getTotal(map);

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("orderList",orderList);
        resultMap.put("total",total);
        return R.success(resultMap);
    }

    /**
     * 更新订单状态
     * @param order
     * @return
     */
    @PostMapping("updateStatus")
    public R updateStatus(@RequestBody Order order){
        Order resultOrder = orderService.getById(order.getId());
        resultOrder.setStatus(order.getStatus());
        orderService.updateById(order);
        return R.success();
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @GetMapping("delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        orderDetailService.remove(new QueryWrapper<OrderDetail>().eq("mid",id));
        orderService.removeById(id);
        return R.success();
    }
}


