package com.company.view;

import static com.company.service.AccountService.WRONG_INPUT;
import static com.company.service.AccountService.createAccount;
import static com.company.service.AccountService.enterInAccount;
import static com.company.service.ValidationService.inputValidation;

public class MainView {

  private static boolean inEnteringMenu = false;
  private static boolean allFilms = false;
  private static boolean nowShowingFilms = false;
  private static boolean comingSoonFilms = false;

  public static void main(String[] args) {
    startMenu();
  }

  public static void startMenu() {
    inEnteringMenu = true;
    System.out.println("Welcome to cinema!");
    while (inEnteringMenu) {
      System.out.println(
          "1. Sign in (if you have an account);\n"
              + "2. Sign up;\n"
              + "3. Exit.");
      switch (inputValidation()) {
        case 1:
          enterInAccount();
          break;
        case 2:
          createAccount();
          break;
        case 3:
          System.out.println("Goodbye!");
          inEnteringMenu = false;
          return;
        default:
          System.out.println(WRONG_INPUT);
          break;
      }
    }
  }

  public static void setInEnteringMenu(boolean inEnteringMenu) {
    MainView.inEnteringMenu = inEnteringMenu;
  }

  public static boolean isAllFilms() {
    return allFilms;
  }

  public static void setAllFilms(boolean allFilms) {
    MainView.allFilms = allFilms;
  }

  public static boolean isNowShowingFilms() {
    return nowShowingFilms;
  }

  public static void setNowShowingFilms(boolean nowShowingFilms) {
    MainView.nowShowingFilms = nowShowingFilms;
  }

  public static boolean isComingSoonFilms() {
    return comingSoonFilms;
  }

  public static void setComingSoonFilms(boolean comingSoonFilms) {
    MainView.comingSoonFilms = comingSoonFilms;
  }
}
