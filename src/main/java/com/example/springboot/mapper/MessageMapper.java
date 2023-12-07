package com.example.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xsy
 * @since 2023-12-04
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    Page<Message> findPage(Page<Message> page, @Param("id") Integer id);
}
