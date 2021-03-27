package com.company.service;

import static com.company.model.film.Film.addTicketsToRent;
import static com.company.model.film.Film.getReleaseDateForm;
import static com.company.model.ticket.Ticket.getSessionDateForm;
import static com.company.service.AccountService.WRONG_INPUT;
import static com.company.service.DataBaseService.addNewFilmSessionToDB;
import static com.company.service.DataBaseService.addNewFilmToDB;
import static com.company.service.DataBaseService.deleteFilmById;
import static com.company.service.DataBaseService.deleteFilmByName;
import static com.company.service.DataBaseService.deleteTicketsByFilm;
import static com.company.service.DataBaseService.deleteTicketsBySession;
import static com.company.service.DataBaseService.getAllFilmsFromDB;
import static com.company.service.DataBaseService.updateAllFilmsWithName;
import static com.company.service.DataBaseService.updateFilmById;
import static com.company.service.DataBaseService.updateTicketFilmName;
import static com.company.service.DataBaseService.updateTicketSessionDate;
import static com.company.service.DataBaseService.updateTicketsCost;
import static com.company.service.ValidationService.inputValidation;
import static com.company.service.ValidationService.releaseDateValidation;
import static com.company.service.ValidationService.sessionDateValidation;
import static com.company.view.AdminView.setInAdminFilmsMenu;
import static com.company.view.MainView.isAllFilms;
import static com.company.view.MainView.isComingSoonFilms;
import static com.company.view.MainView.isNowShowingFilms;
import static com.company.view.MainView.setAllFilms;
import static com.company.view.MainView.setComingSoonFilms;
import static com.company.view.MainView.setNowShowingFilms;
import static com.company.view.ManagerView.setInManagerFilmsMenu;
import static com.company.view.ManagerView.setInManagerRentMenu;
import static com.company.view.ManagerView.setInManagerSessionsMenu;

import com.company.model.film.Film;
import com.company.model.film.FilmComparator;
import com.company.model.film.FilmGenre;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

public class FilmService {

  private static final String CHANGES_CANCELED = "Changes canceled.";
  private static final int CHARACTERS_IN_FILM_NAME = 100;
  private static final int CHARACTERS_IN_PRODUCER_NAME = 100;
  private static final Logger LOGGER = Logger.getLogger(FilmService.class);
  private static Film selectedFilm;

