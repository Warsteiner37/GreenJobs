package code.warsteiner.jobs.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.templates.FileTemplate;

public class FileManager {

	private FileTemplate locs;
	private FileTemplate data;
	private FileTemplate blocks;

	private FileConfiguration cfg;
	private File cfg_file;

	private FileConfiguration jobs;
	private File jobs_file;

	private FileConfiguration confirm;
	private File confirm_file;

	private FileConfiguration options;
	private File options_file;

	private FileConfiguration rewards;
	private File rewards_file;

	private FileConfiguration command;
	private File command_file;

	private FileConfiguration sounds;
	private File sounds_file;

	private FileConfiguration papi;
	private File papi_file;

	public void setFiles() {

		checkConfigFile();
		checkJobsGUIFile();
		checkConfirmGUIFile();
		checkOptionsGUIFile();
		checkCommandsGUIFile();
		checkRewardsGUIFile();
		checkSoundsFile();
		checkPlaceHolderFile();

		String loc = getConfigConfig().getString("SaveDataIn");

		this.locs = new FileTemplate("locations", loc);
		this.locs.create();
		
		this.blocks = new FileTemplate("blockdata", loc);
		this.blocks.create();


		this.data = new FileTemplate("data", loc);
		this.data.create();
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

	public File getOptionsFile() {
		return this.options_file;
	}

	public FileConfiguration getOptionsConfig() {
		return this.options;
	}

	public boolean checkPlaceHolderFile() {
		papi_file = new File(GreenJobs.getPlugin().getDataFolder(),
				"integrations" + File.separatorChar + "placeholderapi.yml");
		if (!papi_file.exists()) {
			papi_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource("integrations" + File.separatorChar + "placeholderapi.yml", false);
		}

		papi = new YamlConfiguration();
		try {
			papi.load(papi_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public File getPAPIFile() {
		return this.papi_file;
	}

	public FileConfiguration getPAPIConfig() {
		return this.papi;
	}

	public boolean checkSoundsFile() {
		sounds_file = new File(GreenJobs.getPlugin().getDataFolder(), "other" + File.separatorChar + "sounds.yml");
		if (!sounds_file.exists()) {
			sounds_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource("other" + File.separatorChar + "sounds.yml", false);
		}

		sounds = new YamlConfiguration();
		try {
			sounds.load(sounds_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public File getSoundsFile() {
		return this.sounds_file;
	}

	public FileConfiguration getSoundsConfig() {
		return this.sounds;
	}

	public boolean checkCommandsGUIFile() {
		command_file = new File(GreenJobs.getPlugin().getDataFolder(), "commands.yml");
		if (!command_file.exists()) {
			command_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource("commands.yml", false);
		}

		command = new YamlConfiguration();
		try {
			command.load(command_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public File getCommandsFile() {
		return this.command_file;
	}

	public FileConfiguration getCommandsConfig() {
		return this.command;
	}

	public boolean checkRewardsGUIFile() {
		rewards_file = new File(GreenJobs.getPlugin().getDataFolder(), "guis" + File.separatorChar + "rewards.yml");
		if (!rewards_file.exists()) {
			rewards_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource("guis" + File.separatorChar + "rewards.yml", false);
		}

		rewards = new YamlConfiguration();
		try {
			rewards.load(rewards_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public File getRewardsFile() {
		return this.rewards_file;
	}

	public FileConfiguration getRewardsConfig() {
		return this.rewards;
	}

	public boolean checkOptionsGUIFile() {
		options_file = new File(GreenJobs.getPlugin().getDataFolder(), "guis" + File.separatorChar + "options.yml");
		if (!options_file.exists()) {
			options_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource("guis" + File.separatorChar + "options.yml", false);
		}

		options = new YamlConfiguration();
		try {
			options.load(options_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public File getConfirmFile() {
		return this.confirm_file;
	}

	public FileConfiguration getConfirmConfig() {
		return this.confirm;
	}

	public boolean checkConfirmGUIFile() {
		confirm_file = new File(GreenJobs.getPlugin().getDataFolder(),
				"guis" + File.separatorChar + "confirm_purchase.yml");
		if (!confirm_file.exists()) {
			confirm_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource("guis" + File.separatorChar + "confirm_purchase.yml", false);
		}

		confirm = new YamlConfiguration();
		try {
			confirm.load(confirm_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public FileConfiguration getJobsConfig() {
		return this.jobs;
	}

	public File getJobsFile() {
		return this.jobs_file;
	}

	public boolean checkJobsGUIFile() {
		jobs_file = new File(GreenJobs.getPlugin().getDataFolder(), "guis" + File.separatorChar + "jobs.yml");
		if (!jobs_file.exists()) {
			jobs_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource("guis" + File.separatorChar + "jobs.yml", false);
		}

		jobs = new YamlConfiguration();
		try {
			jobs.load(jobs_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public File getConfigFile() {
		return this.cfg_file;
	}

	public FileConfiguration getConfigConfig() {
		return this.cfg;
	}

	public boolean checkConfigFile() {
		cfg_file = new File(GreenJobs.getPlugin().getDataFolder(), "Config.yml");
		if (!cfg_file.exists()) {
			cfg_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource("Config.yml", false);
		}

		cfg = new YamlConfiguration();
		try {
			cfg.load(cfg_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
