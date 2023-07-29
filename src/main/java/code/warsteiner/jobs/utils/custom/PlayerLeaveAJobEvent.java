package code.warsteiner.jobs.utils.custom;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.templates.Job;

public class PlayerLeaveAJobEvent extends Event {
	 
    private static final HandlerList handlers = new HandlerList(); 
    private UUID ID;
    private String job;
    
    public PlayerLeaveAJobEvent(UUID iD2, String job) { 
        this.ID = iD2;
        this.job = job;
    }
    
    public String getJob() {
    	return this.job;
    }
    
    public Job getRealJob() {
    	return GreenJobs.getPlugin().getJobAPI().getLoadedJobsHash().get(this.job);
    }
 
    public UUID getID() {
    	return this.ID;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public Player getPlayer() {
    	return Bukkit.getPlayer(this.ID);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}