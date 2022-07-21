package com.kang.javamail.controller.admin;


import com.kang.javamail.entity.BigType;
import com.kang.javamail.entity.PageBean;
import com.kang.javamail.entity.Product;
import com.kang.javamail.entity.R;
import com.kang.javamail.service.IProductService;
import com.kang.javamail.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product")
public class AdminProductController {

    @Autowired
    private IProductService productService;

    @Value("${productImageFilePath}")
    private String productImageFilePath;

    @Value("${HomeSwiperImageFilePath}")
    private String HomeSwiperImageFilePath;

    /**
     * 根据条件分页查询 商品信息
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        //System.out.println("pageBean:+"+pageBean);
        Map<String,Object> map=new HashMap<>();
        map.put("name",pageBean.getQuery().trim());
        map.put("currentPageNum",pageBean.getCurrentPageNum());
        map.put("pageSize",pageBean.getPageSize());

        List<Product> productList=productService.list(map);
        Long total=productService.getTotal(map);

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("productList",productList);
        resultMap.put("total",total);
        return R.success(resultMap);
    }

    /**
     * 更新热卖状态
     * @param id
     * @param hot
     * @return
     */
    @GetMapping("/updateHot/{id}/state/{hot}")
    public R updateHot(@PathVariable(value = "id")Integer id,@PathVariable(value = "hot")boolean hot){
        Product product = productService.getById(id);
        product.setHot(hot);
        if(hot){
            product.setHotDateTime(new Date());
        }else {
            product.setHotDateTime(null);
        }
        productService.saveOrUpdate(product);
        return R.success();
    }

    /**
     * 更新轮播状态
     * @param id
     * @param swiper
     * @return
     */
    @GetMapping("/updateSwiper/{id}/state/{swiper}")
    public R updateSwiper(@PathVariable(value = "id")Integer id,@PathVariable(value = "swiper")boolean swiper){
        Product product = productService.getById(id);
        product.setSwiper(swiper);
        productService.saveOrUpdate(product);
        return R.success();
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id")Integer id){
        productService.removeById(id);
        return R.success();
    }

    /**
     * 上传商品图片
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
            //获取图片后缀
            String suffixName=originalFilename.substring(originalFilename.indexOf("."));
            //新文件名
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;
            //文件上传
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(productImageFilePath+newFileName));
            map.put("code",1);
            map.put("msg","上传成功");

            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/product/"+newFileName);

            map.put("data",dataMap);
        }
        return map;
    }


    /**
     * 上传首页幻灯图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadHomeSwiperImage")
    public Map<String,Object> uploadHomeSwiperImage(MultipartFile file) throws Exception{
        Map<String,Object> map=new HashMap<>();
        if(!file.isEmpty()){
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            //获取图片后缀
            String suffixName=originalFilename.substring(originalFilename.indexOf("."));
            //新文件名
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;
            //文件上传
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(HomeSwiperImageFilePath+newFileName));
            map.put("code",1);
            map.put("msg","上传成功");

            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/swiper/"+newFileName);

            map.put("data",dataMap);
        }
        return map;
    }



    /**
     * 添加或者修改
     * @param product
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody Product product){
        if(product.getId()==null||product.getId()==-1){
            productService.add(product);
        }else{
            productService.update(product);
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
        Product product = productService.findById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("product",product);
        return R.success(map);
    }
}
