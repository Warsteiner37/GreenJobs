package code.warsteiner.jobs.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import code.warsteiner.jobs.GreenJobs; 
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobAction;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class JobAPI {

	private GreenJobs plugin = GreenJobs.getPlugin();

	private HashMap<String, Job> jobs = new HashMap<String, Job>();
	private ArrayList<String> listed = new ArrayList<String>();
 
	public HashMap<String, Job> getLoadedJobsHash() {
		return this.jobs;
	}
	 
	public ArrayList<String> getLoadedJobsArray() {
		return this.listed;
	}
	
	public void clearLists() {
		this.jobs.clear();
		this.listed.clear();
		this.sorted_actions.clear();
	}
	
	public void AddActiveJob(Job job) {
	 
		this.jobs.put(job.getID(), job);
		this.listed.add(job.getID());
	 
	}
	
	public boolean existJob(String job) {
		return this.listed.contains(job.toUpperCase());
	}
	
	private HashMap<String, ArrayList<String>> sorted_actions = new HashMap<String, ArrayList<String>>();
	
	public String canWorkInRegion(Location loc, String flag) {
		if (plugin.isInstalled("WorldGuard")) {

			return plugin.getWorldGuardSupport().checkFlag(loc, flag);

		}
		return "ALLOW";
	}
	
	public boolean isInWorlds(Location loc, Job job) {
		
		FileConfiguration config = plugin.getFileManager().getJobsSettings();
		
		String world = loc.getWorld().getName();
		
		if(job.getWorlds().contains(world)) {
			return true;
		}
		
		if(config.getStringList("GlobalJobWorlds").contains(world)) {
			return true;
		}
		
		return false;
		
	}

	
	public void sortJobsAfterActions() {
	 
		for(String action : plugin.getJobActionManager().getArrayListTwo()) { 
			ArrayList<String> current = null;
			
			
			
			JobAction rl = plugin.getJobActionManager().getHashMap().get(action);
		 
			String d = rl.getID().toUpperCase();
			
			if(this.sorted_actions.get(d) == null) {
				current = new ArrayList<String>();
			} else {
				current = this.sorted_actions.get(d);
			}
			
			for(String listed : this.getLoadedJobsArray()) {
				
				Job real = this.getLoadedJobsHash().get(listed);
				
				List<String> act = real.getActions();
				
				if(act.contains(rl.getID())) { 
					current.add(real.getID());
				}
				
			}
			
			this.sorted_actions.put(d, current);
			  
		}
		
	}
	
	public boolean hasJobsByAction(String ac) {
		 
		return this.sorted_actions.get(ac) != null;
	}
	
	public ArrayList<String> getJobsByAction(String ac) {
		return this.sorted_actions.get(ac);
	}
	
	public HashMap<String, ArrayList<String>> getSortedActions() {
		return this.sorted_actions;
	}
	
}
