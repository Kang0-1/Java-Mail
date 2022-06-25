package com.kang.javamail.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.javamail.constant.SystemConstant;
import com.kang.javamail.entity.Admin;
import com.kang.javamail.entity.PageBean;
import com.kang.javamail.entity.R;
import com.kang.javamail.entity.WxUserInfo;
import com.kang.javamail.service.IAdminService;
import com.kang.javamail.service.IWxUserInfoService;
import com.kang.javamail.util.JwtUtils;
import com.kang.javamail.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IWxUserInfoService wxUserInfoService;

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    @PostMapping("/Login")
    public R adminLogin(@RequestBody Admin admin){
        if(admin==null){
            return R.error();
        }
        if(StringUtil.isEmpty(admin.getUserName())){
            return R.error("用户名不能为空!");
        }
        if(StringUtil.isEmpty(admin.getPassword())){
            return R.error("密码不能为空!");
        }
        Admin resultAdmin = adminService.getOne(new QueryWrapper<Admin>().eq("userName", admin.getUserName()));
        if(resultAdmin==null){
            return R.error("用户不存在!");
        }
        if(!resultAdmin.getPassword().trim().equals(admin.getPassword())){
            return R.error("用户名或密码错误!");
        }
        String token = JwtUtils.createJWT("-1", "admin", SystemConstant.JWT_TTL);
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        return R.success(map);
    }

    /**
     * 根据条件分页查询 用户信息
     * @param pageBean
     * @return
     */
    @RequestMapping("/user/list")
    public R list(@RequestBody PageBean pageBean){
        System.out.println("pageBean:+"+pageBean);
        String query=pageBean.getQuery().trim();
        Page<WxUserInfo> page=new Page<>(pageBean.getCurrentPageNum(),pageBean.getPageSize());
        Page<WxUserInfo> pageRes = wxUserInfoService.page(page, new QueryWrapper<WxUserInfo>().like(StringUtil.isNotEmpty(query), "nickName", query));
        Map<String,Object> map=new HashMap<>();
        map.put("userList",pageRes.getRecords());
        map.put("total",pageRes.getTotal());
        return R.success(map);
    }

    /**
     * 修改密码
     * @param admin
     * @return
     */
    @PostMapping("/modifyPassword")
    public R modifyPassword(@RequestBody Admin admin){
        if(StringUtil.isEmpty(admin.getUserName())){
            return R.error("用户名不能为空!");
        }
        if(StringUtil.isEmpty(admin.getNewPassword())){
            return R.error("新密码不能为空!");
        }
        adminService.update(new UpdateWrapper<Admin>().set("password",admin.getNewPassword()).eq("userName",admin.getUserName()));
        return R.success();
    }












}
