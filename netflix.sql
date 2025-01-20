
--

DELIMITER $$
--
-- Procedures
--
CREATE PROCEDURE `AddEpisode` (IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_series_id` INT)   BEGIN
    INSERT INTO `episode` (`title`, `duration`, `series_id`)
    VALUES (p_title, p_duration, p_series_id);
END$$

CREATE PROCEDURE `AddGenre` (IN `p_name` VARCHAR(255))   BEGIN
    INSERT INTO `genre` (`genre_name`)
    VALUES (p_name);
END$$

CREATE PROCEDURE `AddGenreForMovie` (IN `p_genreId` INT, IN `p_movieId` INT)   BEGIN
	INSERT INTO `genreformovie` (`genre_id`, `movie_id`)
    VALUES (p_genreId, p_movieId);
END$$

CREATE PROCEDURE `AddGenreForSeries` (IN `p_genreId` INT, IN `p_seriesId` INT)   BEGIN
	INSERT INTO `genreforseries` (`genre_id`, `series_id`)
    VALUES (p_genreId, p_seriesId);
END$$

CREATE PROCEDURE `AddGenreForUser` (IN `p_genreId` INT, IN `p_accountId` INT)   BEGIN
	INSERT INTO `genreforuser` (`genre_id`, `account_id`)
    VALUES (p_genreId, p_accountId);
END$$

CREATE PROCEDURE `AddLanguage` (IN `p_name` VARCHAR(255))   BEGIN
    INSERT INTO `language` (`name`)
    VALUES (p_name);
END$$

CREATE PROCEDURE `AddMovie` (IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_sd_available` BIT(1), IN `p_hd_available` BIT(1), IN `p_uhd_available` BIT(1), IN `p_minimum_age` INT(3))   BEGIN
    INSERT INTO `movie` (`title`, `duration`, `sd_available`, `hd_available`, `uhd_available` , `minimum_age`)
    VALUES (p_title, p_duration, p_sd_available, p_hd_available, p_uhd_available, p_minimum_age);
END$$

CREATE PROCEDURE `AddMoviesProfileWatchlist` (IN `p_profileId` INT, IN `p_movieId` INT)   BEGIN
	INSERT INTO `moviesprofilewatchlist` (`profile_id`, `movie_id`)
    VALUES (p_profileId, p_movieId);
END$$

CREATE PROCEDURE `AddMovieViewCount` (IN `p_accountId` INT, IN `p_movieId` INT)   BEGIN
    
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
        -- 
        INSERT INTO movieviewcount (`account_id`, `movie_id`, `number`, `last_viewed`)
        VALUES (p_accountId, p_movieId, 1, current_timestamp());
    END IF;

    DELETE FROM moviesprofilewatchlist
    WHERE profile_id IN (
        SELECT profile_id
        FROM profile
        WHERE account_id = p_accountId
    )
    AND movie_id = p_movieId;
END$$

CREATE PROCEDURE `AddProfile` (IN `p_account_id` BIGINT(20), IN `p_profile_image` VARCHAR(255), IN `p_age` INT(3), IN `p_name` VARCHAR(255))   BEGIN
    INSERT INTO `profile` (`account_id`, `profile_image`, `age`, `name`)
    VALUES (p_account_id, p_profile_image, p_age, p_name);
END$$

CREATE PROCEDURE `AddSeries` (IN `p_title` VARCHAR(255), IN `p_minimum_age` INT(11))   BEGIN
    INSERT INTO `series` (`title`, `minimum_age`)
    VALUES (p_title, p_minimum_age);
END$$

CREATE PROCEDURE `AddSeriesProfileWatchlist` (IN `p_profileId` INT, IN `p_seriesId` INT)   BEGIN
	INSERT INTO `seriesprofilewatchlist` (`profile_id`, `series_id`)
    VALUES (p_profileId, p_seriesId);
END$$

CREATE PROCEDURE `AddSeriesViewCount` (IN `p_accountId` INT, IN `p_seriesId` INT)   BEGIN
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
        -- If it doesn't exist, CREATE PROCEDURE a new entry with initial count = 1
        INSERT INTO seriesviewcount (`account_id`, `series_id`, `number`, `last_viewed`)
        VALUES (p_accountId, p_seriesId, 1, current_timestamp());
    END IF;

    DELETE FROM seriesprofilewatchlist
    WHERE profile_id IN (
        SELECT profile_id
        FROM profile
        WHERE account_id = p_accountId
    )
    AND series_id = p_seriesId;
END$$

CREATE PROCEDURE `AddUser` (IN `p_email` VARCHAR(255), IN `p_password` VARCHAR(255), IN `p_payment_method` VARCHAR(255), IN `p_language_id` INT(11), IN `p_subscription` ENUM('SD','HD','UHD'))   BEGIN
    INSERT INTO `user` (`email`, `password`, `payment_method`, `language_id`, `subscription`)
    VALUES (p_email, p_password, p_payment_method, p_language_id, p_subscription);
END$$

CREATE PROCEDURE `DeleteEpisode` (IN `p_episode_id` INT)   BEGIN
    DELETE FROM `episode` WHERE `episode_id` = p_episode_id;
END$$

CREATE PROCEDURE `DeleteGenre` (IN `p_genre_id` INT)   BEGIN
    DELETE FROM `genre`
    WHERE `genre_id` = p_genre_id;
END$$

CREATE PROCEDURE `DeleteGenreForMovie` (IN `p_genre_id` INT, IN `p_movie_id` INT)   BEGIN
    DELETE FROM genreformovie
    WHERE movie_id = p_movie_id AND genre_id = p_genre_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE PROCEDURE `DeleteGenreForSeries` (IN `p_genre_id` INT, IN `p_series_id` INT)   BEGIN
    DELETE FROM genreforseries
    WHERE series_id = p_series_id AND genre_id = p_genre_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE PROCEDURE `DeleteGenreForUser` (IN `p_genre_id` INT, IN `p_account_id` BIGINT(20))   BEGIN
    DELETE FROM genreforuser
    WHERE account_id = p_account_id AND genre_id = p_genre_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE PROCEDURE `DeleteLanguage` (IN `p_language_id` INT)   BEGIN
    DELETE FROM `language` WHERE `language_id` = p_language_id;
END$$

CREATE PROCEDURE `DeleteMovie` (IN `p_movie_id` INT(11))   BEGIN
    DELETE FROM `movie`
    WHERE `movie_id` = p_movie_id;
END$$

CREATE PROCEDURE `DeleteMoviesProfileWatchlist` (IN `p_profile_id` INT, IN `p_movie_id` INT)   BEGIN
    DELETE FROM moviesprofilewatchlist
    WHERE profile_id = p_profile_id AND movie_id = p_movie_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE PROCEDURE `DeleteMovieViewCount` (IN `p_account_id` BIGINT(20), IN `p_movie_id` INT)   BEGIN
    DELETE FROM movieviewcount
    WHERE account_id = p_account_id AND movie_id = p_movie_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE PROCEDURE `DeleteProfile` (IN `p_profile_id` INT)   BEGIN
    DELETE FROM `profile` WHERE `profile_id` = p_profile_id;
