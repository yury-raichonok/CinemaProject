package com.company.model.message;

import com.company.model.user.User;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String DATE_TIME_PAT = "dd.MM.yyyy, HH.mm.ss";
  private static final DateTimeFormatter MESSAGE_DATE_FORM = DateTimeFormatter
      .ofPattern(DATE_TIME_PAT);

  private final User SENDER;
  private final User RECEIVER;
  private final LocalDateTime DATE_OF_SENDING;
  private final MessageTheme THEME;
  private final String MESSAGE;
  private final String VALUE;

  private int id;

  public Message(User sender, User receiver, LocalDateTime dateOfSending, MessageTheme theme,
      String message, String value) {
    this.SENDER = sender;
    this.RECEIVER = receiver;
    this.DATE_OF_SENDING = dateOfSending;
    this.THEME = theme;
    this.MESSAGE = message;
    this.VALUE = value;
  }

  public Message(int id, User sender, User receiver, LocalDateTime dateOfSending, MessageTheme theme,
      String message, String value) {
    this(sender, receiver, dateOfSending, theme, message, value);
    this.id = id;
  }

  public static DateTimeFormatter getMessageDateForm() {
    return MESSAGE_DATE_FORM;
  }

  @Override
  public String toString() {
    return getFormattedDateOfSending() + ", " + SENDER.getFirstname() + " " + SENDER.getLastname()
        + ", theme - " + THEME.getDescription() + "\n" + MESSAGE;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Message message = (Message) o;
    return id == message.id && Objects.equals(SENDER, message.SENDER) && Objects
        .equals(RECEIVER, message.RECEIVER) && Objects
        .equals(DATE_OF_SENDING, message.DATE_OF_SENDING) && Objects
        .equals(THEME, message.THEME) && Objects.equals(MESSAGE, message.MESSAGE)
        && Objects.equals(VALUE, message.VALUE);
  }

  @Override
  public int hashCode() {
    return Objects.hash(SENDER, RECEIVER, DATE_OF_SENDING, THEME, MESSAGE, VALUE, id);
  }

  public int getId() {
    return id;
  }

  public MessageTheme getTHEME() {
    return THEME;
  }

  public String getMESSAGE() {
    return MESSAGE;
  }

  public User getSENDER() {
    return SENDER;
  }

  public User getRECEIVER() {
    return RECEIVER;
  }

  public LocalDateTime getDATE_OF_SENDING() {
    return DATE_OF_SENDING;
  }

  public String getFormattedDateOfSending() {
    return DATE_OF_SENDING.format(MESSAGE_DATE_FORM);
  }

  public String getVALUE() {
    return VALUE;
  }
}
