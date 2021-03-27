# CinemaProject
Cinema application with a console UI. Final project at the end of the Java Core course.

# About application
An application for a cinema where you can watch sessions, purchase tickets for a specific movie.

There are three user levels (each has its own access level):
- regular user (can view events sessions, films in cinema, buy and return tickets, view purchased tickets);
- cinema manager (can edit sessions, films, buy and return tickets for a specific user);
- cinema admin (can delete and modify users, delete and change film sessions).

The data is stored in the database, the dump of which is located in the src.com.company.data package. Every user action is logged with saving the log to the same package.

User passwords are hashed using SHA-512 encoding. The password for the entities presented in the database is 123qwerQWER.
