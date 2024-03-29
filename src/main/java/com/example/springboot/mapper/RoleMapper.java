package com.example.springboot.mapper;

import com.example.springboot.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select id from sys_role where flag=#{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}
