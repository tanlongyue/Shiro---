package com.shiro.Shiro_Demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Role implements Serializable {

  private long id;
  private String name;
  private String description;

  private List<Permission> permissionList;


}
