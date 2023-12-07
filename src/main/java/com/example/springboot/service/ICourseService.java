package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> page, String name);

    void setStudentCourse(Integer courseId, Integer studentId);

    Page<Course> findStuPage(Page<Course> page,Integer studentId);

    void updateStudentCourse(Integer studentId, Integer courseId, Integer param,String type);
}
