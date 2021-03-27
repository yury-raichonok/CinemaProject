package com.company.service;

import static com.company.model.message.MessageTheme.CHANGE_BIRTHDAY;
import static com.company.model.message.MessageTheme.CHANGE_FIRSTNAME;
import static com.company.model.message.MessageTheme.CHANGE_LASTNAME;
import static com.company.model.message.MessageTheme.DELETE_ACCOUNT;
import static com.company.model.message.MessageTheme.RETURN_TICKET;
import static com.company.service.AccountService.WRONG_INPUT;
import static com.company.service.AccountService.getCinemaUser;
import static com.company.service.DataBaseService.addMessage;
import static com.company.service.DataBaseService.deleteUser;
import static com.company.service.DataBaseService.getMessagesFromDB;
import static com.company.service.DataBaseService.returnTicketById;
import static com.company.service.DataBaseService.updateTicketById;
import static com.company.service.DataBaseService.updateUserByUsername;
import static com.company.service.ValidationService.inputValidation;
import static com.company.view.AdminView.setInAdminMessagesMenu;
import static com.company.view.ManagerView.setInManagerMessagesMenu;
import static com.company.view.RegularUserView.setInUserMessagesMenu;

import com.company.model.message.Message;
import com.company.model.message.MessageComparator;
import com.company.model.message.MessageTheme;
import com.company.model.ticket.Ticket;
import com.company.model.user.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class MessagesService {

  public static final int CHARACTERS_IN_MESSAGE = 500;
  private static final Logger LOGGER = Logger.getLogger(MessagesService.class);

  private static Message selectedMessage;

  public static Message selectMessage() {
    int positionInList;
    List<Message> messages = getMessagesFromDB(getCinemaUser().getUsername());
    if (messages.isEmpty()) {
      System.out.println("At the moment you have no messages.");
      setInAdminMessagesMenu(false);
      setInManagerMessagesMenu(false);
      setInUserMessagesMenu(false);
      return null;
    }
    messages.sort(new MessageComparator());
    System.out.println("Select message:");
    printMessagesList(messages);
    System.out.printf("%d. Back.\n", messages.size() + 1);
    while (true) {
      positionInList = inputValidation();
      if (positionInList > 0 && positionInList <= messages.size()) {
        LOGGER.info("User chose message.");
        return messages.get(positionInList - 1);
      } else if (positionInList == messages.size() + 1) {
        setInAdminMessagesMenu(false);
        setInManagerMessagesMenu(false);
        setInUserMessagesMenu(false);
        return null;
      } else {
        System.out.println(WRONG_INPUT);
      }
    }
  }

  public static void printMessagesList(List<Message> messages) {
    int index = 1;
    for (Message message : messages) {
      if (getCinemaUser().getTimeInMessagesMenu().isBefore(message.getDATE_OF_SENDING())) {
        System.out.printf("%d. NEW (%s) %s %s (%s) - %s;\n", index,
            message.getFormattedDateOfSending(), message.getSENDER().getFirstname(),
            message.getSENDER().getLastname(), message.getSENDER().getUsername(),
            message.getTHEME().getDescription());
      } else {
        System.out.printf("%d. (%s) %s %s (%s) - %s;\n", index, message.getFormattedDateOfSending(),
            message.getSENDER().getFirstname(), message.getSENDER().getLastname(),
            message.getSENDER().getUsername(), message.getTHEME().getDescription());
      }
      index++;
    }
  }

  public static void sendMessage(User receiver, LocalDateTime dateTime, MessageTheme theme,
      String message, String value) {
    addMessage(new Message(getCinemaUser(), receiver, dateTime, theme, message, value));
    LOGGER.info(String.format("User sent message to %s %s (%s).",
        receiver.getFirstname(), receiver.getLastname(), receiver.getUsername()));
  }

  public static void returnTicketToUser(Ticket ticket, String reason) {
    sendMessage(ticket.getUser(), LocalDateTime.now(), RETURN_TICKET,
        String.format("%s - %s.", ticket.toString(), reason), String.valueOf(ticket.getId()));
    ticket.getUser().setMoney(ticket.getUser().getMoney() + ticket.getCOST());
    updateUserByUsername(ticket.getUser());
    ticket.setPurchased(false);
    ticket.setReturnRequest(false);
    ticket.setUser(null);
    returnTicketById(ticket);
    System.out.println("Ticket returned successfully!");
  }

  public static void denyReturnTicketRequest(Ticket ticket) {
    String reason;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("Briefly describe the reason for request denying:");
      reason = scanner.nextLine();
      if (reason.length() > CHARACTERS_IN_MESSAGE) {
        System.out.printf("Message is too long, it can be up to %d characters\n!",
            CHARACTERS_IN_MESSAGE);
      }
    } while (reason.length() > CHARACTERS_IN_MESSAGE);
    sendMessage(ticket.getUser(), LocalDateTime.now(), RETURN_TICKET,
        ticket.toString() + " - " + reason, String.valueOf(ticket.getId()));
    ticket.setReturnRequest(false);
    updateTicketById(ticket);
    System.out.println("Ticket returning request has been rejected!");
  }

  public static void firstnameChangingReq(User user, String firstname, String reason) {
    sendMessage(user, LocalDateTime.now(), CHANGE_FIRSTNAME, reason, firstname);
    user.setFirstname(firstname);
    updateUserByUsername(user);
    System.out.println("First name has been successfully changed!");
  }

  public static void denyFirstnameChangingReq(User user, String firstname, String reason) {
    sendMessage(user, LocalDateTime.now(), CHANGE_FIRSTNAME, reason, firstname);
    System.out.println("First name changing request has been rejected!");
  }

  public static void lastnameChangingReq(User user, String lastname, String reason) {
    sendMessage(user, LocalDateTime.now(), CHANGE_LASTNAME, reason, lastname);
    user.setLastname(lastname);
    updateUserByUsername(user);
    System.out.println("Last name has been successfully changed!");
  }

  public static void denyLastnameChangingReq(User user, String lastname, String reason) {
    sendMessage(user, LocalDateTime.now(), CHANGE_LASTNAME, reason, lastname);
    System.out.println("Last name changing request has been rejected!");
  }

  public static void birthdayChangingReq(User user, String date, String reason) {
    sendMessage(user, LocalDateTime.now(), CHANGE_BIRTHDAY, reason, date);
    user.setBirthday(date);
    updateUserByUsername(user);
    System.out.println("Date of birth has been successfully changed!");
  }

  public static void denyBirthdayChangingReq(User user, String date, String reason) {
    sendMessage(user, LocalDateTime.now(), CHANGE_BIRTHDAY, reason, date);
    System.out.println("Date of birth changing request has been rejected!");
  }

  public static void accountRemovingReq(User user) {
    deleteUser(user);
    System.out.println("Account has been successfully removed!");
  }

  public static void denyAccountRemovingReq(User user) {
    sendMessage(user, LocalDateTime.now(), DELETE_ACCOUNT, "User's request", "");
    System.out.println("Account has been successfully removed!");
  }

  public static Message getSelectedMessage() {
    return selectedMessage;
  }

  public static void setSelectedMessage(Message selectedMessage) {
    MessagesService.selectedMessage = selectedMessage;
  }
}