package code.warsteiner.jobs.utils.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import code.warsteiner.jobs.GreenJobs;

public class JobLevel {
	
	private GreenJobs plugin = GreenJobs.getPlugin();

	private int lvel; 
	private List<String> des;
	private String display;
	private double reward;
	private double earn_more;
	private double earn_less;
	private List<String> commands;
	private String icon;
	private String job;
	private int icon_data;
	private String song;
	
	public JobLevel(String job, int level,  List<String> custom_desc, String display, double reward, double earn_more, double earn_less, List<String> commands2, String icon, int data, String song) {
	 
		this.lvel = level;
		this.des = custom_desc; 
		this.display = display;
		this.reward = reward;
		this.earn_more = earn_more;
		this.earn_less = earn_less;
		this.commands = commands2;
		this.icon = icon;
		this.job = job;
		this.icon_data = data;
		this.song = song;
	}
	
	public String getSongPath() {
		return this.song;
	}
	
	public boolean hasSong() {
		return this.song != null;
	}
	
	public double getEarnLess() {
		return this.earn_less;
	}
	
	public double getEarnMore() {
		return this.earn_more;
	}
	
	public double getReward() {
		return this.reward;
	}
	  
	public List<String> getDescription() {
		return this.des;
	}
	
	public String getJobID() {
		return this.job;
	}
	
	public int getLevel() {
		return this.lvel;
	}
 
	public String getDisplay() {
		return this.display;
	}
 
	public boolean hasDisplay() {
		return this.display != null;
	}
	
	public boolean hasCommands() {
		return this.commands != null;
	}
	
	public List<String> getCommands() {
		return this.commands;
	}
	 
	public ItemStack getItemStack() {
		return plugin.getItemManager().createOrGetItem("job_levels_"+this.getJobID()+"_"+this.lvel, this.icon, null, this.icon_data);
	}
  
	
	
}
