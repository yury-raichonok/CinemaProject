package com.company.service;

public class DBConstantService {

  public static final String USER_TABLE = "users";
  public static final String USER_USERNAME = "Username";
  public static final String USER_PASSWORD = "Password";
  public static final String USER_EMAIL = "Email";
  public static final String USER_FIRSTNAME = "Firstname";
  public static final String USER_LASTNAME = "Lastname";
  public static final String USER_USER_TYPE = "UserType";
  public static final String USER_BIRTHDAY = "Birthday";
  public static final String USER_MONEY = "Money";
  public static final String USER_TIME_IN_MESSAGES_MENU = "TimeInMessagesMenu";
  public static final String USER_REGISTRATION_DATE = "RegistrationDate";

  public static final String MESSAGES_TABLE = "messages";
  public static final String MESSAGES_ID = "ID";
  public static final String MESSAGES_SENDER = "Sender";
  public static final String MESSAGES_RECEIVER = "Receiver";
  public static final String MESSAGES_DATE_OF_SENDING = "DateOfSending";
  public static final String MESSAGES_THEME = "Theme";
  public static final String MESSAGES_MESSAGE = "Message";
  public static final String MESSAGES_VALUE = "Value";

  public static final String FILM_TABLE = "films";
  public static final String FILM_ID = "ID";
  public static final String FILM_NAME = "Name";
  public static final String FILM_PRODUCER = "Producer";
  public static final String FILM_GENRES = "FilmGenres";
  public static final String FILM_AGE_RESTRICTIONS = "AgeRestrictions";
  public static final String FILM_TICKET_COST = "TicketCost";
  public static final String FILM_TICKETS_AMOUNT = "TicketsAmount";
  public static final String FILM_DURATION = "FilmDuration";
  public static final String FILM_RELEASE_DATE = "ReleaseDate";
  public static final String FILM_SESSION_DATE = "SessionDate";
  public static final String FILM_IS_IN_RENT = "IsInRent";

  public static final String TICKET_TABLE = "tickets";
  public static final String TICKET_ID = "ID";
  public static final String TICKET_FILM_NAME = "FilmName";
  public static final String TICKET_SEAT_NUMBER = "SeatNumber";
  public static final String TICKET_COST = "Cost";
  public static final String TICKET_USER = "User";
  public static final String TICKET_IS_PURCHASED = "IsPurchased";
  public static final String TICKET_RETURN_REQUEST = "ReturnRequest";
  public static final String TICKET_PURCHASING_TIME = "PurchasingTime";
  public static final String TICKET_SESSION_DATE = "SessionDate";

  public static final String SIGN_UP_USER = "INSERT INTO " + USER_TABLE + "(" + USER_USERNAME
      + ", " + USER_PASSWORD + ", " + USER_EMAIL + ", " + USER_FIRSTNAME + ", " + USER_LASTNAME
      + ", " + USER_USER_TYPE + ", " + USER_BIRTHDAY + ", " + USER_MONEY + ", "
      + USER_TIME_IN_MESSAGES_MENU + ", " + USER_REGISTRATION_DATE
      + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String SELECT_ALL_USERS = "SELECT * FROM " + USER_TABLE;
  public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + USER_TABLE + " WHERE "
      + USER_USERNAME + "=?";
  public static final String SELECT_USER_BY_USERTYPE = "SELECT * FROM " + USER_TABLE + " WHERE "
      + USER_USER_TYPE + "=?";
  public static final String UPDATE_USER_BY_USERNAME = "UPDATE " + USER_TABLE + " SET "
      + USER_PASSWORD + "=?, " + USER_EMAIL + "=?, " + USER_FIRSTNAME + "=?, " + USER_LASTNAME
      + "=?, " + USER_USER_TYPE + "=?, " + USER_BIRTHDAY + "=?, " + USER_MONEY + "=?, "
      + USER_TIME_IN_MESSAGES_MENU + "=?, " + USER_REGISTRATION_DATE + "=? WHERE " + USER_USERNAME
      + "=?";
  public static final String UPDATE_USERNAME = "UPDATE " + USER_TABLE + " SET "
      + USER_USERNAME + "=? WHERE " + USER_USERNAME + "=?";
  public static final String DELETE_USER = "DELETE FROM " + USER_TABLE + " WHERE "
      + USER_USERNAME + "=?";

  public static final String ADD_MESSAGE = "INSERT INTO " + MESSAGES_TABLE + "(" + MESSAGES_SENDER
      + ", " + MESSAGES_RECEIVER + ", " + MESSAGES_DATE_OF_SENDING + ", " + MESSAGES_THEME + ", "
      + MESSAGES_MESSAGE + ", " + MESSAGES_VALUE + ") VALUES (?, ?, ?, ?, ?, ?)";
  public static final String SELECT_MESSAGES_BY_RECEIVER = "SELECT * FROM " + MESSAGES_TABLE
      + " WHERE " + MESSAGES_RECEIVER + "=?";
  public static final String DELETE_MESSAGE = "DELETE FROM " + MESSAGES_TABLE + " WHERE "
      + MESSAGES_ID + "=?";

