package org.openpanfu.gameserver.games.multiplayer;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.PanfuPacket;
import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.constants.Packets;
import org.openpanfu.gameserver.util.Logger;

public class RockPaperScissors {

	public void onEnter(User sender) {
		// Other player is ready, we can start.
		if (sender.getMultiGamePartner().getCurrentGame() == 41) {
			User victim = sender.getMultiGamePartner();
			Logger.info(String.format("[RPS] Player %s started a RPS match with %s!", sender.getUsername(),
					victim.getUsername()));
			// Packets to start the game
			PanfuPacket player1StartPacket = new PanfuPacket(Packets.RES_ON_MULTIGAME_MESSAGE);
			player1StartPacket.writeInt(41); // Game id
			player1StartPacket.writeInt(0); // Sender (0 = server, I guess)
			player1StartPacket.writeString("setPlayer");
			player1StartPacket.writeInt(1);
			PanfuPacket player2StartPacket = new PanfuPacket(Packets.RES_ON_MULTIGAME_MESSAGE);
			player2StartPacket.writeInt(41); // Game id
			player2StartPacket.writeInt(0); // Sender (0 = server, I guess)
			player2StartPacket.writeString("setPlayer");
			player2StartPacket.writeInt(2);
			sender.sendPacket(player1StartPacket);
			victim.sendPacket(player2StartPacket);
		}
	}

	// If the user leaves or disconnects
	public void onExit(User user) {
		Logger.info(String.format("[RPS] Player %s left RPS!", user.getUsername()));
		if (user.getMultiGamePartner() != null) {
			// We need to inform the partner that the user has left.
			PanfuPacket quitPacket = new PanfuPacket(Packets.RES_ON_MULTIGAME_MESSAGE);
			quitPacket.writeInt(41); // Game id
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
			information.writeInt(41); // Game id
			information.writeInt(user.getUserId()); // Sender (0 = server, I guess)
			information.writeInt(senderId);
			information.writeString(action);
			information.writeString(parameter);
			partner.sendPacket(information);
		}
	}
}
