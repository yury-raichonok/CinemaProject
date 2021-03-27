package com.company.model.message;

import java.io.Serializable;

public enum MessageTheme implements Serializable {

  CHANGE_FIRSTNAME("Change first name"),
  CHANGE_LASTNAME("Change last name"),
  CHANGE_BIRTHDAY("Change date of birth"),
  DELETE_ACCOUNT("Delete account"),
  RETURN_TICKET("Return ticket");

  private static final long serialVersionUID = 1L;

  private final String description;

  MessageTheme(String description){
    this.description = description;
  }

  public static MessageTheme getThemeByDescription(String description) {
    switch (description) {
      case "Change first name":
        return CHANGE_FIRSTNAME;
      case "Change last name":
        return CHANGE_LASTNAME;
      case "Change date of birth":
        return CHANGE_BIRTHDAY;
      case "Delete account":
        return DELETE_ACCOUNT;
      default:
        return RETURN_TICKET;
    }
  }

  public String getDescription() {
    return description;
  }
}
