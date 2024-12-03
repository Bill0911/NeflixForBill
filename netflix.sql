
<<<<<<< HEAD
=======
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
--
-- Database: `netflix`
>>>>>>> 246e22be1471d7e7490c4cce9aa42153c91af555
--

-- --------------------------------------------------------

--
-- Table structure for table `episode`
--

USE `netflix`;

CREATE TABLE `episode` (
  `episode_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` time DEFAULT '00:00:00',
  `series_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `genre_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genreaccount`
--

CREATE TABLE `genreaccount` (
  `account_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genreformovie`
--

CREATE TABLE `genreformovie` (
  `genre_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genreforseries`
--

CREATE TABLE `genreforseries` (
  `genre_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE `language` (
  `language_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `language`
--

INSERT INTO `language` (`language_id`, `name`) VALUES
(1, 'en'),
(2, 'nl'),
(3, 'lv'),
(4, 'ru'),
(5, 'es'),
(6, 'ua');

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `movie_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` varchar(255) NOT NULL,
  `sd_available` bit(1) DEFAULT NULL,
  `hd_available` bit(1) DEFAULT NULL,
  `uhd_available` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `moviesprofilewatchlist`
--

CREATE TABLE `moviesprofilewatchlist` (
  `profile_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movieviewcount`
--

CREATE TABLE `movieviewcount` (
  `account_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movie_view_count`
--

CREATE TABLE `movie_view_count` (
  `number` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `profile_child` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `series`
--

CREATE TABLE `series` (
  `series_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `age_range` enum('6','9','12','16','16+') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seriesprofilewatchlist`
--

CREATE TABLE `seriesprofilewatchlist` (
  `account_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seriesviewcount`
--

CREATE TABLE `seriesviewcount` (
  `account_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subtitleformovie`
--

CREATE TABLE `subtitleformovie` (
  `language_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subtitleforseries`
--

CREATE TABLE `subtitleforseries` (
  `language_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `account_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `payment_method` varchar(255) DEFAULT 'Credit Card',
  `blocked` bit(1) DEFAULT b'0',
  `subscription` enum('SD','HD','UHD') DEFAULT 'SD',
  `trial_start_date` datetime DEFAULT current_timestamp(),
  `language_id` int(11) DEFAULT NULL,
  `role` enum('VIEWER','JUNIOR','MEDIOR','SENIOR') DEFAULT 'VIEWER',
  `failed_attempts` int(11) DEFAULT 0,
  `lock_time` datetime DEFAULT NULL,
  `discount` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `blocked`, `subscription`, `trial_start_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES
(3, 'fjodor.smorodins@gg.com', '$2a$10$58JKWr8f48delHpsHW/DSece6c3N5ZyjXdsnRwJnVTJ3iu.SfdTB2', 'CC', b'0', 'SD', '2024-12-03 15:25:53', 1, 'VIEWER', 0, NULL, b'0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `episode`
--
ALTER TABLE `episode`
  ADD PRIMARY KEY (`episode_id`);

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genre_id`);

--
-- Indexes for table `genreaccount`
--
ALTER TABLE `genreaccount`
  ADD PRIMARY KEY (`account_id`,`genre_id`);

--
-- Indexes for table `genreformovie`
--
ALTER TABLE `genreformovie`
  ADD PRIMARY KEY (`genre_id`,`movie_id`);

--
-- Indexes for table `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD PRIMARY KEY (`genre_id`,`series_id`);

--
-- Indexes for table `language`
--
ALTER TABLE `language`
  ADD PRIMARY KEY (`language_id`);

--
-- Indexes for table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`movie_id`);

--
-- Indexes for table `moviesprofilewatchlist`
--
ALTER TABLE `moviesprofilewatchlist`
  ADD PRIMARY KEY (`profile_id`,`movie_id`);

--
-- Indexes for table `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD PRIMARY KEY (`account_id`,`movie_id`),
  ADD KEY `FK960o3778b43sfm6eh63p8yaaj` (`movie_id`);

--
-- Indexes for table `movie_view_count`
--
ALTER TABLE `movie_view_count`
  ADD PRIMARY KEY (`movie_id`,`account_id`),
  ADD KEY `FKeuppqu23hf24imh2ijutas38v` (`account_id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`);

--
-- Indexes for table `series`
--
ALTER TABLE `series`
  ADD PRIMARY KEY (`series_id`);

--
-- Indexes for table `seriesprofilewatchlist`
--
ALTER TABLE `seriesprofilewatchlist`
  ADD PRIMARY KEY (`account_id`,`series_id`);

--
-- Indexes for table `seriesviewcount`
--
ALTER TABLE `seriesviewcount`
  ADD PRIMARY KEY (`account_id`,`series_id`);

--
-- Indexes for table `subtitleformovie`
--
ALTER TABLE `subtitleformovie`
  ADD PRIMARY KEY (`language_id`,`movie_id`);

--
-- Indexes for table `subtitleforseries`
--
ALTER TABLE `subtitleforseries`
  ADD PRIMARY KEY (`language_id`,`series_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `FKj9k2portqypgs993xn20pywtr` (`language_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `episode`
--
ALTER TABLE `episode`
  MODIFY `episode_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `series`
--
ALTER TABLE `series`
  MODIFY `series_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`

  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD CONSTRAINT `FK960o3778b43sfm6eh63p8yaaj` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  ADD CONSTRAINT `FKcpuurwpcxsr5pqfikypjvl3qn` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`);

--
-- Constraints for table `movie_view_count`
--
ALTER TABLE `movie_view_count`
  ADD CONSTRAINT `FKeuppqu23hf24imh2ijutas38v` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`),
  ADD CONSTRAINT `FKqe3n17lnq55qyrw79fjk77wln` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKj9k2portqypgs993xn20pywtr` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`);
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT;
