package code.warsteiner.jobs.manager;

import java.util.ArrayList;

import code.warsteiner.jobs.utils.templates.JobsMultiplier;

public class MultiplierManager {

	private ArrayList<JobsMultiplier> global = new ArrayList<JobsMultiplier>();
	 
	public ArrayList<JobsMultiplier> getGlobalList() {
		return this.global;
	}
	
}
