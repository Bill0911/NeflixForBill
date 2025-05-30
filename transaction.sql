USE netflix;

DELIMITER $$

START TRANSACTION $$

INSERT INTO `user` (`account_id`, `email`, `password`, `payment_method`, `active`, `blocked`, `subscription`, `trial_start_date`, `trial_end_date`, `language_id`, `role`, `failed_attempts`, `lock_time`, `discount`) VALUES 
(55, 'janjassen@gmail.com', '$2a$10$7v5Ho908rYS1FPGp.N.7nOjJuJcRxnkSkaFPNFtXVPwHJhlREVW12', 'IDEAL', b'1', b'0', 'HD', '2025-01-15 17:30:45', NULL, 1, 'VIEWER', 0, NULL, b'1'),
(56, 'billJ1@outlook.com', '$2a$10$cvns7c5I6KUpgymcO610D.6CBWdZJAl90undCy/AZzLsIGSFkvJXi', 'IDEAL', b'1', b'0', 'HD', '2025-01-17 13:56:32', NULL, 1, 'VIEWER', 0, NULL, b'1');

UPDATE `user` SET `payment_method` = 'newly created card' WHERE `account_id` = 3;

SAVEPOINT savepoint1;

DELETE FROM `user` WHERE `account_id` = 55;
DELETE FROM `user` WHERE `account_id` = 56;

ROLLBACK TO savepoint1;

COMMIT $$

DELIMITER ;
