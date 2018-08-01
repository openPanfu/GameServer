/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.openpanfu.gameserver.GameServer;
import org.openpanfu.gameserver.util.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static HikariDataSource dataSource;

    public static boolean connect()
    {
        HikariConfig config = new HikariConfig();
        try {
            Properties properties = GameServer.getProperties();
            config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", properties.getProperty("database.host"), properties.getProperty("database.port"), properties.getProperty("database.database")));
            config.setUsername(properties.getProperty("database.user"));
            config.setPassword(properties.getProperty("database.pass"));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("tcpKeepAlive", true);
            config.addDataSourceProperty("maxReconnects", 6);
            config.addDataSourceProperty("useSSL", "false");
        } catch (Exception e) {
            Logger.error("There was an error initializing the database connection.");
            e.printStackTrace();
            return false;
        }
        dataSource = new HikariDataSource(config);
        return true;
    }
    public static Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }
}
