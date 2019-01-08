package org.openpanfu.gameserver.games.multiplayer;

import java.util.LinkedList;

import org.openpanfu.gameserver.User;

public class GameQueue {
	private LinkedList<User> list = new LinkedList<>();

	public void put(User user) {
		list.addLast(user);
	}

	// Allows us to check whether there's still someone in the queue
	public User peek() {
		return list.getFirst();
	}

	public boolean hasUserWaiting() {
		try {
			return (list.getFirst() != null);
		} catch (Exception e) {
			return false;
		}
	}

	// Removes and returns the user
	public User get() {
		// Could happen, who knows.
		if (list.isEmpty()) {
			return null;
		}
		return list.removeFirst();
	}

	public void removeUserIfIn(User user) {
		if (list.contains(user))
			list.remove(user);
	}
}
