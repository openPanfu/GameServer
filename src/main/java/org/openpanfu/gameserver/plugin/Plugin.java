package org.openpanfu.gameserver.plugin;

import org.openpanfu.gameserver.User;

public class Plugin implements IPlugin {
    public boolean onStartup() {
        return false;
    }

    public void onUserJoinRoom(User user, int roomId, boolean isHome) {

    }

    public boolean handleUserJoinRoom(User user, int roomId, boolean isHome) {
        return true;
    }

    public void onUserConnect(User user) {

    }

    public boolean handleUserConnect(User user) {
        return true;
    }

    public void onUserChat(User user, String message) {

    }

    public boolean handleUserChat(User user, String message) {
        return true;
    }
}
