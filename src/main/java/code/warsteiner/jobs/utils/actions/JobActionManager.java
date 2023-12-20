package code.warsteiner.jobs.utils.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;

import code.warsteiner.jobs.utils.templates.JobAction;

public class JobActionManager {

	private ArrayList<JobAction> list = new ArrayList<JobAction>();
	private HashMap<String, JobAction> maped = new HashMap<String, JobAction>(); 
	private ArrayList<String> list2 = new ArrayList<String>();
	
	public void clearLists() {
		this.list.clear();
		this.maped.clear();
		this.list2.clear();
	}
	
	public ArrayList<JobAction> getArrayList() {
		return this.list;
	}
	
	public ArrayList<String> getArrayListTwo() {
		return this.list2;
	}
	
	public HashMap<String, JobAction> getHashMap() {
		return this.maped;
	}
	
	public void addAction(JobAction e) {
		this.list.add(e);
		this.maped.put(e.getID(), e);
		this.list2.add(e.getID());
	  
	}
	
	public void regiserActions() {
		for(JobAction ac : this.list) { 
			Bukkit.getConsoleSender().sendMessage("§2Register "+ac.getID()+" Event...");
			ac.register();
		}
	}
	
	public boolean isRealAction(String check) {
		
		for(JobAction a : this.list) {
			
			if(a.getID().equalsIgnoreCase(check.toUpperCase())) {
				return true;
			}
			
		}
		return false;
		
	}
	
}
