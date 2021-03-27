package com.company.service;

import static com.company.model.message.MessageTheme.RETURN_TICKET;
import static com.company.model.user.UserType.MANAGER;
import static com.company.service.AccountService.WRONG_INPUT;
import static com.company.service.AccountService.getCinemaUser;
import static com.company.service.AccountService.putMoney;
import static com.company.service.DataBaseService.getTicketsByFilm;
import static com.company.service.DataBaseService.getTicketsByUser;
import static com.company.service.DataBaseService.getUserByUserType;
import static com.company.service.DataBaseService.updateTicketById;
import static com.company.service.DataBaseService.updateUserByUsername;
import static com.company.service.FilmService.getChangesCanceled;
import static com.company.service.FilmService.getSelectedFilm;
import static com.company.service.MessagesService.CHARACTERS_IN_MESSAGE;
import static com.company.service.MessagesService.returnTicketToUser;
import static com.company.service.MessagesService.sendMessage;
import static com.company.service.ValidationService.inputValidation;
import static com.company.view.RegularUserView.isShowUpcomingTickets;
import static com.company.view.RegularUserView.setShowUpcomingTickets;

import com.company.model.film.Film;
import com.company.model.ticket.Ticket;
import com.company.model.ticket.TicketComparator;
import com.company.model.user.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

public class TicketsService {

  private static final Logger LOGGER = Logger.getLogger(TicketsService.class);

  private static Ticket selectedTicket;

  public static void buyTicket(Ticket ticket) {
    LOGGER.info("User entered the ticket purchasing menu.");
    while (true) {
      if (getCinemaUser().getMoney() >= ticket.getCOST()) {
        System.out.println("You sure that you want to purchase this ticket?\n"
            + "1. Yes;\n"
            + "2. No.");
        switch (inputValidation()) {
          case 1:
            LOGGER.info(String.format("User bought a ticket for \"%s\".",
                ticket.getFILM_NAME()));
            getCinemaUser().setMoney(getCinemaUser().getMoney() - ticket.getCOST());
            ticket.setUser(getCinemaUser());
            ticket.setPurchased(true);
            ticket.setPurchasingTime(LocalDateTime.now());
            updateTicketById(ticket);
            updateUserByUsername(getCinemaUser());
            System.out.println("Congratulations! Ticket purchased successfully!");
            return;
          case 2:
            LOGGER.info("User canceled purchasing.");
            return;
          default:
            System.out.println(WRONG_INPUT);
        }
      } else {
        System.out.println(
            "There is not enough money in your account. Do you want to top up your balance?\n"
                + "1. Yes;\n"
                + "2. No.");
        switch (inputValidation()) {
          case 1:
            putMoney();
            break;
          case 2:
            LOGGER.info("User canceled purchasing (not enough money).");
            return;
          default:
            System.out.println(WRONG_INPUT);
        }
      }
    }
  }

  public static int getAvailableTickets(Film film) {
    int availableTickets = 0;
    for (Ticket ticket : getTicketsByFilm(film)) {
      if (!ticket.isPurchased()) {
        availableTickets++;
      }
    }
    return availableTickets;
  }

  public static void getFilmTickets() {
    int index;
    int positionInList;
    List<Ticket> availableTickets;
    if (getCinemaUser().getAge() < getSelectedFilm().getAgeRestrictions()) {
      System.out
          .printf("You cannot buy tickets for this movie as do not fit the age limit (%d+).\n",
              getSelectedFilm().getAgeRestrictions());
      return;
    }
    if (getAvailableTickets(getSelectedFilm()) == 0) {
      System.out.println("Tickets for this session have expired. "
          + "Choose a different movie or session");
      return;
    }
    while (true) {
      LOGGER.info("User entered the seat selection menu.");
      index = 1;
      availableTickets = getTicketsByFilm(getSelectedFilm()).stream().filter(n -> !n.isPurchased())
          .collect(Collectors.toList());
      System.out.println("Choose the seat you like:");
      for (Ticket ticket : availableTickets) {
        System.out.printf("%d. Seat №%s;\n", index, ticket.getSEAT_NUMBER());
        index++;
      }
      System.out.printf("%d. Back.\n", index);
      positionInList = inputValidation();
      index = 1;
      if (positionInList > 0 && positionInList <= availableTickets.size()) {
        for (Ticket ticket : availableTickets) {
          if (positionInList == index) {
            selectedTicket = ticket;
            LOGGER.info(String.format("User selected seat №%d.", positionInList));
            buyTicket(selectedTicket);
            return;
          }
          index++;
        }
      } else if (positionInList == availableTickets.size() + 1) {
        LOGGER.info("User canceled seat selection.");
        return;
      } else {
        System.out.println(WRONG_INPUT);
      }
    }
  }

  public static void getTickets(User user) {
    int index;
    int positionInList;
    List<Ticket> ticketsList;
    while (true) {
      if (getTicketsByUser(user.getUsername()).size() == 0) {
        switch (getCinemaUser().getUSER_TYPE()) {
          case REGULAR:
            System.out.println("At the moment you have no purchased tickets.");
            setShowUpcomingTickets(false);
            return;
          case MANAGER:
          case ADMIN:
            System.out.println("At the moment selected user have no purchased tickets.");
            setShowUpcomingTickets(false);
            return;
        }
      }
      if (isShowUpcomingTickets()) {
        ticketsList = getTicketsByUser(user.getUsername()).stream().
            filter(n -> n.getSESSION_DATE().isAfter(LocalDateTime.now())).
            collect(Collectors.toList());
      } else {
        ticketsList = getTicketsByUser(user.getUsername());
      }
      ticketsList.sort(new TicketComparator());
      index = 1;
      for (Ticket ticket : ticketsList) {
        System.out.printf("%d. %s;\n", index, ticket.toString());
        index++;
      }
      System.out.printf("%d. Back.\n", index);
      positionInList = inputValidation();
      if (positionInList > 0 && positionInList <= ticketsList.size()) {
        selectedTicket = ticketsList.get(positionInList - 1);
        LOGGER.info("User selected ticket.");
        switch (getCinemaUser().getUSER_TYPE()) {
          case REGULAR:
            actionOnMyTicket(selectedTicket);
            break;
          case MANAGER:
            actionOnUsersTicket(selectedTicket);
            break;
        }
      } else if (positionInList == ticketsList.size() + 1) {
        setShowUpcomingTickets(false);
        selectedTicket = null;
        return;
      } else {
        System.out.println(WRONG_INPUT);
      }
    }
  }

