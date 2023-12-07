package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
  @TableName("sys_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String name;

      /**
     * 授课老师id
     */
      private Integer teacherId;

      /*表示表里没有这个数据*/
      @TableField(exist = false)
      private String teacher;

    private Float fee;

    private String coursetype;

    private String coursetime;

    private String evaluationStatus;

}
