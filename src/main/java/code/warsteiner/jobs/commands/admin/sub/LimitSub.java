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

public class LimitSub extends AdminSubCommand {

	private static GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "limit";
	}

	@Override
	public String getDescription() {
		return "Update Player's max Jobs";
	}

	@Override
	public void perform(CommandSender sender, String[] args) {

		MessageManager m = plugin.getMessageManager();
		PlayerDataManager data = plugin.getPlayerDataManager();

		if (args.length == 1) {

			sender.sendMessage(m.getPrefix(sender) + " §7Usage§8: §6" + getUsage());

		} else if (args.length == 4 && args[1].toLowerCase().equalsIgnoreCase("set")) {

			String player = args[2];
			String value = args[3];

			if (data.getPlayerByName(player) == null) {
				sender.sendMessage(m.getPrefix(sender) + " §7Error! Player §c" + player + " §7does not exist!");
				return;
			}

			UUID uuid = data.getPlayerByName(player);

			if (isInt(value)) {

				JobsPlayer jb = data.getJobsPlayerList().get(uuid);

				int rl = Integer.valueOf(value)-1;
				
				jb.setMax(rl);

				sender.sendMessage(
						m.getPrefix(sender) + " §7Set §c" + player + "'s §7Job-Limit to §a"+value+"§7!");
				return;

			} else {
				sender.sendMessage(m.getPrefix(sender) + " §7Error! The value must be a Integer");

				return;
			}

		} else if (args.length == 4 && args[1].toLowerCase().equalsIgnoreCase("add")) {
			
			String player = args[2];
			String value = args[3];

			if (data.getPlayerByName(player) == null) {
				sender.sendMessage(m.getPrefix(sender) + " §7Error! Player §c" + player + " §7does not exist!");
				return;
			}

			UUID uuid = data.getPlayerByName(player);

			if (isInt(value)) {

				JobsPlayer jb = data.getJobsPlayerList().get(uuid);
				
				int old = jb.getMaxJobs();

				int rl = Integer.valueOf(value)-1;
				
				jb.setMax(old+rl);

				sender.sendMessage(
						m.getPrefix(sender) + " §7Added "+value+" to the max Jobs Limit of §c" + player + "!");
				return;

			} else {
				sender.sendMessage(m.getPrefix(sender) + " §7Error! The value must be a Integer");

				return;
			}

		} else if (args.length == 4 && args[1].toLowerCase().equalsIgnoreCase("remove")) {
			
			String player = args[2];
			String value = args[3];

			if (data.getPlayerByName(player) == null) {
				sender.sendMessage(m.getPrefix(sender) + " §7Error! Player §c" + player + " §7does not exist!");
				return;
			}

			UUID uuid = data.getPlayerByName(player);

			if (isInt(value)) {

				JobsPlayer jb = data.getJobsPlayerList().get(uuid);
				
				int old = jb.getMaxJobs();

				int rl = Integer.valueOf(value)-1;
				
				jb.setMax(old-rl);

				sender.sendMessage(
						m.getPrefix(sender) + " §7Removed "+value+" from the max Jobs Limit of §c" + player + "!");
				return;

			} else {
				sender.sendMessage(m.getPrefix(sender) + " §7Error! The value must be a Integer");

				return;
			}

		}  else {
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
		return "§7/Jobsadmin §alimit §7<set,add,remove> <name> <amount>";
	}

	@Override
	public String getPermission() {
		return "greenjobs.admin.limit";
	}

	@Override
	public boolean showOnHelp() {
		return true;
	}

	@Override
	public String getArgsLayout() {
		return "limit <set,add,remove> <name> <amount>";
	}

	@Override
	public AdminCommandCategory getCategory() { 
		return AdminCommandCategory.PLAYER_STATS;
	}
	
}