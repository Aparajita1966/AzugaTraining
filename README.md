
# Azuga Training

Hello and welcome to my Java training notes.

This repository contains Java codes for various scenarios and was created during Azuga's training phase under the direction of CodeOps. The codes were written over the course of four weeks and are available on the Github repository. They will be available for future referencing and upgrades. Any and all contributions are welcome.



## Authors

- [@Aparajita1966](https://github.com/Aparajita1966/AzugaTraining)       



## Requirements

There are no such prerequisites. Although, a suitable JDK (preferably JDK 11 or higher) and an IDE must be installed.

For simplicity, this project is built using the Gradle build tool on the IntelliJ IDE.

However, in order to expand on this project, a number of additional dependencies are needed which can be imported directly by build.gradle; a list of them is provided in the last section Dependencies.
All the necessary dependencies mentioned in the list must be imported for the programs to run error free.

## Installation

Install my-project using terminal

```bash
  git clone https://github.com/Aparajita1966/AzugaTraining
  cd my-project
```
    
## Roadmap

The whole project is divided into weekly tasks and are as follows:

- [Week 1](https://github.com/Aparajita1966/AzugaTraining/tree/Week1) :
          Week 1's aim was to imitate the behaviour of CLI commands such as pwd, date, cd, mkdir, pipe, and so on.
          
 The tasks from Weeks 1 and 2 are unrelated, and the former was completed to help learn how CLI commands are used in programming.        
 All tasks are interrelated from Week 2 until the end.
   
- [Week 2](https://github.com/Aparajita1966/AzugaTraining/tree/Week2) :
         Week 2's tasks included retrieving data from RestAPIs and generating CSV and other file extensions from the data retrieved from an API. Meaningful charts were also suggested to be generated from the data obtained. The API used for the preceding task is detailed in the following section.
      
- [Week 3_Week 4](https://github.com/Aparajita1966/AzugaTraining/tree/Week3_Week4) :
         Week 3 and Week 4 goals were to make the code object-oriented, package it into JAR files for distribution, zip the reports generated from acquired API data and email them, and finally write test cases for the complete code.
         
      
## API Reference

## - BasketBall API

#### Get all players

```http
  GET https://www.balldontlie.io/api/v1/players
```

#### Get all teams

```http
  GET https://www.balldontlie.io/api/v1/teams
```

#### Get all games

```http
  GET https://www.balldontlie.io/api/v1/games
```


#### Get game stat for a player

```http
  GET https://www.balldontlie.io/api/v1/season_averages?season=2018&player_ids[]=1&player_ids[]=2
```  
 ## - Documentation

Go through the attatched API documentation carefully before advancing

[BASKETBALL API - Documentation](https://www.balldontlie.io/#introduction)

## Used By

This project is used by:

- AZUGA 
- APARAJITA MISHRA - created and maintaining
 
###
## License

Java_Project is licensed under the [AZUGA_TELEMATICS](https://www.azuga.com/) 

Using : [Java 18 ](https://www.java.com/en/)


###
## Feedback

Feel free to reach out to [Aparajita Mishra](mishra.aparajita.0000@gmail.com) for any feedback and suggestions.
Thank You

## Dependencies 

   - testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
   - testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
   - // https://mvnrepository.com/artifact/org.json/json
    implementation 'org.json:json:20220320'
   - // https://mvnrepository.com/artifact/commons-io/commons-io
    implementation group: 'commons-io', name: 'commons-io', version: '2.7'
   - // https://mvnrepository.com/artifact/org.apache.poi/poi
    implementation group: 'org.apache.poi', name: 'poi', version: '5.2.3'
   - //https://mvnrepository.com/artifact/com.itextpdf/itextpdf
    implementation group: 'com.itextpdf', name: 'itextpdf', version: '5.5.13.3'
   - // https://mvnrepository.com/artifact/au.com.bytecode/opencsv
    implementation group: 'au.com.bytecode', name: 'opencsv', version: '2.4'
   - // https://mvnrepository.com/artifact/org.apache.poi/poi
    implementation group: 'org.apache.poi', name: 'poi', version: '3.9'
   - // https://mvnrepository.com/artifact/jfree/jfreechart
    implementation group: 'jfree', name: 'jfreechart', version: '1.0.13'
   - // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation group: 'org.apache.commons', name: 'commons-lang3', version: '3.12.0'


