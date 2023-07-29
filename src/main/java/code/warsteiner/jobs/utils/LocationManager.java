package code.warsteiner.jobs.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.templates.Job;
 
public class LocationManager {
	
	private GreenJobs plugin = GreenJobs.getPlugin();
	
	private HashMap<String, Location> locs = new HashMap<String, Location>();

	public void saveLocation(String name, Location loc) {
		HashMap<String, Location> listed = this.locs;
		
		listed.put(name, loc);
		
		this.locs = listed;
	}
	
	public Location getSavedLocation(String name) {
		return this.locs.get(name);
	}
	
	public void setLocation(String name, Location loc) {
		
		FileConfiguration File = plugin.getFileManager().getLocationConfig();
		
		File.set("Loc."+name+".X", loc.getBlockX());
		File.set("Loc."+name+".Y", loc.getY());
		File.set("Loc."+name+".Z", loc.getZ());
		File.set("Loc."+name+".Pitch", loc.getPitch());
		File.set("Loc."+name+".World", loc.getWorld().getName());
		File.set("Loc."+name+".Yaw", loc.getYaw()); 
	 
		plugin.getFileManager().getLocationFile().save();
	}
	
	public boolean exist(String name) {
		return plugin.getFileManager().getLocationConfig().contains("Loc."+name+".World");
	}
	
	public Location getLocation(String name) {
		
		FileConfiguration File = plugin.getFileManager().getLocationConfig();
		
		double x = File.getDouble("Loc."+name+".X");
		double y = File.getDouble("Loc."+name+".Y");
		double z = File.getDouble("Loc."+name+".Z");
		float pitch = File.getInt("Loc."+name+".Pitch");
		String world = File.getString("Loc."+name+".World");
		float yaw = File.getInt("Loc."+name+".Yaw"); 
		
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

}
