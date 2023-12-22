package code.warsteiner.jobs.utils.files;

import java.io.File;
import java.io.IOException; 
import org.bukkit.configuration.InvalidConfigurationException; 
import org.bukkit.configuration.file.YamlConfiguration;

import code.warsteiner.jobs.GreenJobs;

public class CustomConfig {
 
	public YamlConfiguration createFile(String path) {
		
		GreenJobs plugin = GreenJobs.getPlugin();
		
		File config_file = new File(plugin.getDataFolder(), path);
		if (!config_file.exists()) {
			config_file.getParentFile().mkdirs();
			GreenJobs.getPlugin().saveResource(path, false);
		}

		YamlConfiguration config = new YamlConfiguration();
		try {
			config.load(config_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace(); 
		} 
	 
		return config;
	}
 
}
