package com.praver.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_role_menu")
@ApiModel(value = "角色菜单关系表", description = "")
public class RoleMenu {

    @ApiModelProperty("角色ID")
    private Integer roleId;
    @ApiModelProperty("菜单ID")
    private Integer menuId;

}
