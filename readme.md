# OpenPanfu GameServer

### About
Panfu is made out of two components, an InformationServer (emulated with [InformationServer](https://github.com/openPanfu/InformationServer)) and a GameServer.

The InformationServer is more for Database tasks and tasks that the other users don't have to know about (Login, Register, inventory).
While the GameServer is for multiplayer interaction in the game. (Moving, Games, multiplayer games like 4boom and hotboom).

### Installation
You need to have a [InformationServer](https://github.com/openPanfu/InformationServer) up and running with database and all.
Then change the details in GameServer.properties to match the database that the InformationServer is running on.

#### Running the GameServer
If you've got a [release](https://github.com/openPanfu/GameServer/releases) you can simply extract it and run `java -jar (jar name)`.

#### Compiling the GameServer
To compile the jar, you need [Maven](https://maven.apache.org/).
After you've installed it and confirmed you can run `mvn -v`, run `mvn clean compile package`

A jar file will appear in the target directory, now copy it and GameServer.properties to a directory and run `java -jar (jar name)`.

#### Adding a new GS to the InformationServer

In your database, there's a table called gameservers, add a new entries for the gameservers you want to host.

All entries in that table will be hosted from this server.