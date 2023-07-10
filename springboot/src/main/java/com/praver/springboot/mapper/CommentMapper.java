package com.praver.springboot.mapper;

import com.praver.springboot.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author praver
 * @since 2023-06-19
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> findCommentDetail(@Param("articleId") Integer articleId);
}