END$$

CREATE PROCEDURE `DeleteSeries` (IN `p_series_id` INT)   BEGIN
    DELETE FROM `series` WHERE `series_id` = p_series_id;
END$$

CREATE PROCEDURE `DeleteSeriesProfileWatchlist` (IN `p_profile_id` INT, IN `p_series_id` INT)   BEGIN
    DELETE FROM seriesprofilewatchlist
    WHERE profile_id = p_profile_id AND series_id = p_series_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE PROCEDURE `DeleteSeriesViewCount` (IN `p_account_id` BIGINT(20), IN `p_series_id` INT)   BEGIN
    DELETE FROM seriesviewcount
    WHERE account_id = p_account_id AND series_id = p_series_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No rows could have been deleted.';
    END IF;
END$$

CREATE PROCEDURE `DeleteUser` (IN `p_account_id` BIGINT(20))   BEGIN
    DELETE FROM `user` WHERE `account_id` = p_account_id;
END$$

CREATE PROCEDURE `GetEpisodeById` (IN `p_episode_id` INT)   BEGIN
    SELECT * FROM `episode` WHERE `episode_id` = p_episode_id;
END$$

CREATE PROCEDURE `GetGenreById` (IN `p_genre_id` INT)   BEGIN
    SELECT * FROM `genre`
    WHERE `genre_id` = p_genre_id;
END$$

CREATE PROCEDURE `GetGenreForMovie` (IN `p_genre_id` INT, IN `p_movie_id` INT)   BEGIN
    SELECT * FROM `genreformovie`
    WHERE `genre_id` = p_genre_id AND `movie_id` = p_movie_id;
END$$

CREATE PROCEDURE `GetGenreForSeries` (IN `p_genre_id` INT, IN `p_series_id` INT)   BEGIN
    SELECT * FROM `genreforseries`
    WHERE `genre_id` = p_genre_id AND `series_id` = p_series_id;
END$$

CREATE PROCEDURE `GetGenreForUser` (IN `p_genre_id` INT, IN `p_account_id` BIGINT(20))   BEGIN
    SELECT * FROM `genreforuser`
    WHERE `genre_id` = p_genre_id AND `account_id` = p_account_id;
END$$

CREATE PROCEDURE `GetLanguageById` (IN `p_language_id` INT)   BEGIN
    SELECT * FROM `language`
    WHERE `language_id` = p_language_id;
END$$

CREATE PROCEDURE `GetManyEpisodes` ()   BEGIN
    SELECT * FROM `episode`;
END$$

CREATE PROCEDURE `GetManyGenreForMovies` ()   BEGIN
    SELECT * FROM `genreformovie`;
END$$

CREATE PROCEDURE `GetManyGenreForSeries` ()   BEGIN
    SELECT * FROM `genreforseries`;
END$$

CREATE PROCEDURE `GetManyGenreForUsers` ()   BEGIN
    SELECT * FROM `genreforuser`;
END$$

CREATE PROCEDURE `GetManyGenres` ()   BEGIN
    SELECT * FROM `genre`;
END$$

CREATE PROCEDURE `GetManyLanguages` ()   BEGIN
    SELECT * FROM `language`;
END$$

CREATE PROCEDURE `GetManyMovies` ()   BEGIN
    SELECT * FROM `movie`;
END$$

CREATE PROCEDURE `GetManyMoviesProfileWatchlists` ()   BEGIN
    SELECT * FROM `moviesprofilewatchlist`;
END$$

CREATE PROCEDURE `GetManyMovieViewCounts` ()   BEGIN
    SELECT * FROM `movieviewcount`;
END$$

CREATE PROCEDURE `GetManyProfiles` ()   BEGIN
        SELECT * FROM `profile`;
    END$$

CREATE DEFINER=`root`@`%` PROCEDURE `GetManySeries` ()   BEGIN
    SELECT * FROM `series`;
END$$

CREATE PROCEDURE `GetManySeriesProfileWatchlists` ()   BEGIN
    SELECT * FROM `seriesprofilewatchlist`;
END$$

CREATE PROCEDURE `GetManySeriesViewCounts` ()   BEGIN
    SELECT * FROM `seriesviewcount`;
END$$

CREATE PROCEDURE `GetManyUsers` ()   BEGIN
    SELECT * FROM `user`;
END$$

CREATE PROCEDURE `GetMovieById` (IN `p_movie_id` INT)   BEGIN
    SELECT * FROM `movie` WHERE `movie_id` = p_movie_id;
END$$

CREATE PROCEDURE `GetMoviesProfileWatchlist` (IN `p_profile_id` INT, IN `p_movie_id` INT)   BEGIN
    SELECT * FROM moviesprofilewatchlist
    WHERE `profile_id` = p_profile_id AND `movie_id` = p_movie_id;
END$$

CREATE PROCEDURE `GetMovieViewCount` (IN `p_account_id` BIGINT(20), IN `p_movie_id` INT)   BEGIN
    SELECT * FROM movieviewcount
    WHERE `movie_id` = p_movie_id AND `account_id` = p_account_id;
END$$

CREATE PROCEDURE `GetPersonalizedOfferMovies` (IN `userId` BIGINT(20), IN `maxMovies` INT)   BEGIN
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

    SELECT SUM(total_views) INTO totalUserViews
    FROM user_genre_count
    WHERE user_id = userId;

    CREATE TEMPORARY TABLE TempPersonalizedOffer (
        movie_id INT,
        title VARCHAR(255)
    );

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO genreId, genreViews;
        IF done THEN
            LEAVE read_loop;
        END IF;

        SET genreLimit = CEIL((genreViews * maxMovies) / totalUserViews);

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

    SELECT DISTINCT movie_id, title
    FROM TempPersonalizedOffer
    LIMIT maxMovies;

    DROP TEMPORARY TABLE TempPersonalizedOffer;
END$$

CREATE PROCEDURE `GetPersonalizedOfferSeries` (IN `userId` BIGINT(20), IN `maxSeries` INT)   BEGIN
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

    SELECT SUM(total_views) INTO totalUserViews
    FROM user_genre_count
    WHERE user_id = userId;

    CREATE TEMPORARY TABLE TempPersonalizedOffer (
        series_id INT,
        title VARCHAR(255)
    );

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO genreId, genreViews;
        IF done THEN
            LEAVE read_loop;
        END IF;

        SET genreLimit = CEIL((genreViews * maxSeries) / totalUserViews);

        INSERT INTO TempPersonalizedOffer (series_id, title)
        SELECT s.series_id, s.title 
        FROM series s
        JOIN genreforseries gfs ON s.series_id = gfs.series_id
        WHERE gfs.genre_id = genreId
          AND NOT EXISTS (
            SELECT 1
            FROM TempPersonalizedOffer t
            WHERE t.series_id = s.series_id
        )
        ORDER BY RAND()
        LIMIT genreLimit;
    END LOOP;

    CLOSE cur;

    SELECT DISTINCT series_id, title
    FROM TempPersonalizedOffer
    LIMIT maxseries;

    DROP TEMPORARY TABLE TempPersonalizedOffer;
