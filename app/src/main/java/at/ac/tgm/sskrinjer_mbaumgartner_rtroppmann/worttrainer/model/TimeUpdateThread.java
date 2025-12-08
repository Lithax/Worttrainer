package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class TimeUpdateThread {

    public static final int UPDATE_INTERVAL_MIN = 1;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private TimeListener timerCallback;

    public void setTimeListener(TimeListener l) {
        this.timerCallback = l;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            if (timerCallback != null) 
                timerCallback.onUpdateTime(LocalDate.now(), LocalTime.now());
        }, 0, UPDATE_INTERVAL_MIN, TimeUnit.MINUTES);
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}//end TimeUpdateThread