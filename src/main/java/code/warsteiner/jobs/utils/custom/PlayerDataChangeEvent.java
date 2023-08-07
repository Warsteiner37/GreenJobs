package code.warsteiner.jobs.utils.custom;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDataChangeEvent extends Event {
	 
    private static final HandlerList handlers = new HandlerList(); 
    private UUID ID;   
    
    public PlayerDataChangeEvent(UUID iD2) { 
        this.ID = iD2; 
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