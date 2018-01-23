package fr.fuizziy.fy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class MainCommand implements CommandExecutor {

	ChatSecurity instance;
	
	public MainCommand(ChatSecurity instance) {
		this.instance = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args[0].toLowerCase().equals("list")) {
			String result = "";
			for (String s : instance.words_list) {
				result += s + ", ";
			}   
			sender.sendMessage(ChatColor.AQUA + "Prohibited words list:" + (result)); 
		}
		return false;
	}

}
 