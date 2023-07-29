package code.warsteiner.jobs.utils.templates;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.enums.CustomItemAction;
import code.warsteiner.jobs.utils.enums.GUIType;

public class CustomItem {
	
	private GreenJobs plugin = GreenJobs.getPlugin();

	
	private String ID;
	private String display;
	private ArrayList<CustomItemAction> actions;
	private ArrayList<String> lore;
	private String icon;
	private int slot;
	private int data;
	private GUIType gui;
	private FileConfiguration cfg;
	  
	public CustomItem(GUIType gui, String id, String display, ArrayList<CustomItemAction> actions, ArrayList<String> lore, String icon, int slot, int data, FileConfiguration config) {
	
		this.ID = id;
		this.display = display;
		this.gui = gui;
		
		this.actions = actions;
		this.lore = lore;
		this.icon = icon;
		this.slot = slot;
		this.data = data;
		this.cfg = config;
	}
	
	public FileConfiguration getConfig() {
		return this.cfg;
	}
	
	public ItemStack getIcon(String player_name) {
		 
		ItemStack item = plugin.getItemManager().createOrGetItem(gui.toString()+this.ID, this.icon, player_name, this.data);
		
		return item;
		
	}
	
	public int getSlot() {
		return this.slot;
	}
	
	public ArrayList<String> getLore() {
		return this.lore;
	}
	
	public boolean hasLore() {
		return this.lore != null;
	}
	
	public ArrayList<CustomItemAction> getActions() {
		return this.actions;
	}
	
	public boolean hasActions() {
		return this.actions != null;
	}
 
	public String getID() {
		return this.ID;
	}
	
	public String getDisplay() {
		return plugin.getBasicPluginManager().toHex(this.display);
	}

  
}
