package com.praver.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

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
 * @since 2023-06-19
 */
@Getter
@Setter
@TableName("t_comment")
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("评论人id")
    private Integer userId;

    @ApiModelProperty("发表时间")
    private String time;

    @ApiModelProperty("上级评论")
    private Integer pid;

    @ApiModelProperty("最上级评论")
    private Integer originId;

    @ApiModelProperty("文章id")
    private Integer articleId;

    @TableField(value = "avatar_url", exist = false)
    private String nickname;

    @TableField(exist = false)
    private String avatarUrl;

    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private String pNickname;

    @TableField(exist = false)
    private Integer pUserId;


}
