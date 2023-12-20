package code.warsteiner.jobs.support;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry; 
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.templates.JobAction;

public class WorldGuardSupport {

	private GreenJobs plugin = GreenJobs.getPlugin();

	
	public static WorldGuardPlugin wg;
	 
	private HashMap<String, StateFlag> list = new HashMap<String, StateFlag>();
 
	public void clearLists() {
		this.list.clear();
	}
	
	public WorldGuardSupport getManager() {
		return this;
	}
	
	public HashMap<String, StateFlag> getFlags() {
		return this.list;
	}

	public void setClass() {

		Plugin wgPlugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	 
		wg = (WorldGuardPlugin) wgPlugin; 
	}
 

	@SuppressWarnings("rawtypes")
	public  void load() {
		WorldGuard worldGuard = WorldGuard.getInstance();
		FlagRegistry flagRegistry = worldGuard.getFlagRegistry();
		
		try {
		
			for(JobAction ac : plugin.getJobActionManager().getArrayList()) {
				
				String w = ac.getWorldGuardFlag().toLowerCase();
				
				StateFlag f = new StateFlag(w, false);
				
				flagRegistry.register((Flag)f);
				
				this.list.put(w, f);
				
			}
			
		 
			
		} catch (Exception e) { 
		}
		
		 
	}
	
	public  StateFlag getFlagFromName(String b) {
		
		for(JobAction ac : plugin.getJobActionManager().getArrayList()) {
			
			String w = ac.getWorldGuardFlag().toLowerCase();
			
			if(w.equalsIgnoreCase(b.toLowerCase())) {
				return this.list.get(w.toLowerCase());
			}
			
		}
		 
		return null;
	}

	public String checkFlag(org.bukkit.Location location, String f) {

		int priority = -1;
		WorldGuard instance = WorldGuard.getInstance();
		RegionContainer container = instance.getPlatform().getRegionContainer();
		BukkitWorld bukkitWorld = new BukkitWorld(location.getWorld());
		RegionQuery query = container.createQuery();
		Location wLoc = new Location((Extent) bukkitWorld, location.getX(), location.getY(), location.getZ());
		ProtectedRegion selected = null;
		for (ProtectedRegion r : query.getApplicableRegions(wLoc)) {
			if (r.getPriority() > priority) {
				priority = r.getPriority();
				selected = r;
			}
		}

		String state = "ALLOW";
		StateFlag d = getFlagFromName(f);
		if (selected != null) {
			if (selected.getFlag(d) != null) {
				if (selected.getFlag(d) == State.DENY) {
					state = "DENY";
				}
			}
		} 
		return state;

	}
	
}