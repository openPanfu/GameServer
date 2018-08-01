package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMD_CHAT implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender)
    {
        String text = packet.readString();
        if(text.length() > 120) {
            sender.disconnect("KICK_SHUTDOWN_MSG");
        }
        List<String> textParts = new ArrayList(Arrays.asList(text.split(" ")));
        if(textParts.get(0).startsWith("#")) {
            textParts.remove(0);
        }
        text = String.join(" ", textParts).replaceAll("\\<[^>]*>","");

        if(sender.getSheriff() > 0) {
            text = "#" + GameServer.getProperties().getProperty("chat.sheriff.prefix") + " " + text;
        }

        PanfuPacket chatPacket = new PanfuPacket(Packets.RES_CHAT_MSG);
        chatPacket.writeInt(sender.getUserId());
        chatPacket.writeString(text);
        sender.sendRoom(chatPacket);
    }
}
