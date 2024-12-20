-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2024 at 01:42 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12


--
-- Database: `netflix`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddMovieViewCount` (IN `p_movieId` INT, IN `p_accountId` INT)   BEGIN
    -- Check if the record exists in the movieviewcount table
    IF EXISTS (
        SELECT 1 
        FROM movieviewcount 
        WHERE account_id = p_accountId AND movie_id = p_movieId
    ) THEN
        -- If it exists, increment the view count
        UPDATE movieviewcount
        SET number = number + 1
        WHERE account_id = p_accountId AND movie_id = p_movieId;
    ELSE
        -- If it doesn't exist, create a new entry with initial count = 1
        INSERT INTO movieviewcount (`account_id`, `movie_id`, `number`)
        VALUES (p_accountId, p_movieId, 1);
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `AddSeriesViewCount` (IN `p_seriesId` INT, IN `p_accountId` INT)   BEGIN
    -- Check if the record exists in the seriesviewcount table
    IF EXISTS (
        SELECT 1 
        FROM seriesviewcount
        WHERE series_id = p_seriesId AND account_id = p_accountId
    ) THEN
        -- If it exists, increment the view count
        UPDATE seriesviewcount
        SET number = number + 1
        WHERE series_id = p_seriesId AND account_id = p_accountId;
    ELSE
        -- If it doesn't exist, create a new entry with initial count = 1
        INSERT INTO seriesviewcount (`account_id`, `series_id`, `number`)
        VALUES (p_accountId, p_seriesId, 1);
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetPersonalizedOffer` (IN `userId` INT, IN `maxMovies` INT)   BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE genreId INT;
    DECLARE genreViews INT;
    DECLARE genreLimit INT;
    DECLARE totalUserViews INT;

    DECLARE cur CURSOR FOR
    SELECT genre_id, total_views
    FROM user_genre_count
    WHERE user_id = userId;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- Step 1: Calculate total views across all genres for the user
    SELECT SUM(total_views) INTO totalUserViews
    FROM user_genre_count
    WHERE user_id = userId;

    -- Temporary table to store proportional movies for the user
    CREATE TEMPORARY TABLE TempPersonalizedOffer (
        movie_id INT,
        title VARCHAR(255)
    );

    -- Step 2: Loop through each genre
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO genreId, genreViews;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Calculate the proportional number of movies for this genre
        SET genreLimit = CEIL((genreViews * maxMovies) / totalUserViews);

        -- Fetch random movies for this genre, ensuring no duplicates
        INSERT INTO TempPersonalizedOffer (movie_id, title)
        SELECT m.movie_id, m.title 
        FROM movie m
        JOIN genreformovie gfm ON m.movie_id = gfm.movie_id
        WHERE gfm.genre_id = genreId
          AND NOT EXISTS (
            SELECT 1
            FROM TempPersonalizedOffer t
            WHERE t.movie_id = m.movie_id
        )
        ORDER BY RAND()
        LIMIT genreLimit;
    END LOOP;

    CLOSE cur;

    -- Step 3: Return the final result, limiting to maxMovies
    SELECT DISTINCT movie_id, title
    FROM TempPersonalizedOffer
    LIMIT maxMovies;

    -- Drop the temporary table
    DROP TEMPORARY TABLE TempPersonalizedOffer;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `email` varchar(255) NOT NULL,
  `subscription` varchar(10) NOT NULL,
  `trial_start_date` date NOT NULL,
  `discount` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `content`
--

