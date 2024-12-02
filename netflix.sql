-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Дек 03 2024 г., 00:20
-- Версия сервера: 10.4.32-MariaDB
-- Версия PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `netflix`
--

-- --------------------------------------------------------

--
-- Структура таблицы `language`
--

CREATE TABLE `language` (
  `language_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `language`
--

INSERT INTO `language` (`language_id`, `name`) VALUES
(1, 'eng'),
(2, 'nl'),
(3, 'lv');

-- --------------------------------------------------------

--
-- Структура таблицы `movie`
--

CREATE TABLE `movie` (
  `movie_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `duration` varchar(255) NOT NULL,
  `age_range` enum('6','9','12','16','16+') NOT NULL DEFAULT '12'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `movie`
--

INSERT INTO `movie` (`movie_id`, `title`, `duration`, `age_range`) VALUES
(1, 'dsfkghsdkgj', '1h23m', '12'),
(2, 'batman', '2h13m', '12'),
(3, 'starwars', '2h30m', '12');

-- --------------------------------------------------------

--
-- Структура таблицы `movieviewcount`
--

CREATE TABLE `movieviewcount` (
  `account_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `number` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `movieviewcount`
--

INSERT INTO `movieviewcount` (`account_id`, `movie_id`, `number`) VALUES
(3, 1, 5),
(3, 2, 6),
(1, 3, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `account_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `blocked` bit(1) NOT NULL,
  `subscription` enum('SD','HD','UHD') NOT NULL,
  `trial_start_date` datetime NOT NULL,
  `language_id` int(11) NOT NULL,
  `role` enum('VIEWER','JUNIOR','MEDIOR','SENIOR') NOT NULL,
  `password` varchar(255) NOT NULL,
  `failed_attempts` int(11) NOT NULL DEFAULT 0,
  `lock_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`account_id`, `email`, `payment_method`, `blocked`, `subscription`, `trial_start_date`, `language_id`, `role`, `password`, `failed_attempts`, `lock_time`) VALUES
(1, 'testuser@example.com', 'Credit Card', b'0', 'HD', '2024-12-01 19:59:05', 1, 'VIEWER', '$2a$10$NqV0coTNtn.xlAUNjsgoEeQpwWfsO6oY1N6vzvgR4jQsmXHz0OQvm', 0, NULL),
(2, 'testuser@example.com', 'Credit Card', b'0', 'HD', '2024-12-01 20:04:03', 1, 'VIEWER', '$2a$10$g4wVMjf7w62uYTE1wNimf.lSDVxIu/KaCkoLb/A5pmzALRrs/i8m2', 0, NULL),
(3, 'fjodorsm@gmail.com', 'Credit Card', b'0', 'HD', '2024-12-01 20:04:44', 3, 'VIEWER', '$2a$10$kCd6DisqZc3AJxmc7FMctO2BUd5Rt.6ou5oeVbApGZVwv22.BppYq', 0, NULL),
(4, 'yarik@gmail.com', '123', b'1', 'HD', '2024-12-03 00:11:11', 1, 'VIEWER', '$2a$10$2M029yo3azaltXc9wa5/feTey1M8gh8Tf3JNnBaaaNIJqtsdKKu2u', 3, NULL),
(5, 'zhora@gmail.com', 'cash', b'0', 'HD', '2024-12-03 00:12:48', 2, 'VIEWER', '$2a$10$/1PUQ1OdRl7OA3M0f.YtUuC/dI/oT/71IiShvQia1kIOGRPvj1rXu', 0, NULL);

--
-- Индексы сохранённых таблиц
--

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
-- Индексы таблицы `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD PRIMARY KEY (`movie_id`,`account_id`),
  ADD KEY `movieviewcount_user` (`account_id`),
  ADD KEY `movieviewcount_movie` (`movie_id`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `language_of_user` (`language_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
  ADD CONSTRAINT `movieviewcount_movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movieviewcount_user` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `language_of_user` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
