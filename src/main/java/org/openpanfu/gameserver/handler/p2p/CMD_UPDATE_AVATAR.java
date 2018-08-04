package org.openpanfu.gameserver.handler.p2p;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.constants.PlayerToPlayerCommands;

public class CMD_UPDATE_AVATAR implements IP2PHandler {
    @Override
    public void handlePacket(PanfuPacket packet, String reciever, User sender) {
        PanfuPacket response = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
        String pokopet = packet.readString();
        int unknown = packet.readInt();
        String playerString = packet.readString();
        response.writeInt(sender.getUserId());
        response.writeInt(PlayerToPlayerCommands.ON_UPDATE_AVATAR);
        response.writeString(pokopet);
        response.writeInt(sender.getSheriff());
        response.writeString(playerString);
        sender.sendRoom(response);
    }
}
