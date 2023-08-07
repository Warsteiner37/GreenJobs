package code.warsteiner.jobs.basic.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerDataChangeEvent;

public class PlayerUpdateInventory implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@EventHandler
	public void onInventoryClick(PlayerDataChangeEvent event) {
		
		Player player = event.getPlayer();
		
		plugin.getBasicGUIManager().updateInventory(player);
		
	}
}
	
