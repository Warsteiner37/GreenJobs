package code.warsteiner.jobs.basic.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.manager.PlayerDataManager;

public class PlayerASyncQuitEvent implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event) {

		PlayerDataManager data = plugin.getPlayerDataManager();

		Player player = event.getPlayer();
		UUID id = player.getUniqueId();
		String name = player.getName();
		
		Location location = player.getLocation();

		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
 
				plugin.getLocationManager().saveLocation(id.toString(), location);
				
			}

		});

	}

}
