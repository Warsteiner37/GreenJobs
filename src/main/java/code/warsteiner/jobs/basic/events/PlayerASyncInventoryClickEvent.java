package code.warsteiner.jobs.basic.events;

import java.util.ArrayList;
import java.util.HashMap;
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
import code.warsteiner.jobs.commands.sub.LevelsSub;
import code.warsteiner.jobs.commands.sub.RewardsSub;
import code.warsteiner.jobs.manager.JobsGUIManager;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.utils.UtilManager;
import code.warsteiner.jobs.utils.enums.CustomItemAction;
import code.warsteiner.jobs.utils.enums.GUIType;
import code.warsteiner.jobs.utils.templates.CustomItem;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobLevel;
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

			String display = plugin.getBasicPluginManager().toHex(player, item.getItemMeta().getDisplayName());

			JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), ID);

			String d = m.getJobData().get(ID);

			Job job = plugin.getJobAPI().getLoadedJobsHash().get(d);

			if (m.isLevelsMenu(player, job, title)) {
				
				FileConfiguration levels = plugin.getFileManager().getLevelsConfig();
 
				event.setCancelled(true);
 
				new BukkitRunnable() {
					@Override
					public void run() {
						
						if(levels.getBoolean("BackToFirstPage.Enabled")) {
							
							ItemStack item_got = plugin.getItemManager().createOrGetItem("Levels.Back.To.First.Page",
									levels.getString("BackToFirstPage.Icon"), player.getName(), levels.getInt("BackToFirstPage.CustomModelData"));
						 
							String got = plugin.getBasicPluginManager().replaceAll(levels.getString("BackToFirstPage.Display"), player,
									job);
							
							if(display.equalsIgnoreCase(got) && item.getType().equals(item_got.getType())) {
								plugin.getJobsGUIManager().setLevelsItem(GUIType.LEVELS, 1, player.getOpenInventory(), player, jb, job);
								 
								plugin.getBasicPluginManager().playSound(player, "LEVELS_CHANGE_PAGE");
							}
							
						}
						 
						executeCustomItemClick(player, display, item, GUIType.LEVELS);
   
					}
				}.runTaskAsynchronously(plugin);

			}  else if (m.isRewardsMenu(player, job, title)) {

				event.setCancelled(true);
 
				new BukkitRunnable() {
					@Override
					public void run() {
						
						FileConfiguration rewards = plugin.getFileManager().getRewardsConfig();

						executeCustomItemClick(player, display, item, GUIType.REWARDS);

						String c1 = rewards.getString("SortModes.RANDOM.Icon");
						String c2 = rewards.getString("SortModes.NORMAL.Icon");

						Material i1 = plugin.getItemManager()
								.createOrGetItem("CatItemRewardsRandom", c1, player.getName(), 0).getType();
						Material i2 = plugin.getItemManager()
								.createOrGetItem("CatItemRewardsNormal", c2, player.getName(), 0).getType();

						List<String> modes = rewards.getStringList("SortModes.List");
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
 
							String player_name = player.getName() + "_" + job.getID();

							int page = 1;
							
							if (RewardsSub.getPages().containsKey(player_name)) {
								page = RewardsSub.getPages().get(player_name);
							}
							
							 
							
							if(RewardsSub.getPages().containsKey(player_name)) {
								page = RewardsSub.getPages().get(player_name);
							}
							
							if (used.size() != 0) {
								String next = used.get(0).toUpperCase();

								plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), page, next);

							} else {
								String def = rewards.getString("DefaultSorting").toUpperCase();

								plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), page, def);

							}

						} else if(rewards.getBoolean("BackToFirstPage.Enabled")) {
							
							ItemStack item_got = plugin.getItemManager().createOrGetItem("Rewards.Back.To.First.Page",
									rewards.getString("BackToFirstPage.Icon"), player.getName(), rewards.getInt("BackToFirstPage.CustomModelData"));
						 
							String got = plugin.getBasicPluginManager().replaceAll(rewards.getString("BackToFirstPage.Display"), player,
									job);
							
							if(display.equalsIgnoreCase(got) && item.getType().equals(item_got.getType())) {
								
								String current = plugin.getBasicGUIManager().getCurrentCate().get(jb.getUUID());
								
								plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), 1,
										current);
								
								plugin.getBasicPluginManager().playSound(player, "REWARDS_CHANGE_PAGE");
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

								String display2 = job.getDisplay(player);

								if (item2.getType().equals(item.getType()) && display.equalsIgnoreCase(display2)) {

									if (jb.getCurrentJobs().contains(job.getID())) {
										// lore.add("In");

										if (plugin.getFileManager().getConfigConfig().getBoolean("JobInfoGUI")) {
											Bukkit.getScheduler().runTaskLater(plugin, () -> {
												plugin.getJobsGUIManager().openOptionsMenu(player, job.getID(), false);
											}, 2);
										} else {

											if (mg.hasMessage("job_already_joined")) {
												player.sendMessage(mg.getMessage(player, "job_already_joined")
														.replaceAll("<job>", job.getDisplay(player)));
											}
										}

										return;
									} else if (jb.getOwnedJobs().contains(job.getID())) {

										if (jb.getCurrentJobs().size() >= jb.getMaxJobs() + 1) {
											if (mg.hasMessage("too_many_jobs")) {
												player.sendMessage(mg.getMessage(player, "too_many_jobs").replaceAll("<job>",
														job.getDisplay(player)));
											}
											return;
										} else {
											jb.addCurrentJob(job.getID(), date);

											if (mg.hasMessage("job_join_message")) {
												player.sendMessage(mg.getMessage(player, "job_join_message").replaceAll("<job>",
														job.getDisplay(player)));
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
												player.sendMessage(mg.getMessage(player, "job_is_free_message")
														.replaceAll("<job>", job.getDisplay(player)));
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

													Bukkit.getScheduler().runTaskLater(plugin, () -> {
														plugin.getJobsGUIManager().openConfPurchaseMenu(player,
																job.getID(), false);
													}, 2);

												} else {

													plugin.getEco().withdrawPlayer(player, job.getPrice());

													jb.addOwnedJob(job.getID());

													plugin.getBasicPluginManager().playSound(player,
															"PLAYER_BOUGHT_JOB");

													player.sendMessage(mg.getMessage(player, "job_buy_message")
															.replaceAll("<price>", job.getPriceToDisplay())
															.replaceAll("<job>", job.getDisplay(player)));

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
													player.sendMessage(mg.getMessage(player, "job_not_enough_money")
															.replaceAll("<job>", job.getDisplay(player)));
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

							String trans = plugin.getBasicPluginManager().toHex(player, displayc);

							if (display.equalsIgnoreCase(trans)) {
								if (plugin.getEco().getBalance(player) >= job.getPrice()
										|| plugin.getEco().getBalance(player) == job.getPrice()) {
									// paid

									plugin.getEco().withdrawPlayer(player, job.getPrice());

									jb.addOwnedJob(job.getID());

									plugin.getBasicPluginManager().playSound(player, "PLAYER_BOUGHT_JOB");

									player.sendMessage(mg.getMessage(player, "job_buy_message")
											.replaceAll("<price>", job.getPriceToDisplay())
											.replaceAll("<job>", job.getDisplay(player)));

									if (plugin.getFileManager().getConfigConfig().getBoolean("ReOpenForUpdate")) {

										Bukkit.getScheduler().runTaskLater(plugin, () -> {
											plugin.getJobsGUIManager().openJobsMenu(player, true);
										}, 2);

									} else {
										Bukkit.getScheduler().runTask(plugin, () -> {
											player.closeInventory();
										});
									}

									return;
								} else {
									if (mg.hasMessage("job_not_enough_money")) {
										player.sendMessage(mg.getMessage(player, "job_not_enough_money").replaceAll("<job>",
												job.getDisplay(player)));
									}
								}
								return;
							}

						}
						if (c.getBoolean("GUI_Buttons.Cancel.Enable")) {

							String displayc = c.getString("GUI_Buttons.Cancel.Display");

							String trans = plugin.getBasicPluginManager().toHex(player, displayc);

							if (display.equalsIgnoreCase(trans)) {
								Bukkit.getScheduler().runTask(plugin, () -> {
									plugin.getJobsGUIManager().openJobsMenu(player, true);
								});

							}
						}
					}
				}.runTaskAsynchronously(plugin);

			} else if (m.isOptionsMenu(player, title, job)) {
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

				String idis = d.toHex(player, item.getDisplay(player));
				String trans = d.toHex(player, display);

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

						Bukkit.getScheduler().runTaskLater(plugin, () -> {
							plugin.getJobsGUIManager().openJobsMenu(player, true);
						}, 2);

						plugin.getBasicPluginManager().playSound(player, "PLAYER_LEAVE_A_JOB");

					}
					if (actions.contains(CustomItemAction.LEAVE)) {

						if (jb.getCurrentJobs() != null && !jb.getCurrentJobs().isEmpty()) {
							if (mg.hasMessage("job_left_all")) {
								player.sendMessage(mg.getMessage(player, "job_left_all"));
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
								plugin.getBasicPluginManager().playSound(player, "PLAYER_ALREADY_LEFT_ALL_JOBS");
								player.sendMessage(mg.getMessage(player, "job_nothing_to_leave"));
							}
						}

					}
					if (actions.contains(CustomItemAction.GO_NEXT_PAGE)) {

						String d2 = m.getJobData().get(ID);

						Job job = plugin.getJobAPI().getLoadedJobsHash().get(d2);
						if (gui.equals(GUIType.REWARDS)) {
							
							String player_name = player.getName() + "_" + job.getID();
 
							if (RewardsSub.getPages().containsKey(player_name)) {
 
								int page = RewardsSub.getPages().get(player_name);

								int total = job.getEveryID().size();

								int startIndex = page * plugin.getFileManager().getRewardsConfig().getInt("ItemsPerPage") + 1;
							 
								String used = null;
								if (plugin.getBasicGUIManager().getCurrentCate().containsKey(jb.getUUID())) {
									used = plugin.getBasicGUIManager().getCurrentCate().get(jb.getUUID());
								} else {
									used = plugin.getFileManager().getRewardsConfig().getString("DefaultSorting")
											.toUpperCase();
								}

								if (total >= startIndex) {
									
									int next_page = page + 1;

									plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), next_page,
											used);

									plugin.getBasicPluginManager().playSound(player, "REWARDS_CHANGE_PAGE");

									return;
								} else {
									player.sendMessage(plugin.getMessageManager().getMessage(player, "rewards_no_other_page"));
									return;
								}
							}
						} else if (gui.equals(GUIType.LEVELS)) {
							 
							FileConfiguration cfg = plugin.getFileManager().getLevelsConfig();
 
							HashMap<String, Integer> p = LevelsSub.getPages();
							
							String player_name = player.getName() + "_" + job.getID();
						 
							if (p.containsKey(player_name)) {
							 
								HashMap<Integer, JobLevel> levels = job.getLevels();

								List<String> slots = cfg.getStringList("ItemPlaces");

								int itemsPerPage = slots.size();
								 
								int page = p.get(player_name);

								int total = levels.size();

								int startIndex = page * itemsPerPage + 1;
							 
								if (total >= startIndex) {
								 
									int next_page = page + 1;

									plugin.getJobsGUIManager().setLevelsItem(GUIType.LEVELS, next_page, player.getOpenInventory(), player, jb, job);

									plugin.getBasicPluginManager().playSound(player, "LEVELS_CHANGE_PAGE");

									return;
								} else {
									player.sendMessage(plugin.getMessageManager().getMessage(player, "levels_no_other_page"));
									return;
								}
							}
						}

					}
					if (actions.contains(CustomItemAction.GO_BACK_PAGE)) {

						String d2 = m.getJobData().get(ID);

						Job job = plugin.getJobAPI().getLoadedJobsHash().get(d2);
						if (gui.equals(GUIType.REWARDS)) {
							String player_name = player.getName() + "_" + job.getID();
							 
							if (RewardsSub.getPages().containsKey(player_name)) {
 
								int page = RewardsSub.getPages().get(player_name);

								String used = null;
								if (plugin.getBasicGUIManager().getCurrentCate().containsKey(jb.getUUID())) {
									used = plugin.getBasicGUIManager().getCurrentCate().get(jb.getUUID());
								} else {
									used = plugin.getFileManager().getRewardsConfig().getString("DefaultSorting")
											.toUpperCase();
								}
 
								if (page == 1) {
									player.sendMessage(plugin.getMessageManager().getMessage(player, "rewards_no_other_page"));
									return;
								} else {

									plugin.getJobsGUIManager().updateBlockRewardsGUI(player, job.getID(), page - 1,
											used);

									plugin.getBasicPluginManager().playSound(player, "REWARDS_CHANGE_PAGE");

									return;
								}
							}
						} else if (gui.equals(GUIType.LEVELS)) {
							 
							HashMap<String, Integer> p = LevelsSub.getPages();
							
							String player_name = player.getName() + "_" + job.getID();
							
							if (p.containsKey(player_name)) {
 
								int page = p.get(player_name);

								if (page == 1) {
									player.sendMessage(plugin.getMessageManager().getMessage(player, "levels_no_other_page"));
									return;
								} else {

									int next_page = page - 1;
							 
									plugin.getJobsGUIManager().setLevelsItem(GUIType.LEVELS, next_page, player.getOpenInventory(), player, jb, job);
 
									plugin.getBasicPluginManager().playSound(player, "LEVELS_CHANGE_PAGE");

									return;
								}
							}
						}

					}
					if (actions.contains(CustomItemAction.OPEN_MAIN)) {
						Bukkit.getScheduler().runTaskLater(plugin, () -> {

							plugin.getBasicPluginManager().playSound(player, "RE_OPEN_MAIN_GUI");

							plugin.getJobsGUIManager().openJobsMenu(player, true);
						}, 2);

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
