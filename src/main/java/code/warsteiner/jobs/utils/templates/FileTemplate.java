package code.warsteiner.jobs.utils.templates;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import code.warsteiner.jobs.GreenJobs;
 
public class FileTemplate {

	public String name;
	private String location;
	
	public FileTemplate(String file, String location) {
		this.name = file;
		this.location = location;
	}
	
	public File cfile;
	public FileConfiguration config;
	
	/**
	 * Where the file is saved
	*/
 
	public GreenJobs plugin = GreenJobs.getPlugin();

	/**
	 * Name of the File
	*/
	
	public String getName() {
		return name;
	}
	
	/**
	 * Create and Load the File
	*/
	
	public void create() {
		if (getfile() != null && !this.plugin.getDataFolder().exists())
			this.plugin.getDataFolder().mkdir();
		if (!getfile().exists()) {
			try {
				getfile().createNewFile();
			} catch (Exception e) { 
			}
		}
		this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(this.cfile);
	}
	
	/**
	 * Get the File
	*/

	public File getfile() {
		this.cfile = new File(this.location, name+".yml");
		if (this.cfile != null) {
			return this.cfile;
		}
		return null;
	}
	
	/**
	 * Load the File without Create
	*/

	public void load() {
		this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(this.cfile);
	}
	
	/**
	 * Get the File as Config
	*/

	public FileConfiguration get() {
		
		if(this.config == null) {
			load();
		}
		
		return this.config;
	}
	
	/**
	 * Save the File
	*/

	public void save() {
		try {
			this.config.save(getfile());
		} catch (Exception e) { 
		}
	}
	
}
