package code.warsteiner.jobs.commands.admin.sub;

import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.manager.PlayerDataManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class LevelSub extends AdminSubCommand {

	private static GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "level";
	}

	@Override
	public String getDescription() {
		return "Update Player's Level in a Job";
	}

	@Override
	public void perform(CommandSender sender, String[] args) {

		MessageManager m = plugin.getMessageManager();
		PlayerDataManager data = plugin.getPlayerDataManager();

		if (args.length == 1) {

			sender.sendMessage(m.getPrefix() + " §7Correct Usage§8: §6" + getUsage());

		} else if (args.length == 5 && args[1].toLowerCase().equalsIgnoreCase("set")) {

			String player = args[2];
			String job = args[3].toUpperCase();
			String value = args[4];

			if (data.getPlayerByName(player) == null) {
				sender.sendMessage(m.getPrefix() + " §7Error! Player §c" + player + " §7does not exist!");
				return;
			}

			UUID uuid = data.getPlayerByName(player);

			if (isInt(value)) {

				if (plugin.getJobAPI().existJob(job)) {

					JobsPlayer jb = data.getJobsPlayerList().get(uuid);

					Job rl = plugin.getJobAPI().getLoadedJobsHash().get(job);

					if (jb.getOwnedJobs().contains(rl.getID())) {

						jb.getJobStats().get(rl.getID()).setLevel(Integer.valueOf(value));

						sender.sendMessage(m.getPrefix() + " §7Set §c" + player + "'s §7Level in Job §a" + job + " §7to §6"
								+ value + ".");
						return;
					} else {
						sender.sendMessage(m.getPrefix() + " §7Error! Player does not own that Job!");
						return;
					}

				} else {
					sender.sendMessage(m.getPrefix() + " §7Error! Cannot find that Job!");
					return;
				}

			} else {
				sender.sendMessage(m.getPrefix() + " §7Error! The value must be a Integer");

				return;
			}

		} else {
			sender.sendMessage(m.getPrefix() + " §7Correct Usage§8: §6" + getUsage());

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
		return "§6/jpm level <set> <name> <job> <amount> §8| §7Manage Levels";
	}

	@Override
	public String getPermission() {
		return "greenjobs.admin.level";
	}

	@Override
	public boolean showOnHelp() {
		return true;
	}

	@Override
	public String getArgsLayout() { 
		return "level <set> <name> <job> <amount>";
	}
	
}