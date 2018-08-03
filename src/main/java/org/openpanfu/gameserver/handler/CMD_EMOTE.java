package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;

public class CMD_EMOTE implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        if((System.currentTimeMillis() - sender.getLastChatMessageTime()) < 500) {
            sender.disconnect("CMD_EMOTE > You are sending emotes too fast!");
            return;
        }
        int emoteId = packet.readInt();
        PanfuPacket emotePacket = new PanfuPacket(Packets.RES_EMOTE_MSG);
        emotePacket.writeInt(sender.getUserId());
        emotePacket.writeInt(emoteId);
        sender.setLastChatMessageTime(System.currentTimeMillis());
        sender.sendRoom(emotePacket);
    }
}
