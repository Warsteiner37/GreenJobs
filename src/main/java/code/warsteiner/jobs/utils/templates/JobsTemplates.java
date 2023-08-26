package code.warsteiner.jobs.utils.templates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.sk89q.worldedit.world.entity.EntityType;

import code.warsteiner.jobs.GreenJobs;
import io.lumine.mythic.api.adapters.AbstractBossBar.BarColor;

public class JobsTemplates {

	private GreenJobs plugin = GreenJobs.getPlugin();

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
	}
	
	public void setMilkmanTemplate() {

		File file = new File("plugins/GreenJobs/jobs/", "milkman.yml");

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

		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("say <name> & job <job>");

		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);

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
				
				if(type != "MUSHROOM_COW") {
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

				if(type != "MUSHROOM_COW") {
					cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", type + "_SPAWN_EGG");
				} else {
					cfg.set("ID." + action + "." + type + ".RewardsGUI.Icon", "RED_MUSHROOM");
				}
				
				cfg.set("ID." + action + "." + type + ".RewardsGUI.Sorting", 2);
			}

		}

		cfg.set("Levels.Config.Base", 29.5);
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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

		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("say <name> & job <job>");

		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);

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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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

		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("say <name> & job <job>");

		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);

		cfg.set("ColorOfBar", "YELLOW");
		cfg.set("CustomGlassPlateColor", "BLUE_STAINED_GLASS_PANE");
		cfg.set("Slot", 16);
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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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

		File file = new File("plugins/GreenJobs/jobs/", "shepherd.yml");

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

		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("say <name> & job <job>");

		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);

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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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

		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("say <name> & job <job>");

		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);

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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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

		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("say <name> & job <job>");

		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);

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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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

		File file = new File("plugins/GreenJobs/jobs/", "guard.yml");

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

		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("say <name> & job <job>");

		cfg.set("Commands.Join", cmds);
		cfg.set("Commands.Quit", cmds);

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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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

		cfg.set("Display", "&eBuilder (Example One)");
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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 50);
		cfg.set("Levels.Config.MaxLevel", 25);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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
		cfg.set("Levels.Config.AddPercentValueLevelUp", 30);
		cfg.set("Levels.Config.MaxLevel", 30);
		cfg.set("Levels.Config.DefaultDisplay", "&7Level <level>");
		
		cfg.set("Levels.3.CustomDisplay", "&e&lLevel <level>");
		cfg.set("Levels.3.CustomIcon", "EMERALD");
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

}
