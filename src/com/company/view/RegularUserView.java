package com.company.view;

import static com.company.service.AccountService.WRONG_INPUT;
import static com.company.service.AccountService.changeDateOfBirth;
import static com.company.service.AccountService.changeEmail;
import static com.company.service.AccountService.changeFirstname;
import static com.company.service.AccountService.changeLastname;
import static com.company.service.AccountService.changePassword;
import static com.company.service.AccountService.changeUsername;
import static com.company.service.AccountService.deleteAccount;
import static com.company.service.AccountService.getCinemaUser;
import static com.company.service.AccountService.putMoney;
import static com.company.service.AccountService.setCinemaUser;
import static com.company.service.AccountService.withdrawMoney;
import static com.company.service.DataBaseService.deleteMessage;
import static com.company.service.DataBaseService.getMessagesFromDB;
import static com.company.service.DataBaseService.updateUserByUsername;
import static com.company.service.FilmService.getSelectedFilm;
import static com.company.service.FilmService.selectFilm;
import static com.company.service.FilmService.setSelectedFilm;
import static com.company.service.MessagesService.getSelectedMessage;
import static com.company.service.MessagesService.selectMessage;
import static com.company.service.MessagesService.setSelectedMessage;
import static com.company.service.TicketsService.getFilmTickets;
import static com.company.service.TicketsService.getTickets;
import static com.company.service.ValidationService.inputValidation;
import static com.company.service.ValidationService.newMessagesValidation;
import static com.company.view.MainView.isComingSoonFilms;
import static com.company.view.MainView.isNowShowingFilms;
import static com.company.view.MainView.setComingSoonFilms;
import static com.company.view.MainView.setInEnteringMenu;
import static com.company.view.MainView.setNowShowingFilms;

import java.time.LocalDateTime;
import org.apache.log4j.Logger;

public class RegularUserView {

  private static final Logger LOGGER = Logger.getLogger(RegularUserView.class);

  private static boolean inUserFilmsMenu = false;
  private static boolean inUserMessagesMenu = false;
  private static boolean showUpcomingTickets = false;

