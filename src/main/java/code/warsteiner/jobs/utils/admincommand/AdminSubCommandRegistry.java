package code.warsteiner.jobs.utils.admincommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import code.warsteiner.jobs.utils.enums.AdminCommandCategory;

public class  AdminSubCommandRegistry  {
	  
    private final List<AdminSubCommand> subCommandList = new ArrayList<AdminSubCommand>();
    private final HashMap<AdminCommandCategory, ArrayList<AdminSubCommand>> sorted = new HashMap<AdminCommandCategory, ArrayList<AdminSubCommand>>();
 
    public List<AdminSubCommand> getSubCommandList() {
        return subCommandList;
    }
 
    public HashMap<AdminCommandCategory, ArrayList<AdminSubCommand>> getCommandsByCategory() {
    	 
    	if(this.sorted.isEmpty()) {
    		this.subCommandList.forEach((command) -> {
    	    	 
        		AdminCommandCategory cat = command.getCategory();
        		
        		ArrayList<AdminSubCommand> got = null;
        		
        		if(this.sorted.containsKey(cat)) {
        			got = this.sorted.get(cat);
        		} else {
        			got = new ArrayList<AdminSubCommand>();
        		}
        		 
        		got.add(command);
        		
        		this.sorted.put(cat, got);
        	});
    	}
    	 
    	return this.sorted;
    }
    
}
