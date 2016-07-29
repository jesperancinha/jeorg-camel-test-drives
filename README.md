# jesperancinha-atm-finder

## How to build and run

```
# Not needed but recommended
$ mvn clean install

# Only needs to be run once
$ jspm install

$ mvn package

$ mvn tomcat7:run

```

## How to check if it works

* http://localhost:8080/atm/rest/provider/{city}/atms

## Examples

* http://localhost:8080/atm/rest/provider/haarlem/atms

* http://localhost:8080/atm/rest/provider/amsterdam/atms

## Start your application

After running your tomcat server you should be able to access the application through here:

* http://localhost:8080/atm/login

## Build data

* Spring MVC

* Sprinc Security

* Camel DSL JSON

* IntelliJ 2016.2

* AngularJS

* JSPM

* Tomcat 7

