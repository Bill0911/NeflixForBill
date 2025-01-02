-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 02, 2025 at 05:01 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `netflix`
--

DELIMITER $$
--
-- Procedures
--
CREATE `AddEpisode` (IN `p_episode_id` INT, IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_series_id` INT)   BEGIN
    INSERT INTO `episode` (`title`, `duration`, `series_id`)
    VALUES (p_title, p_duration, p_series_id);
END$$

CREATE `AddGenre` (IN `p_title` VARCHAR(255))   BEGIN
    INSERT INTO `genre` (`title`)
    VALUES (p_title);
END$$

CREATE `AddGenreForMovie` (IN `p_genreId` INT, IN `p_movieId` INT)   BEGIN
	INSERT INTO `genreformovie` (`genre_id`, `movie_id`)
    VALUES (p_genreId, p_movieId);
END$$

CREATE `AddGenreForSeries` (IN `p_genreId` INT, IN `p_seriesId` INT)   BEGIN
	INSERT INTO `genreforseries` (`genre_id`, `series_id`)
    VALUES (p_genreId, p_seriesId);
END$$

CREATE `AddGenreForUser` (IN `p_genreId` INT, IN `p_accountId` INT)   BEGIN
	INSERT INTO `genreforuser` (`genre_id`, `account_id`)
    VALUES (p_genreId, p_accountId);
END$$

CREATE `AddLanguage` (IN `p_name` VARCHAR(255))   BEGIN
    INSERT INTO `genre` (`name`)
    VALUES (p_name);
END$$

CREATE `AddMovie` (IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_minimum_age` INT(11))   BEGIN
    INSERT INTO `movie` (`title`, `duration`, `minimum_age`)
    VALUES (p_title, p_duration, p_minimum_age);
END$$

CREATE `AddMoviesProfileWatchlist` (IN `p_profileId` INT, IN `p_movieId` INT)   BEGIN
	INSERT INTO `moviesprofilewatchlist` (`profile_id`, `movie_id`)
    VALUES (p_profileId, p_movieId);
END$$

CREATE `AddMovieViewCount` (IN `p_movieId` INT, IN `p_accountId` INT)   BEGIN
    -- Check if the record exists in the movieviewcount table
    IF EXISTS (
        SELECT 1 
        FROM movieviewcount 
        WHERE account_id = p_accountId AND movie_id = p_movieId
    ) THEN
        -- If it exists, increment the view count
        UPDATE movieviewcount
        SET number = number + 1, last_viewed = current_timestamp()
        WHERE account_id = p_accountId AND movie_id = p_movieId;
    ELSE
        -- If it doesn't exist, create a new entry with initial count = 1
        INSERT INTO movieviewcount (`account_id`, `movie_id`, `number`, `last_viewed`)
        VALUES (p_accountId, p_movieId, 1, current_timestamp());
    END IF;
END$$

CREATE `AddProflie` (IN `p_account_id` INT(11), IN `p_profile_image` VARCHAR(255), IN `p_age` INT(3), IN `p_name` VARCHAR(255))   BEGIN
    INSERT INTO `profile` (`account_id`, `profile_image`, `age`, `name`)
    VALUES (p_account_id, p_profile_image, p_age, p_name);
END$$

CREATE `AddSeries` (IN `p_title` VARCHAR(255), IN `p_minimum_age` INT(11))   BEGIN
    INSERT INTO `series` (`title`, `minimum_age`)
    VALUES (p_title, p_minimum_age);
END$$

CREATE `AddSeriesProfileWatchlist` (IN `p_profileId` INT, IN `p_seriesId` INT)   BEGIN
	INSERT INTO `seriesprofilewatchlist` (`profile_id`, `series_id`)
    VALUES (p_profileId, p_seriesId);
END$$

CREATE `AddSeriesViewCount` (IN `p_seriesId` INT, IN `p_accountId` INT)   BEGIN
    -- Check if the record exists in the seriesviewcount table
    IF EXISTS (
        SELECT 1 
        FROM seriesviewcount
        WHERE series_id = p_seriesId AND account_id = p_accountId
    ) THEN
        -- If it exists, increment the view count
        UPDATE seriesviewcount
        SET number = number + 1, last_viewed = current_timestamp()
        WHERE series_id = p_seriesId AND account_id = p_accountId;
    ELSE
        -- If it doesn't exist, create a new entry with initial count = 1
        INSERT INTO seriesviewcount (`account_id`, `series_id`, `number`, `last_viewed`)
        VALUES (p_accountId, p_seriesId, 1, current_timestamp());
    END IF;
