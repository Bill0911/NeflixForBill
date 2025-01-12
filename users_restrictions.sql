DROP ROLE IF EXISTS senior;
DROP ROLE IF EXISTS medior;
DROP ROLE IF EXISTS junior;
DROP ROLE IF EXISTS api;

DROP USER IF EXISTS 'fjodorsenior'@'%';
DROP USER IF EXISTS 'zhimedior'@'%';
DROP USER IF EXISTS 'billjunior'@'%';
DROP USER IF EXISTS 'main_api_user'@'%';

CREATE ROLE senior;
CREATE ROLE medior;
CREATE ROLE junior;
CREATE ROLE api;

-- ---------------SENNIOR PERMISSIONS-----------------------
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.* TO senior;
-- ---------------------------------------------------------

-- ---------------MEDIOR PERMISSIONS------------------------
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.episode TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.genre TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.genreformovie TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.genreforseries TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.genreforuser TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.language TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.movie TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.moviesprofilewatchlist TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.movieviewcount TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.profile TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.series TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.seriesprofilewatchlist TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.seriesviewcount TO medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.user TO medior;
GRANT SELECT ON netflix.user_for_junior TO medior;
GRANT SELECT ON netflix.user_genre_count TO medior;
GRANT SELECT ON netflix.user_view TO medior;
-- ---------------------------------------------------------

-- ---------------JUNIOR PERMISSIONS------------------------
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.episode TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.genre TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.genreformovie TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.genreforseries TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.genreforuser TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.language TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.movie TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.moviesprofilewatchlist TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.movieviewcount TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.profile TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.series TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.seriesprofilewatchlist TO junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.seriesviewcount TO junior;
GRANT SELECT ON netflix.user_for_junior TO junior;
-- ---------------------------------------------------------

-- --------------------------API-------------------------------

SELECT CONCAT('GRANT SELECT ON `netflix`.`', table_name, '` TO api;')
INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/netflix_views_grants.sql' -- --this path is just an example for my server
FIELDS TERMINATED BY '\n'
FROM information_schema.tables
WHERE table_schema = 'netflix' AND table_type = 'VIEW';

SELECT CONCAT('GRANT EXECUTE ON PROCEDURE `netflix`.`', routine_name, '` TO api;')
INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/netflix_procedures_grants.sql' -- --this path is just an example for my server
FIELDS TERMINATED BY '\n'
FROM information_schema.routines
WHERE routine_schema = 'netflix';

-- -------------------------IMPORTANT-------------------------
-- --make sure to check check which directory is allowed by the server
-- --To do that run the script: SHOW VARIABLES LIKE 'secure_file_priv';
-- --Then copy the files to the directory that is allowed by the server
-- --And paste the allowed chosen path to replace 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/netflix_views_grants.sql' 
-- --and 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/netflix_procedures_grants.sql'

-- ---------------------------------------------------------

CREATE USER 'fjodorsenior'@'%' IDENTIFIED BY '1234';
GRANT senior TO 'fjodorsenior'@'%';

CREATE USER 'zhimedior'@'%' IDENTIFIED BY '5678';
GRANT medior TO 'zhimedior'@'%';

CREATE USER 'billjunior'@'%' IDENTIFIED BY '8765';
GRANT junior TO 'billjunior'@'%';

CREATE USER 'main_api_user'@'%' IDENTIFIED BY '1234api';
GRANT api TO 'main_api_user'@'%';
SET DEFAULT ROLE api TO 'main_api_user'@'%';



-- --------------------NOTICE---------------------------
-- --CREATE SERVER CONNECTION FOR DIFFERENT ROLES-------
-- --make sure to query 'SET ROLE their_role;' before running any queries
