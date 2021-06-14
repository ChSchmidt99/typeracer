# Server protocol
(Work in progress)

## Game initialization
### Register a new user
{
    "type":"register",
    "playerName":"*some username*"
}

### Create new game
{
    "type":"new game",
    "userId":"*some userId*"
}

### Join existing game
{
    "type":"join game",
    "userId":"*some userId*",
    "gameId":"*some gameId*"
}