END$$

CREATE `AddUser` (IN `p_email` VARCHAR(255), IN `p_password` VARCHAR(255), IN `p_payment_method` VARCHAR(255), IN `p_language_id` INT(11))   BEGIN
    INSERT INTO `user` (`email`, `password`, `payment_method`, `language_id`)
    VALUES (p_email, p_password, p_payment_method, p_language_id);
END$$

CREATE `DeleteEpisode` (IN `p_episode_id` INT)   BEGIN
    DELETE FROM `episode` WHERE `episode_id` = p_episode_id;
END$$

CREATE `DeleteGenre` (IN `p_genre_id` INT)   BEGIN
    DELETE FROM `genre`
    WHERE `genre_id` = p_genre_id;
END$$

CREATE `DeleteGenreForMovie` (IN `p_genre_id` INT, IN `p_movie_id` INT)   BEGIN
    DELETE FROM genreformovie
    WHERE movie_id = p_movie_id AND genre_id = p_genre_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE `DeleteGenreForSeries` (IN `p_genre_id` INT, IN `p_series_id` INT)   BEGIN
    DELETE FROM genreforseries
    WHERE series_id = p_series_id AND genre_id = p_genre_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE `DeleteGenreForUser` (IN `p_genre_id` INT, IN `p_account_id` INT)   BEGIN
    DELETE FROM genreforuser
    WHERE account_id = p_account_id AND genre_id = p_genre_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE `DeleteLanguage` (IN `p_language_id` INT)   BEGIN
    DELETE FROM `language` WHERE `language_id` = p_language_id;
END$$

CREATE `DeleteMovie` (IN `p_movie_id` INT(11))   BEGIN
    DELETE FROM `movie`
    WHERE `movie_id` = p_movie_id;
END$$

CREATE `DeleteMoviesProfileWatchlist` (IN `p_profile_id` INT, IN `p_movie_id` INT)   BEGIN
    DELETE FROM moviesprofilewatchlist
    WHERE profile_id = p_profile_id AND movie_id = p_movie_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE `DeleteMovieViewCount` (IN `p_account_id` INT, IN `p_movie_id` INT)   BEGIN
    DELETE FROM movieviewcount
    WHERE account_id = p_account_id AND movie_id = p_movie_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE `DeleteProfile` (IN `p_profile_id` INT)   BEGIN
    DELETE FROM `profile` WHERE `profile_id` = p_profile_id;
END$$

CREATE `DeleteSeries` (IN `p_series_id` INT)   BEGIN
    DELETE FROM `series` WHERE `series_id` = p_series_id;
END$$

CREATE `DeleteSeriesProfileWatchlist` (IN `p_profile_id` INT, IN `p_series_id` INT)   BEGIN
    DELETE FROM seriesprofilewatchlist
    WHERE profile_id = p_profile_id AND series_id = p_series_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE `DeleteSeriesViewCount` (IN `p_account_id` INT, IN `p_series_id` INT)   BEGIN
    DELETE FROM seriesviewcount
    WHERE account_id = p_account_id AND series_id = p_series_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE `DeleteUser` (IN `p_account_id` INT)   BEGIN
    DELETE FROM `user` WHERE `account_id` = p_account_id;
END$$

CREATE `GetEpisodeById` (IN `p_episode_id` INT)   BEGIN
    SELECT * FROM `episode` WHERE `episode_id` = p_episode_id;
END$$

CREATE `GetGenreById` (IN `p_genre_id` INT)   BEGIN
    SELECT * FROM `genre`
    WHERE `genre_id` = p_genre_id;
END$$

CREATE `GetGenreForMovie` (IN `p_genre_id` INT, IN `p_movie_id` INT)   BEGIN
    SELECT * FROM `genreformovie`
    WHERE `genre_id` = p_genre_id AND `movie_id` = p_movie_id;
END$$

CREATE `GetGenreForSeries` (IN `p_genre_id` INT, IN `p_series_id` INT)   BEGIN
    SELECT * FROM `genreforseries`
    WHERE `genre_id` = p_genre_id AND `series_id` = p_series_id;
END$$

CREATE `GetGenreForUser` (IN `p_genre_id` INT, IN `p_account_id` INT)   BEGIN
    SELECT * FROM `genreforuser`
    WHERE `genre_id` = p_genre_id AND `account_id` = p_account_id;
END$$

CREATE `GetLanguageById` (IN `p_language_id` INT)   BEGIN
    SELECT * FROM `language`
    WHERE `language_id` = p_language_id;
