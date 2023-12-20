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
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.manager.PlayerDataManager;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommand;
import code.warsteiner.jobs.utils.enums.AdminCommandCategory;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobAction;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class CheckSub extends AdminSubCommand {

	private static GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "check";
	}

	@Override
	public String getDescription() {
		return "Check Plugin Features";
	}

	@Override
	public void perform(CommandSender sender, String[] args) {

		MessageManager m = plugin.getMessageManager();
		PlayerDataManager data = plugin.getPlayerDataManager();

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (args.length == 1) {

				sender.sendMessage(m.getPrefix(sender) + " §7Usage§8: §6" + getUsage());

			} else if (args.length == 3 && args[2].toLowerCase().equalsIgnoreCase("worlds")) {

				String job = args[1].toUpperCase();

				if (plugin.getJobAPI().existJob(job)) {

					Job rl = plugin.getJobAPI().getLoadedJobsHash().get(job);

					List<String> worlds = rl.getWorlds();

					String world_in = player.getWorld().getName();

					if (worlds.contains(world_in)) {
						sender.sendMessage(m.getPrefix(sender) + " " + rl.getDisplay(player) + " §8| §b" + world_in
								+ " §8| §atrue");
					} else {
						sender.sendMessage(m.getPrefix(sender) + " " + rl.getDisplay(player) + " §8| §b" + world_in
								+ " §8| §cfalse");
					}

				} else {
					sender.sendMessage(m.getPrefix(sender) + " §7Error! Cannot find that Job!");
					return;
				}

			} else if (args.length == 3 && args[2].toLowerCase().equalsIgnoreCase("in_job")) {

				String job = args[1].toUpperCase();

				if (plugin.getJobAPI().existJob(job)) {

					JobsPlayer jb = data.getJobsPlayerList().get(player.getUniqueId());

					Job rl = plugin.getJobAPI().getLoadedJobsHash().get(job);

					if (jb.getCurrentJobs().contains(rl.getID())) {
						sender.sendMessage(
								m.getPrefix(sender) + " " + rl.getDisplay(player) + " §8| §bIs in Job §8| §atrue");
					} else {
						sender.sendMessage(
								m.getPrefix(sender) + " " + rl.getDisplay(player) + " §8| §bIs in Job §8| §cfalse");
					}

				} else {
					sender.sendMessage(m.getPrefix(sender) + " §7Error! Cannot find that Job!");
					return;
				}

			} else if (args.length == 3 && args[2].toLowerCase().equalsIgnoreCase("worldguard")) {

				String job = args[1].toUpperCase();

				if (plugin.getJobAPI().existJob(job)) {

					Job rl = plugin.getJobAPI().getLoadedJobsHash().get(job);
					Location loc = player.getLocation();

					if (rl.getActions() != null && !rl.getActions().isEmpty()) {
						for (String ac : rl.getActions()) {

							JobAction action = plugin.getJobActionManager().getHashMap().get(ac);

							if (plugin.getJobAPI().canWorkInRegion(loc, action.getWorldGuardFlag())
									.equalsIgnoreCase("ALLOW")) {
								sender.sendMessage(
										m.getPrefix(sender) + " §7WorldGuard §8> §6" + action.getID().toLowerCase()
												+ " §8| §b" + action.getWorldGuardFlag() + " §8| §aALLOW");
							} else {
								sender.sendMessage(
										m.getPrefix(sender) + " §7WorldGuard §8> §6" + action.getID().toLowerCase()
												+ " §8| §b" + action.getWorldGuardFlag() + " §8| §cDENY");
							}

						}
					} else {
						sender.sendMessage(m.getPrefix(sender) + " §cJob has no Actions.");
						return;
					}

				} else {
					sender.sendMessage(m.getPrefix(sender) + " §7Error! Cannot find that Job!");
					return;
				}

			} else if (args.length == 3 && args[2].toLowerCase().equalsIgnoreCase("block_look_at")) {

				String job = args[1].toUpperCase();

				if (plugin.getJobAPI().existJob(job)) {

					Job rl = plugin.getJobAPI().getLoadedJobsHash().get(job);

					Block block = player.getTargetBlock(null, 25);

					Material type = block.getType();

					if (!type.equals(Material.AIR)) {

						if (rl.getActions() != null && !rl.getActions().isEmpty()) {
							for (String ac : rl.getActions()) {

								JobAction action = plugin.getJobActionManager().getHashMap().get(ac);

								String id = action.getID().toString().toUpperCase() + "_" + type.toString();

								if (rl.getEveryID().containsKey(id.toString().toUpperCase())) {
									sender.sendMessage(m.getPrefix(sender) + " " + rl.getDisplay(player) + " §8| §a"
											+ action.getID().toString().toLowerCase() + " §8| §b"
											+ type.toString().toLowerCase() + " §8| §atrue");
								} else {
									sender.sendMessage(m.getPrefix(sender) + " " + rl.getDisplay(player) + " §8| §a"
											+ action.getID().toString().toLowerCase() + " §8| §b"
											+ type.toString().toLowerCase() + " §8| §cfalse");
								}

							}
						} else {
							sender.sendMessage(m.getPrefix(sender) + " §cJob has no Actions.");
							return;
						}

					} else {
						sender.sendMessage(m.getPrefix(sender) + " §cBlock cannot be AIR.");
						return;
					}

				} else {
					sender.sendMessage(m.getPrefix(sender) + " §7Error! Cannot find that Job!");
					return;
				}

			} else {
				sender.sendMessage(m.getPrefix(sender) + " §7Usage§8: §6" + getUsage());

			}

		} else {
			sender.sendMessage(m.getPrefix(sender) + " §4§lOnly a Player can use this Command!");
			return;
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
		return "§7/Jobsadmin §acheck §7<job> <worlds,in_job,worldguard,block_look_at>";
	}

	@Override
	public String getPermission() {
		return "greenjobs.admin.check.work";
	}

	@Override
	public boolean showOnHelp() {
		return true;
	}

	@Override
	public String getArgsLayout() {
		return "check <job> <worlds,in_job,worldguard,block_look_at>";
	}

	@Override
	public AdminCommandCategory getCategory() {
		return AdminCommandCategory.OTHER_COMMANDS;
	}

}