package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xsy
 * @since 2023-04-16
 */
@Getter
@Setter
  @TableName("student_course")
public class StudentCourse implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer studentId;

      private Integer courseId;
      @TableField(exist = false)
      private String nickname;
    private String feeStatus;

    private String bookStatus;

    private String checkIn;
    private String evaluateStatus;
    private String evaluation;
    private String success;
    @TableField(exist = false)
    private List<Course>courses;




}
