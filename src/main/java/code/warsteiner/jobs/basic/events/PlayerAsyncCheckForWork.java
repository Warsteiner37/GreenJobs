package code.warsteiner.jobs.basic.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

import code.warsteiner.jobs.GreenJobs;
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
	public void onBlockPlace(BlockPlaceEvent e) {

		if (e.isCancelled()) {
			e.setCancelled(true);
			return;
		}

		e.getBlock().setMetadata("placed-by-player",
				new FixedMetadataValue(GreenJobs.getPlugin(), e.getPlayer().getUniqueId()));
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
						if (canWorkInRegion(loc, rl.getWorldGuardFlag()).equalsIgnoreCase("ALLOW")) {
							if (real.getEveryID().containsKey(id.toUpperCase())) {

								JobID rld = real.getEveryID().get(id.toUpperCase());

								JobStats stats = jbb.getJobStats().get(real.getID());

								stats.addTimesBrokenABlock(id, 1);
								stats.addTimesBrokenBlockDateToday(id, 1);

								double chance = rld.getChance();

								Random r = new Random();
								int chance2 = r.nextInt(100);
								if (chance2 < chance) {

									double reward = rld.getMoneyReward();
									double points = rld.getPoints();
									double exp = rld.getExp();

									plugin.getEco().depositPlayer(player, reward);

									stats.addEarnedMoneyByBlock(id, reward);
									stats.addEarningsByBlockDateToday(id, reward);
									stats.addEarningsForToday(reward);
									stats.addWorkedTimes(1);
									stats.addTotalEarnings(reward);

									if (!plugin.getLevelAPI().isMaxLevel(jbb, real.getID())) {
										stats.addExp(exp);
									}

									stats.checkDate(plugin.getBasicPluginManager().getDateTodayFromCal());
									jbb.addPoints(points);

									plugin.getBasicPluginManager().playSound(player, "PLAYER_FINISH_WORK");

									if (real.getRewardMessages() != null) {
										HashMap<String, String> rw = real.getRewardMessages();

										if (rw.containsKey("BOSSBAR")) {

											String bm = plugin.getBasicPluginManager()
													.toHex(rw.get("BOSSBAR").replaceAll("<block>", rld.getDisplay())
															.replaceAll("<job>", real.getDisplay())
															.replaceAll("<money>", "" + reward));

											Date isago5seconds = new Date((new Date()).getTime() + 3000L);
											if (BossBarHandler.last_work.containsKey(ID))
												BossBarHandler.last_work.remove(ID);
											BossBarHandler.last_work.put(ID, isago5seconds);

											double use = BossBarHandler.calculate(stats.getExp(),
													plugin.getLevelAPI().isMaxLevel(jbb, real.getID()),
													stats.getNeed());

											BarColor color = real.getBarColor();

											if (!BossBarHandler.exist(ID)) {
												BossBarHandler.createBar(player, bm, color, ID, use);
											} else {
												BossBarHandler.renameBossBar(bm, ID);
												BossBarHandler.recolorBossBar(color, ID);
												BossBarHandler.updateProgress(use, ID);
											}

										}
										if (rw.containsKey("ACTIONBAR")) {
											player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
													TextComponent.fromLegacyText(plugin.getBasicPluginManager()
															.toHex(rw.get("ACTIONBAR")
																	.replaceAll("<block>", rld.getDisplay())
																	.replaceAll("<job>", real.getDisplay())
																	.replaceAll("<money>", "" + reward))));

										}
										if (rw.containsKey("MESSAGE")) {
											player.sendMessage(plugin.getBasicPluginManager()
													.toHex(rw.get("MESSAGE").replaceAll("<block>", rld.getDisplay())
															.replaceAll("<job>", real.getDisplay())
															.replaceAll("<money>", "" + reward)));
										}
									}

									new BukkitRunnable() {

										@Override
										public void run() {
											PlayerFinishJobEvent event = new PlayerFinishJobEvent(ID, action, id,
													real.getID(), real);
											Bukkit.getServer().getPluginManager().callEvent(event);
										}
									}.runTask(plugin);

									if (plugin.getFileManager().getConfigConfig().getBoolean("UseLevels")) {
										plugin.getLevelAPI().checkLevel(jbb, real);
									}
								}

							}
						}
					}
				}

			}

			 	});

	}

	public String canWorkInRegion(Location loc, String flag) {
		if (plugin.isInstalled("WorldGuard")) {

			return plugin.getWorldGuardSupport().checkFlag(loc, flag);

		}
		return "ALLOW";
	}

}
