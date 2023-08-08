package code.warsteiner.jobs.support;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.JobAPI;
import code.warsteiner.jobs.basic.BasicPluginManager;
import code.warsteiner.jobs.manager.PlayerDataManager;
import code.warsteiner.jobs.utils.templates.JobsPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceHolderManager extends PlaceholderExpansion {
	
	private GreenJobs plugin = GreenJobs.getPlugin();


	@Override
	public @NotNull String getIdentifier() { 
		return "GreenJobs";
	}

	@Override
	public @NotNull String getAuthor() { 
		return "Warsteiner37";
	}

	@Override
	public @NotNull String getVersion() { 
		return "v1";
	}

	@Override
	public String onRequest(OfflinePlayer player, String pr) {
	
		UUID UUID = player.getUniqueId();
		 
		PlayerDataManager data = plugin.getPlayerDataManager();
		JobAPI job = plugin.getJobAPI();
		
		
		FileConfiguration cfg = plugin.getFileManager().getPAPIConfig();
		BasicPluginManager basic = plugin.getBasicPluginManager();
		
		
		if(data.getUUIDArrayList().contains(""+UUID)) {
			
			JobsPlayer jb = data.getJobsPlayerList().get(UUID);
			
			if (pr.equalsIgnoreCase("current_jobs")) {
				 
				if(jb.getCurrentJobs().isEmpty()) {
					return basic.toHex(cfg.getString("HasNoJob"));
				} else if(jb.getCurrentJobs().size() == 1) {
					return job.getLoadedJobsHash().get(jb.getCurrentJobs().get(0)).getDisplay();
				} else {
					
					String d = null;
					
					for(String b : jb.getCurrentJobs()) {
						
						String display = job.getLoadedJobsHash().get(b).getDisplay();
						
						if(d == null) {
							d = ""+display;
						} else {
							d = d+"&7, "+display;
						}
						
					}
					
					return d;
					
				}
				 
			} else if(pr.contains("_displayname")) {
				
				String[] split = pr.split("_");
				
				String d = split[0].toUpperCase(); 
				if(job.existJob(d)) {
					
					return job.getLoadedJobsHash().get(d).getDisplay();
					
				} else {
					return basic.toHex(cfg.getString("UnknownJob"));
				}
				
			} else if(pr.contains("_level")) {
				
				String[] split = pr.split("_");
				
				String d = split[0].toUpperCase(); 
				if(job.existJob(d)) {
					
					if(jb.getOwnedJobs().contains(d)) {
						return ""+jb.getJobStats().get(d).getLevel();
					} else {
						return basic.toHex(cfg.getString("NoLevel"));
					}
					
				} else {
					return basic.toHex(cfg.getString("UnknownJob"));
				}
				
			}else if(pr.contains("_displaylevel")) {
				
				String[] split = pr.split("_");
				
				String d = split[0].toUpperCase(); 
				if(job.existJob(d)) {
					
					if(jb.getOwnedJobs().contains(d)) {
						int lv = jb.getJobStats().get(d).getLevel();
						return plugin.getLevelAPI().getDisplayOfLevel(plugin.getJobAPI().getLoadedJobsHash().get(d), lv);
					} else {
						return basic.toHex(cfg.getString("NoLevel"));
					}
					
				} else {
					return basic.toHex(cfg.getString("UnknownJob"));
				}
				
			} else if(pr.contains("_exp")) {
				
				String[] split = pr.split("_");
				
				String d = split[0].toUpperCase(); 
				if(job.existJob(d)) {
					
					if(jb.getOwnedJobs().contains(d)) {
						return plugin.getBasicPluginManager().Format(jb.getJobStats().get(d).getExp());
					} else {
						return basic.toHex(cfg.getString("NoExp"));
					}
					
				} else {
					return basic.toHex(cfg.getString("UnknownJob"));
				}
				
			} else if(pr.contains("_need")) {
				
				String[] split = pr.split("_");
				
				String d = split[0].toUpperCase(); 
				if(job.existJob(d)) {
					
					if(jb.getOwnedJobs().contains(d)) {
						return plugin.getBasicPluginManager().Format(jb.getJobStats().get(d).getNeed());
					} else {
						return basic.toHex(cfg.getString("NoNeed"));
					}
					
				} else {
					return basic.toHex(cfg.getString("UnknownJob"));
				}
				
			}
		}
		return null;
		
		
	}
	
}
