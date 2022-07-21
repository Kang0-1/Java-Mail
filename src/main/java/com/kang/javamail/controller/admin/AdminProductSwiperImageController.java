package com.kang.javamail.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.javamail.entity.*;
import com.kang.javamail.service.IProductSwiperImageService;
import com.kang.javamail.util.DateUtil;
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
@RequestMapping("/admin/productSwiperImage")
public class AdminProductSwiperImageController {

    @Autowired
    private IProductSwiperImageService productSwiperImageService;


    @Value("${ProductSwiperImageFilePath}")
    private String ProductSwiperImageFilePath;


    /**
     * 查询所有
     * @param id
     * @return
     */
    @GetMapping("/list/{id}")
    public R list(@PathVariable(value = "id") Integer id){
        List<ProductSwiperImage> productSwiperImageList = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>().eq("productId", id));
        Map<String,Object> map=new HashMap<>();
        map.put("productSwiperImageList",productSwiperImageList);
        return R.success(map);
    }

    /**
     * 添加
     * @param productSwiperImage
     * @return
     */
    @RequestMapping("/add")
    public R add(@RequestBody ProductSwiperImage productSwiperImage){
        productSwiperImageService.saveOrUpdate(productSwiperImage);
        return R.success();
    }

    /**
     * 删除商品大类
     * @param id
     * @return
     */
    @GetMapping("delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        productSwiperImageService.removeById(id);
        return R.success();
    }

    /**
     * 上传商品详情轮播图片
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
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(ProductSwiperImageFilePath+newFileName));
            map.put("code",1);
            map.put("msg","上传成功");
            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/productSwiperImg/"+newFileName);

            map.put("data",dataMap);
        }
        return map;
    }
}
