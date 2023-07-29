package code.warsteiner.jobs.utils.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
  
public class PlayerSkill {
 
	private UUID ID;
	private String Name;
	private int level;
 
 
	public PlayerSkill(UUID id, String name, int level) {
	
		this.ID = id;
		this.Name = name;
		this.level = level;
		
	}
 
	public int getLevel() {
		return this.level;
	}
	
	public UUID getUUID() {
		return this.ID;
	}
	
	public String getName() {
		return this.Name;
	}

	
	
}
