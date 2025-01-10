CREATE ROLE senior;
GRANT SELECT, UPDATE, INSERT, DELETE on netflix.* to senior;
CREATE user 'fjodorsenior' identified by '1234' default ROLE 'senior';