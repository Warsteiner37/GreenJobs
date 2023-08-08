package code.warsteiner.jobs.utils.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import code.warsteiner.jobs.GreenJobs; 

public class Job {
	
	private GreenJobs plugin = GreenJobs.getPlugin();


	private String ID;
	private List<String> actions;
	private String Name;
	private String icon;
	private int icon_data;
	private int slot;
	private String desc;
	private double price;
	private ArrayList<String> stats_in;
	private ArrayList<String> stats_look;
	private HashMap<String, String> lvl;
	private ArrayList<String> cmd_join;
	private ArrayList<String> cmd_leave;
	
	private HashMap<String, ArrayList<String>> all_ids;
	private HashMap<String, JobID> everyid;
	private Map<String, Integer> so1;
	private ArrayList<String> so2;
	private List<String> worlds;
	private HashMap<String, String> reward_messages;
	private String bar_color; 
	private HashMap<Integer, JobLevel> levels;
	
	public Job(String id, String name, List<String> actions,
			String icon, int icon_data, List<String> command_join, List<String> command_leave, String bar_color, int slot, double price, List<String> worlds,
			String desc, List<String> stats_in2, List<String> stats_look2, HashMap<String, ArrayList<String>> act, HashMap<String, JobID> everyid,
			HashMap<String, String> lvl, Map<String, Integer> so1, ArrayList<String> arraylist_sorted02, HashMap<String, String> reward_messages, HashMap<Integer, JobLevel> levels) {
	 
		this.ID = id;
		this.actions = actions;
		this.Name = name;
		this.icon = icon;
		this.icon_data = icon_data;
		this.slot = slot;
		this.desc = desc;
		this.price = price;
		this.stats_in = (ArrayList<String>) stats_in2;
		this.stats_look = (ArrayList<String>) stats_look2;
		this.lvl = lvl;
		this.cmd_join = (ArrayList<String>) command_join;
		this.cmd_leave = (ArrayList<String>) command_leave;
		
		this.all_ids = act;
		this.everyid = everyid;
		this.so1 = so1;
		this.so2 = arraylist_sorted02;
		this.worlds = worlds;
		this.reward_messages = reward_messages;
		this.bar_color = bar_color;
		this.levels = levels;
	}
	
	public boolean existLevel(int i) {
		return this.levels.containsKey(i);
	}
	
	public JobLevel getLevel(int i) {
		return this.levels.get(i);
	}
	
	public HashMap<Integer, JobLevel> getLevels() {
		return this.levels;
	}
 
	public BarColor getBarColor() {
		return BarColor.valueOf(this.bar_color);
	}
	
	public HashMap<String, String> getRewardMessages() {
		return this.reward_messages;
	}
	
	public List<String> getWorlds() {
		return this.worlds;
	}
	
	public ArrayList<String> getArraySortedAfterNumbers() {
		
		ArrayList<String> array = new ArrayList<String>();
		
		this.so1.forEach((ID, place) -> {
			array.add(ID);
		});
		
		return array;
	}
	
	public ArrayList<String> getArrayList2Sorted() {
		return this.so2;
	}
	
	public HashMap<String, JobID> getEveryID() {
		return this.everyid;
	}
	
	public Map<String, Integer> getSortedByNumbers() {
		return this.so1;
	}
	
	public List<String> getActions() {
		return this.actions;
	}
	
	public ArrayList<String> getJoinCommands() {
		return this.cmd_join;
	}
	
	public ArrayList<String> getLeaveCommands() {
		return this.cmd_leave;
	}
	
	public HashMap<String, String> getLevelOptionsList() {
		return this.lvl;
	}
	
	public ArrayList<String> getMessageLook() {
		return this.stats_look;
	}
	
	public ArrayList<String> getMessageIn() {
		return this.stats_in;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String getPriceToDisplay() {
		return plugin.getBasicPluginManager().Format(this.price);
	}
	
	public String getDescription() {
		return this.desc;
	}
	
	public int getSlot() {
		return this.slot;
	}
	
	public ItemStack getIcon() {
		return plugin.getItemManager().createOrGetItem("job_icon_"+ID, this.icon, null, this.icon_data);
	}
	
	public String getDisplay() {
		return plugin.getBasicPluginManager().toHex(this.Name);
	}
 
	public String getID() {
		return this.ID.toUpperCase();
	}
	
	public String getName() {
		return plugin.getBasicPluginManager().toHex(this.Name);
	}

	
	
}
