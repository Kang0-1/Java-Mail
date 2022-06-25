package com.kang.javamail.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.javamail.entity.Order;
import com.kang.javamail.entity.OrderDetail;
import com.kang.javamail.entity.R;
import com.kang.javamail.properties.WeixinpayProperties;
import com.kang.javamail.service.IOrderDetailService;
import com.kang.javamail.service.IOrderService;
import com.kang.javamail.util.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.*;

@RestController
@RequestMapping("/my/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private WeixinpayProperties weixinpayProperties;

    //创建订单
    @RequestMapping("/create")
    @Transactional
    public R create(@RequestBody Order order,@RequestHeader(value = "token")String token){
        Claims claims=JwtUtils.validateJWT(token).getClaims();
        if(claims!=null){
            System.out.println("CREATE:open_id="+claims.getId());
            order.setUserId(claims.getId());
        }
        order.setOrderNo("JAVA"+ DateUtil.getCurrentDateStr());
        order.setCreateDate(new Date());
        OrderDetail[] goods = order.getGoods();
        orderService.save(order);
        //添加订单详情到数据库
        for (int i = 0; i < goods.length; i++) {
            OrderDetail orderDetail=goods[i];
            orderDetail.setMId(order.getId());
            orderDetailService.save(orderDetail);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("orderNo",order.getOrderNo());
        return R.success(map);
    }

    /**
     * 调用统一下单,支付
     * @param orderNo
     * @return
     * @throws Exception
     */
    @RequestMapping("/preparePay")
    public R preparePay(@RequestBody String orderNo)throws Exception{
        //通过token获取openid
        //System.out.println("token="+token);
        System.out.println("PAY:order="+orderNo);
        //添加订单到数据库
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("orderNo", orderNo));
//        System.out.println("appid="+weixinpayProperties.getAppid());
//        System.out.println("mch_id="+weixinpayProperties.getMch_id());
//        System.out.println("body="+"java-mail商品购买测试");
//        System.out.println("out_trade_no="+orderNo);
//        System.out.println("total_fee="+order.getTotalPrice().movePointRight(2));
//        System.out.println("notify_url="+weixinpayProperties.getNotify_url());
//        System.out.println("trade_type="+"JSAPI");
        //System.out.println("openid="+order.getUserId());
        order.setStatus(2);
        order.setPayDate(new Date());
        orderService.updateById(order);
        return R.success();
    }

    /**
     * 微信支付签名算法sign
     */
    private String getSign(Map<String,Object> map) {
        StringBuffer sb = new StringBuffer();
        String[] keyArr = (String[]) map.keySet().toArray(new String[map.keySet().size()]);//获取map中的key转为array
        Arrays.sort(keyArr);//对array排序
        for (int i = 0, size = keyArr.length; i < size; ++i) {
            if ("sign".equals(keyArr[i])) {
                continue;
            }
            sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        sb.append("key=" + weixinpayProperties.getKey());
        String sign = string2MD5(sb.toString());
        System.out.println("sign="+sign);
        return sign;
    }
    /***
     * MD5加码 生成32位md5码
     */
    private String string2MD5(String str){
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 订单查询 type : 0 全部订单  1：待付款  2：待收货  3：退款/退货
     * @param type
     * @return
     */
    @RequestMapping("/list")
    public R list(Integer type,Integer page,Integer pageSize){
        System.out.println("type="+type);
        List<Order> orderList=null;
        Map<String,Object> resultMap=new HashMap<>();

        Page<Order> pageOrder=new Page<>(page,pageSize);
        if(type==0){
            //orderList= orderService.list(new QueryWrapper<Order>().orderByDesc("id"));
            Page<Order> orderResult = orderService.page(pageOrder, new QueryWrapper<Order>().orderByDesc("id"));
            orderList=orderResult.getRecords();
            resultMap.put("total",orderResult.getTotal());
            resultMap.put("totalPage",orderResult.getPages());
        }else {
            //orderList= orderService.list(new QueryWrapper<Order>().eq("status",type).orderByDesc("id"));
            Page<Order> orderResult = orderService.page(pageOrder, new QueryWrapper<Order>().eq("status",type).orderByDesc("id"));
            orderList=orderResult.getRecords();
            resultMap.put("total",orderResult.getTotal());
            resultMap.put("totalPage",orderResult.getPages());
        }
        resultMap.put("orderList",orderList);
        return R.success(resultMap);
    }

}