END$$

CREATE `GetManyEpisodes` ()   BEGIN
    SELECT * FROM `episode` LIMIT 49;
END$$

CREATE `GetManyGenreForMovies` ()   BEGIN
    SELECT * FROM `genreformovie` LIMIT 49;
END$$

CREATE `GetManyGenreForSeries` ()   BEGIN
    SELECT * FROM `genreforseries` LIMIT 49;
END$$

CREATE `GetManyGenreForUsers` ()   BEGIN
    SELECT * FROM `genreforuser` LIMIT 49;
END$$

CREATE `GetManyGenres` ()   BEGIN
    SELECT * FROM `genre` LIMIT 49;
END$$

CREATE `GetManyMoviesProfileWatchlists` ()   BEGIN
    SELECT * FROM `moviesprofilewatchlist` LIMIT 49;
END$$

CREATE `GetManyMovieViewCounts` ()   BEGIN
    SELECT * FROM `movieviewcount` LIMIT 49;
END$$

CREATE `GetManySeriesProfileWatchlists` ()   BEGIN
    SELECT * FROM `seriesprofilewatchlist` LIMIT 49;
END$$

CREATE `GetManySeriesViewCounts` ()   BEGIN
    SELECT * FROM `seriesviewcount` LIMIT 49;
END$$

CREATE `GetMovieById` (IN `p_movie_id` INT)   BEGIN
    SELECT * FROM `movie` WHERE `movie_id` = p_movie_id;
END$$

CREATE `GetMoviesProfileWatchlist` (IN `p_profile_id` INT, IN `p_movie_id` INT)   BEGIN
    SELECT * FROM moviesprofilewatchlist
    WHERE `profile_id` = p_profile_id AND `movie_id` = p_movie_id;
END$$

CREATE `GetMovieViewCount` (IN `p_account_id` INT, IN `p_movie_id` INT)   BEGIN
    SELECT * FROM movieviewcount
    WHERE `movie_id` = p_movie_id AND `account_id` = p_account_id;
END$$

CREATE `GetPersonalizedOffer` (IN `userId` INT, IN `maxMovies` INT)   BEGIN
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

CREATE `GetProfileById` (IN `p_profile_id` INT)   BEGIN
    SELECT * FROM `profile`
    WHERE `profile_id` = p_profile_id;
END$$

CREATE `GetSeriesById` (IN `p_series_id` INT(11))   BEGIN
    SELECT * FROM `series` WHERE `series_id` = p_series_id;
END$$

CREATE `GetSeriesProfileWatchlist` (IN `p_profile_id` INT, IN `p_series_id` INT)   BEGIN
    SELECT * FROM seriesprofilewatchlist
    WHERE `profile_id` = p_profile_id AND `eries_id` = p_series_id;
END$$

CREATE `GetSeriesViewCount` (IN `p_account_id` INT, IN `p_series_id` INT)   BEGIN
    SELECT * FROM seriesviewcount
    WHERE `series_id` = p_series_id AND `account_id` = p_account_id;
END$$

CREATE `GetUserByEmail` (IN `p_email` VARCHAR(255))   BEGIN
    SELECT * FROM `user` WHERE `email` = p_email;
END$$

CREATE `GetUserById` (IN `p_account_id` INT(11))   BEGIN
    SELECT * FROM `user` WHERE `account_id` = p_account_id;
END$$

