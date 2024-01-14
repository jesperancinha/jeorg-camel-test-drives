# jeorg-camel-app-atm

## Introduction

This example uses the [Open ATM Machine finder endpoint](https://www.ing.nl/api/locator/atms/) provided by ING.

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

* Spring Boot

* Spring Web

* Spring Security

* Camel DSL JSON

* AngularJS

* JSPM

## References

-   [https://www.ing.nl/api/locator/atms/](https://www.ing.nl/api/locator/atms/)
-   [ING Developer Portal](https://developer.ing.com/openbanking/home)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
