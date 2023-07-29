package code.warsteiner.jobs.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import code.warsteiner.jobs.GreenJobs; 
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobAction;
import code.warsteiner.jobs.utils.templates.JobID;

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
					
			     	HashMap<String, String> lvl = new HashMap<String, String>();
				 
			     	lvl.put("base", ""+cfg.getDouble("Levels.Config.Base"));
			    	lvl.put("percent", ""+cfg.getDouble("Levels.Config.AddPercentValueLevelUp"));
			    	lvl.put("maxlvl", ""+cfg.getDouble("Levels.Config.MaxLevel"));
			     	
					//load ids
					
					HashMap<String, ArrayList<String>> ac = new HashMap<String, ArrayList<String>>();
					HashMap<String, JobID> everyid = new HashMap<String, JobID>();
					ArrayList<String> arraylist_sorted02 = new ArrayList<String>();
					
					HashMap<String, String> rw = new HashMap<String, String>();
					
					if(cfg.contains("RewardMessages.Actionbar")) {
						rw.put("ACTIONBAR", cfg.getString("RewardMessages.Actionbar"));
					}
					
					if(cfg.contains("RewardMessages.BossBar")) {
						rw.put("BOSSBAR", cfg.getString("RewardMessages.BossBar"));
					}
					
					
					if(cfg.contains("RewardMessages.Message")) {
						rw.put("MESSAGE", cfg.getString("RewardMessages.Message"));
					}
					
					
					
					actions.forEach((act) -> {
						 
						ArrayList<String> list = null;
						
						if(ac.get(act) == null) {
							list = new ArrayList<String>();
						} else {
							list = ac.get(act);
						}
						
						List<String> ids = cfg.getStringList("ID."+act+".List");
					 
						for(String id : ids) {
							int chance = cfg.getInt("ID."+act+"."+id+".Chance");
							String id_item = cfg.getString("ID."+act+"."+id+".ID");
							double money = cfg.getDouble("ID."+act+"."+id+".Money");
							String icond = cfg.getString("ID."+act+"."+id+".Icon");
							double exp = cfg.getDouble("ID."+act+"."+id+".Exp");
							double points = cfg.getDouble("ID."+act+"."+id+".Points");
							
							List<String> commands = cfg.getStringList("ID."+act+"."+id+".Commands");
							
							String display_item = cfg.getString("ID."+act+"."+id+".Display");
							String idreewards_display = cfg.getString("ID."+act+"."+id+".RewardsGUI.Display");
							List<String> lore = cfg.getStringList("ID."+act+"."+id+".RewardsGUI.Lore");
							List<String> lore2 = cfg.getStringList("ID."+act+"."+id+".RewardsGUI.LoreAddWhenOwnJob");
							String idreewards_desc = cfg.getString("ID."+act+"."+id+".RewardsGUI.Desc");
							int idreewards_st = cfg.getInt("ID."+act+"."+id+".RewardsGUI.Sorting");
							int idreewards_data = cfg.getInt("ID."+act+"."+id+".RewardsGUI.CustomModelData");
							String idreewards_icon = cfg.getString("ID."+act+"."+id+".RewardsGUI.Icon");
							 
							JobID jobid = new JobID(id_item, icond, lore, lore2, chance, money, exp, points, display_item, idreewards_display, idreewards_desc, idreewards_icon, idreewards_st, idreewards_data,(ArrayList<String>) commands);
							
							everyid.put(act+"_"+id_item, jobid);
							arraylist_sorted02.add(act+"_"+id_item);
							list.add(id_item);
							
							Bukkit.getConsoleSender().sendMessage("§5Register "+id_item+" for "+job_id+" | action "+act+"...");
						};
						
						ac.put(act, list);
						
					 
						 
					});
					
					HashMap<String, Integer> to_sort_numbers = new HashMap<String, Integer>();
				 
					
					everyid.forEach((id, type) -> {
						
					 
							int numero = type.getSortNumber();
							
							to_sort_numbers.put(id, numero);
					 
						
					});
					 
					Map<String, Integer> sorted_after_numbers = to_sort_numbers.entrySet().stream()
				               .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				               .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
				     
				 
					
					Job job = new Job(cfg, job_id, display, actions, icon, icon_data, command_join, command_leave, color, slot, price, worlds, desc, stats_in, stats_look, ac, everyid, lvl, sorted_after_numbers, arraylist_sorted02, rw);
				
					plugin.getJobAPI().AddActiveJob(job);
				}
				
			}
		}
	}
	
	
}












