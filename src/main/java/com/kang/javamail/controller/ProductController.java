package com.kang.javamail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.javamail.entity.Product;
import com.kang.javamail.entity.ProductSwiperImage;
import com.kang.javamail.entity.R;
import com.kang.javamail.service.IProductService;
import com.kang.javamail.service.IProductSwiperImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductSwiperImageService productSwiperImageService;

//    查询轮播商品
    @GetMapping("/findSwiper")
    public R findSwipper(){
        List<Product> swiperProductList = productService.list(new QueryWrapper<Product>().eq("isSwiper", true).orderByAsc("swiperSort"));
        Map<String,Object> map=new HashMap<>();
        map.put("message",swiperProductList);
        return R.success(map);
    }

    /**
     * 查询热门推荐商品
     * @return
     */
    @GetMapping("/findHot")
    public R findHot(){
        Page<Product> page=new Page<>(0,8);
        Page<Product> productPage = productService.page(page, new QueryWrapper<Product>().eq("isHot", true).orderByAsc("hotDateTime"));
        List<Product> hotProductList = productPage.getRecords();
        Map<String ,Object> map=new HashMap<>();
        map.put("message",hotProductList);
        return R.success(map);
    }

    /**
     * 根据id查询商品信息
     * @return
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Integer id){
        Product product = productService.getById(id);
        List<ProductSwiperImage> productSwiperImageList = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>().eq("productId", product.getId()).orderByAsc("sort"));
        product.setProductSwiperImageList(productSwiperImageList);
        Map<String ,Object> map=new HashMap<>();
        map.put("message",product);
        return R.success(map);
    }

    /**
     * 商品搜索
     * @param q
     * @return
     */
    @GetMapping("/search")
    public R search(String q){
        List<Product> productList = productService.list(new QueryWrapper<Product>().like("name", q));
        Map<String ,Object> map=new HashMap<>();
        map.put("message",productList);
        return R.success(map);
    }
}
