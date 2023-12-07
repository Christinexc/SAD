package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.controller.UserDto;
import com.example.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
public interface IUserService extends IService<User> {

    UserDto login(UserDto userDto);

    Page<User> findPage(Page<User> objectPage, String username, String id);

    Page<User> findRolePage(Page<User> objectPage, String roleId);
}
