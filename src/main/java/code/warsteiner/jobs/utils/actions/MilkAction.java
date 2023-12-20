package code.warsteiner.jobs.utils.actions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class MilkAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();
 
	@Override
	public String getID() {
		return "MILK";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-milk-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(PlayerInteractAtEntityEvent event) {
		Entity clicked = event.getRightClicked();

		if (clicked instanceof Cow) {
			
			if (event.getPlayer() == null) {
				return;
			}

			if (event.getPlayer().getItemInHand() == null) {
				return;
			}

			if (event.getPlayer().getItemInHand().getType() != Material.BUCKET) {
				return;
			}
			
			Player player = event.getPlayer();
			UUID ID = player.getUniqueId();
			
			String ent = clicked.getType().toString().toUpperCase();

			PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "MILK", ent, 1);
			Bukkit.getServer().getPluginManager().callEvent(ev);
		}

	}

}
