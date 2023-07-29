package code.warsteiner.jobs.commands.admin;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs; 

public class JobsManagerCommand implements CommandExecutor {

	private GreenJobs plugin = GreenJobs.getPlugin();

	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		int length = args.length;

		if(sender.hasPermission("greenjobs.admin.basic")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				String name = player.getName();
				UUID id = player.getUniqueId();
				 
				if(length == 0) {
					
					plugin.getJobsGUIManager().openJobsManager(player, 1);
					
				}
				
			}
		}
		return false;
	}
}