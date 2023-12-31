package code.warsteiner.jobs.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.templates.Job; 
import code.warsteiner.jobs.utils.templates.JobID;
import code.warsteiner.jobs.utils.templates.JobLevel;

public class JobLoadAPI {

	private GreenJobs plugin = GreenJobs.getPlugin();

	public void loadJobsbyStart() {
		File dataFolder = new File("plugins/GreenJobs/jobs/");
		File[] files = dataFolder.listFiles();

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String name = files[i].getName();
				File file = files[i];

				if (file.isFile()) {

					YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

					String job_id = name.replaceAll(".yml", " ").replaceAll(" ", "");

					String display = cfg.getString("Display");

					List<String> actions = cfg.getStringList("Actions");

					String icon = cfg.getString("Icon");
					int icon_data = cfg.getInt("CustomModelData");

					List<String> command_join = cfg.getStringList("Commands.Join");
					List<String> command_leave = cfg.getStringList("Commands.Quit");

					String color = cfg.getString("ColorOfBar");

					int slot = cfg.getInt("Slot");

					double price = cfg.getDouble("Price");

					List<String> worlds = cfg.getStringList("Worlds");

					String desc = cfg.getString("Desc");

					List<String> stats_in = cfg.getStringList("Stats.In");

					List<String> stats_look = cfg.getStringList("Stats.Look");
 
					HashMap<String, ArrayList<String>> ac = new HashMap<String, ArrayList<String>>();
					HashMap<String, JobID> everyid = new HashMap<String, JobID>();
					ArrayList<String> arraylist_sorted02 = new ArrayList<String>();

					HashMap<String, String> rw = new HashMap<String, String>();

					if (cfg.contains("RewardMessages.Actionbar")) {
						rw.put("ACTIONBAR", cfg.getString("RewardMessages.Actionbar"));
					}

					if (cfg.contains("RewardMessages.BossBar")) {
						rw.put("BOSSBAR", cfg.getString("RewardMessages.BossBar"));
					}

					if (cfg.contains("RewardMessages.Message")) {
						rw.put("MESSAGE", cfg.getString("RewardMessages.Message"));
					}

					actions.forEach((act) -> {

						ArrayList<String> list = null;

						if (ac.get(act) == null) {
							list = new ArrayList<String>();
						} else {
							list = ac.get(act);
						}

						List<String> ids = cfg.getStringList("ID." + act + ".List");

						if (!cfg.getBoolean("PayForEveryPossibleBlock")) {

							for (String id : ids) {
								int chance = cfg.getInt("ID." + act + "." + id + ".Chance");
								String id_item = cfg.getString("ID." + act + "." + id + ".ID");
								double money = cfg.getDouble("ID." + act + "." + id + ".Money");
								String icond = cfg.getString("ID." + act + "." + id + ".Icon");
								double exp = cfg.getDouble("ID." + act + "." + id + ".Exp");
								double points = cfg.getDouble("ID." + act + "." + id + ".Points");

								List<String> commands = cfg.getStringList("ID." + act + "." + id + ".Commands");

								String display_item = cfg.getString("ID." + act + "." + id + ".Display");
								String idreewards_display = cfg
										.getString("ID." + act + "." + id + ".RewardsGUI.Display");
								List<String> lore = cfg.getStringList("ID." + act + "." + id + ".RewardsGUI.Lore");
								List<String> lore2 = cfg
										.getStringList("ID." + act + "." + id + ".RewardsGUI.LoreAddWhenOwnJob");
								String idreewards_desc = cfg.getString("ID." + act + "." + id + ".RewardsGUI.Desc");
								int idreewards_st = cfg.getInt("ID." + act + "." + id + ".RewardsGUI.Sorting");
								int idreewards_data = cfg
										.getInt("ID." + act + "." + id + ".RewardsGUI.CustomModelData");
								String idreewards_icon = cfg.getString("ID." + act + "." + id + ".RewardsGUI.Icon");

								JobID jobid = new JobID(id_item, icond, lore, lore2, chance, money, exp, points,
										display_item, idreewards_display, idreewards_desc, idreewards_icon,
										idreewards_st, idreewards_data, (ArrayList<String>) commands);

								everyid.put(act + "_" + id_item, jobid);
								arraylist_sorted02.add(act + "_" + id_item);
								list.add(id_item);

								 
							}
							 
						} else {

							int ilikethis = 0;
							
							for (Material m : Material.values()) {

								if (m.isBlock()) {

									List<String> ignore = cfg.getStringList("IgnoreIDs");

									if (!ignore.contains(m.toString())) {

										if (m.isItem()) {

											if (m != null) {

												String realtype = m.toString().toUpperCase();
												String used_in_file = "ALL_BLOCKS";

												int chance = cfg.getInt("ID." + act + "." + used_in_file + ".Chance");

												double money = cfg
														.getDouble("ID." + act + "." + used_in_file + ".Money");

												double exp = cfg.getDouble("ID." + act + "." + used_in_file + ".Exp");
												double points = cfg
														.getDouble("ID." + act + "." + used_in_file + ".Points");

												List<String> commands = cfg
														.getStringList("ID." + act + "." + used_in_file + ".Commands");

												String named = WordUtils.capitalizeFully(realtype.toLowerCase())
														.replaceAll("_", " ");

												String display_item = cfg
														.getString("ID." + act + "." + used_in_file + ".Display")
														.replaceAll("<block>", named);
												String idreewards_display = cfg.getString(
														"ID." + act + "." + used_in_file + ".RewardsGUI.Display")
														.replaceAll("<block>", named);
												List<String> lore = cfg.getStringList(
														"ID." + act + "." + used_in_file + ".RewardsGUI.Lore");
												List<String> lore2 = cfg.getStringList("ID." + act + "." + used_in_file
														+ ".RewardsGUI.LoreAddWhenOwnJob");
												String idreewards_desc = cfg.getString(
														"ID." + act + "." + used_in_file + ".RewardsGUI.Desc");
												int idreewards_st = ilikethis;
												int idreewards_data = cfg.getInt("ID." + act + "." + used_in_file
														+ ".RewardsGUI.CustomModelData");

												JobID jobid = new JobID(realtype, realtype, lore, lore2, chance, money,
														exp, points, display_item, idreewards_display, idreewards_desc,
														realtype, idreewards_st, idreewards_data,
														(ArrayList<String>) commands);

												everyid.put(act + "_" + realtype, jobid);
												arraylist_sorted02.add(act + "_" + realtype);
												list.add(realtype);
												
												ilikethis++;
 
											}
										}
									}

								}

							}
						 
						}

						ac.put(act, list);

					});
					 
					HashMap<String, Integer> to_sort_numbers = new HashMap<String, Integer>();

					everyid.forEach((id, type) -> {

						int numero = type.getSortNumber();

						to_sort_numbers.put(id, numero);

					});
					
					HashMap<Integer, JobLevel> levels = new HashMap<Integer, JobLevel>();
					
					double base = cfg.getDouble("Levels.Config.AddPercentValueLevelUp");
					double add = cfg.getDouble("Levels.Config.AddPercentValueLevelUp");
					int maxlevel = cfg.getInt("Levels.Config.MaxLevel");
					String default_display = cfg.getString("Levels.Config.DefaultDisplay");
					
					HashMap<String, String> lvl = new HashMap<String, String>();

					lvl.put("Base", "" + base);
					lvl.put("AddPercentValueLevelUp", "" + add);
					lvl.put("MaxLevel", "" + maxlevel);
					lvl.put("DefaultDisplay", default_display);
					 
					for(int i1=0; i1<maxlevel; i1++) {
						
						String custom_display = cfg.getString("Levels."+i1+".CustomDisplay");
						String custom_item  = cfg.getString("Levels."+i1+".CustomIcon");
						int custom_data  = cfg.getInt("Levels."+i1+".CustomModelData");
						List<String> custom_desc  = cfg.getStringList("Levels."+i1+".Description");
						String song  = cfg.getString("Levels."+i1+".PlaySong");
						double reward = cfg.getDouble("Levels."+i1+".Reward");
						boolean fireworks = cfg.getBoolean("Levels."+i1+".Firework");
						List<String> commands  = cfg.getStringList("Levels."+i1+".Commands");
						
						double earn_more = cfg.getDouble("Levels."+i1+".EarnMorePercent");
						double earn_less = cfg.getDouble("Levels."+i1+".EarnLessPercent");
					 
						JobLevel level = new JobLevel(job_id, i1, custom_desc, custom_display, reward, earn_more, earn_less, commands, custom_item, custom_data, song, fireworks);
						
						levels.put(i1, level);
					}
					
					Bukkit.getConsoleSender().sendMessage("§6Loaded "+to_sort_numbers.size()+"x IDs & "+levels.size()+"x Levels for Job "+job_id+"...");
					 

					Map<String, Integer> sorted_after_numbers = to_sort_numbers.entrySet().stream()
							.sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors
									.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

					Job job = new Job(job_id, display, actions, icon, icon_data, command_join, command_leave,
							color, slot, price, worlds, desc, stats_in, stats_look, ac, everyid, lvl,
							sorted_after_numbers, arraylist_sorted02, rw, levels);

					plugin.getJobAPI().AddActiveJob(job);
				}

			}
		}
	}

}
