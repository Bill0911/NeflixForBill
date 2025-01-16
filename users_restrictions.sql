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

-- ---------------SENIOR PERMISSIONS-----------------------
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
GRANT EXECUTE ON PROCEDURE `netflix`.`AddEpisode` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddGenre` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddGenreForMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddGenreForSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddGenreForUser` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddLanguage` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddMoviesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddMovieViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddProfile` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddSeriesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddSeriesViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`AddUser` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteEpisode` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteGenre` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteGenreForMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteGenreForSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteGenreForUser` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteLanguage` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteMoviesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteMovieViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteProfile` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteSeriesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteSeriesViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`DeleteUser` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetEpisodeById` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetGenreById` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetGenreForMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetGenreForSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetGenreForUser` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetLanguageById` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyEpisodes` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyGenreForMovies` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyGenreForSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyGenreForUsers` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyGenres` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyLanguages` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyMovies` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyMoviesProfileWatchlists` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyMovieViewCounts` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyProfiles` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManySeriesProfileWatchlists` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManySeriesViewCounts` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManyUsers` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetManySeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetMovieById` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetMoviesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetMovieViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetPersonalizedOfferMovies` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetPersonalizedOfferSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetProfileById` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetSeriesById` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetSeriesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetSeriesViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetUserByEmail` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`GetUserById` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`InsertExpiredTrialsIntoPayments` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchEpisode` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchGenreForMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchGenreForSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchGenreForUser` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchMoviesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchMovieViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchProfile` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchSeriesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchSeriesViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`PatchUser` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`process_payment` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateEpisode` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateGenre` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateGenreForMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateGenreForSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateGenreForUser` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateLanguage` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateMovie` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateMovieProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateMovieViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateProfile` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateSeries` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateSeriesProfileWatchlist` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateSeriesViewCount` TO api;
GRANT EXECUTE ON PROCEDURE `netflix`.`UpdateUser` TO api;

GRANT SELECT ON `netflix`.`paymentstatus` TO api;
GRANT SELECT ON `netflix`.`subscription_cost` TO api;
GRANT SELECT ON `netflix`.`user_genre_count` TO api;
GRANT SELECT ON `netflix`.`moviesprofilewatchlist` TO 'main_api_user';
GRANT SELECT, INSERT, UPDATE, DELETE ON `netflix`.`language` TO 'main_api_user';
GRANT SELECT, INSERT, UPDATE, DELETE ON `netflix`.`invitation` TO 'main_api_user';

-- -------------------------IMPORTANT-------------------------

-- --make sure to check check which directory is allowed by the server
-- --To do that run the script: SHOW VARIABLES LIKE 'secure_file_priv';
-- --Then copy the files to the directory that is allowed by the server
-- --And paste the allowed chosen path to replace 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/netflix_views_grants.sql' 
-- --and 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/netflix_procedures_grants.sql'

-- ---------------------------------------------------------

CREATE USER 'fjodorsenior'@'%' IDENTIFIED BY '1234';
GRANT senior TO 'fjodorsenior'@'%';
SET DEFAULT ROLE senior TO 'fjodorsenior'@'%';

CREATE USER 'zhimedior'@'%' IDENTIFIED BY '5678';
GRANT medior TO 'zhimedior'@'%';
SET DEFAULT ROLE medior TO 'zhimedior'@'%';

CREATE USER 'billjunior'@'%' IDENTIFIED BY '8765';
GRANT junior TO 'billjunior'@'%';
SET DEFAULT ROLE junior TO 'billjunior'@'%';

CREATE USER 'main_api_user'@'%' IDENTIFIED BY '1234api';
GRANT api TO 'main_api_user'@'%';
SET DEFAULT ROLE api TO 'main_api_user'@'%';

-- --------------------NOTICE---------------------------
-- --CREATE SERVER CONNECTION FOR DIFFERENT ROLES-------
-- --make sure to query 'SET ROLE their_role;' before running any queries
