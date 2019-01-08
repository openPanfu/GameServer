/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;

public class CMD_GET_SALT implements IHandler {
	@Override
	public void handlePacket(PanfuPacket packet, User sender) {
		PanfuPacket salt = new PanfuPacket(Packets.RES_ON_GET_SALT);
		salt.writeString("P4nfu8Ri5$3*m/#4nt1Ch34t2gHTu.%ru1{<0?K_&45fS4lt6,]-lO5=+354y");
		sender.sendPacket(salt);
	}
}
