package com.company.model.user;

import java.io.Serializable;

public enum UserType implements Serializable {

  REGULAR("Regular"),
  MANAGER("Manager"),
  ADMIN("Admin");

  private static final long serialVersionUID = 1L;

  private String name;

  UserType(String name) {
    this.name = name;
  }

  public static UserType getUserTypeByName(String name) {
    switch (name) {
      case "Manager":
        return MANAGER;
      case "Admin":
        return ADMIN;
      default:
        return REGULAR;
    }
  }

  public String getName() {
    return name;
  }

//  public void setName(String name) {
//    this.name = name;
//  }
}