CREATE `PatchEpisode` (IN `p_episode_id` INT, IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_series_id` INT)   BEGIN
    UPDATE `episode`
    SET 
        `title` = COALESCE(p_title, `title`),
        `duration` = COALESCE(p_duration, `duration`),
        `series_id` = COALESCE(p_series_id, `series_id`)
    WHERE `episode_id` = p_episode_id;
END$$

CREATE `PatchGenreForMovie` (IN `p_old_genre_id` INT, IN `p_old_movie_id` INT, IN `p_new_genre_id` INT, IN `p_new_movie_id` INT)   BEGIN
    UPDATE genreformovie
    SET
        genre_id = IFNULL(p_new_genre_id, genre_id),
        movie_id = IFNULL(p_new_movie_id, movie_id)
    WHERE genre_id = p_old_genre_id AND movie_id = p_old_movie_id;
END$$

CREATE `PatchGenreForSeries` (IN `p_old_genre_id` INT, IN `p_old_series_id` INT, IN `p_new_genre_id` INT, IN `p_new_series_id` INT)   BEGIN
    UPDATE genreforseries
    SET
        genre_id = IFNULL(p_new_genre_id, genre_id),
        series_id = IFNULL(p_new_series_id, series_id)
    WHERE genre_id = p_old_genre_id AND series_id = p_old_series_id;
END$$

CREATE `PatchGenreForUser` (IN `p_old_user_id` INT, IN `p_old_genre_id` INT, IN `p_new_user_id` INT, IN `p_new_genre_id` INT)   BEGIN

    UPDATE GenreForUser
    SET
        genre_id = IFNULL(p_new_genre_id, genre_id),  
        user_id = IFNULL(p_new_user_id, user_id)     
    WHERE user_id = p_old_user_id AND genre_id = p_old_genre_id;
END$$

CREATE `PatchMovie` (IN `p_movie_id` INT(11), IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_sd_available` BIT(1), IN `p_hd_available` BIT(1), IN `p_uhd_available` BIT(1), IN `p_minimum_age` INT(3))   BEGIN
    UPDATE `movie`
    SET 
        `title` = COALESCE(p_title, `title`),
        `duration` = COALESCE(p_duration, `duration`),
        `sd_available` = COALESCE(p_sd_available, `sd_available`),
        `hd_available` = COALESCE(p_hd_available, `hd_available`),
        `uhd_available` = COALESCE(p_uhd_available, `uhd_available`),
        `minimum_age` = COALESCE(p_minimum_age, `minimum_age`)
    WHERE `movie_id` = p_movie_id;
END$$

CREATE `PatchMovieProfileWatchlist` (IN `p_old_profile_id` INT, IN `p_old_movie_id` INT, IN `p_new_profile_id` INT, IN `p_new_movie_id` INT)   BEGIN
    UPDATE movieprofilewatchlist
    SET
        profile_id = IFNULL(p_new_profile_id, profile_id),
        movie_id = IFNULL(p_new_movie_id, movie_id)
    WHERE profile_id = p_old_profile_id AND movie_id = p_old_movie_id;
END$$

CREATE `PatchMovieViewCount` (IN `p_account_id` INT, IN `p_movie_id` INT, IN `p_new_number` INT, IN `p_new_last_viewed` DATETIME)   BEGIN
    UPDATE movieviewcount
    SET
        number = IFNULL(p_new_number, number),
        last_viewed = IFNULL(p_new_last_viewed, last_viewed)
    WHERE account_id = p_account_id AND movie_id = p_movie_id;
END$$

CREATE `PatchProfile` (IN `p_profile_id` INT(11), IN `p_account_id` INT(11), IN `p_profile_image` VARCHAR(255), IN `p_age` INT(3), IN `p_name` VARCHAR(255))   BEGIN
    UPDATE `profile`
    SET 
        `account_id` = COALESCE(p_account_id, `account_id`),
        `profile_image` = COALESCE(p_profile_image, `profile_image`),
        `age` = COALESCE(p_age, `age`),
        `name` = COALESCE(p_name, `name`)
    WHERE `profile_id` = p_profile_id;
END$$

CREATE `PatchSeries` (IN `p_series_id` INT(11), IN `p_title` VARCHAR(255), IN `p_minimum_age` INT(3))   BEGIN
    UPDATE `profile`
    SET 
        `title` = COALESCE(p_title, `title`),
        `minimum_age` = COALESCE(p_minimum_age, `minimum_age`)
    WHERE `series_id` = p_series_id;
END$$

CREATE `PatchSeriesProfileWatchlist` (IN `p_old_profile_id` INT, IN `p_old_series_id` INT, IN `p_new_profile_id` INT, IN `p_new_series_id` INT)   BEGIN
    UPDATE seriesprofilewatchlist
    SET
        profile_id = IFNULL(p_new_profile_id, profile_id),
        series_id = IFNULL(p_new_series_id, series_id)
    WHERE profile_id = p_old_profile_id AND series_id = p_old_series_id;
END$$

CREATE `PatchSeriesViewCount` (IN `p_account_id` INT, IN `p_series_id` INT, IN `p_new_number` INT, IN `p_new_last_viewed` DATETIME)   BEGIN
    UPDATE seriesviewcount
    SET
        number = IFNULL(p_new_number, number),
        last_viewed = IFNULL(p_new_last_viewed, last_viewed)
    WHERE account_id = p_account_id AND series_id = p_series_id;
END$$

