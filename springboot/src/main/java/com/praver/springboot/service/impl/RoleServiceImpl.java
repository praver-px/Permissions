package com.praver.springboot.service.impl;

import com.praver.springboot.entity.Role;
import com.praver.springboot.mapper.RoleMapper;
import com.praver.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author praver
 * @since 2023-06-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