END$$

CREATE PROCEDURE `GetProfilesByAccountId` (IN `p_account_id` INT)   BEGIN
    SELECT * FROM `profile`
    WHERE `account_id` = p_account_id;
END$$

CREATE PROCEDURE `GetProfileById` (IN `p_profile_id` INT)   BEGIN
    SELECT * FROM `profile`
    WHERE `profile_id` = p_profile_id;
END$$

CREATE PROCEDURE `GetSeriesById` (IN `p_series_id` INT(11))   BEGIN
    SELECT * FROM `series` WHERE `series_id` = p_series_id;
END$$

CREATE PROCEDURE `GetSeriesProfileWatchlist` (IN `p_profile_id` INT, IN `p_series_id` INT)   BEGIN
    SELECT * FROM seriesprofilewatchlist
    WHERE `profile_id` = p_profile_id AND `eries_id` = p_series_id;
END$$

CREATE PROCEDURE `GetSeriesViewCount` (IN `p_account_id` BIGINT(20), IN `p_series_id` INT)   BEGIN
    SELECT * FROM seriesviewcount
    WHERE `series_id` = p_series_id AND `account_id` = p_account_id;
END$$

CREATE PROCEDURE `GetUserByEmail` (IN `p_email` VARCHAR(255))   BEGIN
    SELECT * FROM user WHERE email = p_email COLLATE utf8mb4_unicode_ci LIMIT 1;
END$$

CREATE PROCEDURE `GetUserById` (IN `p_account_id` BIGINT(20))   BEGIN
    SELECT * FROM `user` WHERE `account_id` = p_account_id;
END$$

CREATE PROCEDURE `InsertExpiredTrialsIntoPayments` ()   BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE userId BIGINT (20);
    DECLARE subscriptionType ENUM('SD', 'HD', 'UHD');
    DECLARE discountApplied BIT(1);
    DECLARE cur CURSOR FOR
        SELECT account_id, subscription, discount
        FROM `user`
        WHERE `trial_end_date` <= CURDATE() AND `active` = b'1';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO userId, subscriptionType, discountApplied;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Then insert into payments table
        INSERT INTO `payments` (`account_id`, `subscription_type`, `payment_amount`, `is_discount_applied`, `is_paid`, `payment_date`, `next_billing_date`)
        VALUES (
            userId,
            subscriptionType,
            CASE 
                WHEN subscriptionType = 'SD' THEN 7.99 
                WHEN subscriptionType = 'HD' THEN 10.99 
                WHEN subscriptionType = 'UHD' THEN 13.99 
            END - 
            CASE 
                WHEN discountApplied = b'1' THEN 2.00 
                ELSE 0 
            END,
            discountApplied,
            b'0',
            NOW(),
            DATE_ADD(NOW(), INTERVAL 1 MONTH)
        );
    END LOOP;

    CLOSE cur;
END$$

CREATE PROCEDURE `PatchEpisode` (IN `p_episode_id` INT, IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_series_id` INT)   BEGIN
    UPDATE `episode`
    SET 
        `title` = COALESCE(p_title, `title`),
        `duration` = COALESCE(p_duration, `duration`),
        `series_id` = COALESCE(p_series_id, `series_id`)
    WHERE `episode_id` = p_episode_id;
END$$

CREATE PROCEDURE `PatchGenreForMovie` (IN `p_old_genre_id` INT, IN `p_old_movie_id` INT, IN `p_new_genre_id` INT, IN `p_new_movie_id` INT)   BEGIN
    UPDATE genreformovie
    SET
        genre_id = IFNULL(p_new_genre_id, genre_id),
        movie_id = IFNULL(p_new_movie_id, movie_id)
    WHERE genre_id = p_old_genre_id AND movie_id = p_old_movie_id;
END$$

CREATE PROCEDURE `PatchGenreForSeries` (IN `p_old_genre_id` INT, IN `p_old_series_id` INT, IN `p_new_genre_id` INT, IN `p_new_series_id` INT)   BEGIN
    UPDATE genreforseries
    SET
        genre_id = IFNULL(p_new_genre_id, genre_id),
        series_id = IFNULL(p_new_series_id, series_id)
    WHERE genre_id = p_old_genre_id AND series_id = p_old_series_id;
END$$

CREATE PROCEDURE `PatchGenreForUser` (IN `p_old_user_id` INT, IN `p_old_genre_id` INT, IN `p_new_user_id` INT, IN `p_new_genre_id` INT)   BEGIN

    UPDATE GenreForUser
    SET
        genre_id = IFNULL(p_new_genre_id, genre_id),  
        user_id = IFNULL(p_new_user_id, user_id)     
    WHERE user_id = p_old_user_id AND genre_id = p_old_genre_id;
END$$

CREATE PROCEDURE `PatchMovie` (IN `p_movie_id` INT(11), IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_sd_available` BIT(1), IN `p_hd_available` BIT(1), IN `p_uhd_available` BIT(1), IN `p_minimum_age` INT(3))   BEGIN
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

CREATE PROCEDURE `PatchMoviesProfileWatchlist` (IN `p_old_profile_id` INT, IN `p_old_movie_id` INT, IN `p_new_profile_id` INT, IN `p_new_movie_id` INT)   BEGIN
    UPDATE moviesprofilewatchlist
    SET
        profile_id = IFNULL(p_new_profile_id, profile_id),
        movie_id = IFNULL(p_new_movie_id, movie_id)
    WHERE profile_id = p_old_profile_id AND movie_id = p_old_movie_id;
END$$

CREATE PROCEDURE `PatchMovieViewCount` (IN `p_account_id` BIGINT(20), IN `p_movie_id` INT, IN `p_new_number` INT, IN `p_new_last_viewed` DATETIME)   BEGIN
    UPDATE movieviewcount
    SET
        number = IFNULL(p_new_number, number),
        last_viewed = IFNULL(p_new_last_viewed, last_viewed)
    WHERE account_id = p_account_id AND movie_id = p_movie_id;
END$$

CREATE PROCEDURE `PatchProfile` (IN `p_profile_id` INT(11), IN `p_account_id` BIGINT(20), IN `p_profile_image` VARCHAR(255), IN `p_age` INT(3), IN `p_name` VARCHAR(255))   BEGIN
    UPDATE `profile`
    SET 
        `account_id` = COALESCE(p_account_id, `account_id`),
        `profile_image` = COALESCE(p_profile_image, `profile_image`),
        `age` = COALESCE(p_age, `age`),
        `name` = COALESCE(p_name, `name`)
    WHERE `profile_id` = p_profile_id;
END$$

CREATE PROCEDURE `PatchSeries` (IN `p_series_id` INT(11), IN `p_title` VARCHAR(255), IN `p_minimum_age` INT(3))   BEGIN
    UPDATE `series`
    SET 
        `title` = COALESCE(p_title, `title`),
        `minimum_age` = COALESCE(p_minimum_age, `minimum_age`)
    WHERE `series_id` = p_series_id;
END$$

