# Typeracer Team 8 

The super cool typeracer of team 8.

## Dependencies

The project requires Java 11.
It was tested on Arch with openjdk 11.0.11 2021-04-20.

The project is built with `gradle`, version 5.6.4. The provided `gradlew` wrapper automatically downloads and uses
the correct gradle version.


## Building the Project

On Linux and Mac OS, run the following command from the project's root directory to compile the program,
run all checks and create an executable jar:

```
./gradlew build jar
```

On Windows, run the following command from the project's root directory to compile the program,
run all checks and create an executable jar:

```
./gradlew.bat build jar
```

If the command succeeds, the jar is found in `build/libs/sample.jar`.
This jar can be executed with `java -jar build/libs/sample.jar`


## Running the Program

To run the application during development without any checks, run `./gradlew run` .

To start the server, run `./gradlew runServer` .

To run the provided jar releases use `java -jar server.jar` or `java -jar client_<OS>.jar`.
`client_linux.jar` was tested on Arch Linux.
`client_macOS.jar` was tested on macOS Catalina 10.15.7.

## Code Formatting 

Run `./gradlew goJF` to automatically reformat all .java files to be Google style guide conform. 