Selenium Tests

This repository contains Selenium tests for the \\ web app.

Make sure you have Java installed on your system, if not follow the vendor instructions for installing them on your operating system.
Make sure you have Maven installed on your system. (http://maven.apache.org/download.cgi)

Supported browser versions : 
  -FireFox : 46.0.1

The following are valid Maven parameters:

To run tests:
```
mvn test
```

To generate allure report:
```
mvn site
```

To generate .zip archive with report:
```
mvn assembly:single
```

To send email with report:
```
mvn postman:send-mail
```

The following are valid for use in the -Dbrowser parameter:
```
-Dbrowser firefox
```
Firefox option used by default

The following are valid test parameters:
```
-Dbrowser firefox
```
Which browser to use, for example "-Dbrowser=firefox"
```
-Dremote
```
Used in case of remote testing (Selenium grid), for example "-Dremote=true"
```
-DgridURL
```
Grid hub url, for example "http://35.164.83.102:4444/wd/hub" 

Running Tests LOCALLY (Windows):
The following steps should get you set up for running Selenium tests locally on your machine:
```
- Clone this repository to your local machine.
- Install browsers
- Set Maven, Java environment :
        - Windows : Set the environment variable JAVA_HOME to C:\Program Files\Java\jdk1.version
        - Windows : M2_HOME=C:\Program Files\Apache Software Foundation\apache-maven-3.X.X
        M2=%M2_HOME%\bin
- Move to the project directory
- To run tests:
    mvn test -Dbrowser=chrome
- Example (includes generating report and sending email) :
    mvn clean test -Dbrowser=chrome site assembly:single postman:send-mail
```

        
Links :
```
Firefox 46.0.1 : https://ftp.mozilla.org/pub/firefox/releases/46.0.1/win32/en-GB/
  Maven : http://maven.apache.org/download.cgi
  Java 8 : http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
```
  