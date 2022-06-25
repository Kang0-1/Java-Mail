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
import java.util.Map;

@RestController
@RequestMapping("/admin/bigType")
public class AdminBigTypeController {

    @Autowired
    private IBigTypeService bigTypeService;

    @Autowired
    private ISmallTypeService smallTypeService;

    @Value("${bigTypeImageFilePath}")
    private String bigTypeImageFilePath;


    /**
     * 根据条件分页查询 商品大类信息
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        String query= pageBean.getQuery().trim();
        Page<BigType> page=new Page<>(pageBean.getCurrentPageNum(), pageBean.getPageSize());
        Page<BigType> bigTypePage = bigTypeService.page(page, new QueryWrapper<BigType>().like(StringUtil.isNotEmpty(query), "name", query));
        Map<String,Object> map=new HashMap<>();
        map.put("bigTypeList",bigTypePage.getRecords());
        map.put("total",bigTypePage.getTotal());
        return R.success(map);
    }

    /**
     * 添加或者修改
     * @param bigType
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody BigType bigType){
        if(bigType.getId()==null||bigType.getId()==-1){
            bigTypeService.save(bigType);
        }else{
            bigTypeService.saveOrUpdate(bigType);
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
        BigType bigType = bigTypeService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("bigType",bigType);
        return R.success(map);
    }

    /**
     * 删除商品大类
     * @param id
     * @return
     */
    @GetMapping("delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        //大类下面如果有小类 返回报错提示

        if( smallTypeService.count(new QueryWrapper<SmallType>().eq("bigTypeId", id)) > 0 ){
            return R.error(500,"大类下面有小类信息,不能删除");
        }else {
            bigTypeService.removeById(id);
            return R.success();
        }
    }

    /**
     * 上传商品大类图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadImage")
    public Map<String,Object> uploadImage(MultipartFile file) throws Exception{
        Map<String,Object> map=new HashMap<>();
        if(!file.isEmpty()){
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            String suffixName=originalFilename.substring(originalFilename.indexOf("."));
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(bigTypeImageFilePath+newFileName));
            map.put("code",1);
            map.put("msg","上传成功");
            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/bigType/"+newFileName);

            map.put("data",dataMap);
        }
        return map;
    }

    /**
     * 查询所有大类数据 (下拉框显示)
     * @return
     */
    @RequestMapping("/listAll")
    public R listAll(){
        Map<String,Object> map=new HashMap<>();
        map.put("bigTypeList",bigTypeService.list());
        return R.success(map);
    }
}
