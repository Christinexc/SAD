package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Dict;
import com.example.springboot.entity.Menu;
import com.example.springboot.mapper.DictMapper;
import com.example.springboot.service.IMenuService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;
    @PostMapping
    public Result save(@RequestBody Menu menu) {
            menuService.saveOrUpdate(menu);
            return Result.success();
            }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
           menuService.removeById(id);
        return Result.success();
            }

    @GetMapping
    public Result findAll() {
            return Result.success( menuService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success( menuService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam (defaultValue = "")String name) {
        QueryWrapper<Menu>queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        return Result.success(menuService.page(new Page<>(pageNum, pageSize),queryWrapper));
        }
    @GetMapping("/icons")
    public Result getIcons() {
        QueryWrapper<Dict>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success( dictMapper.selectList(null));
    }

}




