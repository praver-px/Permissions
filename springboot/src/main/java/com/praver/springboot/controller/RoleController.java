package com.praver.springboot.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.entity.Menu;
import com.praver.springboot.entity.RoleMenu;
import com.praver.springboot.service.IMenuService;
import com.praver.springboot.service.IRoleMenuService;
import com.praver.springboot.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import com.praver.springboot.service.IRoleService;
import com.praver.springboot.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author praver
 * @since 2023-06-09
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IMenuService menuService;
    @Resource
    private IRoleMenuService roleMenuService;

    // 新增或修改
    @PostMapping
    public Result save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role));
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }

    // 查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        queryWrapper.orderByAsc("id");
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    //批量删除
    @PostMapping("/deleteList")
    public Result deleteList(@RequestBody List<Integer> ids) {
        return Result.success(roleService.removeByIds(ids));
    }

    /**
     * 绑定角色和菜单的关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     * @return
     */

    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleMenuService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<RoleMenu> list = roleMenuService.list(queryWrapper);
        List<Integer> menuIds = new ArrayList<>();
        for (RoleMenu roleMenu : list) {
//            将path为空的一级才当排除
            Menu menu = menuService.getById(roleMenu.getMenuId());
            if (!StrUtil.isBlank(menu.getPath())) {
                menuIds.add(roleMenu.getMenuId());
            }
        }
        return Result.success(menuIds);
    }

}

