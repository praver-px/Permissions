package com.praver.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author praver
 * @since 2023-06-05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    Page<User> findPage(Page<User> page, @Param("nickname") String nickname,
                        @Param("email") String email, @Param("address") String address);
}
