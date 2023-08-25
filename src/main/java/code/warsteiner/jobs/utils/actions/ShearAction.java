package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import code.warsteiner.jobs.GreenJobs; 
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;  

public class ShearAction  extends JobAction implements Listener {
	
	private GreenJobs plugin = GreenJobs.getPlugin();


	@Override
	public String getName() { 
		return "Shear";
	}

	@Override
	public String getID() { 
		return "SHEAR";
	}

	@Override
	public String getWorldGuardFlag() { 
		return "greenjobs-shear-flag";
	}

	@Override
	public void register() { 
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}
 
	 
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerShearEntityEvent  event) {
		 
		if (event.getEntity() instanceof Sheep) {

			Sheep sheep = (Sheep) event.getEntity();

			DyeColor color = sheep.getColor();
 
			Player player = event.getPlayer();
			UUID ID = player.getUniqueId();
		  

			PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "SHEAR", color.toString().toUpperCase());
			Bukkit.getServer().getPluginManager().callEvent(ev);
		}

	}
 

}