CREATE PROCEDURE `PatchSeriesProfileWatchlist` (IN `p_old_profile_id` INT, IN `p_old_series_id` INT, IN `p_new_profile_id` INT, IN `p_new_series_id` INT)   BEGIN
    UPDATE seriesprofilewatchlist
    SET
        profile_id = IFNULL(p_new_profile_id, profile_id),
        series_id = IFNULL(p_new_series_id, series_id)
    WHERE profile_id = p_old_profile_id AND series_id = p_old_series_id;
END$$

CREATE PROCEDURE `PatchSeriesViewCount` (IN `p_account_id` BIGINT(20), IN `p_series_id` INT, IN `p_new_number` INT, IN `p_new_last_viewed` DATETIME)   BEGIN
    UPDATE seriesviewcount
    SET
        number = IFNULL(p_new_number, number),
        last_viewed = IFNULL(p_new_last_viewed, last_viewed)
    WHERE account_id = p_account_id AND series_id = p_series_id;
END$$

CREATE PROCEDURE `PatchUser` (IN `p_account_id` BIGINT(20), IN `p_password` VARCHAR(255), IN `p_payment_method` VARCHAR(255), IN `p_active` BIT(1), IN `p_blocked` BIT(1), IN `p_subscription` ENUM('SD','HD','UHD'), IN `p_trial_start_date` DATETIME, IN `p_trial_end_date` DATETIME, IN `p_language_id` INT(11), IN `p_role` ENUM('VIEWER','JUNIOR','MEDIOR','SENIOR'), IN `p_failed_attempts` INT(11), IN `p_lock_time` DATETIME, IN `p_discount` BIT(1))   BEGIN
    UPDATE `user`
    SET 
        `password` = COALESCE(p_password, `password`),
        `payment_method` = COALESCE(p_payment_method, `payment_method`),
        `active` = COALESCE(p_active, `active`),
        `blocked` = COALESCE(p_blocked, `blocked`),
        `subscription` = COALESCE(p_subscription, `subscription`),
        `trial_start_date` = COALESCE(p_trial_start_date, `trial_start_date`),
        `trial_end_date`= COALESCE(p_trial_end_date, `trial_end_date`),
        `language_id` = COALESCE(p_language_id, `language_id`), 
        `role` = COALESCE(p_role, `role`), 
        `failed_attempts` = COALESCE(p_failed_attempts, `failed_attempts`), 
        `lock_time` = COALESCE(p_lock_time, `lock_time`), 
        `discount` = COALESCE(p_discount, `discount`)
    WHERE `account_id` = p_account_id;
END$$

CREATE PROCEDURE `process_payment` (IN `userId` INT, IN `subscriptionType` VARCHAR(10), IN `discountApplied` BIT)   BEGIN
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

CREATE PROCEDURE `UpdateEpisode` (IN `p_episode_id` INT, IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_series_id` INT)   BEGIN
    UPDATE `episode`
    SET 
        `title` = p_title,
        `duration` = IFNULL(p_duration, '00:00:00'),
        `series_id` = p_series_id
    WHERE `episode_id` = p_episode_id;
END$$

CREATE PROCEDURE `UpdateGenre` (IN `p_genre_id` INT, IN `p_genre_name` VARCHAR(255))   BEGIN
    UPDATE `genre`
    SET `genre_name` = p_genre_name
    WHERE `genre_id` = p_genre_id;
END$$

CREATE PROCEDURE `UpdateGenreForMovie` (IN `p_old_genre_id` INT, IN `p_old_movie_id` INT, IN `p_new_genre_id` INT, IN `p_new_movie_id` INT)   BEGIN
    UPDATE genreformovie
    SET
        genre_id = p_new_genre_id,
        movie_id = p_new_movie_id
    WHERE genre_id = p_old_genre_id AND movie_id = p_old_movie_id;
END$$

CREATE PROCEDURE `UpdateGenreForSeries` (IN `p_old_genre_id` INT, IN `p_old_series_id` INT, IN `p_new_genre_id` INT, IN `p_new_series_id` INT)   BEGIN
    UPDATE genreforseries
    SET
        genre_id = p_new_genre_id,
        series_id = p_new_series_id
    WHERE genre_id = p_old_genre_id AND series_id = p_old_series_id;
END$$

CREATE PROCEDURE `UpdateGenreForUser` (IN `p_old_user_id` INT, IN `p_old_genre_id` INT, IN `p_new_user_id` INT, IN `p_new_genre_id` INT)   BEGIN

    UPDATE GenreForUser
    SET
        genre_id = p_new_genre_id,  
        user_id = p_new_user_id     
    WHERE user_id = p_old_user_id AND genre_id = p_old_genre_id;
END$$

CREATE PROCEDURE `UpdateLanguage` (IN `p_language_id` INT(11), IN `p_name` VARCHAR(255))   BEGIN
    UPDATE `language`
    SET `name` = p_name
    WHERE `language_id` = p_language_id;
END$$

CREATE PROCEDURE `UpdateMovie` (IN `p_movie_id` INT(11), IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_sd_available` BIT(1), IN `p_hd_available` BIT(1), IN `p_uhd_available` BIT(1), IN `p_minimum_age` INT(3))   BEGIN
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

CREATE PROCEDURE `UpdateMovieProfileWatchlist` (IN `p_old_profile_id` INT, IN `p_old_movie_id` INT, IN `p_new_profile_id` INT, IN `p_new_movie_id` INT)   BEGIN
    UPDATE movieprofilewatchlist
    SET
        profile_id = p_new_profile_id,
        movie_id = p_new_movie_id
    WHERE profile_id = p_old_profile_id AND movie_id = p_old_movie_id;
END$$

CREATE PROCEDURE `UpdateMovieViewCount` (IN `p_account_id` BIGINT(20), IN `p_movie_id` INT, IN `p_new_number` INT, IN `p_new_last_viewed` DATETIME)   BEGIN
    UPDATE movieviewcount
    SET
        number = p_new_number,
        last_viewed = p_new_last_viewed
    WHERE account_id = p_account_id AND movie_id = p_movie_id;
END$$

CREATE PROCEDURE `UpdateProfile` (IN `p_profile_id` INT(11), IN `p_accountId` INT(11), IN `p_profile_image` VARCHAR(255), IN `p_age` INT(3), IN `p_name` VARCHAR(255))   BEGIN
    UPDATE `profile`
    SET 
        `account_id` = p_account_id, 
        `profile_image` = p_profile_image, 
        `age` = p_age, 
        `name` = p_name 
    WHERE `profile_id` = p_profile_id;
END$$

CREATE PROCEDURE `UpdateSeries` (IN `p_series_id` INT(11), IN `p_title` VARCHAR(255), IN `p_minimum_age` INT(3))   BEGIN
    UPDATE `series`
    SET 
        `title` = p_title,
        `minimum_age` = p_minimum_age
    WHERE `series_id` = p_series_id;
END$$

CREATE PROCEDURE `UpdateSeriesProfileWatchlist` (IN `p_old_profile_id` INT, IN `p_old_series_id` INT, IN `p_new_profile_id` INT, IN `p_new_series_id` INT)   BEGIN
    UPDATE seriesprofilewatchlist
    SET
        profile_id = p_new_profile_id,
        series_id = p_new_series_id
    WHERE profile_id = p_old_profile_id AND series_id = p_old_series_id;
