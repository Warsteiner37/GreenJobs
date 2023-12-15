package code.warsteiner.jobs.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.basic.BasicGUIManager;
import code.warsteiner.jobs.commands.sub.LevelsSub;
import code.warsteiner.jobs.commands.sub.RewardsSub;
import code.warsteiner.jobs.utils.enums.GUIType;
import code.warsteiner.jobs.utils.templates.CustomItem;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobID;
import code.warsteiner.jobs.utils.templates.JobLevel;
import code.warsteiner.jobs.utils.templates.JobStats;
import code.warsteiner.jobs.utils.templates.JobsPlayer;
import code.warsteiner.jobs.utils.templates.PlaceholderItem;

public class JobsGUIManager {

	private GreenJobs plugin = GreenJobs.getPlugin();

	public void openLevelsMenu(Player player, int page, String job, boolean r) {

		JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());
		BasicGUIManager b = plugin.getBasicGUIManager();

		if (!r) {
			plugin.getBasicPluginManager().playSound(player, "OPEN_LEVELS_GUI");
		}

		String name = player.getName();

		GUIType type = GUIType.LEVELS;

		Inventory inv = plugin.getBasicGUIManager().openInventory(player, type, null, job.toUpperCase(), null);

		plugin.executor.submit(() -> {

			setOther(player, type, inv, name);

			setLevelsItem(type, page, inv, player, jb, job.toUpperCase());

		});

