package org.openpanfu.gameserver.handler.p2p;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.constants.PlayerToPlayerCommands;

public class CMD_SHOW_STATUS implements IP2PHandler {
    @Override
    public void handlePacket(PanfuPacket packet, String receiver, User sender) {
        PanfuPacket StatusBroadcast = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
        StatusBroadcast.writeInt(sender.getUserId());
        StatusBroadcast.writeInt(PlayerToPlayerCommands.ON_SHOW_STATUS);
        StatusBroadcast.writeString(packet.readString());
        StatusBroadcast.writeString(packet.readString());
        sender.sendForReceiver(StatusBroadcast, receiver);
    }
}
