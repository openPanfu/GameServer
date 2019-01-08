package org.openpanfu.gameserver.games.multiplayer;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.util.Logger;

public class FourBoom {
	private GameQueue queue = new GameQueue();

	// Called on CMD_ENTER_MULTIGAME
	public void onEnter(User user) {
		// First we check if someone else is already in the queue.
		if (queue.hasUserWaiting()) {
			// We join this user's session.
			User victim = queue.get();
			victim.setMultiGamePartner(user);
			user.setMultiGamePartner(victim);
			Logger.info(String.format("[4boom] Player %s was matched with %s from the 4boom queue!", user.getUsername(),
					victim.getUsername()));
			// Packets to start the game
			PanfuPacket player1StartPacket = new PanfuPacket(Packets.RES_ON_MULTIGAME_MESSAGE);
			player1StartPacket.writeInt(25); // Game id
			player1StartPacket.writeInt(0); // Sender (0 = server, I guess)
			player1StartPacket.writeString("setPlayer");
			player1StartPacket.writeInt(1);
			PanfuPacket player2StartPacket = new PanfuPacket(Packets.RES_ON_MULTIGAME_MESSAGE);
			player2StartPacket.writeInt(25); // Game id
			player2StartPacket.writeInt(0); // Sender (0 = server, I guess)
			player2StartPacket.writeString("setPlayer");
			player2StartPacket.writeInt(2);
			user.sendPacket(player1StartPacket);
			victim.sendPacket(player2StartPacket);
		} else {
			Logger.info(String.format("[4boom] Player %s joined the 4boom queue!", user.getUsername()));
			queue.put(user);
		}
	}

	// If the user leaves or disconnects
	public void onExit(User user) {
		Logger.info(String.format("[4boom] Player %s left the 4boom queue!", user.getUsername()));
		queue.removeUserIfIn(user);
		if (user.getMultiGamePartner() != null) {
			// We need to inform the partner that the user has left.
			PanfuPacket quitPacket = new PanfuPacket(Packets.RES_ON_MULTIGAME_MESSAGE);
			quitPacket.writeInt(25); // Game id
			quitPacket.writeInt(user.getUserId()); // Sender (0 = server, I guess)
			quitPacket.writeString("unsetPlayer");
			quitPacket.writeInt(user.getUserId()); // user id
			user.getMultiGamePartner().sendPacket(quitPacket);
			user.getMultiGamePartner().setMultiGamePartner(null);
			user.setMultiGamePartner(null);
		}
	}

	// Called on CMD_MULTIGAME, used to inform the other user of a multiplayer
	// interaction
	public void onMessage(User user, PanfuPacket panfuPacket) {
		int gameId = panfuPacket.readInt();
		int senderId = panfuPacket.readInt();
		String action = panfuPacket.readString();
		String parameter = panfuPacket.readString();
		User partner = user.getMultiGamePartner();
		if (partner != null) {
			PanfuPacket information = new PanfuPacket(Packets.RES_ON_MULTIGAME_MESSAGE);
			information.writeInt(25); // Game id
			information.writeInt(user.getUserId()); // Sender (0 = server, I guess)
			information.writeInt(senderId);
			information.writeString(action);
			information.writeString(parameter);
			partner.sendPacket(information);
		}
	}
}
