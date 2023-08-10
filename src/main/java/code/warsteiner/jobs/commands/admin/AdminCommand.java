package code.warsteiner.jobs.commands.admin;

import java.util.List;
 
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
 

public class AdminCommand implements CommandExecutor {

	private static GreenJobs plugin = GreenJobs.getPlugin();
 
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		int length = args.length;

		MessageManager m = plugin.getMessageManager();
		
		if (sender.hasPermission("greenjobs.admin.command")) {

			if (length == 0) {
				
				sender.sendMessage("§7");
				sender.sendMessage(" §8| §a§lGreenJobs §8- §bHelp §8|");

				for(AdminSubCommand cmd : plugin.getAdminSubCommandManager().getSubCommandList()) {
					sender.sendMessage("§8-> "+cmd.getUsage()); 
				}
				
				sender.sendMessage("§7");
				
			} else {
				String ar = args[0].toLowerCase();

				if (find(ar) == null) {
					sender.sendMessage(m.getPrefix(sender) + " §7Error! §7Use §6/jpm §7to see all Commands.");
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
