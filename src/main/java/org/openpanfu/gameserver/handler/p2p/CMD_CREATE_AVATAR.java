package org.openpanfu.gameserver.handler.p2p;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.constants.PlayerToPlayerCommands;

public class CMD_CREATE_AVATAR implements IP2PHandler {
	@Override
	public void handlePacket(PanfuPacket packet, String receiver, User sender) {
		PanfuPacket response = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
		int x = packet.readInt();
		int y = packet.readInt();
		String action = packet.readString();
		int rotation = packet.readInt();
		String timeTravel = packet.readString();
		String pokopetType = packet.readString();
		int sheriff = packet.readInt();
		String clothes = packet.readString();
		response.writeInt(sender.getUserId());
		response.writeInt(PlayerToPlayerCommands.ON_CREATE_AVATAR);
		response.writeInt(x);
		response.writeInt(y);
		response.writeString(action);
		response.writeInt(rotation);
		response.writeString(timeTravel);
		response.writeString(pokopetType);
		response.writeInt(sender.getSheriff());
		response.writeString(clothes);
		sender.sendForReceiver(response, receiver);
	}
}
