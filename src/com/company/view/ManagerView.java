package com.company.view;

import static com.company.service.AccountService.WRONG_INPUT;
import static com.company.service.AccountService.getCinemaUser;
import static com.company.service.AccountService.getSelectedUser;
import static com.company.service.AccountService.selectUser;
import static com.company.service.AccountService.setCinemaUser;
import static com.company.service.AccountService.setSelectedUser;
import static com.company.service.DataBaseService.deleteMessage;
import static com.company.service.DataBaseService.getMessagesFromDB;
import static com.company.service.DataBaseService.getTicketById;
import static com.company.service.DataBaseService.updateUserByUsername;
import static com.company.service.FilmService.addFilmToRent;
import static com.company.service.FilmService.addNewSession;
import static com.company.service.FilmService.createFilm;
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
import static com.company.service.MessagesService.denyReturnTicketRequest;
import static com.company.service.MessagesService.getSelectedMessage;
import static com.company.service.MessagesService.returnTicketToUser;
import static com.company.service.MessagesService.selectMessage;
import static com.company.service.MessagesService.setSelectedMessage;
import static com.company.service.TicketsService.getTickets;
import static com.company.service.ValidationService.inputValidation;
import static com.company.service.ValidationService.newMessagesValidation;
import static com.company.view.MainView.setAllFilms;
import static com.company.view.MainView.setComingSoonFilms;
import static com.company.view.MainView.setInEnteringMenu;
import static com.company.view.MainView.setNowShowingFilms;

import java.time.LocalDateTime;
import org.apache.log4j.Logger;

public class ManagerView {

  private static final Logger LOGGER = Logger.getLogger(ManagerView.class);

  private static boolean inManagerFilmsMenu = false;
  private static boolean inManagerRentMenu = false;
  private static boolean inManagerSessionsMenu = false;
  private static boolean inManagerUsersMenu = false;
  private static boolean inManagerMessagesMenu = false;

