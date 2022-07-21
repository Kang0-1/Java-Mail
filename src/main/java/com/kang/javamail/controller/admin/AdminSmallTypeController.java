package com.kang.javamail.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.javamail.entity.*;
import com.kang.javamail.service.IBigTypeService;
import com.kang.javamail.service.ISmallTypeService;
import com.kang.javamail.util.DateUtil;
import com.kang.javamail.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/smallType")
public class AdminSmallTypeController {


    @Autowired
    private ISmallTypeService smallTypeService;

    @Autowired
    private IBigTypeService bigTypeService;


    /**
     * 根据条件分页查询 商品小类信息
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        System.out.println("pageBean:+"+pageBean);
        Map<String,Object> map=new HashMap<>();
        map.put("name",pageBean.getQuery().trim());
        map.put("currentPageNum",pageBean.getCurrentPageNum());
        map.put("pageSize",pageBean.getPageSize());

        List<SmallType> smallTypeList=smallTypeService.list(map);
        Long total=smallTypeService.getTotal(map);

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("smallTypeList",smallTypeList);
        resultMap.put("total",total);
        return R.success(resultMap);
    }

    /**
     * 根据商品大类id,查询所有小类
     * @return
     */
    @GetMapping("/listAll/{bigTypeId}")
    public R listAll(@PathVariable(value = "bigTypeId")Integer bigTypeId){
        List<SmallType> smallTypeList = smallTypeService.list(new QueryWrapper<SmallType>().eq("bigTypeId", bigTypeId));
        Map<String ,Object> map=new HashMap<>();
        map.put("smallTypeList",smallTypeList);
        return R.success(map);
    }

    /**
     * 添加或者修改
     * @param smallType
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody SmallType smallType){
        //前端只返回bigType实体 通过手动设置bigTypeId来修改数据库记录
        smallType.setBigTypeId(smallType.getBigType().getId());
        if(smallType.getId()==null||smallType.getId()==-1){
            smallTypeService.save(smallType);
        }else{
            smallTypeService.saveOrUpdate(smallType);
        }
        return R.success();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public R findById(@PathVariable(value = "id") Integer id){
        SmallType smallType = smallTypeService.getById(id);
        smallType.setBigType(bigTypeService.getById(smallType.getBigTypeId()));
        Map<String,Object> map=new HashMap<>();
        map.put("smallType",smallType);
        return R.success(map);
    }

    /**
     * 删除商品小类
     * @param id
     * @return
     */
    @GetMapping("delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        smallTypeService.removeById(id);
        return R.success();
    }


}
