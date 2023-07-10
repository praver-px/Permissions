package com.praver.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.controller.dto.UserDTO;
import com.praver.springboot.exception.ServiceException;
import com.praver.springboot.util.Constants;
import com.praver.springboot.util.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.praver.springboot.service.IUserService;
import com.praver.springboot.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author praver
 * @since 2023-06-05
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;


    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        if (StrUtil.isBlank(userDTO.getUsername()) || StrUtil.isBlank(userDTO.getPassword())) {
            throw new ServiceException(Constants.CODE_400, "参数错误！");
        }
        return Result.success(userService.register(userDTO));
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        if (StrUtil.isBlank(userDTO.getUsername()) || StrUtil.isBlank(userDTO.getPassword())) {
            throw new ServiceException(Constants.CODE_400, "参数错误！");
        }
        return Result.success(userService.login(userDTO));
    }

    // 新增或修改
    @PostMapping
    public Result save(@RequestBody User user) {
        return Result.success(userService.saveOrUpdate(user));
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    // 查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/role/{role}")
    public Result findByRole(@PathVariable String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", role);
        return Result.success(userService.list(queryWrapper));
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    //根据用户名查询
    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));
    }

    /**
     * 文件下载
     *
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws Exception {
        List<User> list = userService.list();
//        通过工具类创建writer写出到磁盘路径
//        内存操作，写出浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
//        自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "手机号");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatar", "头像");
//        一次性写出list内的对象到excel，使用默认样式强制输出标题
        writer.write(list, true);
        //        设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
        return Result.success();
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        1.通过javabean的方式读取Excel表，要求表头与数据库的字段名相同
//        List<User> list = reader.readAll(User.class);
//        2.忽略表头中文按表格数据位置导入
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatar(row.get(6).toString());
            users.add(user);
        }
        return Result.success(userService.saveBatch(users));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String nickname,
                           @RequestParam(defaultValue = "") String email,
                           @RequestParam(defaultValue = "") String address) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if (!"".equals(username)) {
//            queryWrapper.like("username", username);
//        }
//        if (!"".equals(email)) {
//            queryWrapper.like("email", email);
//        }
//        if (!"".equals(address)) {
//            queryWrapper.like("address", address);
//        }
//        queryWrapper.orderByDesc("id");
        return Result.success(userService.findPage(new Page<>(pageNum, pageSize), nickname, email, address));
    }

    //批量删除
    @DeleteMapping("/deleteList/{ids}")
    public Result deleteList(@PathVariable Integer[] ids) {
        return Result.success(userService.removeByIds(Arrays.asList(ids)));
    }

}

