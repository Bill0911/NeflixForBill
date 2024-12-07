
-- Database: `netflix`
--

-- --------------------------------------------------------

--
-- Table structure for table `episode`
--

CREATE TABLE `episode` (
  `episode_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` time DEFAULT '00:00:00',
  `series_id` int(11) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genreformovie`
--

CREATE TABLE `genreformovie` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genreforseries`
--

CREATE TABLE `genreforseries` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE `language` (
  `language_id` int(11) UNSIGNED NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `movie_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` time NOT NULL DEFAULT '00:00:00',
  `sd_available` bit(1) DEFAULT b'1',
  `hd_available` bit(1) DEFAULT b'0',
  `uhd_available` bit(1) DEFAULT b'0',
  `minimum_age` int(3) NOT NULL DEFAULT 12
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movieviewcount`
--

CREATE TABLE `movieviewcount` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `age` int(3) NOT NULL DEFAULT 0,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `series`
--

CREATE TABLE `series` (
  `series_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `minimum_age` int(3) NOT NULL DEFAULT 12
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seriesprofilewatchlist`
--

CREATE TABLE `seriesprofilewatchlist` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `payment_method` varchar(255) DEFAULT 'Credit Card',
  `blocked` bit(1) DEFAULT b'0',
  `subscription` enum('SD','HD','UHD') DEFAULT 'SD',
  `trial_start_date` datetime DEFAULT current_timestamp(),
  `language_id` int(11) UNSIGNED DEFAULT NULL,
  `role` enum('VIEWER','JUNIOR','MEDIOR','SENIOR') DEFAULT 'VIEWER',
  `failed_attempts` int(11) DEFAULT 0,
  `lock_time` datetime DEFAULT NULL,
  `discount` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `episode`
--
ALTER TABLE `episode`
  ADD PRIMARY KEY (`episode_id`),
  ADD KEY `FK_episode_series` (`series_id`);

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genre_id`);

--
-- Indexes for table `genreformovie`
--
ALTER TABLE `genreformovie`
  ADD PRIMARY KEY (`genre_id`,`movie_id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Indexes for table `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD PRIMARY KEY (`genre_id`,`series_id`),
  ADD KEY `series_id` (`series_id`);

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
-- Indexes for table `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD PRIMARY KEY (`account_id`,`movie_id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `series`
--
ALTER TABLE `series`
  ADD PRIMARY KEY (`series_id`);

--
-- Indexes for table `seriesprofilewatchlist`
--
ALTER TABLE `seriesprofilewatchlist`
  ADD PRIMARY KEY (`profile_id`,`series_id`),
  ADD KEY `series_id` (`series_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `language_id` (`language_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `episode`
--
ALTER TABLE `episode`
  MODIFY `episode_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `series`
--
ALTER TABLE `series`
  MODIFY `series_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `episode`
--
ALTER TABLE `episode`
  ADD CONSTRAINT `FK_episode_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `genreformovie`
--
ALTER TABLE `genreformovie`
  ADD CONSTRAINT `genreformovie_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `genreformovie_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD CONSTRAINT `genreforseries_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `genreforseries_ibfk_2` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD CONSTRAINT `movieviewcount_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movieviewcount_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `seriesprofilewatchlist`
--
ALTER TABLE `seriesprofilewatchlist`
  ADD CONSTRAINT `seriesprofilewatchlist_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `seriesprofilewatchlist_ibfk_2` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;