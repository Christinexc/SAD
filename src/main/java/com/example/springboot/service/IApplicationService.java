package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Application;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Message;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
public interface IApplicationService extends IService<Application> {

    Page<Application> findPage(Page<Application> objectPage, Integer id);
}
