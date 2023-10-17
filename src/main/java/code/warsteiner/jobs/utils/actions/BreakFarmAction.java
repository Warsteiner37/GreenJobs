package code.warsteiner.jobs.utils.actions;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.BlockAPI;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class BreakFarmAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@Override
	public String getName() {
		return "Farmer (Break)";
	}

	private List<Material> breakingMaterials = List.of(Material.SUGAR_CANE, Material.CACTUS, Material.BAMBOO);

	@Override
	public String getID() {
		return "FARM_BREAK";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-farm-break-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(BlockBreakEvent event) {

		BlockAPI bapi = plugin.getBlockAPI();

		Block block = event.getBlock();
		Material type = block.getType();
		String BlockID = block.getType().toString();
		Location loc = block.getLocation();

		Player player = event.getPlayer();
		UUID ID = player.getUniqueId();

		int amount = 0;
	 
		if (breakingMaterials.contains(type)) {
 
			for (int i = 0; i <= 16; i++) {
				Block bl = block.getLocation().add(0, i, 0).getBlock();
				Material d = bl.getType();

				if (d.equals(type)) {
					bapi.removeBlock(loc, BlockID);
					amount++;
				}

			}

			if (amount != 1) {
				if (bapi.isFullyGrown(block)) {
					PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "FARM_BREAK", BlockID, amount);
					Bukkit.getServer().getPluginManager().callEvent(ev);
				}
			}

		} else {

			if (bapi.isFullyGrown(block)) {
				PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "FARM_BREAK", BlockID, 1);
				Bukkit.getServer().getPluginManager().callEvent(ev);
			}
		}

	}

}
