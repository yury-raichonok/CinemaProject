package com.company.view;

import static com.company.service.AccountService.WRONG_INPUT;
import static com.company.service.AccountService.changeDateOfBirth;
import static com.company.service.AccountService.changeFirstname;
import static com.company.service.AccountService.changeLastname;
import static com.company.service.AccountService.deleteAccount;
import static com.company.service.AccountService.getCinemaUser;
import static com.company.service.AccountService.getSelectedUser;
import static com.company.service.AccountService.selectUser;
import static com.company.service.AccountService.setCinemaUser;
import static com.company.service.AccountService.setSelectedUser;
import static com.company.service.DataBaseService.deleteMessage;
import static com.company.service.DataBaseService.getMessagesFromDB;
import static com.company.service.DataBaseService.updateUserByUsername;
import static com.company.service.FilmService.deleteFilmSession;
import static com.company.service.FilmService.editFilmAgeRestrictions;
import static com.company.service.FilmService.editFilmDateOfRelease;
import static com.company.service.FilmService.editFilmDuration;
import static com.company.service.FilmService.editFilmGenres;
import static com.company.service.FilmService.editFilmName;
import static com.company.service.FilmService.editFilmProducer;
import static com.company.service.FilmService.editFilmSession;
import static com.company.service.FilmService.getSelectedFilm;
import static com.company.service.FilmService.selectFilm;
import static com.company.service.FilmService.setSelectedFilm;
import static com.company.service.MessagesService.accountRemovingReq;
import static com.company.service.MessagesService.birthdayChangingReq;
import static com.company.service.MessagesService.denyAccountRemovingReq;
import static com.company.service.MessagesService.denyBirthdayChangingReq;
import static com.company.service.MessagesService.denyFirstnameChangingReq;
import static com.company.service.MessagesService.denyLastnameChangingReq;
import static com.company.service.MessagesService.firstnameChangingReq;
import static com.company.service.MessagesService.getSelectedMessage;
import static com.company.service.MessagesService.lastnameChangingReq;
import static com.company.service.MessagesService.selectMessage;
import static com.company.service.MessagesService.setSelectedMessage;
import static com.company.service.ValidationService.inputValidation;
import static com.company.service.ValidationService.newMessagesValidation;
import static com.company.view.MainView.setAllFilms;
import static com.company.view.MainView.setInEnteringMenu;

import java.time.LocalDateTime;
import org.apache.log4j.Logger;

public class AdminView {

  private static final Logger LOGGER = Logger.getLogger(AdminView.class);

  private static boolean inAdminFilmsMenu = false;
  private static boolean inAdminUsersMenu = false;
  private static boolean inAdminMessagesMenu = false;

