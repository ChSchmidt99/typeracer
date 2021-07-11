# Changelog
All notable changes to this project will be documented
in this file .
The format is based on
[ Keep a Changelog ]( https :// keepachangelog . com / en /1.0.0/) ,
and this project adheres to
[ Semantic Versioning ]( https :// semver . org / spec / v2 .0.0. html ).

## [Abgabe 4] - 2021-07-07
### Added
- Custom Icon picker
- Custom Racetrack
- Race results after game screen
- Countdown before race start
- Chat on Lobby screen
- Leave button on game screen
- Singleplayer
- Extended Stats

### Changed
- Closing Client on Window Close
- Lobby list now refreshes automatically in set interval (1 sec)
- Smoother updates on game screen (time based instead of character based)
- Now showing player state on lobby screen

## [Abgabe 3] - 2021-07-04
### Added
- Names to stored lobbies on server
- Custom lobby list view cells containing name 
- send player updates to server when a game is running
- receive race updates at a set interval
- GUI functionality for game screen setup (usernames, texttotype etc.)
- key handler for userData input during game
- progress indicator during gameplay
- color indicator for correctly/wrongly typed letters  
- username requirement for entering multiplayer
- connection of gui, database and typeracer

### Changed
- List View Actions to Interface callback on cell button click
- adjusted how users are managed in the backend to better suit our architecture
- fxml cleanup and optimization for several screens

## [Abgabe 2] - 2021-06-20
### Added
- server browser screen
- create game screen
- game lobby screen  
- all necessary controllers and fxml files
- basic transition functionality

## [Abgabe 1] - 2021-06-10
### Added
- Backend lobby management (create, join, leave, start)
- Get all open lobbies
- Api interface that provides all server functions
- Runnable server that waits for socket connections and calls Api with corresponding requests
- Client that provides an interface to make server requests
- Factory classes for Request and Response
- main method
- basic startscreen
