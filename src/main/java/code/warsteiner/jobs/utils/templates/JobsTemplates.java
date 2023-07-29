package code.warsteiner.jobs.utils.templates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import code.warsteiner.jobs.GreenJobs;

public class JobsTemplates {

	private GreenJobs plugin = GreenJobs.getPlugin();

	public void createDefaultJobs() {
		setMinerTemplate();
		setLumberjackTemplate();
		setBuilderTemplate();
		setKillMobTemplate();
	}
	
	public void setKillMobTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "KillMob.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>(); 
		actions.add("KILL_MOB");
		
		ArrayList<String> worlds = new ArrayList<String>(); 
		worlds.add("world");
		
		cfg.set("Display", "&cMob Killer");
		cfg.set("Actions", actions);
		cfg.set("Icon", "IRON_SWORD");
		 
		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		
		
		ArrayList<String> cmds = new ArrayList<String>(); 
		cmds.add("say <name> & job <job>");
		
		
		
		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);
		
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 12);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);
		
		//stats messages
		cfg.set("Desc", "&7Earn Money while killing mobs!");
		
		ArrayList<String> stats = new ArrayList<String>(); 
		stats.add("&7Level: &6#<level>");
		stats.add("&7Exp: &7<exp>/<need>");
		stats.add("&7Bought Date: &7<date_bought>");
		stats.add("&7Joined Date: &7<joined_date>");
		stats.add("&7");
		stats.add("&7Times worked: &7<times>x");
		stats.add("&7Earned Today: &7<earned_today>$");
		stats.add("&7Earned all time: &7<earned_all>$"); 
		stats.add("&8");
		
		ArrayList<String> stats2 = new ArrayList<String>(); 
		stats2.add("&7Level: &6#<level>");
		stats2.add("&7Exp: &7<exp>/<need>");
		stats2.add("&8");
		
		cfg.set("Stats.In", stats);
		cfg.set("Stats.Look", stats2);
		
		//ids
		
		ArrayList<String> ids = new ArrayList<String>(); 
		ids.add("ZOMBIE");
		ids.add("CREEPER");
		
		cfg.set("ID.KILL_MOB.List", ids);
	 
		//stone
		cfg.set("ID.KILL_MOB.ZOMBIE.Chance", 90);
		cfg.set("ID.KILL_MOB.ZOMBIE.ID", "ZOMBIE");
		cfg.set("ID.KILL_MOB.ZOMBIE.Icon", "ZOMBIE_SPAWN_EGG");
		cfg.set("ID.KILL_MOB.ZOMBIE.Money", 2);
		cfg.set("ID.KILL_MOB.ZOMBIE.Exp", 3);
		cfg.set("ID.KILL_MOB.ZOMBIE.Points", 0.5);
		cfg.set("ID.KILL_MOB.ZOMBIE.Display", "&aZombie");
		cfg.set("ID.KILL_MOB.ZOMBIE.RewardsGUI.Display", "&8< &aZombie &8>");
		cfg.set("ID.KILL_MOB.ZOMBIE.RewardsGUI.Desc", "&7Careful with Zombies!");
		
		ArrayList<String> lore5 = new ArrayList<String>(); 
		lore5.add("&8&m--------------"); 
		lore5.add("&7Reward&8: &a<money>");
		lore5.add("&7Exp&8: &a<exp>");
		lore5.add("&7Chance&8: &c<chance>");
		lore5.add("&7Points&8: &a<points>");

		ArrayList<String> lorein7 = new ArrayList<String>(); 
		lorein7.add("&a");
		lorein7.add("&7You killed this Mob &b<times>x &7times");
		lorein7.add("&7You earned &c<earned_today>$ &7by this Block today");
		lorein7.add("&7You earned &c<earned>$ &7by this Block in total");
		
		
		cfg.set("ID.KILL_MOB.ZOMBIE.RewardsGUI.Lore", lore5);
		cfg.set("ID.KILL_MOB.ZOMBIE.RewardsGUI.LoreAddWhenOwnJob", lorein7);
		
		cfg.set("ID.KILL_MOB.ZOMBIE.RewardsGUI.Icon", "ZOMBIE_SPAWN_EGG");
		cfg.set("ID.KILL_MOB.ZOMBIE.RewardsGUI.Sorting", 2);
		 
		cfg.set("ID.KILL_MOB.CREEPER.Chance", 90);
		cfg.set("ID.KILL_MOB.CREEPER.ID", "CREEPER");
		cfg.set("ID.KILL_MOB.CREEPER.Icon", "CREEPER_SPAWN_EGG");
		cfg.set("ID.KILL_MOB.CREEPER.Money", 2);
		cfg.set("ID.KILL_MOB.CREEPER.Exp", 3);
		cfg.set("ID.KILL_MOB.CREEPER.Points", 0.5);
		cfg.set("ID.KILL_MOB.CREEPER.Display", "&eCreeper");
		cfg.set("ID.KILL_MOB.CREEPER.RewardsGUI.Display", "&8< &eCreeper &8>"); 
		
		ArrayList<String> lore4 = new ArrayList<String>(); 
		lore4.add("&8&m--------------"); 
		lore4.add("&7Reward&8: &a<money>");
		lore4.add("&7Exp&8: &a<exp>");
		lore4.add("&7Chance&8: &c<chance>");
		lore4.add("&7Points&8: &a<points>");

		ArrayList<String> lorein4 = new ArrayList<String>(); 
		lorein4.add("&a");
		lorein4.add("&7You killed this Mob &b<times>x &7times");
		lorein4.add("&7You earned &c<earned_today>$ &7by this Block today");
		lorein4.add("&7You earned &c<earned>$ &7by this Block in total");
		 
		cfg.set("ID.KILL_MOB.CREEPER.RewardsGUI.Lore", lore4);
		cfg.set("ID.KILL_MOB.CREEPER.RewardsGUI.LoreAddWhenOwnJob", lorein4);
		
		cfg.set("ID.KILL_MOB.CREEPER.RewardsGUI.Icon", "CREEPER_SPAWN_EGG");
		cfg.set("ID.KILL_MOB.CREEPER.RewardsGUI.Sorting", 1);
		
		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		
		try {
			cfg.save(file);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	public void setBuilderTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "BUILDER.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>(); 
		actions.add("PLACE");
		
		ArrayList<String> worlds = new ArrayList<String>(); 
		worlds.add("world");
		
		cfg.set("Display", "&eBuilder");
		cfg.set("Actions", actions);
		cfg.set("Icon", "GRASS_BLOCK");
		 
		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		
		
		ArrayList<String> cmds = new ArrayList<String>(); 
		cmds.add("say <name> & job <job>");
		
		
		
		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);
		
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 11);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);
		
		//stats messages
		cfg.set("Desc", "&7Earn Money while placing blocks!");
		
		ArrayList<String> stats = new ArrayList<String>(); 
		stats.add("&7Level: &6#<level>");
		stats.add("&7Exp: &7<exp>/<need>");
		stats.add("&7Bought Date: &7<date_bought>");
		stats.add("&7Joined Date: &7<joined_date>");
		stats.add("&7");
		stats.add("&7Times worked: &7<times>x");
		stats.add("&7Earned Today: &7<earned_today>$");
		stats.add("&7Earned all time: &7<earned_all>$"); 
		stats.add("&8");
		
		ArrayList<String> stats2 = new ArrayList<String>(); 
		stats2.add("&7Level: &6#<level>");
		stats2.add("&7Exp: &7<exp>/<need>");
		stats2.add("&8");
		
		cfg.set("Stats.In", stats);
		cfg.set("Stats.Look", stats2);
		
		//ids
		
		ArrayList<String> ids = new ArrayList<String>(); 
		ids.add("GRASS_BLOCK");
		ids.add("QUARTZ_BLOCK");
		
		cfg.set("ID.PLACE.List", ids);
		
		//stone
		cfg.set("ID.PLACE.GRASS_BLOCK.Chance", 90);
		cfg.set("ID.PLACE.GRASS_BLOCK.ID", "GRASS_BLOCK");
		cfg.set("ID.PLACE.GRASS_BLOCK.Icon", "GRASS_BLOCK");
		cfg.set("ID.PLACE.GRASS_BLOCK.Money", 2);
		cfg.set("ID.PLACE.GRASS_BLOCK.Exp", 3);
		cfg.set("ID.PLACE.GRASS_BLOCK.Points", 0.5);
		cfg.set("ID.PLACE.GRASS_BLOCK.Display", "&eGrass Block");
		cfg.set("ID.PLACE.GRASS_BLOCK.RewardsGUI.Display", "&8< &eGrass Block &8>");
		cfg.set("ID.PLACE.GRASS_BLOCK.RewardsGUI.Desc", "&7Grass is a great material!");
		
		ArrayList<String> lore5 = new ArrayList<String>(); 
		lore5.add("&8&m--------------"); 
		lore5.add("&7Reward&8: &a<money>");
		lore5.add("&7Exp&8: &a<exp>");
		lore5.add("&7Chance&8: &c<chance>");
		lore5.add("&7Points&8: &a<points>");

		ArrayList<String> lorein7 = new ArrayList<String>(); 
		lorein7.add("&a");
		lorein7.add("&7You placed this Block &b<times>x &7times");
		lorein7.add("&7You earned &c<earned_today>$ &7by this Block today");
		lorein7.add("&7You earned &c<earned>$ &7by this Block in total");
		
		
		cfg.set("ID.PLACE.GRASS_BLOCK.RewardsGUI.Lore", lore5);
		cfg.set("ID.PLACE.GRASS_BLOCK.RewardsGUI.LoreAddWhenOwnJob", lorein7);
		
		cfg.set("ID.PLACE.GRASS_BLOCK.RewardsGUI.Icon", "GRASS_BLOCK");
		cfg.set("ID.PLACE.GRASS_BLOCK.RewardsGUI.Sorting", 2);
		
		//iron ore
		cfg.set("ID.PLACE.QUARTZ_BLOCK.Chance", 90);
		cfg.set("ID.PLACE.QUARTZ_BLOCK.ID", "QUARTZ_BLOCK");
		cfg.set("ID.PLACE.QUARTZ_BLOCK.Icon", "QUARTZ_BLOCK");
		cfg.set("ID.PLACE.QUARTZ_BLOCK.Money", 2);
		cfg.set("ID.PLACE.QUARTZ_BLOCK.Exp", 3);
		cfg.set("ID.PLACE.QUARTZ_BLOCK.Points", 0.5);
		cfg.set("ID.PLACE.QUARTZ_BLOCK.Display", "&fQuartz Block");
		cfg.set("ID.PLACE.QUARTZ_BLOCK.RewardsGUI.Display", "&8< &fQuartz Block &8>"); 
		
		ArrayList<String> lore4 = new ArrayList<String>(); 
		lore4.add("&8&m--------------"); 
		lore4.add("&7Reward&8: &a<money>");
		lore4.add("&7Exp&8: &a<exp>");
		lore4.add("&7Chance&8: &c<chance>");
		lore4.add("&7Points&8: &a<points>");

		ArrayList<String> lorein4 = new ArrayList<String>(); 
		lorein4.add("&a");
		lorein4.add("&7You placed this Block &b<times>x &7times");
		lorein4.add("&7You earned &c<earned_today>$ &7by this Block today");
		lorein4.add("&7You earned &c<earned>$ &7by this Block in total");
		 
		cfg.set("ID.PLACE.QUARTZ_BLOCK.RewardsGUI.Lore", lore4);
		cfg.set("ID.PLACE.QUARTZ_BLOCK.RewardsGUI.LoreAddWhenOwnJob", lorein4);
		
		cfg.set("ID.PLACE.QUARTZ_BLOCK.RewardsGUI.Icon", "QUARTZ_BLOCK");
		cfg.set("ID.PLACE.QUARTZ_BLOCK.RewardsGUI.Sorting", 1);
		
		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		
		try {
			cfg.save(file);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	public void setLumberjackTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "LUMBERJACK.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>(); 
		actions.add("BREAK");
		actions.add("STRIPLOG");
		
		ArrayList<String> worlds = new ArrayList<String>(); 
		worlds.add("world");
		
		cfg.set("Display", "&6Lumberjack");
		cfg.set("Actions", actions);
		cfg.set("Icon", "IRON_AXE");
		
		 
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		
		ArrayList<String> cmds = new ArrayList<String>(); 
		cmds.add("say <name> & job <job>");
		
		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 10);
		cfg.set("Price", 10);
		cfg.set("Worlds", worlds);
		
		//stats messages
		cfg.set("Desc", "&7Earn Money while breaking blocks!");
		
		ArrayList<String> stats = new ArrayList<String>(); 
		stats.add("&7Level: &6#<level>");
		stats.add("&7Exp: &7<exp>/<need>");
		stats.add("&7Bought Date: &7<date_bought>");
		stats.add("&7Joined Date: &7<joined_date>");
		stats.add("&7");
		stats.add("&7Times worked: &7<times>x"); 
		stats.add("&7Earned Today: &7<earned_today>$");
		stats.add("&7Earned all time: &7<earned_all>$"); 
		stats.add("&8");
		
		ArrayList<String> stats2 = new ArrayList<String>(); 
		stats2.add("&7Level: &6#<level>");
		stats2.add("&7Exp: &7<exp>/<need>");
		stats2.add("&8");
		
		cfg.set("Stats.In", stats);
		cfg.set("Stats.Look", stats2);
		
		//ids
		
		ArrayList<String> ids = new ArrayList<String>(); 
		ids.add("OAK_LOG");
		ids.add("BIRCH_LOG");
		 
		cfg.set("ID.BREAK.List", ids);
		 
		cfg.set("ID.BREAK.OAK_LOG.Chance", 90);
		cfg.set("ID.BREAK.OAK_LOG.ID", "OAK_LOG");
		cfg.set("ID.BREAK.OAK_LOG.Icon", "OAK_LOG");
		cfg.set("ID.BREAK.OAK_LOG.Money", 2);
		cfg.set("ID.BREAK.OAK_LOG.Exp", 3);
		cfg.set("ID.BREAK.OAK_LOG.Points", 0.5);
		cfg.set("ID.BREAK.OAK_LOG.Display", "&7Oak Log");
		cfg.set("ID.BREAK.OAK_LOG.RewardsGUI.Display", "&8< &7Oak Log &8>");
		
		ArrayList<String> lor2233e5 = new ArrayList<String>(); 
		lor2233e5.add("&8&m--------------"); 
		lor2233e5.add("&7Reward&8: &a<money>");
		lor2233e5.add("&7Exp&8: &a<exp>");
		lor2233e5.add("&7Chance&8: &c<chance>");
		lor2233e5.add("&7Points&8: &a<points>");

		ArrayList<String> loreiasddn7 = new ArrayList<String>(); 
		loreiasddn7.add("&a");
		loreiasddn7.add("&7You broke this Block &b<times>x &7times");
		loreiasddn7.add("&7You earned &c<earned>$ &7by this Block today");
		loreiasddn7.add("&7You earned &c<earned>$ &7by this Block in total");
		
		
		cfg.set("ID.BREAK.OAK_LOG.RewardsGUI.Lore", lor2233e5);
		cfg.set("ID.BREAK.OAK_LOG.RewardsGUI.LoreAddWhenOwnJob", loreiasddn7);
		
		
		cfg.set("ID.BREAK.OAK_LOG.RewardsGUI.Desc", "&7Oak is a great material!");
		cfg.set("ID.BREAK.OAK_LOG.RewardsGUI.Icon", "OAK_LOG");
		cfg.set("ID.BREAK.OAK_LOG.RewardsGUI.Sorting", 1);
		 
		cfg.set("ID.BREAK.BIRCH_LOG.Chance", 50);
		cfg.set("ID.BREAK.BIRCH_LOG.ID", "BIRCH_LOG");
		cfg.set("ID.BREAK.BIRCH_LOG.Icon", "BIRCH_LOG");
		cfg.set("ID.BREAK.BIRCH_LOG.Money", 2);
		cfg.set("ID.BREAK.BIRCH_LOG.Exp", 3);
		cfg.set("ID.BREAK.BIRCH_LOG.Points", 0.5);
		cfg.set("ID.BREAK.BIRCH_LOG.Display", "&7Birch Log");
		cfg.set("ID.BREAK.BIRCH_LOG.RewardsGUI.Display", "&8< &7Birch Log &8>"); 
		
		
		ArrayList<String> lor23e5 = new ArrayList<String>(); 
		lor23e5.add("&8&m--------------"); 
		lor23e5.add("&7Reward&8: &a<money>");
		lor23e5.add("&7Exp&8: &a<exp>");
		lor23e5.add("&7Chance&8: &c<chance>");
		lor23e5.add("&7Points&8: &a<points>");

		ArrayList<String> loreidn7 = new ArrayList<String>(); 
		loreidn7.add("&a");
		loreidn7.add("&7You broke this Block &b<times>x &7times");
		loreidn7.add("&7You earned &c<earned_today>$ &7by this Block today");
		loreidn7.add("&7You earned &c<earned>$ &7by this Block in total");
		
		
		cfg.set("ID.BREAK.BIRCH_LOG.RewardsGUI.Lore", lor23e5);
		cfg.set("ID.BREAK.BIRCH_LOG.RewardsGUI.LoreAddWhenOwnJob", loreidn7);
		
		cfg.set("ID.BREAK.BIRCH_LOG.RewardsGUI.Icon", "BIRCH_LOG");
		cfg.set("ID.BREAK.BIRCH_LOG.RewardsGUI.Sorting", 3);
		
		ArrayList<String> ids_2nd = new ArrayList<String>(); 
		ids_2nd.add("OAK_LOG"); 
		 
		cfg.set("ID.STRIPLOG.List", ids_2nd);
		 
		cfg.set("ID.STRIPLOG.OAK_LOG.Chance", 90);
		cfg.set("ID.STRIPLOG.OAK_LOG.ID", "STRIPPED_OAK_LOG");
		cfg.set("ID.STRIPLOG.OAK_LOG.Icon", "STRIPPED_OAK_LOG");
		cfg.set("ID.STRIPLOG.OAK_LOG.Money", 2);
		cfg.set("ID.STRIPLOG.OAK_LOG.Exp", 3);
		cfg.set("ID.STRIPLOG.OAK_LOG.Points", 0.5);
		cfg.set("ID.STRIPLOG.OAK_LOG.Display", "&7Oak Log Stripped");
		cfg.set("ID.STRIPLOG.OAK_LOG.RewardsGUI.Display", "&8< &7Oak Log Stripped &8>");
		cfg.set("ID.STRIPLOG.OAK_LOG.RewardsGUI.Desc", "&7Oak is a great material!");
		cfg.set("ID.STRIPLOG.OAK_LOG.RewardsGUI.Icon", "STRIPPED_OAK_LOG");
		
		ArrayList<String> lore5 = new ArrayList<String>(); 
		lore5.add("&8&m--------------"); 
		lore5.add("&7Reward&8: &a<money>");
		lore5.add("&7Exp&8: &a<exp>");
		lore5.add("&7Chance&8: &c<chance>");
		lore5.add("&7Points&8: &a<points>");

		ArrayList<String> lorein7 = new ArrayList<String>(); 
		lorein7.add("&a");
		lorein7.add("&7You stripped this Block &b<times>x &7times");
		lorein7.add("&7You earned &c<earned_today>$ &7by this Block today");
		lorein7.add("&7You earned &c<earned>$ &7by this Block in total");
		
		
		cfg.set("ID.STRIPLOG.OAK_LOG.RewardsGUI.Lore", lore5);
		cfg.set("ID.STRIPLOG.OAK_LOG.RewardsGUI.LoreAddWhenOwnJob", lorein7);
		
		cfg.set("ID.STRIPLOG.OAK_LOG.RewardsGUI.Sorting", 2);
		
		cfg.set("Levels.Config.Base", 25.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 50);
		cfg.set("Levels.Config.MaxLevel", 25);
		
		try {
			cfg.save(file);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	public void setMinerTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "MINER.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>(); 
		actions.add("BREAK");
		
		ArrayList<String> worlds = new ArrayList<String>(); 
		worlds.add("world");
		
		cfg.set("Display", "&bMiner");
		cfg.set("Actions", actions);
		cfg.set("Icon", "DIAMOND_PICKAXE");
		 
		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		
		
		ArrayList<String> cmds = new ArrayList<String>(); 
		cmds.add("say <name> & job <job>");
		
		
		
		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);
		
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 9);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);
		
		//stats messages
		cfg.set("Desc", "&7Earn Money while breaking blocks!");
		
		ArrayList<String> stats = new ArrayList<String>(); 
		stats.add("&7Level: &6#<level>");
		stats.add("&7Exp: &7<exp>/<need>");
		stats.add("&7Bought Date: &7<date_bought>");
		stats.add("&7Joined Date: &7<joined_date>");
		stats.add("&7");
		stats.add("&7Times worked: &7<times>x");
		stats.add("&7Earned Today: &7<earned_today>$");
		stats.add("&7Earned all time: &7<earned_all>$"); 
		stats.add("&8");
		
		ArrayList<String> stats2 = new ArrayList<String>(); 
		stats2.add("&7Level: &6#<level>");
		stats2.add("&7Exp: &7<exp>/<need>");
		stats2.add("&8");
		
		cfg.set("Stats.In", stats);
		cfg.set("Stats.Look", stats2);
		
		//ids
		
		ArrayList<String> ids = new ArrayList<String>(); 
		ids.add("STONEBLOCK");
		ids.add("IRON_ORE");
		
		cfg.set("ID.BREAK.List", ids);
		
		//stone
		cfg.set("ID.BREAK.STONEBLOCK.Chance", 90);
		cfg.set("ID.BREAK.STONEBLOCK.ID", "STONE");
		cfg.set("ID.BREAK.STONEBLOCK.Icon", "STONE");
		cfg.set("ID.BREAK.STONEBLOCK.Money", 2);
		cfg.set("ID.BREAK.STONEBLOCK.Exp", 3);
		cfg.set("ID.BREAK.STONEBLOCK.Points", 0.5);
		cfg.set("ID.BREAK.STONEBLOCK.Display", "&7Stone");
		cfg.set("ID.BREAK.STONEBLOCK.RewardsGUI.Display", "&8< &7Stone &8>");
		cfg.set("ID.BREAK.STONEBLOCK.RewardsGUI.Desc", "&7Stone is a great material!");
		
		ArrayList<String> lore5 = new ArrayList<String>(); 
		lore5.add("&8&m--------------"); 
		lore5.add("&7Reward&8: &a<money>");
		lore5.add("&7Exp&8: &a<exp>");
		lore5.add("&7Chance&8: &c<chance>");
		lore5.add("&7Points&8: &a<points>");

		ArrayList<String> lorein7 = new ArrayList<String>(); 
		lorein7.add("&a");
		lorein7.add("&7You broke this Block &b<times>x &7times");
		lorein7.add("&7You earned &c<earned_today>$ &7by this Block today");
		lorein7.add("&7You earned &c<earned>$ &7by this Block in total");
		
		
		cfg.set("ID.BREAK.STONEBLOCK.RewardsGUI.Lore", lore5);
		cfg.set("ID.BREAK.STONEBLOCK.RewardsGUI.LoreAddWhenOwnJob", lorein7);
		
		cfg.set("ID.BREAK.STONEBLOCK.RewardsGUI.Icon", "STONE");
		cfg.set("ID.BREAK.STONEBLOCK.RewardsGUI.Sorting", 2);
		
		//iron ore
		cfg.set("ID.BREAK.IRON_ORE.Chance", 90);
		cfg.set("ID.BREAK.IRON_ORE.ID", "IRON_ORE");
		cfg.set("ID.BREAK.IRON_ORE.Icon", "IRON_ORE");
		cfg.set("ID.BREAK.IRON_ORE.Money", 2);
		cfg.set("ID.BREAK.IRON_ORE.Exp", 3);
		cfg.set("ID.BREAK.IRON_ORE.Points", 0.5);
		cfg.set("ID.BREAK.IRON_ORE.Display", "&fIron Ore");
		cfg.set("ID.BREAK.IRON_ORE.RewardsGUI.Display", "&8< &fIron Ore &8>"); 
		
		ArrayList<String> lore4 = new ArrayList<String>(); 
		lore4.add("&8&m--------------"); 
		lore4.add("&7Reward&8: &a<money>");
		lore4.add("&7Exp&8: &a<exp>");
		lore4.add("&7Chance&8: &c<chance>");
		lore4.add("&7Points&8: &a<points>");

		ArrayList<String> lorein4 = new ArrayList<String>(); 
		lorein4.add("&a");
		lorein4.add("&7You broke this Block &b<times>x &7times");
		lorein4.add("&7You earned &c<earned_today>$ &7by this Block today");
		lorein4.add("&7You earned &c<earned>$ &7by this Block in total");
		
		
		cfg.set("ID.BREAK.IRON_ORE.RewardsGUI.Lore", lore4);
		cfg.set("ID.BREAK.IRON_ORE.RewardsGUI.LoreAddWhenOwnJob", lorein4);
		
		cfg.set("ID.BREAK.IRON_ORE.RewardsGUI.Icon", "IRON_ORE");
		cfg.set("ID.BREAK.IRON_ORE.RewardsGUI.Sorting", 1);
		
		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		
		try {
			cfg.save(file);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

}
