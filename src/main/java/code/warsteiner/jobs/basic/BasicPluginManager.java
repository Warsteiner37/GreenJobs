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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.K;

import code.warsteiner.jobs.GreenJobs;
import net.md_5.bungee.api.ChatColor;

public class BasicPluginManager {

	public static final Pattern HEX_PATTERN = Pattern.compile("#(\\w{5}[0-9a-f])#");
	 
	private GreenJobs plugin = GreenJobs.getPlugin();

	
	public String toHex(String textToTranslate) {

		String text = textToTranslate.replaceAll("&", "§");
	 
		Matcher matcher = HEX_PATTERN.matcher(text);
		StringBuffer buffer = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
		}

		return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());

		
	}
	
	public void playSound(Player player, String type) {
		Bukkit.getScheduler().runTask(plugin, () -> {
			 
			FileConfiguration file = plugin.getFileManager().getSoundsConfig();
			
			if(file.contains("Sounds."+type+".Type")) {
				 
				String ty = file.getString("Sounds."+type+".Type");
				float vol = (float) file.getInt("Sounds."+type+".Vol");
				float pitch = (float) file.getInt("Sounds."+type+".Pitch");
				
				player.playSound(player, Sound.valueOf(ty), vol, pitch);
			}
			
		});
	}
	
	public String getDateTodayFromCal() {
		DateFormat format = new SimpleDateFormat( plugin.getFileManager().getConfigConfig().getString("Date"));
		Date data = new Date();
		return format.format(data);
	}
	
	public String Format(double i) {
 
		DecimalFormat t = new DecimalFormat(plugin.getFileManager().getConfigConfig().getString("Format"));
		String b = t.format(i).replaceAll(",", ".");
		return b;
	}

	 
}
