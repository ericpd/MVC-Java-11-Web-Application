# MVC-Java-11-Web-Application

Introduction:</a>
	This project archetype is a template for creating a fully functional MVC web application using Hibernate, JSTL and Bootstrap. It has an automatic database creation, auto initial load of the data, with different variety of users. It also has a checkstyle to check the proper coding of your project immediately right after you enter the code.
	
![preview-xl](https://user-images.githubusercontent.com/17751104/161626134-ccd6e55d-98a3-4d6a-b24a-695d9972d9a4.jpg)
	
	
Requirements:</p>
•	Java v11+</p>
•	Maven v3+</p>
•	Apache Tomcat v9+</p>
•	Your database server of choice (Any DB server. i.e. PostgreSQL, SQLServer, Oracle, DB2, etc.)</p>
•	IDE of choice (Eclipse or IntelliJ)</p>
Setup and Configuration:
1.	Download Java JDK.
2.	Download Maven.
3.	Download and configure database.
4.	Download Apache Tomcat
5.	Download and configure IDE
6.	Download the “MVC Hibernate JSTL Bootstrap” maven archetype
7.	Configure you default maven Installation and maven settings. Go to Windows  Preferences  Maven  Installations
	
![image001](https://user-images.githubusercontent.com/17751104/161618522-96a90271-be60-4ad3-af59-6a856dc68642.png)

8.	Set the maven as default maven installation!	

![image003](https://user-images.githubusercontent.com/17751104/161619397-72646c63-d256-4f2a-bd7f-3c8ade9d9751.png)

9.	Select the settings.xml from the maven installation folder as the default global settings

![image005](https://user-images.githubusercontent.com/17751104/161619711-4797faec-bb23-465e-acff-6d3e19a32e7b.png)

10.	Configure Apache Tomcat Server, Under Windows  Preferences  Server  Runtime Environments, click Add button.
11.	Select Apache Tomcat v9.0 and check the “Create a new local server”.

![image007](https://user-images.githubusercontent.com/17751104/161619872-dd866164-36dd-47f8-bff5-868db1ef228c.png)

12.	Select your tomcat installation folder and hit Finish button.

![image009](https://user-images.githubusercontent.com/17751104/161620062-95174fbe-c41e-4837-a373-5dd9e3268a29.png)

13.	Open your IDE and import “MVC Hibernate JSTL Bootstrap” archetype maven project

![image011](https://user-images.githubusercontent.com/17751104/161620182-9d254d8b-5978-4ad1-9f3f-ed70e6f9ba57.png)

14.	Create new run configuration. Under Run  Run Configurations, Create new Maven Build.
15.	Set the name of the configuration, select the mvc-hibernate-jstl-bootstrap project and add the goal “clean package install”, then hit Apply and Run.

![image013](https://user-images.githubusercontent.com/17751104/161620323-26691fd8-42c2-4d43-824a-6d8aaba91832.png)


16.	Success! You can now create you template project from the new mvc-hibernate-jstl-bootstrap maven archetype.

Creating a web project:
1.	Create new maven project.

![image015](https://user-images.githubusercontent.com/17751104/161620559-0b5cba9f-d2a4-4e36-8bc1-f64b449c41fc.png)

2.	Hit Next twice.
3.	Select the “mvc-hibernate-jstl-bootstrap” archetype

![image016](https://user-images.githubusercontent.com/17751104/161620611-b91525ff-8cda-4652-9da9-c25e6708b01b.png)


4.	Set the following required fields:
a.	Group ID – Company name (i.e. com.sample)
b.	Artifact ID – Your project name (all small letter) (i.e. sample)
c.	Version – The initial version of the project (i.e. 1.0)
d.	contextPath – The default context path of your web project (i.e. sample)
e.	name – Name of the project to be display in the UI (i.e. Sample)
f.	description A short description of this project (i.e. Sample web application with Hibernate, JSTL and Bootstrap)

![image018](https://user-images.githubusercontent.com/17751104/161620734-2ee59ffd-afb5-4d2f-adc3-085ada33204c.png)

5.	5 new projects should be created:
a.	sample – The parent project containing all the four projects.
b.	sample-beans – Base data model project, contains all the database table with hibernate annotation. (Model)
c.	sample-formbeans – Request/Response data model for the UI, consist of data model representation for web UI request and response. (View)
d.	sample-app – Main business logic project. Contains all the logic between the UI and the database.
e.	sample-ui – Web project, consist of servlets and jsp pages for the web application (Controller)
6.	Create new run configuration, under Run  Run Configuration, create new Maven Build. Enter the configuration name, Select the main project (in this case the “sample” project), then add “clean package install” as the Goals. Hit the Apply and Run button.

![image020](https://user-images.githubusercontent.com/17751104/161620894-369529f1-d550-4964-bacd-b33091a82d2d.png)


You should get the following logs:

![image022](https://user-images.githubusercontent.com/17751104/161620958-12871cb1-06f2-44c8-b4dd-a19b844d7a10.png)

7.	To be able to automatically create the tables in the database, you need to create the database and the user that has read/write access to create or drop the database tables and indexes. (If you are using PostgreSQL database, follow the instruction of creating the database below.)
8.	Go to Window  Show View  Servers, add the UI project in the server by right clicking into the server and click Add and Remove. Select the project then click “Add >”. Then hit Finish.
9.	Under the Project Explorer  Servers  <Your new local server>, open catalina.properties.
10.	At the bottom of the file, add the following:
DB_CREATED=N
DEBUG_ENABLE=false
SHOW_SQL=false
ZONE=America/Winnipeg
#DATABASE_HOST_NAME=localhost
#DATABASE_PORT=5432
#DATABASE_NAME=sample
#DATABASE_USERNAME=sampleadm
#DATABASE_PASSWORD=sampleadm

•	DB_CREATED  Y to create new database or N to update existing database
•	DEBUG_ENABLE  true or false to enable debug
•	SHOW_SQL  true or false to show sql queries in the logs
•	ZONE  Your default timezone for localization
•	DATABASE_HOST_NAME  the IP or hostname of the database server
•	DATABASE_PORT  the port of the database server
•	DATABASE_NAME  the name of the database
•	DATABASE_USERNAME  the database user
•	DATABASE_PASSWORD  the database user password
Note: Remove the “#” to uncomment the properties.
11.	Right click and start the server. By default, the database tables will be automatically created and an initial set of data will be loaded into the database.
a.	su/su  the Super User
b.	root/su  the Regular User
c.	member/su  the Member of the regular user
12.	You can access the website using this url: http://localhost:8080/<contextPath>/ (i.e., http://localhost:8080/sample/)
13.	Enjoy!!!

  Creating a user and database on PostgreSQL:
1.	Open PGAdmin
2.	Under PostgreSQL <Version>, Right click on Login/Group Roles  Create  Login/Group Role.
3.	Enter the Name under the General tab and the password under the Definition tab. (in this case we use the same username and password for testing purposes, you can put whatever you like.)

  ![image024](https://user-images.githubusercontent.com/17751104/161621126-65cd32d3-3769-49f8-bb97-cfa21d249b43.png)

  4.	Under the Priveleges tab, set the following
a.	Can login?  YES
b.	Superuser?  NO
c.	Create roles?  NO
d.	Create databases?  YES
e.	Update catalogs?  NO
f.	Inherit rights from the parent roles?  YES
g.	Can initiate streaming replication and backups? NO
5.	Click the “Save” button.
6.	Right click on Databases  Create  Database
7.	Set the database name and select the new created user (i.e. sampleadm) as the owner. Then hist “Save” button.

  Setup Project CheckStyle:
1.	Under Windows  Preferences, Select Checkstyle. Click the New button. Select External Configuration File for the Type, enter the name of the checkstyle and navigate to your parent project default folder and look for checkstyle.xml file.

  ![image026](https://user-images.githubusercontent.com/17751104/161621266-5d89f568-d6e0-41d4-86a1-52e356222453.png)

  2.	Select the new checkstyle as your default.
3.	On your projects (*-app, *.beans, *.ui), Right click and Properties. Check the “Checkstyle active for this project” and hit “Apply and Close”.

  ![image028](https://user-images.githubusercontent.com/17751104/161621353-b255ac39-e599-4af1-a0dd-8a6e4ffe637a.png)

  
cv
