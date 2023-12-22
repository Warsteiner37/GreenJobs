package code.warsteiner.jobs.utils.templates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.WordUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.sk89q.worldedit.world.entity.EntityType;

import code.warsteiner.jobs.GreenJobs;
import io.lumine.mythic.api.adapters.AbstractBossBar.BarColor;

public class JobsTemplates {
 
	public void createDefaultJobs() {
		setMinerTemplate();
		setLumberjackTemplate();
		setBuilderTemplate();
		setKillMobTemplate();
		setButcherTemplate();
		setDiggerTemplate();
		setShearTemplate();
		setBuilderV2Template();
		setFisherTemplate();
		setMilkmanTemplate();
		setFarmer_BreakTemplate();
		setTameTemplate();
		setBreedTemplate();
		setCarveTemplate();
		setBerryTemplate();
		setHoneyTemplate();
		setTreasureHunterTemplate();
		setSmeltTemplate();
	}
	
	public void setSmeltTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "SmeltJob.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("SMELT");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");
	 
		cfg.set("Display", "&f&lSmelt Items");
		cfg.set("Actions", actions);
		cfg.set("Icon", "FURNACE");
	 
		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "RED");
		cfg.set("CustomGlassPlateColor", "RED_STAINED_GLASS_PANE");
		cfg.set("Slot", 25);
		cfg.set("Price", 50000);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while burning some fuel!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("IRON_INGOT");
		ids.add("CHARCOAL"); 
		ids.add("GOLD_INGOT"); 
		ids.add("COOKED_BEEF"); 
		ids.add("COOKED_COD"); 
		  
		cfg.set("ID.SMELT.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type);
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

				ArrayList<String> lore5 = new ArrayList<String>();
				lore5.add("&8&m--------------");
				lore5.add("&7Reward&8: &a<money>");
				lore5.add("&7Exp&8: &a<exp>");
				lore5.add("&7Chance&8: &c<chance>");
				lore5.add("&7Points&8: &a<points>");

				ArrayList<String> lorein7 = new ArrayList<String>();
				lorein7.add("&a");
				lorein7.add("&7Collected Times : &b<times>x &7times");
				lorein7.add("&7You earned &c<earned_today>$ &7by this Item today");
				lorein7.add("&7You earned &c<earned>$ &7by this Item in total");

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);
		
		ArrayList<String> desc = new ArrayList<String>();

		desc.add("&c");
		desc.add("&7This is a Example Desc. for the Levels GUI.");
		desc.add("&7");
		desc.add("&7Rewards:");
		desc.add("&a+ &71500$"); 

		cfg.set("Levels.3.Description", desc);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setTreasureHunterTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "TreasureHunter.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("FIND_TREASURE");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");
	 
		cfg.set("Display", "&6Treasure Hunter");
		cfg.set("Actions", actions);
		cfg.set("Icon", "CHEST");
	 
		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "YELLOW");
		cfg.set("CustomGlassPlateColor", "YELLOW_STAINED_GLASS_PANE");
		cfg.set("Slot", 24);
		cfg.set("Price", 100000);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while exploring for treasures!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("DESERT_PYRAMID");
		ids.add("RUINED_PORTAL"); 
		ids.add("BURIED_TREASURE"); 
 
		cfg.set("ID.FIND_TREASURE.List", ids);
 
		HashMap<String, String> icons = new HashMap<String, String>();
		icons.put("DESERT_PYRAMID", "SANDSTONE");
		icons.put("RUINED_PORTAL", "OBSIDIAN");
		icons.put("BURIED_TREASURE", "CHEST");  
		 
		cfg.set("ID.FIND_TREASURE.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);

				cfg.set("ID." + action + "." + type + ".Icon", icons.get(type));

				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

				ArrayList<String> lore5 = new ArrayList<String>();
				lore5.add("&8&m--------------");
				lore5.add("&7Reward&8: &a<money>");
				lore5.add("&7Exp&8: &a<exp>");
				lore5.add("&7Chance&8: &c<chance>");
				lore5.add("&7Points&8: &a<points>");

				ArrayList<String> lorein7 = new ArrayList<String>();
				lorein7.add("&a");
				lorein7.add("&7Times found: &b<times>x &7times");
				lorein7.add("&7You earned &c<earned_today>$ &7by this treasure");
				lorein7.add("&7You earned &c<earned>$ &7by this treasure in total");

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", icons.get(type));

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}
		}


		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);
		
		ArrayList<String> desc = new ArrayList<String>();

		desc.add("&c");
		desc.add("&7This is a Example Desc. for the Levels GUI.");
		desc.add("&7");
		desc.add("&7Rewards:");
		desc.add("&a+ &71500$"); 

		cfg.set("Levels.3.Description", desc);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setHoneyTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "CollectHoney.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("COLLECT_HONEY");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");
	 
		cfg.set("Display", "&eHoney Collector");
		cfg.set("Actions", actions);
		cfg.set("Icon", "HONEY_BOTTLE");
	 
		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "YELLOW");
		cfg.set("CustomGlassPlateColor", "YELLOW_STAINED_GLASS_PANE");
		cfg.set("Slot", 23);
		cfg.set("Price", 50000);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while collecting some fresh honey!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("BEE_NEST");
		ids.add("BEEHIVE"); 

		cfg.set("ID.COLLECT_HONEY.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type);
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);
		
		ArrayList<String> desc = new ArrayList<String>();

		desc.add("&c");
		desc.add("&7This is a Example Desc. for the Levels GUI.");
		desc.add("&7");
		desc.add("&7Rewards:");
		desc.add("&a+ &71500$"); 

		cfg.set("Levels.3.Description", desc);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBerryTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "CollectBerrys.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("COLLECT_BERRYS");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");
	 
		cfg.set("Display", "&aCollect Berrys");
		cfg.set("Actions", actions);
		cfg.set("Icon", "SWEET_BERRIES");
	 
		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "GREEN_STAINED_GLASS_PANE");
		cfg.set("Slot", 22);
		cfg.set("Price", 50000);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while harvesting berries!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("CAVE_VINES_PLANT");
		ids.add("SWEET_BERRY_BUSH"); 
	 
		HashMap<String, String> icons = new HashMap<String, String>();
		icons.put("CAVE_VINES_PLANT", "GLOW_BERRIES");
		icons.put("SWEET_BERRY_BUSH", "SWEET_BERRIES"); 
		
		HashMap<String, String> names = new HashMap<String, String>();
		names.put("CAVE_VINES_PLANT", "Glow Berries");
		names.put("SWEET_BERRY_BUSH", "Sweet Berries"); 
	  
		cfg.set("ID.COLLECT_BERRYS.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);

				cfg.set("ID." + action + "." + type + ".Icon", icons.get(type));

				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + names.get(type) + " &8>");

				ArrayList<String> lore5 = new ArrayList<String>();
				lore5.add("&8&m--------------");
				lore5.add("&7Reward&8: &a<money>");
				lore5.add("&7Exp&8: &a<exp>");
				lore5.add("&7Chance&8: &c<chance>");
				lore5.add("&7Points&8: &a<points>");

				ArrayList<String> lorein7 = new ArrayList<String>();
				lorein7.add("&a");
				lorein7.add("&7Harvested: &b<times>x &7times");
				lorein7.add("&7You earned &c<earned_today>$ &7by this Block today");
				lorein7.add("&7You earned &c<earned>$ &7by this Block in total");

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", icons.get(type));

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);
		
		ArrayList<String> desc = new ArrayList<String>();

		desc.add("&c");
		desc.add("&7This is a Example Desc. for the Levels GUI.");
		desc.add("&7");
		desc.add("&7Rewards:");
		desc.add("&a+ &71500$"); 

		cfg.set("Levels.3.Description", desc);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCarveTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "CarveArtist.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("CARVE");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");

		cfg.set("Display", "&6Carve Artist");
		cfg.set("Actions", actions);
		cfg.set("Icon", "SHEARS");
	 
		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "YELLOW");
		cfg.set("CustomGlassPlateColor", "ORANGE_STAINED_GLASS_PANE");
		cfg.set("Slot", 21);
		cfg.set("Price", 40000);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while for carving art!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("PUMPKIN"); 

		cfg.set("ID.CARVE.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type);
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

				ArrayList<String> lore5 = new ArrayList<String>();
				lore5.add("&8&m--------------");
				lore5.add("&7Reward&8: &a<money>");
				lore5.add("&7Exp&8: &a<exp>");
				lore5.add("&7Chance&8: &c<chance>");
				lore5.add("&7Points&8: &a<points>");

				ArrayList<String> lorein7 = new ArrayList<String>();
				lorein7.add("&a");
				lorein7.add("&7Times carved &b<times>x &7times");
				lorein7.add("&7You earned &c<earned_today>$ &7by this Block today");
				lorein7.add("&7You earned &c<earned>$ &7by this Block in total");

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);
		
		ArrayList<String> desc = new ArrayList<String>();

		desc.add("&c");
		desc.add("&7This is a Example Desc. for the Levels GUI.");
		desc.add("&7");
		desc.add("&7Rewards:");
		desc.add("&a+ &71500$"); 

		cfg.set("Levels.3.Description", desc);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setBreedTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "BreedJob.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("BREED");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");

		cfg.set("Display", "&eBreed Animals");
		cfg.set("Actions", actions);
		cfg.set("Icon", "PIG_SPAWN_EGG");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 20);
		cfg.set("Price", 30000);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while breeding cute animals!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("WOLF");
		ids.add("CAT"); 
		ids.add("COW");
		ids.add("PIG");
		ids.add("CHICKEN");
		ids.add("SHEEP"); 

		cfg.set("ID.BREED.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type+"_SPAWN_EGG");
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type+"_SPAWN_EGG");
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);
		
		ArrayList<String> desc = new ArrayList<String>();

		desc.add("&c");
		desc.add("&7This is a Example Desc. for the Levels GUI.");
		desc.add("&7");
		desc.add("&7Rewards:");
		desc.add("&a+ &71500$"); 

		cfg.set("Levels.3.Description", desc);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setTameTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "TameJob.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("TAME");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");

		cfg.set("Display", "&fTame Animals");
		cfg.set("Actions", actions);
		cfg.set("Icon", "WOLF_SPAWN_EGG");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 19);
		cfg.set("Price", 20000);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while taming cute animals!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("WOLF");
		ids.add("CAT"); 

		cfg.set("ID.TAME.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type+"_SPAWN_EGG");
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type+"_SPAWN_EGG");
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFarmer_BreakTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Farmer_Break.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("FARM_BREAK");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");

		cfg.set("Display", "&aFarmer (Break)");
		cfg.set("Actions", actions);
		cfg.set("Icon", "WHEAT");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block> &8| &a<amount>x");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block> &8| &a<amount>x");

		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 18);
		cfg.set("Price", 10000);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while breaking grown crops!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("WHEAT");
		ids.add("SUGAR_CANE");
		ids.add("CARROTS"); 
		ids.add("BAMBOO");
		ids.add("MELON");
		ids.add("CACTUS");
		ids.add("COCOA");
	 
		HashMap<String, String> icons = new HashMap<String, String>();
		icons.put("WHEAT", "WHEAT");
		icons.put("SUGAR_CANE", "SUGAR_CANE");
		icons.put("CARROTS", "CARROT"); 
		icons.put("BAMBOO", "BAMBOO");
		icons.put("MELON", "MELON");
		icons.put("CACTUS", "CACTUS");
		icons.put("COCOA", "COCOA_BEANS");
	  
		cfg.set("ID.FARM_BREAK.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);

				cfg.set("ID." + action + "." + type + ".Icon", icons.get(type));

				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

				ArrayList<String> lore5 = new ArrayList<String>();
				lore5.add("&8&m--------------");
				lore5.add("&7Reward&8: &a<money>");
				lore5.add("&7Exp&8: &a<exp>");
				lore5.add("&7Chance&8: &c<chance>");
				lore5.add("&7Points&8: &a<points>");

				ArrayList<String> lorein7 = new ArrayList<String>();
				lorein7.add("&a");
				lorein7.add("&7Harvested: &b<times>x &7times");
				lorein7.add("&7You earned &c<earned_today>$ &7by this Block today");
				lorein7.add("&7You earned &c<earned>$ &7by this Block in total");

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", icons.get(type));

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMilkmanTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Milkman.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("MILK");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");

		cfg.set("Display", "&fMilkman");
		cfg.set("Actions", actions);
		cfg.set("Icon", "MILK_BUCKET");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "WHITE");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 17);
		cfg.set("Price", 100);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while milking some sus mobs!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("MUSHROOM_COW");
		ids.add("COW");
		ids.add("GOAT");

		cfg.set("ID.MILK.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);

				if (type != "MUSHROOM_COW") {
					cfg.set("ID." + action + "." + type + ".Icon", type + "_SPAWN_EGG");
				} else {
					cfg.set("ID." + action + "." + type + ".Icon", "RED_MUSHROOM");
				}

				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				if (type != "MUSHROOM_COW") {
					cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type + "_SPAWN_EGG");
				} else {
					cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", "RED_MUSHROOM");
				}

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);
		
		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFisherTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Fisher.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("FISH");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");

		cfg.set("Display", "&bFisher");
		cfg.set("Actions", actions);
		cfg.set("Icon", "FISHING_ROD");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "WHITE");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 16);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money by fishing!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();

		ids.add("TROPICAL_FISH");
		ids.add("PUFFERFISH");
		ids.add("COD");
		ids.add("SALMON");
		ids.add("ROTTEN_FLESH");
		ids.add("NAME_TAG");

		cfg.set("ID.FISH.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type);
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d + " ");
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

				ArrayList<String> lore5 = new ArrayList<String>();
				lore5.add("&8&m--------------");
				lore5.add("&7Reward&8: &a<money>");
				lore5.add("&7Exp&8: &a<exp>");
				lore5.add("&7Chance&8: &c<chance>");
				lore5.add("&7Points&8: &a<points>");

				ArrayList<String> lorein7 = new ArrayList<String>();
				lorein7.add("&a");
				lorein7.add("&7You fished this Mob &b<times>x &7times");
				lorein7.add("&7You earned &c<earned_today>$ &7by this fish today");
				lorein7.add("&7You earned &c<earned>$ &7by this fish in total");

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBuilderV2Template() {

		File file = new File("plugins/GreenJobs/jobs/", "BuilderV2.yml");

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

		cfg.set("Display", "&aBuilder (Example Two)");
		cfg.set("Actions", actions);
		cfg.set("Icon", "QUARTZ_BLOCK");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "YELLOW");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 26);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);
 
		// stats messages
		cfg.set("Desc", "&7Earn Money while placing everything!");

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

		cfg.set("PayForEveryPossibleBlock", true);

		// ids

		ArrayList<String> ids = new ArrayList<String>();

		ids.add("ALL_BLOCKS");

		cfg.set("ID.PLACE.List", ids);

		ArrayList<String> idsignore = new ArrayList<String>();

		idsignore.add("AIR");
		idsignore.add("BARRIER");
		idsignore.add("STRUCTURE_BLOCK");
		idsignore.add("STRUCTURE_VOID");
		idsignore.add("SPAWNER");
		idsignore.add("COMMAND_BLOCK");
		idsignore.add("REPEATING_COMMAND_BLOCK");

		cfg.set("IgnoreIDs", idsignore);

		String action = "PLACE";
		String type = "ALL_BLOCKS";

		cfg.set("ID." + action + "." + type + ".Chance", 90);
		cfg.set("ID." + action + "." + type + ".ID", type);
		cfg.set("ID." + action + "." + type + ".Money", 2);
		cfg.set("ID." + action + "." + type + ".Exp", 3);
		cfg.set("ID." + action + "." + type + ".Points", 0.5);

		cfg.set("ID." + action + "." + type + ".Display", "&e<block>");
		cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e<block> &8>");

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

		cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
		cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setShearTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Shepherd.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("SHEAR");

		ArrayList<String> worlds = new ArrayList<String>();
		worlds.add("world");

		cfg.set("Display", "&fShepherd");
		cfg.set("Actions", actions);
		cfg.set("Icon", "SHEARS");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "WHITE");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 15);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while shearing some sheeps!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();

		for (DyeColor color : DyeColor.values()) {
			ids.add(color.toString().toUpperCase());
		}

		cfg.set("ID.SHEAR.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type + "_WOOL");
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d + " Sheep");
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " Sheep" + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type + "_WOOL");
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDiggerTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Digger.yml");

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

		cfg.set("Display", "&eDigger");
		cfg.set("Actions", actions);
		cfg.set("Icon", "SAND");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "YELLOW");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 13);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while breaking sand or gravel!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("GRAVEL");
		ids.add("SAND");

		cfg.set("ID.BREAK.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type);
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setButcherTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Butcher.yml");

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

		cfg.set("Display", "&aButcher");
		cfg.set("Actions", actions);
		cfg.set("Icon", "WOODEN_SWORD");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 14);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while killing friendly mobs!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("PIG");
		ids.add("COW");
		ids.add("CHICKEN");
		ids.add("HORSE");
		ids.add("RABBIT");
		ids.add("SQUID");
		ids.add("SHEEP");

		cfg.set("ID.KILL_MOB.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type + "_SPAWN_EGG");
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type + "_SPAWN_EGG");
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setKillMobTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Guard.yml");

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

		cfg.set("Display", "&cGuard");
		cfg.set("Actions", actions);
		cfg.set("Icon", "IRON_SWORD");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 12);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);

		// stats messages
		cfg.set("Desc", "&7Earn Money while killing hostile mobs!");

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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("ZOMBIE");
		ids.add("CREEPER");
		ids.add("SKELETON");
		ids.add("BLAZE");
		ids.add("ENDERMAN");
		ids.add("GHAST");
		ids.add("WITCH");

		cfg.set("ID.KILL_MOB.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type + "_SPAWN_EGG");
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type + "_SPAWN_EGG");
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBuilderTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Builder.yml");

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

		cfg.set("Display", "&eBuilder (Example One)");
		cfg.set("Actions", actions);
		cfg.set("Icon", "GRASS_BLOCK");

		cfg.set("RewardMessages.Actionbar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block>");
 
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 11);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);

		// stats messages
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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("GRASS_BLOCK");
		ids.add("QUARTZ_BLOCK");
		ids.add("OAK_LOG");
		ids.add("REDSTONE");

		cfg.set("ID.PLACE.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type);
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setLumberjackTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Lumberjack.yml");

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
 
		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 10);
		cfg.set("Price", 10);
		cfg.set("Worlds", worlds);

		// stats messages
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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("OAK_LOG");
		ids.add("BIRCH_LOG");
		ids.add("ACACIA_LOG");
		ids.add("CHERRY_LOG");
		ids.add("DARK_OAK_LOG");
		ids.add("JUNGLE_LOG");

		cfg.set("ID.BREAK.List", ids);

		ArrayList<String> ids_2nd = new ArrayList<String>();
		ids_2nd.add("STRIPPED_OAK_LOG");
		ids_2nd.add("STRIPPED_BIRCH_LOG");
		ids_2nd.add("STRIPPED_ACACIA_LOG");
		ids_2nd.add("STRIPPED_CHERRY_LOG");
		ids_2nd.add("STRIPPED_DARK_OAK_LOG");
		ids_2nd.add("STRIPPED_JUNGLE_LOG");

		cfg.set("ID.STRIPLOG.List", ids_2nd);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type);
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 25.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMinerTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "Miner.yml");

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
		cfg.set("RewardMessages.BossBar", "&a&l+ &7<money>$ &7by <job> &7for <block> &8| &b<points> P. &8| &a<level_exp_total>&8/&c<level_req> &8| &6&l<level_progress_percent> &8| &c<money_total>$");

		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("say <name> & job <job>");

		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);

		cfg.set("ColorOfBar", "GREEN");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 9);
		cfg.set("Price", 0);
		cfg.set("Worlds", worlds);

		// stats messages
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

		// ids

		ArrayList<String> ids = new ArrayList<String>();
		ids.add("STONE");
		ids.add("COAL_ORE");
		ids.add("IRON_ORE");
		ids.add("COPPER_ORE");
		ids.add("GOLD_ORE");
		ids.add("REDSTONE_ORE");
		ids.add("LAPIS_ORE");
		ids.add("EMERALD_ORE");
		ids.add("DIAMOND_ORE");

		cfg.set("ID.BREAK.List", ids);

		for (String action : actions) {

			for (String type : cfg.getStringList("ID." + action + ".List")) {
				String d = WordUtils.capitalizeFully(type.toLowerCase()).replaceAll("_", " ");

				cfg.set("ID." + action + "." + type + ".Chance", 90);
				cfg.set("ID." + action + "." + type + ".ID", type);
				cfg.set("ID." + action + "." + type + ".Icon", type);
				cfg.set("ID." + action + "." + type + ".Money", 2);
				cfg.set("ID." + action + "." + type + ".Exp", 3);
				cfg.set("ID." + action + "." + type + ".Points", 0.5);
				cfg.set("ID." + action + "." + type + ".Display", "&e" + d);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Display", "&8< &e" + d + " &8>");

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

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Lore", lore5);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.LoreAddWhenOwnJob", lorein7);

				cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type);
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 48);
		cfg.set("Levels.Config.MaxLevel", 48);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");

		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
		cfg.set("Levels.3.Firework", true);
		cfg.set("Levels.3.Reward", 1500);
		
		ArrayList<String> desc = new ArrayList<String>();

		desc.add("&c");
		desc.add("&7This is a Example Desc. for the Levels GUI.");
		desc.add("&7");
		desc.add("&7Rewards:");
		desc.add("&a+ &71500$"); 

		cfg.set("Levels.3.Description", desc);

		ArrayList<String> commands = new ArrayList<String>();

		commands.add("say <name> just reached level 3 in <job>");

		cfg.set("Levels.3.Commands", commands);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
