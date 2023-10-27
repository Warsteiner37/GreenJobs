package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.BlockAPI;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class CollectHoneyAction extends JobAction implements Listener {
 
	@Override
	public String getName() {
		return "Collect Honey Action";
	}

	@Override
	public String getID() {
		return "COLLECT_HONEY";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-collect-honey-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerInteractEvent event) {
  
		Player player = event.getPlayer();
		UUID ID = player.getUniqueId();
 
		Action action = event.getAction();
		Block clickedBlock = event.getClickedBlock();
	 
		if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock != null
				&& (clickedBlock.getType().equals(Material.BEEHIVE)
						|| clickedBlock.getType().equals(Material.BEE_NEST))) {
		
			BlockData bdata = clickedBlock.getBlockData();
			Beehive beehive = (Beehive) bdata;

			if (beehive.getHoneyLevel() != beehive.getMaximumHoneyLevel()) { 
				return;
			}
			
			Material item = player.getItemInHand().getType();

			if (item == null) {
				return;
			}

			if (item != Material.GLASS_BOTTLE) {
				return;
			}
		 
			PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "COLLECT_HONEY", clickedBlock.getType().toString(), 1);
			Bukkit.getServer().getPluginManager().callEvent(ev);
			
		}

		 
 
	}

}