END$$

CREATE PROCEDURE `UpdateSeriesViewCount` (IN `p_account_id` BIGINT(20), IN `p_series_id` INT, IN `p_new_number` INT, IN `p_new_last_viewed` DATETIME)   BEGIN
    UPDATE seriesviewcount
    SET
        number = p_new_number,
        last_viewed = p_new_last_viewed
    WHERE account_id = p_account_id AND series_id = p_series_id;
END$$

CREATE PROCEDURE `UpdateUser` (IN `p_account_id` BIGINT(20), IN `p_password` VARCHAR(255), IN `p_payment_method` VARCHAR(255), IN `p_active` BIT(1), IN `p_blocked` BIT(1), IN `p_subscription` ENUM('SD','HD','UHD'), IN `p_trial_start_date` DATETIME, IN `p_trial_end_date` DATETIME, IN `p_language_id` INT(11), IN `p_role` ENUM('VIEWER','JUNIOR','MEDIOR','SENIOR'), IN `p_failed_attempts` INT(11), IN `p_lock_time` DATETIME, IN `p_discount` BIT(1))   BEGIN
    UPDATE `user`
    SET 
        `password` = p_password,
        `active` = p_active,
        `payment_method` = p_payment_method,
        `blocked` = p_blocked,
        `subscription` = p_subscription,
        `trial_start_date` = p_trial_start_date,
        `trial_end_date` = p_trial_end_date,
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
-- Table structure for table `episode`
--

