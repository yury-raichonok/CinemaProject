package com.company.model.film;

import java.util.Comparator;

public class FilmComparator implements Comparator<Film> {

  @Override
  public int compare(Film o1, Film o2) {
    if (o1.isInRent() && o2.isInRent()) {
      if (o1.getSessionDate().isBefore(o2.getSessionDate())) {
        return -1;
      } else if (o1.getSessionDate().isAfter(o2.getSessionDate())) {
        return 1;
      } else {
        return 0;
      }
    } else {
      if (o1.getReleaseDate().isBefore(o2.getReleaseDate())) {
        return 1;
      } else if (o1.getReleaseDate().isAfter(o2.getReleaseDate())) {
        return -1;
      } else {
        return 0;
      }
    }
  }
}
