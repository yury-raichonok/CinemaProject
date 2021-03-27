package com.company.model.film;

import java.io.Serializable;

public enum FilmGenre implements Serializable {

  ABSURDIST("Absurdist"),
  ACTION("Action"),
  ADVENTURE("Adventure"),
  ANIMATION("Animation"),
  COMEDY("Comedy"),
  CRIME("Crime"),
  DRAMA("Drama"),
  FAMILY("Family"),
  FANTASY("Fantasy"),
  HISTORICAL("Historical"),
  HISTORICAL_FICTION("Historical fiction"),
  HORROR("Horror"),
  MAGICAL_REALISM("Magical realism"),
  MYSTERY("Mystery"),
  PARANOID_FICTION("Paranoid fiction"),
  PHILOSOPHICAL("Philosophical"),
  POLITICAL("Political"),
  ROMANCE("Romance"),
  SAGA("Saga"),
  SATIRE("Satire"),
  SCIENCE_FICTION("Science fiction"),
  SOCIAL("Social"),
  SPECULATIVE("Speculative"),
  THRILLER("Thriller"),
  URBAN("Urban"),
  WESTERN("Western");

  private static final long serialVersionUID = 1L;

  private String name;

  FilmGenre(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
