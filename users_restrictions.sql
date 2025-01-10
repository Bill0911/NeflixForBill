CREATE ROLE senior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.* TO senior;
CREATE USER 'fjodorsenior'@'%' IDENTIFIED BY '1234' DEFAULT ROLE senior;
GRANT senior TO 'fjodorsenior'@'%';

CREATE ROLE medior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.* TO medior;
REVOKE SELECT, UPDATE, INSERT, DELETE ON netflix.payments FROM medior;
CREATE USER 'zhimedior'@'%' IDENTIFIED BY '5678' DEFAULT ROLE medior;
GRANT medior TO 'zhimedior'@'%';

CREATE ROLE junior;
GRANT SELECT, UPDATE, INSERT, DELETE ON netflix.* TO junior;
REVOKE SELECT, UPDATE, INSERT, DELETE ON netflix.payments FROM junior;
REVOKE SELECT (email, payment_method) ON netflix.user FROM junior;
CREATE USER 'billjunior'@'%' IDENTIFIED BY '8765' DEFAULT ROLE junior;
GRANT junior TO 'billjunior'@'%';
