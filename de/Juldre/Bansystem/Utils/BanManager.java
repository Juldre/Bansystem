package de.Juldre.Bansystem.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.Juldre.Bansystem.main;
import de.Juldre.Bansystem.Utils.Objects.BanTemplateObject;
import net.md_5.bungee.BungeeCord;

public class BanManager {
	public BanManager() {
		try {
			Statement statement = main.mySQL.getStatement();
			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS BannedPlayers (UUID VARCHAR(40) NOT NULL,Time BIGINT(255) NOT NULL,Reason VARCHAR(100) NOT NULL,Ip VARCHAR(100))");
			statement.close();
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
	}

	public boolean isBanned(String uuid) {
		try {
			Statement statement = main.mySQL.getStatement();
			ResultSet result = statement
					.executeQuery("SELECT `UUID` FROM `BannedPlayers` WHERE `UUID` = '" + uuid + "'");
			if (result.next() != false) {
				statement.close();
				return true;
			}
			statement.close();
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
		return false;
	}

	public boolean isBannedIP(String ip) {
		try {
			Statement statement = main.mySQL.getStatement();
			ResultSet result = statement.executeQuery("SELECT `UUID` FROM `BannedPlayers` WHERE `IP` = '" + ip + "'");
			if (result.next() != false) {
				statement.close();
				return true;
			}
			statement.close();
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
		return false;
	}

	public long getTime(String uuid) {
		try {
			Statement statement = main.mySQL.getStatement();
			ResultSet result = statement
					.executeQuery("SELECT `Time` FROM `BannedPlayers` WHERE `UUID` = '" + uuid + "'");
			if (result.next() != false) {
				long banEnd = result.getLong("Time");
				return banEnd;
			}
		} catch (Exception e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
		return 0;
	}
	public String buildMessage(String uuid) {
		String reason = getReason(uuid);
		long time = getTime(uuid);
		String message = main.files.getBanDeny(reason, time);
		return message;
	}
	public String getReason(String uuid) {
		try {
			Statement statement = main.mySQL.getStatement();
			ResultSet result = statement
					.executeQuery("SELECT `Reason` FROM `BannedPlayers` WHERE `UUID` = '" + uuid + "'");
			if (result.next() != false) {
				String banReason = result.getString("Reason");
				return banReason;
			}
		} catch (Exception e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
		return "";
	}
	public boolean unbannable(String uuid) {
		if (getTime(uuid) < System.currentTimeMillis()) {
			unban(uuid);
			return true;
		}else {
			return false;
		}
	}

	public void unban(String uuid) {
		try {
			Statement statement = main.mySQL.getStatement();
			statement.executeUpdate("DELETE FROM `BannedPlayers` WHERE `UUID` = '" + uuid + "'");
			statement.close();
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
	}
	public void ban(String uuid,String ip, BanTemplateObject banTemplateObject) {
		try {
			Statement statement = main.mySQL.getStatement();
			System.out.println(banTemplateObject.getTime().toMillis());
			long time = System.currentTimeMillis()+banTemplateObject.getTime().toMillis();
			statement.executeUpdate("INSERT INTO `BannedPlayers`(`UUID`, `Time`, `Reason`,`Ip`) VALUES ('" + uuid
					+ "','" + time + "','" + banTemplateObject.getReason()+ "','"+ip+"')");
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean isIpBanned(String ip) {
		try {
			Statement statement = main.mySQL.getStatement();
			ResultSet result = statement.executeQuery("SELECT `UUID` FROM `BannedPlayers` WHERE `Ip` = '" + ip + "'");
			while (result.next()) {
				if (main.banManager.isBanned(result.getString("UUID"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
		return false;
	}
}
