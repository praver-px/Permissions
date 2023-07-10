package com.praver.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.entity.RoleMenu;
import com.praver.springboot.entity.User;
import com.praver.springboot.service.IUserService;
import com.praver.springboot.util.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.praver.springboot.util.Result;
import com.praver.springboot.service.ICourseService;
import com.praver.springboot.entity.Course;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author praver
 * @since 2023-06-14
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @Resource
    private IUserService userService;


    // 新增或修改
    @PostMapping
    public Result save(@RequestBody Course course) {
        return Result.success(courseService.saveOrUpdate(course));
    }

    @PostMapping("/checkCourse/{courseId}/{studentId}")
    public Result checkCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        courseService.setStudentCourse(courseId,studentId);
        return Result.success();
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(courseService.removeById(id));
    }

    // 查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(courseService.list());
    }


    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(courseService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
//        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name", name);
//        queryWrapper.orderByAsc("id");
//        Page<Course> page = courseService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        List<Course> records = page.getRecords();
//        for (Course record : records) {
//            User user = userService.getById(record.getTeacherId());
//            if (user != null){
//                record.setTeacher(user.getNickname());
//            }
//        }
        return Result.success(courseService.findPage(new Page<>(pageNum, pageSize), name));
    }

    //批量删除
    @PostMapping("/deleteList")
    public Result deleteList(@RequestBody List<Integer> ids) {
        return Result.success(courseService.removeByIds(ids));
    }

}

