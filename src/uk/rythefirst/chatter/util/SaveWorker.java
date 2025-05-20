package uk.rythefirst.chatter.util;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.bounties.DataHandler;

public class SaveWorker {
	
	private BukkitTask worker = null;
	
	public SaveWorker() {
		
		worker = new BukkitRunnable(){
			
			@Override
			public void run() {
				Main.configManager.saveAllAsync();
				DataHandler.saveBounties(false);
			}
			
		}.runTaskTimer(Main.instance, 36000L, 36000L);
		
	}
	
	public void stopWorker() {
		worker.cancel();
	}

}
