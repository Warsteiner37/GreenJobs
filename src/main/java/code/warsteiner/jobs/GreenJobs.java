package code.warsteiner.jobs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import code.warsteiner.jobs.api.JobAPI;
import code.warsteiner.jobs.api.JobLoadAPI;
import code.warsteiner.jobs.api.LevelAPI;
import code.warsteiner.jobs.api.SkullCreatorAPI;
import code.warsteiner.jobs.basic.BasicGUIManager;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.basic.events.CommandListeners;
import code.warsteiner.jobs.basic.events.PlayerASyncInventoryClickEvent;
import code.warsteiner.jobs.basic.events.PlayerASyncJoinEvent;
import code.warsteiner.jobs.basic.events.PlayerASyncQuitEvent;
import code.warsteiner.jobs.basic.events.PlayerAsyncCheckForWork;
import code.warsteiner.jobs.basic.events.PlayerUpdateInventory;
import code.warsteiner.jobs.commands.JobTabComplete;
import code.warsteiner.jobs.commands.JobsCommand;
import code.warsteiner.jobs.commands.admin.AdminCommand;
import code.warsteiner.jobs.commands.admin.AdminTabComplete;
import code.warsteiner.jobs.commands.admin.JobsManagerCommand;
import code.warsteiner.jobs.commands.admin.sub.ExpSub;
import code.warsteiner.jobs.commands.admin.sub.LevelSub;
import code.warsteiner.jobs.commands.admin.sub.LimitSub;
import code.warsteiner.jobs.commands.admin.sub.NeedSub;
import code.warsteiner.jobs.commands.sub.HelpSub;
import code.warsteiner.jobs.commands.sub.RewardsSub;
import code.warsteiner.jobs.manager.ItemManager;
import code.warsteiner.jobs.manager.JobsGUIManager;
import code.warsteiner.jobs.manager.LoadAndStoreGUIManager;
import code.warsteiner.jobs.manager.MessageManager;
import code.warsteiner.jobs.manager.MultiplierManager;
import code.warsteiner.jobs.manager.PlayerDataManager;
import code.warsteiner.jobs.support.PlaceHolderManager;
import code.warsteiner.jobs.support.WorldGuardSupport;
import code.warsteiner.jobs.utils.BossBarHandler;
import code.warsteiner.jobs.utils.FileManager;
import code.warsteiner.jobs.utils.LocationManager;
import code.warsteiner.jobs.utils.Metrics;
import code.warsteiner.jobs.utils.UtilManager;
import code.warsteiner.jobs.utils.actions.BreakAction;
import code.warsteiner.jobs.utils.actions.FishAction;
import code.warsteiner.jobs.utils.actions.JobActionManager;
import code.warsteiner.jobs.utils.actions.KillMobAction;
import code.warsteiner.jobs.utils.actions.PlaceAction;
import code.warsteiner.jobs.utils.actions.ShearAction;
import code.warsteiner.jobs.utils.actions.StripLogAction;
import code.warsteiner.jobs.utils.admincommand.AdminSubCommandRegistry;
import code.warsteiner.jobs.utils.playercommand.PlayerSubCommandRegistry;
import code.warsteiner.jobs.utils.templates.FileTemplate;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobAction;
import code.warsteiner.jobs.utils.templates.JobStats;
import code.warsteiner.jobs.utils.templates.JobsMultiplier;
import code.warsteiner.jobs.utils.templates.JobsPlayer;
import code.warsteiner.jobs.utils.templates.JobsTemplates;
import code.warsteiner.jobs.utils.templates.PlayerSkill;
import net.milkbowl.vault.economy.Economy;

public class GreenJobs extends JavaPlugin {

	private static GreenJobs plugin;
	private Economy econ;

	public ExecutorService executor;

	private UtilManager util;
	private PlayerDataManager data_manager;
	private BasicPluginManager basic;
	private FileManager files;
	private LocationManager locations;
	private MultiplierManager mm;
	private JobsTemplates jtmp;
	private BasicGUIManager gui;
	private JobsGUIManager gm;
	private ItemManager items;
	private LoadAndStoreGUIManager gs;
	private MessageManager messages;
	private AdminSubCommandRegistry as;
	private PlayerSubCommandRegistry cp;

