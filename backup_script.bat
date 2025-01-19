@echo off
:: Define a fixed backup file path
set BACKUP_PATH=D:mysql_db.sql

:: Run mysqldump and overwrite the existing file
mysqldump -h 134.209.199.147 -P 3306 -u root -p"p1234" --databases netflix > "%BACKUP_PATH%"

:: Log completion
echo Backup replaced on %date% at %time% >> backup_log.txt
