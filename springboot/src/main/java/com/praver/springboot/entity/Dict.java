package com.praver.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_dict")
@ApiModel(value = "dict对象", description = "")
public class Dict {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("内容")
    private String value;
    @ApiModelProperty("类型")
    private String type;
}
