/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.handler;

import java.util.List;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.constants.RoomCommands;

public class CMD_QUERY_SHARED_ITEMS implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		int type = packet.readInt();
		int roomToQuery = packet.readInt();
		final int interactableItems = 255;
		int[] interactables = new int[interactableItems];
		for (int temp = 0; temp < interactableItems; temp++) {
			interactables[temp] = -1;
		}

		PanfuPacket result = new PanfuPacket(Packets.RES_RECEIVE_SHARED_ITEMS);
		result.writeInt(type);
		result.writeInt(roomToQuery);
		switch (type) {
		case RoomCommands.TYPE_GETITEMSSTATE:
			List<User> users = sender.getGameServer().getSessionManager().getUsersInRoom(roomToQuery, sender.isInHome(),
					sender.getSubRoom());
			for (User u : users) {
				if (u.getInteractingWith() != -1) {
					interactables[u.getInteractingWith()] = u.getUserId();
				}
			}
			for (int userInteracting : interactables) {
				result.writeInt(userInteracting);
			}
			break;
		case RoomCommands.TYPE_UPDATEITEMSTATE:
			int itemId = packet.readInt();
			int userId = packet.readInt();
			result.writeInt(itemId);
			if (userId == -1) {
				result.writeInt(-1);
			} else {
				result.writeInt(sender.getUserId());
			}
			sender.setInteractingWith(itemId);
			sender.sendRoom(result);
			return;
		}
		sender.sendPacket(result);
	}
}
