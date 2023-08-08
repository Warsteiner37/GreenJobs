package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import code.warsteiner.jobs.GreenJobs; 
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;  

public class BreakAction  extends JobAction implements Listener {
	
	private GreenJobs plugin = GreenJobs.getPlugin();


	@Override
	public String getName() { 
		return "Block Break";
	}

	@Override
	public String getID() { 
		return "BREAK";
	}

	@Override
	public String getWorldGuardFlag() { 
		return "greenjobs-break-flag";
	}

	@Override
	public void register() { 
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}
 
	 
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(BlockBreakEvent event) {
		
		if(event.isCancelled()) {
			event.setCancelled(true);
			return;
		}

		if(!event.getBlock().hasMetadata("placed-by-player")) {
			Player player = event.getPlayer();
			UUID ID = player.getUniqueId();
			Block block = event.getBlock();
			String BlockID = block.getType().toString(); 

			PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "BREAK", BlockID);
			Bukkit.getServer().getPluginManager().callEvent(ev);
		}

	}
 

}