

--
-- Database: `sql7753243`
--

DELIMITER $$
--
-- Procedures
--
CREATE PROCEDURE `AddEpisode` (IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_series_id` INT)   BEGIN
    INSERT INTO `episode` (`title`, `duration`, `series_id`)
    VALUES (p_title, IFNULL(p_duration, '00:00:00'), p_series_id);
END$$

CREATE PROCEDURE `AddGenre` (IN `p_genre_name` VARCHAR(255))   BEGIN
    INSERT INTO `genre` (`genre_name`)
    VALUES (p_genre_name);
END$$

CREATE PROCEDURE `AddMovieViewCount` (IN `p_movieId` INT, IN `p_accountId` INT)   BEGIN
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

CREATE PROCEDURE `AddSeriesViewCount` (IN `p_seriesId` INT, IN `p_accountId` INT)   BEGIN
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

CREATE PROCEDURE `DeleteEpisode` (IN `p_episode_id` INT)   BEGIN
    DELETE FROM `episode` WHERE `episode_id` = p_episode_id;
END$$

CREATE PROCEDURE `DeleteGenre` (IN `p_genre_id` INT)   BEGIN
    DELETE FROM `genre`
    WHERE `genre_id` = p_genre_id;
END$$

CREATE PROCEDURE `GetAllEpisodes` ()   BEGIN
    SELECT * FROM `episode`;
END$$

CREATE PROCEDURE `GetAllGenres` ()   BEGIN
    SELECT * FROM `genre`;
END$$

CREATE PROCEDURE `GetEpisodeById` (IN `p_episode_id` INT)   BEGIN
    SELECT * FROM `episode` WHERE `episode_id` = p_episode_id;
END$$

CREATE PROCEDURE `GetGenreById` (IN `p_genre_id` INT)   BEGIN
    SELECT * FROM `genre`
    WHERE `genre_id` = p_genre_id;
END$$

CREATE PROCEDURE `GetPersonalizedOffer` (IN `userId` INT, IN `maxMovies` INT)   BEGIN
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

CREATE PROCEDURE `PatchEpisode` (IN `p_episode_id` INT, IN `p_title` VARCHAR(255), IN `p_duration` TIME, IN `p_series_id` INT)   BEGIN
    UPDATE `episode`
    SET 
        `title` = COALESCE(p_title, `title`),
        `duration` = COALESCE(p_duration, `duration`),
        `series_id` = COALESCE(p_series_id, `series_id`)
    WHERE `episode_id` = p_episode_id;
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
  `discount` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `episode`
--

CREATE TABLE `episode` (
  `episode_id` int(11) UNSIGNED NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` time DEFAULT '00:00:00',
  `series_id` int(11) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `genre_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
(8, 'sci-fi'),
(17, 'Adventure'),
(18, 'triler'),
(19, 'biography'),
(20, 'history'),
(21, 'musical');

-- --------------------------------------------------------

--
-- Table structure for table `genreformovie`
--

CREATE TABLE `genreformovie` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `genreformovie`
--

INSERT INTO `genreformovie` (`genre_id`, `movie_id`) VALUES
(2, 1),
(5, 1),
(7, 2),
(3, 3),
(8, 3);

-- --------------------------------------------------------

--
-- Table structure for table `genreforseries`
--

CREATE TABLE `genreforseries` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `series_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `genreforuser`
--

CREATE TABLE `genreforuser` (
  `genre_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE `language` (
  `language_id` int(11) UNSIGNED NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `minimum_age` int(3) NOT NULL DEFAULT '12'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `movieviewcount`
--

CREATE TABLE `movieviewcount` (
  `account_id` int(11) UNSIGNED NOT NULL,
  `movie_id` int(11) UNSIGNED NOT NULL,
  `number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movieviewcount`
--

INSERT INTO `movieviewcount` (`account_id`, `movie_id`, `number`) VALUES
(1, 1, 3),
(1, 2, 2),
(1, 3, 1),
(3, 1, 1),
(5, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) UNSIGNED NOT NULL,
  `account_id` int(11) UNSIGNED DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `age` int(3) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `minimum_age` int(3) NOT NULL DEFAULT '12'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Table structure for table `user`
--
CREATE TABLE `user` (
  `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `payment_method` varchar(255) DEFAULT 'Credit Card',
  `blocked` bit(1) DEFAULT b'0',
  `subscription` enum('SD','HD','UHD') DEFAULT 'SD',
  `trial_start_date` datetime DEFAULT NULL,
  `language_id` int(11) UNSIGNED DEFAULT NULL,
  `role` enum('JUNIOR','MEDIOR','SENIOR') DEFAULT 'JUNIOR',
  `failed_attempts` int(11) DEFAULT '0',
  `lock_time` datetime DEFAULT NULL,
  `discount` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`account_id`),
  INDEX (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `blocked`, `subscription`, `trial_start_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES
 (1, 'fjodor.smorodins@gmail.com', '$2a$10$hszeHDUNOv4lnd24ZS9sOeOkOJUYo5zSi2H2makEPti1uznr4s5P2', 'abc', b'0', 'SD', '2024-12-07 14:32:59', 4, 'SENIOR', 0, NULL, 0),
 (2, 'fjodorsm@gmail.com', '$2a$10$2QlecdJ25ELwT/avANQAUelbxtS9tysiRO5LSE0omLATaWhdAPfZC', 'Credit Card', b'0', 'SD', '2024-12-07 14:33:33', 1, 'JUNIOR', 0, NULL, 0),
 (3, 'smorodins@gmail.com', '$2a$10$KhhGnFeK2q32DYG7/fMhNe/GEzf1dDJVkQqq5isK1vwuIO9h0zor.', 'CrC', b'0', 'SD', '2024-12-16 18:54:30', 3, 'JUNIOR', 0, NULL, 0),
 (5, 'fjodors@hello.com', '$2a$10$hKsRL99MRpKUr.vrJPqsfuG3qhGkDjXQEYDxHytWFBYgW7HZJ/54W', 'golden bars', b'0', 'SD', '2024-12-20 16:19:25', 2, 'JUNIOR', 0, NULL, 0),
 (6, 'artjoms.grishajevs@hello.com', '$2a$10$NboUZOHniHtnfHhFFECcF.dA64uJsp.8/OnD0B0NEuMvTyvIfN7we', 'children', b'0', 'SD', '2024-12-20 16:24:58', 1, 'JUNIOR', 0, NULL, 0),
 (7, 'somebody@hello.com', '$2a$10$4H41Ugw1ho9ga4DfTV1rwegl.uxbZcTbEu3/SBeklNzsHnXoYliTe', 'money', b'0', 'SD', '2024-12-20 17:08:59', 1, 'JUNIOR', 0, NULL, 0),
 (9, 'somepersonwhatever@hello.com', '$2a$10$DhZSCWySz9rypM/jM8mR6.yzaCPIpugVlITMSWx9whkmEp1ciPK42', 'something', b'0', 'SD', '2024-12-20 17:24:39', 2, 'JUNIOR', 0, NULL, 0),
 (10, 'iamsteve@hello.com', '$2a$10$92qxixAWTf94z9sK.Lf2iebtyLdBV9ckOx.xfzGLv4enlX5gdsis6', 'mastercard', b'0', 'SD', '2024-12-20 17:58:22', 3, 'JUNIOR', 0, NULL, 0),
 (15, 'test1@.com', '$2a$10$aP97IvFmxH8yLGuL1012Xe4sfLd6s1SdokAAKOhG3.tvWCTkmfD2.', 'some method', b'0', 'SD', '2024-12-20 22:46:29', 3, 'JUNIOR', 1, NULL, 0),
 (17, 'medior.fjodor@g.com', '$2a$10$gQuhxuEegp0Ypg.IrGiL8.bmQwV4sdMzXirKh7N0N4KbOXAq4xwFi', 'some money transfer method', b'0', 'SD', '2024-12-23 17:55:18', 3, 'JUNIOR', 0, NULL, 0);

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
-- Stand-in structure for view `subscriptioncosts`
-- (See below for the actual view)
--

CREATE TABLE `subscriptioncosts` (
`UserID` int(11) unsigned
,`Email` varchar(255)
,`SubscriptionType` enum('SD','HD','UHD')
,`SubscriptionCost` int(4)
);

-- --------------------------------------------------------
--
-- Structure for view `paymentstatus`
---- --------------------------------------------------------

CREATE TABLE `payments` (
  `payment_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` int(11) UNSIGNED NOT NULL,
  `is_discount_applied` bit(1) NOT NULL DEFAULT b'0',
  `is_paid` bit(1) NOT NULL DEFAULT b'0',
  `payment_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `subscription_type` varchar(10) NOT NULL,
  `payment_amount` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`payment_id`),
  FOREIGN KEY (`account_id`) REFERENCES `user`(`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- Structure for view `paymentstatus`
--
CREATE VIEW `paymentstatus` AS
SELECT 
    p.payment_id,
    u.account_id,
    u.email,
    p.subscription_type,
    p.payment_amount,
    p.is_paid,
    p.is_discount_applied,
    p.payment_date
FROM 
    payments p
JOIN 
    user u ON p.account_id = u.account_id;
-- --------------------------------------------------------
--
-- Structure for procedure `payment`
--
DELIMITER //

CREATE PROCEDURE `process_payment` (
    IN userId INT,
    IN subscriptionType VARCHAR(10),
    IN discountApplied BIT
)
BEGIN
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
END //

DELIMITER ;
-- ------------------------------------------------------
--
-- Structure for view `subscriptioncosts`
--
DROP TABLE IF EXISTS `subscriptioncosts`;

CREATE ALGORITHM = UNDEFINED  
SQL SECURITY DEFINER VIEW `subscriptioncosts`  
AS SELECT `u`.`account_id` AS `UserID`, `u`.`email` 
AS `Email`, `u`.`subscription` AS `SubscriptionType`, 
(case when ((to_days(curdate()) - to_days(`u`.`trial_start_date`)) <= 7) 
then 0 else ((case when (`u`.`subscription` = 'SD') 
then 10 when (`u`.`subscription` = 'HD') 
then 15 when (`u`.`subscription` = 'UHD') 
then 20 else 0 end) - (case when (`u`.`discount` = 1) 
then 2 else 0 end)) end) AS `SubscriptionCost` FROM `user` AS `u` ;

-- --------------------------------------------------------

--
-- Structure for view `user_genre_count`
--
DROP TABLE IF EXISTS `user_genre_count`;

CREATE ALGORITHM = UNDEFINED
SQL SECURITY DEFINER VIEW `user_genre_count`  
AS SELECT `mvc`.`account_id` AS `user_id`, `g`.`genre_id` 
AS `genre_id`, `g`.`genre_name` AS `genre_name`, sum(`mvc`.`number`) 
AS `total_views` FROM (((`movieviewcount` `mvc` 
join `movie` `m` on((`mvc`.`movie_id` = `m`.`movie_id`))) 
join `genreformovie` `mg` on((`m`.`movie_id` = `mg`.`movie_id`))) 
join `genre` `g` on((`mg`.`genre_id` = `g`.`genre_id`))) 
GROUP BY `mvc`.`account_id`, `g`.`genre_id` 
ORDER BY `mvc`.`account_id` ASC, sum(`mvc`.`number`) DESC ;

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
  ADD KEY `FKj9k2portqypgs993xn20pywtr` (`language_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `episode`
--
ALTER TABLE `episode`
  MODIFY `episode_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

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
  MODIFY `account_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `episode`
--
ALTER TABLE `episode`
  ADD CONSTRAINT `FK6hj3n90dblxp7xd9fc1urj8ko` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`);

--
-- Constraints for table `genreformovie`
--
ALTER TABLE `genreformovie`
  ADD CONSTRAINT `FKcooxhml1jwh4bnp2o9n63ka3k` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  ADD CONSTRAINT `FK9k0bup58wyigpu52ap88luwm3` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`);

--
-- Constraints for table `genreforseries`
--
ALTER TABLE `genreforseries`
  ADD CONSTRAINT `FKn3exg02r09uca4mdojqte8dly` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`),
  ADD CONSTRAINT `FK46gf3ps13sggr3a79227kltsn` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`);

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
  ADD CONSTRAINT `FKcpuurwpcxsr5pqfikypjvl3qn` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`),
  ADD CONSTRAINT `FK960o3778b43sfm6eh63p8yaaj` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`);

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
