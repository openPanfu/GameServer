package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;

public class CMD_ENTER_MULTIGAME implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        int gameId = packet.readInt();
        int partnerId = packet.readInt();
        if(gameId == 25) { // 4boom
            sender.joinGame(25);
            sender.getGameServer().getFourBoom().onEnter(sender);
        }
        if(gameId == 41) { // RPS
            User partner = sender.getGameServer().getSessionManager().getUserById(partnerId);
            if(partner != null) {
                sender.joinGame(41);
                sender.setMultiGamePartner(partner);
                sender.getGameServer().getRockPaperScissors().onEnter(sender);
            }
        }
    }
}