CREATE TABLE `content` (
  `content_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) NOT NULL,
  `type` enum('MOVIE','SERIES') NOT NULL,
  `quality` enum('SD','HD','UHD') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `content_history`
--

CREATE TABLE `content_history` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `content_id` int(11) UNSIGNED NOT NULL,
  `viewed_at` datetime DEFAULT NULL,
  `paused_at` datetime DEFAULT NULL,
  `resumed_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `genre_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`genre_id`, `genre_name`) VALUES
(1, 'Horror'),
(2, 'Comedy'),
(3, 'Romance'),
(4, 'Melodrama'),
(5, 'Tearjerker'),
(6, 'Pg-13'),
(7, 'fantasy'),
(8, 'sci-fi');

-- --------------------------------------------------------

--
-- Table structure for table `genreformovie`
--

CREATE TABLE `genreformovie` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `genreformovie`
--

INSERT INTO `genreformovie` (`genre_id`, `movie_id`) VALUES
(2, 1),
(3, 3),
(5, 1),
(7, 2),
(8, 3);

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
-- Table structure for table `genreforuser`
--

CREATE TABLE `genreforuser` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE `language` (
  `language_id` int(11) UNSIGNED NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `language`
--

INSERT INTO `language` (`language_id`, `name`) VALUES
(1, 'english'),
(2, 'dutch'),
(3, 'latvian'),
(4, 'russian'),
(5, 'spanish'),
(6, 'ukranian');

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

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`movie_id`, `title`, `duration`, `sd_available`, `hd_available`, `uhd_available`, `minimum_age`) VALUES
(1, 'wompwomp funny', '01:25:27', b'1', b'1', b'0', 14),
(2, 'lordoftherings', '03:30:52', b'1', b'1', b'0', 12),
(3, 'star wars', '00:31:16', b'1', b'1', b'0', 12);

-- --------------------------------------------------------

--
-- Table structure for table `moviesprofilewatchlist`
--

CREATE TABLE `moviesprofilewatchlist` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL
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

--
-- Dumping data for table `movieviewcount`
--

INSERT INTO `movieviewcount` (`account_id`, `movie_id`, `number`) VALUES
(1, 1, 2),
(1, 2, 2),
(1, 3, 1),
(3, 1, 1);

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

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`profile_id`, `account_id`, `profile_image`, `age`, `name`) VALUES
(1, 2, 'pizdiets.png', 16, 'krutoy patsan'),
(2, 2, 'abcdefg.png', 12, 'tupoy loshara');

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
-- Table structure for table `seriesviewcount`
--

CREATE TABLE `seriesviewcount` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL,
  `episode_id` int(11) UNSIGNED NOT NULL,
  `number` int(11) DEFAULT NULL,
  `last_viewed` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `subscriptioncosts`
-- (See below for the actual view)
--
CREATE TABLE `subscriptioncosts` (
`UserID` int(11) unsigned
,`Email` varchar(255)
,`SubscriptionType` enum('SD','HD','UHD')
,`SubscriptionCost` int(3)
);

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
  `discount` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `blocked`, `subscription`, `trial_start_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES
(1, 'fjodor.smorodins@gmail.com', '$2a$10$hszeHDUNOv4lnd24ZS9sOeOkOJUYo5zSi2H2makEPti1uznr4s5P2', 'abc', b'0', 'SD', '2024-12-07 14:32:59', 4, 'VIEWER', 0, NULL, 0),
(2, 'fjodorsm@gmail.com', '$2a$10$2QlecdJ25ELwT/avANQAUelbxtS9tysiRO5LSE0omLATaWhdAPfZC', 'Credit Card', b'0', 'SD', '2024-12-07 14:33:33', 1, 'VIEWER', 0, NULL, 0),
(3, 'smorodins@gmail.com', '$2a$10$KhhGnFeK2q32DYG7/fMhNe/GEzf1dDJVkQqq5isK1vwuIO9h0zor.', 'CrC', b'0', 'SD', '2024-12-16 18:54:30', 3, 'VIEWER', 0, NULL, 0),
(4, 'fjodor@gg.com', '$2a$10$2a1DrTOPXSoPBp7fmVBSx.EfUawn.8oW1oondBTX6idrvmtXzMe7W', 'cash', b'0', 'SD', '2024-12-20 11:13:32', 1, 'VIEWER', 0, NULL, 0);

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_genre_count`
-- (See below for the actual view)
--
CREATE TABLE `user_genre_count` (
`user_id` int(11) unsigned
,`genre_id` int(11) unsigned
,`genre_name` varchar(255)
,`total_views` decimal(32,0)
);

-- --------------------------------------------------------

--
-- Structure for view `subscriptioncosts`
--
DROP TABLE IF EXISTS `subscriptioncosts`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `subscriptioncosts`  AS SELECT `u`.`account_id` AS `UserID`, `u`.`email` AS `Email`, `u`.`subscription` AS `SubscriptionType`, CASE WHEN to_days(curdate()) - to_days(`u`.`trial_start_date`) <= 7 THEN 0 ELSE CASE WHEN `u`.`subscription` = 'SD' THEN 10 WHEN `u`.`subscription` = 'HD' THEN 15 WHEN `u`.`subscription` = 'UHD' THEN 20 ELSE 0 END- CASE WHEN `u`.`discount` = 1 THEN 2 ELSE 0 END END AS `SubscriptionCost` FROM `user` AS `u` ;

-- --------------------------------------------------------

--
-- Structure for view `user_genre_count`
--
DROP TABLE IF EXISTS `user_genre_count`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_genre_count`  AS SELECT `mvc`.`account_id` AS `user_id`, `g`.`genre_id` AS `genre_id`, `g`.`genre_name` AS `genre_name`, sum(`mvc`.`number`) AS `total_views` FROM (((`movieviewcount` `mvc` join `movie` `m` on(`mvc`.`movie_id` = `m`.`movie_id`)) join `genreformovie` `mg` on(`m`.`movie_id` = `mg`.`movie_id`)) join `genre` `g` on(`mg`.`genre_id` = `g`.`genre_id`)) GROUP BY `mvc`.`account_id`, `g`.`genre_id` ORDER BY `mvc`.`account_id` ASC, sum(`mvc`.`number`) DESC ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`);

--
-- Indexes for table `content`
--
ALTER TABLE `content`
  ADD PRIMARY KEY (`content_id`);

--
-- Indexes for table `content_history`
--
ALTER TABLE `content_history`
  ADD PRIMARY KEY (`account_id`,`content_id`),
  ADD KEY `content_id` (`content_id`);

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
-- Indexes for table `genreforuser`
--
ALTER TABLE `genreforuser`
  ADD PRIMARY KEY (`genre_id`,`account_id`),
  ADD KEY `account_id` (`account_id`);

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
  ADD PRIMARY KEY (`profile_id`,`movie_id`),
  ADD KEY `FK_MoviesProfileWatchlist_Movie` (`movie_id`);

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
-- Indexes for table `seriesviewcount`
--
ALTER TABLE `seriesviewcount`
  ADD PRIMARY KEY (`account_id`,`series_id`),
  ADD KEY `FK_SeriesViewCount_Series` (`series_id`),
  ADD KEY `FKeh1b2xgu8esqripye7l4o90rq` (`episode_id`);

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
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `content`
--
ALTER TABLE `content`
  MODIFY `content_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `episode`
--
ALTER TABLE `episode`
  MODIFY `episode_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `content_history`
--
ALTER TABLE `content_history`
  ADD CONSTRAINT `content_history_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `content_history_ibfk_2` FOREIGN KEY (`content_id`) REFERENCES `content` (`content_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `episode`
--
ALTER TABLE `episode`
  ADD CONSTRAINT `FK6hj3n90dblxp7xd9fc1urj8ko` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`);

--
-- Constraints for table `genreformovie`
--
ALTER TABLE `genreformovie`
  ADD CONSTRAINT `FK9k0bup58wyigpu52ap88luwm3` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`),
  ADD CONSTRAINT `FKcooxhml1jwh4bnp2o9n63ka3k` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`);

