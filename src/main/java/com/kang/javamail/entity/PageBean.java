package com.kang.javamail.entity;

import lombok.Data;

@Data
public class PageBean {

    private int currentPageNum; //第几页
    private int pageSize;  //每页记录数
    private int start; //起始页
    private String query; //查询参数

}
