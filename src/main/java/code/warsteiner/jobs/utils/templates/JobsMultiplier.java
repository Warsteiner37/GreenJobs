package code.warsteiner.jobs.utils.templates;

import code.warsteiner.jobs.utils.enums.MultiplierType;
import code.warsteiner.jobs.utils.enums.MultiplierWeight;

public class JobsMultiplier {
 
	private String name;
	
	private MultiplierType type;
	
	private String hasJob;
	
	private String until;
	
	private MultiplierWeight weight;
	private double val;

	public JobsMultiplier(String name, MultiplierType type_of, String until, MultiplierWeight weight, double value, String job) {
		this.name = name; 
		this.type = type_of;
		this.until = until;
		this.weight = weight;
		this.val = value;
		this.hasJob = job;
	}
	
	public String getName() {
		return name;
	}
	
	public String getJob() {
		return hasJob;
	}
	
	public boolean hasJob() {
		return hasJob != null;
	}
	
	public double getValue() {
		return val;
	}
	
	public MultiplierWeight getWeight() {
		return weight;
	}
	
	public String getUntil() {
		return until;
	}
	
	public MultiplierType getType() {
		return type;
	}
	
 
	
}
