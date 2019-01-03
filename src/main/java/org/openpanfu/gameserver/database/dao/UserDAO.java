package org.openpanfu.gameserver.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openpanfu.gameserver.database.Database;
import org.openpanfu.gameserver.database.UserData;

public class UserDAO {
	public static UserData getById(int id) {
		Connection database = null;
		try {
	        database = Database.getConnection();
	        PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM users WHERE id = ? LIMIT 1");
	        preparedStatement.setInt(1, id);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if(resultSet.next()) {
	        	UserData data = new UserData();
	        	data.id = resultSet.getInt("id");
	        	data.name = resultSet.getString("name");
	        	data.email = resultSet.getString("email");
	        	data.sex = resultSet.getInt("sex");
	        	data.ticket_id = resultSet.getString("ticket_id");
	        	data.goldpanda = resultSet.getInt("goldpanda");
	        	data.sheriff = resultSet.getInt("sheriff");
	        	return data;
	        }
		} catch(SQLException e) {
			System.err.println("[UserDAO] -- Error getting user by id!");
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
	
	public static UserData getByIdAndTicket(int id, int ticket) {
		Connection database = null;
		try {
	        database = Database.getConnection();
	        PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM users WHERE id = ? AND ticket_id = ? LIMIT 1");
	        preparedStatement.setInt(1, id);
	        preparedStatement.setInt(2, ticket);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if(resultSet.next()) {
	        	UserData data = new UserData();
	        	data.id = resultSet.getInt("id");
	        	data.name = resultSet.getString("name");
	        	data.email = resultSet.getString("email");
	        	data.sex = resultSet.getInt("sex");
	        	data.ticket_id = resultSet.getString("ticket_id");
	        	data.goldpanda = resultSet.getInt("goldpanda");
	        	data.sheriff = resultSet.getInt("sheriff");
	        	return data;
	        }
		} catch(SQLException e) {
			System.err.println("[UserDAO] -- Error getting user by id and ticket!");
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
	
	public static void nullTicket(int id) {
        Connection database = null;
        try {
        	database = Database.getConnection();
            PreparedStatement preparedStatement = database.prepareStatement("UPDATE users SET ticket_id = NULL where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            database.close();
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
	
	public static void setCurrentGameServer(int id, int currentGameServer) {
		Connection database = null;
        try {
            database = Database.getConnection();
            PreparedStatement preparedStatement = database.prepareStatement("UPDATE users SET current_gameserver = ? where id = ?");
            preparedStatement.setInt(1, currentGameServer);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            database.close();
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
	
	public static void clearCurrentGameServer(int id) {
		Connection database = null;
        try {
            database = Database.getConnection();
            PreparedStatement preparedStatement = database.prepareStatement("UPDATE users SET current_gameserver = NULL where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            database.close();
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
