package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;

public class CMD_ADDBUDDY implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		int buddyId = packet.readInt();
		String yourUsername = packet.readString();
		User buddy = sender.getGameServer().getSessionManager().getUserById(buddyId);
		if (buddy != null) {
			PanfuPacket inviteMessage = new PanfuPacket(Packets.RES_ADD_BUDDY);
			inviteMessage.writeInt(sender.getUserId());
			inviteMessage.writeString(sender.getUsername());
			buddy.sendPacket(inviteMessage);
		}
	}
}