		player.openInventory(inv);
	}

	public void setLevelsItem(GUIType type, int page, InventoryView inv, Player player, JobsPlayer j, Job job) {

		FileConfiguration cfg = plugin.getFileManager().getLevelsConfig();

		List<String> slots = cfg.getStringList("ItemPlaces");

		int itemsPerPage = slots.size();

		HashMap<String, Integer> p = LevelsSub.getPages();

		String player_name = player.getName() + "_" + job.getID();

		if (p.containsKey(player_name)) {
			LevelsSub.getPages().remove(player_name);
		}

		LevelsSub.getPages().put(player_name, page);

		HashMap<Integer, JobLevel> d = job.getLevels();

		int total = d.size();

		int startIndex = (page - 1) * itemsPerPage;
		int endIndex = Math.min(startIndex + itemsPerPage, total);

		int current = j.getJobStats().get(job.getID()).getLevel();

		int where = 0;

		for (String slot : slots) {

			int wh = Integer.valueOf(slot);

			inv.setItem(wh, null);

		}

		if (cfg.getBoolean("BackToFirstPage.Enabled")) {

			int show_item = cfg.getInt("BackToFirstPage.ShowFromPage");

			inv.setItem(cfg.getInt("BackToFirstPage.Slot"), null);

			if (page == show_item || page >= show_item) {
				ItemStack item = plugin.getItemManager().createOrGetItem("Levels.Back.To.First.Page",
						cfg.getString("BackToFirstPage.Icon"), player.getName(),
						cfg.getInt("BackToFirstPage.CustomModelData"));
				ItemMeta meta = item.getItemMeta();

				String display = plugin.getBasicPluginManager().replaceAll(cfg.getString("BackToFirstPage.Display"),
						player, job);

				meta.setDisplayName(display);

				ArrayList<String> l = new ArrayList<String>();

				for (String line : cfg.getStringList("BackToFirstPage.Lore")) {
					l.add(plugin.getBasicPluginManager().replaceAll(line, player, job));
				}

				meta.setLore(l);

				item.setItemMeta(meta);

				inv.setItem(cfg.getInt("BackToFirstPage.Slot"), item);
			}

		}

		for (int i = startIndex; i < endIndex; i++) {
			if (i != 0) {

				if (job.getLevels().size() >= i || job.getLevels().size() == i) {
					JobLevel get = d.get(i);

					int check = get.getLevel();

					if (plugin.getLevelAPI().isLastLevelByInt(job.getID(), get.getLevel())) {

						boolean enchanted_last = false;
						boolean desc_last = false;

						String display = cfg.getString("LastLevelIcon.Display");
						String icon = cfg.getString("LastLevelIcon.Icon");
						List<String> lore = null;

						if (plugin.getLevelAPI().isMaxLevel(j, job.getID())) {

							enchanted_last = true;
							desc_last = true;
							lore = cfg.getStringList("LastLevelIcon.Lore");

						} else if (current + 1 == check) {
							lore = cfg.getStringList("LevelLore.Currently");
						} else {
							lore = cfg.getStringList("LevelLore.Locked");
						}

						ItemStack item = plugin.getItemManager().createOrGetItem(
								"ItemMaterials.LastIcon." + job.getID(), icon, player.getName(),
								cfg.getInt("LastLevelIcon.CustomModelData"));

						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(plugin.getBasicPluginManager().toHex(player, display));

						item.setAmount(get.getLevel());

						ArrayList<String> l = new ArrayList<String>();

						if (enchanted_last) {
							meta.addEnchant(Enchantment.LUCK, 1, false);
						}

						if (lore != null) {
							for (String line : lore) {
								String tr = plugin.getBasicPluginManager().replaceAll(line, player, job);

								l.add(tr);

							}
						}

						if (desc_last) {
							if (!get.getDescription().isEmpty()) {

								for (String line : get.getDescription()) {

									String tr = plugin.getBasicPluginManager().replaceAll(line, player, job);

									l.add(tr);
								}
							}
						}

						meta.setLore(l);

						item.setItemMeta(meta);

						inv.setItem(Integer.valueOf(slots.get(where)), item);

					} else {

						ItemStack item = null;

						List<String> lore = null;

						boolean enchanted = false;
						boolean desc = false;

						if (current + 1 == check) {
							item = plugin.getItemManager().createOrGetItem("ItemMaterials.Currently",
									cfg.getString("ItemMaterials.Currently"), player.getName(),
									cfg.getInt("ItemMaterials.CurrentlyCustomModel"));
							lore = cfg.getStringList("LevelLore.Currently");
							desc = cfg.getBoolean("LevelDesc.Currently");

						} else if (current >= check) {
							item = plugin.getItemManager().createOrGetItem("ItemMaterials.Reached",
									cfg.getString("ItemMaterials.Reached"), player.getName(),
									cfg.getInt("ItemMaterials.ReachedCustomModel"));
							lore = cfg.getStringList("LevelLore.Reached");

							if (cfg.getBoolean("EnchantAlreadyReachedLevels")) {
								enchanted = true;
							}

							desc = cfg.getBoolean("LevelDesc.Reached");

						} else {
							item = plugin.getItemManager().createOrGetItem("ItemMaterials.Locked",
									cfg.getString("ItemMaterials.Locked"), player.getName(),
									cfg.getInt("ItemMaterials.LockedCustomModel"));
							lore = cfg.getStringList("LevelLore.Locked");
							desc = cfg.getBoolean("LevelDesc.Locked");

						}

						String display = plugin.getBasicPluginManager()
								.replaceAll(cfg.getString("LevelDisplay"), player, job)
								.replaceAll("<gui_level>", "" + get.getLevel());

						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(display);

						item.setAmount(get.getLevel());

						ArrayList<String> l = new ArrayList<String>();

						if (enchanted) {
							meta.addEnchant(Enchantment.LUCK, 1, false);
						}

						if (lore != null) {
							for (String line : lore) {
								String tr = plugin.getBasicPluginManager().replaceAll(line, player, job);

								l.add(tr);

							}
						}

						if (desc) {
							if (!get.getDescription().isEmpty()) {

								for (String line : get.getDescription()) {

									String tr = plugin.getBasicPluginManager().replaceAll(line, player, job);

									l.add(tr);
								}
							}
						}

						meta.setLore(l);

						item.setItemMeta(meta);

						inv.setItem(Integer.valueOf(slots.get(where)), item);
					}

				}

			} else {

				ItemStack item = plugin.getItemManager().createOrGetItem("InfoItem.Icon",
						cfg.getString("InfoItem.Icon"), player.getName(), cfg.getInt("InfoItem.CustomModelData"));
				ItemMeta meta = item.getItemMeta();

				String display = plugin.getBasicPluginManager().replaceAll(cfg.getString("InfoItem.Display"), player,
						job);

				meta.setDisplayName(display);

				ArrayList<String> l = new ArrayList<String>();

				for (String line : cfg.getStringList("InfoItem.Lore")) {
					l.add(plugin.getBasicPluginManager().replaceAll(line, player, job));
				}

				meta.setLore(l);

				item.setItemMeta(meta);

				inv.setItem(Integer.valueOf(slots.get(where)), item);
			}

			where++;
		}
	}

	public void setLevelsItem(GUIType type, int page, Inventory inv, Player player, JobsPlayer j, String jb) {

		FileConfiguration cfg = plugin.getFileManager().getLevelsConfig();

		List<String> slots = cfg.getStringList("ItemPlaces");

		Job job = plugin.getJobAPI().getLoadedJobsHash().get(jb);

		int itemsPerPage = slots.size();

		HashMap<String, Integer> p = LevelsSub.getPages();

		String player_name = player.getName() + "_" + job.getID();

		if (p.containsKey(player_name)) {
			LevelsSub.getPages().remove(player_name);
		}

		LevelsSub.getPages().put(player_name, page);

		HashMap<Integer, JobLevel> d = job.getLevels();

		int current = j.getJobStats().get(jb).getLevel();

		int total = d.size();

		int startIndex = (page - 1) * itemsPerPage;
		int endIndex = Math.min(startIndex + itemsPerPage, total);

		int where = 0;

		for (String slot : slots) {

			int wh = Integer.valueOf(slot);

			inv.setItem(wh, null);

		}

		if (cfg.getBoolean("BackToFirstPage.Enabled")) {

			inv.setItem(cfg.getInt("BackToFirstPage.Slot"), null);

			int show_item = cfg.getInt("BackToFirstPage.ShowFromPage");

			if (page == show_item || page >= show_item) {
				ItemStack item = plugin.getItemManager().createOrGetItem("Levels.Back.To.First.Page",
						cfg.getString("BackToFirstPage.Icon"), player.getName(),
						cfg.getInt("BackToFirstPage.CustomModelData"));
				ItemMeta meta = item.getItemMeta();

				String display = plugin.getBasicPluginManager().replaceAll(cfg.getString("BackToFirstPage.Display"),
						player, job);

				meta.setDisplayName(display);

				ArrayList<String> l = new ArrayList<String>();

				for (String line : cfg.getStringList("BackToFirstPage.Lore")) {
					l.add(plugin.getBasicPluginManager().replaceAll(line, player, job));
				}

				meta.setLore(l);

				item.setItemMeta(meta);

				inv.setItem(cfg.getInt("BackToFirstPage.Slot"), item);
			}

		}

		for (int i = startIndex; i < endIndex; i++) {
			if (i != 0) {

				if (job.getLevels().size() >= i || job.getLevels().size() == i) {
					JobLevel get = d.get(i);

					int check = get.getLevel();

					if (plugin.getLevelAPI().isLastLevelByInt(job.getID(), get.getLevel())) {

						boolean enchanted_last = false;
						boolean desc_last = false;

						String display = cfg.getString("LastLevelIcon.Display");
						String icon = cfg.getString("LastLevelIcon.Icon");
						List<String> lore = null;

						if (plugin.getLevelAPI().isMaxLevel(j, job.getID())) {

							enchanted_last = true;
							desc_last = true;
							lore = cfg.getStringList("LastLevelIcon.Lore");

						} else if (current + 1 == check) {
							lore = cfg.getStringList("LevelLore.Currently");
						} else {
							lore = cfg.getStringList("LevelLore.Locked");
						}

						ItemStack item = plugin.getItemManager().createOrGetItem(
								"ItemMaterials.LastIcon." + job.getID(), icon, player.getName(),
								cfg.getInt("LastLevelIcon.CustomModelData"));

						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(plugin.getBasicPluginManager().toHex(player, display));

						item.setAmount(get.getLevel());

						ArrayList<String> l = new ArrayList<String>();

						if (enchanted_last) {
							meta.addEnchant(Enchantment.LUCK, 1, false);
						}

						if (lore != null) {
							for (String line : lore) {
								String tr = plugin.getBasicPluginManager().replaceAll(line, player, job);

								l.add(tr);

							}
						}

						if (desc_last) {
							if (!get.getDescription().isEmpty()) {

								for (String line : get.getDescription()) {

									String tr = plugin.getBasicPluginManager().replaceAll(line, player, job);

									l.add(tr);
								}
							}
						}

						meta.setLore(l);

						item.setItemMeta(meta);

						inv.setItem(Integer.valueOf(slots.get(where)), item);

					} else {

						ItemStack item = null;

						List<String> lore = null;

						boolean enchanted = false;
						boolean desc = false;

						if (current + 1 == check) {
							item = plugin.getItemManager().createOrGetItem("ItemMaterials.Currently",
									cfg.getString("ItemMaterials.Currently"), player.getName(),
									cfg.getInt("ItemMaterials.CurrentlyCustomModel"));
							lore = cfg.getStringList("LevelLore.Currently");
							desc = cfg.getBoolean("LevelDesc.Currently");

						} else if (current >= check) {
							item = plugin.getItemManager().createOrGetItem("ItemMaterials.Reached",
									cfg.getString("ItemMaterials.Reached"), player.getName(),
									cfg.getInt("ItemMaterials.ReachedCustomModel"));
							lore = cfg.getStringList("LevelLore.Reached");

							if (cfg.getBoolean("EnchantAlreadyReachedLevels")) {
								enchanted = true;
							}

							desc = cfg.getBoolean("LevelDesc.Reached");

						} else {
							item = plugin.getItemManager().createOrGetItem("ItemMaterials.Locked",
									cfg.getString("ItemMaterials.Locked"), player.getName(),
									cfg.getInt("ItemMaterials.LockedCustomModel"));
							lore = cfg.getStringList("LevelLore.Locked");
							desc = cfg.getBoolean("LevelDesc.Locked");

						}

						String display = plugin.getBasicPluginManager()
								.replaceAll(cfg.getString("LevelDisplay"), player, job)
								.replaceAll("<gui_level>", "" + get.getLevel());

						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(display);

						item.setAmount(get.getLevel());

						ArrayList<String> l = new ArrayList<String>();

						if (enchanted) {
							meta.addEnchant(Enchantment.LUCK, 1, false);
						}

						if (lore != null) {
							for (String line : lore) {
								String tr = plugin.getBasicPluginManager().replaceAll(line, player, job);

								l.add(tr);

							}
						}

						if (desc) {
							if (!get.getDescription().isEmpty()) {

								for (String line : get.getDescription()) {

									String tr = plugin.getBasicPluginManager().replaceAll(line, player, job);

									l.add(tr);
								}
							}
						}

						meta.setLore(l);

						item.setItemMeta(meta);

						inv.setItem(Integer.valueOf(slots.get(where)), item);
					}

				}

			} else {

				ItemStack item = plugin.getItemManager().createOrGetItem("InfoItem.Icon",
						cfg.getString("InfoItem.Icon"), player.getName(), cfg.getInt("InfoItem.CustomModelData"));
				ItemMeta meta = item.getItemMeta();

				String display = plugin.getBasicPluginManager().replaceAll(cfg.getString("InfoItem.Display"), player,
						job);

				meta.setDisplayName(display);

				ArrayList<String> l = new ArrayList<String>();

				for (String line : cfg.getStringList("InfoItem.Lore")) {
					l.add(plugin.getBasicPluginManager().replaceAll(line, player, job));
				}

				meta.setLore(l);

				item.setItemMeta(meta);

				inv.setItem(Integer.valueOf(slots.get(where)), item);
			}

			where++;

		}
	}

	public void openBlockRewardsMenu(Player player, String job_name, int page, String sd, boolean r) {

		LoadAndStoreGUIManager d = plugin.getLoadAndStoreGUIManager();
		JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());
		BasicGUIManager b = plugin.getBasicGUIManager();

		FileConfiguration file = plugin.getFileManager().getRewardsConfig();

		if (!r) {
			plugin.getBasicPluginManager().playSound(player, "OPEN_REWARDS_GUI");
		}

		String player_name = player.getName() + "_" + job_name;

		if (RewardsSub.getPages().containsKey(player_name)) {
			RewardsSub.getPages().remove(player_name);
		}

		RewardsSub.getPages().put(player_name, page);

		String name = player.getName();

		GUIType type = GUIType.REWARDS;

		Inventory inv = plugin.getBasicGUIManager().openInventory(player, type, null, job_name, sd);

		plugin.executor.submit(() -> {

			setOther(player, type, inv, name);

			setItemsSorted(type, page, file.getInt("ItemsPerPage"), inv, player, sd, job_name, jb);

			setCatItem(type, page, file.getInt("ItemsPerPage"), inv, player, sd, job_name, jb);
		});

		player.openInventory(inv);
	}

	public void updateBlockRewardsGUI(Player player, String job_name, int page, String sd) {

		plugin.executor.submit(() -> {

			JobsGUIManager m2 = plugin.getJobsGUIManager();

			LoadAndStoreGUIManager d = plugin.getLoadAndStoreGUIManager();
			JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());
			BasicGUIManager b = plugin.getBasicGUIManager();

			FileConfiguration file = plugin.getFileManager().getRewardsConfig();

			String name = player.getName();

			GUIType type = GUIType.REWARDS;

			Inventory inv = player.getOpenInventory().getTopInventory();

			inv.clear();

			plugin.getBasicGUIManager().getCurrentCate().put(player.getUniqueId(), sd);

			setOther(player, type, inv, name);

			setItemsSorted(type, page, file.getInt("ItemsPerPage"), inv, player, sd, job_name, jb);

			setCatItem(type, page, file.getInt("ItemsPerPage"), inv, player, sd, job_name, jb);
		});

	}

	public void setCatItem(GUIType type, int page, int perpage, InventoryView inv, Player player, String sorted,
			String jb, JobsPlayer j) {

		FileConfiguration file = plugin.getFileManager().getRewardsConfig();

		String display_start = file.getString("SortModes.Display");
		int slot = file.getInt("SortModes.Slot");

		if (sorted.equalsIgnoreCase("RANDOM")) {

			String cat = file.getString("SortModes.RANDOM.Display");
			String oc = file.getString("SortModes.RANDOM.Icon");
			List<String> lored = file.getStringList("SortModes.RANDOM.Lore");

			ItemStack item = plugin.getItemManager().createOrGetItem("CatItemRewardsRandom", oc, player.getName(), 0);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(plugin.getBasicPluginManager().toHex(player, display_start + cat));

			ArrayList<String> lore = new ArrayList<String>();

			for (String line : lored) {
				lore.add(plugin.getBasicPluginManager().toHex(player, line));
			}

			meta.setLore(lore);

			item.setItemMeta(meta);

			inv.setItem(slot, item);

		} else if (sorted.equalsIgnoreCase("NORMAL")) {

			String cat = file.getString("SortModes.NORMAL.Display");
			String oc = file.getString("SortModes.NORMAL.Icon");
			List<String> lored = file.getStringList("SortModes.NORMAL.Lore");

			ItemStack item = plugin.getItemManager().createOrGetItem("CatItemRewardsNormal", oc, player.getName(), 0);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(plugin.getBasicPluginManager().toHex(player, display_start + cat));

			ArrayList<String> lore = new ArrayList<String>();

			for (String line : lored) {
				lore.add(plugin.getBasicPluginManager().toHex(player, line));
			}

			meta.setLore(lore);

			item.setItemMeta(meta);

			inv.setItem(slot, item);

		}
	}

	public void setCatItem(GUIType type, int page, int perpage, Inventory inv, Player player, String sorted, String jb,
			JobsPlayer j) {

		FileConfiguration file = plugin.getFileManager().getRewardsConfig();

		String display_start = file.getString("SortModes.Display");
		int slot = file.getInt("SortModes.Slot");

		if (sorted.equalsIgnoreCase("RANDOM")) {

			String cat = file.getString("SortModes.RANDOM.Display");
			String oc = file.getString("SortModes.RANDOM.Icon");
			List<String> lored = file.getStringList("SortModes.RANDOM.Lore");

			ItemStack item = plugin.getItemManager().createOrGetItem("CatItemRewardsRandom", oc, player.getName(), 0);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(plugin.getBasicPluginManager().toHex(player, display_start + cat));

			ArrayList<String> lore = new ArrayList<String>();

			for (String line : lored) {
				lore.add(plugin.getBasicPluginManager().toHex(player, line));
			}

			meta.setLore(lore);

			item.setItemMeta(meta);

			inv.setItem(slot, item);

		} else if (sorted.equalsIgnoreCase("NORMAL")) {

			String cat = file.getString("SortModes.NORMAL.Display");
			String oc = file.getString("SortModes.NORMAL.Icon");
			List<String> lored = file.getStringList("SortModes.NORMAL.Lore");

			ItemStack item = plugin.getItemManager().createOrGetItem("CatItemRewardsNormal", oc, player.getName(), 0);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(plugin.getBasicPluginManager().toHex(player, display_start + cat));

			ArrayList<String> lore = new ArrayList<String>();

			for (String line : lored) {
				lore.add(plugin.getBasicPluginManager().toHex(player, line));
			}

			meta.setLore(lore);

			item.setItemMeta(meta);

			inv.setItem(slot, item);

		}
	}

	public void setItemsSorted(GUIType type, int page, int perpage, Inventory inv, Player player, String sorted,
			String jb, JobsPlayer j) {

		Job job = plugin.getJobAPI().getLoadedJobsHash().get(jb);

		FileConfiguration cfg = plugin.getFileManager().getRewardsConfig();

		int itemsPerPage = perpage;

		String player_name = player.getName() + "_" + job.getID();

		if (RewardsSub.getPages().containsKey(player_name)) {
			RewardsSub.getPages().remove(player_name);
		}

		RewardsSub.getPages().put(player_name, page);

		if (cfg.getBoolean("BackToFirstPage.Enabled")) {

			int show_item = cfg.getInt("BackToFirstPage.ShowFromPage");

			inv.setItem(cfg.getInt("BackToFirstPage.Slot"), null);

			if (page == show_item || page >= show_item) {
				ItemStack item = plugin.getItemManager().createOrGetItem("Rewards.Back.To.First.Page",
						cfg.getString("BackToFirstPage.Icon"), player.getName(),
						cfg.getInt("BackToFirstPage.CustomModelData"));
				ItemMeta meta = item.getItemMeta();

				String display = plugin.getBasicPluginManager().replaceAll(cfg.getString("BackToFirstPage.Display"),
						player, job);

				meta.setDisplayName(display);

				ArrayList<String> l = new ArrayList<String>();

				for (String line : cfg.getStringList("BackToFirstPage.Lore")) {
					l.add(plugin.getBasicPluginManager().replaceAll(line, player, job));
				}

				meta.setLore(l);

				item.setItemMeta(meta);

				inv.setItem(cfg.getInt("BackToFirstPage.Slot"), item);
			}

		}

		if (sorted.equalsIgnoreCase("RANDOM")) {

			HashMap<String, JobID> list = job.getEveryID();

			int total = list.size();

			int startIndex = (page - 1) * itemsPerPage;
			int endIndex = Math.min(startIndex + itemsPerPage, total);

			for (int i = startIndex; i < endIndex; i++) {

				String get = job.getArrayList2Sorted().get(i);

				JobID real = job.getEveryID().get(get);

				ItemStack item = real.getItemToDisplayInRewards();
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(real.getDisplayInRewards(player));

				ArrayList<String> lore = new ArrayList<String>();

				for (String line : real.getLore()) {
					lore.add(plugin.getBasicPluginManager().toHex(player, line

							.replaceAll("<money>", "" + real.getMoneyReward()).replaceAll("<exp>", "" + real.getExp())
							.replaceAll("<points>", "" + real.getPoints()).replaceAll("<chance>", "" + real.getChance())

					));
				}

				if (j.getOwnedJobs().contains(jb.toUpperCase())) {

					JobStats stats = j.getJobStats().get(jb.toUpperCase());

					for (String line : real.getLoreWhenOwnJob()) {
						lore.add(plugin.getBasicPluginManager().toHex(player, line

								.replaceAll("<times>", "" + stats.getTimesBrokenBlockIfExist(get))
								.replaceAll("<earned_today>", "" + stats.getEarningsByBlockToday(get))
								.replaceAll("<earned>", "" + stats.getEarnedMoneyByBlockID(get))

						));
					}
				}

				meta.setLore(lore);

				item.setItemMeta(meta);

				inv.addItem(item);

			}

		} else if (sorted.equalsIgnoreCase("NORMAL")) {

			ArrayList<String> list = job.getArraySortedAfterNumbers();

			int total = list.size();

			int startIndex = (page - 1) * itemsPerPage;
			int endIndex = Math.min(startIndex + itemsPerPage, total);

			for (int i = startIndex; i < endIndex; i++) {

				String get = job.getArraySortedAfterNumbers().get(i);

				JobID real = job.getEveryID().get(get);

				ItemStack item = real.getItemToDisplayInRewards();
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(real.getDisplayInRewards(player));

				ArrayList<String> lore = new ArrayList<String>();

				for (String line : real.getLore()) {
					lore.add(plugin.getBasicPluginManager().toHex(player, line

							.replaceAll("<money>", "" + real.getMoneyReward()).replaceAll("<exp>", "" + real.getExp())
							.replaceAll("<points>", "" + real.getPoints()).replaceAll("<chance>", "" + real.getChance())

					));
				}

				if (j.getOwnedJobs().contains(jb.toUpperCase())) {

					JobStats stats = j.getJobStats().get(jb.toUpperCase());

					for (String line : real.getLoreWhenOwnJob()) {
						lore.add(plugin.getBasicPluginManager().toHex(player, line

								.replaceAll("<times>", "" + stats.getTimesBrokenBlockIfExist(get))
								.replaceAll("<earned_today>", "" + stats.getEarningsByBlockToday(get))
								.replaceAll("<earned>", "" + stats.getEarnedMoneyByBlockID(get))

						));
					}
				}

				meta.setLore(lore);

				item.setItemMeta(meta);

				inv.addItem(item);

			}

		}

	}

	public void updateConfPurchasMenu(Player player, String name) {

		plugin.executor.submit(() -> {

			Inventory inv = player.getOpenInventory().getTopInventory();
			;

			inv.clear();

			GUIType type = GUIType.BUY_CONFIRM;

			setOther(player, type, inv, name);

			// buttons
			setButtons(player, name, inv);

		});

	}

	public void openConfPurchaseMenu(Player player, String string, boolean r) {

		LoadAndStoreGUIManager d = plugin.getLoadAndStoreGUIManager();
		JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());

		String name = player.getName();

		GUIType type = GUIType.BUY_CONFIRM;

		if (!r) {
			plugin.getBasicPluginManager().playSound(player, "OPEN_CONFIRM_GUI");
		}

		Inventory inv = plugin.getBasicGUIManager().openInventory(player, type, null, string, null);

		plugin.executor.submit(() -> {

			setOther(player, type, inv, name);

			// buttons
			setButtons(player, name, inv);

		});

		player.openInventory(inv);

	}

	public void setButtons(Player player, String name, Inventory inv) {
		FileConfiguration c = plugin.getFileManager().getConfirmConfig();

		if (c.getBoolean("GUI_Buttons.Confirm.Enable")) {

			String id = c.getString("GUI_Buttons.Confirm.Icon");
			String display = c.getString("GUI_Buttons.Confirm.Display");
			int slot = c.getInt("GUI_Buttons.Confirm.Slot");
			int data = c.getInt("GUI_Buttons.Confirm.CustomModelData");

			ItemStack item = plugin.getItemManager().createOrGetItem("ConfirmGUI_Button1", id, name, data);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(plugin.getBasicPluginManager().toHex(player, display));

			ArrayList<String> lore = new ArrayList<String>();

			if (c.contains("GUI_Buttons.Confirm.Lore")) {
				for (String line : c.getStringList("GUI_Buttons.Confirm.Lore")) {
					lore.add(plugin.getBasicPluginManager().toHex(player, line));
				}
			}

			meta.setLore(lore);

			item.setItemMeta(meta);

			inv.setItem(slot, item);
		}

		if (c.getBoolean("GUI_Buttons.Cancel.Enable")) {

			String id = c.getString("GUI_Buttons.Cancel.Icon");
			String display = c.getString("GUI_Buttons.Cancel.Display");
			int slot = c.getInt("GUI_Buttons.Cancel.Slot");
			int data = c.getInt("GUI_Buttons.Cancel.CustomModelData");

			ItemStack item = plugin.getItemManager().createOrGetItem("ConfirmGUI_Button2", id, name, data);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(plugin.getBasicPluginManager().toHex(player, display));

			ArrayList<String> lore = new ArrayList<String>();

			if (c.contains("GUI_Buttons.Cancel.Lore")) {
				for (String line : c.getStringList("GUI_Buttons.Cancel.Lore")) {
					lore.add(plugin.getBasicPluginManager().toHex(player, line));
				}
			}

			meta.setLore(lore);

			item.setItemMeta(meta);

			inv.setItem(slot, item);

		}
	}

	public void openOptionsMenu(Player player, String job, boolean r) {

		LoadAndStoreGUIManager d = plugin.getLoadAndStoreGUIManager();

		if (!r) {
			plugin.getBasicPluginManager().playSound(player, "OPEN_OPTIONS_GUI");
		}

		String name = player.getName();

		GUIType type = GUIType.OPTIONS;

		Inventory inv = plugin.getBasicGUIManager().openInventory(player, type, null, job, null);

		plugin.executor.submit(() -> {

			setOther(player, type, inv, name);

		});

		player.openInventory(inv);

	}

	public void updateOptionsGUI(Player player) {

		plugin.executor.submit(() -> {
			Inventory open = player.getOpenInventory().getTopInventory();

			String name = player.getName();

			GUIType type = GUIType.OPTIONS;

			setOther(player, type, open, name);

		});

	}

	public void openJobsMenu(Player player, boolean r) {

		LoadAndStoreGUIManager d = plugin.getLoadAndStoreGUIManager();
		JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());

		String name = player.getName();

		GUIType type = GUIType.JOBS;

		if (!r) {
			plugin.getBasicPluginManager().playSound(player, "OPEN_JOBS_GUI");
		}

		Inventory inv = plugin.getBasicGUIManager().openInventory(player, type, null, null, null);

		plugin.executor.submit(() -> {

			setOther(player, type, inv, name);

			setJobItems(type, inv, name, jb);
		});

		player.openInventory(inv);

	}

	public void updateJobMenu(Player player) {

		plugin.executor.submit(() -> {
			String title = player.getOpenInventory().getTitle();
			Inventory open = player.getOpenInventory().getTopInventory();

			String name = player.getName();
			JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(name, player.getUniqueId());

			if (plugin.getBasicGUIManager().isJobsMenu(player, title)) {

				GUIType type = GUIType.JOBS;

				setOther(player, type, open, name);

				setJobItems(type, open, name, jb);

			}
		});

	}

	public void setJobItems(GUIType type, Inventory inv, String name, JobsPlayer jb) {
 
		if (plugin.getJobAPI().getLoadedJobsArray() != null) {
 
			plugin.getJobAPI().getLoadedJobsHash().forEach((id, job) -> {
 
				Player player = Bukkit.getPlayer(jb.getUUID());

				ItemStack item = job.getIcon();

				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(job.getDisplay(player));

				ArrayList<String> lore = new ArrayList<String>();

				if (job.getDescription() != null) {
					lore.add(plugin.getBasicPluginManager().toHex(player, job.getDescription()));
					lore.add("§8");
				}

				if (jb.getCurrentJobs().contains(job.getID())) {

					ArrayList<String> add = job.getMessageIn();

					JobStats stats = jb.getJobStats().get(job.getID());

					int level = stats.getLevel();
					double exp = stats.getExp();
					String bought = stats.getBoughtDate();
					String joined = stats.getJoinedDate();
					int times = stats.getWorkedTimes();
					double earned = stats.getTotalEarnings();

					double worked_today = stats.getEarnedToday();

					double need = stats.getNeed();

					for (String line : add) {
						lore.add(plugin.getBasicPluginManager().toHex(player, line).replaceAll("<level>", "" + level)
								.replaceAll("<earned_today>", plugin.getBasicPluginManager().Format(worked_today))
								.replaceAll("<need>", plugin.getBasicPluginManager().Format(need))
								.replaceAll("<times>", "" + times)
								.replaceAll("<earned_all>", plugin.getBasicPluginManager().Format(earned))
								.replaceAll("<joined_date>", joined).replaceAll("<date_bought>", bought)
								.replaceAll("<exp>", plugin.getBasicPluginManager().Format(exp)));
					}

					String message = plugin.getMessageManager().getMessage(player, "job_gui_in");

					lore.add(message);
				} else if (jb.getOwnedJobs().contains(job.getID())) {

					JobStats stats = jb.getJobStats().get(job.getID());

					int level = stats.getLevel();
					double exp = stats.getExp();

					double need = stats.getNeed();

					ArrayList<String> add = job.getMessageLook();

					for (String line : add) {
						lore.add(plugin.getBasicPluginManager().toHex(player, line)
								.replaceAll("<need>", plugin.getBasicPluginManager().Format(need))
								.replaceAll("<level>", "" + level)
								.replaceAll("<exp>", plugin.getBasicPluginManager().Format(exp)));
					}

					String message = plugin.getMessageManager().getMessage(player, "job_gui_join");

					lore.add(message);
				} else {

					if (job.getPrice() == 0) {

						String message = plugin.getMessageManager().getMessage(player, "job_is_free_gui");

						lore.add(message);
						// free

					} else {

						// paid

						String message = plugin.getMessageManager().getMessage(player, "job_gui_buy")
								.replaceAll("<money>", job.getPriceToDisplay());

						lore.add(message);

					}

				}

				meta.setLore(lore);

				item.setItemMeta(meta);

				inv.setItem(job.getSlot(), item);
			});
		}
	}

	public void setOther(Player player, GUIType type, Inventory open, String name) {

		LoadAndStoreGUIManager d = plugin.getLoadAndStoreGUIManager();

		for (CustomItem custom : d.getCustomItems(type)) {

			ItemStack item = custom.getIcon(name);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(custom.getDisplay(player));

			ArrayList<String> lore = new ArrayList<String>();

			if (custom.hasLore()) {
				for (String line : custom.getLore()) {
					lore.add(plugin.getBasicPluginManager().toHex(player, line));
				}
			}

			meta.setLore(lore);

			item.setItemMeta(meta);

			open.setItem(custom.getSlot(), item);

		}

		for (PlaceholderItem place : d.getPlaceholderItems(type)) {

			ItemStack item = place.getIcon(name);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(place.getDisplay(player));

			item.setItemMeta(meta);

			open.setItem(place.getSlot(), item);

		}

	}

}
