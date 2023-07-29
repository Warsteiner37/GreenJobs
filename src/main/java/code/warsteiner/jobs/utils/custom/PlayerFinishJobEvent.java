package code.warsteiner.jobs.utils.custom;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import code.warsteiner.jobs.GreenJobs; 
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobAction;

public class PlayerFinishJobEvent extends Event {
	 
    private static final HandlerList handlers = new HandlerList(); 
    private UUID ID; 
    private String block;
    private String act;
    private String job;
    private Job jb;
    
    public PlayerFinishJobEvent(UUID iD2, String act, String block, String job, Job jb) { 
        this.ID = iD2;
        this.block = block;
        this.act = act;
        this.job = job;
        this.jb = jb;
    }
    
    public Job getRealJob() {
    	return this.jb;
    }
    
    public String getJob() {
    	return this.job;
    }
    
    public String getWorkedAction() {
    	return this.act;
    }
    
    public String getWorkedID() {
    	return this.block;
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