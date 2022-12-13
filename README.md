## Installation / Setup
It is necessary to install jdk 18 and maven, open the terminal and type the commands below
```sh
brew install java
java --version
```
```sh
brew install openjdk@18
javac --version
```
```sh
brew install maven
mvn --version
```
## Usage
To run the tests use the `cd` command and locate the root directory of the project, and type the command below
```sh
mvn clean test
```
To view the execution in more detail, you can access the execution report that was generated through the path
```sh
yoloTestApi/target/surefire-reports/index.html
```
