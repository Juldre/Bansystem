package de.Juldre.Bansystem.Utils;

import net.md_5.bungee.api.CommandSender;

public class Logger {
	private String prefix;

	@SuppressWarnings("deprecation")
	public void logError(CommandSender sender, String stackTrace) {
		sender.sendMessage(prefix+"\n§7------§cBegin Error§7------\n§c" + stackTrace + "\n§7-------§cEnd Error§7-------");
	}

	@SuppressWarnings("deprecation")
	public void log(CommandSender sender, String message) {
		sender.sendMessage(prefix+message);
	}

	public Logger() {
		setPrefix("§8[§6Bansystem§8]§7 ");
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
