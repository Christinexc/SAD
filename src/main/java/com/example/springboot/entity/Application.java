package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-04-15
 */
@Getter
@Setter
  @TableName("sys_application")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;
    private Integer companyId;
    private String comecompany;

    private String status;

    private String techname;

    private String introduce;

    private String opinion;


}
