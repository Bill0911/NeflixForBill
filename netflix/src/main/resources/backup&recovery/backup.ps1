# Define variables
$backupDir = "C:\Users\dober\Desktop\DataPro\NeflixForBill\netflix\src\main\resources\backup&recovery"
$dbName = "netflix"
$date = Get-Date -Format "yyyy-MM-dd"

# Create backup
& "C:\Program Files (x86)\MySQL\bin\mysqldump.exe" --defaults-extra-file=C:\Users\dober\.my.cnf $dbName > "$backupDir\netflix_backup_$date.sql"

# Remove backups older than 7 days
Get-ChildItem -Path $backupDir -Filter "*.sql" | Where-Object { $_.LastWriteTime -lt (Get-Date).AddDays(-7) } | Remove-Item