package code.warsteiner.jobs.utils.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import code.warsteiner.jobs.GreenJobs;
import code.warsteiner.jobs.utils.custom.PlayerDataChangeEvent;

public class JobStats {

	private GreenJobs plugin = GreenJobs.getPlugin();

	private UUID ID;
	private int level;
	private double need;
	private double lvlup;
	private String bought;
	private String joined;

	private int worked_times; 

	private double total;
	private HashMap<String, Double> earnings_dates;

	private HashMap<String, HashMap<String, Integer>> times_broken_a_block_dates;
	private HashMap<String, HashMap<String, Double>> earnings_by_block_dates;

	private HashMap<String, Integer> times_broken_block;
	private HashMap<String, Double> earned_money_broken_block;
	
	private ArrayList<String> worked_dates_list;

	public JobStats(UUID d, int level, double need, double lvlup, double total, String bought, String joined, int worked_times,
		 HashMap<String, Double> earnings_all,
			HashMap<String, HashMap<String, Integer>> times_broken_a_block,
			HashMap<String, HashMap<String, Double>> earnings_by_block, HashMap<String, Integer> times_broken_block,
			HashMap<String, Double> earned_money_broken_block, ArrayList<String> worked_dates_list) {

		this.level = level;
		this.need = need;

		this.bought = bought;
		this.joined = joined;
		this.total = total;
		this.worked_times = worked_times; 
		this.lvlup = lvlup;
		this.earnings_dates = earnings_all;
		this.times_broken_a_block_dates = times_broken_a_block;
		this.earnings_by_block_dates = earnings_by_block;
		this.times_broken_block = times_broken_block;
		this.earned_money_broken_block = earned_money_broken_block;
		this.worked_dates_list = worked_dates_list;
		this.ID = d;
	
	
	}
	
	public UUID getPlayerID() {
		return this.ID;
	}
	
