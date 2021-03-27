package com.company.service;

import static com.company.model.message.MessageTheme.CHANGE_BIRTHDAY;
import static com.company.model.message.MessageTheme.CHANGE_FIRSTNAME;
import static com.company.model.message.MessageTheme.CHANGE_LASTNAME;
import static com.company.model.message.MessageTheme.DELETE_ACCOUNT;
import static com.company.model.user.User.getBirthdayFrom;
import static com.company.model.user.UserType.ADMIN;
import static com.company.model.user.UserType.REGULAR;
import static com.company.service.CryptService.hashPass;
import static com.company.service.DataBaseService.getRegularUsers;
import static com.company.service.DataBaseService.getUserByUserType;
import static com.company.service.DataBaseService.signUpUser;
import static com.company.service.DataBaseService.updateUserByUsername;
import static com.company.service.DataBaseService.updateUsersUsername;
import static com.company.service.FilmService.getChangesCanceled;
import static com.company.service.LogService.setLogProperties;
import static com.company.service.MessagesService.accountRemovingReq;
import static com.company.service.MessagesService.birthdayChangingReq;
import static com.company.service.MessagesService.firstnameChangingReq;
import static com.company.service.MessagesService.lastnameChangingReq;
import static com.company.service.MessagesService.sendMessage;
import static com.company.service.ValidationService.dateOfBirthValidation;
import static com.company.service.ValidationService.emailValidation;
import static com.company.service.ValidationService.inputValidation;
import static com.company.service.ValidationService.passwordValidation;
import static com.company.service.ValidationService.userVerification;
import static com.company.service.ValidationService.usernameAvailableValidation;
import static com.company.service.ValidationService.usernameValidation;
import static com.company.view.AdminView.adminMainMenu;
import static com.company.view.AdminView.setInAdminUsersMenu;
import static com.company.view.ManagerView.managerMainMenu;
import static com.company.view.ManagerView.setInManagerUsersMenu;
import static com.company.view.RegularUserView.userMainMenu;

