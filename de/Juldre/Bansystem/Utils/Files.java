package de.Juldre.Bansystem.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.Juldre.Bansystem.main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Files {

	private String prefix, banMessage, unbanMessage, playerNotfound, templateNotfound, banDeny, banUsage, unbanUsage,
			alreadyBanned, alreadyUnbanned, permBan, permUnban;
	private SimpleDateFormat format;

	public String getPrefix() {
		return prefix.replace("&", "§");
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getBanMessage(String playerName) {
		return banMessage.replaceAll("%player%", playerName).replace("&", "§");
	}

	public void setBanMessage(String banMessage) {
		this.banMessage = banMessage;
	}

	public String getUnbanMessage(String playerName) {
		return unbanMessage.replaceAll("%player%", playerName).replace("&", "§");
	}

	public void setUnbanMessage(String unbanMessage) {
		this.unbanMessage = unbanMessage;
	}

	public String getPlayerNotfound(String playerName) {
		return playerNotfound.replaceAll("%player%", playerName).replace("&", "§");
	}

	public void setPlayerNotfound(String playerNotfound) {
		this.playerNotfound = playerNotfound;
	}

	public String getTemplateNotfound(String templateID) {
		return templateNotfound.replaceAll("%tempolate%", templateID).replace("&", "§");
	}

	public void setTemplateNotfound(String templateNotfound) {
		this.templateNotfound = templateNotfound;
	}

	public String getBanDeny(String reason, long time) {
		return banDeny.replace("%reason%", reason).replace("%time%", getFormat().format(new Date(time))).replace("&",
				"§");
	}

	public void setBanDeny(String banDeny) {
		this.banDeny = banDeny;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = new SimpleDateFormat(format.replace("&", "§"));
	}

	private File file, folder;
	private String host, database, username, password;
	private int port;
	Configuration config;

	public Files(File dataFolder) {
		file = new File(dataFolder, "config.yml");
		folder = dataFolder;
		try {
			createFolder(folder);

			String setPrefix = "Messages.Prefix", setBanMessage = "Messages.Ban.Message",
					setUnbanMessage = "Messages.Unban.Message", setDenyMessage = "Messages.Ban.Deny",
					setTimeFormat = "Messages.Time.Format", setPlayerNotFoundMessage = "Messages.Ban.Notfound.Player",
					setTemplateNotFoundMessage = "Messages.Ban.Notfound.Template",
					setPlayerAlreadyBanned = "Messages.Ban.AlreadyBanned", setBanUsageMegssage = "Messages.Ban.Usage",
					setUnbanUsageMessage = "Messages.Unban.Usage",
					setAlreadyUnbannedMessage = "Messages.Unban.AlreadyUnbanned";
			String permSetBan = "Permissions.Ban", permSetUnban = "Permissions.Unban";
			if (createFile(file)) {
				config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				main.templateManager = new TemplateManager();

				config.set("MySQL.Host", "localhost");
				config.set("MySQL.Port", 3306);
				config.set("MySQL.Database", "database");
				config.set("MySQL.Username", "username");
				config.set("MySQL.Password", "password");

				config.set(setPrefix, "&8[&6Bansystem&8]&7 ");
				config.set(setBanMessage, "&6%player% &7successfully Banned");
				config.set(setUnbanMessage, "&6%player%&7 successfully Unbanned");
				config.set(setDenyMessage, "&6&4You were Banned!\n" + "&6Reason&8:&7 %reason%\n"
						+ "&6Your ban lasts until&8:\n" + "&7%time%");
				config.set(setTimeFormat, "yyyy/MM/dd HH:mm (z)");
				config.set(setPlayerNotFoundMessage, "&4&l%player% &4not found");
				config.set(setTemplateNotFoundMessage, "&4&l%template% &4not found");
				config.set(setPlayerAlreadyBanned, "&6%player%&7 is already Banned");
				config.set(setBanUsageMegssage, "&7Usage&8: &7/&6ban &7[&6Player&7] [&6Template-ID&7]");
				config.set(setUnbanUsageMessage, "&7Usage&8: &7/&6unban &7[&6Player&7]");
				config.set(setAlreadyUnbannedMessage, "&6%player% &7is already Unbanned");
				config.set(permSetBan, "BanSystem.Ban");
				config.set(permSetUnban, "BanSystem.UnBan");
				main.templateManager.generateTemplates(config);

				saveConfig();
			} else {
				config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				main.templateManager = new TemplateManager();
			}
			setHost(config.getString("MySQL.Host"));
			setPort(config.getInt("MySQL.Port"));
			setDatabase(config.getString("MySQL.Database"));
			setUsername(config.getString("MySQL.Username"));
			setPassword(config.getString("MySQL.Password"));

			main.templateManager.loadTemplates(config);

			setPrefix(config.getString(setPrefix));
			setFormat(config.getString(setTimeFormat));
			setBanDeny(config.getString(setDenyMessage));
			setBanMessage(config.getString(setBanMessage));
			setUnbanMessage(config.getString(setUnbanMessage));
			setPlayerNotfound(config.getString(setPlayerNotFoundMessage));
			setTemplateNotfound(config.getString(setTemplateNotFoundMessage));
			setAlreadyBanned(config.getString(setPlayerAlreadyBanned));
			setBanUsage(config.getString(setBanUsageMegssage));
			setUnbanUsage(config.getString(setUnbanUsageMessage));
			setAlreadyUnbanned(config.getString(setAlreadyUnbannedMessage));
			setPermBan(config.getString(permSetBan));
			setPermUnban(config.getString(permSetUnban));
			main.logger.setPrefix(getPrefix());

			main.mySQL = new MySQL(getHost(), getDatabase(), getUsername(), getPassword(), getPort());

		} catch (IOException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void saveConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (IOException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
		}
	}

	public boolean createFile(File file) {
		if (file.exists()) {
			return false;
		}
		try {
			file.createNewFile();
			return true;
		} catch (IOException e) {
			main.logger.logError(BungeeCord.getInstance().getConsole(), e.getMessage());
			return false;
		}

	}

	public boolean createFolder(File folder) {
		if (folder.exists()) {
			return false;
		}
		folder.mkdir();
		return true;
	}

	public String getAlreadyBanned(String playerName) {
		return alreadyBanned.replaceAll("%player%", playerName).replaceAll("&", "§");
	}

	public void setAlreadyBanned(String alreadyBanned) {
		this.alreadyBanned = alreadyBanned;
	}

	public String getUnbanUsage() {
		return unbanUsage.replace("&", "§");
	}

	public void setUnbanUsage(String unbanUsage) {
		this.unbanUsage = unbanUsage;
	}

	public String getBanUsage() {
		return banUsage.replace("&", "§");
	}

	public void setBanUsage(String banUsage) {
		this.banUsage = banUsage;
	}

	public String getAlreadyUnbanned(String playerName) {
		return alreadyUnbanned.replaceAll("%player%", playerName).replaceAll("&", "§");
	}

	public void setAlreadyUnbanned(String alreadyUnbanned) {
		this.alreadyUnbanned = alreadyUnbanned;
	}

	public String getPermBan() {
		return permBan;
	}

	public void setPermBan(String permBan) {
		this.permBan = permBan;
	}

	public String getPermUnban() {
		return permUnban;
	}

	public void setPermUnban(String permUnban) {
		this.permUnban = permUnban;
	}
}
