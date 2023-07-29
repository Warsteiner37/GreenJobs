package code.warsteiner.jobs.api;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.basic.BasicGUIManager;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobStats;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class LevelAPI {

	private GreenJobs plugin = GreenJobs.getPlugin();
 
	public Double getNeedForLvlOne(Job job) { 
		return Double.valueOf( job.getLevelOptions().get("base"));
		
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
				
				double one_percent = need / 100;
				
				double add_more = job.getConfig().getDouble("Levels.Config.AddPercentValueLevelUp");
				
				double calc = need  + one_percent * add_more + 15;
				
				stats.setLevel(new_level);
				stats.setExp(0);
				stats.setNeed(calc);
				
				plugin.getBasicPluginManager().playSound(player, "PLAYER_LEVEL_UP");
				
				if(m.getBoolean("LevelUp.Titles.Enabled")) {

					String t1 = m.getString("LevelUp.Titles.T1").replaceAll("<level>", ""+new_level).replaceAll("<job>", job.getDisplay());
					String t2 = m.getString("LevelUp.Titles.T2").replaceAll("<level>", ""+new_level).replaceAll("<job>", job.getDisplay());
					
					player.sendTitle(bc.toHex(t1), bc.toHex(t2));
				}
				
				if(m.getBoolean("LevelUp.Message.Enabled")) {
					String me = m.getString("LevelUp.Message.Message").replaceAll("<prefix>", plugin.getMessageManager().getPrefix()).replaceAll("<level>", ""+new_level).replaceAll("<job>", job.getDisplay());;
					
					player.sendMessage(bc.toHex(me));
				}
				
			}
		}
		
	}
 
	public boolean isMaxLevel(JobsPlayer jb, String job) {
		return jb.getJobStats().get(job.toUpperCase()).getLevel() >= plugin.getJobAPI().getLoadedJobsHash().get(job.toUpperCase()).getConfig().getInt("Levels.Config.MaxLevel");
	}
	
}
