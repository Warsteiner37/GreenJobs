package code.warsteiner.jobs.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitRunnable;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.manager.JobsGUIManager;
import code.warsteiner.jobs.manager.LoadAndStoreGUIManager;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.utils.BossBarHandler;
import code.warsteiner.jobs.utils.enums.GUIType;
import code.warsteiner.jobs.utils.templates.Job;

public class BasicGUIManager {

	private GreenJobs plugin = GreenJobs.getPlugin();

	private HashMap<UUID, GUIType> guis = new HashMap<UUID, GUIType>();
	private HashMap<UUID, String> details_about = new HashMap<UUID, String>();
	private HashMap<UUID, String> details_cat = new HashMap<UUID, String>();
	private HashMap<UUID, String> details_job = new HashMap<UUID, String>();

	public void clearLists() {
		this.guis.clear();
		this.details_about.clear();
		this.details_cat.clear();
		this.details_job.clear();
	}

	public void updateInventory(Player player) {

		plugin.executor.submit(() -> {

			MessageManager mg = plugin.getMessageManager();
			JobsGUIManager gui = plugin.getJobsGUIManager();

			if (player.getOpenInventory() != null) {

				InventoryView inv = player.getOpenInventory();
				String title = inv.getTitle();

				if (inv.getTitle() != null) {

					UUID ID = player.getUniqueId();
					String name = player.getName();

					if (guis.containsKey(ID)) {

						GUIType get = guis.get(ID);

						String d = getJobData().get(ID);

						Job job = plugin.getJobAPI().getLoadedJobsHash().get(d);

						if (get.equals(GUIType.BUY_CONFIRM) && isConfirmBuyMenu(player, job, title)) {

							if (job.getPrice() != 0) {
								double bal = plugin.getEco().getBalance(player);
								double price = job.getPrice() - 0.05;

								if (bal <= price) {

									player.sendMessage(mg.getMessage("livegui_cancel_not_enough_money")
											.replaceAll("<job>", job.getDisplay()));

									plugin.getBasicPluginManager().playSound(player, "LIVEGUI_PROGRESS_CANCELED");

									Bukkit.getScheduler().runTask(plugin, () -> {
										gui.openJobsMenu(player, true);
									});
								} else {
									plugin.getJobsGUIManager().updateConfPurchasMenu(player, name);
								}
							}

						} else if (get.equals(GUIType.JOBS) && isJobsMenu(player, title)) {
							gui.updateJobMenu(player);
						} else if (get.equals(GUIType.OPTIONS) && isOptionsMenu(player, title, job)) {
							gui.updateOptionsGUI(player);
						} else if (get.equals(GUIType.REWARDS) && isRewardsMenu(player, job, title)) {

							int page = gui.details_page_manager.get(ID);
							String next = plugin.getBasicGUIManager().getCurrentCate().get(ID);

							gui.updateBlockRewardsGUI(player, job.getID(), page, next);

						}

					}

				}

			}

		});

	}

	public HashMap<UUID, GUIType> getGUIData() {
		return this.guis;
	}

	public HashMap<UUID, String> getJobData() {
		return this.details_job;
	}

	public HashMap<UUID, String> getCurrentCate() {
		return this.details_cat;
	}

	public boolean isRewardsMenu(Player player, Job job, String right_now) {

		UUID ID = player.getUniqueId();
		String name = player.getName();

	 
			if (this.guis.containsKey(ID)) {

				GUIType get = this.guis.get(ID);

				if(job != null) {
					String translated = plugin.getBasicPluginManager()
							.toHex(plugin.getLoadAndStoreGUIManager().getName(get)).replaceAll("<name>", name)
							.replaceAll("<job>", job.getDisplay());

					String title = plugin.getBasicPluginManager().toHex(right_now).replaceAll("<name>", name)
							.replaceAll("<job>", job.getDisplay());

					if (translated.equalsIgnoreCase(title) && get.equals(GUIType.REWARDS)) {
						return true;
					}
				} else if (get.equals(GUIType.REWARDS)) {
					return true;
				}

		 
		}

		return false;
	}
 
