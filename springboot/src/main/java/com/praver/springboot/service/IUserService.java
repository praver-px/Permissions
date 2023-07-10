package com.praver.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.controller.dto.UserDTO;
import com.praver.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author praver
 * @since 2023-06-05
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    Page<User> findPage(Page<User> page, String nickname, String email, String address);
}
