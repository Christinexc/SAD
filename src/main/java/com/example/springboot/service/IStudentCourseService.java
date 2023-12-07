package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.StudentCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
public interface IStudentCourseService extends IService<StudentCourse> {

    Page<User> findPage(Page<User> objectPage, String username, String id,String course_id);

    void updateStuM(StudentCourse studentCourse);
}