	private JobLoadAPI load;
	private SkullCreatorAPI skulls;
	private JobAPI jobapi;
	private LevelAPI levels;
	private JobActionManager acm;
	private WorldGuardSupport wg;

	public void onLoad() {

		plugin = this;
		executor = Executors.newCachedThreadPool();

		createFolders();

		this.setClassesv1();

		if (isInstalled("WorldGuard")) {
			this.wg = new WorldGuardSupport();
			this.wg.setClass();
			this.wg.load();
		}

	}

	@Override
	public void onEnable() {

		this.hook();

		this.registerJobEvents();

		this.registerEvents();

		this.registerCommands();

		this.setClassesv2();

		if (isInstalled("PlaceHolderAPI")) {
			Bukkit.getConsoleSender().sendMessage("§aLoading PlaceHolderAPI Support...");
			new PlaceHolderManager().register();
		}

		BossBarHandler.startSystemCheck();

		FileManager f = plugin.getFileManager();

		FileConfiguration dt = f.getDataFile().get();

		executor.submit(() -> {
 
			ArrayList<String> people = (ArrayList<String>) dt.getStringList("PlayerList");

			for (String guy : people) {

				UUID d = UUID.fromString(guy.toString());

				String name = dt.getString("Player." + d + ".Name");
 
				int max = dt.getInt("Player." + d + ".Max");
				double points = dt.getDouble("Player." + d + ".Points");
				double sal = dt.getDouble("Player." + d + ".Salary");
				String sal_date = dt.getString("Player." + d + ".SalaryDate");

				List<String> current = dt.getStringList("Player." + d + ".CurrentJobs");
				List<String> owned = dt.getStringList("Player." + d + ".OwnedJobs");

				HashMap<String, JobStats> stats = new HashMap<String, JobStats>();

				for (String job : owned) {

					Job real = plugin.getJobAPI().getLoadedJobsHash().get(job);

					int level = dt.getInt("JobStats." + d + ".Job." + job + ".Level");
					double exp = dt.getDouble("JobStats." + d + ".Job." + job + ".Exp");
					double need = dt.getDouble("JobStats." + d + ".Job." + job + ".Need");
					double total = dt.getDouble("JobStats." + d + ".Job." + job + ".Total");
					String bought = dt.getString("JobStats." + d + ".Job." + job + ".BoughtDate");
					String joined = dt.getString("JobStats." + d + ".Job." + job + ".JoinedDate");
					int worked_times = dt.getInt("JobStats." + d + ".Job." + job + ".WorkedTimes");

					List<String> worked_dates_list = dt
							.getStringList("JobStats." + d + ".Job." + job + ".DatesWorkedOn");

					HashMap<String, Double> earnings_all = new HashMap<String, Double>();

					HashMap<String, HashMap<String, Integer>> t = new HashMap<String, HashMap<String, Integer>>();
					HashMap<String, HashMap<String, Double>> t2 = new HashMap<String, HashMap<String, Double>>();

					HashMap<String, Integer> times_broken_block = new HashMap<String, Integer>();
					HashMap<String, Double> earned_money_broken_block = new HashMap<String, Double>();

					HashMap<String, Integer> listed = new HashMap<String, Integer>();
					HashMap<String, Double> listed2 = new HashMap<String, Double>();

					if (real != null) {
						for (String date : worked_dates_list) {

							double earnings_all_amounts = dt
									.getDouble("JobDates." + d + ".Job." + job + "." + date + ".Earnings");

							earnings_all.put(date, earnings_all_amounts);

							real.getEveryID().forEach((id, type) -> {

								listed.put(id, dt.getInt(
										"JobDates." + d + ".Job." + job + "." + date + ".BrokenTimesBlock." + id));
								listed2.put(id, dt.getDouble(
										"JobDates." + d + ".Job." + job + "." + date + ".EarnedMoney." + id));

							});

							t.put(date, listed);
							t2.put(date, listed2);

						}

						real.getEveryID().forEach((id, type) -> {
							times_broken_block.put(id,
									dt.getInt("JobDates." + d + ".Job." + job + ".TimesBrokenThisBlock." + id));
							earned_money_broken_block.put(id,
									dt.getDouble("JobDates." + d + ".Job." + job + ".EarnedByThisBlock." + id));
						});
					}

					JobStats stats_created = new JobStats(d, level, exp, need, total, bought, joined, worked_times,
							earnings_all, t, t2, times_broken_block, earned_money_broken_block,
							(ArrayList<String>) worked_dates_list);

					stats.put(job, stats_created);
				}

				JobsPlayer jobs_player = new JobsPlayer(d, name, max, points, sal, sal_date, current, owned, stats,
						null, 0, null);

				plugin.getPlayerDataManager().addToList(d, jobs_player);

				 

			}
			
			int total = plugin.getPlayerDataManager().getJobsPlayerList().size();
			
			Bukkit.getConsoleSender().sendMessage("§4§lLoaded a total of "+total+"x Player's Data!");

		});

		new Metrics(this, 19287);

	}

