-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: InternetBankingDB
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB-0+deb9u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- -----------------------------------------------------
-- Schema InternetBankingDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `InternetBankingDB` ;

-- -----------------------------------------------------
-- Schema InternetBankingDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `InternetBankingDB` DEFAULT CHARACTER SET utf8 COLLATE 'utf8_general_ci';
USE `InternetBankingDB` ;

--
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
CREATE TABLE `Account` (
  `ID` int(11) NOT NULL,
  `AccountNumber` varchar(255) NOT NULL,
  `Balance` decimal(19,2) NOT NULL,
  `CardNumber` varchar(255) NOT NULL,
  `Currency` varchar(255) NOT NULL,
  `User_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKrk4qmw3tb6f8kj01739wn8q3d` (`User_ID`),
  CONSTRAINT `FKrk4qmw3tb6f8kj01739wn8q3d` FOREIGN KEY (`User_ID`) REFERENCES `User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

--
-- Table structure for table `Transaction`
--

DROP TABLE IF EXISTS `Transaction`;
CREATE TABLE `Transaction` (
  `ID` int(11) NOT NULL,
  `ConstantSymbol` varchar(255) DEFAULT NULL,
  `CreatedDate` datetime NOT NULL,
  `DueDate` datetime NOT NULL,
  `Message` varchar(255) DEFAULT NULL,
  `ReceivedAmount` varchar(255) NOT NULL,
  `ReceiverAccountNumber` varchar(255) NOT NULL,
  `SenderAccountNumber` varchar(255) NOT NULL,
  `SentAmount` varchar(255) NOT NULL,
  `SpecificSymbol` varchar(255) DEFAULT NULL,
  `VariableSymbol` varchar(255) DEFAULT NULL,
  `receiver_ID` int(11) DEFAULT NULL,
  `sender_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKptn36rl9kx2j0rj0fxe8aolm5` (`receiver_ID`),
  KEY `FK9ck67h6fs1hl7prx9ja26gih` (`sender_ID`),
  CONSTRAINT `FK9ck67h6fs1hl7prx9ja26gih` FOREIGN KEY (`sender_ID`) REFERENCES `Account` (`ID`),
  CONSTRAINT `FKptn36rl9kx2j0rj0fxe8aolm5` FOREIGN KEY (`receiver_ID`) REFERENCES `Account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

--
-- Table structure for table `TransactionTemplate`
--

DROP TABLE IF EXISTS `TransactionTemplate`;
CREATE TABLE `TransactionTemplate` (
  `ID` int(11) NOT NULL,
  `ConstantSymbol` varchar(255) DEFAULT NULL,
  `DueDate` datetime DEFAULT NULL,
  `Message` varchar(255) DEFAULT NULL,
  `ReceiverAccountNumber` varchar(255) NOT NULL,
  `SenderAccountNumber` varchar(255) NOT NULL,
  `SentAmount` decimal(19,2) NOT NULL,
  `SpecificSymbol` varchar(255) DEFAULT NULL,
  `TemplateName` varchar(255) NOT NULL,
  `VariableSymbol` varchar(255) DEFAULT NULL,
  `owner_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK3rqqysiecwm2kc81pbdbaxgjy` (`owner_ID`),
  CONSTRAINT `FK3rqqysiecwm2kc81pbdbaxgjy` FOREIGN KEY (`owner_ID`) REFERENCES `User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `ID` int(11) NOT NULL,
  `BirthDate` datetime DEFAULT NULL,
  `BirthNumber` varchar(255) NOT NULL,
  `City` varchar(255) DEFAULT NULL,
  `Email` varchar(255) NOT NULL,
  `Enabled` bit(1) NOT NULL,
  `FirstName` varchar(255) NOT NULL,
  `HouseNumber` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) NOT NULL,
  `MobileNumber` varchar(255) DEFAULT NULL,
  `Password` varchar(255) NOT NULL,
  `Role` varchar(255) NOT NULL,
  `Street` varchar(255) DEFAULT NULL,
  `Login` varchar(255) NOT NULL,
  `ZipCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

-- -----------------------------------------------------
-- Populate DB
-- -----------------------------------------------------
INSERT INTO InternetBankingDB.User (ID, BirthDate, BirthNumber, City, Email, Enabled, FirstName, HouseNumber, LastName, MobileNumber, Password, Role, Street, Login, ZipCode) VALUES (1, null, '870515/2213', null, 'kratochvil.jan@gmail.com', true, 'Jan', null, 'Kratochvíl', null, '$2a$10$ARvhGS7CyDaSUi9ANHvF1OIxf2FOBVNDsSbkMJbe6/4cxBnq4IIgy', 'ADMIN', null, 'Admin001', null);
INSERT INTO InternetBankingDB.User (ID, BirthDate, BirthNumber, City, Email, Enabled, FirstName, HouseNumber, LastName, MobileNumber, Password, Role, Street, Login, ZipCode) VALUES (2, null, '630913/1985', null, 'brad@pitt.com', true, 'Brad', null, 'Pitt', null, '$2a$10$fKX7ALXIaVGS6H5PpgencO19U4Blsz4vyoRr8lUaykaNCUw1eCLgK', 'CUSTOMER', null, 'User0001', null);
INSERT INTO InternetBankingDB.User (ID, BirthDate, BirthNumber, City, Email, Enabled, FirstName, HouseNumber, LastName, MobileNumber, Password, Role, Street, Login, ZipCode) VALUES (3, null, '940815/2118', 'Praha', 'kral.filip@gmail.com', true, 'Filip', '1023', 'Král', null, '$2a$10$hGuSfwk177ttIy/WfBFBsuysuJPrTDC/Yu6lUF6xHVfy6trDG4XpC', 'CUSTOMER', 'Pražská', 'User0002', '100 02');
INSERT INTO InternetBankingDB.Account (ID, AccountNumber, Balance, CardNumber, Currency, User_ID) VALUES (4, '232124-2321243368/7300', 7451.12, '2321243368922611', 'USD', 2);
INSERT INTO InternetBankingDB.Account (ID, AccountNumber, Balance, CardNumber, Currency, User_ID) VALUES (5, '039675-0396758932/7300', 127905.70, '0396758932489929', 'CZK', 3);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (6, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '224,94 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '10 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (7, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '320,09 USD', '232124-2321243368/7300', '039675-0396758932/7300', '7 200 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (8, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '1 012,23 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '45 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (9, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '7,69 USD', '232124-2321243368/7300', '039675-0396758932/7300', '173 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (10, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '2 901,72 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '129 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (11, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '53,35 USD', '232124-2321243368/7300', '039675-0396758932/7300', '1 200 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (12, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '200,05 USD', '232124-2321243368/7300', '039675-0396758932/7300', '4 500 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (13, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '224,94 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '10 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (14, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '337,41 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '15 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (15, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '967,24 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '43 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (16, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '269,93 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '12 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (17, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '247,43 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '11 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (18, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '17,78 USD', '232124-2321243368/7300', '039675-0396758932/7300', '400 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (19, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '14,63 USD', '232124-2321243368/7300', '039675-0396758932/7300', '329 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (20, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '17,78 USD', '232124-2321243368/7300', '039675-0396758932/7300', '400 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (21, null, '2019-01-05 11:46:49', '2019-01-05 11:46:49', null, '53,35 USD', '232124-2321243368/7300', '039675-0396758932/7300', '1 200 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (22, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '14,63 USD', '232124-2321243368/7300', '039675-0396758932/7300', '329 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (23, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '365,21 USD', '232124-2321243368/7300', '039675-0396758932/7300', '8 215 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (24, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '967,24 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '43 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (25, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '15 745,77 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '700 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (26, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '224,94 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '10 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (27, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '320,09 USD', '232124-2321243368/7300', '039675-0396758932/7300', '7 200 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (28, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '1 012,23 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '45 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (29, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '7,69 USD', '232124-2321243368/7300', '039675-0396758932/7300', '173 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (30, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '2 901,72 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '129 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (31, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '53,35 USD', '232124-2321243368/7300', '039675-0396758932/7300', '1 200 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (32, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '200,05 USD', '232124-2321243368/7300', '039675-0396758932/7300', '4 500 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (33, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '224,94 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '10 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (34, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '337,41 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '15 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (35, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '967,24 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '43 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (36, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '269,93 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '12 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (37, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '247,43 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '11 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (38, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '17,78 USD', '232124-2321243368/7300', '039675-0396758932/7300', '400 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (39, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '14,63 USD', '232124-2321243368/7300', '039675-0396758932/7300', '329 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (40, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '17,78 USD', '232124-2321243368/7300', '039675-0396758932/7300', '400 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (41, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '53,35 USD', '232124-2321243368/7300', '039675-0396758932/7300', '1 200 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (42, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '14,63 USD', '232124-2321243368/7300', '039675-0396758932/7300', '329 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (43, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '365,21 USD', '232124-2321243368/7300', '039675-0396758932/7300', '8 215 CZK', null, null, 4, 5);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (44, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '967,24 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '43 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.Transaction (ID, ConstantSymbol, CreatedDate, DueDate, Message, ReceivedAmount, ReceiverAccountNumber, SenderAccountNumber, SentAmount, SpecificSymbol, VariableSymbol, receiver_ID, sender_ID) VALUES (45, null, '2019-01-05 11:46:50', '2019-01-05 11:46:50', null, '15 745,77 CZK', '039675-0396758932/7300', '232124-2321243368/7300', '700 USD', null, null, 5, 4);
INSERT INTO InternetBankingDB.hibernate_sequence (next_val) VALUES (100);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-05 13:56:36