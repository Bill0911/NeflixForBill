
--
-- Структура таблицы `episode`
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
-- Структура таблицы `genre`
--

CREATE TABLE `genre` (
  `genre_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `genreaccount`
--

CREATE TABLE `genreaccount` (
  `account_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `genreformovie`
--

CREATE TABLE `genreformovie` (
  `genre_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `genreforseries`
--

CREATE TABLE `genreforseries` (
  `genre_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `language`
--

CREATE TABLE `language` (
  `language_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `language`
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
-- Структура таблицы `movie`
--

CREATE TABLE `movie` (
  `movie_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` time NOT NULL DEFAULT '00:00:00',
  `sd_available` bit(1) DEFAULT b'1',
  `hd_available` bit(1) DEFAULT b'0',
  `uhd_available` bit(1) DEFAULT b'0',
  `minimum_age` int(3) NOT NULL DEFAULT 12
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `movie`
--

INSERT INTO `movie` (`movie_id`, `title`, `duration`, `sd_available`, `hd_available`, `uhd_available`, `minimum_age`) VALUES
(1, 'Perdyaschiy malchik', '01:30:25', b'1', b'0', b'0', 6),
(2, 'Porn', '01:12:05', b'1', b'0', b'0', 18),
(3, 'Chtoto neponyatnoe', '02:23:05', b'1', b'0', b'0', 15);

-- --------------------------------------------------------

--
-- Структура таблицы `moviesprofilewatchlist`
--

CREATE TABLE `moviesprofilewatchlist` (
  `profile_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `movieviewcount`
--

CREATE TABLE `movieviewcount` (
  `account_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `movieviewcount`
--

INSERT INTO `movieviewcount` (`account_id`, `movie_id`, `number`) VALUES
(5, 1, 2),
(5, 3, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `age` int(3) NOT NULL DEFAULT 0,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `profile`
--

INSERT INTO `profile` (`profile_id`, `account_id`, `profile_image`, `age`, `name`) VALUES
(17, 4, 'lublyu_srat\'.png', 6, NULL),
(18, 4, 'dyadya_zhopa\'.png', 6, 'abcdefgqwerty'),
(19, 5, 'qwerty\'.png', 13, 'stupidboy'),
(20, 5, 'pizdiec\'.png', 17, 'krutoy huyesos');

-- --------------------------------------------------------

--
-- Структура таблицы `series`
--

CREATE TABLE `series` (
  `series_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `minimum_age` int(3) NOT NULL DEFAULT 12
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `seriesprofilewatchlist`
--

CREATE TABLE `seriesprofilewatchlist` (
  `account_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `seriesviewcount`
--

CREATE TABLE `seriesviewcount` (
  `account_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `subtitleformovie`
--

CREATE TABLE `subtitleformovie` (
  `language_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `subtitleforseries`
--

CREATE TABLE `subtitleforseries` (
  `language_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `user`
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
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `blocked`, `subscription`, `trial_start_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES
(3, 'fjodor.smorodins@gg.com', '$2a$10$58JKWr8f48delHpsHW/DSece6c3N5ZyjXdsnRwJnVTJ3iu.SfdTB2', 'CC', b'0', 'SD', '2024-12-03 15:25:53', 1, 'VIEWER', 0, NULL, b'0'),
(4, 'zhi.nasral.v.shtani@gmail.com', '$2a$10$E0hWca4/I7i2LaL4A34lvu40/g.9rsVM/nqti2v/DMrVZFFrPNw.a', 'abc', b'0', 'HD', '2024-12-03 18:55:44', 4, 'VIEWER', 0, NULL, b'0'),
(5, 'fjodor.smorodins@gmail.com', '$2a$10$xCXwS/hWMI0qiTub3jC4VOUkgY5OkMo/rx/SxsGBIRiTOayXYZ5y2', 'abc', b'0', 'HD', '2024-12-04 15:33:43', 1, 'VIEWER', 0, NULL, b'0');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `episode`
--
ALTER TABLE `episode`
  ADD PRIMARY KEY (`episode_id`);

--
-- Индексы таблицы `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genre_id`);

--
-- Индексы таблицы `genreaccount`
--
ALTER TABLE `genreaccount`
  ADD PRIMARY KEY (`account_id`,`genre_id`);

--
-- Индексы таблицы `genreformovie`
--
ALTER TABLE `genreformovie`
  ADD PRIMARY KEY (`genre_id`,`movie_id`);

--
-- Индексы таблицы `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD PRIMARY KEY (`genre_id`,`series_id`);

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
  ADD PRIMARY KEY (`profile_id`,`movie_id`);

--
-- Индексы таблицы `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD PRIMARY KEY (`account_id`,`movie_id`),
  ADD KEY `FK960o3778b43sfm6eh63p8yaaj` (`movie_id`);

--
-- Индексы таблицы `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`),
  ADD KEY `FKorsmm7ow2nvslj85y0bs2xii4` (`account_id`);

--
-- Индексы таблицы `series`
--
ALTER TABLE `series`
  ADD PRIMARY KEY (`series_id`);

--
-- Индексы таблицы `seriesprofilewatchlist`
--
ALTER TABLE `seriesprofilewatchlist`
  ADD PRIMARY KEY (`account_id`,`series_id`);

--
-- Индексы таблицы `seriesviewcount`
--
ALTER TABLE `seriesviewcount`
  ADD PRIMARY KEY (`account_id`,`series_id`);

--
-- Индексы таблицы `subtitleformovie`
--
ALTER TABLE `subtitleformovie`
  ADD PRIMARY KEY (`language_id`,`movie_id`);

--
-- Индексы таблицы `subtitleforseries`
--
ALTER TABLE `subtitleforseries`
  ADD PRIMARY KEY (`language_id`,`series_id`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `FKj9k2portqypgs993xn20pywtr` (`language_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `episode`
--
ALTER TABLE `episode`
  MODIFY `episode_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT для таблицы `series`
--
ALTER TABLE `series`
  MODIFY `series_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD CONSTRAINT `FK_MovieViewCount_Account` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_MovieViewCount_Movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `FKorsmm7ow2nvslj85y0bs2xii4` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`);

--
-- Ограничения внешнего ключа таблицы `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKj9k2portqypgs993xn20pywtr` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`);