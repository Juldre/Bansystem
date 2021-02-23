package de.Juldre.Bansystem;

import de.Juldre.Bansystem.Commands.BanCommand;
import de.Juldre.Bansystem.Commands.UnbanCommand;
import de.Juldre.Bansystem.Events.JoinEvent;
import de.Juldre.Bansystem.Utils.BanManager;
import de.Juldre.Bansystem.Utils.Files;
import de.Juldre.Bansystem.Utils.Logger;
import de.Juldre.Bansystem.Utils.MySQL;
import de.Juldre.Bansystem.Utils.TemplateManager;
import de.Juldre.Bansystem.Utils.UserManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;


public class main extends Plugin {
	public static Logger logger;
	public static Files files;
	public static BanManager banManager;
	public static UserManager userManager;
	public static MySQL mySQL;
	public static TemplateManager templateManager;

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		logger = new Logger();
		files = new Files(this.getDataFolder());
		if (mySQL.isError()) {
			main.logger.logError(BungeeCord.getInstance().getConsole(),
					"Cannot connect to MySQL, please check '" + this.getDataFolder().toPath() + "/config.yml'");
			return;
		}
		
		userManager = new UserManager();
		banManager = new BanManager();
		
		BungeeCord instance = BungeeCord.getInstance();
		instance.getPluginManager().registerListener(this, new JoinEvent());
		instance.getPluginManager().registerCommand(this, new BanCommand());
		instance.getPluginManager().registerCommand(this, new UnbanCommand());
	}

}
