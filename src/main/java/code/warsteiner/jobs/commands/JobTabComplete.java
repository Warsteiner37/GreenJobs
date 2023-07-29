package code.warsteiner.jobs.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.JobAPI;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
import code.warsteiner.jobs.utils.playercommand.PlayerSubCommand;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class JobTabComplete  implements TabCompleter {

	private GreenJobs plugin = GreenJobs.getPlugin();

	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		ArrayList<String> l = new ArrayList<String>();

		int lg = args.length;
		 
			for (PlayerSubCommand c : plugin.getPlayerSubCommandManager().getSubCommandList()) {
				 
				 String layout = c.getArgsLayout().replaceAll("<", ";").replaceAll(">", ";").replaceAll(";", "");
				
				 String[] split = layout.split(" ");
				 
				 String va = split[0];
				 
				 if(args.length == 1) {
					 
					 l.add(va);
					 
				 } else if(args[0].equalsIgnoreCase(va)) {
					 
					 if(split.length >= lg) {
						 
						 String get = split[lg-1];
						 
						 if(get.contains(",")) {
							 
							 String[] splitnr2 = get.split(",");
							 
							 for(String m : splitnr2) {
								 l.add(m);
							 }
							 
						 } else if(get.equalsIgnoreCase("name")){
							 
							 for (Player players : Bukkit.getOnlinePlayers()) {
									l.add(players.getName());
								}
							 
						 } else if(get.equalsIgnoreCase("job")){
							 
							 for (String job : plugin.getJobAPI().getLoadedJobsArray()) {
									l.add(plugin.getJobAPI().getLoadedJobsHash().get(job).getID().toLowerCase());
								}
							 
						 } else {
							 l.add(get);
						 }
						 
					 }
					 
				 }

		 
		}

		return l;
	}
}