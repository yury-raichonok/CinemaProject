package com.company.model.ticket;

import java.util.Comparator;

public class TicketComparator implements Comparator<Ticket> {

  @Override
  public int compare(Ticket o1, Ticket o2) {
    if (o1.getSESSION_DATE().isAfter(o2.getSESSION_DATE())) {
      return 1;
    } else if (o1.getSESSION_DATE().isBefore(o2.getSESSION_DATE())) {
      return -1;
    } else {
      return Integer.compare(o1.getSEAT_NUMBER(), o2.getSEAT_NUMBER());
    }
  }
}