  public static void adminMainMenu() {
    System.out.println("Cinema admin menu.");
    while (true) {
      LOGGER.info("User opened main menu.");
      System.out.printf(
          "1. Films in cinema;\n"
              + "2. Users list;\n"
              + "3. Messages (%d, new - %d);\n"
              + "4. Log out;\n"
              + "5. Exit.\n", getMessagesFromDB(getCinemaUser().getUsername()).size(),
          newMessagesValidation(getCinemaUser().getTimeInMessagesMenu()));
      switch (inputValidation()) {
        case 1:
          adminFilmsMenu();
          break;
        case 2:
          adminUsersMenu();
          break;
        case 3:
          adminMessagesMenu();
          break;
        case 4:
          LOGGER.info("User exited the application.");
          setCinemaUser(null);
          return;
        case 5:
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

  public static void adminFilmsMenu() {
    inAdminFilmsMenu = true;
    setAllFilms(true);
    while (inAdminFilmsMenu) {
      LOGGER.info("User opened films menu.");
      setSelectedFilm(selectFilm());
      while (getSelectedFilm() != null) {
        if (!getSelectedFilm().isInRent()) {
          System.out.println(getSelectedFilm().filmInfo()
              + "\n1. Edit film name;\n"
              + "2. Edit film producer;\n"
              + "3. Edit age rating of the film;\n"
              + "4. Edit film date of release ;\n"
              + "5. Edit film duration;\n"
              + "6. Edit film genre(s);\n"
              + "7. Delete film (session);\n"
              + "8. Back;\n"
              + "9. Back in main menu.");
          switch (inputValidation()) {
            case 1:
              editFilmName(getSelectedFilm());
              break;
            case 2:
              editFilmProducer(getSelectedFilm());
              break;
            case 3:
              editFilmAgeRestrictions(getSelectedFilm());
              break;
            case 4:
              editFilmDateOfRelease(getSelectedFilm());
              break;
            case 5:
              editFilmDuration(getSelectedFilm());
              break;
            case 6:
              editFilmGenres(getSelectedFilm());
              break;
            case 7:
              deleteFilmSession(getSelectedFilm());
              break;
            case 8:
              setSelectedFilm(null);
              break;
            case 9:
              inAdminFilmsMenu = false;
              setAllFilms(false);
              setSelectedFilm(null);
              return;
            default:
              System.out.println(WRONG_INPUT);
              break;
          }
        } else if (getSelectedFilm().getSessionDate().isAfter(LocalDateTime.now())) {
          System.out.println(getSelectedFilm().filmInfo()
              + "\n1. Edit session;\n"
              + "2. Edit film name;\n"
              + "3. Edit film producer;\n"
              + "4. Edit age rating of the film;\n"
              + "5. Edit film date of release ;\n"
              + "6. Edit film duration;\n"
              + "7. Edit film genre(s);\n"
              + "8. Delete film (session);\n"
              + "9. Back;\n"
              + "10. Back in main menu.");
          switch (inputValidation()) {
            case 1:
              editFilmSession(getSelectedFilm());
              break;
            case 2:
              editFilmName(getSelectedFilm());
              break;
            case 3:
              editFilmProducer(getSelectedFilm());
              break;
            case 4:
              editFilmAgeRestrictions(getSelectedFilm());
              break;
            case 5:
              editFilmDateOfRelease(getSelectedFilm());
              break;
            case 6:
              editFilmDuration(getSelectedFilm());
              break;
            case 7:
              editFilmGenres(getSelectedFilm());
              break;
            case 8:
              deleteFilmSession(getSelectedFilm());
              break;
            case 9:
              setSelectedFilm(null);
              break;
            case 10:
              inAdminFilmsMenu = false;
              setAllFilms(false);
              setSelectedFilm(null);
              return;
            default:
              System.out.println(WRONG_INPUT);
              break;
          }
        } else {
          System.out.println(getSelectedFilm().filmInfo()
              + "\n1. Delete film (session);\n"
              + "2. Back;\n"
              + "3. Back in main menu.");
          switch (inputValidation()) {
            case 1:
              deleteFilmSession(getSelectedFilm());
              setSelectedFilm(null);
              break;
            case 2:
              setSelectedFilm(null);
              break;
            case 3:
              inAdminFilmsMenu = false;
              setAllFilms(false);
              setSelectedFilm(null);
              return;
            default:
              System.out.println(WRONG_INPUT);
              break;
          }
        }
      }
    }
  }

  public static void adminUsersMenu() {
    inAdminUsersMenu = true;
    while (inAdminUsersMenu) {
      LOGGER.info("User opened users menu.");
      setSelectedUser(selectUser());
      while (getSelectedUser() != null) {
        getSelectedUser().printPersonalData();
        System.out.println("1. Change first name;\n"
            + "2. Change last name;\n"
            + "3. Change date of birth;\n"
            + "4. Delete account;\n"
            + "5. Back;\n"
            + "6. Back in main menu.");
        switch (inputValidation()) {
          case 1:
            changeFirstname();
            break;
          case 2:
            changeLastname();
            break;
          case 3:
            changeDateOfBirth();
            break;
          case 4:
            deleteAccount();
            break;
          case 5:
            setSelectedUser(null);
            break;
          case 6:
            setSelectedUser(null);
            inAdminUsersMenu = false;
            return;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
    }
  }

  public static void adminMessagesMenu() {
    inAdminMessagesMenu = true;
    while (inAdminMessagesMenu) {
      LOGGER.info("User opened messages menu.");
      setSelectedMessage(selectMessage());
      while (getSelectedMessage() != null) {
        System.out.println(getSelectedMessage().toString()
            + "\n1. Accept request;\n"
            + "2. Deny request;\n"
            + "3. Back;\n"
            + "4. Back in main menu.");
        switch (inputValidation()) {
          case 1:
            switch (getSelectedMessage().getTHEME()) {
              case CHANGE_FIRSTNAME:
                firstnameChangingReq(getSelectedMessage().getSENDER(),
                    getSelectedMessage().getVALUE(),
                    String.format("Your first name has been changed to \"%s\".",
                        getSelectedMessage().getVALUE()));
                deleteMessage(getSelectedMessage());
                setSelectedMessage(null);
                LOGGER.info("User approved first name changing request.");
                break;
              case CHANGE_LASTNAME:
                lastnameChangingReq(getSelectedMessage().getSENDER(),
                    getSelectedMessage().getVALUE(),
                    String.format("Your last name has been changed to \"%s\".",
                        getSelectedMessage().getVALUE()));
                deleteMessage(getSelectedMessage());
                setSelectedMessage(null);
                LOGGER.info("User rejected last name changing request.");
                break;
              case CHANGE_BIRTHDAY:
                birthdayChangingReq(getSelectedMessage().getSENDER(),
                    getSelectedMessage().getVALUE(),
                    String.format("Your date of bithh has been changed to \"%s\".",
                        getSelectedMessage().getVALUE()));
                deleteMessage(getSelectedMessage());
                setSelectedMessage(null);
                LOGGER.info("User approved date of birth changing request.");
                break;
              case DELETE_ACCOUNT:
                accountRemovingReq(getSelectedMessage().getSENDER());
                deleteMessage(getSelectedMessage());
                setSelectedMessage(null);
                LOGGER.info("User rejected account removing request.");
                break;
            }
            break;
          case 2:
            switch (getSelectedMessage().getTHEME()) {
              case CHANGE_FIRSTNAME:
                denyFirstnameChangingReq(getSelectedMessage().getSENDER(),
                    getSelectedMessage().getVALUE(), "First name change request denied.");
                deleteMessage(getSelectedMessage());
                setSelectedMessage(null);
                LOGGER.info("User rejected first name changing request.");
                break;
              case CHANGE_LASTNAME:
                denyLastnameChangingReq(getSelectedMessage().getSENDER(),
                    getSelectedMessage().getVALUE(), "Last name change request denied.");
                deleteMessage(getSelectedMessage());
                setSelectedMessage(null);
                LOGGER.info("User rejected last name changing request.");
                break;
              case CHANGE_BIRTHDAY:
                denyBirthdayChangingReq(getSelectedMessage().getSENDER(),
                    getSelectedMessage().getVALUE(), "Date of birth change request denied.");
                deleteMessage(getSelectedMessage());
                setSelectedMessage(null);
                LOGGER.info("User rejected date of birth changing request.");
                break;
              case DELETE_ACCOUNT:
                denyAccountRemovingReq(getSelectedMessage().getSENDER());
                deleteMessage(getSelectedMessage());
                setSelectedMessage(null);
                LOGGER.info("User rejected account removing request.");
                break;
            }
            break;
          case 3:
            setSelectedMessage(null);
            getCinemaUser().setTimeInMessagesMenu(LocalDateTime.now());
            updateUserByUsername(getCinemaUser());
            break;
          case 4:
            inAdminMessagesMenu = false;
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

  public static void setInAdminFilmsMenu(boolean inAdminFilmsMenu) {
    AdminView.inAdminFilmsMenu = inAdminFilmsMenu;
  }

  public static void setInAdminUsersMenu(boolean inAdminUsersMenu) {
    AdminView.inAdminUsersMenu = inAdminUsersMenu;
  }

  public static void setInAdminMessagesMenu(boolean inAdminMessagesMenu) {
    AdminView.inAdminMessagesMenu = inAdminMessagesMenu;
  }
}
