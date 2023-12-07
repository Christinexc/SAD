package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Application;
import com.example.springboot.mapper.ApplicationMapper;
import com.example.springboot.mapper.MessageMapper;
import com.example.springboot.service.IApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
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
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {
    @Resource
    ApplicationMapper applicationMapper;
    @Override
    public Page<Application> findPage(Page<Application> page, Integer id) {
        return applicationMapper.findPage(page,id);
    }
}
