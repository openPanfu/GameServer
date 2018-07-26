package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;

public class CMD_JOIN_GAME implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        int gameId = packet.readInt();
        sender.joinGame(gameId);
    }
}
