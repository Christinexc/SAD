package com.example.springboot.service.impl;

import com.example.springboot.entity.Menu;
import com.example.springboot.mapper.MenuMapper;
import com.example.springboot.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
