-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Дек 17 2024 г., 00:00
-- Версия сервера: 10.4.32-MariaDB
-- Версия PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


--
-- База данных: `netflix`
--

DELIMITER $$
--
-- Процедуры
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

DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `account`
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
-- Структура таблицы `episode`
--

CREATE TABLE `episode` (
  `episode_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` time DEFAULT '00:00:00',
  `series_id` int(11) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `genre`
--

CREATE TABLE `genre` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `genre_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `genre`
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
-- Структура таблицы `genreformovie`
--

CREATE TABLE `genreformovie` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `genreformovie`
--

INSERT INTO `genreformovie` (`genre_id`, `movie_id`) VALUES
(2, 1),
(3, 3),
(5, 1),
(7, 2),
(8, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `genreforseries`
--

CREATE TABLE `genreforseries` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `genreforuser`
--

CREATE TABLE `genreforuser` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `language`
--

CREATE TABLE `language` (
  `language_id` int(11) UNSIGNED NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `language`
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
-- Структура таблицы `movie`
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
-- Дамп данных таблицы `movie`
--

INSERT INTO `movie` (`movie_id`, `title`, `duration`, `sd_available`, `hd_available`, `uhd_available`, `minimum_age`) VALUES
(1, 'wompwomp funny', '01:25:27', b'1', b'1', b'0', 14),
(2, 'lordoftherings', '03:30:52', b'1', b'1', b'0', 12),
(3, 'star wars', '00:31:16', b'1', b'1', b'0', 12);

-- --------------------------------------------------------

--
-- Структура таблицы `moviesprofilewatchlist`
--

CREATE TABLE `moviesprofilewatchlist` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `movieviewcount`
--

CREATE TABLE `movieviewcount` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `movieviewcount`
--

INSERT INTO `movieviewcount` (`account_id`, `movie_id`, `number`) VALUES
(1, 1, 2),
(1, 2, 2),
(1, 3, 1),
(3, 1, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `age` int(3) NOT NULL DEFAULT 0,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `profile`
--

INSERT INTO `profile` (`profile_id`, `account_id`, `profile_image`, `age`, `name`) VALUES
(1, 2, 'pizdiets.png', 16, 'krutoy patsan'),
(2, 2, 'abcdefg.png', 12, 'tupoy loshara');

-- --------------------------------------------------------

--
-- Структура таблицы `series`
--

CREATE TABLE `series` (
  `series_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `minimum_age` int(3) NOT NULL DEFAULT 12
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `seriesprofilewatchlist`
--

CREATE TABLE `seriesprofilewatchlist` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `seriesviewcount`
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
-- Дублирующая структура для представления `subscriptioncosts`
-- (См. Ниже фактическое представление)
--
CREATE TABLE `subscriptioncosts` (
`UserID` int(11) unsigned
,`Email` varchar(255)
,`SubscriptionType` enum('SD','HD','UHD')
,`SubscriptionCost` int(3)
);

-- --------------------------------------------------------

--
-- Структура таблицы `user`
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
  `discount` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`account_id`),
  KEY `language_id` (`language_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `blocked`, `subscription`, `trial_start_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES
(1, 'fjodor.smorodins@gmail.com', '$2a$10$hszeHDUNOv4lnd24ZS9sOeOkOJUYo5zSi2H2makEPti1uznr4s5P2', 'abc', b'0', 'SD', '2024-12-07 14:32:59', 4, 'VIEWER', 0, NULL, 0),
(2, 'fjodorsm@gmail.com', '$2a$10$2QlecdJ25ELwT/avANQAUelbxtS9tysiRO5LSE0omLATaWhdAPfZC', 'Credit Card', b'0', 'SD', '2024-12-07 14:33:33', 1, 'VIEWER', 0, NULL, 0),
(3, 'smorodins@gmail.com', '$2a$10$KhhGnFeK2q32DYG7/fMhNe/GEzf1dDJVkQqq5isK1vwuIO9h0zor.', 'CrC', b'0', 'SD', '2024-12-16 18:54:30', 3, 'VIEWER', 0, NULL, 0);

-- --------------------------------------------------------

--
-- Дублирующая структура для представления `user_genre_count`
-- (См. Ниже фактическое представление)
--
CREATE TABLE `user_genre_count` (
`user_id` int(11) unsigned
,`genre_id` int(11) unsigned
,`total_views` decimal(32,0)
);

--- ----------------------------------------------------

CREATE TABLE content (
    content_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    type ENUM('MOVIE', 'SERIES') NOT NULL,
    quality ENUM('SD', 'HD', 'UHD') NOT NULL,
    PRIMARY KEY (content_id)
);

-- --------------------------------------------------------

CREATE TABLE content_history (
    account_id INT(11) UNSIGNED NOT NULL,
    content_id INT(11) UNSIGNED NOT NULL,
    viewed_at DATETIME DEFAULT NULL,
    paused_at DATETIME DEFAULT NULL,
    resumed_at DATETIME DEFAULT NULL,
    PRIMARY KEY (account_id, content_id),
    FOREIGN KEY (account_id) REFERENCES user(account_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (content_id) REFERENCES content(content_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- --------------------------------------------------------

DROP TABLE IF EXISTS `subscriptioncosts`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `subscriptioncosts`  AS SELECT `u`.`account_id` AS `UserID`, `u`.`email` AS `Email`, `u`.`subscription` AS `SubscriptionType`, CASE WHEN to_days(curdate()) - to_days(`u`.`trial_start_date`) <= 7 THEN 0 ELSE CASE WHEN `u`.`subscription` = 'SD' THEN 10 WHEN `u`.`subscription` = 'HD' THEN 15 WHEN `u`.`subscription` = 'UHD' THEN 20 ELSE 0 END- CASE WHEN `u`.`discount` = 1 THEN 2 ELSE 0 END END AS `SubscriptionCost` FROM `user` AS `u` ;

-- --------------------------------------------------------

--
-- Структура для представления `user_genre_count`
--
DROP TABLE IF EXISTS `user_genre_count`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_genre_count`  AS SELECT `mvc`.`account_id` AS `user_id`, `g`.`genre_id` AS `genre_id`, sum(`mvc`.`number`) AS `total_views` FROM (((`movieviewcount` `mvc` join `movie` `m` on(`mvc`.`movie_id` = `m`.`movie_id`)) join `genreformovie` `mg` on(`m`.`movie_id` = `mg`.`movie_id`)) join `genre` `g` on(`mg`.`genre_id` = `g`.`genre_id`)) GROUP BY `mvc`.`account_id`, `g`.`genre_id` ORDER BY `mvc`.`account_id` ASC, sum(`mvc`.`number`) DESC ;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`);

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
-- Индексы таблицы `genreforuser`
--
ALTER TABLE `genreforuser`
  ADD PRIMARY KEY (`genre_id`,`account_id`),
  ADD KEY `account_id` (`account_id`);

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
  ADD KEY `FK_SeriesViewCount_Series` (`series_id`),
  ADD KEY `FKeh1b2xgu8esqripye7l4o90rq` (`episode_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `account`
--
ALTER TABLE `account`
  MODIFY `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `episode`
--
ALTER TABLE `episode`
  MODIFY `episode_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT для таблицы `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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


-- Drop the foreign key constraint from the content_history table
ALTER TABLE `content_history` DROP FOREIGN KEY `content_history_ibfk_1`;

-- Modify the account_id column in the user table
ALTER TABLE `user` MODIFY `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

-- Re-add the foreign key constraint to the content_history table
ALTER TABLE `content_history` ADD CONSTRAINT `content_history_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `episode`
--
ALTER TABLE `episode`
  ADD CONSTRAINT `FK_episode_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE SET NULL ON UPDATE CASCADE;

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
-- Ограничения внешнего ключа таблицы `genreforuser`
--
ALTER TABLE `genreforuser`
  ADD CONSTRAINT `genreforuser_accountid` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `genreforuser_genreid` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE;

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
  ADD CONSTRAINT `FK_SeriesViewCount_Series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKeh1b2xgu8esqripye7l4o90rq` FOREIGN KEY (`episode_id`) REFERENCES `episode` (`episode_id`);

--
-- Ограничения внешнего ключа таблицы `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

--
--
--

CREATE VIEW SubscriptionCosts AS
SELECT 
    u.account_id AS UserID,
    u.email AS Email,
    u.subscription AS SubscriptionType,
    CASE 
        WHEN DATEDIFF(CURDATE(), u.trial_start_date) <= 7 THEN 0 -- Free within trial period
        ELSE 
            CASE 
                WHEN u.subscription = 'SD' THEN 10
                WHEN u.subscription = 'HD' THEN 15
                WHEN u.subscription = 'UHD' THEN 20
                ELSE 0 -- Default for invalid subscription type
            END - 
            CASE 
                WHEN u.discount = TRUE THEN 2 ELSE 0 -- Apply $2 discount
            END
    END AS SubscriptionCost
FROM 
    `user` u;


CREATE VIEW UserLoginView AS
SELECT 
    u.account_id AS UserID,
    u.email AS Email,
    u.failed_attempts AS FailedAttempts,
    blocked,
    CASE 
        WHEN FailedAttempts >= 3 THEN 'Account is locked'
        ELSE 'Account is not locked'
    END AS AccountStatus
FROM 
    user;


CREATE VIEW UserProfileView AS 
SELECT 
    u.account_id AS UserID,
    u.email AS Email,
    p.profile_id AS ProfileID,
    p.age AS ProfileAge,
    p.profile_image AS ProfileImage
FROM 
    user u
JOIN
    profile p ON u.account_id = p.account_id;



CREATE VIEW UserContentView AS 
SELECT 
    u.account_id,
    c.content_id,
    c.title,
    c.type,
    c.quality,
    h.viewed_at,
    h.paused_at,
    h.resumed_at
  FROM 
    user u
  JOIN 
   content_history h ON u.account_id = h.account_id
  JOIN 
    content c ON h.content_id = c.content_id;


DELIMITER $$

CREATE PROCEDURE AddMovieViewCount(
    IN p_movieId INT,
    IN p_accountId INT
)
BEGIN
    -- Check the record exists in the movieviewcount table
    IF EXISTS (
        SELECT 1 
        FROM movieviewcount 
        WHERE account_id = p_accountId AND movie_id = p_movieId
    ) THEN
        --increment the view count
        UPDATE movieviewcount
        SET number = number + 1
        WHERE account_id = p_accountId AND movie_id = p_movieId;
    ELSE
        -- If it doesn't exist, create a new entry with initial count = 1
        INSERT INTO movieviewcount (`account_id`, `movie_id`, `number`)
        VALUES (p_accountId, p_movieId, 1);
    END IF;
END $$

DELIMITER ;

-- --------------------------------------------------------

DELIMITER $$

CREATE PROCEDURE AddSeriesViewCount(
    IN p_seriesId INT,
    IN p_accountId INT
)
BEGIN
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
END $$

DELIMITER ;


