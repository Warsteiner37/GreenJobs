package code.warsteiner.jobs.manager;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import code.warsteiner.jobs.GreenJobs; 

public class ItemManager {

	private GreenJobs plugin = GreenJobs.getPlugin();

	private HashMap<String, ItemStack> items = new HashMap<String, ItemStack>();
	
	
	@SuppressWarnings("deprecation")
	public ItemStack createOrGetItem(String id, String type, String player_name, int custom_model) {
		
		ItemStack item = null;
		
		
		if(!items.containsKey(id.toLowerCase())) {
			
			ItemStack it = null;
			 
			if (type.contains(";")) {
				String[] split = type.split(";");
				if (split[1] != null) {
					String s = split[1];
					if (split[0].toLowerCase().equalsIgnoreCase("url")) {
						it = plugin.getSkullCreatorAPI().itemFromUrl(s);
					} else if (split[0].toLowerCase().equalsIgnoreCase("uuid")) {
						it = plugin.getSkullCreatorAPI().itemFromUuid(s);
					} 
					if (split[0].toLowerCase().equalsIgnoreCase("base64")) {
						it = plugin.getSkullCreatorAPI().itemFromBase64(s);
					}
				}
			} else {

				try {
					Material used = Material.valueOf(type.toUpperCase());

					it = new ItemStack(used, 1);
				} catch (IllegalArgumentException ex) {
				}
			}
			
			HashMap<String, ItemStack> listed = this.items;
			
			listed.put(id, it);
			
			this.items = listed;
			
			item = it;
			
		} else {
			item = items.get(id.toLowerCase());
		}
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setCustomModelData(custom_model);
		
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_DYE);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE); 
		
		item.setItemMeta(meta);
		
		return item;
		
	}
	
}