CREATE `PatchUser` (IN `p_account_id` INT(11), IN `p_password` VARCHAR(255), IN `p_payment_method` VARCHAR(255), IN `p_blocked` BIT(1), OUT `output` VARCHAR(255), IN `p_subscription` ENUM('SD','HD','UHD'), IN `p_trial_start_date` DATETIME, IN `p_language_id` INT(11), IN `p_role` ENUM('JUNIOR','MEDIOR','SENIOR'), IN `p_failed_attempts` INT(11), IN `p_lock_time` DATETIME, IN `p_discount` BIT(1))   BEGIN
    UPDATE `user`
    SET 
        `password` = COALESCE(p_password, `password`),
        `payment_method` = COALESCE(p_payment_method, `payment_method`),
        `blocked` = COALESCE(p_blocked, `blocked`),
        `subscription` = COALESCE(p_subscription, `subscription`),
        `trial_start_date` = COALESCE(p_trial_start_date, `trial_start_date`),
        `language_id` = COALESCE(p_language_id, `language_id`), `role` = COALESCE(p_role, `role`), `failed_attempts` = COALESCE(p_failed_attempts, `failed_attempts`), `lock_time` = COALESCE(p_lock_time, `lock_time`), `discount` = COALESCE(p_discount, `discount`)
    WHERE `account_id` = p_account_id;
END$$

CREATE `process_payment` (IN `userId` INT, IN `subscriptionType` VARCHAR(10), IN `discountApplied` BIT)   BEGIN
    DECLARE paymentAmount DECIMAL(10, 2);
    IF subscriptionType = 'SD' THEN
        SET paymentAmount = 7.99;
    ELSEIF subscriptionType = 'HD' THEN
        SET paymentAmount = 10.99;
    ELSEIF subscriptionType = 'UHD' THEN
        SET paymentAmount = 13.99;
    END IF;

    IF discountApplied THEN
        SET paymentAmount = paymentAmount - 2.00;
    END IF;

    INSERT INTO payments (account_id, subscription_type, payment_amount, is_discount_applied, is_paid)
    VALUES (userId, subscriptionType, paymentAmount, discountApplied, b'1');
END$$

CREATE `SetUserBlocked` (IN `p_email` INT(11))   BEGIN
    UPDATE `user`
    SET `lock_time` = current_timestamp(),
`blocked` = 1
    WHERE `email` = p_email;
END$$

CREATE `UpdateEpisode` (IN `p_episode_id` INT, IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_series_id` INT)   BEGIN
    UPDATE `episode`
    SET 
        `title` = p_title,
        `duration` = IFNULL(p_duration, '00:00:00'),
        `series_id` = p_series_id
    WHERE `episode_id` = p_episode_id;
END$$

CREATE `UpdateGenre` (IN `p_genre_id` INT, IN `p_genre_name` VARCHAR(255))   BEGIN
    UPDATE `genre`
    SET `genre_name` = p_genre_name
    WHERE `genre_id` = p_from_genre_id;
END$$

CREATE `UpdateGenreForMovie` (IN `p_old_genre_id` INT, IN `p_old_movie_id` INT, IN `p_new_genre_id` INT, IN `p_new_movie_id` INT)   BEGIN
    UPDATE genreformovie
    SET
        genre_id = p_new_genre_id,
        movie_id = p_new_movie_id
    WHERE genre_id = p_old_genre_id AND movie_id = p_old_movie_id;
END$$

CREATE `UpdateGenreForSeries` (IN `p_old_genre_id` INT, IN `p_old_series_id` INT, IN `p_new_genre_id` INT, IN `p_new_series_id` INT)   BEGIN
    UPDATE genreforseries
    SET
        genre_id = p_new_genre_id,
        series_id = p_new_series_id
    WHERE genre_id = p_old_genre_id AND series_id = p_old_series_id;
END$$

CREATE `UpdateGenreForUser` (IN `p_old_user_id` INT, IN `p_old_genre_id` INT, IN `p_new_user_id` INT, IN `p_new_genre_id` INT)   BEGIN

    UPDATE GenreForUser
    SET
        genre_id = p_new_genre_id,  
        user_id = p_new_user_id     
    WHERE user_id = p_old_user_id AND genre_id = p_old_genre_id;
END$$

CREATE `UpdateLanguage` (IN `p_language_id` INT(11), IN `p_name` VARCHAR(255))   BEGIN
    UPDATE `language`
    SET `name` = p_name
    WHERE `language_id` = p_language_id;
END$$

CREATE `UpdateMovie` (IN `p_movie_id` INT(11), IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_sd_available` BIT(1), IN `p_hd_available` BIT(1), IN `p_uhd_available` BIT(1), IN `p_minimum_age` INT(3))   BEGIN
    UPDATE `movie`
    SET 
        `title` = p_title,
        `duration` = p_duration,
        `sd_available` = p_sd_available,
        `hd_available` = p_hd_available,
        `uhd_available` = p_uhd_available,
        `minimum_age` = p_minimum_age
    WHERE `movie_id` = p_movie_id;
