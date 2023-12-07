package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Course;
import com.example.springboot.mapper.CourseMapper;
import com.example.springboot.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    private CourseMapper courseMapper;
    @Override
    public Page<Course> findPage(Page<Course> page, String name) {
        return courseMapper.findPage(page,name);
    }
 @Transactional
    @Override
    public void setStudentCourse(Integer courseId, Integer studentId) {
        courseMapper.deleteStudentCourse(courseId,studentId);
        courseMapper.setStudentCourse(courseId,studentId);
    }

    @Override
    public Page<Course> findStuPage(Page<Course> page,Integer studentId) {
        return courseMapper.findStuPage(page,studentId);
    }

    @Override
    public void updateStudentCourse(Integer studentId, Integer courseId, Integer param,String type) {
        if(Objects.equals(type, "success"))
            courseMapper.updateStudentCourse(courseId, studentId, param);
        else if(Objects.equals(type, "evaluation"))
            courseMapper.updateEStudentCourse(courseId, studentId, param);

    }
}
