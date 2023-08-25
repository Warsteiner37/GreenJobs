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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class StripLogAction extends JobAction implements Listener {

	@Override
	public String getName() {
		return "Striplog Action";
	}

	@Override
	public String getID() {
		return "STRIPLOG";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-striplog-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerInteractEvent event) {
 
		if(event.getClickedBlock() != null) {
			if(event.getClickedBlock().getType() != null) {
				Player player = event.getPlayer();
				UUID ID = player.getUniqueId();
				Block block = event.getClickedBlock();
				String BlockID = block.getType().toString();
				Action action = event.getAction();
				Material item = event.getMaterial();

				if(action != null) {
					if (action == Action.RIGHT_CLICK_BLOCK && item.toString().contains("_AXE")) {

						if (block.toString().contains("LOG")) {
							 
							PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "STRIPLOG", BlockID);
							Bukkit.getServer().getPluginManager().callEvent(ev);

						}
					}
				}
			}
		}

	}

}