END$$

CREATE `UpdateMovieProfileWatchlist` (IN `p_old_profile_id` INT, IN `p_old_movie_id` INT, IN `p_new_profile_id` INT, IN `p_new_movie_id` INT)   BEGIN
    UPDATE movieprofilewatchlist
    SET
        profile_id = p_new_profile_id,
        movie_id = p_new_movie_id
    WHERE profile_id = p_old_profile_id AND movie_id = p_old_movie_id;
END$$

CREATE `UpdateMovieViewCount` (IN `p_account_id` INT, IN `p_movie_id` INT, IN `p_new_number` INT, IN `p_new_last_viewed` DATETIME)   BEGIN
    UPDATE movieviewcount
    SET
        number = p_new_number,
        last_viewed = p_new_last_viewed
    WHERE account_id = p_account_id AND movie_id = p_movie_id;
END$$

CREATE `UpdateProfile` (IN `p_profile_id` INT(11), IN `p_accountId` INT(11), IN `p_profile_image` VARCHAR(255), IN `p_age` INT(3), IN `p_name` VARCHAR(255))   BEGIN
    UPDATE `profile`
    SET 
        `account_id` = p_account_id, 
        `profile_image` = p_profile_image, 
        `age` = p_age, 
        `name` = p_name 
    WHERE `profile_id` = p_profile_id;
END$$

CREATE `UpdateSeries` (IN `p_series_id` INT(11), IN `p_title` VARCHAR(255), IN `p_minimum_age` INT(3))   BEGIN
    UPDATE `series`
    SET 
        `title` = p_title,
        `minimum_age` = p_minimum_age
    WHERE `series_id` = p_series_id;
END$$

CREATE `UpdateSeriesProfileWatchlist` (IN `p_old_profile_id` INT, IN `p_old_series_id` INT, IN `p_new_profile_id` INT, IN `p_new_series_id` INT)   BEGIN
    UPDATE seriesprofilewatchlist
    SET
        profile_id = p_new_profile_id,
        series_id = p_new_series_id
    WHERE profile_id = p_old_profile_id AND series_id = p_old_series_id;
END$$

CREATE `UpdateSeriesViewCount` (IN `p_account_id` INT, IN `p_series_id` INT, IN `p_new_number` INT, IN `p_new_last_viewed` DATETIME)   BEGIN
    UPDATE seriesviewcount
    SET
        number = p_new_number,
        last_viewed = p_new_last_viewed
    WHERE account_id = p_account_id AND series_id = p_series_id;
END$$

CREATE `UpdateUser` (IN `p_account_id` INT(11), IN `p_password` VARCHAR(255), IN `p_payment_method` VARCHAR(255), IN `p_blocked` BIT(1), OUT `output` VARCHAR(255), IN `p_subscription` ENUM('SD','HD','UHD'), IN `p_trial_start_date` DATETIME, IN `p_language_id` INT(11), IN `p_role` ENUM('JUNIOR','MEDIOR','SENIOR'), IN `p_failed_attempts` INT(11), IN `p_lock_time` DATETIME, IN `p_discount` BIT(1))   BEGIN
    UPDATE `user`
    SET 
        `password` = p_password,
        `payment_method` = p_payment_method,
        `blocked` = p_blocked,
        `subscription` = p_subscription,
        `trial_start_date` = p_trial_start_date,
        `language_id` = p_language_id, 
        `role` = p_role, 
        `failed_attempts` = p_failed_attempts, 
        `lock_time` = p_lock_time, 
        `discount` = p_discount
    WHERE `account_id` = p_account_id;
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
(7, 'fantasy'),
(8, 'sci-fi'),
(17, 'Adventure'),
(18, 'triler'),
(19, 'biography'),
(20, 'History'),
(21, 'musical'),
(29, 'War');

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
(5, 1),
(7, 2),
(8, 3),
(17, 3);

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
(2, 'latvian'),
(3, 'lithuanian'),
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
(1, 'womp-womp, funny', '01:32:00', b'1', b'1', b'0', 14),
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
  `number` int(11) DEFAULT NULL,
  `last_viewed` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movieviewcount`
--

INSERT INTO `movieviewcount` (`account_id`, `movie_id`, `number`, `last_viewed`) VALUES
(1, 1, 3, NULL),
(1, 2, 2, NULL),
(1, 3, 1, NULL),
(3, 1, 1, NULL),
(5, 2, 2, NULL),
(1, 1, 3, NULL),
(1, 2, 2, NULL),
(1, 3, 1, NULL),
(3, 1, 1, NULL),
(5, 2, 2, NULL),
(1, 1, 3, NULL),
(1, 2, 2, NULL),
(1, 3, 1, NULL),
(3, 1, 1, NULL),
(5, 2, 2, NULL),
(1, 1, 3, NULL),
(1, 2, 2, NULL),
(1, 3, 1, NULL),
(3, 1, 1, NULL),
(5, 2, 2, NULL),
(1, 1, 3, NULL),
(1, 2, 2, NULL),
(1, 3, 1, NULL),
(3, 1, 1, NULL),
(5, 2, 2, NULL),
(1, 1, 3, NULL),
(1, 2, 2, NULL),
(1, 3, 1, NULL),
(3, 1, 1, NULL),
(5, 2, 2, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `id` bigint(20) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `expiry_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `payment_id` bigint(20) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `is_discount_applied` bit(1) NOT NULL DEFAULT b'0',
  `is_paid` bit(1) NOT NULL DEFAULT b'0',
  `payment_date` datetime NOT NULL DEFAULT current_timestamp(),
  `subscription_type` varchar(10) NOT NULL,
  `payment_amount` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `paymentstatus`
