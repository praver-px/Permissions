package com.praver.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.praver.springboot.entity.Dict;
import com.praver.springboot.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author praver
 * @since 2023-06-07
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);
}
