package code.warsteiner.jobs.utils.files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.basic.BasicGUIManager;
import code.warsteiner.jobs.manager.LoadAndStoreGUIManager;
import code.warsteiner.jobs.utils.enums.GUIType;

public class FileManager {

	private FileTemplate locs;
	private FileTemplate data;
	private FileTemplate blocks;
 
	private YamlConfiguration jobs_settings;
	private YamlConfiguration gui_settings;
	private YamlConfiguration levels_settings;
	private YamlConfiguration util_settings; 
	private YamlConfiguration command_settings;
	private YamlConfiguration sounds_settings;
	
	private YamlConfiguration bstats;
	private YamlConfiguration placeholderapi;
	
	private YamlConfiguration gui_config_purchase;
	private YamlConfiguration gui_jobs;
	private YamlConfiguration gui_levels;
	private YamlConfiguration gui_options;
	private YamlConfiguration gui_rewards;
	
	public void setFiles() {
  
		this.jobs_settings = new CustomConfig().createFile("settings" + File.separatorChar + "JobsSettings.yml");
		this.gui_settings = new CustomConfig().createFile("settings" + File.separatorChar + "GuiSettings.yml");
		this.levels_settings = new CustomConfig().createFile("settings" + File.separatorChar + "LevelsSettings.yml");
		this.util_settings = new CustomConfig().createFile("settings" + File.separatorChar + "UtilSettings.yml");
		this.command_settings = new CustomConfig().createFile("settings" + File.separatorChar + "CommandSettings.yml");
		this.sounds_settings = new CustomConfig().createFile("settings" + File.separatorChar + "SoundsSettings.yml");
		
		this.placeholderapi = new CustomConfig().createFile("integrations" + File.separatorChar + "PlaceholderAPI.yml");
		this.bstats = new CustomConfig().createFile("integrations" + File.separatorChar + "bStats.yml");
		
		this.gui_config_purchase = new CustomConfig().createFile("guis" + File.separatorChar + "Confirm_Purchase.yml");
		this.gui_jobs = new CustomConfig().createFile("guis" + File.separatorChar + "Jobs.yml");
		this.gui_levels = new CustomConfig().createFile("guis" + File.separatorChar + "Levels.yml");
		this.gui_options = new CustomConfig().createFile("guis" + File.separatorChar + "Overview.yml");
		this.gui_rewards = new CustomConfig().createFile("guis" + File.separatorChar + "Rewards.yml"); 
		 
		String loc = this.getUtilSettings().getString("SaveDataIn");

		this.locs = new FileTemplate("locations", loc);
		this.locs.create();
		
		this.blocks = new FileTemplate("blockdata", loc);
		this.blocks.create();


		this.data = new FileTemplate("data", loc);
		this.data.create();
		 
	}
	
	public YamlConfiguration getJobsConfig() {
		return this.gui_jobs;
	}
	
	public YamlConfiguration getConfirmConfig() {
		return this.gui_config_purchase;
	}
	
	public YamlConfiguration getRewardsConfig() {
		return this.gui_rewards;
	}
	
	public YamlConfiguration getOptionsConfig() {
		return this.gui_options;
	}
	
	public YamlConfiguration getLevelsConfig() {
		return this.gui_levels;
	}
	
	public YamlConfiguration getbStatsConfig() {
		return this.bstats;
	}
	
	public YamlConfiguration getPlaceholderConfig() {
		return this.placeholderapi;
	}
	 
	public FileConfiguration getSoundsSettings() {
		return this.sounds_settings;
	}
	
	public FileConfiguration getCommandSettings() {
		return this.command_settings;
	}
	
	public FileConfiguration getJobsSettings() {
		return this.jobs_settings;
	}
 
	public FileConfiguration getGuiSettings() {
		return this.gui_settings;
	}
	
	public FileConfiguration getLevelsSettings() {
		return this.levels_settings;
	}
	
	public FileConfiguration getUtilSettings() {
		return this.util_settings;
	}
	
	public boolean isLoaded(GUIType type) {
		
		LoadAndStoreGUIManager basic = GreenJobs.getPlugin().getLoadAndStoreGUIManager();
		
		if(basic.getConfig(type)  != null) {
			return true;
		}
		return false; 
	}
	
	public FileTemplate getBlockFile() {
		return this.blocks;
	}
	
	public FileConfiguration getBlockConfig() {
		return this.blocks.get();
	}

	public FileTemplate getDataFile() {
		return this.data;
	}

	public FileConfiguration getDataConfig() {
		return this.data.get();
	}

	public FileTemplate getLocationFile() {
		return this.locs;
	}

	public FileConfiguration getLocationConfig() {
		return this.locs.get();
	}
 
}