	public boolean isConfirmBuyMenu(Player player, Job job, String right_now) {

		UUID ID = player.getUniqueId();
		String name = player.getName();

		if (this.guis.containsKey(ID)) {

			GUIType get = this.guis.get(ID);

			if (job != null) {
				String translated = plugin.getBasicPluginManager()
						.toHex(plugin.getLoadAndStoreGUIManager().getName(get)).replaceAll("<name>", name)
						.replaceAll("<job>", job.getDisplay());

				String title = plugin.getBasicPluginManager().toHex(right_now).replaceAll("<name>", name)
						.replaceAll("<job>", job.getDisplay());

				if (translated.equalsIgnoreCase(title) && get.equals(GUIType.BUY_CONFIRM)) {
					return true;
				}
			} else if (get.equals(GUIType.BUY_CONFIRM)) {
				return true;
			}
		}

		return false;
	}

	public boolean isJobsManagerMenu(Player player, String right_now) {

		UUID ID = player.getUniqueId();
		String name = player.getName();

		if (this.guis.containsKey(ID)) {

			GUIType get = this.guis.get(ID);

			String translated = plugin.getBasicPluginManager().toHex(plugin.getLoadAndStoreGUIManager().getName(get))
					.replaceAll("<name>", name);

			String title = plugin.getBasicPluginManager().toHex(right_now).replaceAll("<name>", name);

			if (translated.equalsIgnoreCase(title) && get.equals(GUIType.MANAGER)) {
				return true;
			}

		}

		return false;
	}

	public boolean isOptionsMenu(Player player, String right_now, Job job) {

		UUID ID = player.getUniqueId();
		String name = player.getName();

		if (this.guis.containsKey(ID)) {

			GUIType get = this.guis.get(ID);

			if (job != null) {

				String translated = plugin.getBasicPluginManager()
						.toHex(plugin.getLoadAndStoreGUIManager().getName(get)).replaceAll("<job>", job.getDisplay())
						.replaceAll("<name>", name);

				String title = plugin.getBasicPluginManager().toHex(right_now).replaceAll("<job>", job.getDisplay())
						.replaceAll("<name>", name);

				if (translated.equalsIgnoreCase(title) && get.equals(GUIType.OPTIONS)) {
					return true;
				}
			} else if (get.equals(GUIType.OPTIONS)) {
				return true;
			}

		}

		return false;
	}

	public boolean isJobsMenu(Player player, String right_now) {

		UUID ID = player.getUniqueId();
		String name = player.getName();

		if (this.guis.containsKey(ID)) {

			GUIType get = this.guis.get(ID);

			String translated = plugin.getBasicPluginManager().toHex(plugin.getLoadAndStoreGUIManager().getName(get))
					.replaceAll("<name>", name);

			String title = plugin.getBasicPluginManager().toHex(right_now).replaceAll("<name>", name);

			if (translated.equalsIgnoreCase(title) && get.equals(GUIType.JOBS)) {
				return true;
			}

		}

		return false;
	}

	public Inventory openInventory(Player player, GUIType name, String about, String d, String cate) {

		String job = "Unknown";

		if (d != null) {
			job = plugin.getJobAPI().getLoadedJobsHash().get(d).getDisplay();
		}

		UUID UUID = player.getUniqueId();

		plugin.executor.submit(() -> {

			if (this.guis.containsKey(UUID)) {
				this.guis.remove(UUID);
			}

			if (this.details_job.containsKey(UUID)) {
				this.details_job.remove(UUID);
			}

			if (this.details_about.containsKey(UUID)) {
				this.details_about.remove(UUID);
			}

			if (about != null) {
				this.details_about.put(UUID, about);
			}

			if (d != null) {

				this.details_job.put(UUID, d);
			}

			if (name != null) {
				this.guis.put(UUID, name);
			}

			if (cate != null) {
				this.details_cat.put(UUID, cate);
			}

		});

		String used = plugin.getLoadAndStoreGUIManager().getName(name).replaceAll("<job>", job);

		final Inventory inv = Bukkit.createInventory(null, plugin.getLoadAndStoreGUIManager().getSize(name) * 9, used);

		return inv;
	}

}
