package code.warsteiner.jobs.utils.templates;
 

import org.bukkit.inventory.ItemStack;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.enums.GUIType; 

public class PlaceholderItem {
	
	private GreenJobs plugin = GreenJobs.getPlugin();
 
	private String display; 
	private String icon;
	private int slot;
	private int data;
	private GUIType gui;
	  
	public PlaceholderItem(String display, String icon, int slot, int data, GUIType gui) {
	
		this.display = display;
 
		this.icon = icon;
		this.slot = slot;
		this.data = data;
		this.gui = gui;
	}
	
	public ItemStack getIcon(String player_name) {
		 
		ItemStack item = plugin.getItemManager().createOrGetItem(this.icon+"_"+this.display+"_"+slot+"_"+gui.toString(), this.icon, player_name, this.data);
		
		return item;
		
	}
	
	public int getSlot() {
		return this.slot;
	}
 
	public String getDisplay() {
		return plugin.getBasicPluginManager().toHex( this.display);
	}

  
}
