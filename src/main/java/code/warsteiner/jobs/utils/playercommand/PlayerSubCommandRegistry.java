package code.warsteiner.jobs.utils.playercommand;

import java.util.ArrayList;
import java.util.List;

public class  PlayerSubCommandRegistry  {
	  
    private final List<PlayerSubCommand> subCommandList = new ArrayList<PlayerSubCommand>();
 
    public List<PlayerSubCommand> getSubCommandList() {
        return subCommandList;
    }

}
