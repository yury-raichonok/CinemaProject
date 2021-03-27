package com.company.model.user;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

  @Override
  public int compare(User o1, User o2) {
    char[] array1;
    char[] array2;
    if (!o1.getFirstname().equals(o2.getFirstname())) {
      array1 = o1.getFirstname().toCharArray();
      array2 = o2.getFirstname().toCharArray();
      if (array1.length <= array2.length) {
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] < array2[i]) {
            return -1;
          } else if (array1[i] > array2[i]) {
            return 1;
          }
        }
      } else {
        for (int i = 0; i < array2.length; i++) {
          if (array1[i] < array2[i]) {
            return -1;
          } else if (array1[i] > array2[i]) {
            return 1;
          }
        }
      }
    } else if (!o1.getLastname().equals(o2.getLastname())) {
      array1 = o1.getLastname().toCharArray();
      array2 = o2.getLastname().toCharArray();
      if (array1.length <= array2.length) {
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] < array2[i]) {
            return -1;
          } else if (array1[i] > array2[i]) {
            return 1;
          }
        }
      } else {
        for (int i = 0; i < array2.length; i++) {
          if (array1[i] < array2[i]) {
            return -1;
          } else if (array1[i] > array2[i]) {
            return 1;
          }
        }
      }
    } else {
      array1 = o1.getUsername().toCharArray();
      array2 = o2.getUsername().toCharArray();
      if (array1.length <= array2.length) {
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] < array2[i]) {
            return -1;
          } else if (array1[i] > array2[i]) {
            return 1;
          }
        }
      } else {
        for (int i = 0; i < array2.length; i++) {
          if (array1[i] < array2[i]) {
            return -1;
          } else if (array1[i] > array2[i]) {
            return 1;
          }
        }
      }
    }
    return 0;
  }
}
