/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.database.Database;
import org.openpanfu.gameserver.database.GameServerData;
import org.openpanfu.gameserver.util.Key;

public class GameServerDAO {
	
	/**
	 * Returns a list of all the GameServers present in the database.
	 * 
	 * @author Altro50 <altro50@msn.com>
	 * @return List<GameServerData>
	 */
	public static List<GameServerData> getGameServers() {
        List<GameServerData> gameservers = new ArrayList<GameServerData>();
		Connection database = null;
		try {
			
			database = Database.getConnection();
	        Statement st = database.createStatement();
	        String query = ("SELECT * FROM gameservers ORDER BY id;");
	        ResultSet resultSet = st.executeQuery(query);
	        
	        while(resultSet.next()) {
	        	GameServerData data = new GameServerData();
                data.id = resultSet.getInt("id");
                data.playerCount = resultSet.getInt("player_count");
                data.name = resultSet.getString("name");
                data.url = resultSet.getString("url");
                data.port = resultSet.getInt("port");
                data.goldpanda = resultSet.getBoolean("goldpanda");
                gameservers.add(data);
	        }
	        
	        return gameservers;
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				database.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Sets up the keys for the GameServer, allowing the Information Server to communicate
	 * with the GameServer securely.
	 * 
	 * @param gameserver
	 */
	public static void setupKeys(GameServer gameserver) {
		Connection database = null;
        try {
			database = Database.getConnection();
            Key keygen = new Key(64, ThreadLocalRandom.current());
            gameserver.setKey(keygen.nextString());
            PreparedStatement preparedStatement = database.prepareStatement("UPDATE gameservers SET secret_key = ? where id = ?");
            preparedStatement.setString(1, gameserver.getKey());
            preparedStatement.setInt(2, gameserver.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			try {
				database.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * Updates the player count
	 * @param serverId
	 * @param playerCount
	 */
	public static void updateUserCount(int serverId, int playerCount) {
        Connection database = null;
        try {
        	database = Database.getConnection();
            PreparedStatement preparedStatement = database.prepareStatement("UPDATE gameservers SET player_count = ? where id = ?");
            preparedStatement.setInt(1, playerCount);
            preparedStatement.setInt(2, serverId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
				database.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
}
