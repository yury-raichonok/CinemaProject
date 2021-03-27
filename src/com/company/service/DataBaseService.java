package com.company.service;

import static com.company.model.film.Film.filmGenresFromJson;
import static com.company.model.film.Film.filmGenresToJson;
import static com.company.model.film.Film.getReleaseDateForm;
import static com.company.model.message.Message.getMessageDateForm;
import static com.company.model.message.MessageTheme.getThemeByDescription;
import static com.company.model.ticket.Ticket.getPurchasingDateForm;
import static com.company.model.ticket.Ticket.getSessionDateForm;
import static com.company.model.user.User.getBirthdayFrom;
import static com.company.model.user.User.getInMessagesMenuForm;
import static com.company.model.user.User.getRegForm;
import static com.company.model.user.UserType.getUserTypeByName;
import static com.company.service.CryptService.hashPass;
import static com.company.service.DBConstantService.ADD_MESSAGE;
import static com.company.service.DBConstantService.AD_FILM;
import static com.company.service.DBConstantService.AD_FILM_SESSION;
import static com.company.service.DBConstantService.AD_TICKET;
import static com.company.service.DBConstantService.DELETE_FILM_BY_ID;
import static com.company.service.DBConstantService.DELETE_FILM_BY_NAME;
import static com.company.service.DBConstantService.DELETE_MESSAGE;
import static com.company.service.DBConstantService.DELETE_TICKET_BY_FILM;
import static com.company.service.DBConstantService.DELETE_TICKET_BY_SESSION;
import static com.company.service.DBConstantService.DELETE_USER;
import static com.company.service.DBConstantService.FILM_AGE_RESTRICTIONS;
import static com.company.service.DBConstantService.FILM_DURATION;
import static com.company.service.DBConstantService.FILM_GENRES;
import static com.company.service.DBConstantService.FILM_ID;
import static com.company.service.DBConstantService.FILM_IS_IN_RENT;
import static com.company.service.DBConstantService.FILM_NAME;
import static com.company.service.DBConstantService.FILM_PRODUCER;
import static com.company.service.DBConstantService.FILM_RELEASE_DATE;
import static com.company.service.DBConstantService.FILM_SESSION_DATE;
import static com.company.service.DBConstantService.FILM_TICKETS_AMOUNT;
import static com.company.service.DBConstantService.FILM_TICKET_COST;
import static com.company.service.DBConstantService.MESSAGES_DATE_OF_SENDING;
import static com.company.service.DBConstantService.MESSAGES_ID;
import static com.company.service.DBConstantService.MESSAGES_MESSAGE;
import static com.company.service.DBConstantService.MESSAGES_RECEIVER;
import static com.company.service.DBConstantService.MESSAGES_SENDER;
import static com.company.service.DBConstantService.MESSAGES_THEME;
import static com.company.service.DBConstantService.MESSAGES_VALUE;
import static com.company.service.DBConstantService.SELECT_ALL_FILMS;
import static com.company.service.DBConstantService.SELECT_ALL_USERS;
import static com.company.service.DBConstantService.SELECT_MESSAGES_BY_RECEIVER;
import static com.company.service.DBConstantService.SELECT_TICKET_BY_FILM;
import static com.company.service.DBConstantService.SELECT_TICKET_BY_ID;
import static com.company.service.DBConstantService.SELECT_TICKET_BY_USER;
import static com.company.service.DBConstantService.SELECT_USER_BY_USERNAME;
import static com.company.service.DBConstantService.SELECT_USER_BY_USERTYPE;
import static com.company.service.DBConstantService.SIGN_UP_USER;
import static com.company.service.DBConstantService.TICKET_COST;
import static com.company.service.DBConstantService.TICKET_FILM_NAME;
import static com.company.service.DBConstantService.TICKET_ID;
import static com.company.service.DBConstantService.TICKET_IS_PURCHASED;
import static com.company.service.DBConstantService.TICKET_PURCHASING_TIME;
import static com.company.service.DBConstantService.TICKET_RETURN_REQUEST;
import static com.company.service.DBConstantService.TICKET_SEAT_NUMBER;
import static com.company.service.DBConstantService.TICKET_SESSION_DATE;
import static com.company.service.DBConstantService.TICKET_USER;
import static com.company.service.DBConstantService.UPDATE_FILM_BY_ID;
import static com.company.service.DBConstantService.UPDATE_FILM_BY_NAME;
import static com.company.service.DBConstantService.UPDATE_FILM_NOT_IN_RENT_BY_NAME;
import static com.company.service.DBConstantService.UPDATE_TICKET_BY_ID;
import static com.company.service.DBConstantService.UPDATE_TICKET_COST;
import static com.company.service.DBConstantService.UPDATE_TICKET_FILM_NAME;
import static com.company.service.DBConstantService.UPDATE_TICKET_SESSION_DATE;
import static com.company.service.DBConstantService.UPDATE_USERNAME;
import static com.company.service.DBConstantService.UPDATE_USER_BY_USERNAME;
import static com.company.service.DBConstantService.USER_BIRTHDAY;
import static com.company.service.DBConstantService.USER_EMAIL;
import static com.company.service.DBConstantService.USER_FIRSTNAME;
import static com.company.service.DBConstantService.USER_LASTNAME;
import static com.company.service.DBConstantService.USER_MONEY;
import static com.company.service.DBConstantService.USER_PASSWORD;
import static com.company.service.DBConstantService.USER_REGISTRATION_DATE;
import static com.company.service.DBConstantService.USER_TIME_IN_MESSAGES_MENU;
import static com.company.service.DBConstantService.USER_USERNAME;
import static com.company.service.DBConstantService.USER_USER_TYPE;