	public void callChangeInData() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				PlayerDataChangeEvent event = new PlayerDataChangeEvent(getPlayerID());
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}.runTask(plugin);
	}
	
	public ArrayList<String> getDatesList() {
		
		if(this.worked_dates_list == null) {
			return new ArrayList<String>();
		}
		
		return this.worked_dates_list;
	}
	
	public void checkDate(String date) {
		
		ArrayList<String> use = null;
		
		if(getDatesList() == null) {
			use = new ArrayList<String>();
		} else {
			use = getDatesList();
		}
		
		if(!use.contains(date)) {
			use.add(date);
		}
		
		this.worked_dates_list = use;
	}
	
	public void setNeed(double nd) {
		this.lvlup = nd;
		
		callChangeInData();
	}
	
	public void setExp(double ep) {
		this.need = ep;
		
		callChangeInData();
	}
	
	public void setLevel(int lvl) {
		this.level = lvl;
	
		callChangeInData();
	}
	
	public void addExp(double d) {
		this.need = this.need + d;
	
		callChangeInData();
	}

	public void addTotalEarnings(double i) {
		this.total = this.total + i;
		
		callChangeInData();
	}
	
	public void addWorkedTimes(int i) {
		this.worked_times = this.worked_times + i;
		
		callChangeInData();
	}
	
	public double getNeed() {
		return this.lvlup;
	}

	public double getTotalEarnings() {
		return this.total;
	}

	public double getEarnedToday() {
		String date = plugin.getBasicPluginManager().getDateTodayFromCal();

		double amount = 0;

		if (!getAllEarnings().containsKey(date)) {
			amount = 0;
		} else {
			amount = getAllEarnings().get(date);
		}

		return amount;

	}

	public Double getEarnedMoneyByBlockIfExist(String id) {
		if (getEarnedMoneyByBlock().containsKey(id)) {
			return getEarnedMoneyByBlock().get(id);
		}
		return 0.0;
	}

	public void addEarnedMoneyByBlock(String block, double am) {

		HashMap<String, Double> list = getEarnedMoneyByBlock();

		if(getEarnedMoneyByBlock().containsKey(block)) {
			list = getEarnedMoneyByBlock();
		} else {
			HashMap<String, Double> test = new HashMap<String, Double>();
			
			test.put(block, 0.0);
			
			list = test;
		}
		
		Double old = list.get(block);

		double calc = old + am;
		
		callChangeInData();

		list.put(block, calc);

		this.earned_money_broken_block = list;
	}

	public HashMap<String, Double> getEarnedMoneyByBlock() {

		if (this.earned_money_broken_block == null) {
			return new HashMap<String, Double>();
		}

		return this.earned_money_broken_block;
	}

	public Double getEarnedMoneyByBlockID(String block) {

		if (getEarnedMoneyByBlock().containsKey(block)) {
			return getEarnedMoneyByBlock().get(block);
		}

		return 0.0;
	}

	public Integer getTimesBrokenBlockIfExist(String id) {
		if (getTimesBrokenABlock().containsKey(id)) {
			return getTimesBrokenABlock().get(id);
		}
		return 0;
	}

	public void addTimesBrokenABlock(String block, int am) {

		HashMap<String, Integer> list = null;

		if(getTimesBrokenABlock().containsKey(block)) {
			list = getTimesBrokenABlock();
		} else {
			HashMap<String, Integer> test = new HashMap<String, Integer>();
			test.put(block, 0);
			list = test;
		}

		int use = 0;

		if (list.containsKey(block)) {
			use = list.get(block);

		}
		
		callChangeInData();

		int calc = use + am;

		list.put(block, calc);

		this.times_broken_block = list;
	}

	public HashMap<String, Integer> getTimesBrokenABlock() {

		if (this.times_broken_block == null) {
			return new HashMap<String, Integer>();
		}

		return this.times_broken_block;
	}

	public HashMap<String, HashMap<String, Double>> getEarningsByBlockDates() {

		if (this.earnings_by_block_dates == null) {
			return new HashMap<String, HashMap<String, Double>>();
		}

		return this.earnings_by_block_dates;
	}

	public void addEarningsByBlockDateToday(String block, double reward) {

		String date = plugin.getBasicPluginManager().getDateTodayFromCal();

		HashMap<String, HashMap<String, Double>> list = null;
		
		if(getEarningsByBlockDates().containsKey(date)) {
			list = getEarningsByBlockDates();
		} else {
			HashMap<String, HashMap<String, Double>> tt = new HashMap<String, HashMap<String,Double>>();
			HashMap<String, Double> d2 = new HashMap<String,Double>();
			d2.put(block, 0.0);
			tt.put(date, d2);
			
			list = tt;
		}

		HashMap<String, Double> d1 = null;
		
		if(getEarningsByBlockDates().containsKey(date)) {
			d1 = getEarningsByBlockDates().get(date);
		}else {
			d1 = new HashMap<String, Double>();
		}

		Double use = 0.0;

		if (d1.containsKey(block)) {
			use = d1.get(block);
		}

		double calc = use + reward;
		
		callChangeInData();

		d1.put(block, calc);

		list.put(date, d1);

		this.earnings_by_block_dates = list;
	}

	public Double getEarningsByBlockToday(String block) {

		String date = plugin.getBasicPluginManager().getDateTodayFromCal();

		if (getEarningsByBlockDates().containsKey(date)) {
			HashMap<String, Double> blocks = getEarningsByBlockDates().get(date);

			if (blocks.containsKey(block)) {
				return blocks.get(block);
			}
			return 0.0;
		}

		return 0.0;

	}

	public HashMap<String, HashMap<String, Integer>> getTimesBrokenABlockDates() {

		if (this.times_broken_a_block_dates == null) {
			return new HashMap<String, HashMap<String, Integer>>();
		}

		return this.times_broken_a_block_dates;
	}

	public void addTimesBrokenBlockDateToday(String block, int reward) {

		String date = plugin.getBasicPluginManager().getDateTodayFromCal();

		HashMap<String, HashMap<String, Integer>> list = null;
		
		if(getTimesBrokenABlockDates().containsKey(date)) {
			list = getTimesBrokenABlockDates();
		} else {
			HashMap<String, HashMap<String, Integer>> tt = new HashMap<String, HashMap<String,Integer>>();
			
			HashMap<String, Integer> d2 = new HashMap<String,Integer>();
			d2.put(block, 0);
			tt.put(date, d2);
			
			list = tt;
			
		}

		HashMap<String, Integer> d1 = null;
		
		if(getTimesBrokenABlockDates().containsKey(date)) {
			d1 = getTimesBrokenABlockDates().get(date);
		} else {
			d1 = new HashMap<String, Integer>();
		}

		Integer use = 0;

		if (d1.containsKey(block)) {
			use = d1.get(block);
		}

		int calc = use + reward;
		
		callChangeInData();

		d1.put(block, calc);

		list.put(date, d1);

		this.times_broken_a_block_dates = list;
	}

	public HashMap<String, Double> getAllEarnings() {

		if (this.earnings_dates == null || this.earnings_dates.isEmpty()) {
			return new HashMap<String, Double>();
		}

		return this.earnings_dates;
	}

	public void addEarningsForToday(double reward) {

		String date = plugin.getBasicPluginManager().getDateTodayFromCal();

		HashMap<String, Double> er = null;
		
		if(getAllEarnings().containsKey(date)) {
			er = getAllEarnings();
		} else {
			HashMap<String, Double> tt = new HashMap<String, Double>();
			tt.put(date, 0.0);
			
			er = tt;
		}

		Double use = 0.0;

		if (er.containsKey(date)) {
			use = er.get(date);
		}

		double calc = use + reward;
		
		callChangeInData();

		er.put(date, calc);

		this.earnings_dates = er;

	}
 
	public int getWorkedTimes() {
		return this.worked_times;
	}

	public String getJoinedDate() {
		return this.joined;
	}

	public void updateJoinedDate(String date) {
		this.joined = date;
	}

	public String getBoughtDate() {
		return this.bought;
	}

	public double getExp() {
		return this.need;
	}

	public int getLevel() {
		return this.level;
	}

}
