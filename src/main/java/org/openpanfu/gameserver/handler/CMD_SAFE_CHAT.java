package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;

public class CMD_SAFE_CHAT implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        if((System.currentTimeMillis() - sender.getLastChatMessageTime()) < 100) {
            sender.disconnect("CMD_CHAT > You are chatting too fast!");
            return;
        }
        if(Integer.valueOf(GameServer.getProperties().getProperty("chat.antispam.enabled")) != 0) {
            int seconds = Integer.parseInt(GameServer.getProperties().getProperty("chat.antispam.secondsbetweenmessages"));
            if ((System.currentTimeMillis() - sender.getLastChatMessageTime()) < (seconds * 1000)) {
                sender.giveChatSpamWarning();
                return;
            }
        }
        String text = packet.readString().replaceAll("\\<[^>]*>","");;
        if(text.length() > 120) {
            sender.disconnect("KICK_SHUTDOWN_MSG");
        }
        sender.setLastChatMessage(text);
        sender.setLastChatMessageTime(System.currentTimeMillis());
        if(sender.getSheriff() > 0) {
            text = "#" + GameServer.getProperties().getProperty("chat.sheriff.prefix") + " " + text;
        }

        PanfuPacket chatPacket = new PanfuPacket(Packets.RES_CHAT_MSG);
        chatPacket.writeInt(sender.getUserId());
        chatPacket.writeString(text);
        sender.sendRoom(chatPacket);
    }
}
