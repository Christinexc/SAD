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
import com.example.springboot.service.IStudentCourseService;
import com.example.springboot.entity.StudentCourse;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/studentcourse")
public class StudentCourseController {

    @Resource
    private IStudentCourseService studentCourseService;

    @PostMapping
    public Result save(@RequestBody StudentCourse studentCourse) {
            studentCourseService.updateStuM(studentCourse);
            return Result.success();
            }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
           studentCourseService.removeById(id);
        return Result.success();
            }

    @GetMapping
    public Result findAll() {
            return Result.success( studentCourseService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success( studentCourseService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")  String username,
                           @RequestParam String id,
                           @RequestParam String course_id)
    {

        return Result.success(studentCourseService.findPage(new Page<>(pageNum, pageSize), username,id,course_id));
        }

}




