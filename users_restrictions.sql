DROP ROLE IF EXISTS senior;
DROP ROLE IF EXISTS medior;
DROP ROLE IF EXISTS junior;
DROP ROLE IF EXISTS api_user;

DROP USER IF EXISTS 'fjodorsenior'@'%';
DROP USER IF EXISTS 'zhimedior'@'%';
DROP USER IF EXISTS 'billjunior'@'%';
DROP USER IF EXISTS 'ghost_api'@'%';

CREATE ROLE senior;
CREATE ROLE medior;
CREATE ROLE junior;
CREATE ROLE api_user;

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


CREATE USER 'fjodorsenior'@'%' IDENTIFIED BY '1234';
GRANT senior TO 'fjodorsenior'@'%';

CREATE USER 'zhimedior'@'%' IDENTIFIED BY '5678';
GRANT medior TO 'zhimedior'@'%';

CREATE USER 'billjunior'@'%' IDENTIFIED BY '8765';
GRANT junior TO 'billjunior'@'%';

CREATE USER 'ghost_api'@'%' IDENTIFIED BY 'ghost';

GRANT EXECUTE ON PROCEDURE netflix.getManyUsers TO ghost_api;
GRANT SELECT ON netflix.user_view TO ghost_api;



-- --------------------NOTICE---------------------------
-- --CREATE SERVER CONNECTION FOR DIFFERENT ROLES-------
-- --make sure to query 'SET ROLE their_role;' before running any queries
