# Server protocol
(Work in progress)

## User management
### Register a new user
Send request to register a new user with the given name:
```json
{
    "type" : "register",
    "playerName" : "<some username>"
}
```
If the registration process was successful, a Request with the assigned user id is sent back:
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
    "userId" : "<some userId>"
}
```
After successfully creating a new lobby, the specified user is added as host with equivalent behavior to the following join lobby request.
### Join existing lobby
Send request to join an existing lobby with the provided lobby id.
```json
{
    "type" : "join lobby",
    "userId" : "<some userId>",
    "lobbyId" : "<some lobbyId>"
}
```
If lobby was joined successfully, a response with information about the created lobby is returned. The same response will also be sent every time the lobby is changed in some way. For example when players join or leave.
### Response on Lobby updates
```json
{
    "type" : "lobby update",
    "lobby" : {
      "id" : "<some lobbyId>",
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
      "players" : ["<string list of player names>"],
      "isRunning" : "<boolean>"
    }]
}
```
isRunning will be true, if there currently is a race running in the given lobby and false otherwise. 
### Mark or unmark player as ready
Send player ready request to mark the user as ready to join the next race, or unmark the user again.
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
      "players" : "<string list of all joined player names>"
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
        "percentProgress" : "<progress percentage in range [0,1]>"
      }
    ]
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