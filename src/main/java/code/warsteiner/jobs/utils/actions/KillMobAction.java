package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class KillMobAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "Kill Mob Action";
	}

	@Override
	public String getID() {
		return "KILL_MOB";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-killmob-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(EntityDeathEvent event) {

		if (event.getEntity().getKiller() == null) {
			return;
		}

		if (event.getEntity().hasMetadata("spawned-by-spawner")) {
			return;
		}

		if (event.getEntity().getKiller() instanceof Player) {
			Player player = (Player) event.getEntity().getKiller();
 
			UUID ID = player.getUniqueId();
			EntityType block = event.getEntity().getType();
			String BlockID = block.toString();

			PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "KILL_MOB", BlockID);
			Bukkit.getServer().getPluginManager().callEvent(ev);

		}
	}

}
