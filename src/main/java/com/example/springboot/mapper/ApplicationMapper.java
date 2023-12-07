package com.example.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

    Page<Application> findPage(Page<Application> page,  @Param("id") Integer id);
}
