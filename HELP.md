# Web Application for Neflix
This file contains guidlines for the usage of the Netflix API system build by NHL Stenden students Fjodor Smorodins, Zhi Cheng and Bill. 
Our language for API is Java and our database is MySQL.


## MySQL

* ### Setup

1) #### Download
Firstly, you need to download mysql installer through https://dev.mysql.com/downloads/windows/installer/8.0.html. 
You need to choose `.msi` file with version 8.0.41. 

2) #### Installation
Using this installer, you should install MySQL Server and MySQL Workbench both having versions 8.0.41.

3) #### User adding
Somewhere in the process of installation or right after that you wiLl be asked to create root user AND another user aswell. 
I highly suggest adding another user with all privileges, just in case.

4) #### Connection

In order to access databases you need to add connection in MySQL Workbench, by pressing "+" behind "MySQL connection" writing.
Then there will appear another panel. 
There you leave everything the same except changing the credentials of the user, accordingly to what user you created during user adding.
You then press ok and try connecting.

5) #### Database upload.

After connecting, go to "Data Import", then select "Import from Self-Contained File". Then to "Default Target Schema" add new schema/database (I will call it `netflix`) and upload the file `netflix.sql`. 

* ### Transactions

In the `transactions.sql` file there is a transactions. Might not work on every MySQL version but the querry logic should be at least correct.


* ### User roles, restrictions

In `user_restrictions.sql` file you can see...


## Java Springboot App project

### Dowloads
Dowload JDK 21 and IntelliJ IDEA.

### App running

1) #### Project opening
Open IntelliJ IDEA.
Click on File > Open.
Navigate to the project directory and select it.
Click OK to open the project.

2) #### Maven in the project
If IntelliJ does not recognize `pom.xml`, right-click on pom.xml and select "Add as Maven Project".

4) #### Configure SDK and Dependencies
Go to File > Project Structure.
Ensure the Project SDK is set to a compatible JDK (e.g., Java 17 or 21).
Check that the Language Level matches your previous setup.
If dependencies are missing, force update: run mvn clean install in the terminal or click Reload Project in the Maven tool window.

 5) #### Run the Project
Open `NetflixApplication.java` (the main class with @SpringBootApplication located in `\src\main\java\com.example.netflix`). Click the "Run" button or use `mvn spring-boot:run` in bash.


## Testing on Postman
1) ### Program setup
Download Postman app via this link: https://www.postman.com/downloads/ , then log in.