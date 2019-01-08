/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @category GameServer
 * @package org.openpanfu.gameserver
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openpanfu.gameserver.commands.Commands;
import org.openpanfu.gameserver.commands.Help;
import org.openpanfu.gameserver.database.Database;
import org.openpanfu.gameserver.database.GameServerData;
import org.openpanfu.gameserver.database.dao.GameServerDAO;
import org.openpanfu.gameserver.games.multiplayer.FourBoom;
import org.openpanfu.gameserver.games.multiplayer.RockPaperScissors;
import org.openpanfu.gameserver.handler.Handler;
import org.openpanfu.gameserver.plugin.PluginManager;
import org.openpanfu.gameserver.sessions.SessionManager;
import org.openpanfu.gameserver.util.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class GameServer {
	private static List<GameServer> gameServers = new ArrayList<GameServer>();
	private static Properties properties = new Properties();

	private int id;
	private String name;
	private int port;
	private boolean chatEnabled = false;
	private SessionManager sessionManager;
	private String secretKey = "";
	private Channel channel;

	// Multiplayer games

	private FourBoom fourBoom;
	private RockPaperScissors rockPaperScissors;

	public GameServer(int id, String name, int port) {
		this.id = id;
		this.name = name;
		this.port = port;
		this.sessionManager = new SessionManager();
		this.chatEnabled = (Integer.valueOf(GameServer.getProperties().getProperty("chat.forcesafechat")) == 0);
		this.fourBoom = new FourBoom();
		this.rockPaperScissors = new RockPaperScissors();
		Logger.info("[" + this.id + "] Starting gameserver on port: " + port);

		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
			b.childHandler(new GameServerInitializer(this));
			this.channel = b.bind(this.port).sync().channel();
		} catch (Exception e) {
			Logger.error("Failed to create channel! (Make sure the port isn't already in use!)");
			e.printStackTrace();
		}
	}

	public int getId() {
		return this.id;
	}

	public int getPort() {
		return this.port;
	}

	public String getName() {
		return this.name;
	}

	public static void main(String[] arguments) throws SQLException, InterruptedException {
		try {
			properties.load(new FileInputStream("GameServer.properties"));
		} catch (Exception e) {
			Logger.error("There was an issue loading the properties file.");
			e.printStackTrace();
			return;
		}
		Handler.initialize();
		if (Database.connect()) {
			for (GameServerData data : GameServerDAO.getGameServers()) {
				GameServer gameserver = new GameServer(data.id, data.name, data.port);
				gameServers.add(gameserver);
				gameserver.updateUserCount();
				GameServerDAO.setupKeys(gameserver);
				Logger.info("Gameserver " + gameserver.name + " is ready for connections.");
			}
		} else {
			Logger.error("HALT! The database connection could not be initialized!");
		}
		Commands.registerCommand("help", new Help());
		PluginManager.loadPlugins("plugins");
	}

	public static Properties getProperties() {
		return properties;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void updateUserCount() {
		GameServerDAO.updateUserCount(this.id, this.sessionManager.getUserCount());
	}

	public static User getUserById(int userId) {
		for (GameServer gs : gameServers) {
			User u = gs.getSessionManager().getUserById(userId);
			if (u != null) {
				return u;
			}
		}
		return null;
	}

	public boolean isChatEnabled() {
		return chatEnabled;
	}

	public FourBoom getFourBoom() {
		return fourBoom;
	}

	public RockPaperScissors getRockPaperScissors() {
		return rockPaperScissors;
	}

	public String getKey() {
		return secretKey;
	}

	public void setKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
