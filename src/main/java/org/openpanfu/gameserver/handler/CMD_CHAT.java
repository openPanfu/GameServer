package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.commands.Commands;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMD_CHAT implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		if ((System.currentTimeMillis() - sender.getLastChatMessageTime()) < 100) {
			sender.disconnect("CMD_CHAT > You are chatting too fast!");
			return;
		}
		if (Integer.valueOf(GameServer.getProperties().getProperty("chat.antispam.enabled")) != 0) {
			int seconds = Integer
					.parseInt(GameServer.getProperties().getProperty("chat.antispam.secondsbetweenmessages"));
			if ((System.currentTimeMillis() - sender.getLastChatMessageTime()) < (seconds * 1000)) {
				sender.giveChatSpamWarning();
				return;
			}
		}
		if (sender.getGameServer().isChatEnabled()) {
			String text = packet.readString();
			if (text.length() > 120) {
				sender.disconnect("KICK_SHUTDOWN_MSG");
			}
			List<String> textParts = new ArrayList(Arrays.asList(text.split(" ")));
			if (textParts.get(0).startsWith("#")) {
				textParts.remove(0);
			}

			if (textParts.get(0).startsWith(GameServer.getProperties().getProperty("chat.commands.prefix"))) {
				String handle = textParts.remove(0).substring(1).toLowerCase();
				String[] commandParameters = textParts.toArray(new String[0]);
				Commands.executeCommand(sender, handle, commandParameters);
				return;
			}

			text = String.join(" ", textParts).replaceAll("\\<[^>]*>", "");
			if (PluginManager.handleUserChat(sender, text)) {
				sender.setLastChatMessage(text);
				sender.setLastChatMessageTime(System.currentTimeMillis());
				if (sender.getSheriff() > 0) {
					text = "#" + GameServer.getProperties().getProperty("chat.sheriff.prefix") + " " + text;
				}

				PanfuPacket chatPacket = new PanfuPacket(Packets.RES_CHAT_MSG);
				chatPacket.writeInt(sender.getUserId());
				chatPacket.writeString(text);
				sender.sendRoom(chatPacket);
				PluginManager.onUserChat(sender, text);
			}
		}
	}
}
