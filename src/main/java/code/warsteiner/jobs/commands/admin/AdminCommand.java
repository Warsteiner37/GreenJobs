package code.warsteiner.jobs.commands.admin;

import java.util.List;
  
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommandRegistry;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
 

public class AdminCommand implements CommandExecutor {

	private static GreenJobs plugin = GreenJobs.getPlugin();
 
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		int length = args.length;

		MessageManager m = plugin.getMessageManager();
		AdminSubCommandRegistry sub = plugin.getAdminSubCommandManager();
		
		if (sender.hasPermission("greenjobs.admin.command")) {

			if (length == 0) {
				
				if(sender instanceof Player) {
					for (int i = 0; i < 99; i++) {
						sender.sendMessage("§a");
					}
				}
				 
				if(sender instanceof Player) {
				 
						TextComponent mg = new TextComponent("§8- §a§lGreenJobs Help Page §8-"); 
						mg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/R3uFfnCw"));
						mg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClick to join for additional support").create()));
                          
						sender.spigot().sendMessage(mg);
					 
				} else {
					sender.sendMessage("§8- §a§lGreenJobs Help Page §8-"); 
				}
				
				sender.sendMessage("§7");
				  
				sub.getCommandsByCategory().forEach((cat, type) -> {
					
					String type_name = cat.toString().replaceAll("_", " ");
					
					sender.sendMessage("§8["+type_name+"]");
					
					if(sender instanceof Player) {
						 
						type.forEach((cmd)-> {
							TextComponent mg = new TextComponent("§8> §7"+cmd.getUsage()); 
							mg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/jobsadmin "+cmd.getArgsLayout()));
							mg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7"+cmd.getDescription()).create()));
	                          
							sender.spigot().sendMessage(mg);
							
						});
			  
					} else {
						type.forEach((cmd)-> {
							sender.sendMessage("§8> §7"+cmd.getUsage()); 
						});
					}
					
					sender.sendMessage("§a");
				});
  
				
			} else {
				String ar = args[0].toLowerCase();

				if (find(ar) == null) {
					sender.sendMessage(m.getPrefix(sender) + " §7Error! §7Use §6/Jobsadmin help §7to see all Commands.");
					return true;
				} else {

					AdminSubCommand cmd = find(ar);

					if (sender.hasPermission(cmd.getPermission())) {
					
						cmd.perform(sender, args);

					} else {
						sender.sendMessage(m.getPrefix(sender) + " §cYou dont have Permissions!");
						return true;
					}
				}

			}

		} else {
			sender.sendMessage(m.getPrefix(sender) + " §cYou dont have Permissions!");
			return true;
		}
		return false;
	}

	public AdminSubCommand find(String given) {
		for (AdminSubCommand subCommand : plugin.getAdminSubCommandManager().getSubCommandList()) {
			if (given.equalsIgnoreCase(subCommand.getName().toLowerCase())) {
				return subCommand;
			}
		}
		return null;
	}
	
	
 
}
