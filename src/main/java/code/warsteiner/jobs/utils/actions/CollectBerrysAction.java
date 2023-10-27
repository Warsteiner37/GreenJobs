package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.BlockAPI;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class CollectBerrysAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "Collect Berrys Action";
	}

	@Override
	public String getID() {
		return "COLLECT_BERRYS";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-collect-berrys-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerHarvestBlockEvent event) {
 
		if(event.getHarvestedBlock() == null) {
			return;
		}
		 
		String id = event.getHarvestedBlock().getType().toString();

		Player player = event.getPlayer();
		UUID ID = player.getUniqueId();

		PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "COLLECT_BERRYS", id, 1);
		Bukkit.getServer().getPluginManager().callEvent(ev);
 
	}

}