  public static void createFilm() {
    LOGGER.info("User entered the film creation menu.");
    Film film;
    String name;
    String producer;
    int ageRestrictions;
    LocalDate releaseDate;
    String dateOfRelease;
    Duration filmDuration;
    Scanner scanner = new Scanner(System.in);
    Set<FilmGenre> filmGenres = new TreeSet<>();
    boolean selectGenre = true;
    boolean addFilm;
    while (true) {
      do {
        System.out.println("Enter the name of the film:");
        name = scanner.nextLine();
        if (name.length() > CHARACTERS_IN_FILM_NAME) {
          System.out.printf("Film name can be up to %d characters long\n!",
              CHARACTERS_IN_FILM_NAME);
        }
      } while (name.length() > CHARACTERS_IN_FILM_NAME);
      do {
        System.out.println("Enter the producer of the film:");
        producer = scanner.nextLine();
        if (producer.length() > CHARACTERS_IN_PRODUCER_NAME) {
          System.out.printf("Film name can be up to %d characters long\n!",
              CHARACTERS_IN_FILM_NAME);
        }
      } while (producer.length() > CHARACTERS_IN_PRODUCER_NAME);
      System.out.println("Enter film's age restrictions:");
      ageRestrictions = inputValidation();
      filmGenres.add(selectFilmGenre());
      while (selectGenre) {
        System.out.println("Do you want to add another genre?\n"
            + "1. Yes;\n"
            + "2. No.");
        switch (inputValidation()) {
          case 1:
            filmGenres.add(selectFilmGenre());
            break;
          case 2:
            selectGenre = false;
            break;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
      do {
        System.out.println("Enter date of release (example - \"01.01.2000\"):");
        dateOfRelease = scanner.nextLine();
      } while (!releaseDateValidation(dateOfRelease));
      releaseDate = LocalDate.parse(dateOfRelease, getReleaseDateForm());
      System.out.println("Enter the duration of the film in minutes:");
      filmDuration = Duration.ofMinutes(inputValidation());
      film = new Film(name, producer, filmGenres, ageRestrictions, filmDuration, releaseDate);
      selectedFilm = film;
      addNewFilmToDB(film);
      addFilm = true;
      while (addFilm) {
        System.out.println(selectedFilm.filmInfo()
            + "\nYou want to add this film to the cinema?\n"
            + "1. Yes, add film to the rent;\n"
            + "2. Yes, add film to poster;\n"
            + "3. No (exit in main menu);\n"
            + "4. Create film from the beginning (this information will be deleted).");
        switch (inputValidation()) {
          case 1:
            LOGGER.info(String.format("User added new film \"%s\".", selectedFilm.getName()));
            addFilmToRent(selectedFilm);
            selectedFilm = null;
            return;
          case 2:
            System.out.printf("\"%s\" is successfully added to poster!\n",
                selectedFilm.getName());
            LOGGER.info(String.format("User added new film \"%s\" to poster.",
                selectedFilm.getName()));
            selectedFilm = null;
            return;
          case 3:
            System.out.println(CHANGES_CANCELED);
            selectedFilm = null;
            LOGGER.info("User canceled changes.");
            return;
          case 4:
            selectedFilm = null;
            addFilm = false;
            break;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
    }
  }

  public static void addFilmToRent(Film film) {
    LOGGER.info("User entered the menu for adding a film to rent.");
    if (film.getId() == 0) {
      for (Film f : getAllFilmsFromDB()) {
        if (f.getName().equals(film.getName())) {
          selectedFilm = f;
        }
      }
    }
    LocalDateTime sessionDate;
    String dateOfSession;
    int ticketCost;
    int ticketsAmount;
    boolean addFilmToRent;
    Scanner scanner = new Scanner(System.in);
    while (true) {
      do {
        System.out.println("Enter session date (example - \"01.01.2000 12:00\"):");
        dateOfSession = scanner.nextLine();
      } while (!sessionDateValidation(dateOfSession));
      sessionDate = LocalDateTime.parse(dateOfSession, getSessionDateForm());
      System.out.println("Enter cost of ticket:");
      ticketCost = inputValidation();
      System.out.println("Enter the amount of tickets:");
      ticketsAmount = inputValidation();
      addFilmToRent = true;
      while (addFilmToRent) {
        System.out.printf("Name: %s,\n"
                + "Producer: %s,\n"
                + "Year of issue: %d,\n"
                + "Genre: %s\n"
                + "Duration: %d min,\n"
                + "Age restrictions: %d+,\n"
                + "Ticket cost: %d BYN.\n"
                + "Session date: %s,\n"
                + "You want to add this film to the rent?\n"
                + "1. Yes (tickets will be available for sale);\n"
                + "2. No (exit in main menu);\n"
                + "3. Create session from the beginning (this information will be deleted).\n",
            film.getName(), film.getProducer(), film.getReleaseDate().getYear(),
            film.printGenres(), film.getFilmDuration().toMinutes(), film.getAgeRestrictions(),
            ticketCost, dateOfSession);
        switch (inputValidation()) {
          case 1:
            LOGGER.info(String.format("User added new film \"%s\" to rent.",
                film.getName()));
            selectedFilm.setInRent(true);
            selectedFilm.setSessionDate(sessionDate);
            selectedFilm.setTicketCost(ticketCost);
            selectedFilm.setTicketsAmount(ticketsAmount);
            addTicketsToRent(selectedFilm.getName(), ticketCost, ticketsAmount, sessionDate);
            updateFilmById(selectedFilm);
            System.out.printf("\"%s\" is successfully added to rent!\n", selectedFilm.getName());
            while (true) {
              System.out.printf("You want to add another session of \"%s\"?\n"
                  + "1. Yes;\n"
                  + "2. No.\n", selectedFilm.getName());
              switch (inputValidation()) {
                case 1:
                  addNewSession(selectedFilm);
                  return;
                case 2:
                  System.out.println(CHANGES_CANCELED);
                  return;
                default:
                  System.out.println(WRONG_INPUT);
              }
            }
          case 2:
            LOGGER.info("User canceled changes.");
            System.out.println(getChangesCanceled());
            return;
          case 3:
            addFilmToRent = false;
            break;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
    }
  }

  public static void addNewSession(Film film) {
    LocalDateTime sessionDate;
    String dateOfSession;
    int ticketCost;
    int ticketsAmount;
    boolean addNewSession;
    boolean addAnotherSession = false;
    Scanner scanner = new Scanner(System.in);
    while (true) {
      LOGGER.info("User entered the menu for adding a new film session.");
      do {
        System.out.println("Enter new session date (example - \"01.01.2000 12:00\"):");
        dateOfSession = scanner.nextLine();
      } while (!sessionDateValidation(dateOfSession));
      sessionDate = LocalDateTime.parse(dateOfSession, getSessionDateForm());
      System.out.println("Enter cost of ticket on this session:");
      ticketCost = inputValidation();
      System.out.println("Enter the amount of tickets:");
      ticketsAmount = inputValidation();
      addNewSession = true;
      while (addNewSession) {
        System.out.printf("Name: %s,\n"
                + "Producer: %s,\n"
                + "Year of issue: %d,\n"
                + "Genre: %s\n"
                + "Duration: %d min,\n"
                + "Age restrictions: %d+,\n"
                + "Ticket cost: %d BYN.\n"
                + "Session date: %s,\n"
                + "You want to add this session?\n"
                + "1. Yes (tickets will be available for sale);\n"
                + "2. No (exit in main menu);\n"
                + "3. Create session from the beginning (this information will be deleted).\n",
            film.getName(), film.getProducer(), film.getReleaseDate().getYear(),
            film.printGenres(), film.getFilmDuration().toMinutes(), film.getAgeRestrictions(),
            ticketCost, dateOfSession);
        switch (inputValidation()) {
          case 1:
            addNewFilmSessionToDB(new Film(film.getName(), film.getProducer(), film.getFilmGenres(),
                film.getAgeRestrictions(), ticketCost, ticketsAmount, film.getFilmDuration(),
                film.getReleaseDate(), sessionDate));
            addTicketsToRent(film.getName(), ticketCost, ticketsAmount, sessionDate);
            LOGGER.info(String.format("User added a new session of \"%s\".", film.getName()));
            System.out.printf("New session of \"%s\" is successfully added in cinema!\n",
                film.getName());
            addNewSession = false;
            addAnotherSession = true;
            break;
          case 2:
            System.out.println(CHANGES_CANCELED);
            LOGGER.info("User canceled changes.");
            return;
          case 3:
            addNewSession = false;
            break;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
      while (addAnotherSession) {
        System.out.printf("You want to add another session of \"%s\"?\n"
            + "1. Yes;\n"
            + "2. No.\n", film.getName());
        switch (inputValidation()) {
          case 1:
            addAnotherSession = false;
            break;
          case 2:
            return;
          default:
            System.out.println(WRONG_INPUT);
        }
      }
    }
  }

  public static void editFilmSession(Film film) {
    LOGGER.info("User entered the film session edition menu.");
    while (true) {
      System.out.println("Select action:\n"
          + "1. Edit session date,\n"
          + "2. Edit session ticket cost,\n"
          + "3. Back.");
      switch (inputValidation()) {
        case 1:
          editSessionDate(film);
          return;
        case 2:
          editSessionTicketCost(film);
          return;
        case 3:
          System.out.println(CHANGES_CANCELED);
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void editSessionDate(Film film) {
    LOGGER.info("User entered the session date edition menu.");
    String dateOfSession;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("Enter new session date (example - \"01.01.2000 12:00\"):");
      dateOfSession = scanner.nextLine();
    } while (!sessionDateValidation(dateOfSession));
    while (true) {
      System.out.printf(
          "Are you sure you want to change session date from \"%s\" to \"%s\"?\n"
              + "1. Yes;\n"
              + "2. No (changes will be canceled).\n",
          film.getFormattedSessionsDate(), dateOfSession);
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User changed session date from \"%s\" to \"%s\".",
              film.getFormattedSessionsDate(), dateOfSession));
          film.setSessionDate(LocalDateTime.parse(dateOfSession, getSessionDateForm()));
          updateFilmById(film);
          updateTicketSessionDate(film, dateOfSession);
          System.out.println("Session date was changed successfully!");
          return;
        case 2:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void editSessionTicketCost(Film film) {
    LOGGER.info("User entered the session ticket cost edition menu.");
    System.out.println("Enter cost of the ticket on this session:");
    int ticketCost = inputValidation();
    while (true) {
      System.out.printf(
          "Are you sure you want to change cost of the ticket on this session from \"%s BRL\" to \"%s BRL\"?\n"
              + "1. Yes;\n"
              + "2. No (changes will be canceled).\n", film.getTicketCost(), ticketCost);
      switch (inputValidation()) {
        case 1:
          LOGGER
              .info(String.format("User changed session ticket cost from \"%s BRL\" to \"%s BRL\".",
                  film.getTicketCost(), ticketCost));
          film.setTicketCost(ticketCost);
          updateFilmById(film);
          updateTicketsCost(film);
          System.out.println("Session cost of the ticket was changed successfully!");
          return;
        case 2:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void editFilmName(Film film) {
    LOGGER.info("User entered the film name edition menu.");
    String name;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("Enter new the name of the film:");
      name = scanner.nextLine();
      if (name.length() > CHARACTERS_IN_FILM_NAME) {
        System.out.printf("Film name can be up to %d characters long\n!",
            CHARACTERS_IN_FILM_NAME);
      }
    } while (name.length() > CHARACTERS_IN_FILM_NAME);
    while (true) {
      System.out.printf(
          "Are you sure you want to change the name of the film from \"%s\" to \"%s\"?\n"
              + "1. Yes (name will be changed in all sessions of this film);\n"
              + "2. No (changes will be canceled).\n", film.getName(), name);
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User changed film name from \"%s\" to \"%s\".",
              film.getName(), name));
          updateAllFilmsWithName(film, name);
          updateTicketFilmName(film, name);
          getSelectedFilm().setName(name);
          System.out.println("The name was changed successfully!");
          return;
        case 2:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void editFilmProducer(Film film) {
    LOGGER.info("User entered the film producer edition menu.");
    String producer;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("Enter the producer of the film:");
      producer = scanner.nextLine();
      if (producer.length() > CHARACTERS_IN_PRODUCER_NAME) {
        System.out.printf("Film name can be up to %d characters long\n!",
            CHARACTERS_IN_FILM_NAME);
      }
    } while (producer.length() > CHARACTERS_IN_PRODUCER_NAME);
    while (true) {
      System.out.printf(
          "Are you sure you want to change producer of the film from \"%s\" to \"%s\"?\n"
              + "1. Yes (producer will be changed in all sessions of this film);\n"
              + "2. No (changes will be canceled).\n", film.getProducer(), producer);
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User changed film producer from \"%s\" to \"%s\".",
              film.getProducer(), producer));
          film.setProducer(producer);
          updateAllFilmsWithName(film, film.getName());
          System.out.println("The producer was changed successfully!");
          return;
        case 2:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void editFilmAgeRestrictions(Film film) {
    LOGGER.info("User entered the film age restrictions edition menu.");
    int ageRestrictions;
    System.out.println("Enter new film's age restrictions:");
    ageRestrictions = inputValidation();
    while (true) {
      System.out.printf(
          "Are you sure you want to change film's age restrictions from \"%d\" to \"%d\"?\n"
              + "1. Yes (age restrictions will be changed in all sessions of this film);\n"
              + "2. No (changes will be canceled).\n", film.getAgeRestrictions(), ageRestrictions);
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User changed film age restrictions from \"%s\" to \"%s\".",
              film.getAgeRestrictions(), ageRestrictions));
          film.setAgeRestrictions(ageRestrictions);
          updateAllFilmsWithName(film, film.getName());
          System.out.println("Film's age restrictions was changed successfully!");
          return;
        case 2:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void editFilmDateOfRelease(Film film) {
    LOGGER.info("User entered the film date of release edition menu.");
    LocalDate releaseDate;
    String dateOfRelease;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("Enter new date of release (example - \"01.01.2000\"):");
      dateOfRelease = scanner.nextLine();
    } while (!releaseDateValidation(dateOfRelease));
    releaseDate = LocalDate.parse(dateOfRelease, getReleaseDateForm());
    while (true) {
      System.out.printf(
          "Are you sure you want to change film's date of release from \"%s\" to \"%s\"?\n"
              + "1. Yes (release date will be changed in all sessions of this film);\n"
              + "2. No (changes will be canceled).\n", film.getFormattedReleaseDate(),
          dateOfRelease);
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User changed film date of release from \"%s\" to \"%s\".",
              film.getFormattedReleaseDate(), dateOfRelease));
          film.setReleaseDate(releaseDate);
          updateAllFilmsWithName(film, film.getName());
          System.out.println("Film's date of release was changed successfully!");
          return;
        case 2:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void editFilmDuration(Film film) {
    LOGGER.info("User entered the film duration edition menu.");
    Duration filmDuration;
    System.out.println("Enter the new duration of the film in minutes:");
    filmDuration = Duration.ofMinutes(inputValidation());
    while (true) {
      System.out.printf(
          "Are you sure you want to change duration of the film from \"%s\" to \"%s\"?\n"
              + "1. Yes (film duration will be changed in all sessions of this film);\n"
              + "2. No (changes will be canceled).\n",
          film.getFilmDuration().toMinutes(), filmDuration.toMinutes());
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User changed film duration from \"%s\" to \"%s\".",
              film.getFilmDuration().toMinutes(), filmDuration.toMinutes()));
          film.setFilmDuration(filmDuration);
          updateAllFilmsWithName(film, film.getName());
          System.out.println("The duration of the film was changed successfully!");
          return;
        case 2:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void editFilmGenres(Film film) {
    LOGGER.info("User entered the film genres edition menu.");
    Set<FilmGenre> filmGenres = new TreeSet<>();
    boolean selectGenre = true;
    filmGenres.add(selectFilmGenre());
    while (selectGenre) {
      System.out.println("Do you want to add another genre?\n"
          + "1. Yes;\n"
          + "2. No.");
      switch (inputValidation()) {
        case 1:
          filmGenres.add(selectFilmGenre());
          break;
        case 2:
          selectGenre = false;
          break;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
    while (true) {
      System.out.printf("Are you sure you want to change film genre(s) from:\n%s\nto:\n%s\n"
          + "1. Yes (film genres will be changed in all sessions of this film);\n"
          + "2. No (changes will be canceled).\n", film.printGenres(), printFilmGenres(filmGenres));
      switch (inputValidation()) {
        case 1:
          LOGGER.info("User changed film genres.");
          film.setFilmGenres(filmGenres);
          updateAllFilmsWithName(film, film.getName());
          System.out.println("The film genre(s) was changed successfully!");
          return;
        case 2:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void deleteFilmSession(Film film) {
    LOGGER.info("User entered the film (session) removing menu.");
    while (true) {
      System.out.printf("You want to delete film \"%s\" from cinema or the selected session?\n"
          + "1. Delete film (all sessions of this film will be deleted from data);\n"
          + "2. Delete chosen session;\n"
          + "3. Cancel deletion.\n", film.getName());
      switch (inputValidation()) {
        case 1:
          LOGGER.info(String.format("User removed film \"%s\" from cinema.", film.getName()));
          deleteFilmByName(film);
          deleteTicketsByFilm(film);
          System.out.println("The film was successfully deleted!");
          setSelectedFilm(null);
          return;
        case 2:
          LOGGER.info(String.format("User removed session of \"%s\" from cinema.", film.getName()));
          if (film.isInRent()) {
            deleteFilmById(film);
            deleteTicketsBySession(film);
          } else {
            deleteFilmById(film);
          }
          System.out.println("The session was successfully deleted!");
          setSelectedFilm(null);
        case 3:
          System.out.println(CHANGES_CANCELED);
          LOGGER.info("User canceled changes.");
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static Film selectFilm() {
    int positionInList;
    List<Film> filmsList = new ArrayList<>();
    if (isAllFilms()) {
      filmsList = getAllFilmsFromDB();
    } else if (isNowShowingFilms()) {
      filmsList = getAllFilmsFromDB().stream().
          filter(Film::isInRent).
          filter(film -> film.getSessionDate().isAfter(LocalDateTime.now())).
          collect(Collectors.toList());
    } else if (isComingSoonFilms()) {
      filmsList = getAllFilmsFromDB().stream().
          filter(film -> !film.isInRent()).
          collect(Collectors.toList());
    }
    if (filmsList.isEmpty()) {
      System.out.println("There are no films on your request.");
      setAllFilms(false);
      setNowShowingFilms(false);
      setComingSoonFilms(false);
      setInManagerRentMenu(false);
      setInManagerFilmsMenu(false);
      setInManagerSessionsMenu(false);
      setInAdminFilmsMenu(false);
      return null;
    }
    filmsList.sort(new FilmComparator());
    System.out.println("Select film:");
    printFilmsList(filmsList);
    System.out.printf("%d. Back.\n", filmsList.size() + 1);
    while (true) {
      LOGGER.info("User entered the film selection menu.");
      positionInList = inputValidation();
      if (positionInList > 0 && positionInList <= filmsList.size()) {
        LOGGER.info(String.format("User selected film \"%s\".",
            filmsList.get(positionInList - 1).getName()));
        return filmsList.get(positionInList - 1);
      } else if (positionInList == filmsList.size() + 1) {
        LOGGER.info("User canceled changes.");
        setAllFilms(false);
        setNowShowingFilms(false);
        setComingSoonFilms(false);
        setInManagerFilmsMenu(false);
        setInManagerSessionsMenu(false);
        setInManagerRentMenu(false);
        setInAdminFilmsMenu(false);
        selectedFilm = null;
        return null;
      } else {
        System.out.println(WRONG_INPUT);
      }
    }
  }

  public static FilmGenre selectFilmGenre() {
    int pos;
    int index = 1;
    List<FilmGenre> genresList = Arrays.asList(FilmGenre.values());
    System.out.println("Select film genre:");
    for (FilmGenre filmGenre : genresList) {
      System.out.printf("%d. %s;\n", index, filmGenre.getName());
      index++;
    }
    while (true) {
      pos = inputValidation();
      if (pos > 0 && pos <= genresList.size()) {
        return genresList.get(pos - 1);
      } else {
        System.out.println(WRONG_INPUT);
      }
    }
  }

  public static void printFilmsList(List<Film> films) {
    int index = 1;
    for (Film film : films) {
      film.printShortDescription(index);
      index++;
    }
  }

  public static String printFilmGenres(Set<FilmGenre> filmGenres) {
    StringBuilder genresString = new StringBuilder();
    for (FilmGenre genre : filmGenres) {
      genresString.append(genre.getName()).append(", ");
    }
    return genresString.toString();
  }

  public static Film getSelectedFilm() {
    return selectedFilm;
  }

  public static void setSelectedFilm(Film selectedFilm) {
    FilmService.selectedFilm = selectedFilm;
  }

  public static String getChangesCanceled() {
    return CHANGES_CANCELED;
  }
}