  public static void userMainMenu() {
    System.out.printf("%s, welcome to our cinema!\n", getCinemaUser().getFirstname());
    while (true) {
      LOGGER.info("User opened main menu.");
      System.out.printf(
          "1. Films;\n"
              + "2. My profile;\n"
              + "3. My tickets;\n"
              + "4. Messages (%d, new - %d);\n"
              + "5. Log out;\n"
              + "6. Exit.\n", getMessagesFromDB(getCinemaUser().getUsername()).size(),
          newMessagesValidation(getCinemaUser().getTimeInMessagesMenu()));
      switch (inputValidation()) {
        case 1:
          userFilmsMenu();
          break;
        case 2:
          userProfileMenu();
          break;
        case 3:
          userTicketsMenu();
          break;
        case 4:
          userMessagesMenu();
          break;
        case 5:
          LOGGER.info("User exited the application.");
          setCinemaUser(null);
          return;
        case 6:
          LOGGER.info("User exited the application.");
          System.out.printf("Goodbye, %s!\n", getCinemaUser().getFirstname());
          setInEnteringMenu(false);
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void userFilmsMenu() {
    inUserFilmsMenu = true;
    while (inUserFilmsMenu) {
      LOGGER.info("User opened films menu.");
      System.out.println
          ("1. Now showing;\n"
              + "2. Coming soon;\n"
              + "3. Back in main menu.");
      switch (inputValidation()) {
        case 1:
          LOGGER.info("User selected now showing films.");
          setNowShowingFilms(true);
          userFilmChoiceMenu();
          break;
        case 2:
          LOGGER.info("User selected coming soon films.");
          setComingSoonFilms(true);
          userFilmChoiceMenu();
          break;
        case 3:
          inUserFilmsMenu = false;
          setNowShowingFilms(false);
          setComingSoonFilms(false);
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void userFilmChoiceMenu() {
    boolean purchasing;
    while (isNowShowingFilms() || isComingSoonFilms()) {
      LOGGER.info("User entered the film choice menu.");
      setSelectedFilm(selectFilm());
      if (getSelectedFilm() != null) {
        LOGGER.info(String.format("Selected film \"%s\".", getSelectedFilm().getName()));
        purchasing = true;
        if (isNowShowingFilms()) {
          while (purchasing) {
            System.out.println(getSelectedFilm().toString()
                + "\n1. Purchase ticket;\n"
                + "2. Back;\n"
                + "3. Back in main menu.");
            switch (inputValidation()) {
              case 1:
                getFilmTickets();
                setSelectedFilm(null);
                purchasing = false;
                break;
              case 2:
                setSelectedFilm(null);
                purchasing = false;
                break;
              case 3:
                setSelectedFilm(null);
                purchasing = false;
                setNowShowingFilms(false);
                inUserFilmsMenu = false;
                break;
              default:
                System.out.println(WRONG_INPUT);
                break;
            }
          }
        } else if (isComingSoonFilms()) {
          while (purchasing) {
            System.out.println(getSelectedFilm().filmInfo()
                + "\n1. Back;\n"
                + "2. Back in main menu.");
            switch (inputValidation()) {
              case 1:
                setSelectedFilm(null);
                purchasing = false;
                break;
              case 2:
                setSelectedFilm(null);
                purchasing = false;
                setComingSoonFilms(false);
                inUserFilmsMenu = false;
                break;
              default:
                System.out.println(WRONG_INPUT);
                break;
            }
          }
        }
      }
    }
  }

  public static void userTicketsMenu() {
    while (true) {
      LOGGER.info("User opened tickets menu.");
      System.out.println
          ("1. Tickets for upcoming events;\n"
              + "2. Tickets purchased for all time;\n"
              + "3. Back in main menu.");
      switch (inputValidation()) {
        case 1:
          showUpcomingTickets = true;
          getTickets(getCinemaUser());
          break;
        case 2:
          getTickets(getCinemaUser());
          break;
        case 3:
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void userProfileMenu() {
    while (true) {
      LOGGER.info("User opened profile menu.");
      getCinemaUser().printPersonalData();
      System.out.println("1. Put money in balance;\n"
          + "2. Withdraw money;\n"
          + "3. Change username;\n"
          + "4. Change password;\n"
          + "5. Change first name;\n"
          + "6. Change last name;\n"
          + "7. Change email;\n"
          + "8. Change date of birth;\n"
          + "9. Delete account;\n"
          + "10. Back in main menu.");
      switch (inputValidation()) {
        case 1:
          putMoney();
          break;
        case 2:
          withdrawMoney();
          break;
        case 3:
          changeUsername();
          break;
        case 4:
          changePassword();
          break;
        case 5:
          changeFirstname();
          break;
        case 6:
          changeLastname();
          break;
        case 7:
          changeEmail();
          break;
        case 8:
          changeDateOfBirth();
          break;
        case 9:
          deleteAccount();
          break;
        case 10:
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void userMessagesMenu() {
    inUserMessagesMenu = true;
    while (inUserMessagesMenu) {
      LOGGER.info("User opened messages menu.");
      setSelectedMessage(selectMessage());
      while (getSelectedMessage() != null) {
        System.out.println(getSelectedMessage().toString()
            + "\n1. Delete message;\n"
            + "2. Back;\n"
            + "3. Back in main menu.");
        switch (inputValidation()) {
          case 1:
            deleteMessage(getSelectedMessage());
            setSelectedMessage(null);
            getCinemaUser().setTimeInMessagesMenu(LocalDateTime.now());
            updateUserByUsername(getCinemaUser());
            break;
          case 2:
            setSelectedMessage(null);
            getCinemaUser().setTimeInMessagesMenu(LocalDateTime.now());
            updateUserByUsername(getCinemaUser());
            break;
          case 3:
            inUserMessagesMenu = false;
            setSelectedMessage(null);
            getCinemaUser().setTimeInMessagesMenu(LocalDateTime.now());
            updateUserByUsername(getCinemaUser());
            break;
          default:
            System.out.println(WRONG_INPUT);
        }
      }
    }
  }

  public static void setInUserMessagesMenu(boolean inUserAlertsMenu) {
    RegularUserView.inUserMessagesMenu = inUserAlertsMenu;
  }

  public static boolean isShowUpcomingTickets() {
    return showUpcomingTickets;
  }

  public static void setShowUpcomingTickets(boolean showUpcomingTickets) {
    RegularUserView.showUpcomingTickets = showUpcomingTickets;
  }
}