  public static final String AD_FILM = "INSERT INTO " + FILM_TABLE + "(" + FILM_NAME
      + ", " + FILM_PRODUCER + ", " + FILM_GENRES + ", " + FILM_AGE_RESTRICTIONS + ", "
      + FILM_DURATION + ", " + FILM_RELEASE_DATE + ", " + FILM_IS_IN_RENT
      + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
  public static final String AD_FILM_SESSION = "INSERT INTO " + FILM_TABLE + "(" + FILM_NAME
      + ", " + FILM_PRODUCER + ", " + FILM_GENRES + ", " + FILM_AGE_RESTRICTIONS + ", "
      + FILM_TICKET_COST + ", " + FILM_TICKETS_AMOUNT + ", " + FILM_DURATION + ", "
      + FILM_RELEASE_DATE + ", " + FILM_SESSION_DATE + ", " + FILM_IS_IN_RENT
      + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String SELECT_ALL_FILMS = "SELECT * FROM " + FILM_TABLE;
  public static final String UPDATE_FILM_BY_ID = "UPDATE " + FILM_TABLE + " SET "
      + FILM_NAME + "=?, " + FILM_PRODUCER + "=?, " + FILM_GENRES + "=?, " + FILM_AGE_RESTRICTIONS
      + "=?, " + FILM_TICKET_COST + "=?, " + FILM_TICKETS_AMOUNT + "=?, " + FILM_DURATION + "=?, "
      + FILM_RELEASE_DATE + "=?, " + FILM_SESSION_DATE + "=?, " + FILM_IS_IN_RENT
      + "=? WHERE " + FILM_ID + "=?";
  public static final String UPDATE_FILM_BY_NAME = "UPDATE " + FILM_TABLE + " SET "
      + FILM_NAME + "=?, " + FILM_PRODUCER + "=?, " + FILM_GENRES + "=?, " + FILM_AGE_RESTRICTIONS
      + "=?, " + FILM_TICKET_COST + "=?, " + FILM_TICKETS_AMOUNT + "=?, " + FILM_DURATION + "=?, "
      + FILM_RELEASE_DATE + "=?, " + FILM_SESSION_DATE + "=?, " + FILM_IS_IN_RENT
      + "=? WHERE " + FILM_NAME + "=?";
  public static final String UPDATE_FILM_NOT_IN_RENT_BY_NAME = "UPDATE " + FILM_TABLE + " SET "
      + FILM_NAME + "=?, " + FILM_PRODUCER + "=?, " + FILM_GENRES + "=?, " + FILM_AGE_RESTRICTIONS
      + "=?, " + FILM_DURATION + "=?, " + FILM_RELEASE_DATE + "=?, " + FILM_IS_IN_RENT
      + "=? WHERE " + FILM_NAME + "=?";
  public static final String DELETE_FILM_BY_ID = "DELETE FROM " + FILM_TABLE + " WHERE "
      + FILM_ID + "=?";
  public static final String DELETE_FILM_BY_NAME = "DELETE FROM " + FILM_TABLE + " WHERE "
      + FILM_NAME + "=?";

  public static final String AD_TICKET = "INSERT INTO " + TICKET_TABLE + "(" + TICKET_FILM_NAME
      + ", " + TICKET_SEAT_NUMBER + ", " + TICKET_COST + ", " + TICKET_IS_PURCHASED + ", "
      + TICKET_RETURN_REQUEST + ", " + TICKET_SESSION_DATE + ") VALUES (?, ?, ?, ?, ?, ?)";
  public static final String SELECT_TICKET_BY_USER = "SELECT * FROM " + TICKET_TABLE + " WHERE "
      + TICKET_USER + "=?";
  public static final String SELECT_TICKET_BY_FILM = "SELECT * FROM " + TICKET_TABLE + " WHERE "
      + TICKET_FILM_NAME + "=? AND " + TICKET_SESSION_DATE + "=?";
  public static final String SELECT_TICKET_BY_ID = "SELECT * FROM " + TICKET_TABLE + " WHERE "
      + TICKET_ID + "=?";
  public static final String UPDATE_TICKET_BY_ID = "UPDATE " + TICKET_TABLE + " SET "
      + TICKET_FILM_NAME + "=?, " + TICKET_SEAT_NUMBER + "=?, " + TICKET_COST + "=?, "
      + TICKET_USER + "=?, " + TICKET_IS_PURCHASED + "=?, " + TICKET_RETURN_REQUEST + "=?, "
      + TICKET_PURCHASING_TIME + "=?, " + TICKET_SESSION_DATE + "=? WHERE " + TICKET_ID + "=?";
  public static final String UPDATE_TICKET_COST = "UPDATE " + TICKET_TABLE + " SET "
      + TICKET_COST + "=? WHERE " + TICKET_FILM_NAME + "=? AND " + TICKET_SESSION_DATE
      + "=?";
  public static final String UPDATE_TICKET_FILM_NAME = "UPDATE " + TICKET_TABLE + " SET "
      + TICKET_FILM_NAME + "=? WHERE " + TICKET_FILM_NAME + "=?";
  public static final String UPDATE_TICKET_SESSION_DATE = "UPDATE " + TICKET_TABLE + " SET "
      + TICKET_SESSION_DATE + "=? WHERE " + TICKET_FILM_NAME + "=? AND "
      + TICKET_SESSION_DATE + "=?";
  public static final String DELETE_TICKET_BY_SESSION = "DELETE FROM " + TICKET_TABLE + " WHERE "
      + TICKET_FILM_NAME + "=? AND " + TICKET_SESSION_DATE + "=?";
  public static final String DELETE_TICKET_BY_FILM = "DELETE FROM " + TICKET_TABLE + " WHERE "
      + TICKET_FILM_NAME + "=?";

}
