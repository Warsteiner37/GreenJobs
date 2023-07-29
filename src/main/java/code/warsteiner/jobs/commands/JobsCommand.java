package code.warsteiner.jobs.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.JobAPI;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
import code.warsteiner.jobs.utils.playercommand.PlayerSubCommand;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class JobsCommand implements CommandExecutor {

	private GreenJobs plugin = GreenJobs.getPlugin();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		int length = args.length;
 
		FileConfiguration f = plugin.getFileManager().getCommandsConfig();
		MessageManager m = plugin.getMessageManager();

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (length == 0) {

				plugin.getJobsGUIManager().openJobsMenu(player, false);

			} else {
				String ar = args[0].toLowerCase();

				if (find(ar) == null) {
					
					String uk = f.getString("CommandConfig.GeneralUnknown");
					 
					sender.sendMessage(plugin.getBasicPluginManager().toHex(uk));
					return true;
				} else {

					PlayerSubCommand cmd = find(ar);

					cmd.perform(sender, args);

				}

			}

		}

		return false;
	}

	public PlayerSubCommand find(String given) {
		for (PlayerSubCommand subCommand : plugin.getPlayerSubCommandManager().getSubCommandList()) {
			if (given.equalsIgnoreCase(subCommand.getName().toLowerCase())) {
				return subCommand;
			}
		}
		return null;
	}

}