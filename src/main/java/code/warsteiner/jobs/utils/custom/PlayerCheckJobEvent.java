package code.warsteiner.jobs.utils.custom;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import code.warsteiner.jobs.GreenJobs; 
import code.warsteiner.jobs.utils.templates.Job;
import code.warsteiner.jobs.utils.templates.JobAction;

public class PlayerCheckJobEvent extends Event {
	 
    private static final HandlerList handlers = new HandlerList(); 
    private UUID ID; 
    private String block;
    private String act;
    
    public PlayerCheckJobEvent(UUID iD2, String act, String block) { 
        this.ID = iD2;
        this.block = block;
        this.act = act;
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