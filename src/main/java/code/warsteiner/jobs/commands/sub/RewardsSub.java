package code.warsteiner.jobs.commands.sub;

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

public class RewardsSub extends PlayerSubCommand {

	private static GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		FileConfiguration f = plugin.getFileManager().getCommandsConfig();
		
		return f.getString("CommandConfig.Rewards.Use").toLowerCase();
	}
 
	@Override
	public void perform(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			 
			MessageManager m = plugin.getMessageManager();
			
			FileConfiguration f = plugin.getFileManager().getCommandsConfig();
			BasicPluginManager v = plugin.getBasicPluginManager();
			
			String name = player.getName();
			UUID id = player.getUniqueId();
			
			String prefix = plugin.getMessageManager().getPrefix(player);
			
			JobAPI jb = plugin.getJobAPI();

			JobsPlayer jp = plugin.getPlayerDataManager().getJobsPlayer(name, id);
			
			String uk = f.getString("CommandConfig.GeneralUnknown");
			
			String use = f.getString("CommandConfig.Rewards.Use");
	 
			if (args.length == 1) {

				if (!f.getBoolean("CommandConfig.Rewards.Enabled")) {
					player.sendMessage(v.toHex(player, uk.replaceAll("<prefix>", prefix)));
					return;
				}

				if (jp.getCurrentJobs().size() != 0) {

					String job = jp.getCurrentJobs().get(0);

					String used = null;
					if (plugin.getBasicGUIManager().getCurrentCate().containsKey(jp.getUUID())) {
						used = plugin.getBasicGUIManager().getCurrentCate().get(jp.getUUID());
					} else {
						used = plugin.getFileManager().getRewardsConfig().getString("DefaultSorting").toUpperCase();
					}
					
					plugin.getJobsGUIManager().openBlockRewardsMenu(player, job.toUpperCase(), 1, used, false);
					return;
				} else {
					player.sendMessage(v.toHex(player, f.getString("CommandConfig.Rewards.NoJobs").replaceAll("<prefix>", prefix)));
					return;

				}

			} else if (args.length == 2) {

				if (!f.getBoolean("CommandConfig.Rewards.Enabled")) {
					player.sendMessage(v.toHex(player, uk.replaceAll("<prefix>", prefix)));
					return;
				}

				String check = args[1];

				if (jb.existJob(check)) {

					String used = null;
					if (plugin.getBasicGUIManager().getCurrentCate().containsKey(jp.getUUID())) {
						used = plugin.getBasicGUIManager().getCurrentCate().get(jp.getUUID());
					} else {
						used = plugin.getFileManager().getRewardsConfig().getString("DefaultSorting").toUpperCase();
					}
					
					plugin.getJobsGUIManager().openBlockRewardsMenu(player, check.toUpperCase(), 1, used, false);
					return;
				} else {
					player.sendMessage(v.toHex(player, f.getString("CommandConfig.Rewards.UnknownJob").replaceAll("<prefix>", prefix)));
					return;
				}

			} else {
				sender.sendMessage(m.getPrefix(player) + getUsage(sender));

			}
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
	public String getUsage(CommandSender player) {
		FileConfiguration f = plugin.getFileManager().getCommandsConfig();
		
		return plugin.getBasicPluginManager().toHex(player, f.getString("CommandConfig.Rewards.Usage"));
	}
 
	@Override
	public String getArgsLayout() { 
		
		FileConfiguration f = plugin.getFileManager().getCommandsConfig();
		
		String use = f.getString("CommandConfig.Rewards.Use");
		
		return use+" <job>";
	}

}