import com.company.model.user.User;
import com.company.model.user.UserComparator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class AccountService {

  public static final int CHARACTERS_IN_NAME = 45;
  public static final int CHARACTERS_IN_USERNAME = 50;
  public static final int CHARACTERS_IN_EMAIL = 100;
  public static final String WRONG_INPUT = "Wrong input!";
  private static final Logger LOGGER = Logger.getLogger(AccountService.class);

  private static User cinemaUser;
  private static User selectedUser;

  public static void createAccount() {
    User user;
    String username;
    String password;
    String email;
    String firstname;
    String lastname;
    String date;
    LocalDate dateOfBirth;
    LocalDateTime registrationDate;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.printf("Enter username (it must contain from three to %d characters, "
              + "can contain numbers, letters and characters \"_\" and \"-\"):\n",
          CHARACTERS_IN_USERNAME);
      username = scanner.nextLine();
      if (!usernameAvailableValidation(username)) {
        System.out.println("This username is occupied by another user!");
      } else if (!usernameValidation(username)) {
        System.out.println(WRONG_INPUT);
      }
    } while (!usernameValidation(username));
    do {
      System.out.println("Enter password (it must contain minimum eight characters, "
          + "at least one uppercase letter, one lowercase letter and one number):");
      password = scanner.nextLine();
      if (!passwordValidation(password)) {
        System.out.println(WRONG_INPUT);
      } else if (password.equals(username)) {
        System.out.println("The password must be different from yhe username!");
      }
    } while (!passwordValidation(password));
    do {
      System.out.println("Enter email:");
      email = scanner.nextLine();
      if (!emailValidation(email)) {
        System.out.println(WRONG_INPUT);
      }
    } while (!emailValidation(email));
    do {
      System.out.println("Enter your first name:");
      firstname = scanner.nextLine();
      if (firstname.length() > CHARACTERS_IN_NAME) {
        System.out.printf("First name can be up to %d characters long\n!",
            CHARACTERS_IN_NAME);
      }
    } while (firstname.length() > CHARACTERS_IN_NAME);
    do {
      System.out.println("Enter your last name:");
      lastname = scanner.nextLine();
      if (lastname.length() > CHARACTERS_IN_NAME) {
        System.out.printf("Last name can be up to %d characters long!\n",
            CHARACTERS_IN_NAME);
      }
    } while (lastname.length() > CHARACTERS_IN_NAME);
    do {
      System.out.println("Enter your date of birth (example - \"01.01.2000\"):");
      date = scanner.nextLine();
    } while (!dateOfBirthValidation(date));
    dateOfBirth = LocalDate.parse(date, getBirthdayFrom());
    registrationDate = LocalDateTime.now();
    user = new User(username, password, email, firstname, lastname, dateOfBirth, registrationDate,
        LocalDateTime.now());
    signUpUser(user);
    setCinemaUser(user);
    setLogProperties();
    LOGGER.info("User created account.");
    userMainMenu();
  }

  public static void enterInAccount() {
    String username;
    String password;
    User user;
    int counter = 3;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("Enter username:");
      username = scanner.nextLine();
      if (DataBaseService.getUserByUsername(username) == null) {
        System.out.println("Sign in failed.\n"
            + "1. Try again;\n"
            + "2. Sing up;\n"
            + "3. Back.");
        switch (inputValidation()) {
          case 1:
            break;
          case 2:
            createAccount();
            return;
          case 3:
            return;
          default:
            System.out.println(WRONG_INPUT);
        }
      }
    } while (DataBaseService.getUserByUsername(username) == null);
    user = DataBaseService.getUserByUsername(username);
    do {
      if (counter == 3) {
        System.out.printf("Enter password (you have %d attempts):\n", counter);
      } else {
        System.out.printf("Try again (you have %d attempts):\n", counter);
      }
      password = scanner.nextLine();
      if (!user.getPassword().equals(hashPass(password))) {
        System.out.println("Wrong password!");
      }
      counter--;
      if (counter == 0) {
        System.out.println("Sign in failed. You entered wrong password 3 times.");
        return;
      }
    } while (!user.getPassword().equals(hashPass(password)));
    setCinemaUser(user);
    setLogProperties();
    LOGGER.info("User entered in account.");
    switch (getCinemaUser().getUSER_TYPE()) {
      case REGULAR:
        userMainMenu();
        break;
      case MANAGER:
        managerMainMenu();
        break;
      case ADMIN:
        adminMainMenu();
        break;
    }
  }

  public static void putMoney() {
    LOGGER.info("User entered the put money menu.");
    int amountOfMoney;
    while (true) {
      System.out.println("Enter the amount of money in BYR:");
      amountOfMoney = inputValidation();
      if (amountOfMoney > 0) {
        getCinemaUser().setMoney(getCinemaUser().getMoney() + amountOfMoney);
        updateUserByUsername(getCinemaUser());
        LOGGER.info("User put money on the balance.");
        System.out.println("Your balance is successfully replenished!");
        return;
      } else {
        System.out.println(WRONG_INPUT);
      }
    }
  }

  public static void withdrawMoney() {
    LOGGER.info("User entered the withdraw money menu.");
    int amountOfMoney;
    while (true) {
      System.out.println("Enter the amount of money in BYR:");
      amountOfMoney = inputValidation();
      if (amountOfMoney > 0 && amountOfMoney <= getCinemaUser().getMoney()) {
        getCinemaUser().setMoney(getCinemaUser().getMoney() - amountOfMoney);
        updateUserByUsername(getCinemaUser());
        LOGGER.info("User withdrawn money from the balance.");
        System.out.println("Money has been successfully withdrawn from the account!");
        return;
      } else if (amountOfMoney > getCinemaUser().getMoney()) {
        System.out
            .println("There is not enough money on the above balance to carry out this operation.");
        return;
      } else {
        System.out.println(WRONG_INPUT);
      }
    }
  }

  public static void changeUsername() {
    LOGGER.info("User entered the account change menu.");
    String username;
    Scanner scanner = new Scanner(System.in);
    if (userVerification()) {
      return;
    }
    do {
      System.out.printf("Enter new username (it must contain from three to %d characters, "
              + "can contain numbers, letters and characters \"_\" and \"-\"):\n",
          CHARACTERS_IN_USERNAME);
      username = scanner.nextLine();
      if (usernameAvailableValidation(username)) {
        System.out.println("This username is occupied by another user!");
      } else if (!usernameValidation(username)) {
        System.out.println(WRONG_INPUT);
      }
    } while (!usernameValidation(username));
    while (true) {
      System.out.printf(
          "Are you sure you want to change your username from \"%s\" to \"%s\"?\n"
              + "1. Yes;\n"
              + "2. No (changes will be canceled).\n",
          getCinemaUser().getUsername(), username);
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User changed username from \"%s\" to \"%s\".",
              getCinemaUser().getUsername(), username));
          updateUsersUsername(getCinemaUser(), username);
          getCinemaUser().setUsername(username);
          System.out.println("Username has been successfully changed!");
          return;
        case 2:
          System.out.println(getChangesCanceled());
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void changePassword() {
    LOGGER.info("User entered the password change menu.");
    String password;
    Scanner scanner = new Scanner(System.in);
    if (userVerification()) {
      return;
    }
    do {
      System.out.println("Enter new password (it must contain minimum eight characters, "
          + "at least one uppercase letter, one lowercase letter and one number):");
      password = scanner.nextLine();
      if (!passwordValidation(password)) {
        System.out.println(WRONG_INPUT);
      } else if (password.equals(getCinemaUser().getUsername())) {
        System.out.println("The password must be different from yhe username!");
      }
    } while (!passwordValidation(password));
    while (true) {
      System.out.println(
          "Are you sure you want to change your password?\n"
              + "1. Yes;\n"
              + "2. No (changes will be canceled).");
      switch (inputValidation()) {
        case 1:
          updateUserByUsername(getCinemaUser());
          getCinemaUser().setPassword(password);
          LOGGER.info("User changed his password.");
          System.out.println("Password has been successfully changed!");
          return;
        case 2:
          System.out.println(getChangesCanceled());
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void changeFirstname() {
    LOGGER.info("User entered the first name change menu.");
    String firstname;
    String reason;
    Scanner scanner = new Scanner(System.in);
    if (userVerification()) {
      return;
    }
    do {
      System.out.println("Enter new first name:");
      firstname = scanner.nextLine();
      if (firstname.length() > CHARACTERS_IN_NAME) {
        System.out.printf("First name can be up to %d characters long!",
            CHARACTERS_IN_NAME);
      }
    } while (firstname.length() > CHARACTERS_IN_NAME);

    System.out.println("Briefly describe the reason for the change:");
    reason = scanner.nextLine();
    while (true) {
      if (getCinemaUser().getUSER_TYPE().equals(REGULAR)) {
        System.out.printf(
            "Are you sure you want to change your first name from \"%s\" to \"%s\"?\n"
                + "1. Yes;\n"
                + "2. No (changes will be canceled).\n",
            getCinemaUser().getFirstname(), firstname);
      } else {
        System.out.printf(
            "Are you sure you want to change user's first name from \"%s\" to \"%s\"?\n"
                + "1. Yes;\n"
                + "2. No (changes will be canceled).\n",
            getCinemaUser().getFirstname(), firstname);
      }
      switch (inputValidation()) {
        case 1:
          switch (getCinemaUser().getUSER_TYPE()) {
            case REGULAR:
              LOGGER.info("User send a first name changing request to cinema administrator.");
              System.out
                  .printf("First name changing request has been sent to the cinema administrator, "
                          + "we will answer you shortly.\n"
                          + "For more information, you can contact our administrator by e-mail %s.",
                      getUserByUserType(ADMIN).getEmail());
              sendMessage(getUserByUserType(ADMIN), LocalDateTime.now(), CHANGE_FIRSTNAME,
                  String.format("Change first name from \"%s\" to \"%s\".\nReason: %s.",
                      getCinemaUser().getFirstname(), firstname, reason), firstname);
              return;
            case ADMIN:
              LOGGER.info(String.format("User changed first name of \"%s\".",
                  getSelectedUser().getUsername()));
              firstnameChangingReq(getSelectedUser(), firstname, reason);
              return;
          }
        case 2:
          System.out.println(getChangesCanceled());
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void changeLastname() {
    LOGGER.info("User entered the last name change menu.");
    String lastname;
    String reason;
    Scanner scanner = new Scanner(System.in);
    if (userVerification()) {
      return;
    }
    do {
      System.out.println("Enter new last name:");
      lastname = scanner.nextLine();
      if (lastname.length() > CHARACTERS_IN_NAME) {
        System.out.printf("Last name can be up to %d characters long!\n",
            CHARACTERS_IN_NAME);
      }
    } while (lastname.length() > CHARACTERS_IN_NAME);
    System.out.println("Briefly describe the reason for the change:");
    reason = scanner.nextLine();
    while (true) {
      if (getCinemaUser().getUSER_TYPE().equals(REGULAR)) {
        System.out.printf(
            "Are you sure you want to change your last name from \"%s\" to \"%s\"?\n"
                + "1. Yes;\n"
                + "2. No (changes will be canceled).\n",
            getCinemaUser().getLastname(), lastname);
      } else {
        System.out.printf(
            "Are you sure you want to change user's last name from \"%s\" to \"%s\"?\n"
                + "1. Yes;\n"
                + "2. No (changes will be canceled).\n",
            getSelectedUser().getLastname(), lastname);
      }
      switch (inputValidation()) {
        case 1:
          switch (getCinemaUser().getUSER_TYPE()) {
            case REGULAR:
              LOGGER.info("User send a last name changing request to cinema administrator.");
              System.out
                  .printf("Last name changing request has been sent to the cinema administrator, "
                          + "we will answer you shortly.\n"
                          + "For more information, you can contact our administrator by e-mail %s\n",
                      getUserByUserType(ADMIN).getEmail());
              sendMessage(getUserByUserType(ADMIN), LocalDateTime.now(), CHANGE_LASTNAME,
                  String.format("Change last name from \"%s\" to \"%s\".\nReason: %s.",
                      getCinemaUser().getLastname(), lastname, reason), lastname);
              return;
            case ADMIN:
              LOGGER.info(String.format("User changed last name of \"%s\".",
                  getSelectedUser().getUsername()));
              lastnameChangingReq(getSelectedUser(), lastname, reason);
              return;
          }
        case 2:
          System.out.println(getChangesCanceled());
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void changeEmail() {
    LOGGER.info("User entered the email change menu.");
    String email;
    Scanner scanner = new Scanner(System.in);
    if (userVerification()) {
      return;
    }
    do {
      System.out.println("Enter new email:");
      email = scanner.nextLine();
      if (!emailValidation(email)) {
        System.out.println(WRONG_INPUT);
      }
    } while (!emailValidation(email));
    while (true) {
      System.out.printf(
          "Are you sure you want to change your email from \"%s\" to \"%s\"?\n"
              + "1. Yes;\n"
              + "2. No (changes will be canceled).\n",
          getCinemaUser().getEmail(), email);
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User changed email from \"%s\" to \"%s\".",
              getCinemaUser().getEmail(), email));
          getCinemaUser().setEmail(email);
          updateUserByUsername(getCinemaUser());
          System.out.println("Email has been successfully changed!");
          return;
        case 2:
          System.out.println(getChangesCanceled());
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void changeDateOfBirth() {
    LOGGER.info("User entered the date of birth change menu.");
    String date;
    String reason;
    Scanner scanner = new Scanner(System.in);
    if (userVerification()) {
      return;
    }
    do {
      System.out.println("Enter new date of birth (example - \"01.01.2000\"):");
      date = scanner.nextLine();
    } while (!dateOfBirthValidation(date));
    System.out.println("Briefly describe the reason for the change:");
    reason = scanner.nextLine();
    while (true) {
      if (getCinemaUser().getUSER_TYPE().equals(REGULAR)) {
        System.out.printf(
            "Are you sure you want to change your date of birth from \"%s\" to \"%s\"?\n"
                + "1. Yes;\n"
                + "2. No (changes will be canceled).\n",
            getCinemaUser().getFormattedBirthday(), date);
      } else {
        System.out.printf(
            "Are you sure you want to change user's date of birth from \"%s\" to \"%s\"?\n"
                + "1. Yes;\n"
                + "2. No (changes will be canceled).\n",
            getSelectedUser().getFormattedBirthday(), date);
      }
      switch (inputValidation()) {
        case 1:
          switch (getCinemaUser().getUSER_TYPE()) {
            case REGULAR:
              LOGGER.info("User send a date of birth changing request to cinema administrator.");
              System.out.printf(
                  "Date of birth changing request has been sent to the cinema administrator, "
                      + "we will answer you shortly.\n"
                      + "For more information, you can contact our administrator by e-mail %s\n",
                  getUserByUserType(ADMIN).getEmail());
              sendMessage(getUserByUserType(ADMIN), LocalDateTime.now(), CHANGE_BIRTHDAY,
                  String.format("Change date of birth from \"%s\" to \"%s\".\nReason: %s.",
                      getCinemaUser().getFormattedBirthday(), date, reason), date);
              return;
            case ADMIN:
              LOGGER.info(String.format("User changed date of birth of \"%s\".",
                  getSelectedUser().getUsername()));
              birthdayChangingReq(getSelectedUser(), date, reason);
              return;
          }
        case 2:
          System.out.println(getChangesCanceled());
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void deleteAccount() {
    LOGGER.info("User entered the account removing menu.");
    if (userVerification()) {
      return;
    }
    String reason = null;
    Scanner scanner = new Scanner(System.in);
    if (getCinemaUser().getUSER_TYPE().equals(REGULAR)) {
      System.out.println("Briefly describe the reason for deleting your account:");
      reason = scanner.nextLine();
    }
    while (true) {
      System.out.println("Аre you sure you want to delete account?\n"
          + "1. Yes;\n"
          + "2. No.");
      switch (inputValidation()) {
        case 1:
          switch (getCinemaUser().getUSER_TYPE()) {
            case REGULAR:
              LOGGER.info("User send account removing request to cinema administrator.");
              System.out
                  .printf("Account removing request has been sent to the cinema administrator.\n"
                          + "For more information, you can contact our administrator by e-mail %s\n",
                      getUserByUserType(ADMIN).getEmail());
              sendMessage(getUserByUserType(ADMIN), LocalDateTime.now(), DELETE_ACCOUNT,
                  reason, getCinemaUser().getUsername());
              break;
            case ADMIN:
              LOGGER.info(String.format("User deleted account of \"%s\".",
                  getSelectedUser().getUsername()));
              accountRemovingReq(getSelectedUser());
              setSelectedUser(null);
              return;
          }
        case 2:
          LOGGER.info("User canceled changes.");
          System.out.println(getChangesCanceled());
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static User selectUser() {
    int positionInList;
    List<User> users = getRegularUsers();
    if (users.size() == 0) {
      System.out.println("There are no users in cinema!");
      setInAdminUsersMenu(false);
      setInManagerUsersMenu(false);
      return null;
    }
    users.sort(new UserComparator());
    System.out.println("Select user:");
    printUsersList(users);
    System.out.printf("%d. Back.\n", users.size() + 1);
    while (true) {
      positionInList = inputValidation();
      if (positionInList > 0 && positionInList <= users.size()) {
        LOGGER.info(String.format("User selected user \"%s\".",
            users.get(positionInList - 1).getUsername()));
        return users.get(positionInList - 1);
      } else if (positionInList == users.size() + 1) {
        setInAdminUsersMenu(false);
        setInManagerUsersMenu(false);
        return null;
      } else {
        System.out.println(WRONG_INPUT);
      }
    }
  }

  public static void printUsersList(List<User> users) {
    int index = 1;
    for (User user : users) {
      System.out.printf("%d. %s;\n", index, user.toString());
      index++;
    }
  }

  public static User getCinemaUser() {
    return cinemaUser;
  }

  public static void setCinemaUser(User cinemaUser) {
    AccountService.cinemaUser = cinemaUser;
  }

  public static User getSelectedUser() {
    return selectedUser;
  }

  public static void setSelectedUser(User selectedUser) {
    AccountService.selectedUser = selectedUser;
  }
}