--
-- Constraints for table `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD CONSTRAINT `FK46gf3ps13sggr3a79227kltsn` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`),
  ADD CONSTRAINT `FKn3exg02r09uca4mdojqte8dly` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`);

--
-- Constraints for table `genreforuser`
--
ALTER TABLE `genreforuser`
  ADD CONSTRAINT `FK1aj6m4bg610vtat73mcux6y8c` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`),
  ADD CONSTRAINT `FKrh2w4n7xtka8f2svniqwoghq1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`);

--
-- Constraints for table `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD CONSTRAINT `FK960o3778b43sfm6eh63p8yaaj` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  ADD CONSTRAINT `FKcpuurwpcxsr5pqfikypjvl3qn` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`);

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `FKorsmm7ow2nvslj85y0bs2xii4` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`);

--
-- Constraints for table `seriesviewcount`
--
ALTER TABLE `seriesviewcount`
  ADD CONSTRAINT `FK4c7s48mkp8ns6sgu7f6tb2jxo` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`),
  ADD CONSTRAINT `FKeh1b2xgu8esqripye7l4o90rq` FOREIGN KEY (`episode_id`) REFERENCES `episode` (`episode_id`),
  ADD CONSTRAINT `FKmqydqyoqjwehbedxfhl2htgxk` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKj9k2portqypgs993xn20pywtr` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`);