	public void onDisable() {

		FileManager f = plugin.getFileManager();

		FileConfiguration File = f.getLocationConfig();
		FileConfiguration dt = f.getDataFile().get();
		java.io.File dt_file = f.getDataFile().getfile();

		executor.submit(() -> {

			HashMap<UUID, JobsPlayer> jlist = this.data_manager.getJobsPlayerList();

			int total = jlist.size();
			
			ArrayList<String> my_list = new ArrayList<String>();
  
			jlist.forEach((ID, jb) -> {

				my_list.add("" + ID);

				if (this.locations.getSavedLocation(ID.toString()) != null) {

					Location loc = this.locations.getSavedLocation(ID.toString());

					String name = ID.toString();

					File.set("Loc." + name + ".X", loc.getBlockX());
					File.set("Loc." + name + ".Y", loc.getY());
					File.set("Loc." + name + ".Z", loc.getZ());
					File.set("Loc." + name + ".Pitch", loc.getPitch());
					File.set("Loc." + name + ".World", loc.getWorld().getName());
					File.set("Loc." + name + ".Yaw", loc.getYaw());

				}

				String d = jb.getUUID().toString();

				dt.set("Player." + d + ".Name", jb.getName());
				dt.set("Player." + d + ".Max", jb.getMaxJobs());
				dt.set("Player." + d + ".Points", jb.getPoints());
				dt.set("Player." + d + ".Salary", jb.getSalary());
				dt.set("Player." + d + ".SalaryDate", jb.getSalaryDate());
				dt.set("Player." + d + ".CurrentJobs", jb.getCurrentJobs());
				dt.set("Player." + d + ".OwnedJobs", jb.getOwnedJobs());

				// stats needed

				HashMap<String, JobStats> stats = jb.getJobStats();

				stats.forEach((job, real) -> {

					dt.set("JobStats." + d + ".Job." + job + ".Level", real.getLevel());
					dt.set("JobStats." + d + ".Job." + job + ".Exp", real.getExp());
					dt.set("JobStats." + d + ".Job." + job + ".Need", real.getNeed());
					dt.set("JobStats." + d + ".Job." + job + ".Total", real.getTotalEarnings());
					dt.set("JobStats." + d + ".Job." + job + ".BoughtDate", real.getBoughtDate());
					dt.set("JobStats." + d + ".Job." + job + ".JoinedDate", real.getJoinedDate());
					dt.set("JobStats." + d + ".Job." + job + ".WorkedTimes", real.getWorkedTimes());
					dt.set("JobStats." + d + ".Job." + job + ".DatesWorkedOn", real.getDatesList());

					for (String date : real.getDatesList()) {

						dt.set("JobDates." + d + ".Job." + job + "." + date + ".Earnings",
								real.getAllEarnings().get(date));

						HashMap<String, Integer> test = real.getTimesBrokenABlockDates().get(date);
						HashMap<String, HashMap<String, Double>> test2 = real.getEarningsByBlockDates();

						if(test != null) {
							test.forEach((id, amount) -> { 
								dt.set("JobDates." + d + ".Job." + job + "." + date + ".BrokenTimesBlock." + id, amount);
							});

							test2.get(date).forEach((id, amount) -> {
								dt.set("JobDates." + d + ".Job." + job + "." + date + ".EarnedMoney." + id, amount);
							});
						}

					}

					HashMap<String, Integer> test3 = real.getTimesBrokenABlock();

					if(test3 != null) {
						test3.forEach((id, amount) -> {
							dt.set("JobDates." + d + ".Job." + job + ".TimesBrokenThisBlock." + id, amount);
						});
					}

					HashMap<String, Double> test4 = real.getEarnedMoneyByBlock();

					if(test4 != null) {
						test4.forEach((id, amount) -> {
							dt.set("JobDates." + d + ".Job." + job + ".EarnedByThisBlock." + id, amount);
						});
					}

				});
 

			});

			dt.set("PlayerList", my_list);
 
			Bukkit.getConsoleSender().sendMessage("§4§lSaving a total of "+total+"x Player's Data!");


			if (!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}
			
			File folder_2 = new File(getDataFolder(), "data");

			if (!folder_2.exists()) {
				folder_2.mkdir();
			}

			f.getLocationFile().save();
			try {
				dt.save(dt_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		});
		 
		BossBarHandler.clearLists();

		if (isInstalled("WorldGuard")) {
			plugin.getWorldGuardSupport().clearLists();
		}

		executor.shutdown();

		plugin.getBasicGUIManager().clearLists();
		plugin.getJobsGUIManager().details_page_manager.clear();
		plugin.getJobAPI().clearLists();
		plugin.getMessageManager().clearLists();
		plugin.getLoadAndStoreGUIManager().clearLists();

		plugin.getJobActionManager().clearLists();

	}

	public void hook() {
		setupEconomy();
	}

	public void registerEvents() {
		PluginManager mg = Bukkit.getPluginManager();

		mg.registerEvents(new PlayerASyncJoinEvent(), this);
		mg.registerEvents(new PlayerASyncQuitEvent(), this);
		mg.registerEvents(new PlayerASyncInventoryClickEvent(), this);
		mg.registerEvents(new PlayerUpdateInventory(), this);
		mg.registerEvents(new CommandListeners(), this);
	}

	public void registerJobEvents() {

		PluginManager mg = Bukkit.getPluginManager();

		JobActionManager f = plugin.getJobActionManager();

		f.registerAction(new StripLogAction());
		f.registerAction(new BreakAction());
		f.registerAction(new PlaceAction());
		f.registerAction(new KillMobAction());
		f.registerAction(new ShearAction());
		f.registerAction(new FishAction());

		mg.registerEvents(new PlayerAsyncCheckForWork(), this);

		executor.submit(() -> {
			this.load.loadJobsbyStart();
			this.jobapi.sortJobsAfterActions();
		});

	}

	public JobActionManager getJobActionManager() {
		return this.acm;
	}

	public boolean isInstalled(String plugin) {
		Plugin Plugin = Bukkit.getServer().getPluginManager().getPlugin(plugin);
		if (Plugin != null) {
			return true;
		}
		return false;
	}

	public void registerCommands() {
		getCommand("jobs").setExecutor(new JobsCommand());
		getCommand("jobs").setTabCompleter(new JobTabComplete());

		getPlayerSubCommandManager().getSubCommandList().add(new RewardsSub());
		getPlayerSubCommandManager().getSubCommandList().add(new HelpSub());

		// getCommand("jobsmanager").setExecutor(new JobsManagerCommand());

		getCommand("jpm").setExecutor(new AdminCommand());
		getCommand("jpm").setTabCompleter(new AdminTabComplete());

		getAdminSubCommandManager().getSubCommandList().add(new ExpSub());
		getAdminSubCommandManager().getSubCommandList().add(new LevelSub());
		getAdminSubCommandManager().getSubCommandList().add(new LimitSub());
		getAdminSubCommandManager().getSubCommandList().add(new NeedSub());
	}

	public void setClassesv1() {

		executor = Executors.newCachedThreadPool();

		this.util = new UtilManager();
		this.data_manager = new PlayerDataManager();
		this.basic = new BasicPluginManager();
		this.files = new FileManager();
		this.locations = new LocationManager();
		this.mm = new MultiplierManager();
		this.load = new JobLoadAPI();
		this.jobapi = new JobAPI();
		this.gui = new BasicGUIManager();
		this.gm = new JobsGUIManager();
		this.skulls = new SkullCreatorAPI();
		this.items = new ItemManager();
		this.gs = new LoadAndStoreGUIManager();
		this.messages = new MessageManager();
		this.levels = new LevelAPI();
		this.acm = new JobActionManager();
		this.as = new AdminSubCommandRegistry();
		this.cp = new PlayerSubCommandRegistry();

		this.files.setFiles();
		this.gs.load();

	}

	public PlayerSubCommandRegistry getPlayerSubCommandManager() {
		return this.cp;
	}

	public AdminSubCommandRegistry getAdminSubCommandManager() {
		return this.as;
	}

	public WorldGuardSupport getWorldGuardSupport() {
		return this.wg;
	}

	public void setClassesv2() {
		this.messages.createMessagesFiles();

	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			econ = (Economy) economyProvider.getProvider();
			Bukkit.getConsoleSender().sendMessage("Loaded Vault Support!");
		} else {
			Bukkit.getConsoleSender().sendMessage("Failed to load vault for GreenJobs!");
		}
		return (econ != null);
	}

	private void createFolders() {

		this.jtmp = new JobsTemplates();

		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}

		File folder_1 = new File(getDataFolder(), "jobs");

		if (!folder_1.exists()) {
			folder_1.mkdir();

			executor.submit(() -> {

				this.jtmp.createDefaultJobs();

			});
		}

		File folder_2 = new File(getDataFolder(), "data");

		if (!folder_2.exists()) {
			folder_2.mkdir();
		}

		File folder_3 = new File(getDataFolder(), "guis");

		if (!folder_3.exists()) {
			folder_3.mkdir();
		}

		File folder_4 = new File(getDataFolder(), "messages");

		if (!folder_4.exists()) {
			folder_4.mkdir();
		}

		File folder_5 = new File(getDataFolder(), "integrations");

		if (!folder_5.exists()) {
			folder_5.mkdir();
		}

	}

