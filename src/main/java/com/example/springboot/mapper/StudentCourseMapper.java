package com.example.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.StudentCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {

    Page<User> findPage(Page<User> page, @Param("username") String username, @Param("id") String id,@Param("course_id") String course_id);

    void updateStuM(@Param("course_id") int course_id, @Param("stu_id") int stu_id,@Param("book_status")String book_status,@Param("check_in") String check_in,@Param("fee_status") String fee_status);
}
