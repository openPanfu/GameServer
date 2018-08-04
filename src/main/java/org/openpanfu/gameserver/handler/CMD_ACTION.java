package org.openpanfu.gameserver.handler;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CMD_ACTION implements IHandler {
    final List<String> throwables = Collections.unmodifiableList(new ArrayList<String>() {{
        add("waterbomb");
        add("slimebomb");
        add("slimebombSprite");
        add("sendFlyingCup");
        add("flyingCup");
        add("sendFlyingBottle");
        add("flyingBottle");
        add("sendPancake");
        add("pancake");
        add("flyingPillow");
        add("sendFlyingBottle2");
        add("flyingBottle2");
        add("sendCake");
        add("cake");
        add("blueSlimebombSprite");
        add("pinkSlimebombSprite");
        add("icecubeSpell");
        add("masterOfIce");
        add("hole");
        add("teleportation");
    }});
    @Override
    public void handlePacket(PanfuPacket packet, User sender) {
        if(Integer.valueOf(GameServer.getProperties().getProperty("actions.antispam.enabled")) != 0) {
            int seconds = Integer.parseInt(GameServer.getProperties().getProperty("actions.antispam.secondsbetweenactions"));
            if ((System.currentTimeMillis() - sender.getLastActionPerformedTime()) < (seconds * 1000)) {
                sender.giveActionSpamWarning();
                return;
            }
        }
        String action = packet.readString();
        if(!action.equals("")) {
            PanfuPacket actionBroadcast = new PanfuPacket(Packets.RES_DO_ACTION);
            actionBroadcast.writeInt(sender.getUserId());

            // Is throwable
            if(action.equals("throw")) {
                if(throwables.contains(sender.getLastActionPerformed())) {
                    int throwX = packet.readInt();
                    int throwY = packet.readInt();
                    String toThrow = packet.readString();
                    int victim = packet.readInt();
                    if (throwables.contains(toThrow)) {
                        actionBroadcast.writeString("throw");
                        actionBroadcast.writeInt(throwX);
                        actionBroadcast.writeInt(throwY);
                        actionBroadcast.writeString(toThrow);
                        if(victim == -1) {
                            actionBroadcast.writeString("false");
                        } else {
                            actionBroadcast.writeInt(victim);
                            actionBroadcast.writeString("false");
                        }
                        sender.setLastActionPerformed(action);
                        sender.setLastActionPerformedTime(System.currentTimeMillis());
                        sender.sendRoom(actionBroadcast);
                    } else {
                        sender.sendAlert(String.format("CMD_ACTION: Unknown throwable: %s", toThrow));
                    }
                } else {
                    Logger.warning(String.format("Prevented user %s (%d) from throwing an item because his last action wasn't a throw action.", sender.getUsername(), sender.getUserId()));
                }
                return;
            }

            // Slide
            if(action.equals("doSlideAnimation") && sender.getRoomId() == 3) {
                final int poolX = 134;
                final int poolX2 = 524;
                final int poolY = 206;
                final int poolY2 = 295;

                int endPosX = packet.readInt();
                int endPosY = packet.readInt();
                packet.readString(); // Not filled by the client
                int unknown = packet.readInt();
                actionBroadcast.writeString("doSlideAnimation");
                if(Integer.valueOf(GameServer.getProperties().getProperty("rooms.pool.jumpintotheshowers")) != 0) {
                    actionBroadcast.writeInt(540);
                    actionBroadcast.writeInt(390);
                } else {
                    actionBroadcast.writeInt(ThreadLocalRandom.current().nextInt(poolX, poolX2 + 1));
                    actionBroadcast.writeInt(ThreadLocalRandom.current().nextInt(poolY, poolY2 + 1));
                }
                actionBroadcast.writeString("");
                actionBroadcast.writeInt(unknown);
                actionBroadcast.writeString("false");
                sender.setLastActionPerformedTime(System.currentTimeMillis());
                sender.sendRoom(actionBroadcast);
                return;
            }
            if(action.equals("doDivingAnimation") && sender.getRoomId() == 3) {
                int endPosX = packet.readInt();
                int endPosY = packet.readInt();
                int plankId = packet.readInt();
                actionBroadcast.writeString("doDivingAnimation");
                if(Integer.valueOf(GameServer.getProperties().getProperty("rooms.pool.jumpintotheshowers")) != 0) {
                    actionBroadcast.writeInt(540);
                    actionBroadcast.writeInt(390);
                } else {
                    actionBroadcast.writeInt(endPosX);
                    actionBroadcast.writeInt(endPosY);
                }
                actionBroadcast.writeInt(plankId);
                actionBroadcast.writeString("false");
                sender.setLastActionPerformedTime(System.currentTimeMillis());
                sender.sendRoom(actionBroadcast);
                return;
            }

            // Lake slide
            if(action.equals("doSlideLakeAnimation") && sender.getRoomId() == 39) {
                int endPosX = packet.readInt();
                int endPosY = packet.readInt();
                int height = packet.readInt();
                String hoop = packet.readString();
                actionBroadcast.writeString("doSlideLakeAnimation");
                actionBroadcast.writeInt(endPosX);
                actionBroadcast.writeInt(endPosY);
                actionBroadcast.writeInt(height);
                actionBroadcast.writeString(hoop);
                sender.sendRoom(actionBroadcast);
                return;
            }
            actionBroadcast.writeString(action);
            sender.setLastActionPerformed(action);

            // Optimization, we don't have to send throwables to everyone.
            // This prevents other people from knowing that the player is about to throw something.
            if(throwables.contains(action)) {
                sender.sendPacket(actionBroadcast);
                return;
            }
            sender.setLastActionPerformedTime(System.currentTimeMillis());
            sender.sendRoom(actionBroadcast);
        }
    }
}
