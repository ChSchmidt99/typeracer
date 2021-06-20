# Server protocol
(Work in progress)

## Game initialization
### Register a new user
{
    "type":"register",
    "playerName":"*some username*"
}

### Create new lobby
{
    "type":"new lobby",
    "userId":"*some userId*"
}

### Join existing lobby
{
    "type":"join lobby",
    "userId":"*some userId*",
    "lobbyId":"*some gameId*"
}

### Leave current lobby
{
    "type":"leave lobby"
}

### Start match in current lobby
{
    "type":"start race"
}