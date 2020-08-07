package com.shiro.Shiro_Demo.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class User implements Serializable {

  private Integer id;
  private String username;
  private String password;
  private Date createTime;
  private String salt;

  private List<Role> roleList;
}
