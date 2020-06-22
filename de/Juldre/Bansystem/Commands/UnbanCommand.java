package de.Juldre.Bansystem.Commands;

import de.Juldre.Bansystem.main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnbanCommand extends Command {

	public UnbanCommand() {
		super("unban", main.files.getPermUnban(), "ub");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 1) {
			main.logger.log(sender, main.files.getUnbanUsage());
			return;
		}
		String uuid;
		String player = args[0];
		ProxiedPlayer t = BungeeCord.getInstance().getPlayer(player);
		if (t == null) {
			// TODO: Offline Player
			uuid = main.userManager.getUUIDByName(player);
			if (uuid == null) {
				main.logger.log(sender, main.files.getPlayerNotfound(player));
				return;
			}
		} else {
			// TODO: Online Player
			uuid = t.getUniqueId().toString();
		}
		if (!main.banManager.isBanned(uuid)) {
			main.logger.log(sender, main.files.getAlreadyUnbanned(player));
			return;
		}
		main.banManager.unban(uuid);
		main.logger.log(sender, main.files.getUnbanMessage(player));
		return;
	}

}
