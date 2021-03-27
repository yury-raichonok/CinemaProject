package com.company.model.ticket;

import com.company.model.user.User;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ticket implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String SESSION_DATE_PAT = "dd.MM.yyyy HH:mm";
  private static final String PURCHASING_DATE_PAT = "dd.MM.yyyy HH:mm";
  private static final DateTimeFormatter SESSION_DATE_FORM = DateTimeFormatter
      .ofPattern(SESSION_DATE_PAT);
  private static final DateTimeFormatter PURCHASING_DATE_FORM = DateTimeFormatter
      .ofPattern(PURCHASING_DATE_PAT);

  private final String FILM_NAME;
  private final int SEAT_NUMBER;
  private final int COST;
  private final LocalDateTime SESSION_DATE;

  private int id;
  private User user;
  private boolean isPurchased;
  private boolean returnRequest;
  private LocalDateTime purchasingTime;

  public Ticket(String filmName, int seatNumber, int cost, LocalDateTime sessionDate) {
    this.FILM_NAME = filmName;
    this.SEAT_NUMBER = seatNumber;
    this.COST = cost;
    this.isPurchased = false;
    this.returnRequest = false;
    this.SESSION_DATE = sessionDate;
  }

  public Ticket(int id, String filmName, int seatNumber, int cost, LocalDateTime sessionDate) {
    this(filmName, seatNumber, cost, sessionDate);
    this.id = id;
  }

  public Ticket(int id, String filmName, int seatNumber, int cost, User user, boolean isPurchased,
      boolean returnRequest, LocalDateTime purchasingTime, LocalDateTime sessionDate) {
    this(id, filmName, seatNumber, cost, sessionDate);
    this.user = user;
    this.isPurchased = isPurchased;
    this.returnRequest = returnRequest;
    this.purchasingTime = purchasingTime;
  }

  public static DateTimeFormatter getSessionDateForm() {
    return SESSION_DATE_FORM;
  }

  public static DateTimeFormatter getPurchasingDateForm() {
    return PURCHASING_DATE_FORM;
  }

  @Override
  public String toString() {
    if (returnRequest) {
      return FILM_NAME +
          " (" + getFormattedSSessionDate() +
          "), seat - " + SEAT_NUMBER +
          ", cost - " + COST + " BYN. (RETURN REQUESTED)";
    } else {
      return FILM_NAME +
          " (" + getFormattedSSessionDate() +
          "), seat - " + SEAT_NUMBER +
          ", cost - " + COST + " BYN.";
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ticket ticket = (Ticket) o;
    return id == ticket.id && SEAT_NUMBER == ticket.SEAT_NUMBER && COST == ticket.COST
        && isPurchased == ticket.isPurchased && returnRequest == ticket.returnRequest
        && Objects.equals(SESSION_DATE, ticket.SESSION_DATE) && Objects
        .equals(FILM_NAME, ticket.FILM_NAME) && Objects.equals(user, ticket.user)
        && Objects.equals(purchasingTime, ticket.purchasingTime);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(SESSION_DATE, id, FILM_NAME, SEAT_NUMBER, COST, isPurchased, returnRequest,
            purchasingTime);
  }

  public LocalDateTime getSESSION_DATE() {
    return SESSION_DATE;
  }

  public String getFormattedSSessionDate() {
    return SESSION_DATE.format(SESSION_DATE_FORM);
  }

  public int getId() {
    return id;
  }

  public String getFILM_NAME() {
    return FILM_NAME;
  }

  public int getSEAT_NUMBER() {
    return SEAT_NUMBER;
  }

  public int getCOST() {
    return COST;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isPurchased() {
    return isPurchased;
  }

  public void setPurchased(boolean purchased) {
    isPurchased = purchased;
  }

  public boolean isReturnRequest() {
    return returnRequest;
  }

  public void setReturnRequest(boolean returnRequest) {
    this.returnRequest = returnRequest;
  }

  public void setPurchasingTime(LocalDateTime purchasingTime) {
    this.purchasingTime = purchasingTime;
  }

  public String getFormattedPurchasingTime() {
    return purchasingTime.format(PURCHASING_DATE_FORM);
  }
}
