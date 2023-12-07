package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;
    @PostMapping("/login")
    //POST请求
    public Result login(@RequestBody UserDto userDto){
        String username=userDto.getUsername();
        String password=userDto.getPassword();
        if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return Result.error(Constants.CODE_400,"登录错误");
        }
        UserDto dto=userService.login(userDto);

            return Result.success(dto);

    }
    @PostMapping
    public Result save(@RequestBody User user) {
            userService.saveOrUpdate(user);
        return Result.success();
            }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
             userService.removeById(id);
        return Result.success();
            }

    @GetMapping
    public Result findAll() {
            return Result.success(userService.list());
            }
    @GetMapping("/role/{role}")
    public Result findUsersByRole(@PathVariable String role) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role",role);
        List<User>list=userService.list(queryWrapper);
        return Result.success(list);
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success(userService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "")  String username ) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.like("username",username);
        return Result.success(userService.page(new Page<>(pageNum, pageSize),queryWrapper));

            }
    @GetMapping("/role/page")
    public Result findRolePage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                               @RequestParam String roleId ) {

        return Result.success(userService.findRolePage(new Page<>(pageNum, pageSize),roleId));

    }
    @GetMapping("/page/stu")
    public Result findsCourse(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam(defaultValue = "")  String username,
                                @RequestParam String id)
    {
        return Result.success(userService.findPage(new Page<>(pageNum, pageSize), username,id));//查看课程
    }


}

