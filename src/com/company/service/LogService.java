package com.company.service;

import static com.company.service.AccountService.getCinemaUser;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class LogService {

  public static void setLogProperties() {
    RollingFileAppender fa = new RollingFileAppender();
    fa.setFile(String.format("src\\com\\company\\data\\%s%s(%s).log",
        getCinemaUser().getFirstname(),
        getCinemaUser().getLastname(),
        getCinemaUser().getUsername()));
    fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
    fa.setThreshold(Level.INFO);
    fa.setMaxFileSize("5MB");
    fa.setAppend(true);
    fa.activateOptions();
    Logger.getRootLogger().removeAllAppenders();
    Logger.getRootLogger().addAppender(fa);
  }
}
