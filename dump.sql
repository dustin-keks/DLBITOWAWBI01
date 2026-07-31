CREATE DATABASE  IF NOT EXISTS `dlbitowawbi01` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dlbitowawbi01`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dlbitowawbi01
-- ------------------------------------------------------
-- Server version	8.4.7

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
-- Table structure for table `aufgabe`
--

DROP TABLE IF EXISTS `aufgabe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aufgabe` (
  `id` binary(16) NOT NULL,
  `beschreibung` text,
  `status` enum('ERLEDIGT','IN_BEARBEITUNG','OFFEN') DEFAULT NULL,
  `titel` varchar(255) DEFAULT NULL,
  `projekt_id` binary(16) DEFAULT NULL,
  `zugewiesener_benutzer` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6nodiis1ymaqqi3wn38iigmsg` (`projekt_id`),
  KEY `FKsx916vgc1jsp2xr1gvv4xhevr` (`zugewiesener_benutzer`),
  CONSTRAINT `FK6nodiis1ymaqqi3wn38iigmsg` FOREIGN KEY (`projekt_id`) REFERENCES `projekt` (`id`),
  CONSTRAINT `FKsx916vgc1jsp2xr1gvv4xhevr` FOREIGN KEY (`zugewiesener_benutzer`) REFERENCES `benutzer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aufgabe`
--

