package com.example.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    Page<Course> findPage(Page<Course> page, @Param("name") String name);

    void deleteStudentCourse(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);
    void setStudentCourse(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    Page<Course> findStuPage(Page<Course> page,@Param("studentId") Integer studentId);

    void updateStudentCourse(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId, @Param("param") Integer param);

    void updateEStudentCourse(@Param("courseId") Integer courseId, @Param("studentId")  Integer studentId, @Param("param")  Integer param);
}
