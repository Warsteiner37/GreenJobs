package code.warsteiner.jobs.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.enums.CustomItemAction;
import code.warsteiner.jobs.utils.enums.GUIType; 
import code.warsteiner.jobs.utils.templates.CustomItem;
import code.warsteiner.jobs.utils.templates.PlaceholderItem;

public class LoadAndStoreGUIManager {

	private GreenJobs plugin = GreenJobs.getPlugin();
 
	public HashMap<GUIType, String> gui_names = new HashMap<GUIType, String>();
	public HashMap<GUIType, Integer> gui_size = new HashMap<GUIType, Integer>();
	public HashMap<GUIType, ArrayList<CustomItem>> gui_customitems = new HashMap<GUIType, ArrayList<CustomItem>>();
	public HashMap<GUIType, ArrayList<PlaceholderItem>> gui_placeholders = new HashMap<GUIType, ArrayList<PlaceholderItem>>(); 
	
	public void clearLists() {
		this.gui_names.clear();
		this.gui_size.clear();
		this.gui_placeholders.clear();
		this.gui_customitems.clear();
	}
	
	public String getName(GUIType type) {
		return plugin.getBasicPluginManager().toHex(this.gui_names.get(type));
	}
	
	public Integer getSize(GUIType type) {
		return this.gui_size.get(type);
	}
	
	public ArrayList<CustomItem> getCustomItems(GUIType type) {
		return this.gui_customitems.get(type);
	}
	
	public ArrayList<PlaceholderItem> getPlaceholderItems(GUIType type) {
		return this.gui_placeholders.get(type);
	}
	
	public void load() { 
		
		gui_names.put(GUIType.MANAGER, "§a§lGreenJobs - Manager");
		gui_size.put(GUIType.MANAGER, 6);
		
		for(GUIType gui : GUIType.values()) {
			Bukkit.getConsoleSender().sendMessage("§aTrying to load "+gui+"...");
			
			if(getConfig(gui) != null) {
				
				Bukkit.getConsoleSender().sendMessage("§aLoading "+gui+"...");
				
				FileConfiguration config = getConfig(gui);
				
				String name = config.getString("GUI_Name");
				int size = config.getInt("GUI_Size");
				
				List<String> items = config.getStringList("GUI_Items.List");
				
				ArrayList<CustomItem> create = new ArrayList<CustomItem>();
				 
				for(String i : items) {
					String display = config.getString("GUI_Items."+i+".Display");
					List<String> actions = config.getStringList("GUI_Items."+i+".Actions");
					List<String> lore = config.getStringList("GUI_Items."+i+".Lore");
					String icon = config.getString("GUI_Items."+i+".Icon");
					int slot = config.getInt("GUI_Items."+i+".Slot");
					int custom = config.getInt("GUI_Items."+i+".CustomModelData");
					
					ArrayList<CustomItemAction> ac = new ArrayList<CustomItemAction>();
					
					try {
						for(String a : actions) {
							ac.add(CustomItemAction.valueOf(a)); 
						}
					} catch (Exception e) {  
						Bukkit.getConsoleSender().sendMessage("§4Failed CustomItemAction for "+gui.toString());
					}
					
					CustomItem created = new CustomItem(gui, i, display, ac, (ArrayList<String>) lore, icon, slot, custom, config);
					
					create.add(created);
				}
				
				List<String> place = config.getStringList("GUI_Placeholders");
				
				ArrayList<PlaceholderItem> l = new ArrayList<PlaceholderItem>();
				
				for(String i : place) {
					
					String[] split = i.split(":");
					
					String mat = split[0];
					
					Integer slot = Integer.valueOf(split[1]);
					
					String display = split[2];
					
					int data = 0;
					
					if(split.length == 4) {
						
						 data = Integer.valueOf(split[3]);
						
					}
					
					PlaceholderItem created = new PlaceholderItem(display, mat, slot, data, gui);
				
					l.add(created);
				}
				
				
				gui_names.put(gui, name);
				gui_size.put(gui, size);
				gui_customitems.put(gui, create);
				gui_placeholders.put(gui, l);
				
				Bukkit.getConsoleSender().sendMessage("§2Loaded GUI -> "+gui+"...");
				
			}
			
		}
	}
	
	public FileConfiguration getConfig(GUIType gui) {
		if(gui.equals(GUIType.JOBS)) {
			return plugin.getFileManager().getJobsConfig();
		}
		if(gui.equals(GUIType.BUY_CONFIRM)) {
			return plugin.getFileManager().getConfirmConfig();
		}
		if(gui.equals(GUIType.OPTIONS)) {
			return plugin.getFileManager().getOptionsConfig();
		}
		if(gui.equals(GUIType.REWARDS)) {
			return plugin.getFileManager().getRewardsConfig();
		}
		return null;
	}
	
}
