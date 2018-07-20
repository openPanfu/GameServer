/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.constants;

public class Packets
{
    // C->S
    public static final int CMD_LOGIN = 0;
    public static final int CMD_LOGOUT = 2;
    public static final int CMD_JOIN_GAME = 11;
    public static final int CMD_LEAVE_ROOM = 13;
    public static final int CMD_ENTER_MULTIGAME = 14;
    public static final int CMD_MULTIGAME = 15;
    public static final int CMD_QUIT_GAME = 16;
    public static final int CMD_ROTATE = 19;
    public static final int CMD_MOVE = 20;
    public static final int CMD_FORCE_COORD = 21;
    public static final int CMD_GOTO_PLAYER = 23;
    public static final int CMD_JOIN_ROOM = 25;
    public static final int CMD_JOIN_HOME = 26;
    public static final int CMD_GET_OPEN_HOUSES = 27;
    public static final int CMD_GET_ALL_HOUSES = 29;
    public static final int CMD_UPDATE_PLAYERS = 35;
    public static final int CMD_UPDATE_PLAYER = 37;
    public static final int CMD_CHAT = 40;
    public static final int CMD_EMOTE = 41;
    public static final int CMD_SEND_ECARD = 42;
    public static final int CMD_SAFE_CHAT = 43;
    public static final int CMD_PROFILE_TEXT = 44;
    public static final int CMD_ACTION = 50;
    public static final int CMD_ADDBUDDY = 60;
    public static final int CMD_GET_ROOM_ATTENDEES = 70;
    public static final int CMD_PLAYER_TO_PLAYER = 113;
    public static final int CMD_REPORT_PLAYER = 114;
    public static final int CMD_LOCK_PLAYER = 115;
    public static final int CMD_SMS_ABO_RECIVE = 120;
    public static final int CMD_INITIATE_RACE = 200;
    public static final int CMD_RESPONSE_RACE_REQUEST = 201;
    public static final int CMD_POKOPET_PRIVATEINVITE_CANCELLED = 202;
    public static final int CMD_SET_PLAYER_STATUS = 210;
    public static final int CMD_GET_PLAYER_LOCATION = 211;
    public static final int CMD_GET_PLAYER_IDS_BY_CLOTHES = 212;
    public static final int CMD_RESPONSE_PUBLIC_RACE_MATCHFOUND = 250;
    public static final int CMD_SET_SINGLE_PLAYER_MODE = 300;
    public static final int CMD_PING = 1050;
    public static final int CMD_SHOW_GS_MSG = 260;
    public static final int CMD_RECIEVE_NEW_MSGBOARD_MSG = 270;
    public static final int CMD_GET_SALT = 301;
    public static final int CMD_SB_LOGIN = 800;
    public static final int CMD_OPTIN = 801;

    // S->C
    public static final int RES_ON_LOGIN = 0;
    public static final int RES_DISCONNECT = 2;
    public static final int RES_LOGOUT_SUCCESS = 3;
    public static final int RES_ON_ROOM_JOINED = 10;
    public static final int RES_ON_SINGLEGAME_JOINED = 11;
    public static final int RES_ON_SUBROOM_ENTER = 12;
    public static final int RES_ON_MULTIGAME_MESSAGE = 15;
    public static final int RES_MOVE_AVATAR = 20;
    public static final int RES_GOTO_PLAYER = 23;
    public static final int RES_ON_HOME_JOINED = 26;
    public static final int RES_RECEIVE_OPENHOUSES = 27;
    public static final int RES_RECEIVE_ALLHOUSES = 29;
    public static final int RES_SET_AVATAR = 30;
    public static final int RES_UNSET_AVATAR = 31;
    public static final int RES_USER_DISCONNECTED = 32;
    public static final int RES_UPDATE_PLAYERINFO = 35;
    public static final int RES_UPDATE_USERINFO = 37;
    public static final int RES_CHAT_MSG = 40;
    public static final int RES_EMOTE_MSG = 41;
    public static final int RES_RECEIVE_ECARD = 42;
    public static final int RES_TOGGLE_SAFE_CHAT = 45;
    public static final int RES_ON_HOME_LOCKED = 46;
    public static final int RES_DO_ACTION = 50;
    public static final int RES_ADD_BUDDY = 60;
    public static final int RES_UPDATE_BUDDY_STATUS = 61;
    public static final int RES_GET_ROOM_ATTENDEES = 70;
    public static final int RES_RECEIVE_ALERT = 80;
    public static final int RES_RECEIVE_MODERATORMSG = 81;
    public static final int RES_RECEIVE_PROFILE_BADWORD = 92;
    public static final int RES_RECEIVE_PROFILE_FIELD_OK = 93;
    public static final int RES_MICROPAY_SUCCES = 110;
    public static final int RES_MICROPAY_FAILED = 111;
    public static final int RES_MICROPAY_ROLLBACK = 112;
    public static final int RES_PLAYER_TO_PLAYER = 113;
    public static final int RES_ON_SMS_ABO_RECIEVED = 120;
    public static final int RES_HOTBOMB_OWNER_CHANGED = 122;
    public static final int RES_HOTBOMB_EXPLODE = 123;
    public static final int RES_HOTBOMB_STATUS_RECEIVED = 124;
    public static final int RES_HOTBOMB_START_GAME = 125;
    public static final int RES_SOCCERGAME_STATUS_RECEIVED = 130;
    public static final int RES_SOCCERGAME_START_GAME = 131;
    public static final int RES_SOCCERGAME_BALL_SHOOT = 132;
    public static final int RES_SOCCERGAME_GOAL = 133;
    public static final int RES_SOCCERGAME_OVER = 134;
    public static final int RES_SOCCERGAME_TEAM_INFO = 135;
    public static final int RES_SOCCERGAME_JOIN_TEAM = 136;
    public static final int RES_PETRACE_PRIVATE_REQUEST = 200;
    public static final int RES_PETRACE_PRIVATE_RESPONSE = 201;
    public static final int RES_PETRACE_PRIVATE_CANCELLED = 202;
    public static final int RES_PLAYER_STATUS_REQUEST = 210;
    public static final int RES_PLAYER_LOCATION_REQUEST = 211;
    public static final int RES_PLAYER_IDS_BY_CLOTHES_REQUEST = 212;
    public static final int RES_PETRACE_PUBLIC_MATCHFOUND = 250;
    public static final int RES_RECEIVE_SHARED_ITEMS = 140;
    public static final int RES_ON_SHOW_GS_MSG = 260;
    public static final int RES_ON_MSGBOARD_NEW_MSG_RECIEVED = 270;
    public static final int RES_ON_CONTAINER_UPDATE = 280;
    public static final int RES_ON_GET_SALT = 301;
    public static final int RES_ON_GET_ROBOT_ATTACK = 302;
    public static final int RES_ON_GET_SP_GP = 310;

