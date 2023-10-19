package code.warsteiner.jobs.basic;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.K;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.support.PlaceHolderManager;
import net.md_5.bungee.api.ChatColor;

public class BasicPluginManager {

	public static final Pattern HEX_PATTERN = Pattern.compile("#(\\w{5}[0-9a-f])#");

	private GreenJobs plugin = GreenJobs.getPlugin();

	public String toHex(Player player, String textToTranslate) {

		String text = textToTranslate.replaceAll("&", "§");

		Matcher matcher = HEX_PATTERN.matcher(text);
		StringBuffer buffer = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
		}

		String trans = ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());

		if (plugin.isInstalled("PlaceHolderAPI")) {
			return PlaceHolderManager.getMessage(player, trans);
		}

		return trans;

	}

	public String toHex(CommandSender player, String textToTranslate) {

		String text = textToTranslate.replaceAll("&", "§");

		Matcher matcher = HEX_PATTERN.matcher(text);
		StringBuffer buffer = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
		}

		String trans = ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());

		if (player instanceof Player) {
			if (plugin.isInstalled("PlaceHolderAPI")) {
				Player d = (Player) player;
				return PlaceHolderManager.getMessage(d, trans);
			}
		}

		return trans;

	}

	public void playSound(Player player, String type) {
		Bukkit.getScheduler().runTask(plugin, () -> {

			FileConfiguration file = plugin.getFileManager().getSoundsConfig();

			if (file.contains("Sounds." + type + ".Type")) {

				if (file.contains("Sounds." + type + ".Vol") && file.contains("Sounds." + type + ".Pitch")) {

					String ty = file.getString("Sounds." + type + ".Type");
					
					Sound sound = null;
					float vol = 0;
					float pitch = 0;
					
					try {
						sound = Sound.valueOf(ty);
					} catch (IllegalArgumentException ex) {
						Bukkit.getConsoleSender().sendMessage("Failed to find Sound ~ "+sound+"!");
					}

					try {
						 vol = Float.valueOf((float) file.getDouble("Sounds." + type + ".Vol"));
					 
					} catch (IllegalArgumentException ex) {
						Bukkit.getConsoleSender().sendMessage("Failed to load Volume for ~ "+sound+"!");
					}

					try {
						pitch = Float.valueOf((float) file.getDouble("Sounds." + type + ".Pitch"));
						  
					} catch (IllegalArgumentException ex) {
						Bukkit.getConsoleSender().sendMessage("Failed to load Pitch for ~ "+sound+"!");
					}
 
					player.playSound(player, sound, vol, pitch);
				}

			}

		});
	}

	public String getDateTodayFromCal() {
		DateFormat format = new SimpleDateFormat(plugin.getFileManager().getConfigConfig().getString("Date"));
		Date data = new Date();
		return format.format(data);
	}

	public String Format(double i) {

		DecimalFormat t = new DecimalFormat(plugin.getFileManager().getConfigConfig().getString("Format"));
		String b = t.format(i).replaceAll(",", ".");
		return b;
	}

}
