/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.constants.PlayerToPlayerCommands;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.handler.p2p.IP2PHandler;
import org.openpanfu.gameserver.handler.p2p.P2PHandler;
import org.openpanfu.gameserver.util.Logger;

public class CMD_PLAYER_TO_PLAYER implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		String reciever = packet.readString();
		int command = packet.readInt();

		IP2PHandler packetHandler = P2PHandler.getHandlerForHeader(command);
		if (packetHandler == null) {
			Logger.warning("Unhandled P2P packet: " + command);
		} else {
			packetHandler.handlePacket(packet, reciever, sender);
		}

	}
}
