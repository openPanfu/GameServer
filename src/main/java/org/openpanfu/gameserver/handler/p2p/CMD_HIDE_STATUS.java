package org.openpanfu.gameserver.handler.p2p;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.constants.PlayerToPlayerCommands;

public class CMD_HIDE_STATUS implements IP2PHandler {
    @Override
    public void handlePacket(PanfuPacket packet, String receiver, User sender) {
        PanfuPacket HideStatusBroadcast = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
        HideStatusBroadcast.writeInt(sender.getUserId());
        HideStatusBroadcast.writeInt(PlayerToPlayerCommands.ON_HIDE_STATUS);
        HideStatusBroadcast.writeString(packet.readString());
        sender.sendForReceiver(HideStatusBroadcast, receiver);
    }
}
