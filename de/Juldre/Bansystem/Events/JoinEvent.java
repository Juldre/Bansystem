package de.Juldre.Bansystem.Events;

import de.Juldre.Bansystem.main;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void LoginEvent(net.md_5.bungee.api.event.LoginEvent e) {
		String uuid = e.getConnection().getUniqueId().toString();
		main.userManager.updatePlayer(e.getConnection());
		if(main.banManager.isBanned(uuid)) {
			if(!main.banManager.unbannable(uuid)) {
				e.setCancelled(true);
				e.setCancelReason(main.banManager.buildMessage(uuid));
			}
		}
	}
}
