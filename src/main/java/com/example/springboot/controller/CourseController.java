package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.annotation.Resource;
import com.example.springboot.common.Result;
import com.example.springboot.service.ICourseService;
import com.example.springboot.entity.Course;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @PostMapping
    public Result save(@RequestBody Course course) {
            courseService.saveOrUpdate(course);
            return Result.success();
            }

    @PostMapping("/studentCourse/{studentId}/{courseId}")
    public Result save(@PathVariable Integer courseId ,@PathVariable Integer studentId) {
        courseService.setStudentCourse(courseId,studentId);
        return Result.success();
    }
    @PostMapping("/update/studentCourse/{studentId}/{courseId}/{param}/{type}")
    public Result update(@PathVariable Integer studentId ,
                       @PathVariable Integer courseId,
                       @PathVariable Integer param,
                         @PathVariable String type
                      ) {
        courseService.updateStudentCourse(studentId,courseId,param,type);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
           courseService.removeById(id);
        return Result.success();
            }

    @GetMapping
    public Result findAll() {
            return Result.success( courseService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success( courseService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
//        QueryWrapper<Course>queryWrapper=new QueryWrapper<>();
//        queryWrapper.like("name",name);
        Page<Course>page=courseService.findPage(new Page<>(pageNum,pageSize),name);
        return Result.success(page);
        }
    @GetMapping("/stu/page")
    public Result findStuPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                              @RequestParam Integer studentId
                           ) {

        Page<Course>page=courseService.findStuPage(new Page<>(pageNum,pageSize),studentId);
        return Result.success(page);
    }
}




