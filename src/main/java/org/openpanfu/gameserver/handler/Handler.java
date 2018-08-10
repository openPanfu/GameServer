/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.constants.HomeCommands;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.constants.RoomCommands;
import org.openpanfu.gameserver.handler.p2p.P2PHandler;
import org.openpanfu.gameserver.util.Logger;

import java.util.HashMap;

public class Handler
{
    private static HashMap<Integer, IHandler> handlers = new HashMap<Integer, IHandler>();
    public static void initialize()
    {
        Logger.info("Initializing Handlers...");
        addPacketHandler(Packets.CMD_LOGIN, new CMD_LOGIN());
        addPacketHandler(Packets.CMD_GET_SALT, new CMD_GET_SALT());
        addPacketHandler(Packets.CMD_GET_ROOM_ATTENDEES, new CMD_GET_ROOM_ATTENDEES());
        addPacketHandler(Packets.CMD_GET_PLAYER_IDS_BY_CLOTHES, new CMD_GET_PLAYER_IDS_BY_CLOTHES());
        addPacketHandler(Packets.CMD_PLAYER_TO_PLAYER, new CMD_PLAYER_TO_PLAYER());
        addPacketHandler(Packets.CMD_MOVE, new CMD_MOVE());
        addPacketHandler(Packets.CMD_CHAT, new CMD_CHAT());
        addPacketHandler(Packets.CMD_SAFE_CHAT, new CMD_SAFE_CHAT());
        addPacketHandler(Packets.CMD_EMOTE, new CMD_EMOTE());
        addPacketHandler(Packets.CMD_ACTION, new CMD_ACTION());
        addPacketHandler(Packets.CMD_JOIN_ROOM, new CMD_JOIN_ROOM());
        addPacketHandler(Packets.CMD_JOIN_HOME, new CMD_JOIN_HOME());
        addPacketHandler(Packets.CMD_JOIN_GAME, new CMD_JOIN_GAME());
        addPacketHandler(Packets.CMD_ENTER_MULTIGAME, new CMD_ENTER_MULTIGAME());
        addPacketHandler(Packets.CMD_QUIT_GAME, new CMD_QUIT_GAME());
        addPacketHandler(RoomCommands.QUERY_SHARED_ITEMS, new CMD_QUERY_SHARED_ITEMS());

        // Home commands
        addPacketHandler(HomeCommands.CMD_UPDATE_SOUND, new CMD_UPDATE_SOUND());
        addPacketHandler(HomeCommands.CMD_UPDATE_ROOM, new CMD_UPDATE_ROOM());
        addPacketHandler(HomeCommands.CMD_CHANGE_ROOM, new CMD_CHANGE_ROOM());

        Logger.info("Registered " + handlers.size() + " Packet handlers.");
        P2PHandler.initialize();
    }
    public static IHandler getHandlerForHeader(int header)
    {
        return handlers.get(header);
    }
    public static void addPacketHandler(int header, IHandler handler)
    {
        if(!handlers.containsKey(header)) {
            handlers.put(header, handler);
        } else {
            Logger.error(String.format("Attempted to register packet header %d, this was denied because a packet handler has already been registered with that header.", header));
        }
    }
}
