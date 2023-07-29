package code.warsteiner.jobs.utils.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import code.warsteiner.jobs.GreenJobs;

public class JobID {
	
	private GreenJobs plugin = GreenJobs.getPlugin();

  
	private String Name;
	
	private String icon;
	private int chance;
	private double money;
	private double exp;
	private double points;
	private String display;
	private String display_rewards;
	private String desc;
	private String icon_rewards;
	private int sorting;
	private int icon_data;
	private ArrayList<String> lore;
	private ArrayList<String> lore_in;
	private ArrayList<String> commands;
	
	public JobID(String name,String icon, List<String> lore2, List<String> lore3, int chance, double money, double exp, double points, String display, String display_rewards, String desc, String icon_rewards, int sorting, int data, ArrayList<String> commands) {
	
		this.Name = name;

		this.icon = icon;
		this.chance = chance;
		this.money = money;
		this.exp = exp;
		this.points = points;
		this.display = display;
		this.display_rewards = display_rewards;
		this.desc = desc;
		this.icon_rewards = icon_rewards;
		this.sorting = sorting;
		this.lore = (ArrayList<String>) lore2;
		this.icon_data = data;
		this.lore_in = (ArrayList<String>) lore3;
		this.commands = commands;
	
	}
	
	public String getDisplay() {
		return plugin.getBasicPluginManager().toHex(this.display);
	}
 
	
	public ArrayList<String> getCommands() {
		return this.commands;
	}
	
	public ArrayList<String> getLoreWhenOwnJob() {
		return this.lore_in;
	}
	
	public double getPoints() {
		return this.points;
	}
	
	public double getExp() {
		return this.exp;
	}
	
	public double getChance() {
		return this.chance;
	}
	
	public double getMoneyReward() {
		return this.money;
	}
	
	public ArrayList<String> getLore() {
		return this.lore;
	}
	
	public String getDisplayInRewards() {
		return plugin.getBasicPluginManager().toHex(this.display_rewards);
	}
	
	public ItemStack getItemToDisplayInRewards() {
		return plugin.getItemManager().createOrGetItem("job_rewardsicon_"+this.Name, this.icon_rewards, null, this.icon_data);
	}
  	
	public int getSortNumber() {
		return this.sorting;
	}
 
	public String getName() {
		return this.Name;
	}

	
	
}