	public LevelAPI getLevelAPI() {
		return this.levels;
	}

	public MessageManager getMessageManager() {
		return this.messages;
	}

	public LoadAndStoreGUIManager getLoadAndStoreGUIManager() {
		return this.gs;
	}

	public ItemManager getItemManager() {
		return this.items;
	}

	public SkullCreatorAPI getSkullCreatorAPI() {
		return this.skulls;
	}

	public JobsGUIManager getJobsGUIManager() {
		return this.gm;
	}

	public BasicGUIManager getBasicGUIManager() {
		return this.gui;
	}

	public JobAPI getJobAPI() {
		return this.jobapi;
	}

	public JobLoadAPI getJobLoadAPI() {
		return this.load;
	}

	public MultiplierManager getMultiplierManager() {
		return this.mm;
	}

	public LocationManager getLocationManager() {
		return this.locations;
	}

	public FileManager getFileManager() {
		return this.files;
	}

	public BasicPluginManager getBasicPluginManager() {
		return this.basic;
	}

	public static GreenJobs getPlugin() {
		return plugin;
	}

	public UtilManager getUtilManager() {
		return this.util;
	}

	public PlayerDataManager getPlayerDataManager() {
		return this.data_manager;
	}

	public Economy getEco() {
		return econ;
	}

}
