package com.company.model.message;

import java.util.Comparator;

public class MessageComparator implements Comparator<Message> {

  @Override
  public int compare(Message o1, Message o2) {
    if (o1.getDATE_OF_SENDING().isBefore(o2.getDATE_OF_SENDING())) {
      return 1;
    } else if (o1.getDATE_OF_SENDING().isAfter(o2.getDATE_OF_SENDING())) {
      return -1;
    } else {
      return 0;
    }
  }
}
