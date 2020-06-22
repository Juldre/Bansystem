package de.Juldre.Bansystem.Commands;

import de.Juldre.Bansystem.main;
import de.Juldre.Bansystem.Utils.Objects.BanTemplateObject;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {

	public BanCommand() {
		super("ban", main.files.getPermBan(), "b");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 2) {
			main.logger.log(sender, main.files.getBanUsage());
			return;
		}
		boolean isOffline = false;
		String uuid;
		String player = args[0];
		String ID = args[1];
		ProxiedPlayer t = BungeeCord.getInstance().getPlayer(player);
		if (t == null) {
			// TODO: Offline Player
			uuid = main.userManager.getUUIDByName(player);
			if (uuid == null) {
				main.logger.log(sender, main.files.getPlayerNotfound(player));
				return;
			}
			isOffline = true;
		} else {
			// TODO: Online Player
			uuid = t.getUniqueId().toString();
			
		}
		if (main.banManager.isBanned(uuid)) {
			main.logger.log(sender, main.files.getAlreadyBanned(player));
			return;
		}
		BanTemplateObject banTemplateObject = main.templateManager.getTemplate(Integer.parseInt(ID));
		if (banTemplateObject == null) {
			main.logger.log(sender, main.files.getTemplateNotfound(ID));
			return;
		}
		main.banManager.ban(uuid,(isOffline?null:t.getAddress().getAddress().getHostAddress()), banTemplateObject);
		if(!isOffline) {
			t.disconnect(main.banManager.buildMessage(uuid));
		}
		main.logger.log(sender,main.files.getBanMessage(player));
		return;
	}

}
