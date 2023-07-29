package code.warsteiner.jobs.utils.templates;

import org.bukkit.event.Listener;

public abstract class JobAction {

	public abstract String getName();

	public abstract String getID();

	public abstract String getWorldGuardFlag();

	public abstract void register();

}
