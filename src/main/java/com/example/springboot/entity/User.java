package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xsy
 * @since 2023-04-15
 */
@Getter
@Setter
  @TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String username;

      private String password;

      private String nickname;

      private String email;

      private String address;

      private String phone;
      private String role;


      @TableField(exist = false)
      private List<Course> courses;

   //   private LocalDateTime create;


}
