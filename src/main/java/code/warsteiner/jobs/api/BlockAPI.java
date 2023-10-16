package code.warsteiner.jobs.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.templates.BlockData;
import code.warsteiner.jobs.utils.templates.FileTemplate;

public class BlockAPI {
	
	private GreenJobs plugin = GreenJobs.getPlugin();
 
	private HashMap<Location, BlockData> blocks = new HashMap<Location, BlockData>(); 
	
	public void addBlock(Location loc, String type, String placed_by, String placed_date) {
		
		BlockData block = new BlockData(loc, type.toUpperCase(), placed_by, placed_date);
		
		HashMap<Location, BlockData> list = this.blocks;
		
		list.put(loc, block);
		
		this.blocks = list;
	}
	
	public void loadData() {
		
		FileTemplate file = plugin.getFileManager().getBlockFile();
		FileConfiguration cfg = file.get();
		
		List<String> list = cfg.getStringList("BlocksList");
		
		HashMap<Location, BlockData> check = new HashMap<Location, BlockData>();
		
		if(list != null) {
			list.forEach((got) -> {
				
				String[] split = got.split("_");
				
				String type = split[0];
				String world = split[1];
				Bukkit.getConsoleSender().sendMessage("§4§lworld: "+world);
				Double gx = cfg.getDouble("Block."+got+".X");
				Double gy = cfg.getDouble("Block."+got+".Y");
				Double gz = cfg.getDouble("Block."+got+".Z");
				
				Location loc = new Location(Bukkit.getWorld(world), gx, gy, gz);
				
				String placed_by = cfg.getString("Block."+got+".PlacedBy");
				String placed_date = cfg.getString("Block."+got+".PlacedDate");
				
				BlockData block = new BlockData(loc, type.toUpperCase(), placed_by, placed_date);
				
				check.put(loc, block);
			});
		}
		
		HashMap<Location, BlockData> clean = cleanUp(check);
		
		this.blocks = clean;
	}
	
	public void saveData() {
		
		if(this.blocks != null) {
		 
			FileTemplate file = plugin.getFileManager().getBlockFile();
			FileConfiguration cfg = file.get();
			
			ArrayList<String> listed = new ArrayList<String>();
			
			this.blocks.forEach((loc, got) -> {
				
				Double x = loc.getX();
				Double y = loc.getY();
				Double z = loc.getZ();
				String world = loc.getWorld().getName();
				
				String type = got.getType();
				
				int test_x = x.intValue();
				int test_y = y.intValue();
				int test_z = z.intValue();
				
				String name = type+"_"+world+"_"+test_x+"_"+test_y+"_"+test_z;
				
				cfg.set("Block."+name+".X", x);
				cfg.set("Block."+name+".Y", y);
				cfg.set("Block."+name+".Z", z);
				cfg.set("Block."+name+".World", world);
				cfg.set("Block."+name+".Type", got.getType().toString());
				cfg.set("Block."+name+".PlacedBy", got.getPlacedBy());
				cfg.set("Block."+name+".PlacedDate", got.getPlacedWhen());
				
				listed.add(name);
			});
			
			cfg.set("BlocksList", listed);
			
			file.save(); 
		}
	}
	
	public HashMap<Location, BlockData> cleanUp(HashMap<Location, BlockData> data) {
	 
		HashMap<Location, BlockData> map = new HashMap<Location, BlockData>();
		
		data.forEach((loc, got) -> {
			
			Location c = got.getLocation();
			String type = got.getType();
			 
			if(c.getWorld() != null) {
				 
				World world = c.getWorld();
				
				
				if(world.getBlockAt(c) != null) {
					String ty = world.getBlockAt(c).getType().toString();
					
					if(ty.equals(type)) {
						map.put(loc, got);
					}
				}
			} 
			
		});
		
		return map;
		
	}
 
	public boolean isABlock(Location loc, String type) {
		
		if(this.blocks.containsKey(loc)) {
			
			BlockData check = this.blocks.get(loc);
			
			if(check.getType().equalsIgnoreCase(type.toUpperCase())) {
				return true;
			}
			
		}
		return false;
		
	}

	public void removeBlock(Location loc, String type) {
	 
		if(isABlock(loc, type)) {
			
			HashMap<Location, BlockData> list = this.blocks;
			
			list.remove(loc);
			
			this.blocks = list;
			
		}
	}

	
}