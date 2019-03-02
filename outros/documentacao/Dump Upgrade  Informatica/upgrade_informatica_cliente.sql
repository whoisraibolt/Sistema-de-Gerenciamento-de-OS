-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: upgrade_informatica
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.25-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `CPFCliente` varchar(14) DEFAULT NULL,
  `RGCliente` varchar(12) DEFAULT NULL,
  `nomeCliente` varchar(100) NOT NULL,
  `dataNascimentoCliente` date NOT NULL,
  `sexoCliente` varchar(9) NOT NULL,
  `CEPCliente` char(9) NOT NULL,
  `enderecoCliente` varchar(100) NOT NULL,
  `bairroCliente` varchar(100) NOT NULL,
  `complementoCliente` varchar(100) DEFAULT NULL,
  `cidadeCliente` varchar(100) NOT NULL,
  `UFCliente` char(2) NOT NULL,
  `telefoneFixoCliente` varchar(14) NOT NULL,
  `telefoneCelularCliente` varchar(15) NOT NULL,
  `emailCliente` varchar(100) DEFAULT NULL,
  `skypeCliente` varchar(100) DEFAULT NULL,
  `observacoesCliente` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `UNIQUE_CPFCLIENTE` (`CPFCliente`),
  UNIQUE KEY `UNIQUE_RGCLIENTE` (`RGCliente`),
  UNIQUE KEY `UNIQUE_EMAILCLIENTE` (`emailCliente`),
  UNIQUE KEY `UNIQUE_SKYPECLIENTE` (`skypeCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-05 15:06:48
