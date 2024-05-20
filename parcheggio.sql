-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.24-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for park
CREATE DATABASE IF NOT EXISTS `park` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `park`;

-- Dumping structure for table park.auto
CREATE TABLE IF NOT EXISTS `auto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `targa` char(7) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modello` varchar(50) NOT NULL,
  `parcheggiata` bit(1) NOT NULL DEFAULT b'0',
  `media_ore` float NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `targa` (`targa`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table park.auto: ~18 rows (approximately)
/*!40000 ALTER TABLE `auto` DISABLE KEYS */;
INSERT INTO `auto` (`id`, `targa`, `marca`, `modello`, `parcheggiata`, `media_ore`) VALUES
	(1, 'ED198NX', 'Fiat', 'Panda', b'0', 0),
	(2, 'DD004RT', 'Ford', 'Fiesta', b'0', 0),
	(3, 'DD058GG', 'BMW', 'Serie 1', b'0', 0),
	(4, 'DR665RT', 'Ford', 'Puma', b'0', 0),
	(5, 'ER777TT', 'Audi', 'A1', b'0', 0),
	(6, 'DD056CF', 'Mercedes', 'Classe A', b'0', 0),
	(7, 'DD454RZ', 'Volkswagen', 'Golf', b'0', 0),
	(8, 'BA005ZZ', 'Fiat', '600', b'0', 0),
	(9, 'CD058YT', 'Citroen', 'C3', b'0', 0),
	(10, 'DZ056DL', 'Toyota', 'Yaris', b'0', 0),
	(11, 'AB056AB', 'Fiat', '600', b'0', 0),
	(12, 'DD587RT', 'Audi', 'A1', b'0', 0),
	(13, 'ED198ND', 'Volkswagen', 'Golf', b'0', 0),
	(14, 'DR785RR', 'Lancia', 'Y', b'0', 0),
	(15, 'ER889TT', 'Volvo', 'V40', b'0', 0),
	(16, 'ER898NF', 'Renault', 'Clio', b'0', 0),
	(17, 'EF190NY', 'Renault', 'Captur', b'0', 0),
	(18, 'GA098DR', 'Opel', 'Corsa', b'0', 0);
/*!40000 ALTER TABLE `auto` ENABLE KEYS */;

-- Dumping structure for view park.auto_parcheggiate
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `auto_parcheggiate` (
	`id` INT(11) NOT NULL,
	`targa` CHAR(7) NOT NULL COLLATE 'utf8mb4_general_ci',
	`marca` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`modello` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`ingresso` TIMESTAMP NULL,
	`luogo_parcheggio` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`id_park` INT(11) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table park.parcheggio
CREATE TABLE IF NOT EXISTS `parcheggio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `luogo` varchar(50) NOT NULL,
  `indirizzo` varchar(50) NOT NULL,
  `num_telefono` varchar(10) NOT NULL,
  `posti_disponibili` tinyint(2) NOT NULL DEFAULT 10,
  `posti_max` tinyint(2) NOT NULL DEFAULT 10,
  `tot_guadagno` float NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table park.parcheggio: ~3 rows (approximately)
/*!40000 ALTER TABLE `parcheggio` DISABLE KEYS */;
INSERT INTO `parcheggio` (`id`, `luogo`, `indirizzo`, `num_telefono`, `posti_disponibili`, `posti_max`, `tot_guadagno`) VALUES
	(1, 'Roma', 'via trionfale, 12', '3330000000', 10, 10, 0),
	(2, 'Milano', 'viale Pisa, 55', '3480000000', 10, 10, 0),
	(3, 'Napoli', 'via della Pizza, 89', '3405655565', 10, 10, 0);
/*!40000 ALTER TABLE `parcheggio` ENABLE KEYS */;

-- Dumping structure for table park.ticket
CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ingresso` timestamp NULL DEFAULT NULL,
  `uscita` timestamp NULL DEFAULT NULL,
  `prezzo` float DEFAULT NULL,
  `ore_passate` int(11) DEFAULT NULL,
  `parcheggio` int(11) NOT NULL,
  `auto` char(7) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ticket_parcheggio` (`parcheggio`),
  KEY `FK_ticket_auto` (`auto`),
  CONSTRAINT `FK_ticket_auto` FOREIGN KEY (`auto`) REFERENCES `auto` (`targa`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ticket_parcheggio` FOREIGN KEY (`parcheggio`) REFERENCES `parcheggio` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5791 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table park.ticket: ~1 rows (approximately)
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;

-- Dumping structure for table park.user
CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_admin` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table park.user: ~2 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`username`, `password`, `is_admin`) VALUES
	('admin', '$2a$10$TjKjPvknPPOKs0w8h4Zz1.EtQK4stestddaL3ixgrIh9LPVNaTNj.', b'1'),
	('user', '$2a$10$nOMl8Lgxc27.OCoaiQbtJeC6DEAEM3Yem5mTrsnPmXHYGchNHDHr6', b'0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for view park.vista_grafico
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `vista_grafico` (
	`id` INT(11) NOT NULL,
	`targa` CHAR(7) NOT NULL COLLATE 'utf8mb4_general_ci',
	`uscita` TIMESTAMP NULL,
	`luogo_parcheggio` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`id_park` INT(11) NOT NULL,
	`tot_guadagno` FLOAT NULL,
	`media_ore` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view park.auto_parcheggiate
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `auto_parcheggiate`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `auto_parcheggiate` AS SELECT ticket.id, ticket.auto AS 'targa', auto.marca, auto.modello, ticket.ingresso, parcheggio.luogo AS 'luogo_parcheggio', parcheggio.id AS 'id_park'
FROM ticket
INNER JOIN auto
ON ticket.auto = auto.targa
INNER JOIN parcheggio
ON ticket.parcheggio = parcheggio.id 
WHERE ticket.uscita IS NULL AND ticket.prezzo IS NULL ;

-- Dumping structure for view park.vista_grafico
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `vista_grafico`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_grafico` AS SELECT ticket.id, ticket.auto AS 'targa', ticket.uscita, parcheggio.luogo AS 'luogo_parcheggio', parcheggio.id AS 'id_park', ticket.prezzo AS 'tot_guadagno', ticket.ore_passate AS 'media_ore'
FROM ticket
INNER JOIN auto
ON ticket.auto = auto.targa
INNER JOIN parcheggio
ON ticket.parcheggio = parcheggio.id 
WHERE ticket.uscita IS NOT NULL AND ticket.prezzo IS NOT NULL ;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
