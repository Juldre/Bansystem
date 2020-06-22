package de.Juldre.Bansystem.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.Juldre.Bansystem.main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.PendingConnection;

public class UserManager {
	public UserManager() {
		try {
			Statement statement = main.mySQL.getStatement();
			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS Userdata (Name VARCHAR(20) NOT NULL,UUID VARCHAR(40) NOT NULL)");
			statement.close();
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
	}
	
	public void addPlayer(PendingConnection c) {
		if (!isCreated(c)) {
			try {
				Statement statement = main.mySQL.getStatement();
				statement.executeUpdate("INSERT INTO `Userdata`(`Name`, `UUID`) VALUES ('" + c.getName() + "','"
						+ c.getUniqueId().toString() + "')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isCreated(String uuid) {
		try {
			Statement statement = main.mySQL.getStatement();
			ResultSet result = statement.executeQuery("SELECT `UUID` FROM `Userdata` WHERE `UUID` = '" + uuid + "'");
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
		}
		return false;
	}

	public boolean isCreated(PendingConnection c) {
		try {
			Statement statement = main.mySQL.getStatement();
			ResultSet result = statement
					.executeQuery("SELECT `UUID` FROM `Userdata` WHERE `UUID` = '" + c.getUniqueId().toString() + "'");
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
		}
		return false;
	}

	
	public void updatePlayer(PendingConnection c) {
		if (isCreated(c)) {
			try {
				Statement statement = main.mySQL.getStatement();
				statement.executeUpdate("UPDATE `Userdata` SET `Name`='" + c.getName() + "' WHERE `UUID` = '"
						+ c.getUniqueId().toString() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			addPlayer(c);
		}
	}

	public String getUUIDByName(String name) {
		try {
			Statement statement = main.mySQL.getStatement();
			ResultSet result = statement.executeQuery("SELECT `UUID` FROM `Userdata` WHERE `Name` = '" + name + "'");
			if (result.next() != false) {
				return result.getString("UUID");
			} else {
				return null;
			}
		} catch (SQLException e) {
			return null;
		}
	}
}
