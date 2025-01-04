
CREATE TABLE `payments` (
  `payment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) UNSIGNED NOT NULL,
  `is_discount_applied` bit(1) DEFAULT b'0',
  `is_paid` bit(1) DEFAULT b'0',
  `payment_date` datetime DEFAULT NULL,
  `subscription_type` enum('SD','HD','UHD') DEFAULT 'SD',
  `payment_amount` decimal(10,2) DEFAULT NULL,
  `next_billing_date` datetime DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE payments
 	ADD CONSTRAINT fk_payments_user FOREIGN KEY (account_id) REFERENCES user (account_id) ON DELETE CASCADE ON UPDATE CASCADE;

SET SQL_SAFE_UPDATES = 0;

UPDATE `user`
SET `trial_end_date` = DATE_ADD(`trial_start_date`, INTERVAL 7 DAY)
WHERE `trial_start_date` IS NOT NULL;

SET SQL_SAFE_UPDATES = 1;
-- --------------------------------------------------------
--
-- Stand-in structure for view `paymentstatus`
-- (See below for the actual view)

CREATE VIEW `paymentstatus` AS 
SELECT 
    p.`payment_id` AS `payment_id`, 
    u.`account_id` AS `account_id`, 
    u.`email` AS `email`, 
    p.`subscription_type` AS `subscription_type`, 
    p.`payment_amount` AS `payment_amount`, 
    p.`is_paid` AS `is_paid`, 
    p.`is_discount_applied` AS `is_discount_applied`, 
    p.`payment_date` AS `payment_date`,
    p.`next_billing_date` AS `next_billing_date`
FROM 
    `payments` p
JOIN 
    `user` u ON p.`account_id` = u.`account_id`;
-- --------------------------------------------------------

CREATE VIEW `subscriptioncosts` AS 
SELECT 
    `u`.`account_id` AS `UserID`, 
    `u`.`email` AS `Email`, 
    `u`.`subscription` AS `SubscriptionType`, 
    CASE 
        WHEN TO_DAYS(CURDATE()) - TO_DAYS(`u`.`trial_start_date`) <= 7 THEN 0 
        ELSE 
            CASE 
                WHEN `u`.`subscription` = 'SD' THEN 7.99 
                WHEN `u`.`subscription` = 'HD' THEN 10.99 
                WHEN `u`.`subscription` = 'UHD' THEN 13.99 
                ELSE 0 
            END - 
            CASE 
                WHEN `u`.`discount` = 1 THEN 2 
                ELSE 0 
            END 
    END AS `SubscriptionCost` 
FROM 
    `user` AS `u`;

-- PROCEDURE TO INSERT EXPIRED TRIALS INTO PAYMENTS TABLE
DELIMITER $$

CREATE PROCEDURE `InsertExpiredTrialsIntoPayments` ()
BEGIN
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

DELIMITER ;

-- Create the event to run the procedure daily
CREATE EVENT `InsertExpiredTrialsDaily`
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_TIMESTAMP
DO
CALL `InsertExpiredTrialsIntoPayments`();

-- Create the invitation table
CREATE TABLE `invitation` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `inviter_id` BIGINT(20) UNSIGNED NOT NULL,
    `invitee_id` BIGINT(20) UNSIGNED NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`inviter_id`) REFERENCES `user`(`account_id`),
    FOREIGN KEY (`invitee_id`) REFERENCES `user`(`account_id`)
);