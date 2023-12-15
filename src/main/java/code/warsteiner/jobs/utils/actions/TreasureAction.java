package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit; 
import org.bukkit.Material; 
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener; 
import org.bukkit.event.player.PlayerInteractEvent;

import code.warsteiner.jobs.GreenJobs; 
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class TreasureAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "Find a Treasure";
	}

	@Override
	public String getID() {
		return "FIND_TREASURE";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-treasure-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(PlayerInteractEvent event) {
		
		Player player = event.getPlayer(); 
		UUID ID = player.getUniqueId();
		
		if (event.getClickedBlock() == null) {
			return;
		}
		if (event.getClickedBlock().getType() != Material.CHEST) {
			return;
		}
		Chest c = (Chest) event.getClickedBlock().getState();
		if (c.getLootTable() == null) {
			return;
		}
	 
		String rep = c.getLootTable().toString().replaceAll("minecraft:chests/", "    ").replaceAll(" ", "");
		 
		PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "FIND_TREASURE", rep, 1);
		Bukkit.getServer().getPluginManager().callEvent(ev);
 
	}

}
