package code.warsteiner.jobs.commands.admin.sub;

import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.manager.PlayerDataManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
import code.warsteiner.jobs.utils.enums.AdminCommandCategory;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class VersionSub extends AdminSubCommand {

	private static GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "version";
	}

	@Override
	public String getDescription() {
		return "Plugin Version Details";
	}

	@Override
	public void perform(CommandSender sender, String[] args) {

		MessageManager m = plugin.getMessageManager();

		if (args.length == 1) {

			String version = plugin.getDescription().getVersion();
			String api = plugin.getDescription().getAPIVersion();
			
			sender.sendMessage(m.getPrefix(sender) + " §7Plugin Version§8: §bv"+version+" §8/ §7"+api);

		} else {
			sender.sendMessage(m.getPrefix(sender) + " §7Usage§8: §6" + getUsage());

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
	public String getUsage() {
		return "§7/Jobsadmin §aversion";
	}

	@Override
	public String getPermission() {
		return "greenjobs.admin.version";
	}

	@Override
	public boolean showOnHelp() {
		return true;
	}

	@Override
	public String getArgsLayout() {
		return "version";
	}

	@Override
	public AdminCommandCategory getCategory() { 
		return AdminCommandCategory.OTHER_COMMANDS;
	}
	
}