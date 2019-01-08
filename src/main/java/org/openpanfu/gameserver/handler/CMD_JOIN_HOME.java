package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;

public class CMD_JOIN_HOME implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		// The user who's treehouse you'd like to enter
		int userId = packet.readInt();

		// Start X and Y
		int x = packet.readInt();
		int y = packet.readInt();

		sender.setX(x);
		sender.setY(y);

		sender.joinHome(userId);
	}
}
