package com.shiro.Shiro_Demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRole implements Serializable {

  private long id;
  private long roleId;
  private long userId;
  private String remarks;


}
