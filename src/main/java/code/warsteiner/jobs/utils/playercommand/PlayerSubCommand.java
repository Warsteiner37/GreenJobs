package code.warsteiner.jobs.utils.playercommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
 

public abstract class PlayerSubCommand {
	
    public abstract String getName();

    public abstract String getUsage(CommandSender sender);
  
    public abstract void perform(CommandSender sender, String[] args);
 
    public abstract String getArgsLayout();
     
}
