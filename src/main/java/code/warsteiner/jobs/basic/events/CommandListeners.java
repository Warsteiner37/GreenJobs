package code.warsteiner.jobs.basic.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerAddNewCurrentJobEvent;
import code.warsteiner.jobs.utils.custom.PlayerFinishJobEvent;
import code.warsteiner.jobs.utils.custom.PlayerLeaveAJobEvent;
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobID;

public class CommandListeners implements Listener {

	private GreenJobs plugin = GreenJobs.getPlugin();

	@EventHandler
	public void onJobJoin(PlayerAddNewCurrentJobEvent event) {
		
		Player player = event.getPlayer(); 
		Job job = event.getRealJob();
		
		if(job.getJoinCommands() != null && !job.getJoinCommands().isEmpty()) {
			
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			 
			List<String> commands = job.getJoinCommands();
			  
			for(String command : commands) { 
				Bukkit.dispatchCommand(console, command.replaceAll("<job>", job.getID()).replaceAll("<name>", player.getName()));
			}
			
		}
		
	}
	
	@EventHandler
	public void onJobQuit(PlayerLeaveAJobEvent event) {
		
		Player player = event.getPlayer();
	
		Job job = event.getRealJob();
		
		if(job.getLeaveCommands() != null && !job.getLeaveCommands().isEmpty()) {
			
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			 
			List<String> commands = job.getLeaveCommands();
			  
			for(String command : commands) {
				Bukkit.dispatchCommand(console, command.replaceAll("<job>", job.getID()).replaceAll("<name>", player.getName()));
			}
			
		}
		
	}
	
	@EventHandler
	public void onJobFinish(PlayerFinishJobEvent event) {
		
		Player player = event.getPlayer();
	
		Job job = event.getRealJob();
		JobID item = job.getEveryID().get(event.getWorkedID());
		
		if(item.getCommands() != null && !item.getCommands() .isEmpty()) {
			
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			 
			List<String> commands = item.getCommands();
			  
			for(String command : commands) {
				Bukkit.dispatchCommand(console, command.replaceAll("<job>", job.getID()).replaceAll("<name>", player.getName()));
			}
			
		}
		
	}
	
}