-- (See below for the actual view)
--
CREATE TABLE `paymentstatus` (
`payment_id` bigint(20)
,`account_id` int(11)
,`email` varchar(255)
,`subscription_type` varchar(10)
,`payment_amount` decimal(10,2)
,`is_paid` bit(1)
,`is_discount_applied` bit(1)
,`payment_date` datetime
);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED NOT NULL,
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

--
-- Dumping data for table `series`
--

INSERT INTO `series` (`series_id`, `title`, `minimum_age`) VALUES
(1, 'Sex education', 18);

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
  `number` int(11) DEFAULT NULL,
  `last_viewed` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `subscriptioncosts`
-- (See below for the actual view)
--
CREATE TABLE `subscriptioncosts` (
`UserID` int(11)
,`Email` varchar(255)
,`SubscriptionType` enum('SD','HD','UHD')
,`SubscriptionCost` int(3)
);

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
  `active` bit(1) NOT NULL DEFAULT b'0',
  `subscription` enum('SD','HD','UHD') DEFAULT 'SD',
  `trial_start_date` datetime DEFAULT NULL,
  `language_id` int(11) UNSIGNED DEFAULT NULL,
  `role` enum('JUNIOR','MEDIOR','SENIOR') DEFAULT 'JUNIOR',
  `failed_attempts` int(11) DEFAULT 0,
  `lock_time` datetime DEFAULT NULL,
  `discount` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `blocked`, `active`, `subscription`, `trial_start_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES
(1, 'fjodor.smorodins@gmail.com', '$2a$10$hszeHDUNOv4lnd24ZS9sOeOkOJUYo5zSi2H2makEPti1uznr4s5P2', 'abc', b'0', b'0', 'SD', '2024-12-07 14:32:59', 4, 'SENIOR', 0, NULL, b'0'),
(2, 'fjodorsm@gmail.com', '$2a$10$2QlecdJ25ELwT/avANQAUelbxtS9tysiRO5LSE0omLATaWhdAPfZC', 'Credit Card', b'0', b'0', 'SD', '2024-12-07 14:33:33', 1, 'JUNIOR', 0, NULL, b'0'),
(3, 'smorodins@gmail.com', '$2a$10$KhhGnFeK2q32DYG7/fMhNe/GEzf1dDJVkQqq5isK1vwuIO9h0zor.', 'CrC', b'0', b'0', 'SD', '2024-12-16 18:54:30', 3, 'JUNIOR', 0, NULL, b'0'),
(5, 'fjodors@hello.com', '$2a$10$hKsRL99MRpKUr.vrJPqsfuG3qhGkDjXQEYDxHytWFBYgW7HZJ/54W', 'golden bars', b'0', b'0', 'SD', '2024-12-20 16:19:25', 2, 'JUNIOR', 0, NULL, b'0'),
(6, 'artjoms.grishajevs@hello.com', '$2a$10$NboUZOHniHtnfHhFFECcF.dA64uJsp.8/OnD0B0NEuMvTyvIfN7we', 'children', b'0', b'0', 'SD', '2024-12-20 16:24:58', 1, 'JUNIOR', 0, NULL, b'0'),
(7, 'somebody@hello.com', '$2a$10$4H41Ugw1ho9ga4DfTV1rwegl.uxbZcTbEu3/SBeklNzsHnXoYliTe', 'money', b'0', b'0', 'SD', '2024-12-20 17:08:59', 1, 'JUNIOR', 0, NULL, b'0'),
(9, 'somepersonwhatever@hello.com', '$2a$10$DhZSCWySz9rypM/jM8mR6.yzaCPIpugVlITMSWx9whkmEp1ciPK42', 'something', b'0', b'0', 'SD', '2024-12-20 17:24:39', 2, 'JUNIOR', 0, NULL, b'0'),
(10, 'iamsteve@hello.com', '$2a$10$92qxixAWTf94z9sK.Lf2iebtyLdBV9ckOx.xfzGLv4enlX5gdsis6', 'mastercard', b'0', b'0', 'SD', '2024-12-20 17:58:22', 3, 'JUNIOR', 0, NULL, b'0'),
(15, 'test1@.com', '$2a$10$aP97IvFmxH8yLGuL1012Xe4sfLd6s1SdokAAKOhG3.tvWCTkmfD2.', 'some method', b'0', b'0', 'SD', '2024-12-20 22:46:29', 3, 'JUNIOR', 1, NULL, b'0'),
(17, 'medior.fjodor@g.com', '$2a$10$gQuhxuEegp0Ypg.IrGiL8.bmQwV4sdMzXirKh7N0N4KbOXAq4xwFi', 'some money transfer method', b'0', b'0', 'SD', '2024-12-23 17:55:18', 3, 'JUNIOR', 0, NULL, b'0');

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
-- Structure for view `paymentstatus`
--
DROP TABLE IF EXISTS `paymentstatus`;

CREATE VIEW `paymentstatus`  AS SELECT `p`.`payment_id` AS `payment_id`, `u`.`account_id` AS `account_id`, `u`.`email` AS `email`, `p`.`subscription_type` AS `subscription_type`, `p`.`payment_amount` AS `payment_amount`, `p`.`is_paid` AS `is_paid`, `p`.`is_discount_applied` AS `is_discount_applied`, `p`.`payment_date` AS `payment_date` FROM (`payments` `p` join `user` `u` on(`p`.`account_id` = `u`.`account_id`)) ;

-- --------------------------------------------------------

--
-- Structure for view `subscriptioncosts`
--
DROP TABLE IF EXISTS `subscriptioncosts`;

CREATE VIEW `subscriptioncosts`  AS SELECT `u`.`account_id` AS `UserID`, `u`.`email` AS `Email`, `u`.`subscription` AS `SubscriptionType`, CASE WHEN to_days(curdate()) - to_days(`u`.`trial_start_date`) <= 7 THEN 0 ELSE CASE WHEN `u`.`subscription` = 'SD' THEN 10 WHEN `u`.`subscription` = 'HD' THEN 15 WHEN `u`.`subscription` = 'UHD' THEN 20 ELSE 0 END- CASE WHEN `u`.`discount` = 1 THEN 2 ELSE 0 END END AS `SubscriptionCost` FROM `user` AS `u` ;

-- --------------------------------------------------------

--
-- Structure for view `user_genre_count`
--
DROP TABLE IF EXISTS `user_genre_count`;

CREATE VIEW `user_genre_count`  AS SELECT `mvc`.`account_id` AS `user_id`, `g`.`genre_id` AS `genre_id`, `g`.`genre_name` AS `genre_name`, sum(`mvc`.`number`) AS `total_views` FROM (((`movieviewcount` `mvc` join `movie` `m` on(`mvc`.`movie_id` = `m`.`movie_id`)) join `genreformovie` `mg` on(`m`.`movie_id` = `mg`.`movie_id`)) join `genre` `g` on(`mg`.`genre_id` = `g`.`genre_id`)) GROUP BY `mvc`.`account_id`, `g`.`genre_id` ORDER BY `mvc`.`account_id` ASC, sum(`mvc`.`number`) DESC ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`);

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
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `account_id` (`account_id`),
  ADD KEY `user_language` (`language_id`);

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
  MODIFY `genre_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

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
-- AUTO_INCREMENT for table `series`
--
ALTER TABLE `series`
  MODIFY `series_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `episode`
--
ALTER TABLE `episode`
  ADD CONSTRAINT `FK_episode_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `genreformovie`
--
ALTER TABLE `genreformovie`
  ADD CONSTRAINT `genre` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD CONSTRAINT `series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_language` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
