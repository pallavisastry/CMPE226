-------------------------------------------------------------------
Overview
-------------------------------------------------------------------

Following are the steps to configrue and run the application. Find the detailed 
instructions in below sections.

1. Install MySQL
2. create schema "readaloud"
3. configure build.properties
4. ant build
5. ant entityloader
6. ant massregistration 
7. ant bookloader
8. ant buildindex
9. ant deploy
10. ./tomcat-start.sh

-------------------------------------------------------------------
Requirements
-------------------------------------------------------------------
Below are the required software for running the project:
1.	MySQL
2.	Apache Tomcat
3.	Apache Ant


-------------------------------------------------------------------
MySQL Installation and settings
-------------------------------------------------------------------
a.  MySQL should be installed and running on locolhost:3306 
    with user as ìrootî and password ìrootî. 

    IF not update the hibernate configuration file 
    with the appropriate details (refer Project Report for detailed 
    instructions)

b.	Create a schema with the name ìreadaloudî.

-------------------------------------------------------------------
Building the project , creating tables, mass registration, 
loading books and building index
-------------------------------------------------------------------

Settings in build.properties

a.	Set the ìapache.tomcat.dirî to Apache tomcat home directory.
b.	Set the ìfilesToLoad.dirî to directory that contains the text books.

=======================
1. Building the project
=======================
a. Use ant, to build/compile the project as shown below

   ant build

   This will re-compile the source code and place the class files in 
   classes directory.

=======================    
2. Create tables in database
=======================  
   Run the entity loader to create the tables in the database and insert 
   the rows into the table.
 
    ant entityloader
    
=======================
3. Mass Registration
=======================
   Use ant, to mass register the users as readers and authors as shown below

   ant massregistration

   This will creates and registers random users as readers and authors with 
   random names, date of birth and email ids.

=======================
4. Loading books
=======================
   Use ant, to load the books into the database

   ant bookloader

   This will create the books and loads the books to the database.

=======================
5. Generate Index
=======================
	After loading the books, we need to create the index of the files. 
  Run the target ìbuildindexî to create the index.
	
	ant buildindex
	
	The indexed books are present in the indexdirectory.


-------------------------------------------------------------------
Deploying and running the application
-------------------------------------------------------------------

a. Use ant, to deploy the war file as shown below

   ant deploy

   This will copy the war file to the tomcat webapps folder.
   
b. Start tomcat by executing the script tomcat-start.sh persent 
   in this directory  


c. Open the browser and type the below url to launch the application:
	localhost:8080/books 
Sample Author— username: ar@gmail.com password: palsSample Reader—username: vv@gmail.com password: pals
   


