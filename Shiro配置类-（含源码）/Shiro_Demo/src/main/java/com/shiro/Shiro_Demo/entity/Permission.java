package com.shiro.Shiro_Demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {

  private long id;
  private String name;
  private String url;


}
