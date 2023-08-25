package code.warsteiner.jobs.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.basic.BasicGUIManager;
import code.warsteiner.jobs.utils.enums.GUIType;
import code.warsteiner.jobs.utils.templates.CustomItem;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobID;
import code.warsteiner.jobs.utils.templates.JobStats;
import code.warsteiner.jobs.utils.templates.JobsPlayer;
import code.warsteiner.jobs.utils.templates.PlaceholderItem;

public class JobsGUIManager {

	private GreenJobs plugin = GreenJobs.getPlugin();

	public HashMap<UUID, Integer> details_page_manager = new HashMap<UUID, Integer>();

	public void openBlockRewardsMenu(Player player, String string, int page, String sd, boolean r) {

		LoadAndStoreGUIManager d = plugin.getLoadAndStoreGUIManager();
		JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());
		BasicGUIManager b = plugin.getBasicGUIManager();

		FileConfiguration file = plugin.getFileManager().getRewardsConfig();

		if (!r) {
			plugin.getBasicPluginManager().playSound(player, "OPEN_REWARDS_GUI");
		}

		String name = player.getName();

		GUIType type = GUIType.REWARDS;

		Inventory inv = plugin.getBasicGUIManager().openInventory(player, type, null, string, sd);

	 	plugin.executor.submit(() -> {

			setOther(player, type, inv, name);

			setItemsSorted(type, page, file.getInt("ItemsPerPage"), inv, player, sd, string, jb);

			setCatItem(type, page, file.getInt("ItemsPerPage"), inv, player, sd, string, jb);
		 });

		player.openInventory(inv);
	}

	public void updateBlockRewardsGUI(Player player, String string, int page, String sd) {

		plugin.executor.submit(() -> {

			JobsGUIManager m2 = plugin.getJobsGUIManager();

			LoadAndStoreGUIManager d = plugin.getLoadAndStoreGUIManager();
			JobsPlayer jb = plugin.getPlayerDataManager().getJobsPlayer(player.getName(), player.getUniqueId());
			BasicGUIManager b = plugin.getBasicGUIManager();

			FileConfiguration file = plugin.getFileManager().getRewardsConfig();

			String name = player.getName();

			GUIType type = GUIType.REWARDS;

			Inventory inv = player.getOpenInventory().getTopInventory();
			;

			inv.clear();

			plugin.getBasicGUIManager().getCurrentCate().put(player.getUniqueId(), sd);
			m2.details_page_manager.put(player.getUniqueId(), page);

			setOther(player, type, inv, name);

			setItemsSorted(type, page, file.getInt("ItemsPerPage"), inv, player, sd, string, jb);

			setCatItem(type, page, file.getInt("ItemsPerPage"), inv, player, sd, string, jb);
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

		int itemsPerPage = perpage;

		UUID ID = player.getUniqueId();

		if (this.details_page_manager.containsKey(ID)) {
			this.details_page_manager.remove(ID);
		}

		this.details_page_manager.put(ID, page);

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

				String date = plugin.getBasicPluginManager().getDateTodayFromCal();

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

						String message = plugin.getMessageManager().getMessage(player, "job_gui_buy").replaceAll("<money>",
								job.getPriceToDisplay());

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

	public void openJobsManager(Player player, int page) {

		Inventory inv = plugin.getBasicGUIManager().openInventory(player, GUIType.MANAGER, null, null, null);

		plugin.executor.submit(() -> {

			int itemsPerPage = 35;

			UUID ID = player.getUniqueId();

			if (this.details_page_manager.containsKey(ID)) {
				this.details_page_manager.remove(ID);
			}

			this.details_page_manager.put(ID, page);

			if (inv != null) {
				ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§8/");
				item.setItemMeta(meta);

				inv.setItem(36, item);
				inv.setItem(37, item);
				inv.setItem(38, item);
				inv.setItem(39, item);
				inv.setItem(40, item);
				inv.setItem(41, item);
				inv.setItem(42, item);
				inv.setItem(43, item);
				inv.setItem(44, item);

			}

			if (inv != null) {
				ItemStack item = new ItemStack(Material.ARROW);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§7Next Page §8->");
				item.setItemMeta(meta);

				inv.setItem(50, item);
			}

			if (inv != null) {
				ItemStack item = new ItemStack(Material.ARROW);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§8<- §7Go Back");
				item.setItemMeta(meta);

				inv.setItem(48, item);
			}

			if (plugin.getJobAPI().getLoadedJobsArray() != null) {

				ArrayList<String> jobs = plugin.getJobAPI().getLoadedJobsArray();

				int total = jobs.size();

				int startIndex = (page - 1) * itemsPerPage;
				int endIndex = Math.min(startIndex + itemsPerPage, total);

				for (int i = startIndex; i < endIndex; i++) {

					String job_by_id = jobs.get(i);
					Job job = plugin.getJobAPI().getLoadedJobsHash().get(job_by_id);

					ItemStack item = job.getIcon();
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName("§8< " + job.getName(player) + " §8>");
					item.setItemMeta(meta);

					inv.addItem(item);

				}
			}

		});

		player.openInventory(inv);

	}

}
