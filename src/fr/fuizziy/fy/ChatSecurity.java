package fr.fuizziy.fy;
 
import java.util.HashSet;
import java.util.Set;
  
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent; 
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
 
 

public class ChatSecurity extends JavaPlugin implements Listener {

	public Set<String> words_list; 
	public String message;
	public boolean super_efficient;
	public boolean log_offenses;
	
	private boolean check_commands; 
	
	public void onEnable() {
		words_list = new HashSet<String>(getConfig().getStringList("words_list"));
		message = ChatColor.translateAlternateColorCodes('&', getConfig().getString("message"));
		super_efficient = getConfig().getBoolean("super_efficient");
		log_offenses = getConfig().getBoolean("log_offenses");
		check_commands = getConfig().getBoolean("check_commands");
		getLogger().info("[FYChatSecurity] Loaded"); 
		if (check_commands)
			getServer().getPluginManager().registerEvents(new CommandsListener(this), this);
		getServer().getPluginManager().registerEvents(this, this); 
		this.getCommand("chatsecurity").setExecutor(new MainCommand(this));
	}
	
	public void onDisable() {
		getLogger().info("[FYChatSecurity] Unloaded");
	}
	 
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) { 
		if (e.getPlayer().hasPermission("fychatsecurity.bypass"))
			return;
		new BukkitRunnable() { 
			@Override
			public void run() { 

				String rawentry = super_efficient ? e.getMessage().replaceAll("/[^A-Za-z]/", "") : e.getMessage();
				for (String s : words_list) {
					if (rawentry.contains(s)) {
						if (log_offenses)
							getLogger().info(e.getPlayer().getName() + " tried to speak: '" + e.getMessage() + "'");
						e.getPlayer().sendMessage(message.replace("%&w", s));
						e.setCancelled(true);
						break;
					}
				} 
				
			}
		}.runTaskAsynchronously(this);
		
	}

}
 