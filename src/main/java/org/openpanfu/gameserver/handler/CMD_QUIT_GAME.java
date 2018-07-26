package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;

public class CMD_QUIT_GAME implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        int gameId = packet.readInt();

        // Makes the user rejoin the room they initially joined the game in.
        // We don't use sender.joinRoom() because then they'll be unset.
        PanfuPacket joinRoom = new PanfuPacket(Packets.RES_ON_ROOM_JOINED);
        joinRoom.writeInt(sender.getRoomId());
        sender.sendPacket(joinRoom);
    }
}
