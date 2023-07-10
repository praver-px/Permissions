package com.praver.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author praver
 * @since 2023-06-14
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    Page<Course> findPage(Page<Course> page,@Param("name") String name);

    void delStudentCourse(@Param("courseId") Integer courseId,@Param("studentId") Integer studentId);
    void setStudentCourse(@Param("courseId") Integer courseId,@Param("studentId") Integer studentId);
}