import com.company.model.film.Film;
import com.company.model.film.FilmGenre;
import com.company.model.message.Message;
import com.company.model.message.MessageTheme;
import com.company.model.ticket.Ticket;
import com.company.model.user.User;
import com.company.model.user.UserType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataBaseService {

  private static PreparedStatement preparedStatement;

  public static PreparedStatement createPreparedStatement(String sql) {
    try {
      preparedStatement = DBConnectorService.getInstance().openConnection().prepareStatement(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return preparedStatement;
  }

  private static Statement createStatement() throws SQLException {
    return DBConnectorService.getInstance().openConnection().createStatement();
  }

  public static void signUpUser(User user) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(SIGN_UP_USER);
      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setString(2, hashPass(user.getPassword()));
      preparedStatement.setString(3, user.getEmail());
      preparedStatement.setString(4, user.getFirstname());
      preparedStatement.setString(5, user.getLastname());
      preparedStatement.setString(6, user.getUSER_TYPE().getName());
      preparedStatement.setString(7, user.getFormattedBirthday());
      preparedStatement.setInt(8, user.getMoney());
      preparedStatement.setString(9, user.getFormattedTimeInMessagesMenu());
      preparedStatement.setString(10, user.getFormattedRegistrationDate());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static List<User> getAllUsersFromDB() {
    List<User> users = new ArrayList<>();
    try {
      ResultSet resultSet = createStatement().executeQuery(SELECT_ALL_USERS);
      while (resultSet.next()) {
        String username = resultSet.getString(USER_USERNAME);
        String password = resultSet.getString(USER_PASSWORD);
        String email = resultSet.getString(USER_EMAIL);
        String firstname = resultSet.getString(USER_FIRSTNAME);
        String lastname = resultSet.getString(USER_LASTNAME);
        UserType userType = getUserTypeByName(resultSet.getString(USER_USER_TYPE));
        LocalDate dateOfBirth = LocalDate.parse(resultSet.getString(USER_BIRTHDAY),
            getBirthdayFrom());
        int money = resultSet.getInt(USER_MONEY);
        LocalDateTime timeInMessagesMenu = LocalDateTime
            .parse(resultSet.getString(USER_TIME_IN_MESSAGES_MENU), getInMessagesMenuForm());
        LocalDateTime registrationDate = LocalDateTime.
            parse(resultSet.getString(USER_REGISTRATION_DATE), getRegForm());
        users.add(new User(username, password, email, firstname, lastname, dateOfBirth, money,
            registrationDate, timeInMessagesMenu, userType));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    return users;
  }

  public static User getUserByUsername(String name) {
    User selectedUser = null;
    List<User> users = new ArrayList<>();
    try {
      PreparedStatement preparedStatement = createPreparedStatement(SELECT_USER_BY_USERNAME);
      preparedStatement.setString(1, name);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String username = resultSet.getString(USER_USERNAME);
        String password = resultSet.getString(USER_PASSWORD);
        String email = resultSet.getString(USER_EMAIL);
        String firstname = resultSet.getString(USER_FIRSTNAME);
        String lastname = resultSet.getString(USER_LASTNAME);
        UserType userType = getUserTypeByName(resultSet.getString(USER_USER_TYPE));
        LocalDate dateOfBirth = LocalDate.parse(resultSet.getString(USER_BIRTHDAY),
            getBirthdayFrom());
        int money = resultSet.getInt(USER_MONEY);
        LocalDateTime timeInMessagesMenu = LocalDateTime
            .parse(resultSet.getString(USER_TIME_IN_MESSAGES_MENU), getInMessagesMenuForm());
        LocalDateTime registrationDate = LocalDateTime.
            parse(resultSet.getString(USER_REGISTRATION_DATE), getRegForm());
        users.add(new User(username, password, email, firstname, lastname, dateOfBirth, money,
            registrationDate, timeInMessagesMenu, userType));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    for (User user : users) {
      selectedUser = user;
    }
    return selectedUser;
  }

  public static User getUserByUserType(UserType type) {
    User selectedUser = null;
    List<User> users = new ArrayList<>();
    try {
      PreparedStatement preparedStatement = createPreparedStatement(SELECT_USER_BY_USERTYPE);
      preparedStatement.setString(1, type.name());
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String username = resultSet.getString(USER_USERNAME);
        String password = resultSet.getString(USER_PASSWORD);
        String email = resultSet.getString(USER_EMAIL);
        String firstname = resultSet.getString(USER_FIRSTNAME);
        String lastname = resultSet.getString(USER_LASTNAME);
        UserType userType = getUserTypeByName(resultSet.getString(USER_USER_TYPE));
        LocalDate dateOfBirth = LocalDate.parse(resultSet.getString(USER_BIRTHDAY),
            getBirthdayFrom());
        int money = resultSet.getInt(USER_MONEY);
        LocalDateTime timeInMessagesMenu = LocalDateTime
            .parse(resultSet.getString(USER_TIME_IN_MESSAGES_MENU), getInMessagesMenuForm());
        LocalDateTime registrationDate = LocalDateTime.
            parse(resultSet.getString(USER_REGISTRATION_DATE), getRegForm());
        users.add(new User(username, password, email, firstname, lastname, dateOfBirth, money,
            registrationDate, timeInMessagesMenu, userType));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    for (User user : users) {
      selectedUser = user;
    }
    return selectedUser;
  }

  public static List<User> getRegularUsers() {
    List<User> users = new ArrayList<>();
    try {
      PreparedStatement preparedStatement = createPreparedStatement(SELECT_USER_BY_USERTYPE);
      preparedStatement.setString(1, UserType.REGULAR.name());
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String username = resultSet.getString(USER_USERNAME);
        String password = resultSet.getString(USER_PASSWORD);
        String email = resultSet.getString(USER_EMAIL);
        String firstname = resultSet.getString(USER_FIRSTNAME);
        String lastname = resultSet.getString(USER_LASTNAME);
        UserType userType = getUserTypeByName(resultSet.getString(USER_USER_TYPE));
        LocalDate dateOfBirth = LocalDate.parse(resultSet.getString(USER_BIRTHDAY),
            getBirthdayFrom());
        int money = resultSet.getInt(USER_MONEY);
        LocalDateTime timeInMessagesMenu = LocalDateTime
            .parse(resultSet.getString(USER_TIME_IN_MESSAGES_MENU), getInMessagesMenuForm());
        LocalDateTime registrationDate = LocalDateTime.
            parse(resultSet.getString(USER_REGISTRATION_DATE), getRegForm());
        users.add(new User(username, password, email, firstname, lastname, dateOfBirth, money,
            registrationDate, timeInMessagesMenu, userType));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    return users;
  }

  public static void updateUserByUsername(User user) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(UPDATE_USER_BY_USERNAME);
      preparedStatement.setString(1, user.getPassword());
      preparedStatement.setString(2, user.getEmail());
      preparedStatement.setString(3, user.getFirstname());
      preparedStatement.setString(4, user.getLastname());
      preparedStatement.setString(5, user.getUSER_TYPE().getName());
      preparedStatement.setString(6, user.getFormattedBirthday());
      preparedStatement.setInt(7, user.getMoney());
      preparedStatement.setString(8, user.getFormattedTimeInMessagesMenu());
      preparedStatement.setString(9, user.getFormattedRegistrationDate());
      preparedStatement.setString(10, user.getUsername());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void updateUsersUsername(User user, String username) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(UPDATE_USERNAME);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, user.getUsername());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void deleteUser(User user) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(DELETE_USER);
      preparedStatement.setString(1, user.getUsername());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void addMessage(Message message) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(ADD_MESSAGE);
      preparedStatement.setString(1, message.getSENDER().getUsername());
      preparedStatement.setString(2, message.getRECEIVER().getUsername());
      preparedStatement.setString(3, message.getFormattedDateOfSending());
      preparedStatement.setString(4, message.getTHEME().getDescription());
      preparedStatement.setString(5, message.getMESSAGE());
      preparedStatement.setString(6, message.getVALUE());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static List<Message> getMessagesFromDB(String username) {
    List<Message> messages = new ArrayList<>();
    try {
      PreparedStatement preparedStatement = createPreparedStatement(SELECT_MESSAGES_BY_RECEIVER);
      preparedStatement.setString(1, username);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int id = resultSet.getInt(MESSAGES_ID);
        User sender = getUserByUsername(resultSet.getString(MESSAGES_SENDER));
        User receiver = getUserByUsername(resultSet.getString(MESSAGES_RECEIVER));
        LocalDateTime dateOfSending = LocalDateTime.
            parse(resultSet.getString(MESSAGES_DATE_OF_SENDING), getMessageDateForm());
        MessageTheme theme = getThemeByDescription(resultSet.getString(MESSAGES_THEME));
        String message = resultSet.getString(MESSAGES_MESSAGE);
        String value = resultSet.getString(MESSAGES_VALUE);
        messages.add(new Message(id, sender, receiver, dateOfSending, theme, message, value));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    return messages;
  }

  public static void deleteMessage(Message message) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(DELETE_MESSAGE);
      preparedStatement.setInt(1, message.getId());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void addNewFilmToDB(Film film) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(AD_FILM);
      preparedStatement.setString(1, film.getName());
      preparedStatement.setString(2, film.getProducer());
      preparedStatement.setString(3, filmGenresToJson(film.getFilmGenres()));
      preparedStatement.setInt(4, film.getAgeRestrictions());
      preparedStatement.setInt(5, (int) film.getFilmDuration().toMinutes());
      preparedStatement.setString(6, film.getFormattedReleaseDate());
      preparedStatement.setBoolean(7, film.isInRent());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void addNewFilmSessionToDB(Film film) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(AD_FILM_SESSION);
      preparedStatement.setString(1, film.getName());
      preparedStatement.setString(2, film.getProducer());
      preparedStatement.setString(3, filmGenresToJson(film.getFilmGenres()));
      preparedStatement.setInt(4, film.getAgeRestrictions());
      preparedStatement.setInt(5, film.getTicketCost());
      preparedStatement.setInt(6, film.getTicketsAmount());
      preparedStatement.setInt(7, (int) film.getFilmDuration().toMinutes());
      preparedStatement.setString(8, film.getFormattedReleaseDate());
      preparedStatement.setString(9, film.getFormattedSessionsDate());
      preparedStatement.setBoolean(10, film.isInRent());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static List<Film> getAllFilmsFromDB() {
    List<Film> films = new ArrayList<>();
    try {
      ResultSet resultSet = createStatement().executeQuery(SELECT_ALL_FILMS);
      while (resultSet.next()) {
        int id = resultSet.getInt(FILM_ID);
        String name = resultSet.getString(FILM_NAME);
        String producer = resultSet.getString(FILM_PRODUCER);
        Set<FilmGenre> filmGenres = filmGenresFromJson(resultSet.getString(FILM_GENRES));
        int ageRestrictions = resultSet.getInt(FILM_AGE_RESTRICTIONS);
        boolean isInRent = resultSet.getBoolean(FILM_IS_IN_RENT);
        if (isInRent) {
          int ticketCost = resultSet.getInt(FILM_TICKET_COST);
          int ticketsAmount = resultSet.getInt(FILM_TICKETS_AMOUNT);
          Duration filmDuration = Duration.ofMinutes(resultSet.getInt(FILM_DURATION));
          LocalDate releaseDate = LocalDate.parse(resultSet.getString(FILM_RELEASE_DATE),
              getReleaseDateForm());
          LocalDateTime sessionDate = LocalDateTime.parse(resultSet.getString(FILM_SESSION_DATE),
              getSessionDateForm());
          films.add(new Film(id, name, producer, filmGenres, ageRestrictions, ticketCost,
              ticketsAmount, filmDuration, releaseDate, sessionDate));
        } else {
          Duration filmDuration = Duration.ofMinutes(resultSet.getInt(FILM_DURATION));
          LocalDate releaseDate = LocalDate.parse(resultSet.getString(FILM_RELEASE_DATE),
              getReleaseDateForm());
          films.add(new Film(id, name, producer, filmGenres, ageRestrictions, filmDuration,
              releaseDate));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    return films;
  }

  public static void updateFilmById(Film film) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(UPDATE_FILM_BY_ID);
      preparedStatement.setString(1, film.getName());
      preparedStatement.setString(2, film.getProducer());
      preparedStatement.setString(3, filmGenresToJson(film.getFilmGenres()));
      preparedStatement.setInt(4, film.getAgeRestrictions());
      preparedStatement.setInt(5, film.getTicketCost());
      preparedStatement.setInt(6, film.getTicketsAmount());
      preparedStatement.setInt(7, (int) film.getFilmDuration().toMinutes());
      preparedStatement.setString(8, film.getFormattedReleaseDate());
      preparedStatement.setString(9, film.getFormattedSessionsDate());
      preparedStatement.setBoolean(10, film.isInRent());
      preparedStatement.setInt(11, film.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void updateAllFilmsWithName(Film film, String filmName) {
    try {
      PreparedStatement preparedStatement;
      if (film.isInRent()) {
        preparedStatement = createPreparedStatement(UPDATE_FILM_BY_NAME);
        preparedStatement.setString(1, filmName);
        preparedStatement.setString(2, film.getProducer());
        preparedStatement.setString(3, filmGenresToJson(film.getFilmGenres()));
        preparedStatement.setInt(4, film.getAgeRestrictions());
        preparedStatement.setInt(5, film.getTicketCost());
        preparedStatement.setInt(6, film.getTicketsAmount());
        preparedStatement.setInt(7, (int) film.getFilmDuration().toMinutes());
        preparedStatement.setString(8, film.getFormattedReleaseDate());
        preparedStatement.setString(9, film.getFormattedSessionsDate());
        preparedStatement.setBoolean(10, film.isInRent());
        preparedStatement.setString(11, film.getName());
      } else {
        preparedStatement = createPreparedStatement(
            UPDATE_FILM_NOT_IN_RENT_BY_NAME);
        preparedStatement.setString(1, filmName);
        preparedStatement.setString(2, film.getProducer());
        preparedStatement.setString(3, filmGenresToJson(film.getFilmGenres()));
        preparedStatement.setInt(4, film.getAgeRestrictions());
        preparedStatement.setInt(5, (int) film.getFilmDuration().toMinutes());
        preparedStatement.setString(6, film.getFormattedReleaseDate());
        preparedStatement.setBoolean(7, film.isInRent());
        preparedStatement.setString(8, film.getName());
      }
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void deleteFilmById(Film film) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(DELETE_FILM_BY_ID);
      preparedStatement.setInt(1, film.getId());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void deleteFilmByName(Film film) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(DELETE_FILM_BY_NAME);
      preparedStatement.setString(1, film.getName());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void addTicket(Ticket ticket) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(AD_TICKET);
      preparedStatement.setString(1, ticket.getFILM_NAME());
      preparedStatement.setInt(2, ticket.getSEAT_NUMBER());
      preparedStatement.setInt(3, ticket.getCOST());
      preparedStatement.setBoolean(4, ticket.isPurchased());
      preparedStatement.setBoolean(5, ticket.isReturnRequest());
      preparedStatement.setString(6, ticket.getFormattedSSessionDate());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static List<Ticket> getTicketsByUser(String username) {
    List<Ticket> tickets = new ArrayList<>();
    try {
      PreparedStatement preparedStatement = createPreparedStatement(SELECT_TICKET_BY_USER);
      preparedStatement.setString(1, username);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int id = resultSet.getInt(TICKET_ID);
        String filmName = resultSet.getString(TICKET_FILM_NAME);
        int seatNumber = resultSet.getInt(TICKET_SEAT_NUMBER);
        int cost = resultSet.getInt(TICKET_COST);
        User user = getUserByUsername(resultSet.getString(TICKET_USER));
        boolean isPurchased = resultSet.getBoolean(TICKET_IS_PURCHASED);
        boolean returnRequest = resultSet.getBoolean(TICKET_RETURN_REQUEST);
        LocalDateTime purchasingTime = LocalDateTime.
            parse(resultSet.getString(TICKET_PURCHASING_TIME), getPurchasingDateForm());
        LocalDateTime sessionDate = LocalDateTime.parse(resultSet.getString(TICKET_SESSION_DATE),
            getSessionDateForm());
        if (isPurchased) {
          tickets.add(new Ticket(id, filmName, seatNumber, cost, user, true, returnRequest,
              purchasingTime, sessionDate));
        } else {
          tickets.add(new Ticket(id, filmName, seatNumber, cost, sessionDate));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    return tickets;
  }

  public static List<Ticket> getTicketsByFilm(Film film) {
    List<Ticket> tickets = new ArrayList<>();
    try {
      PreparedStatement preparedStatement = createPreparedStatement(SELECT_TICKET_BY_FILM);
      preparedStatement.setString(1, film.getName());
      preparedStatement.setString(2, film.getFormattedSessionsDate());
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int id = resultSet.getInt(TICKET_ID);
        String filmName = resultSet.getString(TICKET_FILM_NAME);
        int seatNumber = resultSet.getInt(TICKET_SEAT_NUMBER);
        int cost = resultSet.getInt(TICKET_COST);
        boolean isPurchased = resultSet.getBoolean(TICKET_IS_PURCHASED);
        LocalDateTime sessionDate = LocalDateTime.parse(resultSet.getString(TICKET_SESSION_DATE),
            getSessionDateForm());
        if (isPurchased) {
          User user = getUserByUsername(resultSet.getString(TICKET_USER));
          boolean returnRequest = resultSet.getBoolean(TICKET_RETURN_REQUEST);
          LocalDateTime purchasingTime = LocalDateTime.
              parse(resultSet.getString(TICKET_PURCHASING_TIME), getPurchasingDateForm());
          tickets.add(new Ticket(id, filmName, seatNumber, cost, user, true, returnRequest,
              purchasingTime, sessionDate));
        } else {
          tickets.add(new Ticket(id, filmName, seatNumber, cost, sessionDate));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    return tickets;
  }

  public static Ticket getTicketById(int searchedId) {
    Ticket myTicket = null;
    List<Ticket> tickets = new ArrayList<>();
    try {
      PreparedStatement preparedStatement = createPreparedStatement(SELECT_TICKET_BY_ID);
      preparedStatement.setInt(1, searchedId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int id = resultSet.getInt(TICKET_ID);
        String filmName = resultSet.getString(TICKET_FILM_NAME);
        int seatNumber = resultSet.getInt(TICKET_SEAT_NUMBER);
        int cost = resultSet.getInt(TICKET_COST);
        User user = getUserByUsername(resultSet.getString(TICKET_USER));
        boolean isPurchased = resultSet.getBoolean(TICKET_IS_PURCHASED);
        boolean returnRequest = resultSet.getBoolean(TICKET_RETURN_REQUEST);
        LocalDateTime purchasingTime = LocalDateTime.
            parse(resultSet.getString(TICKET_PURCHASING_TIME), getPurchasingDateForm());
        LocalDateTime sessionDate = LocalDateTime.parse(resultSet.getString(TICKET_SESSION_DATE),
            getSessionDateForm());
        tickets.add(new Ticket(id, filmName, seatNumber, cost, user, isPurchased, returnRequest,
            purchasingTime, sessionDate));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
    for (Ticket ticket : tickets) {
      myTicket = ticket;
    }
    return myTicket;
  }

  public static void updateTicketById(Ticket ticket) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(UPDATE_TICKET_BY_ID);
      preparedStatement.setString(1, ticket.getFILM_NAME());
      preparedStatement.setInt(2, ticket.getSEAT_NUMBER());
      preparedStatement.setInt(3, ticket.getCOST());
      preparedStatement.setString(4, ticket.getUser().getUsername());
      preparedStatement.setBoolean(5, ticket.isPurchased());
      preparedStatement.setBoolean(6, ticket.isReturnRequest());
      preparedStatement.setString(7, ticket.getFormattedPurchasingTime());
      preparedStatement.setString(8, ticket.getFormattedSSessionDate());
      preparedStatement.setInt(9, ticket.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void returnTicketById(Ticket ticket) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(UPDATE_TICKET_BY_ID);
      preparedStatement.setString(1, ticket.getFILM_NAME());
      preparedStatement.setInt(2, ticket.getSEAT_NUMBER());
      preparedStatement.setInt(3, ticket.getCOST());
      preparedStatement.setString(4, null);
      preparedStatement.setBoolean(5, false);
      preparedStatement.setBoolean(6, false);
      preparedStatement.setString(7, null);
      preparedStatement.setString(8, ticket.getFormattedSSessionDate());
      preparedStatement.setInt(9, ticket.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void updateTicketFilmName(Film film, String filmName) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(UPDATE_TICKET_FILM_NAME);
      preparedStatement.setString(1, filmName);
      preparedStatement.setString(2, film.getName());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void updateTicketSessionDate(Film film, String sessionDate) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(UPDATE_TICKET_SESSION_DATE);
      preparedStatement.setString(1, sessionDate);
      preparedStatement.setString(2, film.getName());
      preparedStatement.setString(3, film.getFormattedSessionsDate());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void updateTicketsCost(Film film) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(UPDATE_TICKET_COST);
      preparedStatement.setInt(1, film.getTicketCost());
      preparedStatement.setString(2, film.getName());
      preparedStatement.setString(3, film.getFormattedSessionsDate());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void deleteTicketsBySession(Film film) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(DELETE_TICKET_BY_SESSION);
      preparedStatement.setString(1, film.getName());
      preparedStatement.setString(2, film.getFormattedSessionsDate());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }

  public static void deleteTicketsByFilm(Film film) {
    try {
      PreparedStatement preparedStatement = createPreparedStatement(DELETE_TICKET_BY_FILM);
      preparedStatement.setString(1, film.getName());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DBConnectorService.getInstance().closeConnection();
  }
}
