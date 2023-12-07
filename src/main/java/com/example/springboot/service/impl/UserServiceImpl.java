package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.controller.UserDto;
import com.example.springboot.entity.Menu;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.RoleMapper;
import com.example.springboot.mapper.RoleMenuMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IMenuService;
import com.example.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;
    public UserDto login(UserDto userDto) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        queryWrapper.eq("password",userDto.getPassword());
        User one=getOne(queryWrapper);
        if(one!=null) {

            BeanUtils.copyProperties(one, userDto, String.valueOf(true));
            String role=one.getRole();
            Integer roleId= roleMapper.selectByFlag(role);
            List<Integer>menuIds= roleMenuMapper.selectByRoleId(roleId);
            List<Menu>menus= menuService.list();//查出所以菜单
            List<Menu>roleMenus=new ArrayList<>();
            //筛选用户菜单
            for(Menu menu :menus)
            {
                if(menuIds.contains((menu.getId())))
                {
                    roleMenus.add(menu);
                }
            }
            userDto.setNickname(one.getNickname());
            userDto.setMenus(roleMenus);
            return userDto;
        }else{
            throw new ServiceException( Constants.CODE_600,"用户名或密码错误");
        }

    }

    @Override
    public Page<User> findPage(Page<User> page, String username, String id) {
        return userMapper.findPage(page,username,id);
    }

    @Override
    public Page<User> findRolePage(Page<User> objectPage, String roleId) {
        return userMapper.findRolePage(objectPage,roleId);
    }
}
