package code.warsteiner.jobs.utils.admincommand;

import java.util.ArrayList;
import java.util.List;

public class  AdminSubCommandRegistry  {
	  
    private final List<AdminSubCommand> subCommandList = new ArrayList<AdminSubCommand>();
 
    public List<AdminSubCommand> getSubCommandList() {
        return subCommandList;
    }

}
