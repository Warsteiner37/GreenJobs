package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;

import com.sk89q.worldedit.world.entity.EntityType;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class FishAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "Fish";
	}

	@Override
	public String getID() {
		return "FISH";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-fish-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerFishEvent event) {

		if (event.getCaught() == null) {
			return;
		}

		String name = event.getCaught().getName().toString();
		String rep = name.replaceAll("Raw ", "").replaceAll(" ", "_");
		 
		Player player = event.getPlayer();
		UUID ID = player.getUniqueId();

		PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "FISH", rep.toUpperCase());
		Bukkit.getServer().getPluginManager().callEvent(ev);

	}

}
