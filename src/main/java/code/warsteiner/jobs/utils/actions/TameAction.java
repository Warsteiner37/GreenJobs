package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityTameEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.BlockAPI;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class TameAction  extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "Animal Tame";
	}

	@Override
	public String getID() {
		return "TAME";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-tame-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(EntityTameEvent  event) {
		
		if (event.getEntity() == null) {
			return;
		}
 
		AnimalTamer player = event.getOwner();
		 
		if(player instanceof Player) {
			UUID ID = player.getUniqueId();
			String type = event.getEntity().getType().toString();
			 
			PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "TAME", type, 1);
			Bukkit.getServer().getPluginManager().callEvent(ev);
 
		}
		 

	}

}
