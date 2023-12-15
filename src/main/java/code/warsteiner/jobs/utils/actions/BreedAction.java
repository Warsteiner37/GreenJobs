package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityTameEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class BreedAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "Breed Animals";
	}

	@Override
	public String getID() {
		return "BREED";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-breed-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(EntityBreedEvent event) {
		
		if (event.getEntity() == null) {
			return;
		}
  
		if(event.getBreeder() instanceof Player) { 
			LivingEntity player = (Player) event.getBreeder();
			UUID ID = player.getUniqueId();
			 
			
			String type = event.getEntity().getType().toString();
			 
			PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "BREED", type, 1);
			Bukkit.getServer().getPluginManager().callEvent(ev);
 
		}
		 

	}

}