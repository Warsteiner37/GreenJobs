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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.api.BlockAPI;
import code.warsteiner.jobs.utils.custom.PlayerCheckJobEvent;
import code.warsteiner.jobs.utils.templates.JobAction;

public class SmeltAction extends JobAction implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();
 
	@Override
	public String getID() {
		return "SMELT";
	}

	@Override
	public String getWorldGuardFlag() {
		return "greenjobs-smelt-flag";
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, GreenJobs.getPlugin());
	}
	
	private List<InventoryType> list = List.of(InventoryType.FURNACE, InventoryType.BLAST_FURNACE);

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(InventoryClickEvent event) {
 
		Player player = (Player) event.getWhoClicked();
		UUID ID = player.getUniqueId();

		if (!this.list.contains(event.getInventory().getType())) {
			return;
		}
		if (event.getSlot() != 2 || event.getInventory().getItem(2) == null) {
			return;
		}

		Material cr = event.getCursor().getType();

		if (!cr.equals(Material.AIR)) {
			return;
		}

		ItemStack item = event.getInventory().getItem(2);

		int amount = item.getAmount();

		PlayerCheckJobEvent ev = new PlayerCheckJobEvent(ID, "SMELT", item.getType().toString(), amount);
		Bukkit.getServer().getPluginManager().callEvent(ev);

	}

}