-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: cinema
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `films`
--

DROP TABLE IF EXISTS `films`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `films` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Producer` varchar(100) NOT NULL,
  `FilmGenres` json NOT NULL,
  `AgeRestrictions` int NOT NULL,
  `TicketCost` int DEFAULT NULL,
  `TicketsAmount` int DEFAULT NULL,
  `FilmDuration` int NOT NULL,
  `ReleaseDate` varchar(45) NOT NULL,
  `SessionDate` varchar(45) DEFAULT NULL,
  `IsInRent` tinyint NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `films`
--

LOCK TABLES `films` WRITE;
/*!40000 ALTER TABLE `films` DISABLE KEYS */;
INSERT INTO `films` VALUES (10,'Angry Birds 4','Luc Besson','[\"ANIMATION\", \"FAMILY\"]',3,25,23,120,'10.03.2021','11.03.2021 10:30',1),(11,'Angry Birds 4','Luc Besson','[\"ANIMATION\", \"FAMILY\"]',3,25,23,120,'10.03.2021','12.03.2021 15:00',1),(13,'Tenet','Nolan','[\"ACTION\", \"THRILLER\"]',13,25,23,150,'05.11.2020','15.03.2021 16:40',1),(14,'Tenet','Nolan','[\"ACTION\", \"THRILLER\"]',13,25,23,150,'05.11.2020','10.03.2021 14:00',1),(15,'Venom: Let There Be Carnage','Avi Arad','[\"ACTION\", \"FANTASY\", \"HORROR\", \"THRILLER\"]',16,NULL,NULL,150,'24.06.2021',NULL,0);
/*!40000 ALTER TABLE `films` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Sender` varchar(45) DEFAULT NULL,
  `Receiver` varchar(45) DEFAULT NULL,
  `DateOfSending` varchar(45) DEFAULT NULL,
  `Theme` varchar(100) DEFAULT NULL,
  `Message` varchar(500) DEFAULT NULL,
  `Value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `FilmName` varchar(100) NOT NULL,
  `SeatNumber` int NOT NULL,
  `Cost` int NOT NULL,
  `User` varchar(45) DEFAULT NULL,
  `IsPurchased` tinyint DEFAULT NULL,
  `ReturnRequest` tinyint DEFAULT NULL,
  `PurchasingTime` varchar(45) DEFAULT NULL,
  `SessionDate` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2577 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (2439,'Angry Birds 4',1,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2440,'Angry Birds 4',2,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2441,'Angry Birds 4',3,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2442,'Angry Birds 4',4,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2443,'Angry Birds 4',5,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2444,'Angry Birds 4',6,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2445,'Angry Birds 4',7,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2446,'Angry Birds 4',8,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2447,'Angry Birds 4',9,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2448,'Angry Birds 4',10,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2449,'Angry Birds 4',11,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2450,'Angry Birds 4',12,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2451,'Angry Birds 4',13,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2452,'Angry Birds 4',14,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2453,'Angry Birds 4',15,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2454,'Angry Birds 4',16,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2455,'Angry Birds 4',17,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2456,'Angry Birds 4',18,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2457,'Angry Birds 4',19,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2458,'Angry Birds 4',20,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2459,'Angry Birds 4',21,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2460,'Angry Birds 4',22,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2461,'Angry Birds 4',23,25,NULL,0,0,NULL,'11.03.2021 10:30'),(2462,'Angry Birds 4',1,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2463,'Angry Birds 4',2,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2464,'Angry Birds 4',3,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2465,'Angry Birds 4',4,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2466,'Angry Birds 4',5,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2467,'Angry Birds 4',6,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2468,'Angry Birds 4',7,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2469,'Angry Birds 4',8,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2470,'Angry Birds 4',9,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2471,'Angry Birds 4',10,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2472,'Angry Birds 4',11,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2473,'Angry Birds 4',12,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2474,'Angry Birds 4',13,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2475,'Angry Birds 4',14,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2476,'Angry Birds 4',15,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2477,'Angry Birds 4',16,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2478,'Angry Birds 4',17,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2479,'Angry Birds 4',18,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2480,'Angry Birds 4',19,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2481,'Angry Birds 4',20,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2482,'Angry Birds 4',21,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2483,'Angry Birds 4',22,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2484,'Angry Birds 4',23,25,NULL,0,0,NULL,'12.03.2021 15:00'),(2485,'Tenet',1,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2486,'Tenet',2,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2487,'Tenet',3,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2488,'Tenet',4,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2489,'Tenet',5,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2490,'Tenet',6,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2491,'Tenet',7,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2492,'Tenet',8,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2493,'Tenet',9,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2494,'Tenet',10,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2495,'Tenet',11,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2496,'Tenet',12,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2497,'Tenet',13,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2498,'Tenet',14,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2499,'Tenet',15,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2500,'Tenet',16,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2501,'Tenet',17,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2502,'Tenet',18,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2503,'Tenet',19,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2504,'Tenet',20,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2505,'Tenet',21,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2506,'Tenet',22,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2507,'Tenet',23,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2508,'Tenet',1,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2509,'Tenet',2,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2510,'Tenet',3,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2511,'Tenet',4,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2512,'Tenet',5,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2513,'Tenet',6,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2514,'Tenet',7,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2515,'Tenet',8,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2516,'Tenet',9,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2517,'Tenet',10,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2518,'Tenet',11,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2519,'Tenet',12,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2520,'Tenet',13,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2521,'Tenet',14,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2522,'Tenet',15,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2523,'Tenet',16,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2524,'Tenet',17,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2525,'Tenet',18,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2526,'Tenet',19,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2527,'Tenet',20,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2528,'Tenet',21,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2529,'Tenet',22,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2530,'Tenet',23,25,NULL,0,0,NULL,'20.03.2021 08:30'),(2531,'Tenet',1,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2532,'Tenet',2,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2533,'Tenet',3,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2534,'Tenet',4,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2535,'Tenet',5,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2536,'Tenet',6,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2537,'Tenet',7,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2538,'Tenet',8,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2539,'Tenet',9,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2540,'Tenet',10,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2541,'Tenet',11,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2542,'Tenet',12,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2543,'Tenet',13,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2544,'Tenet',14,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2545,'Tenet',15,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2546,'Tenet',16,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2547,'Tenet',17,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2548,'Tenet',18,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2549,'Tenet',19,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2550,'Tenet',20,25,'qwer',1,0,'05.03.2021 11:39','15.03.2021 16:40'),(2551,'Tenet',21,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2552,'Tenet',22,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2553,'Tenet',23,25,NULL,0,0,NULL,'15.03.2021 16:40'),(2554,'Tenet',1,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2555,'Tenet',2,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2556,'Tenet',3,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2557,'Tenet',4,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2558,'Tenet',5,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2559,'Tenet',6,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2560,'Tenet',7,25,'qwer',1,0,'05.03.2021 11:40','10.03.2021 14:00'),(2561,'Tenet',8,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2562,'Tenet',9,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2563,'Tenet',10,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2564,'Tenet',11,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2565,'Tenet',12,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2566,'Tenet',13,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2567,'Tenet',14,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2568,'Tenet',15,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2569,'Tenet',16,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2570,'Tenet',17,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2571,'Tenet',18,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2572,'Tenet',19,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2573,'Tenet',20,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2574,'Tenet',21,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2575,'Tenet',22,25,NULL,0,0,NULL,'10.03.2021 14:00'),(2576,'Tenet',23,25,NULL,0,0,NULL,'10.03.2021 14:00');
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(150) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Firstname` varchar(45) NOT NULL,
  `Lastname` varchar(45) NOT NULL,
  `UserType` varchar(45) NOT NULL,
  `Birthday` varchar(45) NOT NULL,
  `Money` int NOT NULL,
  `TimeInMessagesMenu` varchar(45) NOT NULL,
  `RegistrationDate` varchar(45) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','-92-8967-1283197-67-113-13-35-3215-58-62-52-74','cinema_admin@mail.ru','Cinema','Admin','Admin','01.01.2000',0,'04.03.2021 12:53','04.03.2021 00:00'),('manager','-92-8967-1283197-67-113-13-35-3215-58-62-52-74','cinema_manager@mail.ru','Cinema','Manager','Manager','01.01.2000',0,'05.03.2021 11:42','04.03.2021 00:00'),('mario','-92-8967-1283197-67-113-13-35-3215-58-62-52-74','qwer@qwer.er','Mario','Bolatelli','Regular','05.05.1990',25,'04.03.2021 13:47','04.03.2021 13:47'),('qwer','-92-8967-1283197-67-113-13-35-3215-58-62-52-74','qwer@qwer.er','qwer','qwer','Regular','01.01.2000',150,'05.03.2021 11:43','04.03.2021 15:51');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-05 11:45:46
