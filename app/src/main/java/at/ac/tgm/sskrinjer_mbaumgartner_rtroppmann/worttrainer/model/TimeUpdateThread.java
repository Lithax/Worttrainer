package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class TimeUpdateThread extends Thread {
	public static final int UPDATE_INTERVAL_MS = 1000 * 60; // 1 min
	private TimeListener timerCallback;

	public void setTimeListener(TimeListener l) {
		this.timerCallback = l;
	}

	@Override
	public void run(){
		while(isAlive() && !isInterrupted()) {
			try {
				Thread.sleep(UPDATE_INTERVAL_MS);
			} catch (Exception e) {}
			timerCallback.onUpdateTime(LocalDate.now(), LocalTime.now());
		}
	}
}//end TimeUpdateThread