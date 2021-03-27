package com.company.service;

import static com.company.model.film.Film.getReleaseDateForm;
import static com.company.model.ticket.Ticket.getSessionDateForm;
import static com.company.service.AccountService.CHARACTERS_IN_EMAIL;
import static com.company.service.AccountService.CHARACTERS_IN_USERNAME;
import static com.company.service.AccountService.WRONG_INPUT;
import static com.company.service.AccountService.getCinemaUser;
import static com.company.service.CryptService.hashPass;
import static com.company.service.DataBaseService.getAllUsersFromDB;
import static com.company.service.DataBaseService.getMessagesFromDB;

import com.company.model.message.Message;
import com.company.model.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class ValidationService {

  private static final Logger LOGGER = Logger.getLogger(ValidationService.class);

  public static int inputValidation() {
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      if (scanner.hasNextInt()) {
        return scanner.nextInt();
      } else {
        System.out.println(WRONG_INPUT);
        scanner.next();
      }
    }
    return scanner.nextInt();
  }

  public static boolean usernameAvailableValidation(String username) {
    List<User> users = getAllUsersFromDB();
    for (User user : users) {
      if (user.getUsername().equals(username)) {
        return false;
      }
    }
    return true;
  }

  public static boolean usernameValidation(String username) {
    if (!usernameAvailableValidation(username)) {
      System.out.println("Username is too long!");
      return false;
    } else if (username.length() > CHARACTERS_IN_USERNAME) {
      return false;
    } else {
      Pattern usernamePattern = Pattern.compile("^[\\w-]{3,19}$");
      Matcher usernameMatcher = usernamePattern.matcher(username);
      return usernameMatcher.find();
    }
  }

  public static boolean passwordValidation(String password) {
    Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
    Matcher passwordMatcher = passwordPattern.matcher(password);
    return passwordMatcher.find();
  }

  public static boolean emailValidation(String email) {
    if (email.length() > CHARACTERS_IN_EMAIL) {
      System.out.println("Email is too long!");
      return false;
    } else {
      Pattern emailPattern = Pattern.compile
          ("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$");
      Matcher emailMatcher = emailPattern.matcher(email);
      return emailMatcher.find();
    }
  }

  public static boolean dateOfBirthValidation(String dateOfBirth) {
    DateTimeFormatter dateOfBirthFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDate birthday;
    try {
      birthday = LocalDate.parse(dateOfBirth, dateOfBirthFormatter);
    } catch (DateTimeParseException e) {
      System.out.println(WRONG_INPUT);
      return false;
    }
    if (birthday.isAfter(LocalDate.now()) ||
        birthday.isBefore(LocalDate.of(1900, 1, 1))) {
      System.out.println(WRONG_INPUT);
      return false;
    } else {
      return true;
    }
  }

  public static boolean sessionDateValidation(String sessionDate) {
    LocalDateTime session;
    try {
      session = LocalDateTime.parse(sessionDate, getSessionDateForm());
    } catch (DateTimeParseException e) {
      System.out.println(WRONG_INPUT);
      return false;
    }
    if (session.isBefore(LocalDateTime.now())) {
      System.out.println(WRONG_INPUT);
      return false;
    } else {
      return true;
    }
  }

  public static boolean releaseDateValidation(String releaseDate) {
    try {
      LocalDate.parse(releaseDate, getReleaseDateForm());
      return true;
    } catch (DateTimeParseException e) {
      System.out.println(WRONG_INPUT);
      return false;
    }
  }

  public static int newMessagesValidation(LocalDateTime dateTime) {
    int newMassages = 0;
    for (Message message : getMessagesFromDB(getCinemaUser().getUsername())) {
      if (dateTime.isBefore(message.getDATE_OF_SENDING())) {
        newMassages++;
      }
    }
    return newMassages;
  }


  public static boolean userVerification() {
    String password;
    int counter = 3;
    Scanner scanner = new Scanner(System.in);
    do {
      if (counter == 3) {
        System.out.printf("Enter password (you have %d attempts):\n", counter);
      } else {
        System.out.printf("Try again (you have %d attempts):\n", counter);
      }
      password = scanner.nextLine();
      if (!getCinemaUser().getPassword().equals(hashPass(password))) {
        System.out.println("Wrong password!");
      }
      counter--;
      if (counter == 0) {
        System.out.println("Edition failed. You entered wrong password 3 times.");
        LOGGER.info("Edition failed. User entered wrong password 3 times.");
        return true;
      }
    } while (!getCinemaUser().getPassword().equals(hashPass(password)));
    return false;
  }
}