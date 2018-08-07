/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.sessions;

import org.openpanfu.gameserver.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class SessionManager {
    private ConcurrentMap<Integer, User> sessions;

    public SessionManager()
    {
        this.sessions = new ConcurrentHashMap<Integer, User>();
    }

    public void addUser(User user)
    {
        if(sessions.get(user.getUserId()) != null) {
            user.disconnect("KICK_NEW_LOGIN");
        }

        sessions.putIfAbsent(user.getUserId(), user);
    }

    public User getUserById(int id)
    {
        return this.sessions.values().stream().filter(s -> s.getUserId() == id).findFirst().get();
    }

    public User getUserByUsername(String username)
    {
        return this.sessions.values().stream().filter(s -> s.getUsername().equals(username)).findFirst().get();
    }

    public void removeUserById(int id)
    {
        sessions.remove(id);
    }

    public int getUserCount()
    {
        return sessions.size();
    }

    public List<User> getUsers()
    {
        return this.sessions.values().stream().collect(Collectors.toList());
    }

    public List<User> getUsersInRoom(int roomid, boolean inHome, int subroom)
    {
        return this.sessions.values().stream().filter(s -> s.getRoomId() == roomid && s.isInHome() == inHome && s.getSubRoom() == subroom).collect(Collectors.toList());
    }

    public ConcurrentMap<Integer, User> getSessions()
    {
        return sessions;
    }
}