CREATE TABLE `episode` (
  `episode_id` int UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `duration` time DEFAULT '00:00:00',
  `series_id` int UNSIGNED DEFAULT NULL,
  `series` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `episode`
--

INSERT INTO `episode` (`episode_id`, `title`, `duration`, `series_id`, `series`) VALUES
(2, 'Dom is vanished', '00:23:00', 2, NULL),
(3, 'Dom is BACK', '00:23:00', 2, NULL),
(4, 'Dom is SCREWED', '00:23:00', 2, NULL),
(5, 'Dom is dumb', '00:23:00', 2, NULL),
(6, 'Dom is cooked', '00:23:00', 2, NULL),
(7, 'Dom is doing things', '00:23:00', 2, NULL),
(8, 'Dom is revenging', '00:23:00', 2, NULL),
(9, 'Dom is gone', '00:23:00', 2, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `genre_id` int UNSIGNED NOT NULL,
  `genre_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
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
  `genre_id` int UNSIGNED NOT NULL,
  `movie_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `genreformovie`
--

INSERT INTO `genreformovie` (`genre_id`, `movie_id`) VALUES
(2, 1),
(5, 1),
(1, 3),
(2, 3),
(3, 3),
(8, 3);

-- --------------------------------------------------------

--
-- Table structure for table `genreforseries`
--

CREATE TABLE `genreforseries` (
  `genre_id` int UNSIGNED NOT NULL,
  `series_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `genreforseries`
--

INSERT INTO `genreforseries` (`genre_id`, `series_id`) VALUES
(1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `genreforuser`
--

CREATE TABLE `genreforuser` (
  `genre_id` int UNSIGNED NOT NULL,
  `account_id` bigint UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `genreforuser`
--

INSERT INTO `genreforuser` (`genre_id`, `account_id`) VALUES
(1, 2),
(1, 3);

-- --------------------------------------------------------

--
-- Stand-in structure for view `genres_without_movie`
-- (See below for the actual view)
--
CREATE TABLE `genres_without_movie` (
`genre_id` int unsigned
,`genre_name` varchar(255)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `genre_total_views`
-- (See below for the actual view)
--
CREATE TABLE `genre_total_views` (
`genre_id` int unsigned
,`genre_name` varchar(255)
,`total_views` decimal(55,0)
);

-- --------------------------------------------------------

--
-- Table structure for table `invitation`
--

CREATE TABLE `invitation` (
  `id` bigint NOT NULL,
  `inviter_id` bigint UNSIGNED NOT NULL,
  `invitee_id` bigint UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invitation`
--

INSERT INTO `invitation` (`id`, `inviter_id`, `invitee_id`, `created_at`) VALUES
(1, 23, 24, '2025-01-17 13:47:07');

-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE `language` (
  `language_id` int UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `language`
--

INSERT INTO `language` (`language_id`, `name`) VALUES
(1, 'american'),
(2, 'latvian'),
(3, 'lithuanian'),
(4, 'dutch'),
(5, 'spanish'),
(6, 'ukranian'),
(7, 'Swedish'),
(8, 'indonesian');


-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `movie_id` int UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `duration` time NOT NULL DEFAULT '00:00:00',
  `sd_available` bit(1) DEFAULT b'1',
  `hd_available` bit(1) DEFAULT b'0',
  `uhd_available` bit(1) DEFAULT b'0',
  `minimum_age` int NOT NULL DEFAULT '12'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`movie_id`, `title`, `duration`, `sd_available`, `hd_available`, `uhd_available`, `minimum_age`) VALUES
(1, 'woops', '01:32:00', b'1', b'1', b'0', 16),
(3, 'star wars', '00:31:16', b'1', b'1', b'0', 12),
(4, 'Home alone', '02:22:00', b'1', b'1', b'0', 10),
(5, 'The terminal 3', '02:15:00', b'1', b'1', b'0', 16),
(6, 'No more ice cream', '01:42:00', b'1', b'1', b'0', 16),
(7, 'Bob Mailey tribute', '03:22:00', b'1', b'1', b'0', 14),
(8, 'Top Gun', '01:22:00', b'1', b'1', b'0', 12),
(9, 'Tom & Jerry (untold story)', '02:22:00', b'1', b'1', b'0', 9),
(10, 'Top Gun: Marverick', '02:32:00', b'1', b'1', b'0', 16),
(11, 'American Pie', '01:22:00', b'1', b'1', b'0', 16),
(12, 'Stupid Dog', '02:02:00', b'1', b'1', b'0', 16),
(13, 'fast X', '02:30:00', b'1', b'1', b'0', 16);

-- --------------------------------------------------------

--
-- Table structure for table `moviesprofilewatchlist`
--

CREATE TABLE `moviesprofilewatchlist` (
  `profile_id` int UNSIGNED NOT NULL,
  `movie_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `moviesprofilewatchlist`
--

INSERT INTO `moviesprofilewatchlist` (`profile_id`, `movie_id`) VALUES
(2, 1);

-- --------------------------------------------------------

--
-- Stand-in structure for view `movies_without_genre`
-- (See below for the actual view)
--
CREATE TABLE `movies_without_genre` (
`movie_id` int unsigned
,`title` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `movieviewcount`
--

CREATE TABLE `movieviewcount` (
  `account_id` bigint UNSIGNED NOT NULL,
  `movie_id` int UNSIGNED NOT NULL,
  `number` int DEFAULT NULL,
  `last_viewed` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movieviewcount`
--

INSERT INTO `movieviewcount` (`account_id`, `movie_id`, `number`, `last_viewed`) VALUES
(1, 1, 1, '2025-01-17 12:00:00'),
(1, 3, 1, NULL),
(2, 1, 5, '2025-01-17 18:57:00'),
(2, 3, 1, '2025-01-17 13:20:31'),
(2, 4, 1, '2025-01-17 13:27:03'),
(3, 1, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `payment_id` bigint NOT NULL,
  `account_id` bigint UNSIGNED NOT NULL,
  `is_discount_applied` bit(1) DEFAULT b'0',
  `is_paid` bit(1) DEFAULT b'0',
  `payment_date` datetime DEFAULT NULL,
  `subscription_type` enum('SD','HD','UHD') COLLATE utf8mb4_general_ci DEFAULT 'SD',
  `payment_amount` double NOT NULL,
  `next_billing_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`payment_id`, `account_id`, `is_discount_applied`, `is_paid`, `payment_date`, `subscription_type`, `payment_amount`, `next_billing_date`) VALUES
(1, 18, b'0', b'0', '2025-01-17 19:44:33', 'SD', 7.99, '2025-02-17 19:44:33'),
(2, 19, b'0', b'0', '2025-01-17 19:44:33', 'SD', 7.99, '2025-02-17 19:44:33');

-- --------------------------------------------------------

--
-- Stand-in structure for view `paymentstatus`
-- (See below for the actual view)
--
CREATE TABLE `paymentstatus` (
`payment_id` bigint
,`account_id` bigint unsigned
,`email` varchar(255)
,`subscription_type` enum('SD','HD','UHD')
,`payment_amount` double
,`is_paid` bit(1)
,`is_discount_applied` bit(1)
,`payment_date` datetime
,`next_billing_date` datetime
);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `profile_id` int UNSIGNED NOT NULL,
  `account_id` bigint UNSIGNED DEFAULT NULL,
  `profile_image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `age` int NOT NULL DEFAULT '0',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `account` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`profile_id`, `account_id`, `profile_image`, `age`, `name`, `account`) VALUES
(2, 2, 'abcdefg.png', 12, 'tupoy loshara', NULL),
(3, NULL, 'http://example.com/image.jpg', 30, 'JimmyJ', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `series`
--

CREATE TABLE `series` (
  `series_id` int UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `minimum_age` int NOT NULL DEFAULT '12'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `series`
--

INSERT INTO `series` (`series_id`, `title`, `minimum_age`) VALUES
(2, 'Sex education', 18),
(3, 'Costa', 18),
(4, 'Suits', 18),
(5, 'Wwe', 18),
(6, 'Mommy I have nuts', 18),
(7, 'Zhi has a chili', 18),
(8, 'Hangover 2', 18),
(9, 'You are nuts', 18),
(10, 'APIs are retarded', 18);

-- --------------------------------------------------------

--
-- Table structure for table `seriesprofilewatchlist`
--

CREATE TABLE `seriesprofilewatchlist` (
  `profile_id` int UNSIGNED NOT NULL,
  `series_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seriesprofilewatchlist`
--

INSERT INTO `seriesprofilewatchlist` (`profile_id`, `series_id`) VALUES
(2, 2),
(2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `seriesviewcount`
--

CREATE TABLE `seriesviewcount` (
  `account_id` bigint UNSIGNED NOT NULL,
  `series_id` int UNSIGNED NOT NULL,
  `number` int DEFAULT NULL,
  `last_viewed` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seriesviewcount`
--

INSERT INTO `seriesviewcount` (`account_id`, `series_id`, `number`, `last_viewed`) VALUES
(2, 3, 5, '2025-01-17 18:57:00');

-- --------------------------------------------------------

--
-- Stand-in structure for view `subscription_cost`
-- (See below for the actual view)
--
CREATE TABLE `subscription_cost` (
`account_id` bigint unsigned
,`subscription` enum('SD','HD','UHD')
,`email` varchar(255)
,`payment_method` varchar(255)
,`subscription_cost` decimal(4,2)
);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `account_id` bigint UNSIGNED NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `payment_method` varchar(255) COLLATE utf8mb4_general_ci DEFAULT 'Credit Card',
  `active` bit(1) DEFAULT b'0',
  `blocked` bit(1) DEFAULT b'0',
  `subscription` enum('SD','HD','UHD') COLLATE utf8mb4_general_ci DEFAULT 'SD',
  `trial_start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `trial_end_date` datetime DEFAULT NULL,
  `language_id` int UNSIGNED DEFAULT NULL,
  `role` enum('VIEWER','JUNIOR','MEDIOR','SENIOR') COLLATE utf8mb4_general_ci DEFAULT 'VIEWER',
  `failed_attempts` int DEFAULT '0',
  `lock_time` datetime DEFAULT NULL,
  `discount` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `active`, `blocked`, `subscription`, `trial_start_date`, `trial_end_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES
(1, 'fjodor.smorodins@gmail.com', '$2a$10$hszeHDUNOv4lnd24ZS9sOeOkOJUYo5zSi2H2makEPti1uznr4s5P2', 'abc', b'0', b'0', 'SD', '2024-12-07 14:32:59', '2024-12-14 14:32:59', 4, 'SENIOR', 0, NULL, b'0'),
(2, 'fjodorsm@gmail.com', '$2a$10$2QlecdJ25ELwT/avANQAUelbxtS9tysiRO5LSE0omLATaWhdAPfZC', 'Credit Card', b'0', b'0', 'SD', '2024-12-07 14:33:33', '2024-12-14 14:33:33', 1, 'JUNIOR', 0, NULL, b'0'),
(3, 'smorodins@gmail.com', '$2a$10$KhhGnFeK2q32DYG7/fMhNe/GEzf1dDJVkQqq5isK1vwuIO9h0zor.', 'CrC', b'0', b'0', 'SD', '2024-12-16 18:54:30', '2024-12-23 18:54:30', 3, 'VIEWER', 0, NULL, b'0'),
(5, 'fjodors@hello.com', '$2a$10$hKsRL99MRpKUr.vrJPqsfuG3qhGkDjXQEYDxHytWFBYgW7HZJ/54W', 'golden bars', b'0', b'0', 'SD', '2024-12-20 16:19:25', '2024-12-27 16:19:25', 2, 'VIEWER', 0, NULL, b'0'),
(6, 'artjoms.grishajevs@hello.com', '$2a$10$NboUZOHniHtnfHhFFECcF.dA64uJsp.8/OnD0B0NEuMvTyvIfN7we', 'children', b'0', b'0', 'SD', '2024-12-20 16:24:58', '2024-12-27 16:24:58', 1, 'JUNIOR', 0, NULL, b'0'),
(7, 'somebody@hello.com', '$2a$10$4H41Ugw1ho9ga4DfTV1rwegl.uxbZcTbEu3/SBeklNzsHnXoYliTe', 'money', b'0', b'0', 'SD', '2024-12-20 17:08:59', '2024-12-27 17:08:59', 1, 'VIEWER', 0, NULL, b'0'),
(9, 'somepersonwhatever@hello.com', '$2a$10$DhZSCWySz9rypM/jM8mR6.yzaCPIpugVlITMSWx9whkmEp1ciPK42', 'something', b'0', b'0', 'SD', '2024-12-20 17:24:39', '2024-12-27 17:24:39', 2, 'VIEWER', 0, NULL, b'0'),
(10, 'iamsteve@hello.com', '$2a$10$92qxixAWTf94z9sK.Lf2iebtyLdBV9ckOx.xfzGLv4enlX5gdsis6', 'mastercard', b'0', b'0', 'SD', '2024-12-20 17:58:22', '2024-12-27 17:58:22', 3, 'VIEWER', 0, NULL, b'0'),
(15, 'test1@.com', '$2a$10$aP97IvFmxH8yLGuL1012Xe4sfLd6s1SdokAAKOhG3.tvWCTkmfD2.', 'some method', b'0', b'0', 'SD', '2024-12-20 22:46:29', '2024-12-27 22:46:29', 3, 'VIEWER', 1, NULL, b'0'),
(17, 'medior.fjodor@g.com', '$2a$10$gQuhxuEegp0Ypg.IrGiL8.bmQwV4sdMzXirKh7N0N4KbOXAq4xwFi', 'some money transfer method', b'0', b'0', 'SD', '2024-12-23 17:55:18', '2024-12-30 17:55:18', 3, 'JUNIOR', 0, NULL, b'0'),
(18, 'billyJ@outlook.com', '$2a$10$3', 'IDEAL', b'1', b'0', 'SD', '2024-12-23 18:00:00', '2024-12-30 18:00:00', 3, 'JUNIOR', 0, NULL, b'0'),
(19, 'random.user@gg.com', '$2a$10$MOir8H30oJGVlQTrcKrBZOi8CNBD5oYACgjg5/3TcOPyWwSP9TLYi', 'a transfer method', b'1', b'0', 'SD', '2025-01-06 15:54:13', '2025-01-13 15:54:13', 1, 'JUNIOR', 0, NULL, b'0'),
(20, 'zhichengliang@gmail.com', '$2a$10$XvJIvdYAT9IXdMeA2VpuR.11JGQlt/3yVwGVTS8BU2ZjNUeS6da..', 'PayPal', b'0', b'0', 'UHD', '2025-01-17 10:33:29', NULL, 1, 'SENIOR', 0, NULL, b'0'),
(21, 'zhizhi@gmail.com', '$2a$10$i69jO1ulvaMpmWhfquAWTeRin88ooKwGuidwBN4Gxfi3.VE3DKp82', 'iDEAL', b'0', b'0', 'HD', '2025-01-17 12:21:38', NULL, 1, 'VIEWER', 0, NULL, b'0'),
(22, 'billJen2@outlook.com', '$2a$10$RYVZBqoWVgcTgO6ngheslO8XVIT9WxBzSqoobyX6L6ci/c3nTnKq.', 'IDEAL', b'0', b'0', 'HD', '2025-01-17 13:24:22', NULL, 1, 'VIEWER', 0, NULL, b'0'),
(23, 'billJen4@outlook.com', '$2a$10$LeuRelq82wLgCr0eoaXmp.VEk6FRZiloihP9W92v3gy/ATwydrCBO', 'IDEAL', b'1', b'0', 'HD', '2025-01-17 13:28:55', NULL, 1, 'VIEWER', 0, NULL, b'1'),
(24, 'billJen5@outlook.com', '$2a$10$oWiphCEfMAbJK9tRAsiejuSawwGw6TgO.hJIGSdZ663vWJhRntW0q', 'IDEAL', b'1', b'0', 'HD', '2025-01-17 13:34:45', NULL, 1, 'VIEWER', 0, NULL, b'1');

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_for_junior`
-- (See below for the actual view)
--
CREATE TABLE `user_for_junior` (
`account_id` bigint unsigned
,`trial_start_date` datetime
,`trial_end_date` datetime
,`language_id` int unsigned
,`role` enum('VIEWER','JUNIOR','MEDIOR','SENIOR')
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_genre_count`
-- (See below for the actual view)
--
CREATE TABLE `user_genre_count` (
`user_id` bigint unsigned
,`genre_id` int unsigned
,`genre_name` varchar(255)
,`total_views` decimal(33,0)
);

-- --------------------------------------------------------

--
-- Structure for view `genres_without_movie`
--
DROP TABLE IF EXISTS `genres_without_movie`;

CREATE VIEW `genres_without_movie`  AS SELECT `g`.`genre_id` AS `genre_id`, `g`.`genre_name` AS `genre_name` FROM (`genre` `g` left join `genreformovie` `gfm` on((`g`.`genre_id` = `gfm`.`genre_id`))) WHERE (`gfm`.`movie_id` is null) ;

-- --------------------------------------------------------

--
-- Structure for view `genre_total_views`
--
DROP TABLE IF EXISTS `genre_total_views`;

CREATE VIEW `genre_total_views`  AS SELECT `user_genre_count`.`genre_id` AS `genre_id`, `user_genre_count`.`genre_name` AS `genre_name`, sum(`user_genre_count`.`total_views`) AS `total_views` FROM `user_genre_count` GROUP BY `user_genre_count`.`genre_id` ORDER BY `total_views` DESC ;

-- --------------------------------------------------------

--
-- Structure for view `movies_without_genre`
--
DROP TABLE IF EXISTS `movies_without_genre`;

CREATE VIEW `movies_without_genre`  AS SELECT `m`.`movie_id` AS `movie_id`, `m`.`title` AS `title` FROM (`movie` `m` left join `genreformovie` `gfm` on((`m`.`movie_id` = `gfm`.`movie_id`))) WHERE (`gfm`.`genre_id` is null) ;

-- --------------------------------------------------------

--
-- Structure for view `paymentstatus`
--
DROP TABLE IF EXISTS `paymentstatus`;

CREATE VIEW `paymentstatus`  AS SELECT `p`.`payment_id` AS `payment_id`, `u`.`account_id` AS `account_id`, `u`.`email` AS `email`, `p`.`subscription_type` AS `subscription_type`, `p`.`payment_amount` AS `payment_amount`, `p`.`is_paid` AS `is_paid`, `p`.`is_discount_applied` AS `is_discount_applied`, `p`.`payment_date` AS `payment_date`, `p`.`next_billing_date` AS `next_billing_date` FROM (`payments` `p` join `user` `u` on((`p`.`account_id` = `u`.`account_id`))) ;

-- --------------------------------------------------------

--
-- Structure for view `subscription_cost`
--
DROP TABLE IF EXISTS `subscription_cost`;

CREATE VIEW `subscription_cost` AS
SELECT 
    `subquery`.`account_id` AS `account_id`, 
    `subquery`.`subscription` AS `subscription`, 
    `subquery`.`email` AS `email`, 
    `subquery`.`payment_method` AS `payment_method`, 
    `subquery`.`subscription_cost` AS `subscription_cost`
FROM (
    SELECT 
        `u`.`account_id` AS `account_id`,
        `u`.`subscription` AS `subscription`,
        `u`.`email` AS `email`,
        `u`.`payment_method` AS `payment_method`,
        (CASE 
            WHEN (`u`.`role` IN ('Junior','Medior','Senior')) THEN 0 
            WHEN (`u`.`discount` = 1) THEN -(2.00) 
            WHEN (`u`.`subscription` = 'SD') THEN 7.99 
            WHEN (`u`.`subscription` = 'HD') THEN 10.99 
            WHEN (`u`.`subscription` = 'UHD') THEN 13.99 
        END) AS `subscription_cost`
    FROM `user` `u`
) AS `subquery`
WHERE (`subquery`.`subscription_cost` > 0);
-- --------------------------------------------------------

--
-- Structure for view `user_for_junior`
--
DROP TABLE IF EXISTS `user_for_junior`;

CREATE VIEW `user_for_junior`  AS SELECT `user`.`account_id` AS `account_id`, `user`.`trial_start_date` AS `trial_start_date`, `user`.`trial_end_date` AS `trial_end_date`, `user`.`language_id` AS `language_id`, `user`.`role` AS `role` FROM `user` ;

-- --------------------------------------------------------

--
-- Structure for view `user_genre_count`
--
DROP TABLE IF EXISTS `user_genre_count`;

CREATE VIEW `user_genre_count`  AS SELECT `mvc`.`account_id` AS `user_id`, `g`.`genre_id` AS `genre_id`, `g`.`genre_name` AS `genre_name`, (ifnull(sum(`mvc`.`number`),0) + ifnull(sum(`svc`.`number`),0)) AS `total_views` FROM ((((((`genre` `g` left join `genreformovie` `mg` on((`g`.`genre_id` = `mg`.`genre_id`))) left join `movie` `m` on((`mg`.`movie_id` = `m`.`movie_id`))) left join `movieviewcount` `mvc` on((`m`.`movie_id` = `mvc`.`movie_id`))) left join `genreforseries` `gfs` on((`g`.`genre_id` = `gfs`.`genre_id`))) left join `series` `s` on((`gfs`.`series_id` = `s`.`series_id`))) left join `seriesviewcount` `svc` on(((`s`.`series_id` = `svc`.`series_id`) and (`svc`.`account_id` = `mvc`.`account_id`)))) GROUP BY `mvc`.`account_id`, `g`.`genre_id` ORDER BY `mvc`.`account_id` ASC, (ifnull(sum(`mvc`.`number`),0) + ifnull(sum(`svc`.`number`),0)) DESC ;

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
  ADD KEY `fk_genreformovie_movie` (`movie_id`);

--
-- Indexes for table `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD PRIMARY KEY (`genre_id`,`series_id`),
  ADD KEY `fk_genreforseries_series` (`series_id`);

--
-- Indexes for table `genreforuser`
--
ALTER TABLE `genreforuser`
  ADD PRIMARY KEY (`genre_id`,`account_id`),
  ADD KEY `fk_genreforuser_user` (`account_id`);

--
-- Indexes for table `invitation`
--
ALTER TABLE `invitation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `inviter_user` (`inviter_id`),
  ADD KEY `invitee_user` (`invitee_id`);

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
  ADD KEY `fk_moviesprofilewatchlist_movie` (`movie_id`);

--
-- Indexes for table `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD PRIMARY KEY (`account_id`,`movie_id`),
  ADD KEY `fk_movieviewcount_movie` (`movie_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `fk_payments_user` (`account_id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`),
  ADD KEY `fk_user` (`account_id`);

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
  ADD KEY `fk_seriesprofilewatchlist_series` (`series_id`);

--
-- Indexes for table `seriesviewcount`
--
ALTER TABLE `seriesviewcount`
  ADD PRIMARY KEY (`account_id`,`series_id`),
  ADD KEY `fk_seriesviewcount_user` (`account_id`),
  ADD KEY `fk_seriesviewcount_series` (`series_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `user_language` (`language_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `episode`
--
ALTER TABLE `episode`
  MODIFY `episode_id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `invitation`
--
ALTER TABLE `invitation`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `language`
--
ALTER TABLE `language`
  MODIFY `language_id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `payment_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `series`
--
ALTER TABLE `series`
  MODIFY `series_id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `account_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

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
  ADD CONSTRAINT `fk_genreformovie_genre` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_genreformovie_movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD CONSTRAINT `fk_genreforseries_genre` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_genreforseries_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `genreforuser`
--
ALTER TABLE `genreforuser`
  ADD CONSTRAINT `fk_genreforuser_genre` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_genreforuser_user` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `invitation`
--
ALTER TABLE `invitation`
  ADD CONSTRAINT `invitee_user` FOREIGN KEY (`invitee_id`) REFERENCES `user` (`account_id`),
  ADD CONSTRAINT `inviter_user` FOREIGN KEY (`inviter_id`) REFERENCES `user` (`account_id`);

--
-- Constraints for table `moviesprofilewatchlist`
--
ALTER TABLE `moviesprofilewatchlist`
  ADD CONSTRAINT `fk_moviesprofilewatchlist_movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_moviesprofilewatchlist_profile` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `movieviewcount`
--
ALTER TABLE `movieviewcount`
  ADD CONSTRAINT `fk_movieviewcount_movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_movieviewcount_user` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `fk_payments_user` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `seriesprofilewatchlist`
--
ALTER TABLE `seriesprofilewatchlist`
  ADD CONSTRAINT `fk_seriesprofilewatchlist_profile` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_seriesprofilewatchlist_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `seriesviewcount`
--
ALTER TABLE `seriesviewcount`
  ADD CONSTRAINT `fk_seriesviewcount_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_seriesviewcount_user` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_language` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE CASCADE ON UPDATE CASCADE;

DELIMITER $$
--
-- Events
--
CREATE EVENT `InsertExpiredTrialsDaily` ON SCHEDULE EVERY 1 DAY STARTS '2025-01-04 19:44:33' ON COMPLETION NOT PRESERVE ENABLE DO CALL `InsertExpiredTrialsIntoPayments`()$$

DELIMITER ;


START TRANSACTION

INSERT INTO `user` (25, 'janjassen@gmail.com', '$2a$10$7v5Ho908rYS1FPGp.N.7nOjJuJcRxnkSkaFPNFtXVPwHJhlREVW12', 'Credit Card', b'1', b'0', 'SD', '2025-01-06 15:54:13', '2025-01-13 15:54:13', 1, 'VIEWER', 0, NULL, b'0'),
(26, 'billJ1@outlook.com', '$2a$10$cvns7c5I6KUpgymcO610D.6CBWdZJAl90undCy/AZzLsIGSFkvJXi', 'IDEAL', b'1', b'0', 'HD', '2025-01-06 15:54:13', '2025-01-13 15:54:13', 1, 'VIEWER', 0, NULL, b'0');

UPDATE `user` SET `payment_method` = 'newly created card' WHERE `account_id` = 3;

SAVEPOINT savepoint1;

DELETE FROM `genre` WHERE `genre_id` = 29;

ROLLBACK TO savepoint1;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
