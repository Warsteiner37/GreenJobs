package code.warsteiner.jobs.basic.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.basic.BasicGUIManager;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.manager.JobsGUIManager;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.utils.UtilManager;
import code.warsteiner.jobs.utils.enums.CustomItemAction;
import code.warsteiner.jobs.utils.enums.GUIType;
import code.warsteiner.jobs.utils.templates.CustomItem;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class PlayerASyncInventoryClickEvent implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {

			if (event.getClickedInventory() == null) {
				return;
			}
			if (event.getCurrentItem() == null) {
				return;
			}

			if (event.getView().getTitle() == null) {
				return;
			}

			if (event.getCurrentItem().getItemMeta() == null) {
				return;
			}

			if (event.getCurrentItem().getItemMeta().getDisplayName() == null) {
				return;
			}

			String title = event.getView().getTitle();

			BasicGUIManager m = plugin.getBasicGUIManager();
			JobsGUIManager m2 = plugin.getJobsGUIManager();
			MessageManager mg = plugin.getMessageManager();

			Player player = (Player) event.getWhoClicked();

			UUID ID = player.getUniqueId();

			ItemStack item = event.getCurrentItem();

			String display = plugin.getBasicPluginManager().toHex(item.getItemMeta().getDisplayName());

			JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), ID);

			String d = m.getJobData().get(ID);

			Job job = plugin.getJobAPI().getLoadedJobsHash().get(d);

			if (m.isRewardsMenu(player, job, title)) {

				event.setCancelled(true);

				FileConfiguration file = plugin.getFileManager().getRewardsConfig();

				new BukkitRunnable() {
					@Override
					public void run() {

						executeCustomItemClick(player, display, item, GUIType.REWARDS);

						String c1 = file.getString("SortModes.RANDOM.Icon");
						String c2 = file.getString("SortModes.NORMAL.Icon");

						Material i1 = plugin.getItemManager()
								.createOrGetItem("CatItemRewardsRandom", c1, player.getName(), 0).getType();
						Material i2 = plugin.getItemManager()
								.createOrGetItem("CatItemRewardsNormal", c2, player.getName(), 0).getType();

						List<String> modes = file.getStringList("SortModes.List");
						;

						if (item.getType().equals(i1) || item.getType().equals(i2)) {

							plugin.getBasicPluginManager().playSound(player, "REWARDS_CHANGE_SORTING");

							List<String> used = null;

							List<String> test = modes;

							String current = null;
							if (plugin.getBasicGUIManager().getCurrentCate().containsKey(jb.getUUID())) {
								current = plugin.getBasicGUIManager().getCurrentCate().get(jb.getUUID());
							} else {
								current = plugin.getFileManager().getRewardsConfig().getString("DefaultSorting")
										.toUpperCase();
							}

							test.remove(current);

							used = test;

							int page = m2.details_page_manager.get(ID);

							if (used.size() != 0) {
								String next = used.get(0).toUpperCase();

								plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), page, next);

							} else {
								String def = file.getString("DefaultSorting").toUpperCase();

								plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), page, def);

							}

						}

					}
				}.runTaskAsynchronously(plugin);

			} else if (m.isJobsManagerMenu(player, title)) {
				event.setCancelled(true);

				new BukkitRunnable() {
					@Override
					public void run() {

						if (m2.details_page_manager.containsKey(ID)) {

							int page = m2.details_page_manager.get(ID);

							if (display.equalsIgnoreCase("§7Next Page §8->")) {

								plugin.getBasicPluginManager().playSound(player, "JOBS_MANAGER_UPDATE_PAGE");

								ArrayList<String> jobs = plugin.getJobAPI().getLoadedJobsArray();

								int total = jobs.size();

								int startIndex = page * 35;
								int endIndex = Math.min(startIndex + 35, total) + 1;

								if (total >= endIndex) {
									plugin.getJobsGUIManager().openJobsManager(player, page + 1);
									player.sendMessage(UtilManager.prefix + "§7Open Page: " + page);
									return;
								} else {
									player.sendMessage(UtilManager.prefix + "§7Theres no other Page!");
									return;
								}
							} else if (display.equalsIgnoreCase("§8<- §7Go Back")) {

								plugin.getBasicPluginManager().playSound(player, "JOBS_MANAGER_UPDATE_PAGE");

								if (page == 1) {
									player.sendMessage(UtilManager.prefix + "§7Theres no other Page!");
									return;
								} else {
									plugin.getJobsGUIManager().openJobsManager(player, page - 1);

									player.sendMessage(UtilManager.prefix + "§7Going back to Page: " + page);
									return;
								}

							}
						}

					}
				}.runTaskAsynchronously(plugin);

			} else if (m.isJobsMenu(player, title)) {
				event.setCancelled(true);

				new BukkitRunnable() {
					@Override
					public void run() {

						String date = plugin.getBasicPluginManager().getDateTodayFromCal();

						executeCustomItemClick(player, display, item, GUIType.JOBS);

						if (plugin.getJobAPI().getLoadedJobsArray() != null) {

							plugin.getJobAPI().getLoadedJobsHash().forEach((id, job) -> {

								ItemStack item2 = job.getIcon();

								String display2 = job.getDisplay();

								if (item2.getType().equals(item.getType()) && display.equalsIgnoreCase(display2)) {

									if (jb.getCurrentJobs().contains(job.getID())) {
										// lore.add("In");

										if (plugin.getFileManager().getConfigConfig().getBoolean("JobInfoGUI")) {
											Bukkit.getScheduler().runTask(plugin, () -> {
												plugin.getJobsGUIManager().openOptionsMenu(player, job.getID(), false);
											});
										} else {

											if (mg.hasMessage("job_already_joined")) {
												player.sendMessage(mg.getMessage("job_already_joined")
														.replaceAll("<job>", job.getDisplay()));
											}
										}

										return;
									} else if (jb.getOwnedJobs().contains(job.getID())) {

										if (jb.getCurrentJobs().size() >= jb.getMaxJobs() + 1) {
											if (mg.hasMessage("too_many_jobs")) {
												player.sendMessage(mg.getMessage("too_many_jobs").replaceAll("<job>",
														job.getDisplay()));
											}
											return;
										} else {
											jb.addCurrentJob(job.getID(), date);

											if (mg.hasMessage("job_join_message")) {
												player.sendMessage(mg.getMessage("job_join_message").replaceAll("<job>",
														job.getDisplay()));
											}

											plugin.getBasicPluginManager().playSound(player, "PLAYER_JOINED_JOB");

											if (plugin.getFileManager().getConfigConfig()
													.getBoolean("ReOpenForUpdate")) {
												plugin.getJobsGUIManager().updateJobMenu(player);
											} else {
												Bukkit.getScheduler().runTask(plugin, () -> {
													player.closeInventory();
												});
											}
										}

										return;
									} else {

										if (job.getPrice() <= 0) {

											jb.addOwnedJob(job.getID());

											if (mg.hasMessage("job_is_free_message")) {
												player.sendMessage(mg.getMessage("job_is_free_message")
														.replaceAll("<job>", job.getDisplay()));
											}

											plugin.getBasicPluginManager().playSound(player, "PLAYER_BOUGHT_JOB");

											if (plugin.getFileManager().getConfigConfig()
													.getBoolean("ReOpenForUpdate")) {
												plugin.getJobsGUIManager().updateJobMenu(player);
											} else {
												Bukkit.getScheduler().runTask(plugin, () -> {
													player.closeInventory();
												});
											}

											return;
										} else {

											if (plugin.getEco().getBalance(player) >= job.getPrice()
													|| plugin.getEco().getBalance(player) == job.getPrice()) {
												// paid

												if (plugin.getFileManager().getConfigConfig()
														.getBoolean("NeedToConfirmWhileBuying")) {

													Bukkit.getScheduler().runTask(plugin, () -> {
														plugin.getJobsGUIManager().openConfPurchaseMenu(player,
																job.getID(), false);
													});

												} else {

													plugin.getEco().withdrawPlayer(player, job.getPrice());

													jb.addOwnedJob(job.getID());

													plugin.getBasicPluginManager().playSound(player,
															"PLAYER_BOUGHT_JOB");

													player.sendMessage(mg.getMessage("job_buy_message")
															.replaceAll("<price>", job.getPriceToDisplay())
															.replaceAll("<job>", job.getDisplay()));

													if (plugin.getFileManager().getConfigConfig()
															.getBoolean("ReOpenForUpdate")) {
														plugin.getJobsGUIManager().updateJobMenu(player);
													} else {
														Bukkit.getScheduler().runTask(plugin, () -> {
															player.closeInventory();
														});
													}

												}

												return;
											} else {
												if (mg.hasMessage("job_not_enough_money")) {
													player.sendMessage(mg.getMessage("job_not_enough_money")
															.replaceAll("<job>", job.getDisplay()));
												}
											}
											return;
										}

									}

								}

							});
						}

					}
				}.runTaskAsynchronously(plugin);

			} else if (m.isConfirmBuyMenu(player, job, title)) {
				event.setCancelled(true);

				new BukkitRunnable() {
					@Override
					public void run() {

						executeCustomItemClick(player, display, item, GUIType.BUY_CONFIRM);

						FileConfiguration c = plugin.getFileManager().getConfirmConfig();

						if (c.getBoolean("GUI_Buttons.Confirm.Enable")) {

							String displayc = c.getString("GUI_Buttons.Confirm.Display");

							String trans = plugin.getBasicPluginManager().toHex(displayc);

							if (display.equalsIgnoreCase(trans)) {
								if (plugin.getEco().getBalance(player) >= job.getPrice()
										|| plugin.getEco().getBalance(player) == job.getPrice()) {
									// paid

									plugin.getEco().withdrawPlayer(player, job.getPrice());

									jb.addOwnedJob(job.getID());

									plugin.getBasicPluginManager().playSound(player, "PLAYER_BOUGHT_JOB");

									player.sendMessage(mg.getMessage("job_buy_message")
											.replaceAll("<price>", job.getPriceToDisplay())
											.replaceAll("<job>", job.getDisplay()));

									if (plugin.getFileManager().getConfigConfig().getBoolean("ReOpenForUpdate")) {

										Bukkit.getScheduler().runTask(plugin, () -> {
											plugin.getJobsGUIManager().openJobsMenu(player, true);
										});

									} else {
										Bukkit.getScheduler().runTask(plugin, () -> {
											player.closeInventory();
										});
									}

									return;
								} else {
									if (mg.hasMessage("job_not_enough_money")) {
										player.sendMessage(mg.getMessage("job_not_enough_money").replaceAll("<job>",
												job.getDisplay()));
									}
								}
								return;
							}

						}
						if (c.getBoolean("GUI_Buttons.Cancel.Enable")) {

							String displayc = c.getString("GUI_Buttons.Cancel.Display");

							String trans = plugin.getBasicPluginManager().toHex(displayc);

							if (display.equalsIgnoreCase(trans)) {
								Bukkit.getScheduler().runTask(plugin, () -> {
									plugin.getJobsGUIManager().openJobsMenu(player, true);
								});

							}
						}
					}
				}.runTaskAsynchronously(plugin);

			} else if (m.isOptionsMenu(player, title, job.getDisplay())) {
				event.setCancelled(true);

				new BukkitRunnable() {
					@Override
					public void run() {

						executeCustomItemClick(player, display, item, GUIType.OPTIONS);

					}
				}.runTaskAsynchronously(plugin);
			}

		}

	}

	public void executeCustomItemClick(Player player, String display, ItemStack icon, GUIType gui) {

		BasicGUIManager m = plugin.getBasicGUIManager();
		JobsGUIManager m2 = plugin.getJobsGUIManager();
		BasicPluginManager d = plugin.getBasicPluginManager();
		MessageManager mg = plugin.getMessageManager();

		JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());

		UUID ID = player.getUniqueId();

		if (m.getGUIData().containsKey(ID)) {

			GUIType type = m.getGUIData().get(ID);

			ArrayList<CustomItem> custom_items = plugin.getLoadAndStoreGUIManager().getCustomItems(type);

			custom_items.forEach((item) -> {

				String idis = d.toHex(item.getDisplay());
				String trans = d.toHex(display);

				ItemStack c = item.getIcon(player.getName());

				if (c.getType().equals(icon.getType()) && idis.equalsIgnoreCase(trans)) {

					ArrayList<CustomItemAction> actions = item.getActions();

					if (actions.contains(CustomItemAction.CLOSE)) {
						Bukkit.getScheduler().runTask(plugin, () -> {
							player.closeInventory();
						});

						plugin.getBasicPluginManager().playSound(player, "PLAYER_CLOSE_BUTTON_ACTION");

					}
					if (actions.contains(CustomItemAction.LEAVE_JOB)) {

						String j = m.getJobData().get(ID);

						jb.removeCurrentJob(j);

						Bukkit.getScheduler().runTask(plugin, () -> {
							plugin.getJobsGUIManager().openJobsMenu(player, true);
						});

						plugin.getBasicPluginManager().playSound(player, "PLAYER_LEAVE_A_JOB");

					}
					if (actions.contains(CustomItemAction.LEAVE)) {

						if (jb.getCurrentJobs() != null && !jb.getCurrentJobs().isEmpty()) {
							if (mg.hasMessage("job_left_all")) {
								player.sendMessage(mg.getMessage("job_left_all"));
							}

							jb.removeAllCurrentJobs();

							plugin.getBasicPluginManager().playSound(player, "PLAYER_LEAVE_ALL_JOBS");

							if (type.equals(GUIType.JOBS)) {
								if (plugin.getFileManager().getConfigConfig().getBoolean("ReOpenForUpdate")) {
									plugin.getJobsGUIManager().updateJobMenu(player);
								} else {
									Bukkit.getScheduler().runTask(plugin, () -> {
										player.closeInventory();
									});
								}
							} else {
								Bukkit.getScheduler().runTask(plugin, () -> {
									player.closeInventory();
								});
							}
						} else {
							if (mg.hasMessage("job_nothing_to_leave")) {
								player.sendMessage(mg.getMessage("job_nothing_to_leave"));
							}
						}

					}
					if (actions.contains(CustomItemAction.GO_NEXT_PAGE)) {

						String d2 = m.getJobData().get(ID);

						Job job = plugin.getJobAPI().getLoadedJobsHash().get(d2);
						if (gui.equals(GUIType.REWARDS)) {
							if (m2.details_page_manager.containsKey(ID)) {

								int page = m2.details_page_manager.get(ID);

								int total = job.getEveryID().size();

								int startIndex = page * 35;
								int endIndex = Math.min(startIndex + 35, total) + 1;

								String used = null;
								if (plugin.getBasicGUIManager().getCurrentCate().containsKey(jb.getUUID())) {
									used = plugin.getBasicGUIManager().getCurrentCate().get(jb.getUUID());
								} else {
									used = plugin.getFileManager().getRewardsConfig().getString("DefaultSorting")
											.toUpperCase();
								}

								if (total >= endIndex) {

									plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), page + 1,
											used);

									plugin.getBasicPluginManager().playSound(player, "REWARDS_CHANGE_PAGE");

									return;
								} else {
									player.sendMessage(plugin.getMessageManager().getMessage("rewards_no_other_page"));
									return;
								}
							}
						}

					}
					if (actions.contains(CustomItemAction.GO_BACK_PAGE)) {

						String d2 = m.getJobData().get(ID);

						Job job = plugin.getJobAPI().getLoadedJobsHash().get(d2);
						if (gui.equals(GUIType.REWARDS)) {
							if (m2.details_page_manager.containsKey(ID)) {

								String used = null;
								if (plugin.getBasicGUIManager().getCurrentCate().containsKey(jb.getUUID())) {
									used = plugin.getBasicGUIManager().getCurrentCate().get(jb.getUUID());
								} else {
									used = plugin.getFileManager().getRewardsConfig().getString("DefaultSorting")
											.toUpperCase();
								}

								int page = m2.details_page_manager.get(ID);

								if (page == 1) {
									player.sendMessage(plugin.getMessageManager().getMessage("rewards_no_other_page"));
									return;
								} else {

									plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), page - 1,
											used);

									plugin.getBasicPluginManager().playSound(player, "REWARDS_CHANGE_PAGE");

									return;
								}
							}
						}

					}
					if (actions.contains(CustomItemAction.OPEN_MAIN)) {
						Bukkit.getScheduler().runTask(plugin, () -> {

							plugin.getBasicPluginManager().playSound(player, "RE_OPEN_MAIN_GUI");

							plugin.getJobsGUIManager().openJobsMenu(player, true);
						});

					}

					if (actions.contains(CustomItemAction.COMMANDS)) {
						Bukkit.getScheduler().runTask(plugin, () -> {

							List<String> cmds = item.getConfig()
									.getStringList("GUI_Items." + item.getID() + ".Commands");

							if (m.getJobData().containsKey(ID)) {

								String d2 = m.getJobData().get(ID);

								Job job = plugin.getJobAPI().getLoadedJobsHash().get(d2);

								for (String command : cmds) {
									player.performCommand(command.replaceAll("<job>", job.getID()));
								}

							} else {
								for (String command : cmds) {
									player.performCommand(command);
								}
							}

						});

					}

				}

			});

		}

	}

}