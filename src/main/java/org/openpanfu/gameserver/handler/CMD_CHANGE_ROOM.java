package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;

public class CMD_CHANGE_ROOM implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		// STUB
		// Not sure why they send this, seeing as I can just read the room the user is
		// in...
		int roomOwner = packet.readInt();
		// The subroom to enter
		int subRoom = packet.readInt();
		// Start x and y
		int x = packet.readInt();
		int y = packet.readInt();
	}
}
