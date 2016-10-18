-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2016. Jún 27. 00:38
-- Kiszolgáló verziója: 5.6.24
-- PHP verzió: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Adatbázis: `points`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `points`
--

CREATE TABLE IF NOT EXISTS `points` (
  `id` int(10) unsigned NOT NULL,
  `id_parent` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `point` double DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

--
-- A tábla adatainak kiíratása `points`
--

INSERT INTO `points` (`id`, `id_parent`, `name`, `point`) VALUES
(1, 9, '1id', 2),
(3, 1, '3id', 5),
(4, 5, '4id', 3),
(5, 6, '5Id', 3),
(6, 9, '6id', 0),
(7, 8, '7id', 2),
(8, 5, 'nyolc', 0),
(9, 0, '9id', 3),
(10, 8, 'tizes', 4.2),
(11, 6, '11id', 4),
(12, 9, '12', 4),
(14, 6, '13', 5),
(16, 1, '16', 3),
(17, 6, 'asd', 3),
(18, 5, 'wsa', 3.4),
(19, 8, '18', 3),
(20, 8, 'qwes', 3),
(21, 17, 'qwer', 4);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `points`
--
ALTER TABLE `points`
  ADD PRIMARY KEY (`id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `points`
--
ALTER TABLE `points`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
