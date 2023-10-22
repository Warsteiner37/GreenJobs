package code.warsteiner.jobs.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import code.warsteiner.jobs.GreenJobs; 

public class MessageManager {

	private GreenJobs plugin = GreenJobs.getPlugin();

	private HashMap<String, String> messages = new HashMap<String, String>();
	private HashMap<String, ArrayList<String>> lists = new HashMap<String, ArrayList<String>>();

	public void clearLists() {
		this.messages.clear();
		this.lists.clear();
	}
	
	public String getMessage(Player player, String key) {
		 
		return plugin.getBasicPluginManager().toHex(player, this.messages.get(key).replaceAll("<prefix>", this.messages.get("prefix")));
		
	}

	public String getPrefix(Player player) {
		return plugin.getBasicPluginManager().toHex(player, this.messages.get("prefix"));
	}
	
	public String getPrefix(CommandSender sender) {
		return plugin.getBasicPluginManager().toHex(sender, this.messages.get("prefix"));
	}
	
	public boolean hasMessage(String key) {

		if (messages.containsKey(key)) {

			return true;

		} else {

			FileConfiguration cfg =  this.getDefaultMessagesConfig();

			if (cfg.contains(key)) {

				String get = cfg.getString(key);

				messages.put(key, get);
 
				return true;

			}

		}

		return false;

	}
	
	private FileConfiguration messages_cfg;
	private File messages_file;

	public void createMessagesFiles() { 
		setDefaultMessages();
	  
	}
	
	public FileConfiguration getDefaultMessagesConfig() {
		return this.messages_cfg;
	}
	
	private List<String> ld = List.of("prefix",
			"job_is_free_message", 
			"job_gui_buy",
			"job_gui_join",
			"job_gui_in", 
			"job_join_message", 
			"job_gui_join_message", 
			"job_buy_message",
			"job_not_enough_money",
			"job_already_joined",
			"job_is_free_gui",
			"job_left_all",
			"no_cat_found",
			"job_nothing_to_leave",
			"rewards_no_other_page",
			"livegui_cancel_not_enough_money",
			"too_many_jobs",
			"levels_no_other_page");
	
	public void loadDefaultValues() {
		
		FileConfiguration cf = getDefaultMessagesConfig(); 
		for(String d : this.ld) { 
			if(cf.contains(d)) {
				
				this.messages.put(d, cf.getString(d));
				
				Bukkit.getConsoleSender().sendMessage("§5Loaded message "+d+"...");
			}
			
		}
		
	}
	
	public void setDefaultMessages() {

		this.messages_file = new File("plugins/GreenJobs/messages/", "messages.yml");

		if (!messages_file.exists()) {
			try {
				messages_file.createNewFile();
				
				messages_cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(messages_file);
			 
				messages_cfg.set("prefix", "&8[&a&lGreenJobs&8]");
				messages_cfg.set("job_is_free_gui", "&a✔ &7Click to add a &bfree &7Job &a✔");
				messages_cfg.set("job_is_free_message", "<prefix> &7You added a free Job!");
				messages_cfg.set("job_gui_buy", "&c✖ &7Click to buy this Job for &c<money>$ &c✖");
				messages_cfg.set("job_gui_join", "&b☆ &7Click to &ajoin &7this Job &b☆");
				messages_cfg.set("job_gui_in", "&a✔ &7You are &ain &7this Job &a✔");
				messages_cfg.set("job_gui_join_message", "&7You are &bin &7this Job!");
				messages_cfg.set("job_join_message", "<prefix> &7You joined the Job <job>&7!");
				messages_cfg.set("job_buy_message", "<prefix> &7You bought the Job <job>&7!");
				messages_cfg.set("job_not_enough_money", "<prefix> &cYou dont have enough Money!");
				messages_cfg.set("job_already_joined", "<prefix> &7You already joined the <job> &7Job!"); 
				messages_cfg.set("job_left_all", "<prefix> &cYou left all current Jobs!");
				messages_cfg.set("job_nothing_to_leave", "<prefix> &cYou are not in any Job!");
				messages_cfg.set("too_many_jobs", "<prefix> &7You reached the &cmax &7Amount of Jobs!");
				messages_cfg.set("rewards_no_other_page", "<prefix> &cThere is no other Pages!");
				messages_cfg.set("no_cat_found", "<prefix> &cCouldnt find any Categories!");
				messages_cfg.set("livegui_cancel_not_enough_money", "<prefix> &cThe process was canceled because you dont have enough money anymore!");
				messages_cfg.set("levels_no_other_page", "<prefix> &cThere is no other Pages!");
				
				try {
					messages_cfg.save(messages_file);
				} catch (IOException e) { 
					e.printStackTrace();
				}
				
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
		
		messages_cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(messages_file);
		 
		
		loadDefaultValues();
	}
 
	

}
