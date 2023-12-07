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
import com.example.springboot.service.IApplicationService;
import com.example.springboot.entity.Application;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Resource
    private IApplicationService applicationService;

    @PostMapping
    public Result save(@RequestBody Application application) {
            applicationService.saveOrUpdate(application);
            return Result.success();
            }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
           applicationService.removeById(id);
        return Result.success();
            }

    @GetMapping
    public Result findAll() {
            return Result.success( applicationService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success( applicationService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam Integer id) {

        return Result.success(applicationService.findPage(new Page<>(pageNum, pageSize),id));
        }

}




