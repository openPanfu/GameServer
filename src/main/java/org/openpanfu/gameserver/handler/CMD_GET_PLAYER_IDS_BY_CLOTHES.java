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

public class CMD_GET_PLAYER_IDS_BY_CLOTHES implements IHandler {
    /* Now, this is an interesting story. */
    /* The only time this is used is for time travel, we don't need to handle it.. yet. */
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        PanfuPacket response = new PanfuPacket(Packets.RES_PLAYER_IDS_BY_CLOTHES_REQUEST);
        sender.sendPacket(response);
    }
}
