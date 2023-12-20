package code.warsteiner.jobs.commands.admin.sub;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.JobAPI;
import code.warsteiner.jobs.api.JobLoadAPI;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.manager.PlayerDataManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
import code.warsteiner.jobs.utils.enums.AdminCommandCategory;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobAction;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class ReloadSub extends AdminSubCommand {

	private static GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public String getDescription() {
		return "Reload Plugin Features";
	}

	@Override
	public void perform(CommandSender sender, String[] args) {

		MessageManager m = plugin.getMessageManager();
		PlayerDataManager data = plugin.getPlayerDataManager();
		JobAPI jap = plugin.getJobAPI();
		JobLoadAPI load = plugin.getJobLoadAPI();

		if (args.length == 1) {

			sender.sendMessage(m.getPrefix(sender) + " §7Usage§8: §6" + getUsage());

		} else if (args.length == 2 && args[1].toLowerCase().equalsIgnoreCase("jobs")) {

			jap.clearLists();
			
			load.loadJobsbyStart();
			jap.sortJobsAfterActions();

			sender.sendMessage(m.getPrefix(sender) + " §a§lReloaded all Jobs!");
			
		} else {
			sender.sendMessage(m.getPrefix(sender) + " §7Usage§8: §6" + getUsage());

		}

	}
  
	@Override
	public String getUsage() {
		return "§7/Jobsadmin §areload §7<jobs>";
	}

	@Override
	public String getPermission() {
		return "greenjobs.admin.reload.jobs";
	}

	@Override
	public boolean showOnHelp() {
		return true;
	}

	@Override
	public String getArgsLayout() {
		return "reload <jobs>";
	}

	@Override
	public AdminCommandCategory getCategory() {
		return AdminCommandCategory.OTHER_COMMANDS;
	}

}