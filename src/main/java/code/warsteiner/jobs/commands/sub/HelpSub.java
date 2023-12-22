package code.warsteiner.jobs.commands.sub;

import java.util.List;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.JobAPI;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.manager.PlayerDataManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
import code.warsteiner.jobs.utils.playercommand.PlayerSubCommand;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class HelpSub extends PlayerSubCommand {

	private static GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		FileConfiguration f = plugin.getFileManager().getCommandSettings();
		
		return f.getString("CommandConfig.Help.Use").toLowerCase();
	}
 
	@Override
	public void perform(CommandSender sender, String[] args) {
		
		Player player = (Player) sender;
 
		MessageManager m = plugin.getMessageManager();
		
		FileConfiguration f = plugin.getFileManager().getCommandSettings();
		BasicPluginManager v = plugin.getBasicPluginManager();
		
		String name = player.getName();
		UUID id = player.getUniqueId();
		
		String prefix = plugin.getMessageManager().getPrefix(player);
		
		JobAPI jb = plugin.getJobAPI();

		JobsPlayer jp = plugin.getPlayerDataManager().getJobsPlayer(name, id);
		
		String uk = f.getString("CommandConfig.GeneralUnknown");
	 
		if (args.length == 1) {

			if (!f.getBoolean("CommandConfig.Help.Enabled")) {
				player.sendMessage(v.toHex(sender, uk.replaceAll("<prefix>", prefix)));
				return;
			}

			List<String> mg = f.getStringList("CommandConfig.Help.Message");

			for (String line : mg) {
				player.sendMessage(v.toHex(sender, line.replaceAll("<prefix>", prefix)));
			}

		} else {
			sender.sendMessage(m.getPrefix(player) + getUsage(sender));

		}
	}

	public boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
 
	@Override
	public String getUsage(CommandSender sender) {
		FileConfiguration f = plugin.getFileManager().getCommandSettings();
		
		return plugin.getBasicPluginManager().toHex(sender, f.getString("CommandConfig.Help.Usage"));
	}
 
	@Override
	public String getArgsLayout() { 
		
		FileConfiguration f = plugin.getFileManager().getCommandSettings();
		
		String use = f.getString("CommandConfig.Help.Use");
		
		return use;
	}

}