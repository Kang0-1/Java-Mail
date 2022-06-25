package com.kang.javamail.Config;

import com.kang.javamail.interceptor.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT",
                        "DELETE","OPTIONS")
                .maxAge(3600);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //访问请求路径转换为本地文件路径
        registry.addResourceHandler("/image/home_swiper/**").addResourceLocations("file:E:\\Java\\java-mail\\image_resource\\homeSwiperImg\\");
        registry.addResourceHandler("/image/bigType/**").addResourceLocations("file:E:\\Java\\java-mail\\image_resource\\bigTypeImg\\");
        registry.addResourceHandler("/image/product/**").addResourceLocations("file:E:\\Java\\java-mail\\image_resource\\productImg\\");
        registry.addResourceHandler("/image/productSwiperImg/**").addResourceLocations("file:E:\\Java\\java-mail\\image_resource\\productSwiperImg\\");
        registry.addResourceHandler("/image/productIntroImg/**").addResourceLocations("file:E:\\Java\\java-mail\\image_resource\\productIntroImg\\");
        registry.addResourceHandler("/image/productParaImg/**").addResourceLocations("file:E:\\Java\\java-mail\\image_resource\\productParaImg\\");
    }

    @Bean
    public SysInterceptor sysInterceptor(){
        return new SysInterceptor();
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns=new String[]{"/admin/Login","/product/**","/bigType/**","/user/wxlogin","/weixinpay/**"};
        registry.addInterceptor(sysInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