LOCK TABLES `aufgabe` WRITE;
/*!40000 ALTER TABLE `aufgabe` DISABLE KEYS */;
INSERT INTO `aufgabe` VALUES (_binary '\Z\ë\Ø\0OEq‡¢\İûÄ»I','3x tÃ¤glich','IN_BEARBEITUNG','Hund fÃ¼ttern',_binary '˜qú-GD÷‚tDgµbKj',NULL),(_binary '‹\ÄI8\ßDŒ=^r\ì^\0ü','NPE in Zeile 27','IN_BEARBEITUNG','Fehler beheben',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç',_binary 'µ¯{¥º\ÇMÊ’ºˆ\İB#'),(_binary '‘{­<\"EÍªd©D\İ#t','fÃ¼r Vielsaft-Trank','ERLEDIGT','Baumschlangenhaut',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'I\Î„E‡†KLú`}$,'),(_binary '\ÄdÕ—\ÆA¦¡¿T	\êI\Ü','Sollen auf den Tischen stehen, sodass geputzt werden kann','ERLEDIGT','StÃ¼hle wegrÃ¤umen',_binary 'Q·ÕŸfV@o*e¶1|%9',_binary 'ò{;bKÛ±ˆa¡•'),(_binary 'õD\ÎBG­ŸC\Ú\å\ÃY','Neue Lehrerin einplanen','OFFEN','5. Schuljahr',_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù',NULL),(_binary '$\Ø}\Ò0\ÅLÏ¬\n\Ùj,±','','ERLEDIGT','2. Schuljahr',_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù',NULL),(_binary '( ùŸ.\âNË¹ÿ/Š\Ë95','','OFFEN','Sitzgelegenheiten',_binary 'MIs´vC“­‡.ü+ÿ\É',_binary 'I\Î„E‡†KLú`}$,'),(_binary '3c\ín[ŸE¤¹6Ho€Qû','Siehe Zeile 10','ERLEDIGT','Fehler beheben',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç',_binary 'zÏ¸a75Fï´›47\Ñ]ü]'),(_binary ':tO\Ò\Ş\ÄL~›yr\ÇW\Ë/%','','IN_BEARBEITUNG','Eine Aufgabe',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç',_binary '3òyişNH²üÀzm\é‹]'),(_binary '=5\í~#~@e½\ê\İó¥ò}','','ERLEDIGT','Eine zweite Aufgabe',_binary '\"ö$­4>D\n¢_\í.\É\0lT',_binary 'µ¯{¥º\ÇMÊ’ºˆ\İB#'),(_binary '?FÆ¬/óA™°» .`l','fÃ¼r Vielsaft-Trank','IN_BEARBEITUNG','KnÃ¶terich',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'I\Î„E‡†KLú`}$,'),(_binary '?yø¥ôA\0¸\è\Öş\Ñ','','ERLEDIGT','Briefe versiegeln',_binary '\'Ã´\Ô\á\ŞC»’\Ö\Üù\Zm2',_binary 'm¶\Ê¾9F­›·:¥B\â\Ó\É'),(_binary 'Qd¦+#E¶b3)3>\Ê','fÃ¼r Schrumpf-Trank','ERLEDIGT','GÃ¤nseblÃ¼mchenwurzel',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'N<b3pN\åº÷¶ˆÒŠõ°'),(_binary 'R|\'„‹JBªq\×c{W\Ï','Arsenius Bunsen','IN_BEARBEITUNG','ZaubertrÃ¤nke und ZauberbrÃ¤ue',_binary 'ö¡1r…SB³‚\\”\ê%h',_binary 'KDóCB\"‹›Oc$€F³'),(_binary 'S\É\×Ê¥@\\.¦lT|','','ERLEDIGT','4. Schuljahr',_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù',NULL),(_binary 'T{8løOC^‰™z¿8½L1','','ERLEDIGT','Deko bestellen',_binary 'MIs´vC“­‡.ü+ÿ\É',_binary 'ù§ÁòB¨¥M¤¿‘\È('),(_binary 'YF3\Ú00N$¹2ˆı\åò™','fÃ¼r Heiltrank gegen Furunkel','ERLEDIGT','Stachelschweinstachel',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'I\Î„E‡†KLú`}$,'),(_binary '[*+ı@r«6\Z“Oy','von Bathilda Bagshot','ERLEDIGT','Geschichte der Zauberei',_binary 'ö¡1r…SB³‚\\”\ê%h',_binary '^À\Ü\âØªD°Ó´@vU–'),(_binary '_l\ß\×\ßE”“Z<\É <=','Am Wochenende','OFFEN','Aquarium putzen',_binary '˜qú-GD÷‚tDgµbKj',NULL),(_binary 'ep	{\åzAÅ›x\ÆÅŠ\â\í','','ERLEDIGT','Briefe in UmschlÃ¤ge packen',_binary '\'Ã´\Ô\á\ŞC»’\Ö\Üù\Zm2',_binary 'KDóCB\"‹›Oc$€F³'),(_binary 'f\ß\ß\ì•<G\à¨rÆ³­ƒ','#fff in #f1f1f1','ERLEDIGT','Farben Ã¤ndern',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç',_binary '\rğu—\rBK„Ô¯ùš\ç'),(_binary 'jŠ\ï­NNˆ’}\ãş6\Ò','','ERLEDIGT','Tische wegrÃ¤umen',_binary 'Q·ÕŸfV@o*e¶1|%9',_binary 'ò{;bKÛ±ˆa¡•'),(_binary 'k6ñc¥EZ£ñ•d>_j','jeden Abend','OFFEN','Blumen gieÃŸen',_binary '˜qú-GD÷‚tDgµbKj',NULL),(_binary 'kT÷\Õ&@È¿?\áU\rZm\ë','Nichts zu erledigen','ERLEDIGT','Eine archivierte Aufgabe',_binary '\"ö$­4>D\n¢_\í.\É\0lT',_binary 'zÏ¸a75Fï´›47\Ñ]ü]'),(_binary 'tŞ²GÒF\à½\İe“','fÃ¼r Trunk des Friedens','OFFEN','Mondstein',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'N<b3pN\åº÷¶ˆÒŠõ°'),(_binary '…°df_‹G.‰¸\ÒİšVÀ','Neuen Lehrer einplanen','OFFEN','3. Schuljahr',_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù',NULL),(_binary 'Uú\Z˜ZA\ç€|LIª','Kein Sport-Unterricht','IN_BEARBEITUNG','6. Schuljahr',_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù',NULL),(_binary '\Û1ü¼jJüˆ¡oy\Ôp\Ú','fÃ¼r Heiltrank gegen Furunkel','ERLEDIGT','Wellhornschnecken',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'I\Î„E‡†KLú`}$,'),(_binary '™\ï\Î3IùJ\n½U~¤\î\á¢P','fÃ¼r StÃ¤rkungs-Trank','OFFEN','Feuersalamander-Blut',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'I\Î„E‡†KLú`}$,'),(_binary '§C\Ú\íHOg»‚>‹\Ó?','','ERLEDIGT','1. Schuljahr',_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù',NULL),(_binary '´1²KO‹Ã®f|ª\ï¦','','OFFEN','Noch eine Aufgabe',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç',_binary '3òyişNH²üÀzm\é‹]'),(_binary '´‹giÀN‡e;l\í·õ\Î','auch vegetarisch','OFFEN','Buffet',_binary 'MIs´vC“­‡.ü+ÿ\É',_binary 'N<b3pN\åº÷¶ˆÒŠõ°'),(_binary '¼•¢\íIƒ¶e>ô1Œ','bla blub bla','OFFEN','Eine andere Aufgabe',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç',_binary '3òyişNH²üÀzm\é‹]'),(_binary '¾”/”ÿ7CV±öb5\0Ö‰G','fÃ¼r Vielsaft-Trank','ERLEDIGT','Flussgras',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'I\Î„E‡†KLú`}$,'),(_binary '¾©ŠG™E!ˆ²EPC³','jeden 2. Tag','OFFEN','Katzenklo sauber machen',_binary '˜qú-GD÷‚tDgµbKj',NULL),(_binary '\Ç\Ùf«aHhœ‹å¯•!\n','fÃ¼r Vielsaft-Trank','ERLEDIGT','Zweihorn-Horn',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'I\Î„E‡†KLú`}$,'),(_binary '\ÈM§ù\ÙFœ¡O$Z+\êx','1x tÃ¤glich','IN_BEARBEITUNG','Staub saugen',_binary '˜qú-GD÷‚tDgµbKj',NULL),(_binary '\Éöi®RøF\r™ J\í`\Ü','','ERLEDIGT','7. Schuljahr',_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù',NULL),(_binary '\Ğg¹+bLóŒû@Ÿ3n\"7','Mit einer Eule','ERLEDIGT','Briefe verschicken',_binary '\'Ã´\Ô\á\ŞC»’\Ö\Üù\Zm2',_binary 'N<b3pN\åº÷¶ˆÒŠõ°'),(_binary '\Öpš\É)¸C\è%°C¢Ô7','muss erledigt werden','ERLEDIGT','Eine Aufgabe',_binary '^qzÿ\ïN/‘ğƒQò*K',_binary 'µ¯{¥º\ÇMÊ’ºˆ\İB#'),(_binary '\á\ç#$ñ\×@\ç¡LD T²','von Emeric Wendel','IN_BEARBEITUNG','Verwandlungen fÃ¼r AnfÃ¤nger',_binary 'ö¡1r…SB³‚\\”\ê%h',_binary 'm¶\Ê¾9F­›·:¥B\â\Ó\É'),(_binary '\ã\ËkK†M‚‡Y\ÃE','','OFFEN','Deko anbringen',_binary 'MIs´vC“­‡.ü+ÿ\É',_binary 'ò{;bKÛ±ˆa¡•'),(_binary 'óJä­\ÓIŸ«ˆa\à¤Q','von Newt Scamander','OFFEN','Phantastische Tierwesen und wo sie zu finden sind',_binary 'ö¡1r…SB³‚\\”\ê%h',NULL),(_binary '÷œƒ¼Ÿ2Ad\ë*\ìõ\Û)ù','','IN_BEARBEITUNG','Boden aufwischen',_binary 'Q·ÕŸfV@o*e¶1|%9',_binary 'KDóCB\"‹›Oc$€F³'),(_binary 'ùupª\ãJ˜œ\ä/i\î\Â','fÃ¼r Wiederbelebungs-Trank','IN_BEARBEITUNG','Alraune',_binary '\ÔbPJ=‰¼{Ï»Œ ‰',_binary 'N<b3pN\åº÷¶ˆÒŠõ°');
/*!40000 ALTER TABLE `aufgabe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `benutzer`
--

DROP TABLE IF EXISTS `benutzer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `benutzer` (
  `id` binary(16) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `passwort` varchar(255) DEFAULT NULL,
  `rolle` enum('ADMIN','BENUTZER','MITARBEITER','PROJEKTLEITER') DEFAULT NULL,
  `mandant_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmy02fi9nqar31pqp9r3ncnqej` (`email`),
  KEY `FKo0sav8krbu9fqirox0jiu7y5r` (`mandant_id`),
  CONSTRAINT `FKo0sav8krbu9fqirox0jiu7y5r` FOREIGN KEY (`mandant_id`) REFERENCES `mandant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benutzer`
--

LOCK TABLES `benutzer` WRITE;
/*!40000 ALTER TABLE `benutzer` DISABLE KEYS */;
INSERT INTO `benutzer` VALUES (_binary 'ù§ÁòB¨¥M¤¿‘\È(','mcgonagall@hogwarts.de','mcgonagall','$2a$10$Z1KngYjXoSBE6vKKz11h1eqGCOg3oSVPWIWEP6QcDUd5EihQAzGI.','PROJEKTLEITER',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary '\rğu—\rBK„Ô¯ùš\ç','benutzer@beispiel.de','benutzer','$2a$10$HWsVn2Gai/4NVDlDhlnmwe8SrLatZEd8iiqaMtd7fpmmij5pVSYNm','BENUTZER',_binary 'ü\×[\í\í‹@!°\È:\Ó)'),(_binary '3òyişNH²üÀzm\é‹]','mitarbeiter@beispiel.de','mitarbeiter','$2a$10$O61PmaJkajjEOMhz1InOCORQdRj0XYvbzUBpKZJMyc7RvmVOOSlu6','MITARBEITER',_binary 'ü\×[\í\í‹@!°\È:\Ó)'),(_binary 'I\Î„E‡†KLú`}$,','snape@hogwarts.de','snape','$2a$10$A6t2F46X9a0nV1KA4zJlHeV2gH5DLPKmwjQCkGp39F0A1a/Vp82mC','PROJEKTLEITER',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary 'N<b3pN\åº÷¶ˆÒŠõ°','dumbledore@hogwarts.de','dumbledore','$2a$10$66z9B2tbnGGe1/YTyKKvuuBOv.gi4E55yuDyI2a0SSEW6F1sO1rNG','ADMIN',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary '^À\Ü\âØªD°Ó´@vU–','hermine@hogwarts.de','hermine','$2a$10$TP9yMjGFFAkEp0mICXrkxOLhr1M75Ht3SggK7YrMKD.GlpXFs/J6i','BENUTZER',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary 'm¶\Ê¾9F­›·:¥B\â\Ó\É','ron@hogwarts.de','ron','$2a$10$09x6ZZxvMo9G2853s5br6uswt1BWslxpLNUvodZL/R9WngqRkdv2S','BENUTZER',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary 'x· ×°JL–«\İj	\êÉ¯\í','draco@hogwarts.de','draco','$2a$10$NnA2.Dqz8DYEqV1r6n5fX.U3DakrHUJf1in3FFvC8XmuWK3RYP2ia','BENUTZER',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary 'zÏ¸a75Fï´›47\Ñ]ü]','projektleiter@beispiel.de','projektleiter','$2a$10$e84OCW.DJ97UwN5px.0J1ObwgGzLYVGq5341OO9j9GGtOcbkUK68G','PROJEKTLEITER',_binary 'ü\×[\í\í‹@!°\È:\Ó)'),(_binary 'KDóCB\"‹›Oc$€F³','harry@hogwarts.de','harry','$2a$10$PF1WhLagDfvMyeXrF.QzfuLaPEjKJyY7XWorV38Xqwy0cM9hpvily','BENUTZER',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary 'µ¯{¥º\ÇMÊ’ºˆ\İB#','admin@beispiel.de','admin','$2a$10$eJ6EFmuWJzQISCTzwFNJKOLSeGwczp7jqgLKrDxHYFpCWG4SKMsSu','ADMIN',_binary 'ü\×[\í\í‹@!°\È:\Ó)'),(_binary 'ò{;bKÛ±ˆa¡•','hagrid@hogwarts.de','hagrid','$2a$10$3KichQtqLRRnyt/1tbDtve/EMN1NeFG2w1c6sQWScGuJICZawUQha','MITARBEITER',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î');
/*!40000 ALTER TABLE `benutzer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mandant`
--

DROP TABLE IF EXISTS `mandant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mandant` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mandant`
--

LOCK TABLES `mandant` WRITE;
/*!40000 ALTER TABLE `mandant` DISABLE KEYS */;
INSERT INTO `mandant` VALUES (_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î','Schule fÃ¼r Hexerei und Zauberei'),(_binary 'ü\×[\í\í‹@!°\È:\Ó)','Beispiel GmbH');
/*!40000 ALTER TABLE `mandant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projekt`
--

DROP TABLE IF EXISTS `projekt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projekt` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` enum('AKTIV','ARCHIVIERT') DEFAULT NULL,
  `mandant_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnnmynv0dqjvp8jil2dicji05o` (`mandant_id`),
  CONSTRAINT `FKnnmynv0dqjvp8jil2dicji05o` FOREIGN KEY (`mandant_id`) REFERENCES `mandant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projekt`
--

LOCK TABLES `projekt` WRITE;
/*!40000 ALTER TABLE `projekt` DISABLE KEYS */;
INSERT INTO `projekt` VALUES (_binary '\ÔbPJ=‰¼{Ï»Œ ‰','Zutaten einkaufen','AKTIV',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç','Ein Projekt','AKTIV',_binary 'ü\×[\í\í‹@!°\È:\Ó)'),(_binary '\"ö$­4>D\n¢_\í.\É\0lT','Ein archiviertes Projekt','ARCHIVIERT',_binary 'ü\×[\í\í‹@!°\È:\Ó)'),(_binary '\'Ã´\Ô\á\ŞC»’\Ö\Üù\Zm2','Briefe verschicken','ARCHIVIERT',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary 'Q·ÕŸfV@o*e¶1|%9','Keller aufrÃ¤umen','AKTIV',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary '^qzÿ\ïN/‘ğƒQò*K','Ein anderes Projekt','AKTIV',_binary 'ü\×[\í\í‹@!°\È:\Ó)'),(_binary '˜qú-GD÷‚tDgµbKj','Noch ein Projekt','AKTIV',_binary 'ü\×[\í\í‹@!°\È:\Ó)'),(_binary 'MIs´vC“­‡.ü+ÿ\É','Halloween-Party planen','AKTIV',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù','StundenplÃ¤ne erstellen','AKTIV',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î'),(_binary 'ö¡1r…SB³‚\\”\ê%h','BÃ¼cher lesen','AKTIV',_binary '¿\ê\Ä:Nš¸&\Øà¥\Ä\Î');
/*!40000 ALTER TABLE `projekt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projekt_mitglieder`
--

DROP TABLE IF EXISTS `projekt_mitglieder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projekt_mitglieder` (
  `benutzer_id` binary(16) NOT NULL,
  `projekt_id` binary(16) NOT NULL,
  PRIMARY KEY (`benutzer_id`,`projekt_id`),
  KEY `FKddkuemol4y7w1cc7ognqt5wk1` (`projekt_id`),
  CONSTRAINT `FK1d8cypjols27p5vmgyobg7vjd` FOREIGN KEY (`benutzer_id`) REFERENCES `benutzer` (`id`),
  CONSTRAINT `FKddkuemol4y7w1cc7ognqt5wk1` FOREIGN KEY (`projekt_id`) REFERENCES `projekt` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projekt_mitglieder`
--

LOCK TABLES `projekt_mitglieder` WRITE;
/*!40000 ALTER TABLE `projekt_mitglieder` DISABLE KEYS */;
INSERT INTO `projekt_mitglieder` VALUES (_binary 'I\Î„E‡†KLú`}$,',_binary '\ÔbPJ=‰¼{Ï»Œ ‰'),(_binary 'N<b3pN\åº÷¶ˆÒŠõ°',_binary '\ÔbPJ=‰¼{Ï»Œ ‰'),(_binary '\rğu—\rBK„Ô¯ùš\ç',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç'),(_binary '3òyişNH²üÀzm\é‹]',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç'),(_binary 'zÏ¸a75Fï´›47\Ñ]ü]',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç'),(_binary 'µ¯{¥º\ÇMÊ’ºˆ\İB#',_binary '\Zş\Å\Ğô|C¹0Beõñ§\Ç'),(_binary 'zÏ¸a75Fï´›47\Ñ]ü]',_binary '\"ö$­4>D\n¢_\í.\É\0lT'),(_binary 'µ¯{¥º\ÇMÊ’ºˆ\İB#',_binary '\"ö$­4>D\n¢_\í.\É\0lT'),(_binary 'N<b3pN\åº÷¶ˆÒŠõ°',_binary '\'Ã´\Ô\á\ŞC»’\Ö\Üù\Zm2'),(_binary 'm¶\Ê¾9F­›·:¥B\â\Ó\É',_binary '\'Ã´\Ô\á\ŞC»’\Ö\Üù\Zm2'),(_binary 'KDóCB\"‹›Oc$€F³',_binary '\'Ã´\Ô\á\ŞC»’\Ö\Üù\Zm2'),(_binary 'N<b3pN\åº÷¶ˆÒŠõ°',_binary 'Q·ÕŸfV@o*e¶1|%9'),(_binary 'KDóCB\"‹›Oc$€F³',_binary 'Q·ÕŸfV@o*e¶1|%9'),(_binary 'ò{;bKÛ±ˆa¡•',_binary 'Q·ÕŸfV@o*e¶1|%9'),(_binary 'µ¯{¥º\ÇMÊ’ºˆ\İB#',_binary '^qzÿ\ïN/‘ğƒQò*K'),(_binary '3òyişNH²üÀzm\é‹]',_binary '˜qú-GD÷‚tDgµbKj'),(_binary 'zÏ¸a75Fï´›47\Ñ]ü]',_binary '˜qú-GD÷‚tDgµbKj'),(_binary 'µ¯{¥º\ÇMÊ’ºˆ\İB#',_binary '˜qú-GD÷‚tDgµbKj'),(_binary 'ù§ÁòB¨¥M¤¿‘\È(',_binary 'MIs´vC“­‡.ü+ÿ\É'),(_binary 'I\Î„E‡†KLú`}$,',_binary 'MIs´vC“­‡.ü+ÿ\É'),(_binary 'N<b3pN\åº÷¶ˆÒŠõ°',_binary 'MIs´vC“­‡.ü+ÿ\É'),(_binary 'ò{;bKÛ±ˆa¡•',_binary 'MIs´vC“­‡.ü+ÿ\É'),(_binary 'N<b3pN\åº÷¶ˆÒŠõ°',_binary 'ô$^\æ¯ôDO§a\ä\Ü3g©ù'),(_binary 'I\Î„E‡†KLú`}$,',_binary 'ö¡1r…SB³‚\\”\ê%h'),(_binary 'N<b3pN\åº÷¶ˆÒŠõ°',_binary 'ö¡1r…SB³‚\\”\ê%h'),(_binary '^À\Ü\âØªD°Ó´@vU–',_binary 'ö¡1r…SB³‚\\”\ê%h'),(_binary 'm¶\Ê¾9F­›·:¥B\â\Ó\É',_binary 'ö¡1r…SB³‚\\”\ê%h'),(_binary 'KDóCB\"‹›Oc$€F³',_binary 'ö¡1r…SB³‚\\”\ê%h');
/*!40000 ALTER TABLE `projekt_mitglieder` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-31 17:59:28
