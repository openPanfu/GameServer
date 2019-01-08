package org.openpanfu.gameserver.handler.special;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.handler.IHandler;
import org.openpanfu.gameserver.util.Logger;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class CMD_INFOMESSAGE implements IHandler {
	private List<String> blockList = new ArrayList<String>();

	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		String externalIp = ((InetSocketAddress) sender.getChannel().remoteAddress()).getAddress().getHostAddress();

		if (blockList.contains(externalIp)) {
			Logger.error(String.format("Denied request of %s, ip has been blocked.", externalIp));
			sender.disconnect();
			return;
		}

		String key = packet.readString();
		String command = packet.readString();
		int userId;
		if (sender.getGameServer().getKey().equals(key)) {
			switch (command) {
			case "testConnection":
				Logger.info(String.format("Connection test from %s success.", externalIp));
				break;
			case "kickUser":
				userId = packet.readInt();
				User toKick = GameServer.getUserById(userId);
				if (toKick != null)
					toKick.disconnect();
				break;
			case "updateBuddyStatus":
				userId = packet.readInt();
				int buddyId = packet.readInt();
				int status = packet.readInt();
				User toInformOfNewStatus = GameServer.getUserById(userId);
				PanfuPacket updateBuddyStatus = new PanfuPacket(Packets.RES_UPDATE_BUDDY_STATUS);
				updateBuddyStatus.writeInt(buddyId);
				updateBuddyStatus.writeInt(status);
				if (toInformOfNewStatus != null) {
					toInformOfNewStatus.sendPacket(updateBuddyStatus);
				}
				break;
			}
		} else {
			blockList.add(externalIp);
			Logger.error(String.format("Invalid key from %s, ip has been blocked.", externalIp));
		}
	}
}
