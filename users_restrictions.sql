
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

GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.* TO senior;

GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.* TO medior;

GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.* TO junior;



CREATE USER 'fjodorsenior'@'%' IDENTIFIED BY '1234';
GRANT senior TO 'fjodorsenior'@'%';

CREATE USER 'zhimedior'@'%' IDENTIFIED BY '5678';
GRANT medior TO 'zhimedior'@'%';

CREATE USER 'billjunior'@'%' IDENTIFIED BY '8765';
GRANT junior TO 'billjunior'@'%';

CREATE USER 'ghost_api'@'%' IDENTIFIED BY 'ghost';



GRANT EXECUTE ON PROCEDURE netflix.getManyUsers TO ghost_api;
GRANT SELECT ON netflix.user_view TO ghost_api;


GRANT SELECT ON netflix.payments TO medior;
REVOKE SELECT ON netflix.payments FROM medior;


GRANT SELECT ON netflix.payments TO junior;
REVOKE SELECT ON netflix.payments FROM junior;
GRANT SELECT (email, payment_method) ON netflix.user TO junior;
REVOKE SELECT (email, payment_method) ON netflix.user FROM junior;