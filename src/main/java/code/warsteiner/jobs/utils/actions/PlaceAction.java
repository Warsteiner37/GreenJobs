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
import org.bukkit.event.block.BlockPlaceEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.BlockAPI;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class PlaceAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "Block Place";
	}

	@Override
	public String getID() {
		return "PLACE";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-place-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(BlockPlaceEvent event) {

		BlockAPI bapi = plugin.getBlockAPI();
		 
		Player player = event.getPlayer();
		UUID ID = player.getUniqueId();
		Block block = event.getBlock();
		String BlockID = block.getType().toString();
 
		  
		PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "PLACE", BlockID, 1);
		Bukkit.getServer().getPluginManager().callEvent(ev);

	}

}
