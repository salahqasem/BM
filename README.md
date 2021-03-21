# Bright Minds 

#Requirements
1- JAVA 1.8 or higher 

2- maven 3.6.3 or higher 

#Run the application 

1- Clone the repo to your machine. `git clone https://github.com/salahqasem/BM`

2- From command line open the project directory and run `mvn spring-boot:run`

3- open http://localhost:8080/login

Run test & reports
1- For unit test & jacoco report

```mvn test```

you can find jacoco report in target/site/jacoco

2- For SonarCube report

```mvn verify sonar:sonar```

you can find the report here: https://sonarcloud.io/dashboard?id=salahqasem_BM
