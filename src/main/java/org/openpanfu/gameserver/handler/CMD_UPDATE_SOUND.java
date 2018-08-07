package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.HomeCommands;

public class CMD_UPDATE_SOUND implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        // Updates the current song being played by the jukebox in someone's home.

        // Song name as in /features/jukebox/conf/jukebox.xml
        // For example, song_b4_nl would be No Limit.
        String songName = packet.readString();

        // Is the sender in their own room?
        if(sender.getRoomId() == sender.getUserId() && sender.isInHome()) {
            PanfuPacket updateSong = new PanfuPacket(HomeCommands.ON_UPDATE_SOUND);
            updateSong.writeString(songName);
            sender.sendRoom(updateSong);
        }
    }
}