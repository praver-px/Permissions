package com.praver.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.controller.dto.UserDTO;
import com.praver.springboot.entity.Menu;
import com.praver.springboot.entity.Role;
import com.praver.springboot.entity.RoleMenu;
import com.praver.springboot.entity.User;
import com.praver.springboot.exception.ServiceException;
import com.praver.springboot.mapper.RoleMapper;
import com.praver.springboot.mapper.RoleMenuMapper;
import com.praver.springboot.mapper.UserMapper;
import com.praver.springboot.service.IMenuService;
import com.praver.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.praver.springboot.util.Constants;
import com.praver.springboot.util.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author praver
 * @since 2023-06-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Resource
    private UserMapper mapper;
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    private static final Log LOG = Log.get();

    @Override
    public User register(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        User user = getUserInfo(userDTO, queryWrapper);
        if (user == null) {
            user = new User();
            BeanUtil.copyProperties(userDTO, user, true);
            save(user);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在！");
        }
        return user;
    }

    @Override
    public Page<User> findPage(Page<User> page, String nickname, String email, String address) {
        return mapper.findPage(page, nickname, email, address);
    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User user = getUserInfo(userDTO, queryWrapper);
        if (user != null) {
            BeanUtil.copyProperties(user, userDTO, true);
            //设置token
            String token = TokenUtils.genToken(user.getId().toString(), user.getPassword());
            userDTO.setToken(token);
            //设置用户的菜单
            List<Menu> userRoleMenus = getRoleMenus(user.getRole());
            userDTO.setMenus(userRoleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误！");
        }
    }

    private User getUserInfo(UserDTO userDTO, QueryWrapper queryWrapper) {
        User user;
        try {
            user = getOne(queryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误！");
        }
        return user;
    }

    /**
     * 获取当前用户的菜单列表
     *
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        QueryWrapper<Role> getRoleId = new QueryWrapper<>();
        getRoleId.eq("flag", roleFlag);
        Integer roleId = roleMapper.selectOne(getRoleId).getId();
        QueryWrapper<RoleMenu> getMenuIds = new QueryWrapper<>();
        getMenuIds.eq("role_id", roleId);
        //获取当前角色的所有菜单id集合
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(getMenuIds);
        List<Integer> menuIds = new ArrayList<>();
        for (RoleMenu roleMenu : roleMenus) {
            menuIds.add(roleMenu.getMenuId());
        }
        //查出所有菜单
        List<Menu> menus = menuService.findMenus("");
        //new 一个最后筛选完的菜单
        List<Menu> userRoleMenus = new ArrayList<>();
        //筛选当前用户的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                userRoleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //removeIf 移除children中不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return userRoleMenus;
    }
}
