package de.Juldre.Bansystem.Utils;

import static java.sql.DriverManager.getConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import de.Juldre.Bansystem.main;
import net.md_5.bungee.BungeeCord;

public class MySQL {
	String host, database, username, password;
	int port;
	private boolean error;

	Connection connection;

	public MySQL(String host, String database, String username, String password, int port) {
		super();
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.port = port;
		try {
			connection = getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			setError(false);
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
			setError(true);
		}

	}

	public Statement getStatement() {
		try {
			if (connection.isClosed()) {
				connection = getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			}
			return connection.createStatement();
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
			return null;
		}
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
