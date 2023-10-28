package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit; 
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action; 
import org.bukkit.event.player.PlayerInteractEvent;

import code.warsteiner.jobs.GreenJobs; 
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class CarveAction extends JobAction implements Listener {
 
	@Override
	public String getName() {
		return "Carve Action";
	}

	@Override
	public String getID() {
		return "CARVE";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-carve-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerInteractEvent event) {
	  
		Player player = event.getPlayer();
		UUID ID = player.getUniqueId();
		
		if (event.getClickedBlock() == null) {
			return;
		}
		
		if (event.getClickedBlock().getType() == null) {
			return;
		}
		
		Action action = event.getAction();
		Material item = event.getMaterial();
		Block block = event.getClickedBlock();
		Material mat = block.getType();
		
		if (action == Action.RIGHT_CLICK_BLOCK && item.equals(Material.SHEARS)) {
			 
			if (mat.equals(Material.PUMPKIN)) {
				PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "CARVE", mat.toString(), 1);
				Bukkit.getServer().getPluginManager().callEvent(ev);
			}
		}
		
		 

		 
 
	}

}