    public static String headerToName(int header)
    {
        if(header == 0) {
            return "CMD_LOGIN";
        }
        if(header == 2) {
            return "CMD_LOGOUT";
        }
        if(header == 11) {
            return "CMD_JOIN_GAME";
        }
        if(header == 13) {
            return "CMD_LEAVE_ROOM";
        }
        if(header == 14) {
            return "CMD_ENTER_MULTIGAME";
        }
        if(header == 15) {
            return "CMD_MULTIGAME";
        }
        if(header == 16) {
            return "CMD_QUIT_GAME";
        }
        if(header == 19) {
            return "CMD_ROTATE";
        }
        if(header == 20) {
            return "CMD_MOVE";
        }
        if(header == 21) {
            return "CMD_FORCE_COORD";
        }
        if(header == 23) {
            return "CMD_GOTO_PLAYER";
        }
        if(header == 25) {
            return "CMD_JOIN_ROOM";
        }
        if(header == 26) {
            return "CMD_JOIN_HOME";
        }
        if(header == 27) {
            return "CMD_GET_OPEN_HOUSES";
        }
        if(header == 29) {
            return "CMD_GET_ALL_HOUSES";
        }
        if(header == 35) {
            return "CMD_UPDATE_PLAYERS";
        }
        if(header == 37) {
            return "CMD_UPDATE_PLAYER";
        }
        if(header == 40) {
            return "CMD_CHAT";
        }
        if(header == 41) {
            return "CMD_EMOTE";
        }
        if(header == 42) {
            return "CMD_SEND_ECARD";
        }
        if(header == 43) {
            return "CMD_SAFE_CHAT";
        }
        if(header == 44) {
            return "CMD_PROFILE_TEXT";
        }
        if(header == 50) {
            return "CMD_ACTION";
        }
        if(header == 60) {
            return "CMD_ADDBUDDY";
        }
        if(header == 70) {
            return "CMD_GET_ROOM_ATTENDEES";
        }
        if(header == 113) {
            return "CMD_PLAYER_TO_PLAYER";
        }
        if(header == 114) {
            return "CMD_REPORT_PLAYER";
        }
        if(header == 115) {
            return "CMD_LOCK_PLAYER";
        }
        if(header == 120) {
            return "CMD_SMS_ABO_RECIVE";
        }
        if(header == 200) {
            return "CMD_INITIATE_RACE";
        }
        if(header == 201) {
            return "CMD_RESPONSE_RACE_REQUEST";
        }
        if(header == 202) {
            return "CMD_POKOPET_PRIVATEINVITE_CANCELLED";
        }
        if(header == 210) {
            return "CMD_SET_PLAYER_STATUS";
        }
        if(header == 211) {
            return "CMD_GET_PLAYER_LOCATION";
        }
        if(header == 212) {
            return "CMD_GET_PLAYER_IDS_BY_CLOTHES";
        }
        if(header == 250) {
            return "CMD_RESPONSE_PUBLIC_RACE_MATCHFOUND";
        }
        if(header == 300) {
            return "CMD_SET_SINGLE_PLAYER_MODE";
        }
        if(header == 1050) {
            return "CMD_PING";
        }
        if(header == 260) {
            return "CMD_SHOW_GS_MSG";
        }
        if(header == 270) {
            return "CMD_RECIEVE_NEW_MSGBOARD_MSG";
        }
        if(header == 301) {
            return "CMD_GET_SALT";
        }
        return "(Unknown)";
    }
}
