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

public class CMD_PLAYER_TO_PLAYER implements IHandler {
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        String Reciever = packet.readString();
        int Command = packet.readInt();
        if(Reciever.equals(String.valueOf(PlayerToPlayerCommands.P2P_RECEIVER_ROOM))) {
            switch(Command) {
                case PlayerToPlayerCommands.CMD_CREATE_AVATAR:
                    int x = packet.readInt();
                    int y = packet.readInt();
                    String action = packet.readString();
                    int rotation = packet.readInt();
                    String timeTravel = packet.readString();
                    String pokopetType = packet.readString();
                    int sheriff = packet.readInt();
                    String clothes = packet.readString();
                    PanfuPacket Response = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
                    Response.writeInt(sender.getUserId());
                    Response.writeInt(PlayerToPlayerCommands.ON_CREATE_AVATAR);
                    Response.writeInt(x);
                    Response.writeInt(y);
                    Response.writeString(action);
                    Response.writeInt(rotation);
                    Response.writeString(timeTravel);
                    Response.writeString(pokopetType);
                    Response.writeInt(sender.getSheriff());
                    Response.writeString(clothes);
                    sender.sendRoomExcludingMe(Response);
                    break;
                case PlayerToPlayerCommands.CMD_SHOW_STATUS:
                    PanfuPacket StatusBroadcast = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
                    StatusBroadcast.writeInt(sender.getUserId());
                    StatusBroadcast.writeInt(PlayerToPlayerCommands.ON_SHOW_STATUS);
                    StatusBroadcast.writeString(packet.readString());
                    StatusBroadcast.writeString(packet.readString());
                    sender.sendRoom(StatusBroadcast);
                    break;
                case PlayerToPlayerCommands.CMD_HIDE_STATUS:
                    PanfuPacket HideStatusBroadcast = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
                    HideStatusBroadcast.writeInt(sender.getUserId());
                    HideStatusBroadcast.writeInt(PlayerToPlayerCommands.ON_HIDE_STATUS);
                    HideStatusBroadcast.writeString(packet.readString());
                    sender.sendRoom(HideStatusBroadcast);
                    break;
            }
        }
    }
}
