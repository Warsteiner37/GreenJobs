package code.warsteiner.jobs.utils.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerAddNewCurrentJobEvent;
import code.warsteiner.jobs.utils.custom.PlayerAddNewOwnedJobEvent;
import code.warsteiner.jobs.utils.custom.PlayerLeaveAJobEvent;
import code.warsteiner.jobs.utils.custom.PlayerLeaveAllJobsEvent;

public class JobsPlayer {

	private GreenJobs plugin = GreenJobs.getPlugin();

	
	private UUID ID;
	private String Name;

	private int max;
	private double points;
	
	
	private double salary;
	private String salary_date;
	
	
	private List<String> current_jobs;
	private List<String> owned_jobs;

	private HashMap<String, JobStats> jobs_stats;

	private ArrayList<JobsMultiplier> multis;

	private int skill_points;
	private HashMap<String, PlayerSkill> skills = new HashMap<String, PlayerSkill>();

	public JobsPlayer(UUID id, String name, int max, double points, double salary, String salary_date, List<String> current_jobs,
			List<String> owned_jobs, HashMap<String, JobStats> job_stats, ArrayList<JobsMultiplier> multis,
			int skill_points, HashMap<String, PlayerSkill> skills) {

		this.ID = id;
		this.Name = name;

		this.max = max;
		this.points = points;
		this.salary = salary;
		this.salary_date = salary_date;
		this.current_jobs = current_jobs;
		this.owned_jobs = owned_jobs;
		
		this.jobs_stats = job_stats;
		this.multis = multis;
		this.skill_points = skill_points;
		this.skills = skills;
	}
 
	public void addPoints(double d) {
		this.points = this.points + d;
	}
	
	public HashMap<String, PlayerSkill> getSkillList() {
		
		HashMap<String, PlayerSkill> used = null;
		 
		if(this.skills == null) {
			used = new HashMap<String, PlayerSkill>();
		} else {
			used = this.skills;
		}
		
		return used;
	}
	
	public int getSkillPoints() {
		return this.skill_points;
	}
	
	public ArrayList<JobsMultiplier> getMultipliers() {
		return this.multis;
	}
	
	public HashMap<String, JobStats> getJobStats() {
		return this.jobs_stats;
	}
	
	public List<String> getOwnedJobs() {
		
		List<String> used = null;
		 
		if(this.owned_jobs == null) {
			used = new ArrayList<String>();
		} else {
			used = this.owned_jobs;
		}
		
		return used;
	}
	
	public void addOwnedJob(String job) {
		List<String> jobs = getOwnedJobs();
		
		String date = GreenJobs.getPlugin().getBasicPluginManager().getDateTodayFromCal();

		
		Job d = plugin.getJobAPI().getLoadedJobsHash().get(job);

		JobStats stat = new JobStats(1, 0, plugin.getLevelAPI().getNeedForLvlOne(d), 0, date, date, 0, null, null, null, null, null, null);

		new BukkitRunnable() {
			
			@Override
			public void run() {
				PlayerAddNewOwnedJobEvent event = new PlayerAddNewOwnedJobEvent(ID, job);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}.runTask(plugin);
		 
		jobs.add(job);

		this.jobs_stats.put(job, stat);
		this.owned_jobs = jobs;
	}
	
	public List<String> getCurrentJobs() {
		
		List<String> used = null;
		 
		if(this.current_jobs == null) {
			used = new ArrayList<String>();
		} else {
			used = this.current_jobs;
		}
		
		return used;
	}
	
	public void removeAllCurrentJobs() {
		 
		new BukkitRunnable() {
			
			@Override
			public void run() {
				PlayerLeaveAllJobsEvent event = new PlayerLeaveAllJobsEvent(ID);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}.runTask(plugin);
		
		this.current_jobs = new ArrayList<String>();
	}
	
	public void removeCurrentJob(String job) {
		List<String> jobs = getCurrentJobs();
		 
		new BukkitRunnable() {
			
			@Override
			public void run() {
				PlayerLeaveAJobEvent event = new PlayerLeaveAJobEvent(ID, job);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}.runTask(plugin);
		
		jobs.remove(job);
		
		this.current_jobs = jobs;
	}
	
	public void addCurrentJob(String job, String date) {
		List<String> jobs = getCurrentJobs();
		 
		new BukkitRunnable() {
			
			@Override
			public void run() { 
				PlayerAddNewCurrentJobEvent event = new PlayerAddNewCurrentJobEvent(ID, job);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}.runTask(plugin);
		
		JobStats stats = this.jobs_stats.get(job);
		
		stats.updateJoinedDate(date);
		
		jobs.add(job);
		
		this.jobs_stats.put(job, stats);
		this.current_jobs = jobs;
	}
	
	public String getSalaryDate() {
		return this.salary_date;
	}
	
	public double getSalary() {
		return this.salary;
	}
	
	public double getPoints() {
		return this.points;
	}
	
	public int getMaxJobs() {
		return this.max;
	}

	public boolean isOnline() {
		return Bukkit.getPlayer(this.ID).isOnline();
	}

	public UUID getUUID() {
		return this.ID;
	}

	public String getName() {
		return this.Name;
	}

}
