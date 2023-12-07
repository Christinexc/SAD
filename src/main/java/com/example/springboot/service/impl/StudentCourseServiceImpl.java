package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.StudentCourse;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.StudentCourseMapper;
import com.example.springboot.service.IStudentCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService {

    @Resource
    StudentCourseMapper studentCourseMapper;
    @Override
    public Page<User> findPage(Page<User> page, String username, String id,String course_id) {
        return studentCourseMapper.findPage(page,username,id,course_id);
    }

    @Override
    public void updateStuM(StudentCourse studentCourse) {
        int course_id=studentCourse.getCourseId();

        int stu_id=studentCourse.getStudentId();
        String book_status=studentCourse.getBookStatus();
        String check_in=studentCourse.getCheckIn();
        String fee_status=studentCourse.getFeeStatus();
        studentCourseMapper.updateStuM(course_id,stu_id,book_status,check_in,fee_status);
    }
}
