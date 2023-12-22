package code.warsteiner.jobs.basic.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.JobAPI;
import code.warsteiner.jobs.manager.PlayerDataManager;
import code.warsteiner.jobs.utils.templates.JobsPlayer;

public class PlayerASyncJoinEvent implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent event) {
 
		FileConfiguration jobs_settings = plugin.getFileManager().getJobsSettings();
		 
		PlayerDataManager data = plugin.getPlayerDataManager(); 
		JobAPI api = plugin.getJobAPI();

		Player player = event.getPlayer();
		UUID id = player.getUniqueId();
		String name = player.getName();

		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {

				if (!data.exist(id)) {
					data.createAJobsPlayer(name, id);
				}
				
				JobsPlayer jb = data.getJobsPlayer(name, id);
				
				api.getLoadedJobsHash().forEach((id, job) -> {
					if (jobs_settings.getBoolean("AutoAddFreeJobsToOwn")) { 
						if (job != null) {
							if (job.getPrice() == 0) { 
								jb.addOwnedJob(job.getID()); 
							}
						} 
					} 
				});
				
				 
			}

		});

	}

}
