package com.kang.javamail;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kang.javamail.mapper")
public class JavaMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaMailApplication.class, args);
    }

}
