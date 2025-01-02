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