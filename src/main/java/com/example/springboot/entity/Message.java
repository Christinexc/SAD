package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xsy
 * @since 2023-12-04
 */
@Getter
@Setter
  @TableName("sys_message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private Integer studentId;

    private Integer courseId;

      /**
     * 评价表或课程通知
     */
      private String type;

    private String content;

    private LocalDateTime time;

    private String sentFrom;


}
