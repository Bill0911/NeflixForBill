
--
--  `netflix`
--

-- --------------------------------------------------------

--
--  `language`
--

CREATE TABLE `language` (
  `language_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`language_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- `language`
--

INSERT INTO `language` (`language_id`, `name`) VALUES
(1, 'eng'),
(2, 'nl'),
(3, 'lv');

-- --------------------------------------------------------

--
--  `movie`
--

CREATE TABLE `movie` (
  `movie_id` int(11) PRIMARY KEY NOT NULL,
  `title` varchar(255) NOT NULL,
  `duration` TIME DEFAULT '00:00:00',
  `sd_available` bit(1),
  `hd_available` bit(1),
  `uhd_available` bit(1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
--  `movie`
--

INSERT INTO `movie` (`movie_id`, `title`, `duration`, `age_range`) VALUES
(1, 'dsfkghsdkgj', '01:23:00', '12'),
(2, 'batman', '02:13:00', '12'),
(3, 'starwars', '02:30:00', '12');

-- --------------------------------------------------------

--
-- `movieviewcount`
--

CREATE TABLE `movieviewcount` (
  `account_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `number` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`account_id`, `movie_id`),
  FOREIGN KEY (`account_id`) REFERENCES `user`(`account_id`),
  FOREIGN KEY (`movie_id`) REFERENCES `movie`(`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- `movieviewcount`
--

INSERT INTO `movieviewcount` (`account_id`, `movie_id`, `number`) VALUES
(3, 1, 5),
(3, 2, 6),
(1, 3, 1);

-- --------------------------------------------------------

--
-- `user`
--

CREATE TABLE `user` (
  `account_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `blocked` bit(1) NOT NULL,
  `subscription` enum('SD','HD','UHD') NOT NULL,
  `trial_start_date` datetime NOT NULL,
  `language_id` int(11) NOT NULL,
  `role` enum('VIEWER','JUNIOR','MEDIOR','SENIOR') NOT NULL,
  `failed_attempts` int(11) NOT NULL DEFAULT 0,
  `lock_time` datetime DEFAULT NULL,
  `discount` bit(1),
  PRIMARY KEY (`account_id`),
  FOREIGN KEY (`language_id`) REFERENCES `language`(`language_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- `user`
--

INSERT INTO `user` (`account_id`, `email`, `payment_method`, `blocked`, `subscription`, `trial_start_date`, `language_id`, `role`, `password`, `failed_attempts`, `lock_time`) VALUES
(1, 'testuser@example.com', 'Credit Card', 0, 'HD', '2024-12-01 19:59:05', 1, 'VIEWER', '$2a$10$NqV0coTNtn.xlAUNjsgoEeQpwWfsO6oY1N6vzvgR4jQsmXHz0OQvm', 0, NULL),
(2, 'testuser@example.com', 'Credit Card', 0, 'HD', '2024-12-01 20:04:03', 1, 'VIEWER', '$2a$10$g4wVMjf7w62uYTE1wNimf.lSDVxIu/KaCkoLb/A5pmzALRrs/i8m2', 0, NULL),
(3, 'fjodorsm@gmail.com', 'Credit Card', 0, 'HD', '2024-12-01 20:04:44', 3, 'VIEWER', '$2a$10$kCd6DisqZc3AJxmc7FMctO2BUd5Rt.6ou5oeVbApGZVwv22.BppYq', 0, NULL),
(4, 'yarik@gmail.com', '123', 1, 'HD', '2024-12-03 00:11:11', 1, 'VIEWER', '$2a$10$2M029yo3azaltXc9wa5/feTey1M8gh8Tf3JNnBaaaNIJqtsdKKu2u', 3, NULL),
(5, 'zhora@gmail.com', 'cash', 0, 'HD', '2024-12-03 00:12:48', 2, 'VIEWER', '$2a$10$/1PUQ1OdRl7OA3M0f.YtUuC/dI/oT/71IiShvQia1kIOGRPvj1rXu', 0, NULL);

--
-- Save table indexes
--

--
--  `language`
--

--
-- `series profile watchlist`
--

CREATE TABLE `seriesProfileWatchList` (
  `account_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`, `series_id`),
  FOREIGN KEY (`account_id`) REFERENCES `user`(`account_id`),
  FOREIGN KEY (`series_id`) REFERENCES `series`(`series_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `seriesProfileWatchList` (`account_id`, `series_id`)
 VALUES (1, 2);

--
-- `movies profile watchlist`
--

CREATE TABLE `moviesProfileWatchList` (
  `profile_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  PRIMARY KEY (`profile_id`, `movie_id`),
  FOREIGN KEY (`profile_id`) REFERENCES `user`(`account_id`),
  FOREIGN KEY (`movie_id`) REFERENCES `movie`(`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `moviesProfileWatchList` (`profile_id`, `movie_id`) 
 VALUES (1, 2);

--
--
--

--
-- `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) NOT NULL PRIMARY KEY,
  `account_id` int(11) NOT NULL,
  `profile_image` VARCHAR(255),
  `profile_child` bit(1),
  FOREIGN KEY (`account_id`) REFERENCES `user`(`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- genre account
--

CREATE TABLE `genreAccount` (
  `account_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`, `genre_id`),
  FOREIGN KEY (`account_id`) REFERENCES `user`(`account_id`),
  FOREIGN KEY (`genre_id`) REFERENCES `genre`(`genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `genreAccount` (`account_id`, `genre_id`) 
VALUES (1, 2);

--
-- genre
--

CREATE TABLE `genre` (
  `genre_id` int(11) NOT NULL PRIMARY KEY,
  `name` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `genre` (`genre_id`, `name`) 
VALUES (1, 'Horror');

INSERT INTO `genre` (`genre_id`, `name`)
VALUES (2, 'Action');

--
-- genre for movies
--

CREATE TABLE `genreForMovie`(
  `genre_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  PRIMARY KEY (`genre_id`, `movie_id`),
  FOREIGN KEY (`genre_id`) REFERENCES `genre`(`genre_id`),
  FOREIGN KEY (`movie_id`) REFERENCES `movie`(`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `genreForMovie` (`genre_id`, `movie_id`) 
VALUES (1, 2);

--
-- series
--

CREATE TABLE `series`(
  `series_id` int(11) NOT NULL PRIMARY KEY,
  `title` VARCHAR(255) NOT NULL,
  `age_range` enum('6', '9', '12', '16+', '18+')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `series`(
  `series_id`, `title`, `age_range`
) VALUES (1, 'The exorcism', '16+');

--
-- episode
--

CREATE TABLE `episode` (
  `episode_id` int(11) NOT NULL PRIMARY KEY,
  `title` VARCHAR(255),
  `duration` TIME DEFAULT '00:00:00',
  `series_id` int(11) NOT NULL,
  FOREIGN KEY (`series_id`) REFERENCES `series`(`series_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `episode` (
  `episode_id`, `title`, `duration`, `series_id`
) VALUES (1, 'The exorcism', '00:20:12', 2);

--
-- series view count
--

CREATE TABLE `seriesViewCount` (
  `account_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL,
  `number` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`account_id`, `series_id`),
  FOREIGN KEY (`account_id`) REFERENCES `user`(`account_id`),
  FOREIGN KEY (`series_id`) REFERENCES `series`(`series_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `seriesViewCount` (
  `account_id`, `series_id`, `number`
) VALUES (1, 2, 1);

--
-- subtitle for movies
--

CREATE TABLE `subtitleForMovies` (
  `language_id` int(11) NOT NULL, 
  `movie_id` int(11) NOT NULL,
  PRIMARY KEY (`language_id`, `movie_id`),
  FOREIGN KEY (`language_id`) REFERENCES `language`(`language_id`),
  FOREIGN KEY (`movie_id`) REFERENCES `movie`(`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `subtitleForMovies` (
  `language_id`, `movie_id`
) VALUES (1, 2);

--
--
--

--
-- subsitle for series 
--

CREATE TABLE `subtitleForSeries` (
  `language_id` int(11) NOT NULL, 
  `series_id` int(11) NOT NULL,
  PRIMARY KEY (`language_id`, `series_id`),
  FOREIGN KEY (`language_id`) REFERENCES `language`(`language_id`),
  FOREIGN KEY (`series_id`) REFERENCES `series`(`series_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `subtitleForSeries` (
  `language_id`, `series_id`
) VALUES (1, 2);

--
-- AUTO_INCREMENT for saved tables
--

--
-- AUTO_INCREMENT `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Foreign key constraints on stored tables
--

--
-- Table Foreign Key Constraints `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD CONSTRAINT `movieviewcount_movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movieviewcount_user` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Table Foreign Key Constraints `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `language_of_user` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE CASCADE ON UPDATE CASCADE;
