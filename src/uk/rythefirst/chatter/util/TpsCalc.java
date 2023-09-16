package uk.rythefirst.chatter.util;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import uk.rythefirst.chatter.Main;

public class TpsCalc {

	public int tps = 0;

	public BukkitTask tpsTask = null;

	public void startCalc() {
		tpsTask = new BukkitRunnable() {

			long sec;
			long currentSec;
			int ticks;

			@Override
			public void run() {
				sec = (System.currentTimeMillis() / 1000);

				if (currentSec == sec) {
					// this code block triggers each tick

					ticks++;
				} else {
					// this code block triggers each second

					currentSec = sec;
					tps = (tps == 0 ? ticks : ((tps + ticks) / 2));
					ticks = 0;

				}

			}
		}.runTaskTimer(Main.instance, 0, 1);
	}

	public void stopCalc() {
		if (!(tpsTask == null)) {
			tpsTask.cancel();
			tpsTask = null;
		}
	}

}
