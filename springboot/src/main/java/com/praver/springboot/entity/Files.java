package com.praver.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author praver
 * @since 2023-06-07
 */
@Getter
@Setter
@TableName("sys_files")
@ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("下载地址")
    private String url;

    @ApiModelProperty("文件md5")
    private String md5;

    @ApiModelProperty("是否删除")
    private Boolean isDelete;

    @ApiModelProperty("是否禁用")
    private Boolean enable;


}
