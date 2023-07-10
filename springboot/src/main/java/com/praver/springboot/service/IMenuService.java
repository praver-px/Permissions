package com.praver.springboot.service;

import com.praver.springboot.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author praver
 * @since 2023-06-10
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
