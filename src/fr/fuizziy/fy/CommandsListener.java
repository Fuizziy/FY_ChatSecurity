package fr.fuizziy.fy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;  

public class CommandsListener implements Listener {

	private ChatSecurity instance;
	
	public CommandsListener(ChatSecurity instance) {
		this.instance = instance;
	}
	
	@EventHandler
	public void OnPlayerCommand(PlayerCommandPreprocessEvent e) {
		if (e.getPlayer().hasPermission("fychatsecurity.bypass"))
			return; 

		String rawentry = instance.super_efficient ? e.getMessage().replaceAll("/[^A-Za-z]/", "") : e.getMessage();
		for (String s : instance.words_list) {
			if (rawentry.contains(s)) {
				if (instance.log_offenses)
					instance.getLogger().info(e.getPlayer().getName() + " tried to speak: '" + e.getMessage() + "'");
				e.getPlayer().sendMessage(instance.message.replace("%w", s));
				e.setCancelled(true);
				break;
			}
		}

	}
}
