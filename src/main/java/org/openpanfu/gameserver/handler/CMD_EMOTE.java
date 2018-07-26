package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;

public class CMD_EMOTE implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        int emoteId = packet.readInt();
        PanfuPacket emotePacket = new PanfuPacket(Packets.RES_EMOTE_MSG);
        emotePacket.writeInt(sender.getUserId());
        emotePacket.writeInt(emoteId);
        sender.sendRoom(emotePacket);
    }
}
