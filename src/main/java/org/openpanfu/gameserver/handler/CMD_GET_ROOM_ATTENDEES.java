/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.handler;

import java.util.List;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;

public class CMD_GET_ROOM_ATTENDEES implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		PanfuPacket roomAttendees = new PanfuPacket(Packets.RES_GET_ROOM_ATTENDEES);
		roomAttendees.writeString(
				getRoomString(sender.getRoomId(), sender.isInHome(), sender.getSubRoom(), sender.getGameServer()));
		sender.sendPacket(roomAttendees);

		PanfuPacket setAvatar = new PanfuPacket(Packets.RES_SET_AVATAR);
		setAvatar.writeInt(sender.getUserId());
		setAvatar.writeInt(sender.getRoomId());
		setAvatar.writeInt(sender.getX());
		setAvatar.writeInt(sender.getY());
		setAvatar.writeString(sender.getUsername());
		sender.sendRoomExcludingMe(setAvatar);
		sender.setChatEnabled(sender.getGameServer().isChatEnabled());
	}

	private String getRoomString(int roomId, boolean inHome, int subroom, GameServer gameServer) {
		String roomString = String.valueOf(roomId);
		List<User> users = gameServer.getSessionManager().getUsersInRoom(roomId, inHome, subroom);
		for (User user : users) {
			if (user.isInHome() == inHome) {
				roomString += ";" + user.getPlayerString();
			}
		}
		return roomString;
	}
}