  public static void managerMainMenu() {
    System.out.println("Cinema manager menu.");
    while (true) {
      LOGGER.info("User opened main menu.");
      System.out.printf(
          "1. Add film to the rent;\n"
              + "2. Add new session;\n"
              + "3. Create new film;\n"
              + "4. Films in cinema;\n"
              + "5. Users list;\n"
              + "6. Messages (%d, new - %d);\n"
              + "7. Log out;\n"
              + "8. Exit.\n", getMessagesFromDB(getCinemaUser().getUsername()).size(),
          newMessagesValidation(getCinemaUser().getTimeInMessagesMenu()));
      switch (inputValidation()) {
        case 1:
          managerRentMenu();
          break;
        case 2:
          managerSessionsMenu();
          break;
        case 3:
          createFilm();
          break;
        case 4:
          managerFilmsMenu();
          break;
        case 5:
          managerUsersMenu();
          break;
        case 6:
          managerMessagesMenu();
          break;
        case 7:
          LOGGER.info("User exited the application.");
          setCinemaUser(null);
          return;
        case 8:
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

  public static void managerFilmsMenu() {
    inManagerFilmsMenu = true;
    setAllFilms(true);
    while (inManagerFilmsMenu) {
      LOGGER.info("User opened films menu.");
      setSelectedFilm(selectFilm());
      while (getSelectedFilm() != null) {
        if (!getSelectedFilm().isInRent()) {
          System.out.println(getSelectedFilm().filmInfo()
              + "\n1. Edit film name;\n"
              + "2. Edit film producer;\n"
              + "3. Edit film age rating;\n"
              + "4. Edit film date of release ;\n"
              + "5. Edit film duration;\n"
              + "6. Edit film genre(s);\n"
              + "7. Back;\n"
              + "8. Back in main menu.");
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
              setSelectedFilm(null);
              break;
            case 8:
              inManagerFilmsMenu = false;
              setAllFilms(false);
              setSelectedFilm(null);
              return;
            default:
              System.out.println(WRONG_INPUT);
              break;
          }
        } else if (getSelectedFilm().getSessionDate().isAfter(LocalDateTime.now())) {
          System.out.println(getSelectedFilm().filmInfo()
              + "\n1. Edit film session;\n"
              + "2. Edit film name;\n"
              + "3. Edit film producer;\n"
              + "4. Edit film age rating;\n"
              + "5. Edit film date of release ;\n"
              + "6. Edit film duration;\n"
              + "7. Edit film genre(s);\n"
              + "8. Back;\n"
              + "9. Back in main menu.");
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
              setSelectedFilm(null);
              break;
            case 9:
              inManagerFilmsMenu = false;
              setAllFilms(false);
              setSelectedFilm(null);
              return;
            default:
              System.out.println(WRONG_INPUT);
              break;
          }
        } else {
          System.out.println(getSelectedFilm().filmInfo()
              + "\nThis session can be deleted by admin.\n"
              + "1. Back;\n"
              + "2. Back in main menu.");
          switch (inputValidation()) {
            case 1:
              setSelectedFilm(null);
              break;
            case 2:
              inManagerFilmsMenu = false;
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

  public static void managerRentMenu() {
    inManagerRentMenu = true;
    setComingSoonFilms(true);
    while (inManagerRentMenu) {
      LOGGER.info("User opened rent menu.");
      setSelectedFilm(selectFilm());
      while (getSelectedFilm() != null) {
        System.out.println(getSelectedFilm().filmInfo()
            + "\n1. Add film to the rent;\n"
            + "2. Back;\n"
            + "3. Back in main menu.");
        switch (inputValidation()) {
          case 1:
            addFilmToRent(getSelectedFilm());
            setSelectedFilm(null);
            break;
          case 2:
            setSelectedFilm(null);
            break;
          case 3:
            inManagerRentMenu = false;
            setComingSoonFilms(false);
            setSelectedFilm(null);
            return;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
    }
  }

  public static void managerSessionsMenu() {
    inManagerSessionsMenu = true;
    setNowShowingFilms(true);
    while (inManagerSessionsMenu) {
      LOGGER.info("User opened sessions menu.");
      setSelectedFilm(selectFilm());
      while (getSelectedFilm() != null) {
        System.out.println(getSelectedFilm().toString()
            + "\n1. Add new session;\n"
            + "2. Back;\n"
            + "3. Back in main menu.");
        switch (inputValidation()) {
          case 1:
            addNewSession(getSelectedFilm());
            setSelectedFilm(null);
            break;
          case 2:
            setSelectedFilm(null);
            break;
          case 3:
            inManagerSessionsMenu = false;
            setNowShowingFilms(false);
            setSelectedFilm(null);
            return;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
    }
  }

  public static void managerUsersMenu() {
    inManagerUsersMenu = true;
    while (inManagerUsersMenu) {
      LOGGER.info("User opened users menu.");
      setSelectedUser(selectUser());
      while (getSelectedUser() != null) {
        System.out.println(getSelectedUser().toString()
            + "\n1. All tickets;\n"
            + "2. Back;\n"
            + "3. Back in main menu.");
        switch (inputValidation()) {
          case 1:
            getTickets(getSelectedUser());
            setSelectedUser(null);
            break;
          case 2:
            setSelectedUser(null);
            break;
          case 3:
            inManagerSessionsMenu = false;
            return;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
    }
  }

  public static void managerMessagesMenu() {
    inManagerMessagesMenu = true;
    while (inManagerMessagesMenu) {
      LOGGER.info("User opened messages menu.");
      setSelectedMessage(selectMessage());
      while (getSelectedMessage() != null) {
        System.out.println(getSelectedMessage().toString()
            + "\n1. Return ticket;\n"
            + "2. Deny a request;\n"
            + "3. Back;\n"
            + "4. Back in main menu.");
        switch (inputValidation()) {
          case 1:
            returnTicketToUser(getTicketById(Integer.parseInt(getSelectedMessage().getVALUE())),
                String.format(
                    "Upon your request, the purchase of a \"%s\" film ticket has been canceled."
                        + "\nMoney has been credited to your balance",
                    getTicketById(Integer.parseInt(getSelectedMessage().getVALUE()))
                        .getFILM_NAME()));
            deleteMessage(getSelectedMessage());
            setSelectedMessage(null);
            getCinemaUser().setTimeInMessagesMenu(LocalDateTime.now());
            updateUserByUsername(getCinemaUser());
            LOGGER.info("User approve ticket returning request.");
            break;
          case 2:
            denyReturnTicketRequest(
                getTicketById(Integer.parseInt(getSelectedMessage().getVALUE())));
            deleteMessage(getSelectedMessage());
            setSelectedMessage(null);
            getCinemaUser().setTimeInMessagesMenu(LocalDateTime.now());
            updateUserByUsername(getCinemaUser());
            LOGGER.info("User rejected ticket returning request.");
            break;
          case 3:
            setSelectedMessage(null);
            getCinemaUser().setTimeInMessagesMenu(LocalDateTime.now());
            updateUserByUsername(getCinemaUser());
            break;
          case 4:
            inManagerMessagesMenu = false;
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

  public static void setInManagerFilmsMenu(boolean inManagerFilmsMenu) {
    ManagerView.inManagerFilmsMenu = inManagerFilmsMenu;
  }

  public static void setInManagerRentMenu(boolean inManagerRentMenu) {
    ManagerView.inManagerRentMenu = inManagerRentMenu;
  }

  public static void setInManagerSessionsMenu(boolean inManagerSessionsMenu) {
    ManagerView.inManagerSessionsMenu = inManagerSessionsMenu;
  }

  public static void setInManagerMessagesMenu(boolean inManagerMessagesMenu) {
    ManagerView.inManagerMessagesMenu = inManagerMessagesMenu;
  }

  public static void setInManagerUsersMenu(boolean inManagerUsersMenu) {
    ManagerView.inManagerUsersMenu = inManagerUsersMenu;
  }
}