  public static void actionOnMyTicket(Ticket ticket) {
    LOGGER.info("User entered ticket actions menu.");
    while (true) {
      System.out.println(ticket.toString());
      if (ticket.isReturnRequest()) {
        System.out.printf("Ticket returning request has been sent to the cinema manager, "
            + "we will answer you shortly.\n"
            + "For more information, you can contact our manager by e-mail %s\n"
            + "1. Back.\n", getUserByUserType(MANAGER).getEmail());
        if (inputValidation() == 1) {
          selectedTicket = null;
          return;
        } else {
          System.out.println(WRONG_INPUT);
        }
      } else if (ticket.getSESSION_DATE().isBefore(LocalDateTime.now())) {
        System.out.println(
            "1. Delete ticket from list;\n"
                + "2. Back.");
        switch (inputValidation()) {
          case 1:
            LOGGER.info("User deleted ticket from list (film session has passed).");
            selectedTicket.setPurchased(false);
            selectedTicket.setUser(null);
            updateTicketById(ticket);
            System.out.println("Ticket successfully deleted!");
            selectedTicket = null;
            return;
          case 2:
            selectedTicket = null;
            LOGGER.info("User canceled changes.");
            return;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      } else {
        System.out.printf(
            "1. %s;\n"
                + "2. Back.\n", RETURN_TICKET.getDescription());
        switch (inputValidation()) {
          case 1:
            returnTicketRequest(ticket);
            selectedTicket = null;
            return;
          case 2:
            selectedTicket = null;
            LOGGER.info("User canceled changes.");
            return;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
    }
  }

  public static void actionOnUsersTicket(Ticket ticket) {
    LOGGER.info("User entered ticket actions menu.");
    String reason;
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println(ticket.toString());
      if (ticket.getSESSION_DATE().isAfter(LocalDateTime.now())) {
        System.out.printf(
            "1. %s;\n"
                + "2. Back.\n", RETURN_TICKET.getDescription());
        switch (inputValidation()) {
          case 1:
            if (ticket.isReturnRequest()) {
              returnTicketToUser(ticket, "User's request");
            } else {
              do {
                System.out.println("Describe the reason for returning the ticket:");
                reason = scanner.nextLine();
                if (reason.length() > CHARACTERS_IN_MESSAGE) {
                  System.out.printf("Message is too long, it can be up to %d characters\n!",
                      CHARACTERS_IN_MESSAGE);
                }
              } while (reason.length() > CHARACTERS_IN_MESSAGE);
              System.out.printf(
                  "Are you sure you want to return the ticket to the \"%s\" of the user %s %s?\n"
                      + "1. Yes;\n"
                      + "2. No (changes will be canceled).\n",
                  ticket.getFILM_NAME(),
                  ticket.getUser().getFirstname(),
                  ticket.getUser().getLastname());
              switch (inputValidation()) {
                case 1:
                  LOGGER.info("User returned chosen ticket.");
                  returnTicketToUser(ticket, reason);
                  selectedTicket = null;
                  return;
                case 2:
                  System.out.println(getChangesCanceled());
                  selectedTicket = null;
                  LOGGER.info("User canceled changes.");
                  return;
                default:
                  System.out.println(WRONG_INPUT);
                  break;
              }
            }
            selectedTicket = null;
            return;
          case 2:
            selectedTicket = null;
            LOGGER.info("User canceled changes.");
            return;
          default:
            System.out.println(WRONG_INPUT);
            break;
        }
      }
    }
  }

  public static void returnTicketRequest(Ticket ticket) {
    LOGGER.info("User entered ticket returning menu.");
    String reason;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("Describe the reason for returning the ticket:");
      reason = scanner.nextLine();
      if (reason.length() > CHARACTERS_IN_MESSAGE) {
        System.out.printf("Message is too long, it can be up to %d characters\n!",
            CHARACTERS_IN_MESSAGE);
      }
    } while (reason.length() > CHARACTERS_IN_MESSAGE);
    System.out.printf(
        "Are you sure you want to return your \"%s\" ticket?\n"
            + "1. Yes;\n"
            + "2. No (changes will be canceled).\n", ticket.getFILM_NAME());
    switch (inputValidation()) {
      case 1:
        LOGGER.info("User sent ticket returning request to cinema manager.");
        sendMessage(getUserByUserType(MANAGER), LocalDateTime.now(),
            RETURN_TICKET, reason, String.valueOf(ticket.getId()));
        System.out.printf("Ticket returning request has been sent to the cinema manager. "
                + "We will answer you shortly.\n"
                + "For more information, you can contact our manager by e-mail %s\n",
            getUserByUserType(MANAGER).getEmail());
        ticket.setReturnRequest(true);
        updateTicketById(ticket);
        return;
      case 2:
        System.out.println(getChangesCanceled());
        return;
      default:
        System.out.println(WRONG_INPUT);
        break;
    }
  }
}
