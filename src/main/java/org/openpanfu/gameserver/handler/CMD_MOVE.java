/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;

public class CMD_MOVE implements IHandler {
    public static final int MOVEMENT_TYPE_WALK = 0;
    public static final int MOVEMENT_TYPE_SLIDE = 1;
    public static final int MOVEMENT_TYPE_SWEEP = 2;
    public static final int MOVEMENT_TYPE_JUMP = 3;
    public static final int MOVEMENT_TYPE_TELEPORT = 4;
    public static final int MOVEMENT_TYPE_RUNNING = 5;
    public static final int MOVEMENT_TYPE_ROLLERBLADES = 6;
    public static final int MOVEMENT_TYPE_SLOW = 7;
    public static final int MOVEMENT_TYPE_SNOWBOARD = 8;

    private static int calcuTime(int x1, int y1, int x2, int y2) {
        double distance = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        if (distance <= 1) {
            return (1);
        }
        return (int)(distance / 0.1);
    }

    @Override
    public void handlePacket(PanfuPacket packet, User sender)
    {
        int toX = packet.readInt();
        int toY = packet.readInt();
        int type = packet.readInt();
        if(type > 8) {
            sender.disconnect("Error: CMD_MOVE, unknown movement type.");
        }

        sender.nullInteractions();

        switch(type) {
            case MOVEMENT_TYPE_WALK:
                int msLeft = 0;
                if(sender.getTimeUntilWalkComplete() - System.currentTimeMillis() > 0) {
                    msLeft = Math.toIntExact((sender.getTimeUntilWalkComplete() - System.currentTimeMillis()) / 2);
                } else {
                    sender.setTimeUntilWalkComplete(System.currentTimeMillis() + calcuTime(sender.getX(), sender.getY(), toX, toY) + msLeft);
                }
                sender.move(toX, toY, calcuTime(sender.getX(), sender.getY(), toX, toY) + msLeft, type);
                break;
            default:
                sender.move(toX, toY, 1000, type);
                break;
        }
    }
}
