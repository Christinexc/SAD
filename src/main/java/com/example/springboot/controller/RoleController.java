package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


import com.example.springboot.service.IRoleService;
import com.example.springboot.entity.Role;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping
    public Result save(@RequestBody Role role) {
             roleService.saveOrUpdate(role);
        return Result.success();
            }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
             roleService.removeById(id);
        return Result.success();
            }

    @GetMapping
    public Result findAll() {
            return Result.success(roleService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success(roleService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                           @RequestParam String name) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);

        return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper));
        }

    /**
     * 绑定角色和菜单的关系
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId,menuIds);
        return Result.success();
    }
    @GetMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId) {
        return Result.success(roleService.getRoleMenu(roleId));

    }
}





