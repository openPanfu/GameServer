package org.openpanfu.gameserver.plugin;

import org.openpanfu.gameserver.User;

public interface IPlugin {

	/**
	 * This method will be ran when the server starts.
	 * 
	 * @return Boolean Returning true will allow the plugin to use the other
	 *         methods.
	 */
	boolean onStartup();

	/**
	 * This method will be called after a user joins a room.
	 * 
	 * @param user   The user that just joined the room.
	 * @param roomId The roomId of the room they just joined. If the user just
	 *               entered a home, this will be the user id of the person who's
	 *               home they just joined.
	 * @param isHome Whether that room is a user home.
	 */
	void onUserJoinRoom(User user, int roomId, boolean isHome);

	/**
	 * Called when a user *tries* to enter a room, this allows you to override
	 * normal room joining behavior.
	 * 
	 * @param user   The user that just joined the room.
	 * @param roomId The roomId of the room they just joined. If the user just
	 *               entered a home, this will be the user id of the person who's
	 *               home they just joined.
	 * @param isHome Whether that room is a user home.
	 * @return boolean If true, the normal handler will be ran afterwards.
	 */
	boolean handleUserJoinRoom(User user, int roomId, boolean isHome);

	/**
	 * Called when a user just logged in from CMD_LOGIN.
	 * 
	 * @param user The user that just connected.
	 */
	void onUserConnect(User user);

	/**
	 * Called when a user is trying to login.
	 * 
	 * @param user The user that just connected.
	 * @return boolean If false, the user's session will be denied and they will be
	 *         disconnected.
	 */
	boolean handleUserConnect(User user);

	/**
	 *
	 * @param user
	 * @param message
	 */
	void onUserChat(User user, String message);

	boolean handleUserChat(User user, String message);
}
