package code.warsteiner.jobs.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import code.warsteiner.jobs.GreenJobs;

public class BossBarHandler {

	private static GreenJobs plugin = GreenJobs.getPlugin();

	public static HashMap<UUID, BossBar> g = new HashMap<UUID, BossBar>();
	public static HashMap<UUID, Date> last_work = new HashMap<UUID, Date>();

	public static void clearLists() {
		 
		for(Player player : Bukkit.getOnlinePlayers()) {
			
			UUID id = player.getUniqueId();
			
			if(g.containsKey(id)) {
				removeBossBar(id);
			}
			
		}
		
		g.clear();
		last_work.clear();
	}

	public static void createBar(Player p, String name, BarColor color, UUID iD, double d) {
		BossBar b = Bukkit.createBossBar(name, color, BarStyle.SOLID, new BarFlag[] {});

		double real = d;

		if (d >= 1.0) {
			real = 1.0;
		}

		if (d <= 0.1) {
			real = 0.1;
		}

		b.setProgress(real);
		b.setVisible(true);
		g.put(iD, b);

		((BossBar) g.get(iD)).addPlayer(p);
	}

	public static boolean exist(UUID id) {
		return g.get(id) != null;
	}

	public static void removeBossBar(UUID ID) {
		if (g.containsKey(ID)) {
			((BossBar) g.get(ID)).setVisible(false);
			g.remove(ID);
		}
	}

	public static void renameBossBar(String name, UUID ID) {
		((BossBar) g.get(ID)).setTitle(name);
	}

	public static void updateProgress(double pr, UUID ID) {
		double real = pr;

		if (pr >= 1.0) {
			real = 1.0;
		}

		if (pr <= 0.1) {
			real = 0.1;
		}

		((BossBar) g.get(ID)).setProgress(real);
	}

	public static void recolorBossBar(BarColor color, UUID ID) {
		((BossBar) g.get(ID)).setColor(color);
	}

	public static void createTempBossBar(Player p, String name, BarColor color, final UUID ID,
			Integer timeInSecondsBeforeRemove, double pr) {
		createBar(p, name, color, ID, pr);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) plugin, new Runnable() {
			public void run() {
				removeBossBar(ID);
			}
		}, (timeInSecondsBeforeRemove.intValue() * 20));
	}

	public static void startSystemCheck() {

		new BukkitRunnable() {

			public void run() {

				plugin.executor.submit(() -> {

					for (Player p : Bukkit.getOnlinePlayers()) {

						UUID d = p.getUniqueId();
						
						Date lastworked = last_work.get(p.getUniqueId());

						if (lastworked == null) {
							continue;
						}

						boolean check = lastworked.after(new Date());

						if (check == false) {
							if (last_work.containsKey(d)) {
								BossBarHandler.removeBossBar(d);
								last_work.remove(d);
							}
						}

					}
				});

			}
		}.runTaskTimer(plugin, 0, 30);
	}

	public static double calculate(double exp, boolean ismaxlevel, double need) {
		double use = 1.0;
		if (!ismaxlevel) {
			double jobneed = need / 100;

			double p = exp / jobneed;

			double max = 1.0 / 100;

			double one = max * p;

			if (one >= 1.0) {
				one = 1.0;
			}
			use = one;

		} else {
			use = 1.0;

		}

		return use;
	}
}