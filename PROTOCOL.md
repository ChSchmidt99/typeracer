# Server protocol
(Work in progress)

## User management
### Register a new userData
Send request to register a new userData with the given name:
```json
{
    "type" : "register",
    "playerName" : "<some username>"
}
```
If the registration process was successful, a Request with the assigned userData id is sent back:
```json
{
    "type" : "registered",
    "userId" : "<some userId>"
}
```
## Lobby controls
### Create new lobby
Send request to create a new lobby and join as host.
```json
{
    "type" : "new lobby",
    "userId" : "<some userId>",
    "iconId" : "<id of selected icon>",
    "lobbyName" : "<some name>"
}
```
After successfully creating a new lobby, the specified userData is added as host with equivalent behavior to the following join lobby request.
### Join existing lobby
Send request to join an existing lobby with the provided lobby id.
```json
{
    "type" : "join lobby",
    "userId" : "<some userId>",
    "iconId" : "<id of selected icon>",
    "lobbyId" : "<some lobbyId>"
}
```
### Request Lobby update
A updated version of the current lobby can be requested using the following request.
```json
{
    "type" : "get lobby update"
}
```
### Response on Lobby updates
After a joining a Lobby, requesting a lobby update or on lobby changes, the following response will be sent to the client.
```json
{
    "type" : "lobby update",
    "lobby" : {
      "id" : "<some lobbyId>",
      "name" : "<some name>",
      "players" : ["<string list of player names>"],
      "isRunning" : "<boolean>"
    }
}
```
### Leave current lobby
Leave lobby request can be sent when currently in a lobby to leave it.
```json
{
    "type" : "leave lobby"
}
```
After leaving, no more updates from the associated lobby will be sent. A new host will be assigned if the current one leaves. Any empty lobbies will be deleted immediately.
### Get all open lobbies
A list of all currently managed lobbies can be requested using the following request.
```json
{
    "type" : "get lobbies"
}
```
After processing, a Response including all lobbies will be sent back.
```json
{
    "type" : "open lobbies",
    "lobbies" : [{
      "id" : "<some lobbyId>",
      "players" : [{
        "name" : "<some name>",
        "userId" : "<some user id>",
        "iconId" : "<some icon id>",
        "state" : "<player state as string>"
      }],
      "isRunning" : "<boolean>"
    }]
}
```
isRunning will be true, if there currently is a race running in the given lobby and false otherwise. 
### Mark or unmark player as ready
Send player ready request to mark the userData as ready to join the next race, or unmark the userData again.
```json
{
    "type" : "player ready",
    "isReady" : "<boolean>"
}
```
### Start match in current lobby
Start match request will try to start a race in the current lobby. It will fail if there is no current lobby, or no player is marked as ready.
```json
{
    "type" : "start race"
}
```
If the game is started successfully, a game starting response is broadcast to all ready users in the current lobby.
```json
{
    "type" : "race starting",
    "race" : {
      "textToType" : "<string to type>",
      "players" : [{ 
        "name" : "<some name>",
        "userId" : "<some userId>",
        "iconId" : "<id of selected icon>"
      }]
    }
}
```
## Race controls 
### Send progress update
After a race was started, all clients need to send frequent update requests to the server. These updates consist of a snapshot of the typing progress including the local start timestamp and timestamp of the update in unix epoch format. Additionally, all updates include the total number of characters typed and mistakes made.  
```json
{
    "type" : "update progress",
    "snapshot" : {
      "raceStartTime" : "<local time of race start as unix epoch long>",
      "timestamp" : "<timestamp of update as unix epoch long>",
      "progress" : "<number of typed characters>",
      "mistakes" : "<number of mistakes made>"
    }
}
```
### Receive updates 
While a race is running, the server will send updates to all clients in some given interval. These updates include a list of the current progress of all players consisting of a progress percentage in the range [0,1] und their words per minute.
```json
{
    "type" : "race update",
    "playerUpdates" : [
      {
        "userId" : "<some userId>",
        "wpm" : "<number of words per minute>",
        "percentProgress" : "<progress percentage in range [0,1]>",
        "isFinished" : "<boolean>",
        "raceDuration" : "<duration of race as unix epoch long>"
      }
    ]
}
```
### Checkered Flag
After the first player reached the finish line, the server will send a Checkered Flag response to all clients. This response includes the server time of the race finish. After that time, no more updates will be received or sent by the server.   
```json
{
    "type" : "checkered flag",
    "raceStop" : "<timestamp of last update as unix epoch long>"
}
```
### Request Race result
The result of the previous game in a lobby can be requested as follows: 
```json
{
    "type" : "previous race result"
}
```
### Race results
After a race was finished, or the result was requested, the following response will be sent be the server.
```json
{
  "type" : "race result",
  "raceResult" : {
    "duration" : "<duration until first player finished as unix epoch long in sec>",
    "classification" : [{
        "userData" : {
          "name" : "<some name>",
          "userId" : "<some user id>",
          "iconId" : "<some icon id>",
          "state" : "<state as string>"
        },
        "wpm" : "<words per minute>",
        "mistakes" : "<number of mistakes>",
        "place" : "<place the player ended up in>"
      }]
  }  
}
```
## Error Handling
### Error Response
In case there is something wrong with the request, or an internal server error occures.
```json
{
  "type" : "error",
  "message" : "*some error message*"
}
```