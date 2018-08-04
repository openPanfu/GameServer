package org.openpanfu.gameserver.handler.p2p;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;

public interface IP2PHandler {
    void handlePacket(PanfuPacket packet, String reciever, User sender);
}
