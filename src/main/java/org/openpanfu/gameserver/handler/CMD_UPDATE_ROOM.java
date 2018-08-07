package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.HomeCommands;

public class CMD_UPDATE_ROOM implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        PanfuPacket updateRoom = new PanfuPacket(HomeCommands.ON_UPDATE_ROOM);
        sender.sendRoom(updateRoom);
    }
}
