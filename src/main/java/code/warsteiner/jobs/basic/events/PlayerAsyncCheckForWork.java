package code.warsteiner.jobs.basic.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import static java.util.Map.entry;

import java.text.DecimalFormat;
import java.util.Map;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.utils.BossBarHandler;
import code.warsteiner.jobs.utils.custom.PlayerAddNewOwnedJobEvent;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.custom.PlayerFinishJobEvent;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobAction;
import code.warsteiner.jobs.utils.templates.JobID;
import code.warsteiner.jobs.utils.templates.JobStats;
import code.warsteiner.jobs.utils.templates.JobsPlayer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerAsyncCheckForWork implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {

		event.getBlock().setMetadata("placed-by-player",
				new FixedMetadataValue(GreenJobs.getPlugin(), "placed-by-player"));

		Block block = event.getBlock();
		Location loc = block.getLocation();
		String BlockID = block.getType().toString();

		Player player = event.getPlayer();

		plugin.getBlockAPI().addBlockToList(loc, BlockID, player.getName(),
				plugin.getBasicPluginManager().getDateTodayFromCal());

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(CreatureSpawnEvent event) {
		if (event.getSpawnReason().equals(org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER)) {
			event.getEntity().setMetadata("spawned-by-spawner", new FixedMetadataValue(plugin, "spawned-by-spawner"));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerCheckJobEvent event) {

		plugin.executor.submit(() -> {

			Player player = event.getPlayer();
			UUID ID = player.getUniqueId();
			String block = event.getWorkedID();
			String action = event.getWorkedAction().toUpperCase();

			int amount = event.getAmount();

			String id = action + "_" + block;
			 
			String world = player.getLocation().getWorld().getName();
			Location loc = player.getLocation();
			JobAction rl = plugin.getJobActionManager().getHashMap().get(action);
			JobsPlayer jbb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());

			ArrayList<String> jobs_action = plugin.getJobAPI().getJobsByAction(rl.getID());

			for (String jb : jobs_action) {

				Job real = plugin.getJobAPI().getLoadedJobsHash().get(jb);

				if (jbb.getCurrentJobs().contains(real.getID())) {

					if (real.getWorlds().contains(world)) {

						if (plugin.getJobAPI().canWorkInRegion(loc, rl.getWorldGuardFlag()).equalsIgnoreCase("ALLOW")) {

							if (real.getEveryID().containsKey(id.toUpperCase())) {

								JobID rld = real.getEveryID().get(id.toUpperCase());

								JobStats stats = jbb.getJobStats().get(real.getID());

								stats.addTimesBrokenABlock(id, amount);
								stats.addTimesBrokenBlockDateToday(id, amount);

								double chance = rld.getChance();

								Random r = new Random();
								int chance2 = r.nextInt(100);
								if (chance2 < chance) {

									double reward = amount * rld.getMoneyReward();
									double points = amount * rld.getPoints();
									double exp = amount * rld.getExp();

									plugin.getEco().depositPlayer(player, reward);

									stats.addEarnedMoneyByBlock(id, reward);
									stats.addEarningsByBlockDateToday(id, reward);
									stats.addEarningsForToday(reward);
									stats.addWorkedTimes(amount);
									stats.addTotalEarnings(reward);

									if (!plugin.getLevelAPI().isMaxLevel(jbb, real.getID())) {
										stats.addExp(exp);
									}

									stats.checkDate(plugin.getBasicPluginManager().getDateTodayFromCal());
									jbb.addPoints(points);

									plugin.getBasicPluginManager().playSound(player, "PLAYER_FINISH_WORK");

									if (plugin.getFileManager().getConfigConfig().getBoolean("UseLevels")) {
										plugin.getLevelAPI().checkLevel(jbb, real);
									}
									if (Bukkit.getPlayer(player.getName()).isOnline()) {
										this.sendRewardMessage(real, player, stats, jbb, amount, rld, action);
									}

									new BukkitRunnable() {

										@Override
										public void run() {
											PlayerFinishJobEvent event = new PlayerFinishJobEvent(ID, action, id,
													real.getID(), real);
											Bukkit.getServer().getPluginManager().callEvent(event);
										}
									}.runTask(plugin);
								}

							}
						}
					}
				}

			}

		});

	}

	public void sendRewardMessage(Job job, Player player, JobStats stats, JobsPlayer jbb, int amount, JobID block,
			String action) {

		BasicPluginManager basic = plugin.getBasicPluginManager();

		UUID ID = player.getUniqueId();

		double reward = amount * block.getMoneyReward();
		double points = amount * block.getPoints();
		double exp = amount * block.getExp();
		double level_req = stats.getNeed();
		int level_int = stats.getLevel();
		double level_exp_total = stats.getExp();

		String level_name = plugin.getLevelAPI().getDisplayOfLevel(player, job, level_int);

		double percent = (level_exp_total / level_req) * 100;
		DecimalFormat format = new DecimalFormat("0.00");
		String output = format.format(percent);

		Map<String, String> replacs = Map.ofEntries(

				entry("<job>", "" + job.getDisplay(player)), entry("<job_id>", "" + job.getID().toLowerCase()),

				entry("<points>", "" + points),

				entry("<level_req>", "" + basic.Format(level_req)), entry("<level_exp>", "" + exp),
				entry("<level_int>", "" + level_int), entry("<level_name>", "" + level_name),
				entry("<level_exp_total>", "" + level_exp_total), entry("<level_progress_percent>", "" + output + "%"),

				entry("<block>", "" + block.getDisplay(player)), entry("<block_action>", "" + action.toLowerCase()),

				entry("<money_total>", "" + stats.getEarnedToday()), entry("<money>", "" + reward),

				entry("<amount>", "" + amount));

		if (job.getRewardMessages() != null) {
			HashMap<String, String> rw = job.getRewardMessages();

			if (rw.containsKey("BOSSBAR")) {

				String bossbar_message = rw.get("BOSSBAR");
				String converted = convertText(bossbar_message, replacs, player);

				Date isago5seconds = new Date((new Date()).getTime() + 3000L);
				if (BossBarHandler.last_work.containsKey(ID))
					BossBarHandler.last_work.remove(ID);
				BossBarHandler.last_work.put(ID, isago5seconds);

				double use = BossBarHandler.calculate(stats.getExp(), plugin.getLevelAPI().isMaxLevel(jbb, job.getID()),
						stats.getNeed());

				BarColor color = job.getBarColor();

				if (!BossBarHandler.exist(ID)) {
					BossBarHandler.createBar(player, converted, color, ID, use);
				} else {
					BossBarHandler.renameBossBar(converted, ID);
					BossBarHandler.recolorBossBar(color, ID);
					BossBarHandler.updateProgress(use, ID);
				}
			}
			if (rw.containsKey("ACTIONBAR")) {

				String bossbar_message = rw.get("ACTIONBAR");
				String converted = convertText(bossbar_message, replacs, player);

				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(converted));

			}
			if (rw.containsKey("MESSAGE")) {

				String bossbar_message = rw.get("MESSAGE");
				String converted = convertText(bossbar_message, replacs, player);

				player.sendMessage(converted);
			}
		}
	}

	public String convertText(String text, Map<String, String> replacer, Player player) {
		for (String key : replacer.keySet()) {
			text = text.replaceAll(key, replacer.get(key));
		}
		return plugin.getBasicPluginManager().toHex(player, text);
	}

 
}
