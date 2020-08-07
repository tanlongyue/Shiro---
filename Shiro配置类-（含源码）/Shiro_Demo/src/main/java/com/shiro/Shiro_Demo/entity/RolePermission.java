package com.shiro.Shiro_Demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermission implements Serializable {

  private long id;
  private long roleId;
  private long permissionId;




}
