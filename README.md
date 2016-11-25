# Server side for [WhichOne App](https://github.com/l-o-b-s-t-e-r/which-one-android)

### Getting Started

* import this repository to IntelliJ IDEA as Gradle project
* install PostgreSQL
* create database "decider"
* locate [decider.properties file](https://github.com/l-o-b-s-t-e-r/which-one-server/blob/master/decider.properties) in %HOME_DIRECTORY%
* override properties for db.url, db.name and db.password
* open Gradle tab and execute the following tasks
  * clean
  * install
  * tomcatRun
