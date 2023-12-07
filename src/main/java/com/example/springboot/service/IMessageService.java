package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xsy
 * @since 2023-12-04
 */
public interface IMessageService extends IService<Message> {

    Page<Message> findPage(Page<Message> objectPage, Integer id);
}
