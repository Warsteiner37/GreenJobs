package code.warsteiner.jobs.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobStats;
import code.warsteiner.jobs.utils.templates.JobsMultiplier;
import code.warsteiner.jobs.utils.templates.JobsPlayer;
import code.warsteiner.jobs.utils.templates.PlayerSkill;

public class PlayerDataManager {

	private GreenJobs plugin = GreenJobs.getPlugin();

	private HashMap<UUID, JobsPlayer> players = new HashMap<UUID, JobsPlayer>();
	private ArrayList<String> list = new ArrayList<String>();

	public HashMap<UUID, JobsPlayer> getJobsPlayerList() {
		return this.players;
	}
	
	public UUID getPlayerByName(String guy) {
		
		for(String p : getUUIDArrayList()) {
			
			JobsPlayer real = getJobsPlayerList().get(UUID.fromString(p.toString()));
			
			if(real.getName().toLowerCase().equalsIgnoreCase(guy.toLowerCase())) {
				return real.getUUID();
			}
			 
		} 
		
		return null;
	}
 
	
	public void addToList(UUID id, JobsPlayer jb) {
		
		HashMap<UUID, JobsPlayer> t1 = this.players;
		
		t1.put(id, jb);
		
		this.players = t1;
		
		ArrayList<String> li = this.list;
		
		li.add(""+id);
		
		this.list = li;
	}

	public ArrayList<String> getUUIDArrayList() {
		return this.list;
	}
 
	public boolean exist(UUID id) {
		return this.players.containsKey(id);
	}

	public JobsPlayer getJobsPlayer(String name, UUID id) {

		if (!this.players.containsKey(id)) {
			createAJobsPlayer(name, id);
		}

		return this.players.get(id);
	}

	public void createAJobsPlayer(String name, UUID id) {

		FileConfiguration cfg = plugin.getFileManager().getConfigConfig();

		int default_max = cfg.getInt("MaxDefaultJobs") - 1;

		List<String> owned = null;
		List<String> current = null;

		HashMap<String, JobStats> stats = new HashMap<String, JobStats>();

		if (cfg.getBoolean("EnabledDefaultJobs")) {
			if (cfg.contains("DefaultJobs")) {
				owned = cfg.getStringList("DefaultJobs");
				String date = plugin.getBasicPluginManager().getDateTodayFromCal();

				for (String job : owned) {
					
					Job d = plugin.getJobAPI().getLoadedJobsHash().get(job);

					JobStats stat = new JobStats(id,1, 0, plugin.getLevelAPI().getNeedForLvlOne(d), 0, date, date, 0, null, null, null, null, null, null);

					stats.put(job, stat);
				}

				if (cfg.getBoolean("AutoJoinDefaultJobs")) {
					current = owned;
				}

			}
		}

		JobsPlayer job_player = new JobsPlayer(id, name, default_max, 0.0, 0.0, "Unknown", current, owned, stats, null,
				0, null);

		HashMap<UUID, JobsPlayer> guys = this.players;

		guys.put(id, job_player);

		ArrayList<String> list2 = this.list;
		
		list2.add(id.toString());
		
		this.players = guys;
		this.list = list2;
	}

}
