
## RESIT NOTE

We have changed quite a few things that we understood from teh feedback.

- Firstly, there are now a lot of more error handling for different cases, like when the rows/items are not found, the request is not appropriate, no access and so on. For instance, when you try to add movie to genre and at least one of those does not exist - it states that in the error and gives the status. When you try to delete something that does not exist - gives another. Also, when you try some routes like in `/api/payments` it won't allow through api user, but through others only, because `/api/payments` and for `inte` are not connected to procedure/view calls.
- Secondly, there are now also CRUD routes for `payment` and `invitation` tables. Also in the tests you can see that the many calls respond in XML, while being able to have request in the same format. The issue was that in the Postman, in order to get XML in response, in `Headers` there should be "`Accept` (key): `application/xml` (value)". For writing requests it's `Content-Type`:`application/xml`. And obviously it was always able to do all that in JSON.
- There is now also a working transaction in `transaction.sql` that can be executed properly.
- Additionally, the diagram was updated.

There is also updated test collection for Postman.

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

### !NOTE!

Some api calls may throw errors when it's not expected in the tests. That is because API requests try to add rows to tables that already exists, add rows with duplicate unique identifiers, or maybe delete something that does not exist. It is possible to try experimenting with different values like, for example, adding `genre-for-user` entry with `userID = 13` and `genreID = 15` if those exist in their respective databases. 

1) ### Program setup
Download Postman app via this link: https://www.postman.com/downloads/ , then log in.

2) ### Import the collection.
There is a file called `finalPollingMoment.postman_collection.json` which needs to be exported as the collection into Postman environment.

3) ### Mock testing server

In a workspace click on the 3 dots near the collection and click on "Mock collection". There you select an exsiting collection which is this one, while doing that check the "Save the mock server URL..." checkbox and create it. After that go to the mock server, copy url base, and put it in place of the url of every API call base how it would be for your app, in our case the base is `localhost:8081`. So every API call in the collection should start with the base and then `/api/...`

### Purpose of mock testing.
The mock tests, in this case, are tests of API calls on the copy of the existing database which is re-created to the same state every time the tests are run. They are needed so that if some mutational APIs are called, for example, adding new user, it will not cause any errors after re-running it, even if the api code checks if this new user's email exists which would call errors in a normal testing.



# Alternative README

This is a Web Application for a Neflix Clone, with a backend API connection to our database.

The application needs to have the following to be able to run: 
1. You need to open this application through highly recommended Intellij with a JDK of 21 or the latest if you don't have this make sure to download it2. Intellij will ask you to load the maven which is required for use, so make sure to automatically add these dependencies.
2. You will need an environment as well to be able to access PHPmyadmin, and for this you are recommended to use Docker and the environment NHL Stenden provides.

Having done the following steps make sure you git clone inside the NHL folder and navigate inside app, and make a folder and git clone in public folder to be able to access the Web Appliccation.

Few Notes for starting the app:
1. To start app, go to the xml folder and run the NetflixApplication (If asked to download any dependencies for maven please do so.)
2. Make sure the apps inside the docker environment are all turned on so our API can connect to the server and database.

To access our DataBase, you would need to go to the resources folder where you can find the database url to the database as well as the username and password. Heads up there are two, one named main_api_user and the alternative one.

IF ANY ERROR ARE PRESENT

please check if all environment are up and running