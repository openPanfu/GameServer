package org.openpanfu.gameserver.handler;

import java.util.List;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;

public class CMD_GET_ALL_HOUSES implements IHandler {

	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		PanfuPacket response = new PanfuPacket(Packets.RES_RECEIVE_ALLHOUSES);
		GameServer gameserver = sender.getGameServer();
		List<User> users = gameserver.getSessionManager().getUsers();
		for (User user : users) {
			if (Integer.valueOf(GameServer.getProperties().getProperty("treehouses.showSheriffAsGold")) != 0)
				response.writeString(
						String.format("%d:%s:%d", user.getUserId(), user.getUsername(), user.getGoldpanda()));
			else
				response.writeString(String.format("%d:%s:%d", user.getUserId(), user.getUsername(),
						(user.getSheriff() > 1) ? 1 : 0));
		}
		sender.sendPacket(response);
	}

}
