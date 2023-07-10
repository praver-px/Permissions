package com.praver.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author praver
 * @since 2023-06-14
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> page, String name);

    void setStudentCourse(Integer courseId, Integer studentId);
}
