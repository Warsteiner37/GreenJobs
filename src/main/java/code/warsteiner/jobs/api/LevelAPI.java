package code.warsteiner.jobs.api;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.basic.BasicGUIManager;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobLevel;
import code.warsteiner.jobs.utils.templates.JobStats;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class LevelAPI {

	private GreenJobs plugin = GreenJobs.getPlugin();
 
	public Double getNeedForLvlOne(Job job) { 
		return Double.valueOf( job.getLevelOptionsList().get("Base"));
		
	}
	
	public String getDisplayOfLevel(Player player, Job job, int level) {
		 
		JobLevel lvl = job.getLevel(level);
		
		String use = job.getLevelOptionsList().get("DefaultDisplay");
		
		if(lvl.hasDisplay()) {
			
			use = lvl.getDisplay();
			
		} 
	 
		return plugin.getBasicPluginManager().toHex(player, use.replaceAll("<level>", ""+level));
		
	}
	
	public void checkLevel(JobsPlayer jb, Job job) {
		
		Player player = Bukkit.getPlayer(jb.getName());
		 
		JobStats stats = jb.getJobStats().get(job.getID());
	
		FileConfiguration m = plugin.getFileManager().getConfigConfig();
		BasicPluginManager bc = plugin.getBasicPluginManager();
		
		double exp = stats.getExp();
		double need = stats.getNeed();
		int level = stats.getLevel();
		
		if(!isMaxLevel(jb, job.getID())) {
			if(exp >= need || exp == need) {
				 
				int new_level = level + 1;

				JobLevel lvl = job.getLevel(new_level);
				
				String dis = getDisplayOfLevel(player, job, new_level);
				
				double one_percent = need / 100;
				
				double add_more = Double.valueOf( job.getLevelOptionsList().get("AddPercentValueLevelUp"));
				
				double calc = need  + one_percent * add_more + 15;
				
				stats.setLevel(new_level);
				stats.setExp(0);
				stats.setNeed(calc);
				
				plugin.getBasicPluginManager().playSound(player, "PLAYER_LEVEL_UP");
				
				
				if(lvl.hasCommands()) {
					
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					 
					List<String> commands = lvl.getCommands();
					  
					for(String command : commands) { 
						
						Bukkit.getScheduler().runTask(plugin, () -> {

							Bukkit.dispatchCommand(console, command.replaceAll("<level_display>", dis).replaceAll("<level>", ""+level).replaceAll("<job>", job.getID()).replaceAll("<name>", player.getName()));
						});
						 
					}
					
				}
				
				plugin.getEco().depositPlayer(player, lvl.getReward());  
				
				
				if(m.getBoolean("LevelUp.Titles.Enabled")) {

					String t1 = m.getString("LevelUp.Titles.T1").replaceAll("<level>", ""+new_level).replaceAll("<job>", job.getDisplay(player));
					String t2 = m.getString("LevelUp.Titles.T2").replaceAll("<level>", ""+new_level).replaceAll("<job>", job.getDisplay(player));
					
					player.sendTitle(bc.toHex(player, t1), bc.toHex(player, t2));
				}
				
				if(m.getBoolean("LevelUp.Message.Enabled")) {
					String me = m.getString("LevelUp.Message.Message").replaceAll("<prefix>", plugin.getMessageManager().getPrefix(player)).replaceAll("<level>", ""+new_level).replaceAll("<job>", job.getDisplay(player));;
					
					player.sendMessage(bc.toHex(player, me));
				}
				
			}
		}
		
	}
	
	public boolean isLastLevelByInt(String job, int i) {
		Job j = plugin.getJobAPI().getLoadedJobsHash().get(job);
		
		int max = Integer.parseInt( j.getLevelOptionsList().get("MaxLevel")) - 1;
		
		if(i >= max || i == max) {
			return true;
		}
		return false;
	 
	}
 
	public boolean isMaxLevel(JobsPlayer jb, String job) {
		
		Job j = plugin.getJobAPI().getLoadedJobsHash().get(job);
		
		int max = Integer.parseInt( j.getLevelOptionsList().get("MaxLevel")) - 1;
	 
		if(jb.getJobStats().get(job.toUpperCase()).getLevel() >= max) {
			return true;
		}
		
		if(jb.getJobStats().get(job.toUpperCase()).getLevel() == max) {
			return true;
		}
		
		return false;
	}
	
}
