/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.constants;

public class PlayerToPlayerCommands {
    public static final int P2P_RECEIVER_ALL = -1;
    public static final int P2P_RECEIVER_ROOM = -2;
    public static final int CMD_CREATE_AVATAR = 10;
    public static final int CMD_UPDATE_AVATAR = 11;
    public static final int CMD_USE_SHARED_ITEM = 12;
    public static final int CMD_SET_TRANSFORMATION = 13;
    public static final int CMD_SHOW_STATUS = 14;
    public static final int CMD_HIDE_STATUS = 15;
    public static final int CMD_ELEMENT_CLICKED = 16;
    public static final int CMD_REMOVE_AVATAR = 17;
    public static final int CMD_KICK_AVATAR = 18;
    public static final int CMD_SEND_SOUND = 19;
    public static final int CMD_SEND_REPORT_FEEDBACK = 20;
    public static final int CMD_SEND_POPUP = 21;
    public static final int CMD_GREYOUT_MC = 22;
    public static final int CMD_SPAWN_SLIME = 23;
    public static final int CMD_SEND_EVIL_CHAT = 24;
    public static final int CMD_SEND_NO_EVIL = 25;
    public static final int CMD_SEND_TT = 26;
    public static final int CMD_SEND_UNTT = 27;
    public static final int ON_CREATE_AVATAR = CMD_CREATE_AVATAR;
    public static final int ON_UPDATE_AVATAR = CMD_UPDATE_AVATAR;
    public static final int ON_REMOVE_AVATAR = CMD_REMOVE_AVATAR;
    public static final int ON_USE_SHARED_ITEM = CMD_USE_SHARED_ITEM;
    public static final int ON_SET_TRANSFORMATION = CMD_SET_TRANSFORMATION;
    public static final int ON_SHOW_STATUS = CMD_SHOW_STATUS;
    public static final int ON_HIDE_STATUS = CMD_HIDE_STATUS;
    public static final int ON_ELEMENT_CLICKED = CMD_ELEMENT_CLICKED;
    public static final int ON_KICK_AVATAR = CMD_KICK_AVATAR;
    public static final int ON_SEND_SOUND = CMD_SEND_SOUND;
    public static final int ON_SEND_REPORT_FEEDBACK = CMD_SEND_REPORT_FEEDBACK;
    public static final int ON_SEND_POPUP_FEEDBACK = CMD_SEND_POPUP;
    public static final int ON_GREYOUT_FEEDBACK = CMD_GREYOUT_MC;
    public static final int ON_SPAWN_SLIME = CMD_SPAWN_SLIME;
    public static final int ON_SEND_EVIL_CHAT = CMD_SEND_EVIL_CHAT;
    public static final int ON_SEND_NO_EVIL = CMD_SEND_NO_EVIL;
    public static final int ON_SEND_TT = CMD_SEND_TT;
    public static final int ON_SEND_UNTT = CMD_SEND_UNTT;
}
