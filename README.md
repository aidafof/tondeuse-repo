# Exercice dev : La tondeuse

-> See specifications in **documentation directory**
# Objectives
Design and write a program running in Java, implementing the specifications : see -> documentation directory [Tondeuse.pdf]  and passing the test below with 
the input data provided in the file [**valid_mower_file.txt**].
# Implementation
Solution implemented is guided by SOLID principle of software design.
The aim was to build a flexible,testable and easy to maintain application. 
application is design in 5 main packages of classes with clear responsabilities:
 
* **Extractor** and reading data have been decoupled into 2 distinct processes: 
The main reason for this design is to make it easier adding of new data sources without iimpacting the existing code.
Extractor classes are responsible for the logic of data extracting
while FileReader classes are focus on the technical aspect of reading data from files 

in this application - MowerDataExtractor has significant role -since data are stored in a file
interfaces allow to abstract the concept of data extraction and data reading from 
different data source (file, or in the future daatbase or web service...)
  
* **Processor** :
here again the dataProcessor interface is flexibility to allow transformation of multiple type of data source.
FileDataProcessor is a custom processor adapted to the data provided in our input mower file
*  **Exception**: classes to handle  the possible business exceptions thrown during the procesess
Runtime exception are just re thrown with more friendly messages
* **Model** : the business data model of the application 
* **Service** : 2 concrete classes :
    LawnMowerService calls the dataProcessor 
    and validationService class : calls after data reading for encapsulation of data validation and logic validation rules 
the application would have been more flexible and implementation-independent with some services interfaces.
-this improvement can be done in a second step - 

# Application launch
* Application entry class main  **isAppMowerLauncher** java class 
 a file is required into the resource folder
for a quick overview on how works the  application 
it is easier to run the  jUnit test class **AppMowerLauncherTest**  
* Before running remember to load an input file (as described in the specification doc) into the resource folder

# java stack 
This application is developed with a standard java technical stack
 with IntelliJ (2022.2.2) IDE
* java 17 (for compiling and running  download JDK 17.
* Maven 3.12.1
* Junit 5 for Junit tests 
* Mockito
* Sl4J Api for logging application messages
* git
* and various standard libraries and plugins lombok, sonarlint)