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
import com.example.springboot.service.IMessageService;
import com.example.springboot.entity.Message;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xsy
 * @since 2023-12-04
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private IMessageService messageService;

    @PostMapping
    public Result save(@RequestBody Message message) {
            messageService.saveOrUpdate(message);
            return Result.success();
            }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
           messageService.removeById(id);
        return Result.success();
            }

    @GetMapping
    public Result findAll() {
            return Result.success( messageService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success( messageService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam Integer id) {
        return Result.success(messageService.findPage(new Page<>(pageNum, pageSize),id));
        }

}




