package code.warsteiner.jobs.utils.playercommand;

import org.bukkit.command.CommandSender;
 

public abstract class PlayerSubCommand {
	
    public abstract String getName();

    public abstract String getUsage();
  
    public abstract void perform(CommandSender sender, String[] args);
 
    public abstract String getArgsLayout();
     
}
