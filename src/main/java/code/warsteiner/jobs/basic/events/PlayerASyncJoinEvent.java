package code.warsteiner.jobs.basic.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.manager.PlayerDataManager;

public class PlayerASyncJoinEvent implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent event) {

		PlayerDataManager data = plugin.getPlayerDataManager();

		Player player = event.getPlayer();
		UUID id = player.getUniqueId();
		String name = player.getName();
		
		Location location = player.getLocation();

		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {

				if (!data.exist(id)) {

					data.createAJobsPlayer(name, id);

				}
				 
			}

		});

	}

}
