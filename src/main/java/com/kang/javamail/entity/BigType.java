package com.kang.javamail.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@TableName("t_bigType")
@NoArgsConstructor
@AllArgsConstructor
/**
 * 商品大类
 */
public class BigType {
    private Integer id;                       //编号
    private String name;                      //名称
    private String remark;                    //备注
    private String image="default.jpg";       //封面图片
    @TableField(select = false)
    private List<SmallType> smallTypeList;    //商品小类集合
}
