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
        PanfuPacket response = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
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
                    response.writeInt(sender.getUserId());
                    response.writeInt(PlayerToPlayerCommands.ON_CREATE_AVATAR);
                    response.writeInt(x);
                    response.writeInt(y);
                    response.writeString(action);
                    response.writeInt(rotation);
                    response.writeString(timeTravel);
                    response.writeString(pokopetType);
                    response.writeInt(sender.getSheriff());
                    response.writeString(clothes);
                    sender.sendRoomExcludingMe(response);
                    break;
                case PlayerToPlayerCommands.CMD_UPDATE_AVATAR:
                    String pokopet = packet.readString();
                    int unknown = packet.readInt();
                    String playerString = packet.readString();
                    response.writeInt(sender.getUserId());
                    response.writeInt(PlayerToPlayerCommands.ON_UPDATE_AVATAR);
                    response.writeString(pokopet);
                    response.writeInt(sender.getSheriff());
                    response.writeString(playerString);
                    sender.sendRoom(response);
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
