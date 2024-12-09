

CREATE TABLE `episode` (
  `episode_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` time DEFAULT '00:00:00',
  `series_id` int(11) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------



CREATE TABLE `genre` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `genre_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--

--
CREATE TABLE `genreforuser` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--

--

CREATE TABLE `genreformovie` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--

--

CREATE TABLE `genreforseries` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------



CREATE TABLE `language` (
  `language_id` int(11) UNSIGNED NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

--

CREATE TABLE `moviesprofilewatchlist` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------



CREATE TABLE `movieviewcount` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--

--

CREATE TABLE `profile` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `age` int(3) NOT NULL DEFAULT 0,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--

INSERT INTO `profile` (`profile_id`, `account_id`, `profile_image`, `age`, `name`) VALUES
(1, 2, 'pizdiets\'.png', 16, 'krutoy patsan'),
(2, 2, 'abcdefg\'.png', 12, 'tupoy loshara');

-- --------------------------------------------------------


--

CREATE TABLE `series` (
  `series_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `minimum_age` int(3) NOT NULL DEFAULT 12
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--

--

CREATE TABLE `seriesprofilewatchlist` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------


--

CREATE TABLE `seriesviewcount` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------



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
--

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `blocked`, `subscription`, `trial_start_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES
(1, 'fjodor.smorodins@gmail.com', '$2a$10$hszeHDUNOv4lnd24ZS9sOeOkOJUYo5zSi2H2makEPti1uznr4s5P2', 'abc', b'0', 'SD', '2024-12-07 14:32:59', 4, 'VIEWER', 0, NULL, b'0'),
(2, 'fjodorsm@gmail.com', '$2a$10$2QlecdJ25ELwT/avANQAUelbxtS9tysiRO5LSE0omLATaWhdAPfZC', 'Credit Card', b'0', 'SD', '2024-12-07 14:33:33', 1, 'VIEWER', 0, NULL, b'0');

--

--
-- Индексы таблицы `episode`
--
ALTER TABLE `episode`
  ADD PRIMARY KEY (`episode_id`),
  ADD KEY `FK_episode_series` (`series_id`);

--
-- Индексы таблицы `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genre_id`);


ALTER TABLE `genreforuser`
  ADD PRIMARY KEY (`genre_id`,`account_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Индексы таблицы `genreformovie`
--
ALTER TABLE `genreformovie`
  ADD PRIMARY KEY (`genre_id`,`movie_id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Индексы таблицы `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD PRIMARY KEY (`genre_id`,`series_id`),
  ADD KEY `series_id` (`series_id`);

--
-- Индексы таблицы `language`
--
ALTER TABLE `language`
  ADD PRIMARY KEY (`language_id`);

--
-- Индексы таблицы `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`movie_id`);

--
-- Индексы таблицы `moviesprofilewatchlist`
--
ALTER TABLE `moviesprofilewatchlist`
  ADD PRIMARY KEY (`profile_id`,`movie_id`),
  ADD KEY `FK_MoviesProfileWatchlist_Movie` (`movie_id`);

--
-- Индексы таблицы `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD PRIMARY KEY (`account_id`,`movie_id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Индексы таблицы `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Индексы таблицы `series`
--
ALTER TABLE `series`
  ADD PRIMARY KEY (`series_id`);

--
-- Индексы таблицы `seriesprofilewatchlist`
--
ALTER TABLE `seriesprofilewatchlist`
  ADD PRIMARY KEY (`profile_id`,`series_id`),
  ADD KEY `series_id` (`series_id`);

--
-- Индексы таблицы `seriesviewcount`
--
ALTER TABLE `seriesviewcount`
  ADD PRIMARY KEY (`account_id`,`series_id`),
  ADD KEY `FK_SeriesViewCount_Series` (`series_id`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `language_id` (`language_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `episode`
--
ALTER TABLE `episode`
  MODIFY `episode_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `series`
--
ALTER TABLE `series`
  MODIFY `series_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `episode`
--
ALTER TABLE `episode`
  ADD CONSTRAINT `FK_episode_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `genreforuser`
--

ALTER TABLE `genreforuser`
  ADD CONSTRAINT `genreforuser_genreid` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `genreforuser_accountid` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `genreformovie`
--

ALTER TABLE `genreformovie`
  ADD CONSTRAINT `genreformovie_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `genreformovie_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD CONSTRAINT `genreforseries_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `genreforseries_ibfk_2` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `moviesprofilewatchlist`
--
ALTER TABLE `moviesprofilewatchlist`
  ADD CONSTRAINT `FK_MoviesProfileWatchlist_Movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_MoviesProfileWatchlist_Profile` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD CONSTRAINT `movieviewcount_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movieviewcount_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `seriesprofilewatchlist`
--
ALTER TABLE `seriesprofilewatchlist`
  ADD CONSTRAINT `seriesprofilewatchlist_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `seriesprofilewatchlist_ibfk_2` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `seriesviewcount`
--
ALTER TABLE `seriesviewcount`
  ADD CONSTRAINT `FK_SeriesViewCount_Account` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_SeriesViewCount_Series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE SET NULL ON UPDATE CASCADE;
