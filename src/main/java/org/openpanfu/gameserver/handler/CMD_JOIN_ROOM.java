/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;

public class CMD_JOIN_ROOM implements IHandler {

    @Override
    public void handlePacket(PanfuPacket packet, User sender)
    {
        int roomId = packet.readInt();
        int x = packet.readInt();
        int y = packet.readInt();
        sender.setX(x);
        sender.setY(y);
        sender.joinRoom(roomId);
    }
}
