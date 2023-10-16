package code.warsteiner.jobs.utils.templates;

import org.bukkit.Location; 

import code.warsteiner.jobs.GreenJobs; 

public class BlockData {
 
	private GreenJobs plugin = GreenJobs.getPlugin();
 
	private Location location;  
	private String type;
	private String placed_by;
	private String placed_date;
	  
	public BlockData(Location loc, String type, String placed_by, String placed_date) {
	
		this.location = loc;
		this.type = type;
		this.placed_by = placed_by;
		this.placed_date = placed_date;
		
	}
	
	public String getPlacedWhen() {
		return this.placed_date;
	}
	
	public String getPlacedBy() {
		return this.placed_by.toLowerCase();
	}
 
	public String getType() {
		return this.type;
	}
	
	public Location getLocation() {
		return this.location;
	}
 
  
}