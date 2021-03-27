package com.company.model.film;

import static com.company.model.ticket.Ticket.getSessionDateForm;
import static com.company.service.DataBaseService.addTicket;
import static com.company.service.TicketsService.getAvailableTickets;

import com.company.model.ticket.Ticket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Film implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String RELEASE_DATE_PAT = "dd.MM.yyyy";
  private static final DateTimeFormatter RELEASE_DATE_FORM = DateTimeFormatter.
      ofPattern(RELEASE_DATE_PAT);

  boolean isInRent;
  private int id;
  private String name;
  private String producer;
  private Set<FilmGenre> filmGenres;
  private int ageRestrictions;
  private int ticketCost;
  private int ticketsAmount;
  private Duration filmDuration;
  private LocalDate releaseDate;
  private LocalDateTime sessionDate;

  public Film(String name, String producer, Set<FilmGenre> filmGenres, int ageRestrictions,
      Duration filmDuration, LocalDate releaseDate) {
    this.name = name;
    this.producer = producer;
    this.filmGenres = filmGenres;
    this.ageRestrictions = ageRestrictions;
    this.filmDuration = filmDuration;
    this.releaseDate = releaseDate;
    isInRent = false;
  }

  public Film(int id, String name, String producer, Set<FilmGenre> filmGenres, int ageRestrictions,
      Duration filmDuration, LocalDate releaseDate) {
    this(name, producer, filmGenres, ageRestrictions, filmDuration, releaseDate);
    this.id = id;
    isInRent = false;
  }

  public Film(String name, String producer, Set<FilmGenre> filmGenres, int ageRestrictions,
      int ticketCost, int ticketsAmount, Duration filmDuration, LocalDate releaseDate,
      LocalDateTime sessionDate) {
    this(name, producer, filmGenres, ageRestrictions, filmDuration, releaseDate);
    this.ticketCost = ticketCost;
    this.ticketsAmount = ticketsAmount;
    this.sessionDate = sessionDate;
    this.isInRent = true;
  }

  public Film(int id, String name, String producer, Set<FilmGenre> filmGenres, int ageRestrictions,
      int ticketCost, int ticketsAmount, Duration filmDuration, LocalDate releaseDate,
      LocalDateTime sessionDate) {
    this(name, producer, filmGenres, ageRestrictions, ticketCost, ticketsAmount, filmDuration,
        releaseDate, sessionDate);
    this.id = id;
    this.isInRent = true;
  }

  public static String filmGenresToJson(Set<FilmGenre> filmGenres) {
    AtomicReference<StringBuilder> genres = new AtomicReference<>(new StringBuilder());
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    for (FilmGenre filmGenre : filmGenres) {
      genres.get().append(gson.toJson(filmGenre));
    }
    return gson.toJson(filmGenres);
  }

  public static Set<FilmGenre> filmGenresFromJson(String genres) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    return gson.fromJson(genres, new TypeToken<Set<FilmGenre>>() {
    }.getType());
  }

  public static void addTicketsToRent(String name, int ticketCost, int ticketsAmount,
      LocalDateTime sessionDate) {
    for (int i = 1; i <= ticketsAmount; i++) {
      addTicket(new Ticket(name, i, ticketCost, sessionDate));
    }
  }

  public static DateTimeFormatter getReleaseDateForm() {
    return RELEASE_DATE_FORM;
  }

  public void printShortDescription(int index) {
    if (isInRent) {
      if (sessionDate.isBefore(LocalDateTime.now())) {
        System.out.printf(
            "%d. %s (film session has passed, can be deleted) | %s (%s min) %d+, "
                + "%d available ticket(s);\n", index, getFormattedSessionsDate(), name,
            filmDuration.toMinutes(), ageRestrictions, getAvailableTickets(this));
      } else {
        System.out.printf("%d. %s | %s (%s min) %d+, %d available ticket(s);\n",
            index, getFormattedSessionsDate(), name, filmDuration.toMinutes(), ageRestrictions,
            getAvailableTickets(this));
      }
    } else {
      System.out.printf("%d. %s (%s min) %d+, release date - %s;\n",
          index, name, filmDuration.toMinutes(), ageRestrictions, getFormattedReleaseDate());
    }
  }

  public String printGenres() {
    StringBuilder genres = new StringBuilder();
    for (FilmGenre genre : filmGenres) {
      genres.append(genre.getName()).append(", ");
    }
    return genres.toString();
  }

  public String filmInfo() {
    return "Name: " + getName()
        + ",\nProducer: " + getProducer()
        + ",\nYear of issue: " + getReleaseDate().getYear()
        + ",\nGenre: " + this.printGenres()
        + "\nDuration: " + getFilmDuration().toMinutes() + " min"
        + ",\nAge restrictions: " + getAgeRestrictions() + "+.";
  }

  @Override
  public String toString() {
    return "Name: " + getName()
        + ",\nProducer: " + getProducer()
        + ",\nYear of issue: " + getReleaseDate().getYear()
        + ",\nGenre: " + this.printGenres()
        + "\nDuration: " + getFilmDuration().toMinutes() + " min"
        + ",\nAge restrictions: " + getAgeRestrictions() + "+"
        + ",\nTicket cost: " + getTicketCost() + " BYN.";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Film film = (Film) o;
    return id == film.id && ageRestrictions == film.ageRestrictions && ticketCost == film.ticketCost
        && ticketsAmount == film.ticketsAmount && isInRent == film.isInRent && Objects
        .equals(name, film.name) && Objects.equals(producer, film.producer)
        && Objects.equals(filmGenres, film.filmGenres) && Objects
        .equals(filmDuration, film.filmDuration) && Objects
        .equals(releaseDate, film.releaseDate) && Objects
        .equals(sessionDate, film.sessionDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, producer, filmGenres, ageRestrictions, ticketCost, ticketsAmount,
        filmDuration, releaseDate, sessionDate, isInRent);
  }

  public int getId() {
    return id;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getFormattedReleaseDate() {
    return releaseDate.format(RELEASE_DATE_FORM);
  }

  public Duration getFilmDuration() {
    return filmDuration;
  }

  public void setFilmDuration(Duration filmDuration) {
    this.filmDuration = filmDuration;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public int getAgeRestrictions() {
    return ageRestrictions;
  }

  public void setAgeRestrictions(int ageRestrictions) {
    this.ageRestrictions = ageRestrictions;
  }

  public Set<FilmGenre> getFilmGenres() {
    return filmGenres;
  }

  public void setFilmGenres(Set<FilmGenre> filmGenres) {
    this.filmGenres = filmGenres;
  }

  public LocalDateTime getSessionDate() {
    return sessionDate;
  }

  public void setSessionDate(LocalDateTime sessionDate) {
    this.sessionDate = sessionDate;
  }

  public String getFormattedSessionsDate() {
    return sessionDate.format(getSessionDateForm());
  }

  public int getTicketCost() {
    return ticketCost;
  }

  public void setTicketCost(int ticketCost) {
    this.ticketCost = ticketCost;
  }

  public boolean isInRent() {
    return isInRent;
  }

  public void setInRent(boolean inRent) {
    isInRent = inRent;
  }

  public int getTicketsAmount() {
    return ticketsAmount;
  }

  public void setTicketsAmount(int ticketsAmount) {
    this.ticketsAmount = ticketsAmount;
  }

}
