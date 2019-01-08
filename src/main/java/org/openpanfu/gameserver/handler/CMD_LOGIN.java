/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.handler;

import java.util.Random;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.database.UserData;
import org.openpanfu.gameserver.database.dao.UserDAO;
import org.openpanfu.gameserver.plugin.PluginManager;
import org.openpanfu.gameserver.util.Logger;

public class CMD_LOGIN implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		int userId = packet.readInt();
		int sessionTicket = packet.readInt();
		int startRoom = packet.readInt();
		UserData data = UserDAO.getByIdAndTicket(userId, sessionTicket);
		if (data != null) {
			sender.setUserId(data.id);
			sender.setGoldpanda(data.goldpanda);
			sender.setUsername(data.name);
			sender.setSheriff(data.sheriff);
			sender.setCurrentGameServer(sender.getGameServer().getId());
			Logger.info("User " + sender.getUsername() + " logged in!");
			sender.getGameServer().getSessionManager().addUser(sender);
			sender.setLoggedIn(true);
			sender.nullTicketId();

			// Plugins can deny a user's login by either returning false or setting their
			// loggedIn to false.
			if (PluginManager.handleUserConnect(sender) && sender.isLoggedIn()) {
				PanfuPacket onLoginPacket = new PanfuPacket(Packets.RES_ON_LOGIN);
				onLoginPacket.writeString("OK");
				sender.sendPacket(onLoginPacket);
				sender.setX(450);
				sender.setY(450);
				int[] randomRooms = { 1, 2, 3, 4 };
				if (startRoom > 0)
					sender.joinRoom(startRoom);
				else
					sender.joinRoom(getRandom(randomRooms));
				sender.getGameServer().updateUserCount();
				PluginManager.onUserConnect(sender);
			} else {
				sender.disconnect();
			}
		} else {
			sender.sendString("0;FAILED|10;0|");
			sender.disconnect("KICK_LOGIN_FAILED_MSG");
		}
	}

	public static int getRandom(int[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
}
