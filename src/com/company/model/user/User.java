package com.company.model.user;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String BIRTHDAY_PAT = "dd.MM.yyyy";
  private static final String REG_DATE_PAT = "dd.MM.yyyy HH:mm";
  private static final String IN_MES_MENU_PAT = "dd.MM.yyyy HH:mm";
  private static final DateTimeFormatter BIRTHDAY_FROM = DateTimeFormatter.ofPattern(BIRTHDAY_PAT);
  private static final DateTimeFormatter REG_FORM = DateTimeFormatter.ofPattern(REG_DATE_PAT);
  private static final DateTimeFormatter IN_MESSAGES_MENU_FORM = DateTimeFormatter.
      ofPattern(IN_MES_MENU_PAT);

  private final LocalDateTime REGISTRATION_DATE;
  private final UserType USER_TYPE;

  private String username;
  private String password;
  private String email;
  private String firstname;
  private String lastname;
  private LocalDate birthday;
  private int money;
  private LocalDateTime timeInMessagesMenu;

  public User(String username, String password, String email, String firstname, String lastname,
      LocalDate birthday, LocalDateTime registrationDate, LocalDateTime timeInMessagesMenu) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.USER_TYPE = UserType.REGULAR;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthday = birthday;
    this.money = 0;
    this.REGISTRATION_DATE = registrationDate;
    this.timeInMessagesMenu = timeInMessagesMenu;
  }

  public User(String username, String password, String email, String firstname, String lastname,
      LocalDate birthday, int money, LocalDateTime registrationDate,
      LocalDateTime timeInMessagesMenu, UserType userType) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthday = birthday;
    this.money = money;
    this.USER_TYPE = userType;
    this.REGISTRATION_DATE = registrationDate;
    this.timeInMessagesMenu = timeInMessagesMenu;
  }

  public static DateTimeFormatter getBirthdayFrom() {
    return BIRTHDAY_FROM;
  }

  public static DateTimeFormatter getRegForm() {
    return REG_FORM;
  }

  public static DateTimeFormatter getInMessagesMenuForm() {
    return IN_MESSAGES_MENU_FORM;
  }

  public void printPersonalData() {
    System.out.printf("%s %s (%s)\n"
            + "Date of birth - %s (%d y.o.)\n"
            + "On your balance - %d BYN\n"
            + "Email - %s\n"
            + "Registration date - %s\n",
        firstname, lastname, username, getFormattedBirthday(), getAge(), money, email,
        getFormattedRegistrationDate());
  }

  public int getAge() {
    return Period.between(birthday, LocalDate.now()).getYears();
  }

  @Override
  public String toString() {
    return firstname + " " + lastname + " (" + username + "), "
        + getAge() + "y.o. ("
        + getFormattedBirthday() + "), email - " + email
        + ", registered " + getFormattedRegistrationDate();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return money == user.money && Objects.equals(REGISTRATION_DATE, user.REGISTRATION_DATE)
        && Objects.equals(username, user.username) && Objects
        .equals(password, user.password) && Objects.equals(email, user.email)
        && Objects.equals(firstname, user.firstname) && Objects
        .equals(lastname, user.lastname) && USER_TYPE == user.USER_TYPE && Objects
        .equals(birthday, user.birthday) && Objects
        .equals(timeInMessagesMenu, user.timeInMessagesMenu);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(REGISTRATION_DATE, username, password, email, firstname, lastname, USER_TYPE,
            birthday,
            money, timeInMessagesMenu);
  }

  public String getFormattedRegistrationDate() {
    return REGISTRATION_DATE.format(REG_FORM);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserType getUSER_TYPE() {
    return USER_TYPE;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public void setBirthday(String date) {
    this.birthday = LocalDate.parse(date, BIRTHDAY_FROM);
  }

  public String getFormattedBirthday() {
    return birthday.format(BIRTHDAY_FROM);
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public LocalDateTime getTimeInMessagesMenu() {
    return timeInMessagesMenu;
  }

  public void setTimeInMessagesMenu(LocalDateTime timeInMessagesMenu) {
    this.timeInMessagesMenu = timeInMessagesMenu;
  }

  public String getFormattedTimeInMessagesMenu() {
    return timeInMessagesMenu.format(IN_MESSAGES_MENU_FORM);
  }
}
