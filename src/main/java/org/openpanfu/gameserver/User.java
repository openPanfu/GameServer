/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver;

import io.netty.channel.Channel;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.constants.PlayerToPlayerCommands;
import org.openpanfu.gameserver.database.Database;
import org.openpanfu.gameserver.handler.Handler;
import org.openpanfu.gameserver.handler.IHandler;
import org.openpanfu.gameserver.util.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class User {
    private int userId = -1;
    private String username;
    private boolean isLoggedIn = false;
    private int sheriff = 0;
    private int goldpanda = 0;
    private Channel channel;
    private GameServer gameServer;
    private boolean inHome = false;
    private int gameId = -1;
    private int roomId = -1;
    private int x;
    private int y;
    private int status = 0;
    private int rot = 0;
    private int interactingWith = -1;
    private long timeUntilWalkComplete;
    private String lastChatMessage = "";
    private long lastChatMessageTime = System.currentTimeMillis();
    private String lastActionPerformed = "";
    private long lastActionPerformedTime = System.currentTimeMillis();
    private int spamWarning = 0;

    public User(Channel channel, GameServer gameServer)
    {
        this.timeUntilWalkComplete = System.currentTimeMillis();
        this.channel = channel;
        this.gameServer = gameServer;
    }

    public void handlePacket(PanfuPacket panfuPacket)
    {
        IHandler packetHandler = Handler.handlers.get(panfuPacket.getHeader());
        if(!this.isLoggedIn) {
            if(panfuPacket.getHeader() != Packets.CMD_LOGIN && panfuPacket.getHeader() != Packets.CMD_GET_SALT) {
                Logger.warning("User tried inputting a forbidden packet for the state he is in! (Not logged in) Attempted packet: " + panfuPacket.getHeader());
                return;
            }
        }

        if(packetHandler == null) {
            Logger.warning("Unhandled packet: " + Packets.headerToName(panfuPacket.getHeader()) + " (" + panfuPacket.getHeader() + ")");
        } else {
            Handler.handlers.get(panfuPacket.getHeader()).handlePacket(panfuPacket, this);
        }
    }

    public void disconnect()
    {
        this.onDisconnect();
        this.channel.disconnect();
    }

    public void disconnect(String message)
    {
        this.sendString("2;" + message + "|");
        this.disconnect();
    }

    public void setChatEnabled(boolean enabled)
    {
        PanfuPacket packetToInform = new PanfuPacket(Packets.RES_TOGGLE_SAFE_CHAT);
        packetToInform.writeInt(enabled ? 0 : 1);
        this.sendPacket(packetToInform);
    }

    public void sendAlert(String message)
    {
        PanfuPacket gameServerAlert = new PanfuPacket(Packets.RES_ON_SHOW_GS_MSG);
        gameServerAlert.writeString(message + "\r\n");
        this.sendPacket(gameServerAlert);
    }

    public void setCurrentGameServer(int gameServerId)
    {
        try {
            Connection database = Database.getConnection();
            PreparedStatement preparedStatement = database.prepareStatement("UPDATE users SET current_gameserver = ? where id = ?");
            preparedStatement.setInt(1, gameServerId);
            preparedStatement.setInt(2, this.userId);
            preparedStatement.executeUpdate();
            database.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(PanfuPacket packet)
    {
        this.sendString(packet.toString());
    }

    public void sendString(String data)
    {
        Logger.debug("{ " + data);
        this.channel.writeAndFlush(data);
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getSheriff()
    {
        return sheriff;
    }

    public void setSheriff(int sheriff)
    {
        this.sheriff = sheriff;
    }

    public int getGoldpanda()
    {
        return goldpanda;
    }

    public void setGoldpanda(int goldpanda)
    {
        this.goldpanda = goldpanda;
    }

    public boolean isInHome()
    {
        return inHome;
    }

    public void setInHome(boolean inHome)
    {
        this.inHome = inHome;
    }

    public Channel getChannel()
    {
        return this.channel;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void onDisconnect()
    {
        Logger.debug("User disconnected");
        if(this.roomId > -1) {
            PanfuPacket statusBroadcast = new PanfuPacket(Packets.RES_PLAYER_TO_PLAYER);
            statusBroadcast.writeInt(userId);
            statusBroadcast.writeInt(PlayerToPlayerCommands.ON_SHOW_STATUS);
            statusBroadcast.writeString("Offline");
            statusBroadcast.writeString("I gotta go now. Bye!");
            this.sendRoomExcludingMe(statusBroadcast);
            PanfuPacket unset = new PanfuPacket(Packets.RES_UNSET_AVATAR);
            unset.writeInt(this.userId);
            this.sendRoomExcludingMe(unset);
        }

        if(this.isLoggedIn) {
            gameServer.getSessionManager().removeUserById(this.userId);
            gameServer.updateUserCount();
            try {
                Connection database = Database.getConnection();
                PreparedStatement preparedStatement = database.prepareStatement("UPDATE users SET current_gameserver = NULL where id = ?");
                preparedStatement.setInt(1, this.userId);
                preparedStatement.executeUpdate();
                database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void move(int toX, int toY, int walkms, int type)
    {
        this.x = toX;
        this.y = toY;
        PanfuPacket movePacket = new PanfuPacket(Packets.RES_MOVE_AVATAR);
        movePacket.writeInt(this.getUserId());
        movePacket.writeInt(walkms);
        movePacket.writeInt(toX);
        movePacket.writeInt(toY);
        movePacket.writeInt(type);
        this.sendRoom(movePacket);
    }

    public void joinRoom(int roomId)
    {
        if(this.roomId > -1) {
            PanfuPacket unset = new PanfuPacket(Packets.RES_UNSET_AVATAR);
            unset.writeInt(this.userId);
            this.sendRoomExcludingMe(unset);
        }
        this.roomId = roomId;
        PanfuPacket joinRoom = new PanfuPacket(Packets.RES_ON_ROOM_JOINED);
        joinRoom.writeInt(this.roomId);
        this.sendPacket(joinRoom);
    }

    public String getPlayerString()
    {
        return String.format("%d:%d:%d:0:%d:%d:0", userId, x, y, status, rot);
    }

    public void sendRoomExcludingMe(PanfuPacket PP)
    {
        List<User> users = gameServer.getSessionManager().getUsersInRoom(this.roomId);
        for (User u : users) {
            if(u.isInHome() == inHome && u.userId != userId) {
                u.sendPacket(PP);
            }
        }
    }

    public void sendRoom(PanfuPacket PP)
    {
        List<User> users = gameServer.getSessionManager().getUsersInRoom(this.roomId);
        for(User u : users) {
            u.sendPacket(PP);
        }
    }

    public void sendForReceiver(PanfuPacket PP, String receiver) {
        if(receiver.contains(",")) {
            String[] split = receiver.split(",");
            if(split[0].equals(String.valueOf(PlayerToPlayerCommands.P2P_RECEIVER_ALL))) {
                List<User> users = getGameServer().getSessionManager().getUsers();
                for(User user : users) {
                    if(user.getUserId() == this.getUserId() && user.getUserId() != Integer.valueOf(split[1]))
                        continue;
                    user.sendPacket(PP);
                }
                return;
            }
            if(split[0].equals(String.valueOf(PlayerToPlayerCommands.P2P_RECEIVER_ROOM))) {
                List<User> users = getGameServer().getSessionManager().getUsersInRoom(this.getRoomId());
                for(User user : users) {
                    if(user.getUserId() == this.getUserId() && user.getUserId() != Integer.valueOf(split[1]))
                        continue;
                    user.sendPacket(PP);
                }
                return;
            }
            return;
        }

        if(receiver.equals(String.valueOf(PlayerToPlayerCommands.P2P_RECEIVER_ALL))) {
            List<User> users = getGameServer().getSessionManager().getUsers();
            for(User user : users) {
                if(user.getUserId() == this.getUserId())
                    continue;
                user.sendPacket(PP);
            }
            return;
        }

        if(receiver.equals(String.valueOf(PlayerToPlayerCommands.P2P_RECEIVER_ROOM))) {
            List<User> users = getGameServer().getSessionManager().getUsersInRoom(this.getRoomId());
            for(User user : users) {
                if(user.getUserId() == this.getUserId())
                    continue;
                user.sendPacket(PP);
            }
            return;
        }

        int receiverAsInt = Integer.valueOf(receiver);
        // User
        if(receiverAsInt > 0) {
            User user = getGameServer().getSessionManager().getUserById(receiverAsInt);
            if(user != null) {
                user.sendPacket(PP);
            }
        }
    }

    public int getInteractingWith()
    {
        return interactingWith;
    }

    public void nullInteractions()
    {
        this.interactingWith = -1;
    }

    public void setInteractingWith(int interactingWith)
    {
        this.interactingWith = interactingWith;
    }

    public int getRoomId()
    {
        return this.roomId;
    }

    public void setRoomId(int roomId)
    {
        this.roomId = roomId;
    }

    public int getCurrentGame()
    {
        return this.gameId;
    }

    public void joinGame(int gameId)
    {
        this.gameId = gameId;
    }

    public void quitGame()
    {
        this.gameId = -1;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getStatus()
    {
        return this.status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getRot()
    {
        return this.rot;
    }

    public void setRot(int rot)
    {
        this.rot = rot;
    }

    public long getTimeUntilWalkComplete()
    {
        return this.timeUntilWalkComplete;
    }

    public void setTimeUntilWalkComplete(long timeUntilWalkComplete)
    {
        this.timeUntilWalkComplete = timeUntilWalkComplete;
    }

    public String getLastChatMessage()
    {
        return lastChatMessage;
    }

    public void setLastChatMessage(String lastChatMessage)
    {
        this.lastChatMessage = lastChatMessage;
    }

    public String getLastActionPerformed()
    {
        return lastActionPerformed;
    }

    public void setLastActionPerformed(String lastActionPerformed)
    {
        this.lastActionPerformed = lastActionPerformed;
    }

    public long getLastChatMessageTime()
    {
        return lastChatMessageTime;
    }

    public void setLastChatMessageTime(long lastChatMessageTime)
    {
        this.lastChatMessageTime = lastChatMessageTime;
    }

    public long getLastActionPerformedTime()
    {
        return lastActionPerformedTime;
    }

    public void setLastActionPerformedTime(long lastActionPerformedTime)
    {
        this.lastActionPerformedTime = lastActionPerformedTime;
    }

    public int getSpamWarningsGiven()
    {
        return spamWarning;
    }

    public void giveActionSpamWarning()
    {
        if(this.spamWarning == Integer.valueOf(GameServer.getProperties().getProperty("chat.antispam.warnings"))) {
            this.disconnect("KICKED: Anti-Spam");
            return;
        }
        this.spamWarning++;
        String alert = GameServer.getProperties().getProperty("actions.antispam.message");
        alert = alert.replaceAll("&&warns&&", String.valueOf(this.spamWarning));
        alert = alert.replaceAll("&&maxwarns&&", GameServer.getProperties().getProperty("chat.antispam.warnings"));
        this.sendAlert(alert);
    }

    public void giveChatSpamWarning()
    {
        if(this.spamWarning == Integer.valueOf(GameServer.getProperties().getProperty("chat.antispam.warnings"))) {
            this.disconnect("KICKED: Anti-Spam");
            return;
        }
        this.spamWarning++;
        String alert = GameServer.getProperties().getProperty("chat.antispam.message");
        alert = alert.replaceAll("&&warns&&", String.valueOf(this.spamWarning));
        alert = alert.replaceAll("&&maxwarns&&", GameServer.getProperties().getProperty("chat.antispam.warnings"));
        this.sendAlert(alert);
    }
}
